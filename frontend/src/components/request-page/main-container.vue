<template>
  <div class="request-page">
    <div class="content-container">
      <!-- <h3>Select Date and Time:</h3> -->
      <component :eventInfo="eventInfo" :is="currentComponent !== '' ? currentComponent : defaultComponent"></component>
      <div class="button-container">
        <button class="button is-primary back-button" @click="goBack" :disabled="selectedComponentIndex === 0">Back</button>
        <button class="button is-primary forward-button" v-if="selectedComponentIndex != components.length - 2" @click="goForward">Forward</button>
        <button class="button is-primary submit-button" v-if="selectedComponentIndex === components.length - 2" @click="submit">Submit</button>
      </div>
      <router-link to="/">
        <button class="button is-primary home-button">Home</button>
      </router-link>     
    </div>
  </div>
</template>

      
<script>
   import StepProgress from 'vue-step-progress';
   import 'vue-step-progress/dist/main.css';
   import DateTimePage from './date-time-page.vue'
   import DetailFormPage from './detail-form-page.vue'
   import ReviewSubPage from './review-sub-page.vue'
   import ReceiptPage from './receipt.vue'
   import axios from 'axios'
      
  export default {
    name: 'RequestPage',
    components: {
      DateTimePage,
      DetailFormPage,
      ReviewSubPage,
      ReceiptPage,
      'StepProgress': StepProgress
        },
        data() {
          return {
            components: [
              { name: 'Date Time', component: DateTimePage },
              { name: 'Detail Form', component: DetailFormPage },
              { name: 'Review Sub', component: ReviewSubPage },
              { name: 'Receipt', component: ReceiptPage}
            ],
            selectedComponentIndex: 0,
            currentStep: 0,
            currentComponent: '',
            defaultComponent: DateTimePage,
            eventInfo : {
              contactFirstName: "",
              contactLastName: "",
              email: "",
              phoneNumber: "",
              addressOfAppearance: "",
              nameOfOrganization: "",
              eventTitle: "",
              eventDescription: "",
              specialInstructions: "",
              outsideOrganizations:"",
              expensesBenefits: "",
              eventDate: "",
              startTime: "",
              endTime: "",
              eventType: "",
              totalCost: 0.00,
              status: "PENDING",
              reciptId: "",
            },
            steps: [
              'Select Date and Time',
              'Complete Form',
              'Review Submission',
            ],
          }
        },

        computed: {
              selectedComponent() {
                  if (this.selectedComponentIndex !== null) {
                    return this.components[this.selectedComponentIndex].component
                  } else {
                  return null
                }
              },
        },
        
        methods: {
          selectComponent(index) {
              this.selectedComponentIndex = index;
              
          },
          goForward() {
            if (this.selectedComponentIndex < this.components.length - 1) {
              this.selectedComponentIndex++;
              this.currentStep++;
              this.currentComponent = this.components[this.selectedComponentIndex].component;
              
            }
          },
          goBack() {
          if (this.selectedComponentIndex > 0) {
            this.selectedComponentIndex--;
            this.currentStep--;
            this.currentComponent = this.components[this.selectedComponentIndex].component;
            }
          },
          sendData(data) {
            this.eventInfo = data;
          },
          submit() {
            if (this.selectedComponentIndex < this.components.length - 1) {
              this.selectedComponentIndex++;
              this.currentStep++;
              this.currentComponent = this.components[this.selectedComponentIndex].component;
            }
            const token=localStorage.getItem('token');
            const headers = {
              'Content-Type': 'application/json'
           }
            axios.post("http://localhost:8080/api/appearances", {
                  eventDate: this.eventInfo.eventDate,
                  startTime: this.eventInfo.startTime,
                  endTime: this.eventInfo.endTime,
                  contactFirstName: this.eventInfo.contactFirstName,
                  contactLastName: this.eventInfo.contactLastName,
                  phoneNumber: this.eventInfo.phoneNumber,
                  email: this.eventInfo.email,
                  eventType: this.eventInfo.eventType,
                  eventTitle: this.eventInfo.eventTitle,
                  nameOfOrg: this.eventInfo.nameOfOrganization,
                  address: this.eventInfo.addressOfAppearance,
                  specialInstructions: this.eventInfo.specialInstructions,
                  expenses: this.eventInfo.expensesBenefits,
                  outsideOrgs: this.eventInfo.outsideOrganizations,
                  description: this.eventInfo.eventDescription
             }, {headers})
             .then(response =>{
                  const data = (response.data);
                  console.log(data.data.requestId);
                  this.successMessage = 'Student added successfully!';
                  this.eventInfo.reciptId = data.data.requestId;
                }).catch(error=>{
                  console.log(error);
          })
        },
          }
        ,
 }
</script>

<style scoped>
  .request-page {
    color: #4d2279;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .content-container {
    margin-top: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .home-button {
    font-size: 20px;
    padding: 10px 0px;
  }

  button.button.is-primary {
    width: 120px;
  }

  .back-button {
    font-size: 20px;
    padding: 10px 0px;
  }

  .forward-button {
    font-size: 20px;
    padding: 10px 0px;
  }
  
  .submit-button {
    font-size: 20px;
    padding: 10px 0px;
  }

  .button-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 30px;
  }
</style>