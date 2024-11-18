<template>
  <el-main class="main">
    <div style="text-align: center; margin-bottom: 10px; position: relative">
      <el-input
        v-model="username"
        style="max-width: 600px"
        placeholder="Please input"
        class="input-with-select"
      >
        <template #prepend>
          <el-select v-model="role" placeholder="选择用户权限" style="width: 115px">
            <el-option label="全部成员" value="-1" />
            <el-option label="管理员" value="0" />
            <el-option label="现役成员" value="1" />
            <el-option label="退役成员" value="2" />
          </el-select>
        </template>
        <template #append>
          <el-button icon="Search" @click="doSearch" />
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
        +新增用户
      </el-button>
      <el-upload
        action=""
        :limit="1"
        :headers="headers"
        style="float: right"
        :show-file-list="true"
        :http-request="uploadData"
      >
        <el-button type="primary">点击上传数据</el-button>
      </el-upload>
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
        :model="userForm"
        status-icon
        label-width="auto"
        class="demo-ruleForm"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userForm.username" />
        </el-form-item>
        <el-form-item label="账号" prop="account">
          <el-input v-model="userForm.account" />
        </el-form-item>
        <el-form-item label="密码" prop="password" v-show="needPassword">
          <el-input v-model="userForm.password" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="userForm.email" />
        </el-form-item>
        <el-form-item label="权限" prop="roleId">
          <el-select
            v-model="userForm.roleId"
            placeholder="请选择权限"
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
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cancelForm">取消</el-button>
          <el-button type="primary" @click="submit"> 提交 </el-button>
        </div>
      </template>
    </el-dialog>
    <el-table :data="state.tableData" stripe style="width: 100%">
      <el-table-column prop="id" label="ID" width="50" />
      <el-table-column prop="username" label="姓名" width="150" />
      <el-table-column prop="account" label="账号" width="150" />
      <el-table-column prop="password" label="密码" />
      <el-table-column prop="email" label="邮箱" width="250" />
      <el-table-column prop="ranking" label="排名" />
      <el-table-column prop="roleId" label="权限" />
      <el-table-column prop="createTime" label="创建时间" :formatter="dateFormat" />
      <el-table-column prop="updateTime" label="修改时间" :formatter="dateFormat" />
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button key type="success" text bg @click="beforeUpdateUser(scope.row)">
            编辑
          </el-button>
          <el-button key @click="deleteUser(scope.row.id)" type="danger" text bg>
            删除
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
    const currentPage = ref(1);
    const pageSize = ref(100);
    const total = ref(1);
    const username = ref();
    const role = ref("-1");
    const dialogVisible = ref(false);
    const needPassword = ref(true);
    const fileList = ref([]);
    const headers = ref({ "Content-Type": "multipart/form-data" });
    const fileType = ref(["xls", "xlsx"]);
    let userForm = reactive({
      id: "",
      account: "",
      username: "",
      password: "",
      email: "",
      ranking: "",
      roleId: "",
    });
    let roleOptions = reactive([
      {
        value: 0,
        label: "管理员",
      },
      {
        value: 1,
        label: "现役成员",
      },
      {
        value: 2,
        label: "退役成员",
      },
      {
        value: 3,
        label: "预备役",
      },
    ]);
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
      // location.reload();
    }
    const getUserLists = function () {
      axios
        .get("/url/user", {
          params: {
            currentPage: currentPage.value,
            pageSize: pageSize.value,
            role: role.value == -1 ? null : role.value,
            username: username.value === "" ? null : username.value,
          },
        })
        .then((res) => res.data)
        .then((res) => res.data)
        .then((data) => {
          console.log(data);
          state.tableData = data.list;
          total.value = data.total;
        })
        .catch((err) => {
          console.log(err);
          ElMessage.error("用户列表获取失败");
        });
    };
    const doSearch = () => {
      nextTick(() => {
        getUserLists();
      });
    };
    const handleClose = (done) => {
      ElMessageBox.confirm("确定取消吗，可能会丢失输入的内容噢", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
      })
        .then(() => {
          done();
          ElMessage({
            type: "success",
            message: "返回成功",
          });
          resetUserForm();
        })
        .catch((e) => {
          console.log(e);
        });
    };
    function resetUserForm() {
      userForm.username = "";
      userForm.account = "";
      userForm.password = "";
      userForm.email = "";
      userForm.ranking = "";
      userForm.roleId = "";
    }
    function submit() {
      dialogVisible.value = false;
      if (needPassword.value == false) addUser();
      else {
        updateUser();
      }
    }
    function addUser() {
      axios
        .post("/url/user", userForm)
        .then((res) => res.data)
        .then((res) => {
          if (res.code == 200) {
            ElMessage.success("添加成功");
            location.reload();
          } else {
            ElMessage.success("添加失败");
          }
        })
        .catch((err) => {
          console.log(err);
          ElMessage.success("添加失败");
        })
        .finally(() => {
          resetUserForm();
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
            .delete("/url/user", {
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
                message: "删除成功",
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
    function copyProperties(user) {
      userForm.id = user.id;
      userForm.username = user.username;
      userForm.account = user.account;
      userForm.password = user.password;
      userForm.email = user.email;
      userForm.ranking = user.ranking;
      userForm.roleId = user.roleId;
    }
    function beforeUpdateUser(user) {
      dialogVisible.value = true;
      needPassword.value = true;
      copyProperties(user);
    }
    function updateUser() {
      axios
        .put("/url/user", userForm)
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
          resetUserForm();
        });
    }
    function cancelForm() {
      resetUserForm();

      dialogVisible.value = false;
    }
    function uploadData(item) {
      ElMessage.info("文件上传中");
      let param = new FormData();
      param.append("user-data", item.file);
      let config = {
        headers: { "Content-Type": "multipart/form-data" },
      };
      axios
        .post("/url/user/upload", param, config)
        .then((res) => res.data)
        .then((res) => {
          if (res.code == 200) {
            ElMessage.success("导入成功");
            location.reload();
          } else {
            ElMessage.error("导入失败");
          }
        })
        .catch((err) => {
          ElMessage.error("导入失败");
        });
    }
    function beforeUpload(file) {
      if (file.type != "" || file.type != null || file.type != undefined) {
        //截取文件的后缀，判断文件类型
        const FileExt = file.name.replace(/.+\./, "").toLowerCase();
        //计算文件的大小
        const isLt5M = file.size / 1024 / 1024 < 50; //这里做文件大小限制
        //如果大于50M
        if (!isLt5M) {
          ElMessage.error("上传文件大小不能超过 50MB!");
          return false;
        }
        //如果文件类型不在允许上传的范围内
        if (this.fileType.includes(FileExt)) {
          return true;
        } else {
          ElMessage.error("上传文件格式不正确!");
          return false;
        }
      }
    }
    return {
      state,
      dateFormat,
      currentPage,
      pageSize,
      username,
      role,
      total,
      handleSizeChange,
      handleCurrentChange,
      getUserLists,
      doSearch,
      dialogVisible,
      handleClose,
      userForm,
      submit,
      resetUserForm,
      roleOptions,
      needPassword,
      deleteUser,
      updateUser,
      beforeUpdateUser,
      cancelForm,
      fileList,
      uploadData,
      headers,
      fileType,
      beforeUpload,
    };
  },
  beforeCreate() {
    this.getUserLists();
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
