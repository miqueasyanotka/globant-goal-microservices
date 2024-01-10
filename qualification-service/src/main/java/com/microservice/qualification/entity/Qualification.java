package com.microservice.qualification.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "qualifications")
public class Qualification {


    @Id
    private String qualificationId;
    private String userId;
    private String restaurantId;
    private int qualification;
    private String observations;
}
