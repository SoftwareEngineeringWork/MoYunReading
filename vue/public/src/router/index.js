import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/common/Home.vue'
import Layout from "@/views/Layout";
import review from "@/views/review/Review.vue";
Vue.use(VueRouter);

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/admin/Login.vue')
  },
  {
    path: '/',
    name: 'home',
    component: Layout,
    redirect: '/home',
    children: [
      {
        path: 'home',
        name: 'Home',
        component: Home
      },
      {
        path: 'review',
        name: 'Review',
        component: review
      },

      // ========= User =============
      {
        path: 'user/list',
        name: 'User',
        component: () => import('../views/user/User.vue')
      },
      {
        path: 'user/add',
        name: 'AddUser',
        component: () => import('../views/user/AddUser.vue')
      },


      // ========= Admin =============
      {
        path: 'admin/list',
        name: 'Admin',
        component: () => import('../views/admin/Admin.vue')
      },
      {
        path: 'admin/add',
        name: 'AddAdmin',
        component: () => import('../views/admin/AddAdmin.vue')
      },

      // ========= Category =============
      {
        path: 'category/list',
        name: 'Category',
        component: () => import('../views/category/Category.vue')
      },
      {
        path: 'category/add',
        name: 'AddCategory',
        component: () => import('../views/category/AddCategory.vue')
      },

      // ========= Book =============
      {
        path: 'book/list',
        name: 'Book',
        component: () => import('../views/book/Book.vue')
      },
      {
        path: 'book/borrow',
        name: 'BookBorrow',
        component: () => import('../views/book/BookBorrow.vue')
      },
      {
        path: 'book/return',
        name: 'BookReturn',
        component: () => import('../views/book/BookReturn.vue')
      }
    ]
  },
  {
    path: '*',
    name: '404',
    component: () => import('../views/error/404.vue')
  }
];

import Cookies from 'js-cookie';

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

router.beforeEach((to,from,next) => {
  if (to.path === '/login') next();
  let user = Cookies.get('user');
  if (user == null && to.path !== '/login'){
    return next('/login');
  }
  next();
});

export default router
