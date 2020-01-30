package com.demo.persistence;

import com.demo.model.AvgRating;
import com.demo.model.LegoSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@Service
public class ReportService {

    private MongoTemplate mongoTemplate;

    public ReportService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<AvgRating> getAvgRatingReport() {
        ProjectionOperation projectionOperation = project()
                .andExpression("name").as("product")
                .andExpression("{$avg : '$reviews.rating'}").as("avgRating");
        Aggregation avgRatingAggregation = newAggregation(LegoSet.class, projectionOperation);
        return this.mongoTemplate
                .aggregate(avgRatingAggregation, LegoSet.class, AvgRating.class)
                .getMappedResults();
    }

}
