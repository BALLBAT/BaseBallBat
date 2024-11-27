<template>
  <header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark custom-navbar">
      <div class="container-fluid">
        <div class="d-flex align-items-center justify-content-center w-100">
          <!-- Logo / Website Name -->
          <span class="navbar-brand mx-auto">직관적으로</span>
          <!-- Hamburger Button for Smaller Screens -->
          <button
            class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span class="navbar-toggler-icon"></span>
          </button>
        </div>
        <!-- Navigation Links -->
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav mx-auto d-flex align-items-center">
            <li class="nav-item">
              <router-link class="nav-link" to="/main">홈</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/matching">매칭</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/trade">중고거래</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/restaurant">맛집 정보</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/teaminfo">구장 정보</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/mypage">마이 페이지</router-link>
            </li>
            <li class="nav-item">
              <!-- 로그인 상태에 따른 버튼 변경 -->
              <button v-if="!isLoggedIn" class="btn btn-success" @click="login">로그인</button>
              <button v-else class="btn btn-danger" @click="logout">로그아웃</button>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </header>
</template>

<script>
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import { mapGetters } from 'vuex';

export default {
  name: "SiteHeader",
  computed: {
    ...mapGetters(['isLoggedIn']), // Vuex의 isLoggedIn getter를 사용하여 로그인 상태를 가져옵니다.
  },
  methods: {
    // 로그인 버튼 클릭시 로그인 페이지 이동
    login() {
      this.$router.push('/login');
    },
    // 로그아웃 버튼 클릭시 로그아웃 처리
    logout() {
      // 로그아웃 상태를 Vuex store를 통해 반영
      this.$store.dispatch('logout');
      
      // 로그아웃 후 로그인 페이지로 이동
      setTimeout(() => {
        this.$router.push('/login');
      }, 100);
    },
  },
};
</script>

<style scoped>
.custom-navbar {
  height: 60px;
  display: flex;
  align-items: center;
  position: fixed; /* 네비게이션 바 고정 */
  width: 100%;
  top: 0;
  z-index: 1000; /* 콘텐츠보다 앞에 배치되도록 설정 */
}

.container-fluid {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-left: 15px;
  padding-right: 15px;
}

.navbar-brand {
  font-weight: bold;
  font-size: 1.5rem;
}

.navbar-nav {
  gap: 15px;
  display: flex;
  align-items: center;
}

.navbar-nav .nav-item {
  white-space: nowrap; /* 텍스트가 두 줄로 나오는 것을 방지 */
}

.navbar-toggler {
  border: none;
}

.collapse {
  background-color: #212529;
  padding: 0; /* 드롭다운 메뉴의 패딩 제거 */
  position: absolute;
  top: 100%; /* 헤더 밑에 붙여서 나오도록 설정 */
  left: 0;
  width: 100%; /* 드롭다운 메뉴의 너비를 헤더와 동일하게 설정 */
  z-index: 1;
}

.nav-link {
  color: #fff;
  padding: 10px 15px;
}

.btn-success {
  padding: 5px 15px;
}

.btn-danger {
  padding: 5px 15px;
}

@media (max-width: 992px) {
  .navbar-collapse {
    width: 100%;
    justify-content: flex-start;
    margin-top: 0;
  }
}
</style>
