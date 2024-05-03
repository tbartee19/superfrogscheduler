<template>
    <div class="button-container">
      <button class="button is-primary reverse-button" @click="reverse">Reverse Decision</button>
      <button class="button is-primary incomplete-button" @click="incomplete">Mark Incomplete</button>
      <button class="button is-primary cancel-button" @click="cancel"> Cancel Appearance</button>
    </div>
</template>
  
<script>
  import axios from 'axios';
  
  export default {
    name: 'EditRequestPage',
    props: ['id'],
    data(){
      return{
        requests: Object,
        responseStatus: undefined
      }
    },
    components: {
      
    },
    methods: {
      incomplete() {
        const headers = {
                  'Content-Type': 'application/json'
               }
                axios.put(`http://localhost:8080/api/appearance/${this.id}/incomplete`)
                 .then(response =>{
                      const data = (response.data);
                      this.returnMsg ="Appearance set as incomplete."
                      this.responseStatus = response.status;
                    }).catch(error=>{
                      console.log(error);
              })
  
      },
      
      reverse() {  
        const headers = {
                  'Content-Type': 'application/json'
               }
                axios.put(`http://localhost:8080/api/appearance/${this.id}/reverse`)
                      .then(response =>{
                      const data = (response.data);
                      console.log(data.data.id);
                      this.requests.id = data.data.id;
                      this.responseStatus = response.status;
                      this.returnMsg = "Status reverse success"
                    }).catch(error=>{
                      console.log(error);
              })
            },

        cancel(){
            const headers = {
                'Content-Type': 'application/json'
            }
            axios.put(`http://localhost:8080/api/appearance/${this.id}/cancel`)
                .then(response =>{
                    const data = (response.data);
                    console.log(data.data.id);
                    this.requests.id = data.data.id;
                    this.responseStatus = response.status;
                    this.returnMsg = "Appearance cancelled"
                }).catch(error=>{
                    console.log(error);
                })
        }
    
  },
  
  beforeMount(){
     axios.get(`http://localhost:8080/api/appearances/${this.id}`)
  
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
  
    .request-1 {
      font-size: 1.2em;
      font-weight: bold;
      margin-bottom: 10px;
      display: flex;
      justify-content: space-evenly;
    }
    .request-1 > div {
      flex-basis: 40%;
      position: relative;
      align-self: center;
      text-align: center;
    }
  
    .request-1 > table{
      flex-basis: content;
    }
  
    .request-table{
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

    .response {
      display: flex;
      align-items: center;
      flex-direction: column;
    }
  </style>