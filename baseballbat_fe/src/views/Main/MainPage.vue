<template>
  <div class="main-container">
    <!-- Hero Section -->
    <section class="hero-section">
      <h1>직관적으로, 야구를 더 쉽게 즐기세요!</h1>
      <p>야구 입문자를 위한 최고의 가이드</p>
      <button @click="navigateTo('login')">지금 가입하기</button>
    </section>

    <!-- 주요 기능 Section -->
    <section class="features-section">
      <div class="feature">
        <h2>직관 같이 보기</h2>
        <p>야구 직관을 위한 동행자를 찾아보세요.</p>
      </div>
      <div class="feature">
        <h2>중고 거래</h2>
        <p>응원용품 및 유니폼을 사고 팔아보세요.</p>
      </div>
      <div class="feature">
        <h2>맛집 리스트</h2>
        <p>구장 주변의 맛집을 추천해드립니다.</p>
      </div>
    </section>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      isLoggedIn: false,
      userName: "",
    };
  },
  mounted() {
    const token = localStorage.getItem("jwt");
    if (!token) {
      this.isLoggedIn = false;
      return;
    }

    // JWT 검증 및 사용자 정보 가져오기
    this.validateToken(token);
  },
  methods: {
    navigateTo(route) {
      this.$router.push(`/${route}`);
    },
        async validateToken(token) {
      try {
        const response = await axios.get("http://localhost:8000/api/auth/validate", {
          headers: { Authorization: `Bearer ${token}` },
        });
        this.isLoggedIn = true;
        this.userName = response.data.realName; // 사용자 이름을 설정
      } catch (error) {
        console.error("JWT 검증 실패:", error);
        this.isLoggedIn = false;
        localStorage.removeItem("jwt");
        this.$router.push("/login");
      }
    },
    logout() {
      localStorage.removeItem("jwt");
      this.isLoggedIn = false;
      alert("로그아웃되었습니다.");
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
.main-container {
  padding: 0;
}

.hero-section {
  text-align: center;
  padding: 50px;
  background-color: #f0f0f0;
  width: 100%;
  box-sizing: border-box;
}

.hero-section h1 {
  font-size: 2.5rem;
  margin-bottom: 10px;
}

.hero-section p {
  font-size: 1.2rem;
  margin-bottom: 20px;
}

.hero-section button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.features-section {
  display: flex;
  justify-content: space-evenly;
  margin: 40px 0;
  flex-wrap: wrap;
}

.feature {
  width: 20%;
  text-align: center;
  margin-bottom: 20px;
  padding: 20px;
  border: 1px solid #ccc;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  transition: transform 0.3s ease;
}

.feature:hover {
  transform: translateY(-5px);
}

.feature h2 {
  font-size: 1.5rem;
  margin-bottom: 10px;
}

/* 반응형 스타일 */
@media (max-width: 768px) {
  .features-section {
    flex-direction: column;
    align-items: center;
  }

  .feature {
    width: 80%;
    margin-bottom: 20px;
  }
}
</style>
