<template>
  <el-main class="main">
    <div style="text-align: center; margin-bottom: 10px; position: relative">
      <el-input
        v-model="contestName"
        style="max-width: 600px"
        placeholder="Please input"
        class="input-with-select"
      >
        <template #append>
          <el-button icon="Search" @click="doSearch" />
        </template>
      </el-input>
      <el-button
        key
        type="primary"
        v-if="isAdmin"
        @click="
          dialogVisible = true;
          isAdd = true;
        "
        text
        bg
        style="margin-left: 10px"
      >
        +新增训练
      </el-button>
      <span style="margin-left: 10px">排名计算区间：</span>
      <el-date-picker
        style="margin-left: 10px"
        v-if="isAdmin"
        v-model="timeInterval"
        type="daterange"
        unlink-panels
        range-separator="|"
        start-placeholder="Start date"
        end-placeholder="End date"
        :shortcuts="shortcuts"
        :size="size"
        format="YYYY/MM/DD"
        value-format="x"
        @change="updateTime"
      />
      <span v-if="isAdmin == false">{{ start }} 到 </span>
      <span v-if="isAdmin == false">{{ end }}</span>
    </div>

    <el-dialog
      style="text-align: center"
      v-model="dialogVisible"
      title="输入用户信息"
      width="500"
      :before-close="handleClose"
    >
      <el-form
        style="max-width: 400px; margin: 0 auto"
        :model="contestForm"
        status-icon
        label-width="auto"
        class="demo-ruleForm"
      >
        <el-form-item label="训练描述" prop="description">
          <el-input v-model="contestForm.description" />
        </el-form-item>
        <el-form-item label="链接" prop="url">
          <el-input v-model="contestForm.url" />
        </el-form-item>
        <el-form-item label="训练面向人群" prop="roleId">
          <el-select
            v-model="contestForm.roleId"
            placeholder="请选择对象"
            style="width: 240px"
          >
            <el-option
              v-for="item in roleOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="拉题人/出题人" prop="author">
          <el-input v-model="contestForm.author" />
        </el-form-item>
        <el-form-item label="计算权重" prop="weight">
          <el-input v-model="contestForm.weight" />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            v-model="contestForm.startTime"
            type="datetime"
            placeholder="请选择开始时间"
            format="YYYY/MM/DD HH:mm:ss"
            value-format="x"
          />
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            v-model="contestForm.endTime"
            type="datetime"
            placeholder="请选择结束时间"
            format="YYYY/MM/DD HH:mm:ss"
            value-format="x"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cancelForm">取消</el-button>
          <el-button type="primary" @click="submit"> 提交 </el-button>
        </div>
      </template>
    </el-dialog>
    <el-dialog
      style="text-align: center"
      v-model="rankVisible"
      title="输入用户信息"
      width="500"
      :before-close="handleClose"
    >
      <el-form
        style="max-width: 400px; margin: 0 auto"
        :model="rankForm"
        status-icon
        label-width="auto"
        class="demo-ruleForm"
      >
        <el-form-item label="现场出题数" prop="raceNum">
          <el-input v-model="rankForm.raceNum" />
        </el-form-item>
        <el-form-item label="现场排名" prop="ranking">
          <el-input v-model="rankForm.ranking" />
        </el-form-item>
        <el-form-item label="补题数" prop="supplement">
          <el-input v-model="rankForm.supplement" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cancelForm">取消</el-button>
          <el-button type="primary" @click="submitRank"> 提交 </el-button>
        </div>
      </template>
    </el-dialog>
    <el-dialog
      style="text-align: center"
      v-model="contestInfoVisible"
      title="排名"
      width="500"
    >
      <el-table :data="rankInfo" style="width: 100%">
        <el-table-column prop="ranking" label="排名" width="100" />
        <el-table-column prop="username" label="姓名" width="100" />
        <el-table-column prop="raceNum" label="现场出题" />
        <el-table-column prop="supplement" label="补题" />
      </el-table>
      <!-- <template #footer>
                <div class="dialog-footer">
                    <el-button @click="cancelForm">取消</el-button>
                </div>
            </template> -->
    </el-dialog>
    <el-dialog
      style="text-align: center"
      v-model="noticeVis"
      title="输入邮件信息"
      width="500"
      :before-close="handleClose"
    >
      <el-form
        style="max-width: 400px; margin: 0 auto"
        :model="noticeForm"
        status-icon
        label-width="auto"
      >
        <el-form-item label="标题" prop="subject">
          <el-input v-model="noticeForm.subject" />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="noticeForm.content" type="textarea" :rows="12" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cancelForm">取消</el-button>
          <el-button type="primary" @click="noticeContest"> 提交 </el-button>
        </div>
      </template>
    </el-dialog>
    <el-table :data="state.tableData" stripe style="width: 100%">
      <el-table-column prop="description" label="训练描述" width="200" />
      <el-table-column prop="url" label="链接" />
      <el-table-column prop="author" label="作者" width="100" />
      <el-table-column prop="roleId" label="面向人群" width="100">
        <template #default="scope">
          <span v-if="scope.row.roleId == 1">现役</span>
          <span v-if="scope.row.roleId == 2">预备役</span>
          <span v-if="scope.row.roleId == -1">全部</span>
        </template>
      </el-table-column>
      <el-table-column prop="weight" label="计算权重" width="100" />
      <el-table-column
        prop="startTime"
        :formatter="dateFormat"
        label="开始时间"
        width="150"
      />
      <el-table-column
        prop="endTime"
        :formatter="dateFormat"
        label="结束时间"
        width="150"
      />
      <el-table-column
        prop="createTime"
        :formatter="dateFormat"
        label="创建时间"
        width="150"
      />

      <el-table-column label="操作" width="300">
        <template v-slot="scope">
          <el-button
            key
            type="success"
            text
            bg
            @click="beforeNoticeContest(scope.row)"
            v-if="isAdmin"
          >
            提醒
          </el-button>
          <el-button
            key
            type="success"
            text
            bg
            @click="queryRank(scope.row)"
            v-if="!isAdmin"
          >
            详情
          </el-button>
          <el-button
            key
            type="success"
            text
            bg
            @click="beforeUpdateContest(scope.row)"
            v-if="isAdmin"
          >
            编辑
          </el-button>
          <el-button
            key
            @click="deleteUser(scope.row.id)"
            type="danger"
            text
            bg
            v-if="isAdmin"
          >
            删除
          </el-button>
          <el-button
            key
            @click="addRankInfo(scope.row.id)"
            type="primary"
            text
            bg
            v-if="!isAdmin"
          >
            填写信息
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[100, 200, 300, 400]"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      style="margin-top: 10px"
    />
  </el-main>
</template>
<script>
import axios from "axios";
import { ElMessage, ElMessageBox } from "element-plus";
import moment from "moment";
import { reactive, ref, nextTick } from "vue";
// import { Search } from '@element-plus/icons-vue'
export default {
  setup() {
    let state = reactive({
      tableData: [],
    });
    let start = ref();
    let end = ref();
    const currentPage = ref(1);
    const pageSize = ref(100);
    const total = ref(1);
    const contestName = ref();
    const role = ref("-1");
    const dialogVisible = ref(false);
    const needPassword = ref(true);
    const isAdd = ref(false);
    const isAdmin = ref(true);
    const rankVisible = ref(false);
    const contestInfoVisible = ref(false);
    const noticeTxt = ref();
    const noticeVis = ref(false);
    const noticeForm = reactive({});
    let rankInfo = ref();
    let timeInterval = ref();
    let user = reactive({});
    const roleOptions = reactive([
      {
        value: 1,
        label: "现役",
      },
      {
        value: 2,
        label: "预备役",
      },
      {
        value: -1,
        label: "全部",
      },
    ]);
    let contestForm = reactive({
      id: 0,
      description: "",
      url: "",
      roleId: "",
      author: "",
      weight: "50",
      startTime: "",
      endTime: "",
    });
    let rankForm = reactive({
      contestId: "",
      userId: "",
      raceNum: "",
      ranking: "",
      supplement: "",
    });
    function dateFormat(row, column) {
      var date = row[column.property];
      if (date == undefined) {
        return "";
      }
      return moment(date).format("YYYY-MM-DD HH:mm:ss");
    }
    function handleSizeChange(val) {
      pageSize.value = val;
      nextTick(() => {
        doSearch();
      });
      // location.reload();
    }
    function handleCurrentChange(val) {
      currentPage.value = val;
      nextTick(() => {
        doSearch();
      });
    }
    const getContestLists = function () {
      user = JSON.parse(localStorage.getItem("user"));

      axios
        .get("/url/contest", {
          params: {
            currentPage: currentPage.value,
            pageSize: pageSize.value,
            contestName: contestName.value === "" ? null : contestName.value,
            roleId: user.roleId,
          },
        })
        .then((res) => res.data)
        .then((res) => res.data)
        .then((data) => {
          state.tableData = data.list;
          total.value = data.total;
        })
        .catch((err) => {
          console.log(err);

          ElMessage.error("用户列表获取失败");
        });
    };
    function doSearch() {
      nextTick(() => {
        getContestLists();
      });
    }
    const handleClose = (done) => {
      done();
      resetContestForm();
    };
    function resetContestForm() {
      contestForm.id = 0;
      contestForm.description = "";
      contestForm.url = "";
      contestForm.roleId = "";
      contestForm.weight = "50";
      contestForm.startTime = "";
      contestForm.endTime = "";
    }
    function submit() {
      dialogVisible.value = false;
      if (isAdd.value == true) addContest();
      else {
        updateContest();
      }
    }
    function addContest() {
      axios
        .post("/url/contest", contestForm)
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
          console.log(err);
          ElMessage.error("添加失败");
        })
        .finally(() => {
          resetContestForm();
        });
    }
    function deleteUser(id) {
      ElMessageBox.confirm("危险操作，确认删除吗", "danger", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          axios
            .delete("/url/contest", {
              params: {
                id: id,
              },
            })
            .then((res) => res.data)
            .then((res) => res.data)
            .then((data) => {
              ElMessage({
                type: "error",
                message: "删除成功",
              });
              location.reload();
            })
            .catch((err) => {
              ElMessage({
                type: "info",
                message: "取消成功",
              });
            });
        })
        .catch(() => {
          ElMessage({
            type: "info",
            message: "取消成功",
          });
        });
    }
    function copyProperties(contest) {
      contestForm.id = contest.id;
      contestForm.description = contest.description;
      contestForm.url = contest.url;
      contestForm.roleId = contest.roleId;
      contestForm.author = contest.author;
      contestForm.weight = contest.weight;
      contestForm.startTime = contest.startTime;
      contestForm.endTime = contest.endTime;
    }
    function beforeUpdateContest(contest) {
      dialogVisible.value = true;
      isAdd.value = false;
      copyProperties(contest);
    }
    function updateContest() {
      axios
        .put("/url/contest", contestForm)
        .then((res) => res.data)
        .then((res) => {
          if (res.code == 200) {
            ElMessage.success("修改成功");
            location.reload();
          } else {
            ElMessage.error("修改失败");
          }
        })
        .catch((err) => {
          console.log(err);
          ElMessage.error("修改失败");
        })
        .finally(() => {
          resetContestForm();
        });
    }
    function addRankInfo(contestId) {
      rankForm.contestId = contestId;
      rankForm.userId = user.id;
      rankVisible.value = true;
    }
    function submitRank() {
      axios
        .post("/url/rank", rankForm)
        .then((res) => res.data)
        .then((res) => {
          if (res.code == 200) {
            ElMessage.success("添加成功");
            // location.reload();
          } else {
            ElMessage.error("添加失败");
          }
        })
        .catch((e) => {
          ElMessage.error("添加失败");
        });
      rankVisible.value = false;
      resetRankForm();
    }
    function resetRankForm() {
      rankForm.ranking = "";
      rankForm.raceNum = "";
      rankForm.supplement = "";
    }
    function cancelForm() {
      resetContestForm();
      resetRankForm();
      dialogVisible.value = false;
      rankVisible.value = false;
    }
    function updateTime() {
      axios
        .put("/url/time", timeInterval.value)
        .then((res) => res.data)
        .then((res) => {
          if (res.code == 200) {
            ElMessage.success("修改时间区间成功");
          } else {
            ElMessage.error("修改时间区间失败");
          }
        })
        .catch((e) => {
          ElMessage.error("修改时间区间失败");
        });
    }
    function getTime() {
      axios
        .get("/url/time")
        .then((res) => res.data)
        .then((res) => {
          this.timeInterval = res.data;
          this.start = new Date(this.timeInterval[0]).toLocaleDateString();
          this.end = new Date(this.timeInterval[1]).toLocaleDateString();
        })
        .catch((e) => {
          console.log(e);
        });
    }
    function queryRank(contest) {
      contestInfoVisible.value = true;
      axios
        .get("/url/rank/info", {
          params: {
            contestId: contest.id,
          },
        })
        .then((res) => res.data)
        .then((res) => {
          if (res.code == 200) {
            this.rankInfo = res.data;
          }
        })
        .catch((e) => {
          console.log(e);
        });
    }
    function beforeNoticeContest(contest) {
      noticeVis.value = true;
      noticeForm.contestId = contest.id;
      console.log(noticeForm);
    }
    function noticeContest() {
      axios
        .post("/url/contest/notice", noticeForm)
        .then((res) => res.data)
        .then((res) => {
          if (res.code == 200) {
            ElMessage.success("提醒成功");
            noticeVis.value = false;
          } else {
            ElMessage.error("提醒失败");
          }
        })
        .catch((err) => {
          console.log(err);
          ElMessage.error("提醒失败");
        });
    }
    return {
      state,
      dateFormat,
      currentPage,
      pageSize,
      contestName,
      role,
      total,
      handleSizeChange,
      handleCurrentChange,
      getContestLists,
      doSearch,
      dialogVisible,
      handleClose,
      contestForm,
      submit,
      resetContestForm,
      needPassword,
      deleteUser,
      updateContest,
      beforeUpdateContest,
      cancelForm,
      isAdd,
      isAdmin,
      rankVisible,
      rankForm,
      resetRankForm,
      roleOptions,
      user,
      submitRank,
      addRankInfo,
      timeInterval,
      updateTime,
      getTime,
      start,
      end,
      rankInfo,
      contestInfoVisible,
      queryRank,
      noticeContest,
      beforeNoticeContest,
      noticeTxt,
      noticeVis,
      noticeForm,
    };
  },
  beforeCreate() {
    this.user = JSON.parse(localStorage.getItem("user"));
    if (this.user.roleId == 0) this.isAdmin = true;
    else this.isAdmin = false;
    this.getContestLists();
    this.getTime();
  },
};
</script>

<style s>
.main {
  background-color: #eee;
}

.mainBody {
  background-color: white;
  width: 70%;
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

.name {
  width: -100px;
  text-align: center;
  margin-top: 20px;
}

.gexing {
  font-family: STXingkai, "华正行楷";
}

.input-with-select .el-input-group__prepend {
  background-color: var(--el-fill-color-blank);
}
</style>
