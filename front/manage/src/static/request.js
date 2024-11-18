import router from "@/router";
import axios from "axios";
import { ElMessage } from "element-plus";

const config = axios.interceptors.request.use(function (config) {
    config.headers = {
        "user-token": localStorage.getItem("isLogin")
    }
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

axios.interceptors.response.use(response => {
    return response
}, err => {
    const code = err.response.status
    if (code === 403) {
        router.push("/login")
        ElMessage.error("登录信息失效，请重新登录")
    }
})
localStorage.setItem('routeFlag',false);
export default config