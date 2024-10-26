// 회원가입 페이지
<template>
  <div class="signup-container">
    <div class="signup-form">
      <h2>회원가입</h2>
      <p>회원이 되어 더 즐거운 야구를 즐겨보세요!</p>
      
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label>아이디</label>
          <div class="input-with-button">
            <input type="text" v-model="username" placeholder="아이디 입력 (6~20자)" />
            <button type="button" class="check-button">중복 확인</button>
          </div>
        </div>
        <div class="form-group">
          <label>비밀번호</label>
          <input type="password" v-model="password" placeholder="비밀번호 입력 (8~20자)" />
        </div>
        <div class="form-group">
          <label>비밀번호 확인</label>
          <input type="password" v-model="confirmPassword" placeholder="비밀번호 재입력" />
        </div>
        <div class="form-group">
          <label>이름</label>
          <input type="text" v-model="name" placeholder="이름을 입력해주세요" />
        </div>
        <div class="form-group">
          <label>전화번호</label>
          <input type="text" v-model="phoneNumber" placeholder="휴대폰 번호 입력 ('-' 제외 11자리 입력)" />
        </div>
        <div class="form-group">
          <label>이메일 주소</label>
          <div class="input-with-select">
            <input type="text" v-model="email" placeholder="이메일 주소" />
            <span>@</span>
            <select v-model="emailDomain">
              <option value="naver.com">naver.com</option>
              <option value="gmail.com">gmail.com</option>
              <option value="kakao.com">kakao.com</option>
            </select>
          </div>
        </div>
        <div class="form-group birthdate-group">
          <label>생년월일</label>
          <div class="birthdate-selects">
            <select v-model="birthYear">
              <option value="">년도</option>
              <option v-for="year in 100" :key="year" :value="2023 - year">{{ 2023 - year }}</option>
            </select>
            <select v-model="birthMonth">
              <option value="">월</option>
              <option v-for="month in 12" :key="month" :value="month">{{ month }}</option>
            </select>
            <select v-model="birthDay">
              <option value="">일</option>
              <option v-for="day in 31" :key="day" :value="day">{{ day }}</option>
            </select>
          </div>
        </div>
        <div class="form-actions">
          <button type="button" class="cancel-button" @click="registerCancel">가입취소</button>
          <button type="submit" class="submit-button">가입하기</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      password: '',
      confirmPassword: '',
      name: '',
      phoneNumber: '',
      email: '',
      emailDomain: '',
      birthYear: '',
      birthMonth: '',
      birthDay: '',
    };
  },
  methods: {
    submitForm() {
      // 회원가입 로직 진행
    },
    // 가입 취소 버튼
    registerCancel() {
        this.$router.push('/login');
    },
  },
};
</script>

<style scoped>
.signup-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 40px;
  height: 100vh;
}

.signup-form {
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  max-width: 500px;
  width: 100%;
}

h2 {
  margin-bottom: 10px;
}

p {
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 20px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
}

.input-with-button {
  display: flex;
  width: 100%;
}

.form-group input {
  flex: 1;
  padding: 10px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
  width: 100%;
  box-sizing: border-box;
}

.input-with-select {
  display: flex;
  align-items: center;
  width: 100%;
}

.input-with-select input {
  flex: 2;
  padding: 10px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.input-with-select select {
  flex: 1;
  padding: 10px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-left: 5px;
}

.check-button {
  margin-left: 10px;
  padding: 10px;
  background-color: #212529;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.birthdate-group {
  width: 100%;
}

.birthdate-selects {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.birthdate-selects select {
  width: 32%;
  padding: 10px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.form-actions {
  display: flex;
  justify-content: space-between;
}

.submit-button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.cancel-button {
  padding: 10px 20px;
  background-color: #ffc107;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

/* 반응형 스타일 */
@media (max-width: 768px) {
  .signup-form {
    padding: 20px;
  }

  .form-group {
    margin-bottom: 15px;
  }

  .input-with-button {
    flex-direction: column;
    align-items: stretch;
  }

  .check-button {
    margin: 10px 0 0;
  }

  .birthdate-selects {
    flex-direction: column;
  }

  .birthdate-selects select {
    width: 100%;
    margin-bottom: 10px;
  }

  .form-actions {
    flex-direction: column;
  }

  .form-actions button {
    width: 100%;
    margin-bottom: 10px;
  }
}
</style>
