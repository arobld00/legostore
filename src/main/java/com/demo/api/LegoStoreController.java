package com.demo.api;

import com.demo.model.LegoSet;
import com.demo.model.LegoSetDifficulty;
import com.demo.model.QLegoSet;
import com.demo.persistence.LegoRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.web.bind.annotation.*;

import javax.xml.soap.Text;
import java.util.Collection;

@RestController
@RequestMapping("legostore/api")
@CrossOrigin
public class LegoStoreController {

    //private MongoTemplate mongoTemplate;

    private LegoRepository legoRepository;

    public LegoStoreController(/*MongoTemplate mongoTemplate,*/ LegoRepository legoRepository) {
        // this.mongoTemplate = mongoTemplate;
        this.legoRepository = legoRepository;
    }

    @PostMapping
    public void insert(@RequestBody LegoSet legoSet) {
        // this.mongoTemplate.insert(legoSet);
        this.legoRepository.insert(legoSet);
    }

    @GetMapping("/all")
    public Collection<LegoSet> all() {
        Sort sortByThemeAsc = Sort.by("theme").ascending();
        // return (Collection<LegoSet>) this.mongoTemplate.findAll(LegoSet.class);
        return (Collection<LegoSet>) this.legoRepository.findAll(sortByThemeAsc);
    }

    @PutMapping
    public void update(@RequestBody LegoSet legoSet) {
        // this.mongoTemplate.save(legoSet);
        this.legoRepository.save(legoSet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        // this.mongoTemplate.remove(new Query(Criteria.where("id").is(id)), LegoSet.class);
        this.legoRepository.deleteById(id);
    }

    @GetMapping("/{id}")
    public LegoSet byId(@PathVariable String id) {
        return (LegoSet) this.legoRepository.findById(id).orElse(null);
    }

    @GetMapping("/byTheme/{theme}")
    public Collection<LegoSet> byTheme(String theme) {
        Sort sortByThemeAsc = Sort.by("theme").ascending();
        return this.legoRepository.findAllByThemeContains(theme, sortByThemeAsc);
    }

    @GetMapping("hardThatStarWithM")
    public Collection<LegoSet> hardThatStartWithM() {
        return this.legoRepository.findAllByDifficultyAndNameStartsWith(LegoSetDifficulty.HARD, "M");
    }

    @GetMapping("/byDeliveryFee/{price}")
    public Collection<LegoSet> byDeliveryFeeLeesThan(@PathVariable int price) {
        return this.legoRepository.findAllByDeliveryPriceLessThan(price);
    }

    @GetMapping("greatReviews")
    public Collection<LegoSet> byGreatReviews() {
        return this.legoRepository.findAllByGreatReviews();
    }

    /*
        Query DSL
     */
    @GetMapping("bestBuys")
    public Collection<LegoSet> bestBuys() {
        QLegoSet query = new QLegoSet("query");
        Predicate inStockFilter = query.deliveryInfo.inStock.isTrue();
        Predicate smallDeliveryFeeFilter = query.deliveryInfo.deliveryFee.lt(50);
        Predicate hasGreatReviews = query.reviews.any().rating.eq(10);
        Predicate bestBuysFilter = ((BooleanExpression) inStockFilter)
                .and(smallDeliveryFeeFilter)
                .and(hasGreatReviews);
        return (Collection<LegoSet>) this.legoRepository.findAll(bestBuysFilter);
    }

    /*
        Full Text Search
     */
    @GetMapping("fullTextSearch/{text}")
    public Collection<LegoSet> fullTextSearch(@PathVariable String text) {
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(text);
        return this.legoRepository.findAllBy(textCriteria);
    }

    @GetMapping("/byPayment/{id}")
    public Collection<LegoSet> byPayment(@PathVariable String id) {
        return this.legoRepository.findByPayment(id);
    }

}
