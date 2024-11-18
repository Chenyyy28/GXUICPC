import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import axios from 'axios'
import router, { addRoutes } from './router'
import store from './store'
import config from './static/request'
import moment from "moment";
// import { ElCollapseTransition } from 'element-plus'
// // fade/zoom
// import 'element-plus/lib/theme-chalk/base.css'

import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// axios.create({
//     baseURL:''
// }

const app = createApp(App)
const boot = async () => {

    await addRoutes();
    app.use(ElementPlus).use(store).use(router).mount('#app')
}
boot()
// app.mount('#app')
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
// app.component(ElCollapseTransition.name, ElCollapseTransition)

const debounce = (fn, delay) => {
    let timer
    return (...args) => {
        if (timer) {
            clearTimeout(timer)
        }
        timer = setTimeout(() => {
            fn(...args)
        }, delay)
    }
}
const _ResizeObserver = window.ResizeObserver;
window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
    constructor(callback) {
        callback = debounce(callback, 200);
        super(callback);
    }
}
window.__VUE_PROD_DEVTOOLS__ = false;
window.__VUE_PROD_HYDRATION_MISMATCH_DETAILS__ = false;
// createApp(App).use(store).use(router).use(ElementPlus).mount('#app')
