<template>
  <div class="container">
    <div class="title-container">
      <span class="title-1">Personal Contact Information</span>
    </div>
    <div class="contact-info">
      <div>
        <span>First Name</span>
        <input type="text" class="normal-text" v-model="eventInfo.contactFirstName" @input="updateParent">
      </div>
      <div>
        <span>Last Name</span>
        <input type="text" class="normal-text" v-model="eventInfo.contactLastName" @input="updateParent">
      </div>
      <div>
        <span>Phone Number</span>
        <input placeholder="(XXX) XXX-XXXX" type="text" class="normal-text" v-model="eventInfo.phoneNumber" @input="updateParent">
      </div>
      <div>
        <span>Email</span>
        <input placeholder="example@email.com" type="text" class="normal-text" v-model="eventInfo.email" @input="updateParent">
      </div>
    </div>
  <div class="title-container">
    <span class="title-1">Event Information</span>
  </div>
  <div class="event-1">
    <div class="dropdown-box">
      <span>Event Type</span>
        <select id="dropdown" v-model="eventInfo.eventType" @change="updateParent">
          <option value="TCU">TCU</option>
          <option value="PUBLIC">Public</option>
          <option value="PRIVATE">Private</option>
        </select>
    </div>

      <div>
        <span>Event Title</span>
        <span><input type="text" class="normal-text" v-model="eventInfo.eventTitle" @input="updateParent"></span>
      </div>

      <div>
        <span>Name of Organization</span>
        <span><input type="text" class="normal-text" v-model="eventInfo.nameOfOrganization" @input="updateParent"></span>
      </div>

      <div>
        <span style="display: inline;">Address of Event</span>
        <span><input placeholder="Street, City, State, Zip Code" type="text" class="normal-text" v-model="eventInfo.addressOfAppearance" @input="updateParent"></span>
      </div>
    </div>
    
    <div class="event-2">
      <div>
        <span>Event Description</span>
        <span><input type="text" class="large-text" v-model="eventInfo.eventDescription" @input="updateParent"></span>
      </div>
      <div>
          <span>Any special instructions?</span>
          <span><input type="text" class="large-text" v-model="eventInfo.specialInstructions" @input="updateParent"></span>
      </div>
      <div>
        <span>Any other outside organizations involved?</span>
        <span><input type="text" class="large-text" v-model="eventInfo.outsideOrganizations" @input="updateParent"></span>
      </div>
      <div>
        <span>Any expenses or benefits to the team members?</span>
        <span><input type="text" class="large-text" v-model="eventInfo.expensesBenefits" @input="updateParent"></span>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'DetailFormPage',
    props: {
      eventInfo: {
        type: Object
      }
    },
    data(){
      return{
          contactFirstName: this.eventInfo.contactFirstName,
          contactLastName: this.eventInfo.contactLastName,
          email: this.eventInfo.email,
          phoneNumber: this.eventInfo.phoneNumber,
          addressOfAppearance: this.eventInfo.addressOfAppearance,
          nameOfOrganization: this.eventInfo.nameOfOrganization,
          eventTitle: this.eventInfo.eventTitle,
          eventDescription: this.eventInfo.eventDescription,
          specialInstructions: this.eventInfo.specialInstructions,
          outsideOrganizations: this.eventInfo.outsideOrganizations,
          expensesBenefits: this.eventInfo.expensesBenefits,
          // scheduleDate: this.eventInfo.scheduleDate,
          eventDate: this.eventInfo.eventDate,
          startTime: this.eventInfo.startTime,
          endTime: this.eventInfo.endTime,
          eventType: this.eventInfo.eventType,
          dropdownValues: ['TCU', 'PUBLIC', 'PRIVATE']
      }
    },
    methods: {
      updateParent() {
        this.$emit('update', {
          eventInfo: this.eventInfo, 
        });
      }
    },
    computed: {
    
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

  .date-1 > table {
    flex-basis: content;
  }

  .date-table {
    background-color: white;
    color:lightslategray;
    padding-left: 5%;
    padding-right: 5%;
    border: 1px solid black;
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

  .event-1 > div > span {
    text-align: left;
  }

  .event-1 > div {
    flex-basis: 50%;
    display: grid;
    justify-content: space-around;
    align-content: stretch;
    align-items: center;
    margin-bottom: 2vh;
  }

  .event-1 > div:nth-child(1) {
    align-items: left;
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

  .contact-info > div {
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

  .normal-text {
    width: 21rem;
    height: 1rem;
    padding: 5px;
    border: 1px solid #ccc;
    position: relative;
  }

  select {
    height: 20px;
  }

  .large-text {
    height: 8rem;
    width: 22rem;
  }

  .event-2 {
    font-size: 1.2em;
    font-weight: bold;
    margin-bottom: 10px;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: space-between;
  }

  .event-2 > div {
    flex-basis: 50%;
    width: 22rem;
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    flex-direction: column;
    margin-bottom: 2vh;
  }

  .event-2 > div > span {
    width: 22rem;
    position: relative;
  }
</style>