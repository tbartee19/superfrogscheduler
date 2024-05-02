package edu.tcu.cs.superfrogscheduler.system;

import edu.tcu.cs.superfrogscheduler.model.PaymentForm;
import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.model.SuperFrogStudent;
import edu.tcu.cs.superfrogscheduler.repository.PaymentFormRepository;
import edu.tcu.cs.superfrogscheduler.repository.SuperFrogAppearanceRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private SuperFrogAppearanceRequestRepository superFrogAppearanceRequestRepository;

    private PaymentFormRepository paymentFormRepository;

    public PaymentService(SuperFrogAppearanceRequestRepository superFrogAppearanceRequestRepository, PaymentFormRepository paymentFormRepository) {
        this.superFrogAppearanceRequestRepository = superFrogAppearanceRequestRepository;
        this.paymentFormRepository = paymentFormRepository;
    }

//    private Map<SuperFrogStudent, List<SuperFrogAppearanceRequest>> groupRequestsBySuperFrogStudent(List<SuperFrogAppearanceRequest> selectedRequests){
//        Map<SuperFrogStudent, List<SuperFrogAppearanceRequest>> studentRequestsMap = selectedRequests.stream()
//                .collect(Collectors.groupingBy(SuperFrogAppearanceRequest::getStudent));
//        return studentRequestsMap;
//    }

//    public List<PaymentForm> generatePaymentForms(List<Integer> appearanceRequestIdList, Period paymentPeriod){
//        List<SuperFrogAppearanceRequest> selectedRequests = this.superFrogAppearanceRequestRepository.findByRequestIdIn(appearanceRequestIdList);
//
//        // Map<SuperFrogStudent, List<SuperFrogAppearanceRequest>> studentRequestsMap = groupRequestBySuperFrogStudent(selectedRequests);
//        return null;
//    }

}