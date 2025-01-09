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
        <span @click="handleFindId" class="find-button option-text">아이디 찾기</span>
        <span class="option-divider">|</span>
        <span @click="handleFindPassword" class="find-button option-text">비밀번호 찾기</span>
      </div>
    </div>

    <div class="login-section right-section">
      <h3>SNS 계정으로 로그인하기</h3>
      <!-- 네이버 로그인 버튼 -->
      <button class="sns-button naver-button" @click="handleNaverLogin">네이버 로그인</button>
      <!-- 카카오 로그인 버튼 -->
      <div id="kakao-login-btn" class="sns-button kakao-button"></div>
      <!-- 구글 로그인 버튼 -->
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
import LoginService from "@/services/auth/LoginService";
import { mapActions } from "vuex";
import axios from "axios";

export default {
  data() {
    return {
      username: "",
      password: "",
    };
  },
  mounted() {
    // 로그인 상태 확인
    const token = localStorage.getItem("authToken");
    if (token) {
      // 이미 로그인된 경우 메인 페이지로 이동
      this.$router.push("/main");
    }

    // URL에서 네이버 로그인 결과 확인
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get("code");
    const state = urlParams.get("state");

    if (code && state) {
      this.handleNaverCallback(code, state);
    }

    // Google Identity Services 초기화
    window.handleCredentialResponse = (response) => {
      console.log("Encoded JWT ID token: " + response.credential);
      this.verifyWithBackend(response.credential);
    };

    window.google.accounts.id.initialize({
      client_id: "69828466810-cho87reh7vfoke4ef242tr6g610bsupj.apps.googleusercontent.com",
      callback: this.handleCredentialResponse,
    });

    window.google.accounts.id.renderButton(
      document.querySelector(".g_id_signin"),
      {
        theme: "outline",
        size: "large",
        width: 222,
        height: 49,
      }
    );

    // 카카오 JavaScript SDK 초기화
    if (window.Kakao && !window.Kakao.isInitialized()) {
      window.Kakao.init("fd27adca5c2fbcb6e74f4786ebe260bb");
    }

    if (window.Kakao) {
      window.Kakao.Auth.createLoginButton({
        container: "#kakao-login-btn",
        success: (authObj) => {
          console.log("Kakao 로그인 성공:", authObj);
          window.Kakao.API.request({
            url: "/v2/user/me",
            success: (response) => {
              console.log("사용자 정보:", response);
            },
            fail: (error) => {
              console.error("사용자 정보 요청 실패:", error);
            },
          });
        },
        fail: (error) => {
          console.error("Kakao 로그인 실패:", error);
        },
      });
    }
  },
  methods: {
    ...mapActions(["login"]),

    async login() {
      try {
        console.log("일반 로그인 시도:", this.username, "비밀번호는?", this.password);

        // LoginService를 이용하여 로그인 요청
        const loginReq = {
          username: this.username,
          password: this.password,
        };

        const { username, token } = await LoginService.login(loginReq);

        // Vuex store에 로그인 상태 업데이트
        this.$store.dispatch("login", { username, token });

        console.log("로그인 성공:", username);
        this.$router.push("/main"); // 로그인 성공 시 메인 화면으로 이동
      } catch (error) {
        console.error("로그인 오류:", error);
        alert("로그인에 실패했습니다. 아이디와 비밀번호를 확인하세요.");
      }
    },

    register() {
      this.$router.push("/register");
    },

    handleFindId() {
      if (window.innerWidth > 768) {
        window.open("/find-id-popup", "아이디 찾기", "width=500,height=600");
      } else {
        this.$router.push("/find-id");
      }
    },

    handleFindPassword() {
      if (window.innerWidth > 768) {
        window.open("/find-password-popup", "비밀번호 찾기", "width=500,height=600");
      } else {
        this.$router.push("/find-password");
      }
    },

    // 네이버 로그인 버튼 클릭
    handleNaverLogin() {
      const clientId = "1SzX67SVz98SbWZaCDoK"; // 네이버 클라이언트 ID
      const redirectUri = encodeURIComponent("http://localhost:8000/api/naver/callback");
      const state = "randomStateValue"; // CSRF 방지를 위한 상태 값
      const naverLoginUrl = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${clientId}&redirect_uri=${redirectUri}&state=${state}`;
      window.location.href = naverLoginUrl;
    },

    // 네이버 로그인 콜백 처리
async handleNaverCallback(code, state) {
    try {
        const response = await axios.get(
            `http://localhost:8000/api/naver/callback?code=${code}&state=${state}`
        );

        const { token, needsAdditionalInfo, email, socialId, provider } = response.data;

        if (needsAdditionalInfo) {
            // 추가 정보 입력 페이지로 이동
            this.$router.push({
                name: 'additionalInfo',
                query: { email, socialId, provider }
            });
        } else {
            // JWT 저장
            localStorage.setItem("jwt", token);
            alert("로그인 성공!");
            this.$router.push("/main");
        }
    } catch (error) {
        console.error("네이버 로그인 처리 중 오류 발생:", error);
        alert("로그인 처리에 실패했습니다.");
    }
},

async saveAdditionalInfo(data) {
    try {
        const response = await axios.post("http://localhost:8000/api/user/additional-info", data);
        console.log("추가 정보 저장 응답:", response); // 로그 출력
        alert("추가 정보 저장 성공!");
        this.$router.push("/main");
    } catch (error) {
        console.error("추가 정보 저장 실패:", error);
        alert("추가 정보를 저장하는 중 문제가 발생했습니다.");
    }
},

    signInWithGoogle() {
      window.google.accounts.id.prompt();
    },

    verifyWithBackend(idToken) {
      fetch("http://localhost:8000/api/google-login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ idToken }),
      })
        .then((response) => response.json())
        .then((data) => {
          console.log("Server Response:", data);
          this.$router.push("/home");
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
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
  margin-top: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  box-sizing: border-box;
  width: 222px;
  height: 49px;
}

.g_id_signin .nsm7Bb-HzV7m-LgbsSe-MJoBVe {
  width: 222px !important;
  height: 49px !important;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.naver-button, .kakao-button {
  width: auto;
  height: auto;
}

.naver-button {
  background-color: #03c75a; /* 네이버의 대표 색상 */
  color: white;
  font-size: 16px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  padding: 10px 20px;
  text-align: center;
}

.naver-button:hover {
  background-color: #029b49; /* 호버 시 더 어두운 색상 */
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
