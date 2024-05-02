<template>
    <h1>Change Password Page</h1>
    <div class="change-password">
        <h2 color="#4d2279">Change Superfrog Student Password</h2>
        <form @submit.prevent="submitForm">
            <div class="form-group">
                <label for="firstname">New Password</label>
                <input type="password" id="password" name="password" v-model="newPassword" placeholder="New Password"/>
            </div>
            
            <div class="form-group">
                <button type="submit" class="submit-button">Update Password</button>
            </div>
        </form>
        <div>
            <div class="form-group" >
                <label for="studentId">Student ID:</label>
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
                    isActive: true, 
                }
            }, 
            newPassword: ''
        };
    },
    methods: {
        submitForm() {
            
            console.log("Edit parameters:", this.student);
            axios.put(`http://localhost:8080/api/students/changePassword/${this.student.id}`, {
                newPassword: this.newPassword

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
            this.$router.push("/");
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
