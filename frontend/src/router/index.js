import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Sales from "../views/Sales"
import UserAdmin from "../views/UserAdmin"
import BookAdmin from "../views/BookAdmin"
import GenreAdmin from "../views/GenreAdmin"

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/sales',
    name: 'Sales',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: Sales
  },
  {
    path: '/users',
    name: 'UserAdmin',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: UserAdmin
  },
  {
    path: '/books',
    name: 'BookAdmin',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: BookAdmin
  },
  {
    path: '/genres',
    name: 'GenreAdmin',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: GenreAdmin
  },
]

const router = new VueRouter({
  routes
})

export default router
