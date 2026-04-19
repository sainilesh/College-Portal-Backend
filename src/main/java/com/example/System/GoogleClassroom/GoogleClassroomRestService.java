package com.example.System.GoogleClassroom;

import com.example.System.Authentication.OAuth.GoogleAccountService;
import com.example.System.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleClassroomRestService {

    private final GoogleAccountService tokenService;
    private final RestTemplate restTemplate = new RestTemplate();
    private final UserRepository userRepository;

    public String createClassroom(Long userId, String subject, String section) {

        try{
            String accessToken = tokenService.getValidAccessToken(userId);

            String url = "https://classroom.googleapis.com/v1/courses";

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            String email = userRepository.findById(userId).get().getEmail();

            Map<String, Object> body = new HashMap<>();
            body.put("name", subject + " - " + section);
            body.put("section", section);
            body.put("courseState", "PROVISIONED");
            body.put("ownerId", email);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Map> response =
                    restTemplate.postForEntity(url, request, Map.class);

            String courseId = (String) response.getBody().get("id");

            return (String) response.getBody().get("id");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Google API failed: " + e.getMessage());
        }
    }

}