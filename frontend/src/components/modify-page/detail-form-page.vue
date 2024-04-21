<template>
  <div class="container" v-show="requests">
    <div class="date-1">
      <table class="date-table">
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
      <div class="dropdown-box">
        <span>Event Type</span>
        <select id="dropdown" v-model="requests.eventType" >
          <option value="TCU">TCU</option>
          <option value="PUBLIC">Public/Non-profit</option>
          <option value="PRIVATE">Private/Residential</option>
        </select>
      </div>
    </div>
      <div class="title-container">
            <span class="title-1">Personal Contact Information</span>
        </div>
        <div class="contact-info">
          
            <div>
              <span>First Name</span>
              <input type="text" class="normal-text" v-model="requests.contactFirstName" >
            </div>
            <div>
              <span>Last Name</span>
              <input type="text" class="normal-text" v-model="requests.contactLastName">
            </div>
            <div>
              <span>Phone Number</span>
              <input placeholder="(xxx)-xxx-xxxx" type="text" class="normal-text" v-model="requests.phoneNumber" >
            </div>
            <div>
              <span>Email</span>
              <input type="text" class="normal-text" v-model="requests.email">
            </div>
          
        </div>
  <div class="title-container">
  <span class="title-1">Event Information</span>
  </div>
  <div class="event-1">
    <div>
      <span>Event Title</span>
      <span><input type="text" class="normal-text" v-model="requests.eventTitle" ></span>
    </div>
    <div>
      <span>Name of Organization</span>
      <span><input type="text" class="normal-text" v-model="requests.nameOfOrg" ></span>
    </div>
    <div>
      <span style="display: inline;">Address of Event</span>
      <span><input placeholder="(street, suite/room/floor, city, state, postal code" type="text" class="normal-text" v-model="requests.address" ></span>
    </div>
  </div>
    
  
  <div class="event-2">
    <div>
      <span>Event Description</span>
      <span><input type="text" class="large-text" v-model="requests.description" ></span>
    </div>
  <div>
      <span>Any special instructions?</span>
      <span><input type="text" class="large-text" v-model="requests.specialInstructions"></span>
  </div>
  <div>
      <span>Any other outside organizations involved?</span>
      <span><input type="text" class="large-text" v-model="requests.outsideOrgs" ></span>
  </div>
  <div>
      <span>Any expenses or benefits to the team members?</span>
      <span><input type="text" class="large-text" v-model="requests.expenses" ></span>
  </div>
  </div>
</div>
<div v-if="responseStatus == 200">{{ returnMsg }}</div>
    <!--For the other css components when you add the input boxes just follow the two as above-->
<div class="button-container">
  <button class="button is-primary submit-button" @click="submit">Submit Changes</button>
  <button class="button is-primary submit-button" @click="cancel">Cancel Request</button>
</div>
</template>

<script>
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'
import axios from 'axios';
import { format } from 'date-fns';

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
    padding-left: 5%;
    padding-right: 5%;
  }

  .event-1 {
    font-size: 1.2em;
    font-weight: bold;
    margin-bottom: 10px;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    text-align: center;
    margin-bottom: 1vh;
  }

  .event-1 > div > span{
    text-align: left;
  }

  .event-1 > div{
    flex-basis: 50%;
    display: grid;
    justify-content: space-around;
    align-content: stretch;
    align-items: center;
    margin-bottom: 2vh;
  }

  .event-1 > div:nth-child(3) {
    flex-basis: 100%;
    justify-content: center;
  }

  .contact-info {
    font-size: 1.2em;
    font-weight: bold;
    margin-bottom: 2vh;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: space-between;
  }

  .title-container {
    display: flex;
    justify-content: center;
    margin-bottom: 1vh;
  }

  .contact-info > div{
    flex-basis: 50%;
    width: 22rem;
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    flex-direction: column;
  }

  .contact-info > div > span{
    width: 22rem;
    position: relative;
  }

  .title-1 {
    font-size: 1.5em;
    font-weight: bold;
    margin-bottom: 10px;
    display: block;
    align-items: center;
  }

  .normal-text{
    width: 21rem;
    height: 1rem;
    padding: 5px;
    border: 1px solid #ccc;
    position: relative;
  }

  select{
    height: 20px;
  }

  .large-text{
    height: 8rem;
    width: 22rem;
  }

  .event-2{
    font-size: 1.2em;
    font-weight: bold;
    margin-bottom: 10px;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: space-between;
  }

  .event-2 > div{
    flex-basis: 50%;
    width: 22rem;
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    flex-direction: column;
    margin-bottom: 2vh;
  }

  .event-2 > div > span{
    width: 22rem;
    position: relative;
  }

  .button-container {
    display: flex;
    justify-content: center;
    align-items: center;

  }
  
  .dropdown-box span {
    display: block;
  }
</style>