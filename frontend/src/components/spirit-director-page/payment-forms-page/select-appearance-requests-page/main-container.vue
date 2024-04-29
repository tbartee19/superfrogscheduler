<template>
  <div class="events-page">
    <p>Click an Appearance Request to Validate It and Send It to Payroll:</p>

      <template v-for="i in requests" :key="i.requestId">
          <table>
            <router-link :to="`/report-generating-algorithm/${i.requestId}`">
              <tr>
                <td>Request Id</td>
                <td>{{ i.requestId }}</td>
              </tr>
              <tr>
                <td>Event Title</td>
                <td>{{i.eventTitle}}</td>
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
                <td>SuperFrog Student</td>
                <td>John Doe</td>
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
        name: 'CalendarPage',
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
  .events-page {
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