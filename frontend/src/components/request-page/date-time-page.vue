<template class="main">
  <div class="date">
    Select Date
  <VueDatePicker
    :enable-time-picker="false"
    v-model="eventDate"
    :format="formatDate"
    @closed="updateDate"
  />
  </div>
  <div class="time">
    <span>Select Start Time</span>
    <VueDatePicker
      v-model="startTime"
      time-picker
      :format="formatS"
      @closed="updateStart"
      :is-24="false"
    />
    <span>Select End Time</span>
    <VueDatePicker
      v-model="endTime"
      time-picker
      :format='formatE'
      @closed="updateEnd"
      :is-24="false"
    />
  </div>
</template>


<script>
  import VueDatePicker from '@vuepic/vue-datepicker';
  import '@vuepic/vue-datepicker/dist/main.css'
  import { format } from 'date-fns';
  import { ref } from 'vue';

  export default {
    name: 'DateTimePage',
    data() {
      return {
        startTime: ref(new Date()),
        endTime: ref(new Date()),
        eventDate: ref(new Date()),   
      }
    },

    computed:{
      
    },

    props: {
      eventInfo: {
        type: Object
      }
    },

    components: {
      VueDatePicker
    },

    methods: {
      updateParent() {
      this.$emit('update', {
        eventInfo: this.eventInfo,
        })},
        updateStart(){
          this.eventInfo.startTime = this.startTime;
          this.updateParent();
        },
        updateEnd(){
          this.eventInfo.endTime = this.endTime;
          this.updateParent();
        },
        updateDate(){
          // this.eventInfo.scheduleDate = this.scheduleDate;
          this.eventInfo.eventDate = this.eventDate;
          this.updateParent();
        },
        formatS(date) {
          this.eventInfo.startTime = format(date, 'hh:mm:ss');
          return format(date, 'hh:mm:ss');
        },
        formatE(date) {
          this.eventInfo.endTime = format(date, 'hh:mm:ss');
          return format(date, 'hh:mm:ss ');
          },
        formatDate(date) {
          // this.eventInfo.scheduleDate = format(date, 'yyyy-MM-dd');
          this.eventInfo.eventDate = format(date, 'yyyy-MM-dd');
          return format(date, 'yyyy-MM-dd');
        },
      }
    } 
</script>

<style scoped>
  .date {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: flex-start;
    margin: 10px;
  }

  .time {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: space-between;

  }
</style>
