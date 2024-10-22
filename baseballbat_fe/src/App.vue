<template>
  <div id="app">
    <SiteHeader /> <!-- 헤더 추가 -->
    <router-view />
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
};
</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    // &.router-link-exact-active {
    //   color: #42b983;
    // }
  }
}

.site-footer {
  margin-top: auto; /* 푸터를 페이지 하단에 고정 */
}
</style>
