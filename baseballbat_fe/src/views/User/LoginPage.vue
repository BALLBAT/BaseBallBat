// 로그인 페이지
// 기본 이이디 비밀번호로 로그인하고, 소셜 로그인 버튼 추가, 회원가입 버튼 필요
<template>
  <div class="login-container">
    <div class="login-section left-section">
      <h2>로그인</h2>
      <form @submit.prevent="login">
        <input type="text" v-model="username" placeholder="아이디" />
        <input type="password" v-model="password" placeholder="비밀번호" />
      </form>
            <div class="additional-options">
        <span @click="register" class="register-button option-text">회원가입</span>
        <span class="option-divider">|</span>
        <span @click="findId" class="find-button option-text">아이디 찾기</span>
        <span class="option-divider">|</span>
        <span @click="findPassword" class="find-button option-text">비밀번호 찾기</span>
      </div>
      <button @click="login" class="login-button">로그인</button>
    </div>

    <div class="login-section right-section">
      <h3>SNS 계정으로 로그인하기</h3>
      <button @click="loginWith('naver')" class="sns-button naver">Naver 계정으로 시작</button>
      <button @click="loginWith('kakao')" class="sns-button kakao">Kakao 계정으로 시작</button>
      <button @click="loginWith('google')" class="sns-button google">Google 계정으로 시작</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      password: '',
    };
  },
  methods: {
    login() {
      // 로그인 로직 처리
    },
    register() {
      // 회원가입 페이지로 이동
      this.$router.push("/register");
    },
    findId() {
      // 아이디 찾기 페이지로 이동
    },
    findPassword() {
      // 비밀번호 찾기 페이지로 이동
    },
    loginWith() {
      // SNS 로그인 처리
    },
  },
};
</script>

<style scoped>
/* 전체 로그인 컨테이너 */
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 70vh; /* 세로 길이 줄임 */
  padding: 20px;
  box-sizing: border-box;
  position: relative;
}

/* 공통 로그인 섹션 */
.login-section {
  width: 40%;
  max-width: 400px;
  padding: 20px;
  box-sizing: border-box;
}

/* 좌측 로그인 섹션 */
.left-section {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

/* 우측 로그인 섹션 */
.right-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

/* 아이디/패스워드 입력 폼 */
form {
  display: flex;
  flex-direction: column;
}

/* 아이디/패스워드 입력 칸 */
input {
  margin-bottom: 10px;
  padding: 10px;
  font-size: 16px;
}

/* 버튼 공통 속성 */
button {
  padding: 10px;
  margin-bottom: 10px;
  font-size: 16px;
  cursor: pointer;
}

/* 일반 로그인 버튼 속성 */
.login-button {
  font-size: 18px;
  color: #fff;
  background-color: #212529;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

/* 소셜 로그인 버튼 속성 */
.sns-button {
  width: 100%;
  max-width: 300px;
  margin-top: 10px;
}

/* 아이디 및 비밀번호 찾기, 회원가입 버튼 속성 */
.additional-options {
  display: flex;
  align-items: center;
  justify-content: center;
  /* margin-top: 10px; */
}

.option-text {
  cursor: pointer;
  text-decoration: underline;
  margin: 0 5px;
}

.option-divider {
  margin: 0 5px;
  color: #999;
}

/* 반응형 스타일 */
@media (max-width: 768px) {
  .login-container {
    flex-direction: column;
  }
  .login-section {
    width: 100%;
  }
  .sns-button {
    max-width: 100%;
  }
}
</style>
