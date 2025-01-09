<template>
  <div class="additional-info-container">
    <h2>추가 정보 입력</h2>
    <form @submit.prevent="submitAdditionalInfo">
      <input
        type="text"
        v-model="form.email"
        placeholder="이메일"
        disabled
        required
      />
      <input
        type="text"
        v-model="form.realname"
        placeholder="실명"
        required
      />
      <input
        type="text"
        v-model="form.username"
        placeholder="사용자명"
        required
      />
      <button type="submit" class="submit-button">저장</button>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      form: {
        email: "",
        realname: "",
        username: "",
        socialId: "",
        provider: "",
      },
    };
  },
  mounted() {
    // URL에서 전달된 추가 정보 데이터 가져오기
    const { email, socialId, provider } = this.$route.query;

    if (email && socialId && provider) {
      this.form.email = email;
      this.form.socialId = socialId;
      this.form.provider = provider;
    } else {
      alert("잘못된 접근입니다.");
      this.$router.push("/login"); // 로그인 페이지로 리다이렉트
    }
  },
  methods: {
    async submitAdditionalInfo() {
      try {
        // 추가 정보 저장 API 호출
        const response = await axios.post("/api/naver/additional-info", this.form);

        if (response.data.success) {
          alert("추가 정보가 성공적으로 저장되었습니다.");
          this.$router.push("/main"); // 메인 페이지로 이동
        } else {
          alert("추가 정보 저장에 실패했습니다.");
        }
      } catch (error) {
        console.error("추가 정보 저장 중 오류:", error);
        alert("추가 정보를 저장하는 도중 오류가 발생했습니다.");
      }
    },
  },
};
</script>

<style scoped>
.additional-info-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 10px;
  background: #f9f9f9;
}

form {
  display: flex;
  flex-direction: column;
}

input {
  margin-bottom: 15px;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

button {
  padding: 10px;
  font-size: 16px;
  color: #fff;
  background-color: #212529;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #333;
}
</style>