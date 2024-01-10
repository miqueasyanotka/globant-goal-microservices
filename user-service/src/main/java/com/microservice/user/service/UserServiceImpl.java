package com.microservice.user.service;

import com.microservice.user.entities.Qualification;
import com.microservice.user.entities.Restaurant;
import com.microservice.user.entities.User;
import com.microservice.user.external.services.QualificationService;
import com.microservice.user.external.services.RestaurantService;
import com.microservice.user.repository.UserRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private QualificationService qualificationService;

    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow();

        ResponseEntity<List<Qualification>> responseEntity = qualificationService.getQualificationsByUserId(userId);

        // Obtén el cuerpo de la respuesta
        List<Qualification> qualifications = responseEntity.getBody();

        List<Qualification> qualificationsList = qualifications.stream().map(qualification -> {
            System.out.println(qualification.getRestaurantId());

            try {
                // Intenta obtener información del restaurante usando el servicio correspondiente
                Restaurant restaurant = restaurantService.getRestaurant(qualification.getRestaurantId());
                qualification.setRestaurant(restaurant);

            } catch (FeignException.NotFound ex) {
                // Maneja la excepción de recurso no encontrado
                logger.warn("Restaurant not found for ID: {}", qualification.getRestaurantId());
                // Puedes asignar un valor predeterminado o tomar otra acción apropiada
                qualification.setRestaurant(new Restaurant());
            }

            return qualification;
        }).collect(Collectors.toList());

        user.setQualifications(qualificationsList);
        return user;
    }

}
