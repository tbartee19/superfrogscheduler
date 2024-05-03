<template>
    <div class="button-container">
        <button class="button" v-on:click="goToCreateStudent">Create Student</button>
        <button class="button" v-on:click="goToFindStudent">Find Students</button>
        <button class="button" v-on:click="goToCalendar">Manage Spirit Director Events</button>
        <button class="button" v-on:click="goToAppearanceRequests">Manage Appearance Requests</button>
        <button class="button" v-on:click="goToPaymentForms">Generate Payment Forms</button>
        <button class="button" v-on:click="goToPerformanceReports">Generate Student Performance Report</button>
        <button class="button" v-on:click="goHome">Go Home</button>
    </div>
</template>

<script>
    export default {
        methods: {
            goToCreateStudent() {
                this.$router.push('/spirit-director/create-student')
            },
            goToCalendar() {
                this.$router.push('/spirit-director/calendar')
            },
            goToPaymentForms() {
                this.$router.push('/spirit-director/payment-forms')
            },
            goToPerformanceReports() {
                this.$router.push('/spirit-director/performance-reports')
            },
            goToFindStudent() {
                this.$router.push('/spirit-director/find-student')
            },
            goToAppearanceRequests(){
                this.$router.push('/spirit-director/appearance-requests')
            },
            goHome() {
                this.$router.push('/')
            }
        }
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