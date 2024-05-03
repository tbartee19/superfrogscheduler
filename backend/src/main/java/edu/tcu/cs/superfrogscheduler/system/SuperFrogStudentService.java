package edu.tcu.cs.superfrogscheduler.system;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearanceRequest;
import edu.tcu.cs.superfrogscheduler.model.SuperFrogStudent;
import edu.tcu.cs.superfrogscheduler.model.Account;
import edu.tcu.cs.superfrogscheduler.model.dto.ProfileUpdateDTO;
import edu.tcu.cs.superfrogscheduler.repository.SuperFrogAppearanceRequestRepository;
import edu.tcu.cs.superfrogscheduler.repository.SuperFrogStudentRepository;
import edu.tcu.cs.superfrogscheduler.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.security.SecureRandom;
import java.util.*;

import javax.validation.ValidationException;



@Service
public class SuperFrogStudentService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SuperFrogStudentRepository studentRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SuperFrogAppearanceRequestRepository superFrogAppearanceRequestRepository;



    public SuperFrogStudent createSuperFrogStudent(SuperFrogStudent newStudent) {
        if (accountRepository.existsByEmail(newStudent.getAccount().getEmail())) {
            throw new IllegalArgumentException("Email already in use.");
        }

        // set role for new account to student so it can be differentiated from spirit directors later
        newStudent.getAccount().setRole("STUDENT");

        String tempPassword = generateTemporaryPassword();
        newStudent.getAccount().setPasswordHash(passwordEncoder.encode(tempPassword));

        // save student to student repository and their account credentials to account
        Account savedAccount = accountRepository.save(newStudent.getAccount());
        newStudent.setAccount(savedAccount);
        SuperFrogStudent savedStudent = studentRepository.save(newStudent);

        // temporary logging temp password here for future implementation
        System.out.println("Temporary Password for " + savedStudent.getFirstName() + ": " + tempPassword);

        return savedStudent;
    }


    public void deactivateSuperFrogStudent(String studentId, String reason) {
        Optional<SuperFrogStudent> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) {
            throw new IllegalArgumentException("Student not found with ID: " + studentId);
        }

        SuperFrogStudent student = studentOpt.get();
        if (!student.getAccount().isActive()) {
            throw new IllegalStateException("Account is already deactivated.");
        }

        // check for incomplete assigned appearances
        // example
        // if (appearanceService.hasIncompleteAppearances(studentId)) {
        //     throw new IllegalStateException("Student has active assigned appearances. Deactivation cannot be done at this moment.");
        // }

        student.getAccount().setActive(false);
        accountRepository.save(student.getAccount());

        // Log or notify
        System.out.println("Account deactivated for student: " + student.getFirstName() + " " + student.getLastName() + ". Reason: " + reason);

    }
    @GetMapping
    public List<SuperFrogStudent> findSuperFrogStudents(Optional<String> firstName, Optional<String> lastName, Optional<String> phoneNumber, Optional<String> email) {
        System.out.println("Service - First Name: " + firstName.orElse("Not provided"));


        if (isPresentAndNotEmpty(firstName) && isPresentAndNotEmpty(lastName) && isPresentAndNotEmpty(phoneNumber) && isPresentAndNotEmpty(email)) {
            return studentRepository.findByFirstNameAndLastNameAndPhoneNumberAndEmail(firstName.get(), lastName.get(), phoneNumber.get(), email.get());
        } else if (isPresentAndNotEmpty(firstName) && isPresentAndNotEmpty(lastName) && isPresentAndNotEmpty(phoneNumber)) {
            return studentRepository.findByFirstNameAndLastNameAndPhoneNumber(firstName.get(), lastName.get(), phoneNumber.get());
        } else if (isPresentAndNotEmpty(firstName) && isPresentAndNotEmpty(lastName)) {
            return studentRepository.findByFirstNameAndLastName(firstName.get(), lastName.get());
        } else if (isPresentAndNotEmpty(firstName) && isPresentAndNotEmpty(phoneNumber)) {
            return studentRepository.findByFirstNameAndPhoneNumber(firstName.get(), phoneNumber.get());
        } else if (isPresentAndNotEmpty(firstName) && isPresentAndNotEmpty(email)) {
            return studentRepository.findByFirstNameAndEmail(firstName.get(), email.get());
        } else if (isPresentAndNotEmpty(lastName) && isPresentAndNotEmpty(phoneNumber)) {
            return studentRepository.findByLastNameAndPhoneNumber(lastName.get(), phoneNumber.get());
        } else if (isPresentAndNotEmpty(lastName) && isPresentAndNotEmpty(email)) {
            return studentRepository.findByLastNameAndEmail(lastName.get(), email.get());
        } else if (isPresentAndNotEmpty(phoneNumber) && isPresentAndNotEmpty(email)) {
            return studentRepository.findByPhoneNumberAndEmail(phoneNumber.get(), email.get());
        } else if (isPresentAndNotEmpty(firstName)) {
            return studentRepository.findByFirstName(firstName.get());
        } else if (isPresentAndNotEmpty(lastName)) {
            return studentRepository.findByLastName(lastName.get());
        } else if (isPresentAndNotEmpty(phoneNumber)) {
            return studentRepository.findByPhoneNumber(phoneNumber.get());
        } else if (isPresentAndNotEmpty(email)) {
            return studentRepository.findByEmail(email.get()).map(Collections::singletonList).orElseGet(Collections::emptyList);
        }
        return Collections.emptyList();
    }


    public Optional<SuperFrogStudent> findStudentById(String studentId) {
        return studentRepository.findById(studentId);
    }

    public SuperFrogStudent updateStudentProfile(String email, ProfileUpdateDTO profileUpdate) throws Exception {
        Optional<SuperFrogStudent> studentOpt = studentRepository.findByEmail(email);
        if (!studentOpt.isPresent()) {
            throw new IllegalArgumentException("Student not found.");
        }
        SuperFrogStudent student = studentOpt.get();
        System.out.println(profileUpdate.getFirstName());
        Account studentAccount = student.getAccount();
        student.setFirstName(profileUpdate.getFirstName());
        student.setLastName(profileUpdate.getLastName());
        student.setPhoneNumber(profileUpdate.getPhoneNumber());
        student.setPhysicalAddress(profileUpdate.getPhysicalAddress());
        student.setEmail(profileUpdate.getEmail());
        student.setInternationalStudent(profileUpdate.getInternationalStudent());
        student.setPaymentPreference(profileUpdate.getPaymentPreference());
        studentAccount.setActive(profileUpdate.getIsActive());
        accountRepository.save(studentAccount);
        validateStudentProfile(student);
        studentRepository.save(student);
        notifyProfileUpdate(student); // Notify the Spirit Director
        return student;
    }

    public void validateStudentProfile(SuperFrogStudent student) throws ValidationException {
        List<String> errors = new ArrayList<>();

        // Validate first name
        if (student.getFirstName() == null || student.getFirstName().trim().isEmpty()) {
            errors.add("First name is required.");
        }

        // Validate last name
        if (student.getLastName() == null || student.getLastName().trim().isEmpty()) {
            errors.add("Last name is required.");
        }

        // Validate phone number format
        if (!student.getPhoneNumber().matches("\\(\\d{3}\\) \\d{3}-\\d{4}")) {
            errors.add("Phone number must be in the format (999) 999-9999.");
        }

        // Validate email format
        if (!student.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            errors.add("Email must be valid.");
        }

        // Validate physical address exists
        if (student.getPhysicalAddress() == null || student.getPhysicalAddress().trim().isEmpty()) {
            errors.add("Physical address is required.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(String.join("\n", errors));
        }
    }


    public void notifyProfileUpdate(SuperFrogStudent student) {
        // just outputting to console
        System.out.println("Notification: Profile updated for student: " + student.getFirstName() + " " + student.getLastName() +
                " (ID: " + student.getId() + "). Email: " + student.getEmail());
    }

    public void changePassword(String studentId, String newPassword) throws Exception {
        SuperFrogStudent student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
        System.out.println("Changing password for student with id:" + studentId);


        String encodedPassword = passwordEncoder.encode(newPassword);
        student.getAccount().setPasswordHash(encodedPassword);
        accountRepository.save(student.getAccount());
        studentRepository.save(student);
    }


    private String generateTemporaryPassword() {
        // generate a secure random password
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[12];
        random.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
    private boolean isPresentAndNotEmpty(Optional<String> param) {
        return param.isPresent() && !param.get().trim().isEmpty();
    }

}