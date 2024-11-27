import { createStore } from 'vuex'

export default createStore({
  state: {
    isLoggedIn: false,
    authToken: null,
    username: null,
  },
  getters: {
    isLoggedIn: state => state.isLoggedIn,
  },
  mutations: {
    setLoginState(state, payload) {
      state.isLoggedIn = payload.isLoggedIn;
      state.authToken = payload.token;
      state.username = payload.username;
    },
    logout(state) {
      state.isLoggedIn = false;
      state.authToken = null;
      state.username = null;
    },
  },
  actions: {
    login({ commit }, { username, token }) {
      localStorage.setItem('authToken', token);
      commit('setLoginState', {
        isLoggedIn: true,
        token,
        username,
      });
    },
    logout({ commit }) {
      localStorage.removeItem('authToken');
      commit('logout');
    },
  },
  modules: {
  }
})
