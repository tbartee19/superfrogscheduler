import { createRouter, createWebHistory } from 'vue-router'
import App from '@/App.vue'

import RequestPage from '@/components/request-page/main-container.vue'
import ModifyPage from '@/components/modify-page/main-container.vue'
import LoginPage from '@/components/login-page/main-container.vue'
import createStudentPage from '@/components/spirit-director-page/create-student/main-container.vue'
import findStudentPage from '@/components/spirit-director-page/find-student/main-container.vue'
import editStudentPage from '@/components/spirit-director-page/edit-student/main-container.vue'
import SpiritDirectorPage from '@/components/spirit-director-page/main-container.vue'
import CalendarPage from '@/components/spirit-director-page/calendar-page/main-container.vue'
import AddEventPage from '@/components/spirit-director-page/calendar-page/add-event/main-container.vue'
import EditOrDeletePage from '@/components/spirit-director-page/calendar-page/edit-or-delete-event/main-container.vue'
import PaymentFormsPage from '@/components/spirit-director-page/payment-forms-page/main-container.vue'
import SelectAppearanceRequestsPage from '@/components/spirit-director-page/payment-forms-page/select-appearance-requests-page/main-container.vue'
import ReportGeneratingAlgorithmPage from '@/components/spirit-director-page/payment-forms-page/select-appearance-requests-page/report-generating-algorithm-page/main-container.vue'
import PerformanceReportsPage from '@/components/spirit-director-page/performance-report-page/main-container.vue'
import SelectStudentsPage from '@/components/spirit-director-page/performance-report-page/select-students.vue'
import ManageSchedulePage from '@/components/superfrog-page/manage-schedule/main-container.vue'
import SuperFrogPage from '@/components/superfrog-page/main-container.vue'
import changePasswordPage from '@/components/superfrog-page/change-password/main-container.vue'
import editEventPage from '@/components/superfrog-page/manage-schedule/edit-event.vue'
import AppearanceRequestPage from '@/components/spirit-director-page/appearance-request-page/main-container.vue'
import EditRequestPage from '@/components/spirit-director-page/appearance-request-page/edit-request/main-container.vue'

const routes = [
    { path: '/', component: App },
    { path: '/request', component: RequestPage },
    { path: '/modify-request', component: ModifyPage },
    { path: '/login', component: LoginPage },

    { path: '/spirit-director', component: SpiritDirectorPage },
    { path: '/spirit-director/create-student', component: createStudentPage },
    { path: '/spirit-director/find-student', component: findStudentPage },
    { path: '/spirit-director/edit-student',name: 'edit-student', component: editStudentPage },
    { path: '/spirit-director/calendar', component: CalendarPage },
    { path: '/spirit-director/calendar/add-event', component: AddEventPage },
    { path: '/spirit-director/calendar/edit-or-delete-event/:id', component: EditOrDeletePage, props: true },
    { path: '/spirit-director/payment-forms', component: PaymentFormsPage },
    { path: '/select-appearance-requests', component: SelectAppearanceRequestsPage },
    { path: '/report-generating-algorithm/:requestId', component: ReportGeneratingAlgorithmPage, props: true },
    { path: '/spirit-director/performance-reports', component: PerformanceReportsPage },
    { path: '/spirit-director/appearance-requests', component: AppearanceRequestPage},
    { path: '/spirit-director/appearance-requests/edit-request/:id', component: EditRequestPage, props: true},
    { path: '/select-students', component: SelectStudentsPage },
    

    { path: '/superfrog', name: 'superfrog', component: SuperFrogPage },
    { path: '/superfrog/change-password', name: 'change-password', component: changePasswordPage },
    { path: '/superfrog/manage-schedule', name: 'manage-schedule', component: ManageSchedulePage },
    { path: '/superfrog/manage-schedule/edit-event', name: 'edit-event', component: editEventPage },
    { path: '/:catchAll(.*)', component: App }
    
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router