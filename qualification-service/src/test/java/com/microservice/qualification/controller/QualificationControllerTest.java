package com.microservice.qualification.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.qualification.entity.Qualification;
import com.microservice.qualification.service.QualificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(QualificationController.class)
@ExtendWith(MockitoExtension.class)
class QualificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QualificationService qualificationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private MongoTemplate mongoTemplate;


    @Test
    void saveQualificationTest() throws Exception {
        Qualification qualification = new Qualification();
        qualification.setQualificationId(UUID.randomUUID().toString());
        qualification.setUserId("user123");
        qualification.setRestaurantId("restaurant123");
        qualification.setQualification(5);
        qualification.setObservations("Good experience");

        Mockito.when(qualificationService.createQualification(any())).thenReturn(qualification);

        mockMvc.perform(post("/qualifications")
                                .content(objectMapper.writeValueAsString(qualification))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.qualificationId").value(qualification.getQualificationId()))
                .andExpect(jsonPath("$.userId").value(qualification.getUserId()))
                .andExpect(jsonPath("$.restaurantId").value(qualification.getRestaurantId()))
                .andExpect(jsonPath("$.qualification").value(qualification.getQualification()))
                .andExpect(jsonPath("$.observations").value(qualification.getObservations()));

        verify(qualificationService, times(1)).createQualification(any());
    }

    @Test
    void getQualificationsTest() throws Exception {
        List<Qualification> qualificationList = Arrays.asList(
                new Qualification("1", "user123", "restaurant123", 5, "Good experience"),
                new Qualification("2", "user456", "restaurant456", 4, "Nice place")
        );

        Mockito.when(qualificationService.getQualifications()).thenReturn(qualificationList);

        mockMvc.perform(get("/qualifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(qualificationList.size())))
                .andExpect(jsonPath("$[0].qualificationId").isNotEmpty())  // Ajusta según tu modelo
                .andExpect(jsonPath("$[0].userId").value("user123"))
                .andExpect(jsonPath("$[0].restaurantId").value("restaurant123"))
                .andExpect(jsonPath("$[0].qualification").value(5))
                .andExpect(jsonPath("$[0].observations").value("Good experience"))
                .andExpect(jsonPath("$[1].userId").value("user456"))
                .andExpect(jsonPath("$[1].restaurantId").value("restaurant456"))
                .andExpect(jsonPath("$[1].qualification").value(4))
                .andExpect(jsonPath("$[1].observations").value("Nice place"));

        verify(qualificationService, times(1)).getQualifications();
    }


    @Test
    void getQualificationsByUserIdTest() throws Exception {
        String userId = "someUserId";
        List<Qualification> qualificationList = Arrays.asList(
                new Qualification("1", userId, "restaurant1", 4, "Good"),
                new Qualification("2", userId, "restaurant2", 5, "Excellent")
        );

        Mockito.when(qualificationService.getQualificationsByUserId(userId)).thenReturn(qualificationList);

        mockMvc.perform(get("/qualifications/users/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(qualificationList.size())))
                .andExpect(jsonPath("$[0].userId").value(userId))
                .andExpect(jsonPath("$[0].restaurantId").value("restaurant1"))
                .andExpect(jsonPath("$[0].qualification").value(4))
                .andExpect(jsonPath("$[0].observations").value("Good"))
                .andExpect(jsonPath("$[1].userId").value(userId))
                .andExpect(jsonPath("$[1].restaurantId").value("restaurant2"))
                .andExpect(jsonPath("$[1].qualification").value(5))
                .andExpect(jsonPath("$[1].observations").value("Excellent"));

        verify(qualificationService, times(1)).getQualificationsByUserId(userId);
    }


    @Test
    void getQualificationsByRestaurantIdTest() throws Exception {
        String restaurantId = "someRestaurantId";
        List<Qualification> qualificationList = Arrays.asList(new Qualification(), new Qualification());

        Mockito.when(qualificationService.getQualificationsByRestaurantId(eq(restaurantId))).thenReturn(qualificationList);

        mockMvc.perform(get("/qualifications/restaurants/{restaurantId}", restaurantId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(qualificationList.size())));

        verify(qualificationService, times(1)).getQualificationsByRestaurantId(eq(restaurantId));
    }

    @Test
    void updateQualificationTest() throws Exception {
        String qualificationId = "someQualificationId";
        Qualification qualificationToUpdate = new Qualification(qualificationId, "user123", "restaurant123", 5, "Updated experience");

        Mockito.when(qualificationService.updateQualification(eq(qualificationId), any(Qualification.class))).thenReturn(qualificationToUpdate);

        mockMvc.perform(put("/qualifications/{qualificationId}", qualificationId)
                                .content(objectMapper.writeValueAsString(qualificationToUpdate))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.qualificationId").value(qualificationToUpdate.getQualificationId()))
                .andExpect(jsonPath("$.userId").value("user123"))
                .andExpect(jsonPath("$.restaurantId").value("restaurant123"))
                .andExpect(jsonPath("$.qualification").value(5))
                .andExpect(jsonPath("$.observations").value("Updated experience"));

        verify(qualificationService, times(1)).updateQualification(eq(qualificationId), any(Qualification.class));
    }


    @Test
    void deleteQualificationTest() throws Exception {
        String qualificationId = "someQualificationId";

        mockMvc.perform(delete("/qualifications/{qualificationId}", qualificationId))
                .andExpect(status().isOk());

        verify(qualificationService, times(1)).deleteQualification(eq(qualificationId));
    }


    // Método para convertir objetos a JSON
    public static String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
