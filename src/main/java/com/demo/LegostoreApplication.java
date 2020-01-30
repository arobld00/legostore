package com.demo;

import com.demo.api.LegoStoreController;
import com.demo.persistence.DataMigrations;
import com.demo.persistence.DbInit;
import com.demo.persistence.LegoRepository;
import com.github.mongobee.Mongobee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
@ComponentScan(basePackageClasses = LegoStoreController.class)
@ComponentScan(basePackageClasses = DbInit.class)
@ComponentScan(basePackageClasses = DataMigrations.class)
public class LegostoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(LegostoreApplication.class, args);
    }

    @Autowired
    MongoTemplate mongoTemplate;

    @Bean
    public Mongobee mongobee() {
        Mongobee runner = new Mongobee("mongodb://localhost:27017/legostore");
        runner.setMongoTemplate(mongoTemplate);
        runner.setChangeLogsScanPackage("com.demo.persistence");
        return runner;
    }
}
