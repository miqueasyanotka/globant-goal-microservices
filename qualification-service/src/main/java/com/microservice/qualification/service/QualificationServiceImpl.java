package com.microservice.qualification.service;

import com.microservice.qualification.entity.Qualification;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class QualificationServiceImpl implements QualificationService {

    private final MongoTemplate mongoTemplate;

    public QualificationServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Qualification createQualification(Qualification qualification) {
        String qualificationId = UUID.randomUUID().toString();
        qualification.setQualificationId(qualificationId);
        return mongoTemplate.save(qualification);
    }

    @Override
    public List<Qualification> getQualifications() {
        return mongoTemplate.findAll(Qualification.class);
    }

    @Override
    public List<Qualification> getQualificationsByUserId(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, Qualification.class);
    }

    @Override
    public List<Qualification> getQualificationsByRestaurantId(String restaurantId) {
        Query query = new Query(Criteria.where("restaurantId").is(restaurantId));
        return mongoTemplate.find(query, Qualification.class);
    }

    @Override
    public Qualification updateQualification(String qualificationId, Qualification qualification) {
        Query query = new Query(Criteria.where("qualificationId").is(qualificationId));
        Qualification existingQualification = mongoTemplate.findOne(query, Qualification.class);

        if (existingQualification == null) {
            throw new NoSuchElementException("Qualification doesn't exist");
        }

        existingQualification.setQualification(qualification.getQualification());
        existingQualification.setObservations(qualification.getObservations());

        return mongoTemplate.save(existingQualification);
    }

    @Override
    public void deleteQualification(String qualificationId) {
        Query query = new Query(Criteria.where("qualificationId").is(qualificationId));
        Qualification existingQualification = mongoTemplate.findOne(query, Qualification.class);

        if (existingQualification == null) {
            throw new NoSuchElementException("Qualification doesn't exist");
        }

        mongoTemplate.remove(existingQualification);
    }
}