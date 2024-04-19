<template>
  <div>
    <h2 class="text">Enter Details For Event</h2>
  </div>
  <div>
    <h3 class="text">Event Information:</h3>
  </div>
  <div>
    <label for="enter-event-title">Enter Event Title:</label>
    <input type="text" id="enter-event-title" v-model="eventTitle">
  </div>
  <br/>
  <div>
    <label for="enter-name-of-org">Enter Name of Organization:</label>
    <input type="text" id="enter-name-of-org" v-model="nameOfOrg">
  </div>
  <br/>
  <div>
    <label for="select-date">Select Date:</label>
    <input type="date" id="select-date" v-model="date">
  </div>
  <br/>
  <div>
    <label for="select-start-time">Select Start Time:</label>
    <input type="time" id="select-start-time" v-model="startTime">
  </div>
  <br/>
  <div>
    <label for="select-end-time">Select End Time:</label>
    <input type="time" id="select-end-time" v-model="endTime">
  </div>
  <br/>
  <div>
    <label for="event-type">Select Event Type:</label>
    <select id="dropdown" v-model="eventType">
        <option value="TCU">TCU</option>
        <option value="PUBLIC">Public/Non-profit</option>
        <option value="PRIVATE">Private/Residential</option>
    </select>
  </div>
  <br/>
  <div>
    <label for="enter-address">Enter Address of Event:</label>
    <input type="text" id="enter-address" v-model="address">
  </div> 
  <br/>
  <div>
    <label>Is the event on TCU campus?</label>
    <div>
      <label>
        <input type="radio" name="campus" v-model="onCampus" value="yes"> Yes
      </label>
      <label>
        <input type="radio" name="campus" v-model="onCampus" value="no"> No
      </label>
    </div>
  </div>
  <br/>
  <div>
    <label>Event Description:</label>
    <div>
      <textarea id="enter-description" v-model="description"></textarea>
    </div>
  </div> 
  <div>
    <h3 class="text">Contact Information:</h3>
  </div>
  <div>
    <label for="enter-first-name">Enter First Name:</label>
    <input type="text" id="enter-first-name" v-model="firstName">
  </div>
  <br/>
  <div>
    <label for="enter-last-name">Enter Last Name:</label>
    <input type="text" id="enter-last-name" v-model="lastName">
  </div>
  <br/>
  <div>
    <label for="enter-phone-number">Enter Phone Number:</label>
    <input type="text" id="enter-phone-number" v-model="phoneNumber" placeholder="(XXX) XXX-XXXX">
  </div>
  <br/>
  <div>
    <label for="enter-email">Enter Email:</label>
    <input type="text" id="enter-email" v-model="email" placeholder="example@email.com">
  </div>
  <div>
    <h3 class="text">Additional Event Information:</h3>
  </div>
  <div>
    <label>Any special instructions?</label>
    <div>
      <textarea id="enter-special-instructions" v-model="specialInstructions"></textarea>
    </div>
  </div>
  <br/>
  <div>
    <label>Any expenses or benefits to the spirit team members?</label>
    <div>
      <textarea id="enter-expenses" v-model="expenses"></textarea>
    </div>
  </div> 
  <br/>
  <div>
    <label for="enter-outside-orgs">Any outside organizations involved?</label>
    <input type="text" id="enter-outside-orgs" v-model="outsideOrgs">
  </div> 
  <br/>
  <div>
    <button class="button-request" @click="goBack">Back</button>
    <button class="button-request">Next</button>
  </div> 
</template>

<script>
  export default {

    methods: {
      goBack(){
        window.history.back();
      }
    }

  }
</script>

<style>
  .text {
    color: #4d2279;
  }

  .button-request {
    margin: 10px;
    padding: 7px 12px;
    font-size: 15px;
    border: none;
    border-radius: 5px;
    background-color: #4d2279;
    color: white;
    cursor: pointer;
  }

  label {
    margin-right: 5px;
  }
</style>