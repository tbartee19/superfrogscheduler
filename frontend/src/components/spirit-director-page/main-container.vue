<template>
    <div class="button-container">
        <button class="button" v-on:click="goToCreateStudent">Create Student</button>
        <button class="button" v-on:click="goToFindAndEditStudent">Find and Edit Students</button>
        <button class="button" v-on:click="goToCalendar">Manage Calendar</button>
        <button class="button" v-on:click="goToPaymentForms">Generate Payment Forms</button>
        <button class="button" v-on:click="goToPerformanceReports">Generate Student Performance Report</button>
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
            goToFindAndEditStudent() {
                this.$router.push('/spirit-director/edit-student')
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