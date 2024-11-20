<template>
  <div class="login-container">
    <div class="login-section left-section">
      <h2>로그인</h2>
      <form @submit.prevent="login">
        <input type="text" v-model="username" placeholder="아이디" />
        <input type="password" v-model="password" placeholder="비밀번호" />
        <button type="submit" class="login-button">로그인</button>
      </form>
      <div class="additional-options">
        <span @click="register" class="register-button option-text">회원가입</span>
        <span class="option-divider">|</span>
        <span @click="findId" class="find-button option-text">아이디 찾기</span>
        <span class="option-divider">|</span>
        <span @click="findPassword" class="find-button option-text">비밀번호 찾기</span>
      </div>
    </div>

    <div class="login-section right-section">
      <h3>SNS 계정으로 로그인하기</h3>
      <div id="naverIdLogin"></div>
      <button @click="loginWith('kakao')" class="sns-button kakao">Kakao 계정으로 시작</button>
      <div id="g_id_onload"
           data-client_id="69828466810-cho87reh7vfoke4ef242tr6g610bsupj.apps.googleusercontent.com"
           data-callback="handleCredentialResponse"
           data-auto_select="false">
      </div>
      <div class="g_id_signin"
           data-type="standard"
           data-shape="rectangular"
           data-theme="outline"
           data-text="signin_with"
           data-size="large"
           data-logo_alignment="left">
      </div>
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
mounted() {
  // Google Identity Services 스크립트가 로드된 후 호출되는 콜백
  window.handleCredentialResponse = (response) => {
    console.log('Encoded JWT ID token: ' + response.credential);

    // 이후 JWT 토큰을 백엔드로 전송하여 사용자 검증
    this.verifyWithBackend(response.credential);
  };

  // Google 계정 ID 초기화
  window.google.accounts.id.initialize({
    client_id: '69828466810-cho87reh7vfoke4ef242tr6g610bsupj.apps.googleusercontent.com',
    callback: this.handleCredentialResponse,
  });

  // Google 로그인 버튼을 렌더링
  window.google.accounts.id.renderButton(
    document.querySelector('.g_id_signin'),
    {
      theme: 'outline',
      size: 'large'
    }
  );

  // 네이버 로그인 버튼 초기화
  const naverScript = document.createElement('script');
  naverScript.src = "https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.0.js";
  naverScript.onload = () => {
    const naverLogin = new window.naver.LoginWithNaverId({
      clientId: '1SzX67SVz98SbWZaCDoK', // 발급받은 네이버 Client ID로 변경
      callbackUrl: 'http://localhost:8080/naver/callback',
      isPopup: true, // 팝업 형태로 로그인을 진행할지 여부
      loginButton: { color: 'green', type: 3, height: '40' },
    });
    naverLogin.init();
  };
  document.body.appendChild(naverScript);
},

  methods: {
    login() {
      // 로그인 로직 처리
      console.log('일반 로그인 시도:', this.username);
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
    signInWithGoogle() {
      // Google 소셜 로그인 처리
      window.google.accounts.id.initialize({
        client_id: '69828466810-cho87reh7vfoke4ef242tr6g610bsupj.apps.googleusercontent.com',
        callback: this.handleCredentialResponse
      });
      window.google.accounts.id.prompt();
    },
    loginWith(provider) {
      if (provider === 'kakao') {
        // 카카오 로그인 로직 (추후 추가 예정)
      }
    },
    verifyWithBackend(idToken) {
      // 구글에서 받은 JWT 토큰을 백엔드로 전송하여 사용자 인증 처리
      fetch('http://localhost:8080/api/google-login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ idToken }),
      })
      .then(response => response.json())
      .then(data => {
        console.log('Server Response:', data);
        // 인증 성공 후 홈 페이지로 리디렉션
        this.$router.push('/home');
      })
      .catch(error => {
        console.error('Error:', error);
      });
    }
  },
};
</script>

<style scoped>
/* 기존 스타일 그대로 사용 */
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
