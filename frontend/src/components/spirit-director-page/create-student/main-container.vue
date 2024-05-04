<template>
    <h1>Create Student Page</h1>
    <div class="create-student">
        <h2 color="#4d2279">Enter new Superfrog Student Details</h2>
        <form @submit.prevent="submitForm">
            <div class="form-group">
                <label for="firstname">First Name:</label>
                <input type="text" id="firstname" name="firstname" v-model="student.firstName" placeholder="John"/>
            </div>
            <div class="form-group">
                <label for="lastname">Last Name:</label>
                <input type="test" id="lastname" name="lastname" v-model="student.lastName" placeholder="Smith"/>
            </div>
            <div class="form-group">
                <label for="email">Phone Number:</label>
                <input type="tel" id="phone" name="phone" v-model="student.phoneNumber" pattern="\(\d{3}\) \d{3}-\d{4}" placeholder="Enter phone number" title="Phone number must be in the format (999) 999-9999">
            </div>
            <div class="form-group">
                <label for="address">Physical Address:</label>
                <input type="text" id="address" name="address" v-model="student.physicalAddress" placeholder="Enter physical address" title="Physical address is required" />
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" v-model="student.email" required pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$" title="Email must be valid" placeholder="Enter email address"/>
            </div>
            <div class="form-group">
                <label for="internationalStudent">International Student:</label>
                <input type="checkbox" id="internationalStudent" name="internationalStudent" v-model="student.internationalStudent"/>
            </div>
            <div class="form-group">
                <label for="paymentPreference">Payment Preference:</label>
                <select id="paymentPreference" name="paymentPreference" v-model="student.paymentPreference">
                    <option value="">Select Payment Method</option>
                    <option value="Mail Check">Mail Check</option>
                    <option value="Pick Up Check">Pick-Up Check</option>
                </select>
            </div>
            <div class="form-group">
                <button type="submit" class="submit-button">Create Student</button>
            </div>
            
        </form>
        <div>
            <div class="form-group" >
                <button type="home" @click="goHome" class="home-button">Go Home</button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'CreateStudent',
    data() {
    return {
        student: {
            firstName: '',
            lastName: '',
            phoneNumber: '',
            physicalAddress: '',
            email: '',
            internationalStudent: false,
            paymentPreference: '',
            account: { 
                email: '',
                role: 'STUDENT'  
            }
        }
    };
},
    methods: {
        submitForm() {
            console.log(this.student.firstName);
            console.log(this.student.lastName);
            console.log(this.student.email);
            console.log(this.student.physicalAddress);
            console.log(this.student.phoneNumber);
            console.log(this.student.internationalStudent);
            console.log(this.student.paymentPreference);
            this.student.account.email = this.student.email
            axios.post('http://localhost:8080/api/admin/createStudent', this.student, {
            headers: {
                    'Content-Type': 'application/json'
                }})
                .then(response => {
                    alert('Student created successfully!');
                    console.log(response.data);
                    
                    this.resetForm();
                })
                .catch(error => {
                    console.error('Error creating student:', error);
                    alert('Failed to create student. Please check the details and try again.');
                });
        },
        resetForm() {
            this.student = {
                firstName: '',
                lastName: '',
                phoneNumber: '',
                physicalAddress: '',
                email: '',
                internationalStudent: false,
                paymentPreference: ''
            };
        },
        goHome() {
            this.$router.push("/spirit-director");
        },
    },
    mounted() {
        console.log("CreateStudent component has been mounted!");
    }
};
</script>

