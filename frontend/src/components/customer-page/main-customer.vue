<template>
    <div class="customer-page">
        <h1 class="page-title">Customer Page</h1>

        <p class="description">
            Request SuperFrog to come to your events!
        </p>

        <div class="button-container">
            <button class="button" @click="goToCalendar">View Calendar</button>
            <button class="button" @click="goToMakeRequest">Make a Request</button>
            <button class="button" @click="goToModifyRequest">Modify a Request</button>
        </div>
    </div>
</template>

<script>
    import Calendar from "./calendar/calendar.vue";
    import MakeRequest from "./make-request/make-request.vue";
    import ModifyRequest from "./modify-request/modify-request.vue";
  
    export default {
        name: "CustomerPage",
        components: {
            Calendar,
            MakeRequest,
            ModifyRequest,
        },
        methods: {
            goToCalendar() {
                this.$router.push('/customer/calendar')
            },
            goToMakeRequest() {
                this.$router.push('/customer/make-request')
            },
            goToModifyRequest() {
                this.$router.push('/customer/modify-request')
            }
        }
    };
</script>

<style>
    .customer-page {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 55vh;
    }

    .page-title {
        color: #4d2279;
        position: absolute;
        top: 10px;
        left: 20px;
        font-size: 50px;
        font-weight: 2000px;
    }

    .description {
        color: #4d2279;
        position: absolute;
        top: 80px;
        left: 20px;
        margin-top: 30px;
        font-size: 24px;
        line-height: 1.2;
    }

    .button-container {
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: left;
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
</style>