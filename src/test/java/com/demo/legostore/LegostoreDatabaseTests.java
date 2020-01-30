package com.demo.legostore;

import com.demo.model.*;
import com.demo.persistence.LegoRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
class LegostoreDatabaseTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private LegoRepository legoRepository;

    @Before
    public void before() {
        this.legoRepository.deleteAll();

        PaymentOption creditCard = new PaymentOption(PaymentType.CreditCard, 0);
        PaymentOption payPal = new PaymentOption(PaymentType.PayPal, 1);

        LegoSet milleniumFalcon = new LegoSet(
                "Millenium Falcon",
                "Star Wars",
                LegoSetDifficulty.HARD,
                new DeliveryInfo(LocalDate.now().plusDays(1), 30, true),
                Arrays.asList(
                        new ProductReview("Dan", 7),
                        new ProductReview("Anna", 10),
                        new ProductReview("John", 8)
                ),
                creditCard
        );

        LegoSet skyPolice = new LegoSet(
                "Sky Police Air Base",
                "City",
                LegoSetDifficulty.MEDIUM,
                new DeliveryInfo(LocalDate.now().plusDays(3), 50, true),
                Arrays.asList(
                        new ProductReview("Dan", 5),
                        new ProductReview("Andrew", 8)
                ),
                payPal
        );

        this.legoRepository.insert(milleniumFalcon);
        this.legoRepository.insert(skyPolice);
    }

    @Test
    public void findAllByGreatReviewsTest() {
        List<LegoSet> results = (List<LegoSet>) this.legoRepository.findAllByGreatReviews();
        assertEquals(1, results.size());
        assertEquals("Millenium Falcon", results.get(0).getName());
    }

}
