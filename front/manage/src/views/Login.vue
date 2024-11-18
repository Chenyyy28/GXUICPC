<template>
    <div class="login-body">
        <div class="login-panel">
            <div class="login-title">用户登录</div>
            <el-form :model="formData" :rules="rules" ref="formDataRef">
                <el-form-item prop="account">
                    <el-input placeholder="请输入账号" v-model="formData.account" size="large" type="text">
                        <template #prefix>
                            <el-icon>
                                <i-ep-user />
                            </el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input placeholder="请输入密码" v-model="formData.password" size="large" type="password"
                        @keydown.enter="login()">
                        <template #prefix>
                            <el-icon>
                                <i-ep-lock />
                            </el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <!-- <el-form-item label="">
                    <div class="check-code-panel">
                        <el-input placeholder="请输入验证码" v-model="formData.checkCode" class="input-panel" />
                        <img src="checkCodeUrl" class="check-code">
                    </div>
                </el-form-item> -->
                <!-- <el-form-item label="">
                    <el-checkbox label="记住密码" name="type" />
                </el-form-item> -->
                <el-form-item label="">
                    <el-button type="primary" style="width: 100%;" @click="login()" size="large">登录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
​
<script>
import { ref, reactive } from 'vue'
// Element-Plus的依赖采用的是自动导入，所以这里无需显示的引入，其他地方的element-plus引用同理
import { ElMessage } from 'element-plus';
// import request from '@/utils/request';      //这里使用自行封装的axios，下文已给出，照搬后修改运行端口即可
import axios from 'axios';
import router, { resetRouter } from '@/router';

export default ({
    setup() {
        // const checkCodeUrl = "api/checkCode?" + new Date().getTime();
        //表单
        const formDataRef = ref();
        let formData = reactive({
            account: "",
            password: ""
        });
        const rules = {
            account: [{
                required: true,
                message: "请输入用户名"
            }],
            password: [{
                required: true,
                message: "请输入密码"
            }],
        }
        const login = () => {
            var form_obj = JSON.parse(JSON.stringify(formData));
            axios.post("/url/login", form_obj).then(res => res.data).then(res => {
                if (res.code === 200) {
                    ElMessage({
                        message: '登录成功',
                        type: 'success',
                    })
                    localStorage.setItem("isLogin", res.data.token);
                    localStorage.setItem("user", JSON.stringify(res.data.user));
                } else {
                    ElMessage.error('账号或密码错误！！！登录失败！！！')
                    throw new Error("error")
                }
            }).then(() => {
                axios.get("/url/route").then(res => res.data).then(data => {
                    return data.data;
                }).then((data) => {
                    localStorage.setItem('routeLists', JSON.stringify(data));
                    return data;
                }).then(() => {
                    router.push('/main');
                }).catch(err=>{})
            }).catch((e) => {
                // ElMessage.error("登录失败")
            });
        };
        
        return {
            formDataRef,
            formData,
            rules,
            login,
        }
    }, created() {
        resetRouter()
        localStorage.clear();
    }
})

</script>
​
<style scoped>
.login-body {
    background: url("../assets/bac.jpg") no-repeat center center;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: absolute;
    left: 0;
    top: 0;
}

.login-title {
    font-size: 22px;
    text-align: center;
    margin-bottom: 22px;
}

.login-panel {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    margin: auto;
    padding: 25px;
    width: 26%;
    min-width: 460px;
    height: 30%;
    min-height: 300px;
    background: rgba(255, 255, 255, 0.6);
    border-radius: 5%;
    box-shadow: 2px 2px 10px #ddd;
}
</style>