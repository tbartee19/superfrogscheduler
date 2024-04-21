<template>
    <div class="input-id" v-if="!requests">
        <span>Enter the ID of the request you wish to modify:</span>
        <input v-model.lazy="requestId" v-bind:placeholder="message" >
        <button class="button is-primary submit-button" @click="getrequests(requestId)">Submit</button>
        <span v-if=message>{{ message }}</span>

    </div>
    <div class="request-page" v-if="requests">
      <div class="content-container">
        <component :requestId="requestId" :is="currentComponent !== '' ? currentComponent : defaultComponent"></component>
        
        <router-link to="/">
          <button class="button is-primary home-button">Home</button>
        </router-link>
        
      </div>
    </div>
  </template>
  
        
  <script>
     import DetailFormPage from './detail-form-page.vue'
     import axios from 'axios'
        
    export default {
      name: 'ModifyPage',
      components: {
       
        DetailFormPage,
      
          },
          data() {
            return {
              components: [
                { name: 'Detail Form', component: DetailFormPage },
              ],
              selectedComponentIndex: 0,
              currentStep: 0,
              currentComponent: '',
              defaultComponent: DetailFormPage,
              requests: undefined,
              requestId: undefined ,
              message: undefined,
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
            
            sendData(data) {
              this.requests = data;
            
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
              axios.put(`http://localhost:8080/api/appearances/${this.requestId}`, {
                eventDate: this.requests.eventDate,
                startTime: this.requests.startTime,
                endTime: this.requests.endTime,
                contactFirstName: this.requests.contactFirstName,
                contactLastName: this.requests.contactLastName,
                phoneNumber: this.requests.phoneNumber,
                email: this.requests.email,
                eventType: this.requests.eventType,
                eventTitle: this.requests.eventTitle,
                nameOfOrg: this.requests.nameOfOrg,
                address: this.requests.addressOfAppearance,
                specialInstructions: this.requests.specialInstructions,
                expenses: this.requests.expenses,
                outsideOrgs: this.requests.outsideOrgs,
                description: this.requests.description
               }, {headers})
               .then(response =>{
                    const data = (response.data);
                    console.log(data.data.requestId);
                    this.successMessage = 'Student added successfully!';
                    this.requests.reciptId = data.data.requestId;
                  }).catch(error=>{
                    console.log(error);
            })
          },
          getrequests(requestId)
          {
            console.log(requestId)
            axios.get(`http://localhost:8080/api/appearances/${this.requestId}`)
           .then((response)  => {
              this.requests = response.data.data;
              console.log(this.requests)
            }).catch((error) => {
                console.log(error.response.status)
                if (error.response.status === 404) {
                    this.message = "Sorry, but that request ID could not be found. Please try again."
                }
            })
          }
        },
   }  
</script>
  
<style scoped>
  .input-id {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-top: 10vh;

    color: #4d2279;
  }

  .input-id > * {
    padding: 2vh;
    font-size: 1.2em;
  }

  .request-page {
    background-color: white;
    color: #4d2279;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .content-container {
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