<template>
    <el-main class="main">
        <div class="mainBody">
            <div style="width: 80%;margin:0 auto;position:absolute;top:5%;left:10%;">
                <h1 style="text-align:center;width:100%;margin-bottom:20px">预备役成员排名</h1>
                <div style="margin:10px auto; width:100%;text-align:center">
                    <span style="margin-left: 10px;">排名计算区间：</span>
                    <span>{{ start }} 到 </span>
                    <span>{{ end }}</span>
                </div>
                <el-table :data="rankInfo" style="width: 100%">
                    <el-table-column prop="ranking" label="排名" width="100" />
                    <el-table-column prop="username" label="姓名" width="100" />
                    <el-table-column prop="score" label="总得分" width="100" />
                    <el-table-column prop="codeforces" label="codeforces" sortable :sort-method="sortByCF" />
                    <el-table-column prop="luogu" label="luogu" sortable :sort-method="sortByLG" />
                    <el-table-column prop="vjudge" label="vjudge" sortable :sort-method="sortByVJ" />
                    <el-table-column prop="nowcoder" label="nowcoder" sortable :sort-method="sortByNK" />
                </el-table>
            </div>
        </div>
    </el-main>
</template>
<script>
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { reactive, ref } from 'vue';


export default {
    setup() {
        let rankInfo = ref([])
        let time = ref();
        let start = ref()
        let end = ref()
        function getInfo() {
            axios.get('/url/rank', {
                params: {
                    role: 'prepare'
                }
            }).then(res => res.data)
                .then(res => {
                    if (res.code == 200) {
                        this.rankInfo = res.data;
                        // console.log(rankInfo);
                    } else {
                        ElMessage.error("获取排名列表失败")
                    }
                }).catch(e => {
                    ElMessage.error("获取排名列表失败")
                })
        }
        function sortByCF(x, y) {
            return y.codeforces - x.codeforces;
        }
        function sortByLG(x, y) {
            return y.luogu - x.luogu;
        }
        function sortByVJ(x, y) {
            return y.vjudge - x.vjudge;
        }
        function sortByNK(x, y) {
            return y.nowcoder - x.nowcoder;
        }
        function getTime() {
            axios.get('/url/time')
                .then(res => res.data)
                .then(res => {
                    if (res.code == 200)
                        this.timeInterval = res.data;
                    this.start = new Date(this.timeInterval[0]).toLocaleDateString();
                    this.end = new Date(this.timeInterval[1]).toLocaleDateString();
                })
                .catch(e => { })
        }
        return {
            rankInfo,
            getInfo,
            time,
            getTime,
            sortByCF,
            sortByLG,
            sortByVJ,
            sortByNK,
            start,
            end,
        }
    },
    created() {
        this.getInfo();
        this.getTime();
    }
}
</script>

<style scoped>
.main {
    background-color: #EEE
}

.mainBody {
    background-color: white;
    width: 70%;
    min-width: 700px;
    height: 100%;
    margin: 0 auto;
    position: relative;
    overflow: auto;
}

.myBody {
    position: absolute;
    left: 10%;
    top: 10%;
    width: 80%;
    height: 90%;
}
</style>
