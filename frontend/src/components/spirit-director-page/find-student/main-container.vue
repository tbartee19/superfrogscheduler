<template>
    <h1>Find Student Page</h1>
    <div class="find-student">
        <h2 color="#4d2279">Enter Superfrog Student Search Details</h2>
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
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" v-model="student.email" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$" title="Email must be valid" placeholder="Enter email address"/>
            </div>
            <div class="form-group">
                <button type="submit" class="submit-button">Find Student</button>
            </div>
            
        </form>
        <div>
            <div class="form-group" >
                <label for="studentId">Found ID:</label>
                <input type="text" id="studentId" name="studentId" v-model="student.id"/>
            </div>
            <div class="form-group" >
                <button @click.prevent="resetForm" type="clear" class="clear-button">Reset Form</button>
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
            email: '',
            id: '',
        }
    };
},
    methods: {
        submitForm() {
            console.log("Search parameters:", this.student);
            axios.get('http://localhost:8080/api/admin/searchStudents', {
                params: {
                    firstName: this.student.firstName,
                    lastName: this.student.lastName,
                    phoneNumber: this.student.phoneNumber,
                    email: this.student.email
                },
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(response => {
                alert('Student found successfully!');
                console.log("Found students:", response.data);
                this.$router.push({
                    name: '/spirit-director/edit-student',
                    query: {
                        studentData: JSON.stringify(response.data[0])
                    }});
                
            })
            .catch(error => {
                console.error('Error finding student:', error);
                alert('Failed to find student. Please check the details and try again.');
            });
        },
        resetForm() {
            console.log("reset form clicked")
            this.student = {
                firstName: '',
                lastName: '',
                phoneNumber: '',
                email: '',
                id: '',
            };
        }
    },
    mounted() {
        console.log("FindStudent component has been mounted!");
    }
};
</script>

