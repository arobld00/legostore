package com.demo.persistence;

import com.demo.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

@Component
public class DbInit implements CommandLineRunner {

    private MongoTemplate mongoTemplate;

    private LegoRepository legoRepository;

    public DbInit(MongoTemplate mongoTemplate, LegoRepository legoRepository) {
        this.mongoTemplate = mongoTemplate;
        this.legoRepository = legoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.legoRepository.deleteAll();
        //this.mongoTemplate.dropCollection(LegoSet.class);

        PaymentOption creditCard = new PaymentOption(PaymentType.CreditCard, 0);
        PaymentOption payPal = new PaymentOption(PaymentType.PayPal, 1);
        this.mongoTemplate.insert(creditCard);
        this.mongoTemplate.insert(payPal);

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
                creditCard);

        LegoSet skyPolice = new LegoSet(
                "Sky Police Air Base",
                "City",
                LegoSetDifficulty.MEDIUM,
                new DeliveryInfo(LocalDate.now().plusDays(3), 50, true),
                Arrays.asList(
                        new ProductReview("Dan", 5),
                        new ProductReview("Andrew", 8)
                ),
                payPal);

        Collection<LegoSet> legos = Arrays.asList(milleniumFalcon, skyPolice);
        //this.mongoTemplate.insertAll(legos);
        this.legoRepository.insert(legos);
    }
}
