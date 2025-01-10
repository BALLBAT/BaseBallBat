<template>
  <div id="app">
    <SiteHeader v-if="$route.path !== '/' && !['/find-id', '/find-id-popup', '/find-password', '/find-password-popup'].includes($route.path)" />
    <div class="content-wrapper">
      <router-view />
    </div>
    <footer class="site-footer" v-if="!['/find-id', '/find-id-popup', '/find-password', '/find-password-popup'].includes($route.path)">
      <SiteFooter /> <!-- 푸터 추가 -->
    </footer>
  </div>
</template>

<script>
import SiteHeader from './components/Header.vue'; // 헤더 컴포넌트 임포트
import SiteFooter from './components/Footer.vue'; // 푸터 컴포넌트 임포트

import LoginService from './services/auth/LoginService'; // 로그인 상태 확인

import { mapActions } from "vuex";

export default {
  name: 'App',
  components: {
    SiteHeader, // 헤더 컴포넌트를 사용할 수 있도록 설정
    SiteFooter, // 푸터 컴포넌트를 사용할 수 있도록 설정
  },
  methods: {
        ...mapActions(["checkLoginState"]),
  },
  mounted() {
        // this.checkL/oginState(); // 애플리케이션 초기화 시 로그인 상태 확인
  },
  created() {
    console.log('App component created');

    // 애플리케이션 시작 시 Vuex의 checkLoginState 호출
    
    // App이 처음 시작될 때 로그인 상태를 체크합니다.
    const token = localStorage.getItem('authToken');
    if (token) {
      // 로그인 서비스에서 로그인 상태를 유지합니다.
      LoginService.setAuthToken(token);
    }
    }
};
</script>

<style lang="scss">
#app {
  font-family: 'KBO-Dia-Gothic', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.content-wrapper {
  flex: 1;
  width: 100%;
  padding-top: 60px; /* 헤더 높이만큼 패딩 추가 */
}

.site-footer {
  margin-top: auto; /* 푸터를 페이지 하단에 고정 */
}

@font-face {
  font-family: 'KBO-Dia-Gothic';
  src: url('@/assets/fonts/KBO-Dia-Gothic_medium.woff') format('woff');
}

body {
  font-family: 'KBO-Dia-Gothic', sans-serif;
}
</style>
