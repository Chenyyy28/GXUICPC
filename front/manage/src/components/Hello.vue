<template>
    <el-main class="main" @click="getmsg">{{ msg }}</el-main>
</template>
<script>
import { ref } from 'vue'

import axios from 'axios';
// import { url } from '@/assets/globalVar';
export default {
    setup() {
        const msg = ref("1111") 
        function getmsg() {
            axios.get('/url/hello')
                .then(data => {
                    msg.value = data.data;
                    console.log(data);
                })
                .catch(e => { })
            // console.log(msg.value);
        }
        return {
            msg,
            getmsg
        }
    }
}
</script>

<style scoped>
.main{
    background-color: #EEE
}
</style>
