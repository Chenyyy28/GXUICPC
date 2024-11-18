<template>
  <div class="common-layout">
    <el-container style="height: 100vh;">
      <Aside></Aside>
      <el-container style="display: block;">
        <Header></Header>
        <router-view />
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Aside from '@/components/Aside.vue';
import Header from '@/components/Header.vue';
import HomePage from '@/components/HomePage.vue';
import Main from '@/components/Hello.vue';
export default {
  components: {
    Aside,
    Header,
    Main,
    HomePage
  },
  setup() {

  },
}
</script>

<style scoped>
.common-layout {
  overflow: hidden;
  height: 100vh;
  width: 100vw;
}

.aside {
  /* height: 100vh; */
}

.header {
  /* background-color: blue; */
  /* display: block; */
}

.main {
  /* background-color: bisque; */
  height: 100%;
}
</style>