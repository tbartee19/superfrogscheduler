package edu.tcu.cs.superfrogscheduler.model;

import edu.tcu.cs.superfrogscheduler.system.Period;

import javax.persistence.*;
import java.math.BigDecimal;

// The Spirit Director wants to generate TCU Honorarium (Payment for services) Requests
// for SuperFrog Students who have completed at least one appearance during a time period,
// so that the SuperFrog Students can get paid by the TCU Payroll Services. The payment
// requests will then be printed, signed, and sent to the TCU Payroll Services.
@Entity
public class PaymentForm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer paymentFormId;
    private String firstName;
    private String lastName;
    private Integer studentId;

    @Embedded
    private Period paymentPeriod;

    private BigDecimal amount;

    public PaymentForm(){

    }

    public PaymentForm(Integer paymentFormId, String firstName, String lastName, Integer studentId, Period paymentPeriod, BigDecimal amount) {
        this.paymentFormId = paymentFormId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        this.paymentPeriod = paymentPeriod;
        this.amount = amount;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public Period getPaymentPeriod() {
        return paymentPeriod;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
