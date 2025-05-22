import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Layout from '../views/Layout.vue'
import Employee from '../views/Employee.vue'
import Department from '../views/Department.vue'
import Menu from '../views/Menu.vue'
import Welcome from '../views/Welcome.vue'
import Apply from '../views/Apply.vue'
import Todo from '../views/Todo.vue'
import Perm from '../views/Perm.vue'

const routes = [
  { path: '/login', component: Login },
  {
    path: '/',
    component: Layout,
    children: [
      { path: '', component: Welcome },
      { path: 'employee', component: Employee },
      { path: 'department', component: Department },
      { path: 'menu', component: Menu },
      { path: 'apply', component: Apply },
      { path: 'todo', component: Todo },
      { path: 'perm', component: Perm }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router 