package com.example.rosnama.Service;

import com.example.rosnama.DTO.ExternalEventDTOIn;
import com.example.rosnama.Repository.ExternalEventRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import serpapi.SerpApi;
import serpapi.SerpApiException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SerpService {

    @Value("${serp.token}")
    private String serpToken;
    private final ExternalEventRepository externalEventRepository;

    // ===============================
    // Public API
    // ===============================

    public List<ExternalEventDTOIn> fetchExternalEvents(LocalDate from, LocalDate to) {

        try {
            SerpApi serpApi = new SerpApi();

            Map<String, String> params = new HashMap<>();
            params.put("engine", "google_events");
            params.put("q", "events in Saudi Arabia");
            params.put("hl", "en");
            params.put("gl", "sa");
            params.put("api_key", serpToken);

            JsonObject json = serpApi.search(params);
            JsonArray eventsArray = json.getAsJsonArray("events_results");

            if (eventsArray == null) {
                return Collections.emptyList();
            }

            List<ExternalEventDTOIn> result = new ArrayList<>();

            for (JsonElement element : eventsArray) {
                JsonObject eventObj = element.getAsJsonObject();
                ExternalEventDTOIn dto = mapToExternalEventDTO(eventObj, from, to);
                if(dto == null)
                    continue;
                result.add(dto);
            }

            return result;

        } catch (SerpApiException e) {
            return Collections.emptyList();
        }
    }

    // ===============================
    // Mapper (JsonObject → DTO)
    // ===============================

    private ExternalEventDTOIn mapToExternalEventDTO(
            JsonObject eventObj,
            LocalDate fallbackFrom,
            LocalDate fallbackTo
    ) {

        ExternalEventDTOIn dto = new ExternalEventDTOIn();


        dto.setTitle(
                nonEmpty(getStringSafe(eventObj, "title"), "External Event")
        );

        if((externalEventRepository.findExternalEventByTitle(dto.getTitle())) != null)
            return null;


        dto.setDescription(
                nonEmpty(getStringSafe(eventObj, "description"),
                        "Imported from Google Events")
        );

        dto.setCity(
                nonEmpty(extractCity(eventObj), "Riyadh")
        );

        dto.setUrl(
                nonEmpty(getStringSafe(eventObj, "link"),
                        "https://www.google.com")
        );

        dto.setType("Others");

        LocalDate startDate = extractStartDate(eventObj);
        LocalDate endDate = extractEndDate(eventObj);

        dto.setStartDate(startDate != null ? startDate : fallbackFrom);
        dto.setEndDate(endDate != null ? endDate : fallbackTo);

        dto.setCategoryId(1);
        dto.setOrganizationName("Google Events");
        dto.setStartTime(LocalTime.of(0,0));
        dto.setEndTime(LocalTime.of(23,59));

        return dto;
    }

    // ===============================
    // City Extraction
    // ===============================

    private String extractCity(JsonObject eventObj) {

        String address = getStringSafe(eventObj, "address");
        if (address != null) {
            if (address.contains("Riyadh")) return "Riyadh";
            if (address.contains("Jeddah")) return "Jeddah";
            if (address.contains("Dammam")) return "Dammam";
        }

        String location = getStringSafe(eventObj, "location");
        if (location != null) {
            if (location.contains("Riyadh")) return "Riyadh";
            if (location.contains("Jeddah")) return "Jeddah";
            if (location.contains("Dammam")) return "dammam";
        }

        return "Saudi";
    }

    // ===============================
    // Date Extraction
    // ===============================

    private LocalDate extractStartDate(JsonObject eventObj) {

        if (eventObj.has("date") && eventObj.get("date").isJsonObject()) {
            JsonObject dateObj = eventObj.getAsJsonObject("date");

            if (dateObj.has("start_date")) {
                return parseDate(dateObj.get("start_date").getAsString());
            }
        }

        return null;
    }

    private LocalDate extractEndDate(JsonObject eventObj) {

        if (eventObj.has("date") && eventObj.get("date").isJsonObject()) {
            JsonObject dateObj = eventObj.getAsJsonObject("date");

            if (dateObj.has("end_date")) {
                return parseDate(dateObj.get("end_date").getAsString());
            }
        }

        return null;
    }

    private LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    // ===============================
    // Safe JSON Helpers
    // ===============================

    /**
     * Handles:
     * - String
     * - Array<String>
     */
    private String getStringSafe(JsonObject obj, String key) {

        if (!obj.has(key) || obj.get(key).isJsonNull()) {
            return null;
        }

        JsonElement element = obj.get(key);

        if (element.isJsonPrimitive()) {
            return element.getAsString();
        }

        if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            if (!array.isEmpty()) {
                return array.get(0).getAsString();
            }
        }

        return null;
    }

    private String nonEmpty(String value, String fallback) {
        return (value == null || value.isBlank()) ? fallback : value;
    }
}
