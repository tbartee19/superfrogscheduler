<template>
    <div class="container">
      <div class="date-1">
        <table class="date-table">
          <tr>
            <td>Report Title</td>
            <td><input type="text" class="normal-text" v-model="reportTitle" ></td>
          </tr>
          <tr>
            <td>Data Source</td>
            <td>
                <select id="dataSource" name="dataSource" v-model="dataSource">
                    <option value="the system">The System</option>
                </select>
            </td>
          </tr>
          <tr>
            <td>Start Date</td>
            <td><VueDatePicker
              :enable-time-picker="false"
              v-model="startDate"
              :model-value="startDate"
              model-type="yyyy-MM-dd"
            /></td>
          </tr>
          <tr>
            <td>End Date</td>
            <td><VueDatePicker
              :enable-time-picker="false"
              v-model="endDate"
              :model-value="endDate"
              model-type="yyyy-MM-dd"
            /></td>
          </tr>
          <tr>
            <td>Report Visualization</td>
            <td>
                <select id="visualization" name="visualization" v-model="visualization">
                    <option value="table">Table</option>
                    <option value="charts">Charts</option>
                    <option value="graph">Graph</option>
                </select>
            </td>
          </tr>
          <tr>
            <td>Sorting Criteria</td>
            <td>
                <select id="sortingCriteria" name="sortingCriteria" v-model="sortingCriteria">
                    <option value="SuperFrog Student last name">SuperFrog Student Last Name</option>
                </select>
            </td>
          </tr>
          <tr>
            <td>Pagination Criteria</td>
            <td>
                <select id="pagination" name="pagination" v-model="pagination">
                    <option value="No pagination">No pagination</option>
                    <option value="10 per page">10 per page</option>
                </select>
            </td>
          </tr>
          <tr>
            <td>Disposition of Report</td>
            <td>
                <select id="disposition" name="disposition" v-model="disposition">
                    <option value="displayed in the system and printed">Displayed in the System and Printed</option>
                </select>
            </td>
          </tr>
          <tr>
            <td>Format of Generated Report</td>
            <td>
                <select id="format" name="format" v-model="format">
                    <option value="PDF">PDF</option>
                </select>
            </td>
          </tr>
        </table>
      </div>
      <div v-if="responseStatus == 200" class="response">{{ returnMsg }}</div>
    </div>

    <div class="button-container">
        <router-link to="/select-appearance-requests">Next</router-link>
    </div>
    
    <!-- <div class="button-container">
      <button class="button is-primary submit-button" @click="submit">Next</button>
    </div> -->
</template>

<script>
    import VueDatePicker from '@vuepic/vue-datepicker';

    export default {
        name: 'PaymentFormPage',
        components: {
            VueDatePicker,
        },
        data(){
            return {
                reportTitle: 'TCU Honorarium (Payment for services) Request Forms',
                dataSource: '',
                startDate: null,
                startTime: null,
                endDate: null,
                endTime: null,
                visualization: '',
                sortingCriteria: '',
                pagination: '',
                disposition: '',
                format: ''
            }
        }
    }
</script>

<style scoped>
.container {
  width: 90vw;
  margin: 0 auto;
  padding: 20px;
  font-family: sans-serif;
}

.date-1 {
  font-size: 1.2em;
  font-weight: bold;
  margin-bottom: 10px;
  display: flex;
  justify-content: space-evenly;
}
.date-1 > div {
  flex-basis: 40%;
  position: relative;
  align-self: center;
  text-align: center;
}

.date-1 > table{
  flex-basis: content;
}

.date-table{
  background-color: white;
  color:#4d2279;
  justify-content: center;
  align-items: center;
}

.normal-text{
  width: 21rem;
  height: 1rem;
  padding: 5px;
  border: 1px solid #ccc;
  position: relative;
}

select {
  height: 20px;
}

.button-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.response {
  display: flex;
  align-items: center;
  flex-direction: column;
}
</style>