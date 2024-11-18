<template>
    <el-header class="header">
        <span class="title">
            欢迎来到GXU-ICPC集训队管理系统
        </span>
        <span class="menu">
            {{ username }}
            <el-dropdown style="margin-top: 20px;margin-left: 5px;">
                <span class="el-dropdown-link">
                    <el-icon size="20px">
                        <Setting />
                    </el-icon>
                </span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item @click="exit">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </span>
    </el-header>
</template>
<script>
import router from '@/router';
import { ref } from 'vue';

export default ({
    setup() {
        const username = ref('');
        function exit() {
            router.push('/login');
            router.routeFlag = false;
        }
        return {
            username,
            exit
        }
    }, created() {
        this.username = JSON.parse(localStorage.getItem('user')).username;
    }
})
</script>
<style scoped>
.header {
    background-color: #C2DDE6;
    position: relative;
}

.header .title {
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    line-height: 60px;
    font-size: 30px;
    text-align: center;
}

.header .menu {
    position: absolute;
    right: 5%;
    line-height: 60px;
    border: 0;
}

.el-dropdown-link:focus {
    outline: none;
}
</style>
