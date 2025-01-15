import { createStore } from 'vuex';
import axios from 'axios';

export default createStore({
  state: {
    isLoggedIn: false, // 로그인 상태 여부
    authToken: null,  // JWT 토큰 저장
    username: null,   // 사용자 이름
  },
  getters: {
    isLoggedIn: (state) => state.isLoggedIn,
    getUsername: (state) => state.username,
    getAuthToken: (state) => state.authToken,
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

      // JWT를 localStorage에 저장 (백업용, 필요 없으면 제거 가능)
      localStorage.setItem('authToken', token);

      // Vuex 상태 업데이트
      commit('setLoginState', {
        isLoggedIn: true,
        token,
        username,
      });
    },
    logout({ commit }) {
      console.log("로그아웃 액션 호출됨"); // 디버깅용 로그

      // 쿠키에서 JWT 제거
      document.cookie = "jwt=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";

      // Vuex 상태 초기화
      commit('logout');

      // localStorage에서 authToken 제거 (백업용)
      localStorage.removeItem('authToken');
    },
    checkLoginState({ commit }) {
      axios.get('http://localhost:8000/api/auth/validate-token', { withCredentials: true })
          .then(response => {
              console.log("JWT 유효성 확인 성공:", response.data);
              commit('setLoginState', {
                  isLoggedIn: true,
                  token: response.data.token, // 필요 시
                  username: response.data.username, // 필요 시
              });
          })
          .catch(error => {
              console.error("JWT 유효성 확인 실패:", error);
              commit('logout');
          });
  } ,
  },
  modules: {},
});
