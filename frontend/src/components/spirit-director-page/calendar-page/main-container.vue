<template>
  <div class="events-page">
    <router-link to="/spirit-director/calendar/add-event">Add An Event</router-link>
    <p>Click an Appearance Request to Edit/Delete it:</p>

    <router-link to="/spirit-director/calendar/edit-or-delete-event">
      <template v-for="i in requests" :key="i.requestId">
        <table>
          <tr>
            <td>Event Title</td>
            <td>{{i.eventTitle}}</td>
          </tr>
          <tr>
            <td>Name of Organization</td>
            <td>{{i.nameOfOrg}}</td>
          </tr>
          <tr>
            <td>Event Date</td>
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
        </table>
      </template>
    </router-link>
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
    height: 55vh;
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