<template>
    <div>
      <h1>Edit Event</h1>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="title">Event Title:</label>
          <input type="text" id="title" v-model="this.event.title">
        </div>
        <div class="form-group">
          <label for="startDateTime">Start Date and Time:</label>
          <input type="datetime-local" id="startDateTime" v-model="this.event.startDateTime">
        </div>
        <div class="form-group">
          <label for="endDateTime">End Date and Time:</label>
          <input type="datetime-local" id="endDateTime" v-model="this.event.endDateTime">
        </div>
        <button type="submit">Update Event</button>
      </form>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        event: {
        id: '',
        title: '',
        startDateTime: '',
        endDateTime: ''
      },
      student: null
      };
    },
    methods: {
      submitForm() {
        axios.put(`http://localhost:8080/api/events/update/${this.event.id}`, {
            id: this.event.id,
            studentId: this.event.studentId,
            title: this.event.title,
            startDateTime: this.event.startDateTime,
            endDateTime: this.event.endDateTime
        })
        .then(response => {
          alert('Event updated successfully');
          
          this.$router.push({
                    name: 'manage-schedule',
                    query: {
                        studentId: this.event.studentId
                    }});
        })
        .catch(error => {
          console.error('Failed to update event', error);
          console.log(this.event);
          alert('Failed to update event');
        });
      }
    },
    created() {
        if (this.$route.query.eventData) {
            try {
                this.event = JSON.parse(this.$route.query.eventData);
            } catch (e) {
                console.error('Error parsing event data:', e);
            }
        }
        else if (this.$route.query.studentInfo){
            try {
                this.student = JSON.parse(this.$route.query.studentInfo)
            } catch (e) {
                console.error('Error parsing student data:',e);
            }
            
        }
        console.log("this.event on created:", this.event);
  
    }
  };
  </script>
  