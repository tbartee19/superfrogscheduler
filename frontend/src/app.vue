<template>
    <router-view v-if="$route.path != '/'"></router-view>

    <div v-if="$route.path == '/'" id="app">
        <div class="landing-page">
            <h1 class="page-title">SuperFrog Scheduler</h1>
    
            <p class="description">
                Please select from the following:
            </p>
            <div class="button-container">
                <button class="button" @click="goToCustomer">I am a Customer</button>
                <button class="button" @click="goToSpiritDirector">I am a Spirit Director</button>
                <button class="button" @click="goToStudent">I am a SuperFrog Student</button>
            </div>
        </div>
    </div>
</template>
  
<script>
    export default {
        name: 'App',
        methods: {
            goToCustomer() {
                this.$router.push('/customer')
            },
            goToSpiritDirector() {
                this.$router.push('/spirit-director')
            },
            goToStudent() {
                this.$router.push('/student')
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
  
    
      <!-- <RouterLink v-if="$route.path != '/'" to="/">Home</RouterLink> -->
      