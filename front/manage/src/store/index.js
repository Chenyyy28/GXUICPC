import { addRoutes } from '@/router';
import { createStore } from 'vuex'

const store = createStore({
  state: {
    dynamicRouter: ""
  },
  getters: {
  },
  mutations: {
    setDynamicRouter(state, dynamicRouter) {
      console.log("store: " + JSON.parse(dynamicRouter));
      state.dynamicRouter = dynamicRouter;

    },
    getDynamicRouter(state) {
      console.log("okokook11111");
      return state.dynamicRouter;
    },
    activeRouter(state) {
      addRoutes();
    }
  },
  actions: {

  },
  modules: {
  }
})
export default store;