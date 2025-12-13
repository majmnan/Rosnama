package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.DTO.ExternalEventDTOIn;
import com.example.rosnama.DTO.ExternalEventDTOOut;
import com.example.rosnama.DTO.InternalEventDTOOut;
import com.example.rosnama.Model.*;
import com.example.rosnama.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.core.*;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalEventService {

    // connect to database
    private final AdminRepository adminRepository;
    private final ExternalEventRepository externalEventRepository;
    private final ExternalEventRequestRepository externalEventRequestRepository;
    private final EventOwnerRepository eventOwnerRepository;
    private final CategoryRepository categoryRepository;
    private final NotificationService notificationService;
    private final  InternalEventRepository internalEventRepository;
    private final AiService aiService;
    private final InternalEventService internalEventService;

    // get all external events (By admin)
    public List<ExternalEvent> getAllExternalEvents(Integer adminId) {
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null){
            throw new ApiException("admin not found");
        }
        return externalEventRepository.findAll();
    }


    // add a new external event (By admin)
    public  void addExternalEventByAdmin(Integer adminId, ExternalEventDTOIn externalEventDTO) {
        // check admin exists
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null)
            throw new ApiException("admin not found");

        // check category exists
        Category category = categoryRepository.findCategoryById(externalEventDTO.getCategoryId());
        if (category == null)
            throw new ApiException("Category not found");

        // add the external event information to the database and save it
        ExternalEvent event = new ExternalEvent(
                null, externalEventDTO.getTitle(), externalEventDTO.getOrganizationName(), externalEventDTO.getDescription(),
                externalEventDTO.getCity(), externalEventDTO.getStartDate(), externalEventDTO.getEndDate(),
                externalEventDTO.getStartTime(), externalEventDTO.getEndTime(), externalEventDTO.getUrl(),
                "Upcoming", externalEventDTO.getType(), null, null, category);

        externalEventRepository.save(event);
        // set category
        // save

         }

    // update external event (By admin)
    public void updateExternalEventByAdmin(Integer adminId, Integer id, ExternalEventDTOIn externalEventDTO) {
        // check admin exists
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null) throw new ApiException("admin not found");
        // check external event exists
        ExternalEvent old = externalEventRepository.findExternalEventById(id);
        if (old == null) throw new ApiException("external event not found");
        // update from DTO to old
        old.setTitle(externalEventDTO.getTitle());
        old.setDescription(externalEventDTO.getDescription());
        old.setCity(externalEventDTO.getCity());
        old.setStartDate(externalEventDTO.getStartDate());
        old.setEndDate(externalEventDTO.getEndDate());
        old.setStartTime(externalEventDTO.getStartTime());
        old.setEndTime(externalEventDTO.getEndTime());
        old.setUrl(externalEventDTO.getUrl());
        old.setType(externalEventDTO.getType());
        // update category
        Category category = categoryRepository.findCategoryById(externalEventDTO.getCategoryId());
        if (category == null) throw new ApiException("Category not found");
        old.setCategory(category);
        // save
        externalEventRepository.save(old);
    }

    // delete external event from the database by admin
    public  void deleteExternalEvent(Integer adminId, Integer id) {
       // check admin exists
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null) throw new ApiException("admin not found");
        // check external event exists
        ExternalEvent externalEvent = externalEventRepository.findExternalEventById(id);
        if (externalEvent == null) throw new ApiException("external event not found");
        // delete
        externalEventRepository.delete(externalEvent);
    }


    /// extra endpoints

    // add event by event owner
    public void requestEventByOwner (ExternalEventDTOIn externalEventDTO){
        // check first if owner exist
        EventOwner  owner = eventOwnerRepository.findEventOwnerById(externalEventDTO.getOwnerId());
        if (owner == null ) throw new ApiException("event owner not found !");
        // check category exists
        Category category = categoryRepository.findCategoryById(externalEventDTO.getCategoryId());
        if (category == null) throw new ApiException("Category not found");
        // save
        ExternalEvent event = new ExternalEvent(
                null, externalEventDTO.getTitle(), externalEventDTO.getOrganizationName(), externalEventDTO.getDescription(),
                externalEventDTO.getCity(), externalEventDTO.getStartDate(), externalEventDTO.getEndDate(),
                externalEventDTO.getStartTime(), externalEventDTO.getEndTime(), externalEventDTO.getUrl(),
                "InActive", externalEventDTO.getType(), null, owner, category);

        externalEventRepository.save(event);
        ExternalEventRequest request = new ExternalEventRequest(null, "Requested", null, event);
        externalEventRequestRepository.save(request);

        //notify admin of the request
        List<Admin> admins = adminRepository.findAll();
        admins.forEach(admin ->
        notificationService.notify(
                admin.getEmail(),
                admin.getPhoneNumber(),
                "Event Price Negotiation",
                admin.getUsername(),
                " New external event:\n" + event.getTitle() + "\nrequest has been submitted by " + owner.getUsername()
        ));
    }


    // update event by event owner
    public void updateEventByOwner(Integer ownerId, Integer eventId, ExternalEventDTOIn externalEventDTO){

        // check owner exists
        EventOwner owner = eventOwnerRepository.findEventOwnerById(ownerId);
        if(owner == null) throw new ApiException("event owner not found !");

        // check event exists
        ExternalEvent old = externalEventRepository.findExternalEventById(eventId);
        if(old == null) throw new ApiException("event not found !");

        // check if the owner who want to update it owns this event
        if (!old.getEventOwner().getId().equals(ownerId)) throw new ApiException("You do not own this event");

        // update
        old.setTitle(externalEventDTO.getTitle());
        old.setDescription(externalEventDTO.getDescription());
        old.setCity(externalEventDTO.getCity());
        old.setStartDate(externalEventDTO.getStartDate());
        old.setEndDate(externalEventDTO.getEndDate());
        old.setStartTime(externalEventDTO.getStartTime());
        old.setEndTime(externalEventDTO.getEndTime());
        old.setUrl(externalEventDTO.getUrl());
        old.setType(externalEventDTO.getType());
        // check category exists and update
        Category category = categoryRepository.findCategoryById(externalEventDTO.getCategoryId());
        if (category == null) throw new ApiException("Category not found");
        old.setCategory(category);
        // save
        externalEventRepository.save(old);
    }

    // delete  event by event owner
    public void deleteEventByOwner(Integer ownerId, Integer eventId){
        // check owner exists
        EventOwner owner = eventOwnerRepository.findEventOwnerById(ownerId);
        if(owner == null)
            throw new ApiException("event owner not found !");

        // check event exists
        ExternalEvent event = externalEventRepository.findExternalEventById(eventId);
        if(event == null)
            throw new ApiException("event not found !");

        // check if the owner who want to update it owns this event
        if(!event.getEventOwner().getId().equals(ownerId))
            throw new ApiException("you do not own this event to update its information!");

        // delete
        externalEventRepository.delete(event);
    }

    //get OnGoing Events
    public List<ExternalEventDTOOut> getOnGoingExternalEvents(){
        return convertToDtoOut(externalEventRepository.findExternalEventsByStatusOrderByEndDateAsc("OnGoing"));
    }

    // get all active events to show to users
    public List<ExternalEventDTOOut> getUpcomingExternalEvents() {
        return convertToDtoOut(externalEventRepository.findExternalEventsByStatusOrderByStartDateAsc("Upcoming"));
    }

    // get events between two dates
    public List<ExternalEventDTOOut> getEventsOnGoingBetween(LocalDate after, LocalDate before){
        return convertToDtoOut(externalEventRepository.findExternalEventsByDateBetween(after, before));
    }

    // get events by type
    public List<ExternalEventDTOOut> getEventsByType(String type) {
        return convertToDtoOut(externalEventRepository.findExternalEventsByType(type));
    }

    // get events by city
    public List<ExternalEventDTOOut> getEventsByCity(String city) {
        return convertToDtoOut(externalEventRepository.findExternalEventsByCity(city));
    }

    // get OnGoing events by category
    public List<ExternalEventDTOOut> getOngoingByCategory(Integer categoryId){
        Category category = categoryRepository.findCategoryById(categoryId);
        if(category == null)
            throw new ApiException("Category not found");

        return convertToDtoOut(externalEventRepository.findExternalEventsByStatusAndCategoryOrderByEndDateAsc("OnGoing",category));
    }

    public List<ExternalEventDTOOut> recommendDependsOnEvent(Integer eventId){
        List<ExternalEventDTOOut> dependsOn = convertToDtoOut(List.of(externalEventRepository.findExternalEventById(eventId)));
        List<ExternalEventDTOOut> from = convertToDtoOut(externalEventRepository.findExternalEventsByDateBetween(LocalDate.now(),LocalDate.now().plusDays(7)));
        return recommend(dependsOn, from);
    }

    public List<ExternalEventDTOOut> recommendDependsOnUserHighRateEvents(Integer userId){
        List<InternalEventDTOOut> dependsOn = internalEventService.convertToDtoOut(internalEventRepository.findInternalEventHighReviewByUser(userId));
        List<ExternalEventDTOOut> from = convertToDtoOut(externalEventRepository.findExternalEventsByDateBetween(LocalDate.now(),LocalDate.now().plusDays(7)));
        return recommend(dependsOn, from);
    }

    public List<ExternalEventDTOOut> recommendDependsOnUserAttendedEvents(Integer userId){
        List<InternalEventDTOOut> dependsOn = internalEventService.convertToDtoOut(internalEventRepository.findInternalEventsByUserIdAndRegistrationStatus(userId, "Used"));
        List<ExternalEventDTOOut> from = convertToDtoOut(externalEventRepository.findExternalEventsByDateBetween(LocalDate.now(),LocalDate.now().plusDays(7)));
        return recommend(dependsOn, from);
    }

    public List<ExternalEventDTOOut> recommend(
            List<?> dependsOn,
            List<ExternalEventDTOOut> from
    ) {

        // Basic validation
        if (dependsOn == null || dependsOn.isEmpty() || from == null || from.isEmpty()) {
            return List.of();
        }

        String dependsClass;
        if(dependsOn.get(0) instanceof InternalEventDTOOut)
            dependsClass = "InternalEventDTOOut";
        else if(dependsOn.get(0) instanceof ExternalEventDTOOut)
            dependsClass = "ExternalEventDTOOut";
        else
            throw new ApiException("invalid List type");
        // 1. Build AI prompt
        String prompt = """
                You are an AI recommendation engine for an event management system called Rosnama.
                
                You are given two inputs:
                
                1) dependsOn:
                A list of %s objects representing user preferences.
                
                2) from:
                A list of ExternalEventDTOOut objects representing candidate events.
                
                Your task:
                - Recommend the TOP 3 most relevant events from "from" based on "dependsOn".
                - Rank them from most relevant to least relevant.
                - Do NOT recommend the same event as dependsOn.
                - Use semantic similarity, not exact text matching.
                
                Priority rules:
                - Highest: categoryName, type
                - Use title and description semantically.
                - High: city, location
                - Medium: startDate, startTime
                - Low: price, dailyCapacity
                
                Tie-breaking rules:
                - Closer date first
                - Then lower price
                
                IMPORTANT:
                - Return ONLY valid JSON
                - Return a JSON ARRAY
                - Each element MUST match ExternalEventDTOOut exactly
                - No explanations
                - No markdown
                - No extra text
                
                dependsOn:
                """.formatted(dependsOn) + aiService.toJson(dependsOn) + """
                
                from:
                """ + aiService.toJson(from);

        // 2. Call AI
        String aiResponse = aiService.callAi(prompt);

        // 3. Parse AI response into DTO
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<ExternalEventDTOOut> recommended =
                    objectMapper.readValue(
                            aiService.extractJsonArray(aiResponse),
                            new TypeReference<>() {
                            }
                    );

            return recommended;

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI recommendation", e);
        }
    }





    public List<ExternalEventDTOOut> convertToDtoOut(List<ExternalEvent> events){
        return events.stream().map(event -> new ExternalEventDTOOut(
                event.getTitle(), event.getOrganizationName(),
                event.getDescription(), event.getCity(),
                event.getStartDate(), event.getEndDate(),
                event.getStartTime(), event.getEndTime(),
                event.getUrl(), event.getType(),
                event.getCategory().getName()
        )).toList();
    }




}
