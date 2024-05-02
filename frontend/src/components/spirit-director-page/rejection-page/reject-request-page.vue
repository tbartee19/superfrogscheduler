<template>
  <div class="reject-request-page">
    <h1>Reject Appearance Request</h1>
    <div v-for="request in requests" :key="request.id">
      <p>{{ request.title }}</p>
      <!-- ... other request details ... -->
      <input type="text" placeholder="Rejection reason" v-model="request.rejectionReason"/>
      <button @click="rejectRequest(request.id, request.rejectionReason)">Reject</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      requests: [], // This should be fetched from the backend
    };
  },
  methods: {
    rejectRequest(requestId, rejectionReason) {
      // Call to backend to reject the request
      axios.put('/api/request/reject/' + requestId, { rejectionReason })
          .then(() => {
            // Handle success
          })
          .catch(error => {
            // Handle error
            console.error('Error rejecting request:', error);
          });
    },
  },
  created() {
    // Fetch pending requests from the backend
    axios.get('/api/requests/pending')
        .then(response => {
          this.requests = response.data;
        })
        .catch(error => {
          // Handle error
          console.error('Error fetching requests:', error);
        });
  }
};
</script>

<!-- Add styles as needed -->
