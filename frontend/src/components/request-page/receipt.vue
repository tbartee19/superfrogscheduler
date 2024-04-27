<template>
  <div style="text-align: center;">
    <h3>Thank You!</h3>
    
    Your request has been submitted.
    <br>
    Please retain this request ID for your records: {{ eventInfo.reciptId }}
  </div>
</template>

<script>
  export default {
    name: 'ReceiptPage',
    props:{
      eventInfo: {
        type: Object
      }
    }
  }
</script>