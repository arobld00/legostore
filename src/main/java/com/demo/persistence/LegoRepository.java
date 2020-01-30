package com.demo.persistence;

import com.demo.model.LegoSet;
import com.demo.model.LegoSetDifficulty;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface LegoRepository extends MongoRepository<LegoSet, String>, QuerydslPredicateExecutor<LegoSet> {

    Collection<LegoSet> findAllByThemeContains(String theme, Sort sort);

    Collection<LegoSet> findAllByDifficultyAndNameStartsWith(LegoSetDifficulty difficulty, String name);

    Collection<LegoSet> findAllBy(TextCriteria textCriteria);

    // ?0 primer parametro
    @Query("{'delivery.deliveryFee' : {$lt : ?0}}")
    Collection<LegoSet> findAllByDeliveryPriceLessThan(int price);

    @Query("{'reviews.rating' : {$eq : 10}}")
    Collection<LegoSet> findAllByGreatReviews();

    @Query("{'paymentOption.id : ?0'}")
    Collection<LegoSet> findByPayment(String id);

}
