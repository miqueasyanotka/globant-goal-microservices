package com.microservice.user.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Qualification {

    private String restaurantId;
    private String qualificationId;
    private String userId;
    private int qualification;
    private String observations;
    private Restaurant restaurant;

}

