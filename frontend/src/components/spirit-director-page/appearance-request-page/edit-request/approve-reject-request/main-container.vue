<template>
  <div class="approve-reject-page">
    <h1>Review Appearance Request</h1>
    <div v-if="request">
      <h2>{{ request.eventTitle }} - {{ request.date }}</h2>
      <p><strong>Organizer:</strong> {{ request.organizerName }}</p>
      <p><strong>Description:</strong> {{ request.description }}</p>
      <button @click="approveRequest" class="btn-approve">Approve</button>
      <button @click="rejectRequest" class="btn-reject">Reject</button>
    </div>
    <div v-else>
      <p>Loading request details...</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      requestId: this.$route.params.requestId,
      request: null
    };
  },
  created() {
    this.fetchRequestDetails();
  },
  methods: {
    fetchRequestDetails() {
      axios.get(`/api/requests/${this.requestId}`)
          .then(response => {
            this.request = response.data;
          })
          .catch(error => {
            console.error('Error fetching request details:', error);
          });
    },
    approveRequest() {
      axios.post(`/api/requests/approve/${this.requestId}`)
          .then(() => {
            alert('Request approved successfully.');
            this.$router.push('/dashboard');
          })
          .catch(error => {
            console.error('Error approving request:', error);
          });
    },
    rejectRequest() {
      axios.post(`/api/requests/reject/${this.requestId}`)
          .then(() => {
            alert('Request rejected successfully.');
            this.$router.push('/dashboard');
          })
          .catch(error => {
            console.error('Error rejecting request:', error);
          });
    }
  }
}
</script>

<style scoped>
.approve-reject-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.btn-approve, .btn-reject {
  margin: 10px;
  padding: 10px 20px;
  border: none;
  color: white;
  font-weight: bold;
  cursor: pointer;
}

.btn-approve {
  background-color: #4CAF50; /* Green */
}

.btn-reject {
  background-color: #f44336; /* Red */
}
</style>
