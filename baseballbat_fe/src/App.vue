<template>
  <div id="app">
    <SiteHeader v-if="$route.path !== '/'" />
    <div class="content-wrapper">
      <router-view />
    </div>
    <footer class="site-footer">
      <SiteFooter /> <!-- 푸터 추가 -->
    </footer>
  </div>
</template>

<script>
import SiteHeader from './components/Header.vue'; // 헤더 컴포넌트 임포트
import SiteFooter from './components/Footer.vue'; // 푸터 컴포넌트 임포트

export default {
  name: 'App',
  components: {
    SiteHeader, // 헤더 컴포넌트를 사용할 수 있도록 설정
    SiteFooter, // 푸터 컴포넌트를 사용할 수 있도록 설정
  },
  created() { console.log('App component created'); }
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
