package edu.tcu.cs.superfrogscheduler.repository;

import edu.tcu.cs.superfrogscheduler.model.PaymentForm;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface PaymentFormRepository extends MongoRepository<PaymentForm, Integer> {

}
