package com.microservice.qualification.controller;

import com.microservice.qualification.entity.Qualification;
import com.microservice.qualification.service.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/qualifications")
public class QualificationController {

    @Autowired
    private QualificationService qualificationService;

    @PostMapping
    public ResponseEntity<Qualification> saveQualification(@RequestBody Qualification qualification) {
        return ResponseEntity.status(HttpStatus.CREATED).body(qualificationService.createQualification(qualification));
    }

    @GetMapping
    public ResponseEntity<List<Qualification>> getQualifications() {
        return ResponseEntity.ok(qualificationService.getQualifications());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Qualification>> getQualificationsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(qualificationService.getQualificationsByUserId(userId));
    }

    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<List<Qualification>> getQualificationsByRestaurantId(@PathVariable String restaurantId) {
        return ResponseEntity.ok(qualificationService.getQualificationsByRestaurantId(restaurantId));
    }

    @PutMapping("/{qualificationId}")
    public ResponseEntity<Qualification> updateQualification(@PathVariable String qualificationId, @RequestBody Qualification qualification) {
        return ResponseEntity.status(HttpStatus.OK).body(qualificationService.updateQualification(qualificationId, qualification));
    }

    @DeleteMapping("/{qualificationId}")
    public void deleteQualification(@PathVariable String qualificationId){
        qualificationService.deleteQualification(qualificationId);
    }
}