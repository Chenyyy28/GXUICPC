<template>
  <el-main class="main">
    <div class="mainBody">
      <div class="left">
        <div>
          <div>
            <el-avatar
              style="display: block; margin: 0 auto; margin-top: 10%"
              :size="200"
            />
          </div>
          <div
            style="
              position: relative;
              width: 200px;
              margin: 0 auto;
              margin-top: 20px;
              text-align: center;
              border-bottom: solid black 1px;
            "
          >
            <el-icon style="position: absolute; left: 0">
              <UserFilled />
            </el-icon>
            {{ user.username }}
          </div>
          <div
            style="
              position: relative;
              width: 200px;
              margin: 0 auto;
              margin-top: 20px;
              text-align: center;
              border-bottom: solid black 1px;
            "
          >
            <el-icon style="position: absolute; left: 0">
              <Message />
            </el-icon>
            {{ user.email }}
          </div>
          <div
            style="
              position: relative;
              width: 200px;
              margin: 0 auto;
              margin-top: 20px;
              text-align: center;
              border-bottom: solid black 1px;
            "
          >
            <!-- <el-icon style="position: absolute;left:0">
                            <Message />
                        </el-icon> -->

            {{
              user.roleId == "0"
                ? "管理员"
                : user.roleId == "1"
                ? "现役"
                : user.roleId == "2"
                ? "退役"
                : "预备役"
            }}
          </div>
          <div
            v-if="isAdmin == false"
            style="
              position: relative;
              width: 200px;
              margin: 0 auto;
              margin-top: 20px;
              text-align: center;
              border-bottom: solid black 1px;
            "
          >
            <!-- <el-icon style="position: absolute;left:0">
                            <Message />
                        </el-icon> -->
            排名：
            {{ user.ranking }}
          </div>
          <div
            v-if="isAdmin == false"
            style="
              position: relative;
              width: 200px;
              margin: 0 auto;
              margin-top: 20px;
              text-align: center;
            "
          >
            <el-button type="primary" @click="pwdVis = true">修改密码</el-button>
          </div>
        </div>
      </div>
      <el-dialog style="text-align: center" v-model="pwdVis" title="修改密码" width="500">
        <el-form
          style="max-width: 400px; margin: 0 auto"
          :model="pwdForm"
          status-icon
          label-width="auto"
          class="demo-ruleForm"
        >
          <el-form-item label="原密码" prop="prev">
            <el-input v-model="pwdForm.prev" />
          </el-form-item>
          <el-form-item label="新密码" prop="now">
            <el-input v-model="pwdForm.now" />
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="submitPwd"> 提交 </el-button>
          </div>
        </template>
      </el-dialog>
      <div class="right">
        <div class="text"></div>

        <div
          style="
            height: 60%;
            min-height: 60px;
            text-align: center;
            margin-top: 10%;
            overflow: auto;
          "
        >
          <h2>未补题记录</h2>
          <div style="margin-bottom: 10px; margin-top: 10px; position: relative">
            <el-input
              v-model="contestName"
              style="max-width: 400px; width: 50%"
              placeholder="Please input"
              class="input-with-select"
            >
              <template #append>
                <el-button icon="Search" @click="getContest" />
              </template>
            </el-input>
            <el-button
              key
              type="primary"
              @click="
                dialogVisible = true;
                needPassword = false;
              "
              text
              bg
              style="margin-left: 10px"
            >
              +新增补题
            </el-button>
          </div>

          <el-dialog
            style="text-align: center"
            v-model="dialogVisible"
            title="输入补题信息"
            width="500"
          >
            <el-form
              style="max-width: 400px; margin: 0 auto"
              :model="contestForm"
              status-icon
              label-width="auto"
              class="demo-ruleForm"
            >
              <el-form-item label="场次名" prop="contestName">
                <el-input v-model="contestForm.contestName" />
              </el-form-item>
              <el-form-item label="链接" prop="url">
                <el-input v-model="contestForm.url" />
              </el-form-item>
            </el-form>
            <template #footer>
              <div class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="submit"> 提交 </el-button>
              </div>
            </template>
          </el-dialog>
          <el-table :data="state.tableData" stripe style="width: 100%">
            <el-table-column prop="contestName" label="场次名" width="300" />
            <el-table-column prop="url" label="链接" />
            <el-table-column label="操作" width="100">
              <template v-slot="scope">
                <el-button key @click="deleteContest(scope.row.id)" type="danger" text bg>
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[5, 10]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            style="margin-top: 10px"
          />
        </div>
        <div
          style="
            height: 10%;
            width: 100%;
            min-height: 60px;
            text-align: center;
            margin: 0 auto;
            margin-bottom: 5%;
            position: absolute;
            bottom: 10%;
          "
        >
          <div>
            <h2
              style="
                margin-bottom: 10px;
                margin-right: 10px;
                display: inline-block;
                text-align: center;
              "
            >
              各网站刷题量
            </h2>
            <el-button
              key
              type="primary"
              @click="acountInfoVisible = true"
              text
              bg
              style="margin-left: auto"
            >
              管理账号信息
            </el-button>
          </div>
          <el-dialog
            style="text-align: center"
            v-model="acountInfoVisible"
            title="账号信息"
            width="500"
          >
            <el-form
              style="max-width: 400px; margin: 0 auto"
              :model="acountInfoData"
              status-icon
              label-width="auto"
            >
              <el-form-item label="codeforces" :prop="codeforces">
                <el-input v-model="acountInfoData.codeforces" />
              </el-form-item>
              <el-form-item label="洛谷" prop="luogu">
                <el-input v-model="acountInfoData.luogu" />
              </el-form-item>
              <el-form-item label="牛客" prop="nowcoder">
                <el-input v-model="acountInfoData.nowcoder" />
              </el-form-item>
              <el-form-item label="vjudge" prop="vjudge">
                <el-input v-model="acountInfoData.vjudge" />
              </el-form-item>
            </el-form>
            <template #footer>
              <div class="dialog-footer">
                <el-button @click="acountInfoVisible = false">取消</el-button>
                <el-button type="primary" @click="submitAccountInfo"> 提交 </el-button>
              </div>
            </template>
          </el-dialog>
          <div class="website">
            <div style="height: 40%; width: 100%">codeforces</div>
            <div>{{ problemAmount.cfProblem }}</div>
          </div>
          <div class="website">
            <div style="height: 40%; width: 100%">luogu</div>
            <div>{{ problemAmount.luoguProblem }}</div>
          </div>
          <div class="website">
            <div style="height: 40%; width: 100%">vjudge</div>
            <div>{{ problemAmount.vjudgeProblem }}</div>
          </div>
          <div class="website">
            <div style="height: 40%; width: 100%">牛客</div>
            <div>{{ problemAmount.nkProblem }}</div>
          </div>
        </div>
      </div>
    </div>
  </el-main>
</template>
<script>
import axios from "axios";
import { ElMessage, ElMessageBox } from "element-plus";
import { nextTick, reactive, ref } from "vue";

export default {
  setup() {
    let dialogVisible = ref(false);
    const currentPage = ref(1);
    const pageSize = ref(5);
    const total = ref("");
    const contestName = ref("");
    const acountInfoVisible = ref(false);
    const pwdVis = ref(false);
    const pwdForm = reactive({
      prev: "",
      now: "",
    });
    let isAdmin = ref(false);
    const acountInfoData = reactive({
      codeforces: "",
      luogu: "",
      nowcoder: "",
      vjudge: "",
    });
    let problemAmount = reactive({
      cfProblem: "0",
      luoguProblem: "0",
      vjudgeProblem: "0",
      nkProblem: "0",
    });
    let user = reactive({});
    const contestForm = reactive({
      contestName: "",
      url: "",
    });
    let state = reactive({
      tableData: [],
    });
    function getContest() {
      axios
        .get("/url/uncontested", {
          params: {
            userId: user.id,
            currentPage: currentPage.value,
            pageSize: pageSize.value,
            contestName: contestName.value === "" ? null : contestName.value,
          },
        })
        .then((res) => res.data)
        .then((res) => res.data)
        .then((res) => {
          state.tableData = res.list;
          total.value = res.total;
        })
        .catch((err) => {
          console.log(err);
          ElMessage.error("获取补题列表失败");
        });
    }
    function handleSizeChange(val) {
      pageSize.value = val;
      nextTick(() => {
        getContest();
      });
      // location.reload();
    }
    function handleCurrentChange(val) {
      currentPage.value = val;
      nextTick(() => {
        getContest();
      });
      // location.reload();
    }
    function getUser() {
      return new Promise(() => {
        user = JSON.parse(localStorage.getItem("user"));
      });
    }
    function submit() {
      dialogVisible.value = false;
      addContest();
    }
    function addContest() {
      axios
        .post("/url/uncontested", contestForm)
        .then((res) => res.data)
        .then((res) => {
          if (res.code == 200) {
            ElMessage.success("添加成功");
            location.reload();
          } else {
            ElMessage.error("添加失败");
          }
        })
        .catch((err) => {
          ElMessage.error("添加失败");
        });
    }
    function deleteContest(id) {
      ElMessageBox.confirm("危险操作，确认删除吗", "danger", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          console.log(id);
          axios
            .delete("/url/uncontested", {
              params: {
                id: id,
              },
            })
            .then((res) => res.data)
            .then((res) => {
              if (res.code == 200) {
                ElMessage.success("删除成功");
                location.reload();
              } else {
                ElMessage.error("删除失败");
              }
            })
            .catch((err) => {
              ElMessage.error("删除失败");
            });
        })
        .catch((e) => {
          ElMessage({
            type: "info",
            message: "取消成功",
          });
        });
    }
    function getProblemAmount() {
      axios
        .get("/url/problem")
        .then((res) => res.data)
        .then((res) => {
          if (res.code == 200) {
            if (res.data !== null) this.problemAmount = res.data;
          } else {
            ElMessage.error("获取题量失败");
          }
        })
        .catch((err) => {
          ElMessage.error("获取题量失败");
        });
    }
    function getAcountInfoData() {
      axios
        .get("/url/account")
        .then((res) => res.data)
        .then((res) => {
          if (res.code == 200) {
            this.acountInfoData.codeforces = res.data.codeforces;
            this.acountInfoData.luogu = res.data.luogu;
            this.acountInfoData.nowcoder = res.data.nowcoder;
            this.acountInfoData.vjudge = res.data.vjudge;
          } else {
            ElMessage.error("获取账号失败");
          }
        })
        .catch((err) => {
          ElMessage.error("获取账号失败");
        });
    }
    function submitAccountInfo() {
      axios
        .post("/url/account", acountInfoData)
        .then((res) => res.data)
        .then((res) => {
          if (res.code == 200) {
            acountInfoVisible.value = false;
            location.reload();
            ElMessage.success(" 修改账号成功");
          } else {
            ElMessage.error("修改账号失败");
          }
        })
        .catch((err) => {
          console.log(err);
          ElMessage.error("修改账号失败");
        });
    }
    function submitPwd() {
      axios
        .put("/url/user/password", pwdForm)
        .then((res) => res.data)
        .then((res) => {
          if (res.code == 400) {
            ElMessage.error("原密码错误");
          } else {
            ElMessage.success("修改成功");
            pwdVis.value = false;
          }
        })
        .catch((err) => {
          console.log(err);
          ElMessage.error("发生未知错误");
        });
    }
    return {
      contestForm,
      state,
      dialogVisible,
      currentPage,
      pageSize,
      total,
      contestName,
      user,
      getContest,
      getUser,
      handleSizeChange,
      handleCurrentChange,
      submit,
      deleteContest,
      problemAmount,
      getProblemAmount,
      acountInfoVisible,
      acountInfoData,
      getAcountInfoData,
      submitAccountInfo,
      isAdmin,
      pwdVis,
      pwdForm,
      submitPwd,
    };
  },
  created() {
    this.getAcountInfoData();
    this.getProblemAmount();
    this.getUser();
    this.user = JSON.parse(localStorage.getItem("user"));
    if (this.user.roleId == 0) this.isAdmin = true;
    else this.isAdmin = false;
    this.getContest();
  },
};
</script>

<style scoped>
.main {
  background-color: #eee;
}

.mainBody {
  background-color: white;
  width: 70%;
  min-width: 700px;
  height: 100%;
  margin: 0 auto;
  position: relative;
}

.myBody {
  position: absolute;
  left: 10%;
  top: 10%;
  width: 80%;
  height: 90%;
}

.left {
  float: left;
  position: relative;
  place-items: center;
  display: grid;
  left: 0;
  top: -10%;
  width: 30%;
  height: 100%;
}

.right {
  float: right;
  position: relative;
  right: 0;
  width: 70%;
  height: 100%;
}

.grid-content {
  background-color: black;
  padding-left: 0;
  padding-right: 0;
}

.website {
  display: inline-block;
  height: 100%;
  width: 15%;
  background-color: aliceblue;
  min-width: 70px;
  margin-right: 50px;
  border-radius: 10px;
}

.website:last-child {
  margin-right: 0;
}
</style>
