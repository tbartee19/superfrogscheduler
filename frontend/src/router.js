import { createRouter, createWebHistory } from 'vue-router'

import App from '@/app.vue'
import CustomerPage from '@/components/customer-page/main-customer.vue'
import SpiritDirectorPage from '@/components/spirit-director-page/main-spirit-director.vue'
import StudentPage from '@/components/student-page/main-student.vue'
import CustomerCalendarPage from '@/components/customer-page/calendar.vue'
import CustomerMakeRequestPage from '@/components/customer-page/make-request.vue'
import CustomerModifyRequestPage from '@/components/customer-page/modify-request.vue'

const routes = [
    { path: '/', component: App },

    { path: '/customer', component: CustomerPage },
    { path: '/spirit-director', component: SpiritDirectorPage },
    { path: '/student', component: StudentPage },
    { path: '/customer/calendar', component: CustomerCalendarPage },
    { path: '/customer/make-request', component: CustomerMakeRequestPage },
    { path: '/customer/modify-request', component: CustomerModifyRequestPage },

    { path: '/:catchAll(.*)', component: App}

]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router