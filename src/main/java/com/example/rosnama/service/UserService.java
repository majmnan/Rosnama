package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.DTO.RecommendationDTO;
import com.example.rosnama.Model.ExternalEvent;
import com.example.rosnama.Model.User;
import com.example.rosnama.Repository.ExternalEventRepository;
import com.example.rosnama.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ExternalEventRepository externalEventRepository;
    private final AiService aiService;

    public List<User>getAllUsers(){
        return userRepository.findAll();
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public void updateUser(Integer id , User user){
        User old = userRepository.findUserById(id);
        if(old == null){
            throw new ApiException("User not Exists");
        }
        old.setAge(user.getAge());
        old.setEmail(user.getEmail());
        old.setPassword(user.getPassword());
        old.setPhoneNumber(user.getPhoneNumber());
        old.setUsername(user.getUsername());
        userRepository.save(old);
    }

    public void deleteUser(Integer id) {
        User user =   userRepository.findUserById(id);
        if(user == null){
            throw new ApiException("User not found");
        }
        userRepository.delete(user);
    }


    /// Extra endpoint

    public String generateAiRecommendations(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        // events the user attended
        List<ExternalEvent> attended = externalEventRepository.findExternalEventsByUserAttendance(userId);

        // all active upcoming events
        List<ExternalEvent> active = externalEventRepository.findExternalEventsByStatus("Active");

        // build AI prompt
        String prompt = buildRecommendationPrompt(attended, active);

        // ask AI
        String aiResponse = aiService.ask(prompt);

        // parse JSON (coming from AI)
        List<RecommendationDTO> recList = parseRecommendations(aiResponse, active);

        // convert to plain text (final output)
        return formatAsText(recList);
    }


    // Build prompt for AI
    private String buildRecommendationPrompt(List<ExternalEvent> attended, List<ExternalEvent> active) {

        String attendedStr = attended.isEmpty()
                ? "The user has not attended any events before."
                : attended.stream()
                .map(e -> "- " + e.getTitle() + " | " + e.getType() + " | " + e.getCity())
                .reduce("", (a, b) -> a + b + "\n");

        String activeStr = active.stream()
                .map(e -> "{id:" + e.getId() + ", title:'" + e.getTitle() + "', type:'" + e.getType() + "', city:'" + e.getCity() + "'}")
                .reduce("", (a, b) -> a + b + "\n");

        return """
                You are an event recommendation system.

                User previously attended:
                %s

                Active upcoming events:
                %s

                Recommend 3 events from the active list.
                Format response as JSON:
                [
                  { "eventId": 3, "aiReason": "reason text" }
                ]
                """.formatted(attendedStr, activeStr);
    }


    // Parse AI response into DTO list
    private List<RecommendationDTO> parseRecommendations(String aiJson, List<ExternalEvent> active) {

        try {
            // parse AI JSON output
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> list = mapper.readValue(aiJson, List.class);

            return list.stream().map(item -> {

                Integer eventId = (Integer) item.get("eventId");
                String aiReason = (String) item.get("aiReason");

                ExternalEvent event = active.stream()
                        .filter(e -> e.getId().equals(eventId))
                        .findFirst()
                        .orElse(null);

                if (event == null) return null;

                return new RecommendationDTO(eventId, event.getTitle(), aiReason);

            }).filter(Objects::nonNull).toList();

        } catch (Exception e) {
            throw new ApiException("Failed to parse AI recommendation response");
        }
    }


    // Convert DTO list into formatted text
    private String formatAsText(List<RecommendationDTO> list) {

        if (list.isEmpty()) return "No recommendations available at the moment.";

        StringBuilder sb = new StringBuilder("Recommended Events:\n\n");

        int index = 1;
        for (RecommendationDTO dto : list) {
            sb.append(index++).append(") ").append(dto.getTitle())
                    .append("\nReason: ").append(dto.getAiReason())
                    .append("\n\n");
        }

        return sb.toString();
    }

}
