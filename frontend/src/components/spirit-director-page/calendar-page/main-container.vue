<template>
  <div class="events-page">
    <router-link to="/spirit-director/calendar/add-event">Add An Event</router-link>
    <p>Click an Event to Edit/Delete it:</p>

      <template v-for="i in requests" :key="i.id">
          <table>
            <router-link :to="`/spirit-director/calendar/edit-or-delete-event/${i.id}`">
              <tr>
                <td>Event Title</td>
                <td>{{i.eventTitle}}</td>
              </tr>
              <tr>
                <td>Start Date</td>
                <td>{{i.startDate}}</td>  
              </tr>
              <tr>
                <td>Start Time</td>
                <td>{{i.startTime}}</td>
              </tr>
              <tr>
                <td>End Date</td>
                <td>{{i.endDate}}</td>  
              </tr>
              <tr>
                <td>End Time</td>
                <td>{{i.endTime}}</td>
              </tr>
              <tr>
                <td>Recurrence Start Date</td>
                <td>{{i.recurrenceStart}}</td>  
              </tr>
              <tr>
                <td>Recurrence End Date</td>
                <td>{{i.recurrenceEnd}}</td>  
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
          axios.get(`http://localhost:8080/api/spirit-director-events`)
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