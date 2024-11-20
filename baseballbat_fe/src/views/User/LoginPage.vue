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
      <div id="naverIdLogin" class="sns-button naver-button"></div>
      <div id="kakao-login-btn" class="sns-button kakao-button"></div>
      <div id="g_id_onload"
           data-client_id="69828466810-cho87reh7vfoke4ef242tr6g610bsupj.apps.googleusercontent.com"
           data-callback="handleCredentialResponse"
           data-auto_select="false">
      </div>
      <div class="g_id_signin sns-button google-signin"
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
        size: 'large',
        width: '300px'
      }
    );

    // 네이버 로그인 초기화
    if (window.naver) {
      const naverLogin = new window.naver.LoginWithNaverId({
        clientId: '1SzX67SVz98SbWZaCDoK',
        callbackUrl: 'http://localhost:8080/naver/callback',
        isPopup: true,
        loginButton: { color: 'green', type: 3, height: '40' },
      });
      naverLogin.init();
      // 네이버 버튼 스타일 강제 설정
      const naverButton = document.getElementById('naverIdLogin');
      if (naverButton) {
        naverButton.style.width = '300px';
        naverButton.style.height = '40px';
      }
    } else {
      console.error('네이버 SDK 로드 오류: 스크립트가 제대로 로드되지 않았습니다.');
    }

    // 카카오 JavaScript SDK 초기화
    if (window.Kakao && !window.Kakao.isInitialized()) {
      window.Kakao.init('fd27adca5c2fbcb6e74f4786ebe260bb');
    }

    // 카카오 로그인 버튼 렌더링
    if (window.Kakao) {
      window.Kakao.Auth.createLoginButton({
        container: '#kakao-login-btn',
        success: (authObj) => {
          console.log('Kakao 로그인 성공:', authObj);
          window.Kakao.API.request({
            url: '/v2/user/me',
            success: (response) => {
              console.log('사용자 정보:', response);
            },
            fail: (error) => {
              console.error('사용자 정보 요청 실패:', error);
            }
          });
        },
        fail: (error) => {
          console.error('Kakao 로그인 실패:', error);
        }
      });
      // 카카오 버튼 스타일 강제 설정
      const kakaoButton = document.getElementById('kakao-login-btn');
      if (kakaoButton) {
        kakaoButton.style.width = '300px';
        kakaoButton.style.height = '40px';
      }
    }
  },
  methods: {
    login() {
      console.log('일반 로그인 시도:', this.username);
    },
    register() {
      this.$router.push("/register");
    },
    findId() {},
    findPassword() {},
    signInWithGoogle() {
      window.google.accounts.id.prompt();
    },
    verifyWithBackend(idToken) {
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
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 70vh;
  padding: 20px;
  box-sizing: border-box;
  position: relative;
}

.login-section {
  width: 40%;
  max-width: 400px;
  padding: 20px;
  box-sizing: border-box;
}

.left-section {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.right-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

form {
  display: flex;
  flex-direction: column;
}

input {
  margin-bottom: 10px;
  padding: 10px;
  font-size: 16px;
}

button {
  padding: 10px;
  margin-bottom: 10px;
  font-size: 16px;
  cursor: pointer;
}

.login-button {
  font-size: 18px;
  color: #fff;
  background-color: #212529;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.sns-button {
  width: 300px;
  margin-top: 10px;
  height: 40px;
}

.naver-button {
  width: 300px;
  height: 40px;
  margin-top: 10px;
}

.kakao-button {
  width: 300px;
  height: 40px;
  margin-top: 10px;
}

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
