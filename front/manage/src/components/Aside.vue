<template>
  <el-aside :style="{ width: asideWidth + 'px' }" style="overflow: hidden" class="aside">
    <el-radio-group
      v-model="isCollapse"
      style="margin-bottom: 10px; width: 70px; overflow: hidden"
    >
      <!-- <el-radio-button @click="open" v-show="isCollapse" :value="false">
                <el-icon>
                    <Open />
                </el-icon>
            </el-radio-button>
            <el-radio-button @click="close" v-show="!isCollapse" :value="true">
                <el-icon>
                    <TurnOff />
                </el-icon>
            </el-radio-button> -->
    </el-radio-group>
    <el-menu
      :default-active="activePath"
      active-text-color="#409EFF"
      :collapse="isCollapse"
      @open="open"
      @close="close"
      :collapse-transition="false"
      style="width: 100%; height: 100%; border: 0"
    >
      <!-- <p>YEs</p> -->
      <template v-for="item in asideState.routes" :key="item.id">
        <router-link :to="item.link">
          <el-menu-item @click="changeActivePath(item.id)" :index="item.id + ''">
            <el-icon>
              <component :is="item.icon" />
            </el-icon>
            <template #title>{{ item.name }}</template>
          </el-menu-item>
        </router-link>
      </template>
    </el-menu>
  </el-aside>
</template>
<script>
// import { resolve } from 'core-js/fn/promise';
import { ref, reactive } from "vue";
export default {
  setup() {
    let isCollapse = ref(true);
    let asideWidth = ref(70);
    let asideState = reactive({
      routes: [],
    });
    let activePath = ref("1");
    function close() {
      // asideState.routes;
      asideWidth.value = 70;
    }
    function open() {
      asideWidth.value = 200;
    }
    function getRoutes() {
      asideState.routes = JSON.parse(localStorage.getItem("routeLists"));
    }
    function changeActivePath(id) {
      localStorage.setItem("active", id);
    }
    return {
      isCollapse,
      asideWidth,
      asideState,
      open,
      close,
      // getMenu,
      getRoutes,
      activePath,
      changeActivePath,
    };
  },
  beforeCreate: function () {
    this.getRoutes();

    if (localStorage.getItem("active") !== null)
      this.activePath = localStorage.getItem("active");
    else this.activePath = "1";
  },
};
</script>
<style scoped>
.aside {
  /* background-color: #a1a3a6; */
}

/* .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 260px;
    height: 100%;

} */
/* .el-menu-item.is-active {
  background-color: #409eff!important;
} */
</style>
