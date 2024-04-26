<template>
    <div class="button-container">
        <button class="button" v-on:click="goToCalendar">Manage Calendar</button>
        <!-- <button class="button" v-on:click="getrequests">Manage Calendar</button> -->
        <button class="button" v-on:click="goToPaymentForms">Generate Payment Forms</button>
        <button class="button" v-on:click="goToPerformanceReports">Generate Student Performance Report</button>
    </div>
</template>

<script>
    import axios from 'axios'

    export default {
        methods: {
            goToCalendar() {
                this.$router.push('/spirit-director/calendar')

                // axios.get(`http://localhost:8080/api/appearances`)
                // .then((response) => {
                //     this.requests = response.data.data;
                //     console.log(this.requests)
                // }).catch((error) => {
                //     console.log(error.response.status)
                //     if (error.response.status === 404) {
                //         this.message = "Sorry, but that request ID could not be found. Please try again."
                //     }
                // })
            },
            goToPaymentForms() {
                this.$router.push('/spirit-director/payment-forms')
            },
            goToPerformanceReports() {
                this.$router.push('/spirit-director/performance-reports')
            }
            // sendData(data){
            //     this.requests = data;
            // }
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