<template>
    <router-view v-if="$route.path != '/'"></router-view>

    <div v-if="$route.path == '/'" id="app">
        <title>Landing Page</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.2/css/bootstrap-combined.no-icons.min.css"
            rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">

        <div class="landing-page">
            <h1 class="heading">SuperFrog Scheduler</h1>

            <p class="description">
                Request SuperFrog to come to your events!
            </p>
            <div class="button-container">
                <button class="button" v-on:click="goToRequest">Make a Request</button>
                <button class="button" v-on:click="goToModifyRequest">Modify a Request</button>
            </div>
        </div>
    </div>
</template>
  
<script>
    export default {
        name: 'App',
        methods: {
            goToRequest() {
                this.$router.push('/request')
            },
            goToModifyRequest() {
                this.$router.push('/modify-request')
            }
        }
    }
</script>
  
<style>
    .landing-page {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 55vh;
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

    .button i {
        font-size: 50px;
        margin-bottom: 10px;
    }

    .heading {
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
</style>