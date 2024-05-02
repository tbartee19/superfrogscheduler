<template>
  <div class="requests-list">
    <h1>Pending Appearance Requests</h1>
    <table class="table is-striped">
      <thead>
      <tr>
        <th>Request ID</th>
        <th>Title</th>
        <th>Date</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="request in requests" :key="request.id">
        <td>{{ request.id }}</td>
        <td>{{ request.title }}</td>
        <td>{{ request.date }}</td>
        <td>
          <button class="button is-success" @click="approveRequest(request.id)">Approve</button>
          <button class="button is-danger" @click="rejectRequest(request.id)">Reject</button>
        </td>
      </tr>
      </tbody>
    </table>
    <div v-if="message" class="notification is-info">
      {{ message }}
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'RequestList',
  data() {
    return {
      requests: [],
      message: ''
    };
  },
  created() {
    this.fetchRequests();
  },
  methods: {
    fetchRequests() {
      axios.get('/api/requests/pending')
          .then(response => {
            this.requests = response.data;
          })
          .catch(error => {
            console.error('There was an error fetching the requests:', error);
          });
    },
    approveRequest(id) {
      axios.post(`/api/requests/approve/${id}`)
          .then(() => {
            this.message = 'Request approved successfully!';
            this.fetchRequests(); // Refresh the list
          })
          .catch(error => {
            console.error('Error approving request:', error);
            this.message = 'Failed to approve request.';
          });
    },
    rejectRequest(id) {
      axios.post(`/api/requests/reject/${id}`)
          .then(() => {
            this.message = 'Request rejected successfully!';
            this.fetchRequests(); // Refresh the list
          })
          .catch(error => {
            console.error('Error rejecting request:', error);
            this.message = 'Failed to reject request.';
          });
    }
  }
};
</script>

<style scoped>
.requests-list {
  max-width: 800px;
  margin: auto;
  text-align: center;
}

.table {
  width: 100%;
  margin-top: 20px;
}

.button {
  margin: 5px;
}
</style>
