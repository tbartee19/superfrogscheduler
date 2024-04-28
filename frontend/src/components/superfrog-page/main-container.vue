<template>
    <h1>SuperFrog Page</h1>
    <div class="button-container">
        <button class="button" v-on:click="goToEditStudent">Edit Student Info</button>
        <button class="button" v-on:click="goToChangePassword">Change Password</button>
        <button class="button" v-on:click="goHome">Go Home</button>
    </div>
</template>
<script>
    export default {
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
            goToEditStudent() {
                this.$router.push({
                    name: 'edit-student',
                    query: {
                        studentData: JSON.stringify(this.student)
                    }});
            },
            goToChangePassword() {
                this.$router.push({
                    name: 'change-password',
                    query: {
                        studentData: JSON.stringify(this.student)
                    }});
            },
            goHome() {
                this.$router.push('/')
            }
        },
        created() {
        if (this.$route.query.studentData) {
            this.student = JSON.parse(this.$route.query.studentData);
        }
    },
    }
</script>

<style>
    .button-container {
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: center;
        margin-top: 20px;
        /* width: 80%; */
    }

    .button {
        margin: 10px;
        padding: 20px 40px;
        font-size: 20px;
        border: none;
        border-radius: 5px;
        background-color: #4d2279;
        color: white;
        cursor: pointer;
    }

    .button i {
        font-size: 50px;
        margin-bottom: 10px;
    }
</style>