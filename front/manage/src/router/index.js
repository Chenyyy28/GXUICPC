// import HomePage from '@/components/HomePage.vue'
// import Hello from '@/components/Hello.vue'
import Login from '@/views/Login.vue'
import MainPage from '@/views/MainPage.vue';

import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/login',
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/main',
    name: 'MainPage',
    component: MainPage,
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})
//在登录界面加载时就使用
export function resetRouter() {
  return new Promise(() => {
    let routeLists = JSON.parse(localStorage.getItem('routeLists'));
    if (routeLists !== null)
      routeLists.forEach(route => {
        if (router.resolve(route.link) != undefined) {
          router.removeRoute(route.pageName);
        }
      })
  })
}
export async function addRoutes() {
  let routeLists = JSON.parse(localStorage.getItem('routeLists'));

  if (routeLists == null)
    return
  routeLists.forEach(route => {
    router.addRoute(route.parent, {
      path: route.link,
      name: route.pageName,
      component: () => import('@/components/' + route.pageName)
    })
  })
}

router.beforeEach((to, from, next) => {
  const routeFlag = localStorage.getItem('routeFlag')
  const token = localStorage.getItem("isLogin");
  if (token) {
    if (routeFlag) {
      next();
    } else {
      localStorage.setItem('routeFlag', true);
      addRoutes();
      next('/home');
    }
  } else {
    if (to.path === "/login" || to.path === "/") {
      next();
    } else {
      next({ name: 'Login' });
    }
  }
})

export default router
