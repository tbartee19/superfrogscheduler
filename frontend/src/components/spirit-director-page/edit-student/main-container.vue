<template>
    <h1>Edit Student Page</h1>
    <div class="find-student">
        <h2 color="#4d2279">Change Superfrog Student Details</h2>
        <form @submit.prevent="submitForm">
            <div class="form-group">
                <label for="firstname">First Name:</label>
                <input type="text" id="firstname" name="firstname" v-model="this.student.firstName" placeholder="John"/>
            </div>
            <div class="form-group">
                <label for="lastname">Last Name:</label>
                <input type="test" id="lastname" name="lastname" v-model="this.student.lastName" placeholder="Smith"/>
            </div>
            <div class="form-group">
                <label for="email">Phone Number:</label>
                <input type="tel" id="phone" name="phone" v-model="this.student.phoneNumber" pattern="\(\d{3}\) \d{3}-\d{4}" placeholder="Enter phone number" title="Phone number must be in the format (999) 999-9999">
            </div>
            <div class="form-group">
                <label for="address">Physical Address:</label>
                <input type="text" id="address" name="address" v-model="this.student.physicalAddress" placeholder="Enter physical address" title="Physical address is required" />
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" v-model="this.student.email" required pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$" title="Email must be valid" placeholder="Enter email address"/>
            </div>
            <div class="form-group">
                <label for="internationalStudent">International Student:</label>
                <input type="checkbox" id="internationalStudent" name="internationalStudent" v-model="this.student.internationalStudent"/>
            </div>
            <div class="form-group">
                <label for="active">Active Student:</label>
                <input type="checkbox" id="active" name="active" v-model="this.student.account.isActive"/>
            </div>
            <div class="form-group">
                <label for="paymentPreference">Payment Preference:</label>
                <select id="paymentPreference" name="paymentPreference" v-model="this.student.paymentPreference">
                    <option value="">Select Payment Method</option>
                    <option value="Mail Check">Mail Check</option>
                    <option value="Pick Up Check">Pick-Up Check</option>
                </select>
            </div>
            <div class="form-group">
                <button type="submit" class="submit-button">Update Student</button>
            </div>
        </form>
        <div>
            <div class="form-group" >
                <label for="studentId">Found ID:</label>
                <input type="text" id="studentId" name="studentId" v-model="this.student.id"/>
            </div>
            <div>
            <div class="form-group" >
                <button type="home" @click="goHome" class="home-button">Go Home</button>
            </div>
        </div>
        </div>
        
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'EditStudent',
    data() {
        return {
            student: {
                id: '',
                firstName: '',
                lastName: '',
                phoneNumber: '',
                email: '',
                physicalAddress: '',
                internationalStudent: false,
                paymentPreference: '',
                account: {
                    email: '',
                    passwordHash: '',
                    role: '',
                    isActive: null, 
                }
            }
        };
    },
    methods: {
        submitForm() {
            console.log("Edit parameters:", this.student);
            axios.put('http://localhost:8080/api/students/updateProfile', {
                firstName: this.student.firstName,
                lastName: this.student.lastName,
                phoneNumber: this.student.phoneNumber,
                email: this.student.email,
                physicalAddress: this.student.physicalAddress,
                internationalStudent: this.student.internationalStudent,
                paymentPreference: this.student.paymentPreference,
                isActive: this.student.account.isActive 
            }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(response => {
                alert('Student found successfully!');
                console.log("Found students:", response.data);
                
                
            })
            .catch(error => {
                console.error('Error finding student:', error);
                alert('Failed to find student. Please check the details and try again.');
            });
        },
        goHome() {
            this.$router.push("/spirit-director");
        },
    },
    created() {
        if (this.$route.query.studentData) {
            this.student = JSON.parse(this.$route.query.studentData);
        }
    },
    mounted() {
        console.log("FindStudent component has been mounted!");
    }
};
</script>

