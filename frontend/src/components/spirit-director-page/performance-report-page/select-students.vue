<template>
    <div>
        <div class="button-container">
            <router-link to="/spirit-director/performance-reports">Back</router-link>
        </div>
        <template v-for="request in requests" :key="request.requestId">
            <!-- If there is anything I need for each invidual request -->
        </template>
        <div class="container">
            <div class="date-1">
                <table class="date-table">
                    <tr>
                        <td>Student Name:</td>
                        <td>Johnny Bravo</td>
                    </tr>
                    <tr>
                        <td>Number of Completed Appearances:</td>
                        <td>{{ totalRequests }}</td>
                    </tr>
                    <tr>
                        <td>Number of Canceled Appearances:</td>
                        <td>0</td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="button-container">
            <router-link to="/spirit-director">Return to Spirit Director Home</router-link>
        </div>
    </div>
</template>

<script>
import VueDatePicker from '@vuepic/vue-datepicker';
import axios from 'axios';

export default {
    name: 'PaymentFormPage',
    components: {
        VueDatePicker,
    },
    data() {
        return {
            requests: [],
            totalRequests: 0
        }
    },
    created() {
        axios.get(`http://localhost:8080/api/appearances`)
            .then((response) => {
                this.requests = response.data.data;
                this.totalRequests = this.requests.length;
                console.log(this.requests)
            }).catch((error) => {
                console.log(error)
            });
    }
}
</script>

<style scoped>
.container {
  width: 90vw;
  margin: 0 auto;
  padding: 20px;
  font-family: sans-serif;
}

.date-1 {
  font-size: 1.2em;
  font-weight: bold;
  margin-bottom: 10px;
  display: flex;
  justify-content: space-evenly;
}
.date-1 > div {
  flex-basis: 40%;
  position: relative;
  align-self: center;
  text-align: center;
}

.date-1 > table{
  flex-basis: content;
}

.date-table{
  background-color: white;
  color:#4d2279;
  justify-content: center;
  align-items: center;
}

.normal-text{
  width: 21rem;
  height: 1rem;
  padding: 5px;
  border: 1px solid #ccc;
  position: relative;
}

select {
  height: 20px;
}

.button-container {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>