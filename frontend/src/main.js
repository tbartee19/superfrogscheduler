import { createApp } from 'vue'
import '@/style.css'
import app from '@/app.vue'
import router from '@/router.js'

createApp(app).use(router).mount('#app')

