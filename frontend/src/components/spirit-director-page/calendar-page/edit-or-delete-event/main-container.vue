<template>
    <div class="container">
      <div class="date-1">
        <table class="date-table">
          <tr>
            <td>Event Title</td>
            <td><input type="text" class="normal-text" v-model="requests.eventTitle" ></td>
          </tr>
          <tr>
            <td>Date</td>
            <td><VueDatePicker
              :enable-time-picker="false"
              v-model="requests.eventDate"
              :model-value="requests.eventDate"
              model-type="yyyy-MM-dd"
            /></td>
          </tr>
          <tr>
            <td>Start Time</td>
            <td><VueDatePicker
                :model-value="requests.startTime"
                model-type="HH:mm:ss"
                time-picker
                :is-24="false"
                :start-time="requests.startTime"
            /></td>
          </tr>
          <tr>
            <td>End Time</td>
            <td><VueDatePicker
                  v-model="requests.endTime"
                  model-type="HH:mm:ss"
                  time-picker
                  :is-24="false"
                  :start-time="requests.endTime"
            /></td>
          </tr>
        </table>
      </div>
    </div>

    <div class="button-container">
      <button class="button is-primary submit-button" @click="submit">Submit Changes</button>
      <button class="button is-primary submit-button" @click="cancel">Cancel Request</button>
    </div>
</template>
  
<script>
  import VueDatePicker from '@vuepic/vue-datepicker';
  import '@vuepic/vue-datepicker/dist/main.css'
  import axios from 'axios';
  
  export default {
    name: 'DetailFormPage',
    props: {
      requestId: String,
    },
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
      findDifferentVars(obj1, obj2) {
        let differentVars = [];
  
        const rawObj1 = isProxy(obj1) ? toRaw(obj1) : obj1;
        const rawObj2 = isProxy(obj2) ? toRaw(obj2) : obj2;
  
        const obj1Keys = Object.keys(rawObj1);
        const obj2Keys = Object.keys(rawObj2);
  
        obj1Keys.forEach((key) => {
          if (rawObj1[key] !== rawObj2[key]) {
            differentVars.push(key);
          }
        });
  
        return differentVars;
      },
      cancel() {
        const headers = {
                  'Content-Type': 'application/json'
               }
                axios.delete(`http://localhost:8080/api/appearances/${this.requestId}`)
                 .then(response =>{
                      const data = (response.data);
                      // console.log(data.data.requestId);
                      // this.requests.requestId = data.data.requestId;
                      this.returnMsg ="This request has been canceled."
                      this.responseStatus = response.status;
                    }).catch(error=>{
                      console.log(error);
              })
  
      },
      
      submit() {
        // let containsCommonElement = false
        // let differentVars = this.findDifferentVars(this.$data.requests, this.$data.ogReq)
        // const pendingVars = ['startTime', 'endTime', 'eventDate', 'eventType', 'eventTitle', 'address', 'description', 'outsideOrgs', 'expenses', 'nameOfOrg', ]
        // pendingVars.forEach((key) => {
        //   if (differentVars.includes(key)) {
        //     containsCommonElement = true
        //   }
        // });
  
        // console.log(differentVars);
  
        const headers = {
                  'Content-Type': 'application/json'
               }
                axios.put(`http://localhost:8080/api/appearances/${this.requestId}`, {
                      contactFirstName: this.requests.contactFirstName,
                      contactLastName: this.requests.contactLastName,
                      email: this.requests.email,
                      phoneNumber: this.requests.phoneNumber,
                      address: this.requests.address,
                      nameOfOrg: this.requests.nameOfOrg,
                      eventTitle: this.requests.eventTitle,
                      description: this.requests.description,
                      specialInstructions: this.requests.specialInstructions,
                      outsideOrgs: this.requests.outsideOrgs,
                      expenses: this.requests.expenses,
                      eventDate: this.requests.eventDate,
                      startTime: this.requests.startTime,
                      endTime: this.requests.endTime,
                      eventType: this.requests.eventType,
                      totalCost: this.requests.totalCost,
                 }, {headers})
                 .then(response =>{
                      const data = (response.data);
                      console.log(data.data.requestId);
                      this.requests.requestId = data.data.requestId;
                      this.responseStatus = response.status;
                      this.returnMsg = "Your request has been updated."
                    }).catch(error=>{
                      console.log(error);
              })
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