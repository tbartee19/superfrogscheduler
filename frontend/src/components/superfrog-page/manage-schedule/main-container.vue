<template>
    <div>
      <h1>SuperFrog Schedule</h1>
        
      <h2>Create New Event</h2>
      <form @submit.prevent="createEvent">
        <input type="text" v-model="newEvent.title" placeholder="Event Title">
        <input type="datetime-local" v-model="newEvent.startDateTime">
        <input type="datetime-local" v-model="newEvent.endDateTime">
        <button type="submit">Add Event</button>
      </form>
      <h3>Personal Schedule</h3>
        <ul>
            <li v-for="event in events" :key="event.id">
            {{ event.title }} - From: {{ event.startDateTime }} To: {{ event.endDateTime }}
            <button @click="editEvent(event)">Edit</button>
            <button @click="deleteEvent(event.id)">Delete</button>
            </li>
        </ul>
    </div>
    <div>
        <button type="events" @click="fetchEvents">Fetch Events</button>
        <button type="home" @click="goHome">Go Home</button>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
      data() {
          return {
              events: [],
              studentId: '', 
              newEvent: {
                  title: '',
                  startDateTime: '',
                  endDateTime: ''
              }
          };
      },
      methods: {
          fetchEvents() {
              axios.get(`http://localhost:8080/api/events/fetch/student/${this.studentId}`)
                  .then(response => {
                      this.events = response.data;
                  })
                  .catch(error => console.error("Failed to load events", error));
          },
          createEvent() {
            console.log(this.newEvent);
              axios.post('http://localhost:8080/api/events/create', {
                  title: this.newEvent.title,
                  startDateTime: this.newEvent.startDateTime,
                  endDateTime: this.newEvent.endDateTime,
                  studentId: this.studentId
              })
              .then(response => {
                  this.events.push(response.data);
                  this.newEvent.title = '';
                  this.newEvent.startDateTime = '';
                  this.newEvent.endDateTime = '';
                  alert('Event created successfully');
              })
              .catch(error => {
                  console.error("Failed to create event", error);
                  alert('Failed to create event');
              });
          },
          editEvent(event) {
                console.log(event);
                this.$router.push({
                    name: 'edit-event',
                    query: {
                        eventData: JSON.stringify(event),
                        studentData: JSON.stringify(this.student)
                    }});
          },
          deleteEvent(eventId) {
              axios.delete(`http://localhost:8080/api/events/delete/${eventId}`)
                  .then(() => {
                      this.events = this.events.filter(event => event.id !== eventId);
                      alert('Event deleted successfully');
                  })
                  .catch(error => console.error("Failed to delete event", error));
          },
          goHome() {
                this.$router.push('/')
            }
      },
      created() {
          
          if (this.$route.query.studentData) {
              this.student = JSON.parse(this.$route.query.studentData);
              this.studentId = this.student.account.id;
          }
          else if (this.$route.query.studentId) {
            this.studentId = this.$route.query.studentId
            console.log(this.studentId);
          }
          this.fetchEvents();
      }
  }
  </script>
  

<style scoped>
li {
  margin-bottom: 10px;
}
button {
  margin-left: 10px;
}
</style>
