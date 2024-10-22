import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import("../views/Home/HomePage.vue")
  },
  {
    path: '/main',
    name: 'mainpage',
    component: () => import("../views/Main/MainPage.vue")
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
