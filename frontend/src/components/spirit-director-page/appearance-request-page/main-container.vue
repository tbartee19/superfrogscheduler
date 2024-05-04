<template>
    <div class="appearance-request-page">
        <p>Click on an Appearance Request to Edit it</p>

            <template v-for="i in requests" :key="i.requestId">
                <table>
                    <router-link :to="`/spirit-director/appearance-requests/edit-request/${i.requestId}`">
                        <tr>
                            <td>Request Id</td>
                            <td>{{i.requestId}}</td>
                        </tr>
                        <tr>
                            <td>Event Title</td>
                            <td>{{i.eventTitle}}</td>
                        </tr>
                        <tr>
                            <td>Event Type</td>
                            <td>{{i.eventType}}</td>
                        </tr>
                        <tr>
                            <td>Name of Organization</td>
                            <td>{{i.nameOfOrg}}</td>
                        </tr>
                        <tr>
                            <td>Date</td>
                            <td>{{i.eventDate}}</td>  
                        </tr>
                        <tr>
                            <td>Start Time</td>
                            <td>{{i.startTime}}</td>
                        </tr>
                        <tr>
                            <td>End Time</td>
                            <td>{{i.endTime}}</td>  
                        </tr>
                        <tr>
                            <td>Address</td>
                            <td>{{i.address}}</td>
                        </tr>
                        <tr>
                            <td>Special Instructions</td>
                            <td>{{i.specialInstructions}}</td>
                        </tr>
                        <tr>
                            <td>Expenses</td>
                            <td>{{i.expenses}}</td>
                        </tr>
                        <tr>
                            <td>Outside Organizations</td>
                            <td>{{i.outsideOrgs}}</td>
                        </tr>
                        <tr>
                            <td>Contact First Name</td>
                            <td>{{i.contactFirstName}}</td>
                        </tr>
                        <tr>
                            <td>Contact Last Name</td>
                            <td>{{i.contactLastName}}</td>
                        </tr>
                        <tr>
                            <td>Phone</td>
                            <td>{{i.phoneNumber}}</td>  
                        </tr>
                        <tr>
                            <td>Email</td>
                            <td>{{i.email}}</td>  
                        </tr>
                        <tr>
                            <td>Status</td>
                            <td>{{i.status}}</td>
                        </tr>
                    </router-link>
                </table>
            </template>
            <router-link to="/spirit-director">Back</router-link>
    </div>
</template>

<script>
  import axios from 'axios'

    export default {
        name: 'AppearanceRequestPage',
        data(){
          return {
            requests: Object
          }
        },
        created(){
          axios.get(`http://localhost:8080/api/appearances`)
            .then((response)  => {
              this.requests = response.data.data;
              console.log(this.requests)
            }).catch((error) => {
              console.log(error)
            })
        },
        methods: {
  
        }
    }
</script>

<style>
  .appearance-request-page {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-top: 20px;
  }

  table {
    margin-bottom: 1%;
  }

  td {
    border-color: lightgray;
    background-color: white;
    color: grey;
    font-size: 1rem;
    text-align: center;
    padding: 2.5px;
  }

</style>