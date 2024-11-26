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
            <button type="button" class="check-button" @click="checkUsername">중복 확인</button>
          </div>
          <div v-if="isUsernameAvailable === true" class="available-message">
            사용 가능한 아이디입니다.
          </div>
          <div v-if="isUsernameAvailable === false" class="unavailable-message">
            이미 사용 중인 아이디입니다.
          </div>
        </div>
        <div class="form-group">
          <label>비밀번호</label>
          <input type="password" v-model="password" placeholder="비밀번호 입력 (8~20자)" />
        </div>
        <div class="form-group">
          <label>비밀번호 확인</label>
          <input type="password" v-model="confirmPassword" placeholder="비밀번호 재입력" @input="checkPasswordsMatch" />
          <div v-if="passwordMismatch" class="password-mismatch-message">
            비밀번호가 다릅니다.
          </div>
        </div>
        <div class="form-group">
          <label>이름</label>
          <input type="text" v-model="realname" placeholder="이름을 입력해주세요" />
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
            <select v-model="emailDomain" @change="emailDomainInput = ''">
              <option value="naver.com">naver.com</option>
              <option value="gmail.com">gmail.com</option>
              <option value="kakao.com">kakao.com</option>
              <option value="">직접 입력</option>
            </select>
            <input v-if="emailDomain === ''" type="text" v-model="emailDomainInput" placeholder="직접 입력" />
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
import UserService from "@/services/user/UserService";

export default {
  data() {
    return {
      username: '',
      password: '',
      confirmPassword: '',
      realname: '',
      phoneNumber: '',
      email: '',
      emailDomain: 'naver.com', // 기본 이메일 도메인 설정
      emailDomainInput: '', // 사용자가 직접 입력할 이메일 도메인
      birthYear: '',
      birthMonth: '',
      birthDay: '',
      isUsernameAvailable: null,  // 아이디 사용 가능 여부 상태
    };
  },
  watch: {
    // 사용자가 아이디 입력 시 중복 확인 상태 초기화
    username() {
      this.isUsernameAvailable = null;
    },
    // 사용자가 비밀번호 또는 비밀번호 확인을 입력할 때 비밀번호 불일치 상태 업데이트
    password() {
      this.checkPasswordsMatch();
    },
    confirmPassword() {
      this.checkPasswordsMatch();
    }
  },
  methods: {
    async submitForm() {
      // 유효성 검사
      if (this.password !== this.confirmPassword) {
        alert('비밀번호가 일치하지 않습니다.');
        return;
      }

      if (!this.validatePhoneNumber(this.phoneNumber)) {
        alert('전화번호 형식이 올바르지 않습니다.');
        return;
      }

      // 이메일과 이메일 도메인을 결합하여 완전한 이메일 주소를 생성
      const fullEmail = `${this.email}@${this.emailDomain || this.emailDomainInput}`;

      // 생년월일 형식 생성 (예: 1990-12-31)
      const birthMonthPadded = String(this.birthMonth).padStart(2, '0');
      const birthDayPadded = String(this.birthDay).padStart(2, '0');
      const birthdate = `${this.birthYear}-${birthMonthPadded}-${birthDayPadded}`;

      // 회원가입 요청에 필요한 데이터 구성
      const user = {
        username: this.username,
        password: this.password,
        realname: this.realname,
        phoneNumber: this.phoneNumber,
        email: fullEmail,
        birthdate: birthdate,
      };

      try {
        // UserService를 이용하여 회원가입 API 호출
        const registeredUser = await UserService.registerUser(user);
        console.log('등록된 사용자:', registeredUser);
        alert('회원가입이 완료되었습니다!');
        this.$router.push('/login'); // 회원가입이 완료되면 로그인 페이지로 이동
        this.resetForm();  // 폼 데이터 초기화
      } catch (error) {
        alert('회원가입에 실패했습니다. 다시 시도해주세요.');
        console.error('회원가입 오류:', error);
        console.log("어떤 오류가 있을까요 :::?" + error)
      }
    },

    // 아이디 중복 확인 버튼 클릭 시 호출되는 메서드
    async checkUsername() {
      if (!this.username) {
        alert('아이디를 입력해주세요.');
        return;
      }

      try {
        const isAvailable = await UserService.checkUsernameAvailability(this.username);
        if (isAvailable) {
          this.isUsernameAvailable = true;
        } else {
          this.isUsernameAvailable = false;
        }
      } catch (error) {
        console.log("어떤 에러인지 볼까~~~요" + error);
        // alert('아이디 중복 확인에 실패했습니다. 다시 시도해주세요.');
        this.isUsernameAvailable = null;  // 실패 시 상태 초기화
      }
    },

    // 가입 취소 버튼
    registerCancel() {
      this.$router.push('/login');
    },

    // 폼 데이터 초기화
    resetForm() {
      this.username = '';
      this.password = '';
      this.confirmPassword = '';
      this.realname = '';
      this.phoneNumber = '';
      this.email = '';
      this.emailDomain = 'naver.com';
      this.emailDomainInput = '';
      this.birthYear = '';
      this.birthMonth = '';
      this.birthDay = '';
      this.isUsernameAvailable = null;
    },

    // 전화번호 유효성 검사 (예: 01012345678 형식)
    validatePhoneNumber(phoneNumber) {
      const phoneRegex = /^\d{11}$/;
      return phoneRegex.test(phoneNumber);
    },
    // 비밀번호와 비밀번호 확인이 일치하는지 검사
    checkPasswordsMatch() {
      this.passwordMismatch = this.password !== this.confirmPassword;
    }
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

.available-message {
  color: green;
  font-size: 14px;
}

.unavailable-message {
  color: red;
  font-size: 14px;
}

.password-mismatch-message {
  color: red;
  font-size: 14px;
  margin-top: 5px;
}
</style>
