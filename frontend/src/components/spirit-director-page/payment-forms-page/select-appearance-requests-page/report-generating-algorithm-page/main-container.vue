<template>
  <div class="container">
    <p class="message">Fill out the following fields, then print out the payment form:</p>
    <div class="date-1">
      <table class="date-table">
        <tr>
          <td>Student Name</td>
          <td>John Doe</td>
        </tr>
        <tr>
          <td>Enter SSN For Student: </td>
          <td><input type="text" class="normal-text"></td>
        </tr>
        <tr>
          <td>Student Address</td>
          <td>123 Main St, Fort Worth, TX 76109</td>
        </tr>
        <tr>
          <td>Amount Owed</td>
          <td>${{getTotalPrice(requests.startTime, requests.endTime, requests.eventType)}}</td>
        </tr>
        <tr>
          <td>Enter Account Code:</td>
          <td><input type="text" class="normal-text"></td>
        </tr>
        <tr>
          <td>Enter Fund Code:</td>
          <td><input type="text" class="normal-text"></td>
        </tr>
        <tr>
          <td>Enter Dept Code:</td>
          <td><input type="text" class="normal-text"></td>
        </tr>
        <tr>
          <td>Enter Project Code:</td>
          <td><input type="text" class="normal-text"></td>
        </tr>
        <tr>
          <td>Enter Name:</td>
          <td><input type="text" class="normal-text"></td>
        </tr>
        <tr>
          <td>Provide Signature:</td>
          <td><input type="text" class="normal-text"></td>
        </tr>
        <tr>
          <td>Date of Service</td>
          <td>{{ requests.eventDate }}</td>
        </tr>
        <tr>
          <td>Nature of Services</td>
          <td>{{ requests.description }}</td>
        </tr>
      </table>
    </div>
  </div>

  <div class="button-container">
    <router-link to='/spirit-director'>Return to Spirit Director Home</router-link>
  </div>
</template>

<script>
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'
import axios from 'axios';

export default {
  name: 'DetailFormPage',
  props: ['requestId'],
  data(){
    return{
      requests: Object,
      responseStatus: undefined
    }
  },
  components: {
    VueDatePicker
  },
  methods: {
    getTotalPrice(startTime, endTime,type) {
          let typeCoef = 0

          if(type == "TCU") typeCoef = 100
          else if(type == "PUBLIC") typeCoef = 100
          else if(type == "PRIVATE") typeCoef = 175
          
          const start = new Date(`2000-01-01T${startTime.replace(/(\d{1,2}):(\d{2}) ([AP]M)/, function(match, hour, minute, period) {
            return `${hour % 12 + (period.toUpperCase() === 'PM' ? 12 : 0)}:${minute}:00`;
          })}`);

          const end = new Date(`2000-01-01T${endTime.replace(/(\d{1,2}):(\d{2}) ([AP]M)/, function(match, hour, minute, period) {
            return `${hour % 12 + (period.toUpperCase() === 'PM' ? 12 : 0)}:${minute}:00`;
          })}`);
          let difference = end.getTime() - start.getTime();
          console.log(difference)
        
          if (end.getTime() < start.getTime()) {
            difference += 12 * 60 * 60 * 1000;
          }
          
          const hours = difference / (60 * 60 * 1000);
          // this.eventInfo.totalCost =  parseFloat(hours * typeCoef)
          return parseFloat(hours * typeCoef);
    },
},

beforeMount(){
   axios.get(`http://localhost:8080/api/appearances/${this.requestId}`)

           .then((response)  => {
            
              this.requests = response.data.data;
              console.log(this.requests)
            
            }).catch((error) => {
              console.log(error)

            })
}
}
</script>

<style scoped>
  .message {
    display: flex;
    justify-content: space-evenly;
    align-self: center;
    text-align: center;
  }
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