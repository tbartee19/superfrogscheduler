import { createRouter, createWebHistory } from 'vue-router'
import RequestPage from '@/components/request-page/main-container.vue'
import App from '@/App.vue'
import ModifyPage from '@/components/modify-page/main-container.vue'

const routes = [
    { path: '/', component: App },

    { path: '/request', component: RequestPage },
    { path: '/modify-request', component: ModifyPage },
    { path: '/:catchAll(.*)',
    component: App}
    
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router