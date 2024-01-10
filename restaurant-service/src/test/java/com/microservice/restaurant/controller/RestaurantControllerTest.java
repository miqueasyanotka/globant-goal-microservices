package com.microservice.restaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.restaurant.entity.Restaurant;
import com.microservice.restaurant.repository.RestaurantRepository;
import com.microservice.restaurant.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)

class RestaurantControllerTest {

    static final String BASE_URL = "/restaurants";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Autowired
    private static RestaurantRepository restaurantRepository;

    @Autowired
    private ObjectMapper objectMapper;

    Restaurant restaurant =
            Restaurant.builder()
                    .restaurantId(UUID.randomUUID().toString())
                    .name("Roque House")
                    .location("Sáenz Peña")
                    .information("Comidas caseras")
                    .build();

    Restaurant restaurant2 =
            Restaurant.builder()
                    .restaurantId(UUID.randomUUID().toString())
                    .name("Rojo Bar")
                    .location("Sáenz Peña")
                    .information("Hamburgesas caseras")
                    .build();

    @Test
    void saveRestaurant() throws Exception {

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(UUID.randomUUID().toString());
        restaurant.setName("Kuva Bar");
        restaurant.setLocation("Sáenz Peña");
        restaurant.setInformation("Las mejores pizzas");

        Mockito.when(restaurantService.createRestaurant(any())).thenReturn(restaurant);

        mockMvc.perform(post(BASE_URL)
                                .content(objectMapper.writeValueAsString(restaurant))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.restaurantId").value(restaurant.getRestaurantId()))
                .andExpect(jsonPath("$.name").value(restaurant.getName()))
                .andExpect(jsonPath("$.location").value(restaurant.getLocation()))
                .andExpect(jsonPath("$.information").value(restaurant.getInformation()));

        verify(restaurantService, times(1)).createRestaurant(any(Restaurant.class));

    }

    @Test
    void getRestaurantById() throws Exception {

        Mockito.when(restaurantService.getRestaurant(any())).thenReturn(restaurant);

        mockMvc.perform(get(BASE_URL + "/{restaurantId}", restaurant.getRestaurantId())
                                .content(objectMapper.writeValueAsString(restaurant))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.restaurantId").value(restaurant.getRestaurantId()))
                .andExpect(jsonPath("$.name").value(restaurant.getName()))
                .andExpect(jsonPath("$.location").value(restaurant.getLocation()))
                .andExpect(jsonPath("$.information").value(restaurant.getInformation()));

        verify(restaurantService, times(1)).getRestaurant(restaurant.getRestaurantId());
    }

    @Test
    void getRestaurants() throws Exception {

        List<Restaurant> restaurantList = Arrays.asList(restaurant, restaurant2);

        Mockito.when(restaurantService.getAll()).thenReturn(restaurantList);

        mockMvc.perform(get(BASE_URL)
                                .content(objectMapper.writeValueAsString(restaurantList))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].restaurantId").value(restaurant.getRestaurantId()))
                .andExpect(jsonPath("$[0].name").value(restaurant.getName()))
                .andExpect(jsonPath("$[0].location").value(restaurant.getLocation()))
                .andExpect(jsonPath("$[0].information").value(restaurant.getInformation()))
                .andExpect(jsonPath("$[1].restaurantId").value(restaurant2.getRestaurantId()))
                .andExpect(jsonPath("$[1].name").value(restaurant2.getName()))
                .andExpect(jsonPath("$[1].location").value(restaurant2.getLocation()))
                .andExpect(jsonPath("$[1].information").value(restaurant2.getInformation()));


        verify(restaurantService, times(1)).getAll();
    }


    @Test
    void updateRestaurantById() throws Exception {

        Restaurant restaurantUpdate =
                Restaurant.builder()
                        .restaurantId(restaurant.getRestaurantId())
                        .name("Shaday")
                        .location("Sáenz Peña")
                        .information("Resto Bar")
                        .build();

        Mockito.when(restaurantService.updateRestaurant(eq(restaurantUpdate.getRestaurantId()), any(Restaurant.class))).thenReturn(restaurantUpdate);

        mockMvc.perform(put(BASE_URL + "/{restaurantId}", restaurantUpdate.getRestaurantId())
                                .content(objectMapper.writeValueAsString(restaurant))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.restaurantId").value(restaurantUpdate.getRestaurantId()))
                .andExpect(jsonPath("$.name").value(restaurantUpdate.getName()))
                .andExpect(jsonPath("$.location").value(restaurantUpdate.getLocation()))
                .andExpect(jsonPath("$.information").value(restaurantUpdate.getInformation()));

        verify(restaurantService, times(1)).updateRestaurant(eq(restaurantUpdate.getRestaurantId()), any(Restaurant.class));

    }
}