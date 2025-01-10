import { createStore } from 'vuex'

export default createStore({
  state: {
    isLoggedIn: false,
    authToken: null,
    username: null,
  },
  getters: {
    isLoggedIn: state => state.isLoggedIn,
    getUsername: state => state.username,
    getAuthToken: state => state.authToken,
  },
  mutations: {
    setLoginState(state, payload) {
      state.isLoggedIn = payload.isLoggedIn;
      state.authToken = payload.token;
      state.username = payload.username || null; // 소셜 로그인 시 username이 없을 수 있음
    },
    logout(state) {
      state.isLoggedIn = false;
      state.authToken = null;
      state.username = null;
    },
  },
  actions: {
    login({ commit }, { username, token }) {
      console.log("로그인 액션 호출됨:", { username, token }); // 디버깅용 로그
      localStorage.setItem("authToken", token);
      commit("setLoginState", {
        isLoggedIn: true,
        token,
        username,
      });
    },
    logout({ commit }) {
      console.log("로그아웃 액션 호출됨"); // 디버깅용 로그
      localStorage.removeItem("authToken");
      commit("logout");
    },
    checkLoginState({ commit }) {
      // document.cookie에서 JWT 추출
      const cookies = document.cookie.split(";").reduce((acc, cookie) => {
        const [key, value] = cookie.trim().split("=");
        acc[key] = value;
        return acc;
      }, {});
    
      const token = cookies.jwt; // JWT 쿠키 가져오기
    
      if (token) {
        console.log("JWT 쿠키 확인됨:", token); // 디버깅 로그
        commit("setLoginState", {
          isLoggedIn: true,
          token,
          username: null, // 필요 시 사용자 이름 추가
        });
      } else {
        console.log("JWT 쿠키 없음. 로그아웃 처리."); // 디버깅 로그
        commit("logout");
      }
    },
  },
  modules: {}
});
