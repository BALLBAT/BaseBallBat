<template>
  <div class="find-id-container">
    <div class="card">
      <h2 class="title">아이디 찾기</h2>
      <form @submit.prevent="sendVerification" class="verification-form">
        <label for="email" class="label">이메일 주소</label>
        <input type="email" id="email" v-model="email" placeholder="이메일 주소 입력" class="input-field" />
        <button type="submit" class="send-button">인증번호 전송</button>
      </form>
      <div v-if="verificationSent" class="verification-container">
        <label for="verificationCode" class="label">인증번호</label>
        <input type="text" id="verificationCode" v-model="verificationCode" placeholder="인증번호 입력" class="input-field" />
        <button @click="verifyCode" class="verify-button">인증하기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      verificationSent: false,
      verificationCode: '',
    };
  },
  methods: {
    sendVerification() {
      console.log('인증번호 전송:', this.email);
      this.verificationSent = true;
    },
    verifyCode() {
      console.log('인증번호 확인:', this.verificationCode);
      // 인증 성공 후 로직 추가 가능
    },
  },
};
</script>

<style scoped>
.find-id-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 70vh; /* 세로 크기 줄임 */
  padding: 20px;
}

.card {
  max-width: 700px; /* 가로 길이 늘림 */
  width: 100%;
  padding: 20px;
  background-color: #fff;
  border-radius: 15px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.title {
  text-align: center;
  margin-bottom: 20px;
  font-size: 28px;
  color: #333;
  font-weight: 700;
}

.verification-form,
.verification-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.label {
  font-weight: bold;
  color: #333;
}

.input-field {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 8px;
  transition: border-color 0.3s ease;
}

.input-field:focus {
  border-color: #007bff;
  outline: none;
}

.send-button,
.verify-button {
  padding: 12px;
  font-size: 16px;
  color: #fff;
  background-color: #28a745;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  font-weight: bold;
}

.send-button:hover,
.verify-button:hover {
  background-color: #218838;
}

.verification-container {
  margin-top: 0; /* 윗 마진 제거 */
}
</style>
