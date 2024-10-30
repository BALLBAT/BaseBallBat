<template>
  <div class="profile-edit-container" style="margin-top: 60px; max-width: 600px;">
    <!-- 기본 정보 수정 섹션 -->
    <section class="basic-info-section">
      <h2>기본 정보 수정</h2>
      <form @submit.prevent="saveProfile">
        <div class="form-group">
          <label for="nickname" class="label-left">닉네임</label>
          <div class="input-with-button">
            <input type="text" id="nickname" v-model="profile.nickname" class="short-input" />
            <button type="button" class="edit-button">수정하기</button>
          </div>
        </div>
        <div class="form-group">
          <label for="email" class="label-left">이메일 주소</label>
          <div class="input-with-button">
            <input type="email" id="email" v-model="profile.email" class="short-input" />
            <button type="button" class="edit-button">수정하기</button>
          </div>
        </div>
        <div class="form-group">
          <label for="phone" class="label-left">전화번호</label>
          <div class="input-with-button">
            <input type="text" id="phone" v-model="profile.phone" class="short-input" />
            <button type="button" class="edit-button">수정하기</button>
          </div>
        </div>
        <div class="form-group">
          <label for="favorite-team" class="label-left">선호 구단</label>
          <div class="input-with-button">
            <select id="favorite-team" v-model="profile.favoriteTeam" class="short-input">
              <option value="">구단을 선택하세요</option>
              <option value="LG 트윈스">LG 트윈스</option>
              <option value="두산 베어스">두산 베어스</option>
              <option value="SSG 랜더스">SSG 랜더스</option>
              <option value="키움 히어로즈">키움 히어로즈</option>
              <option value="한화 이글스">한화 이글스</option>
              <option value="KIA 타이거즈">KIA 타이거즈</option>
              <option value="삼성 라이온즈">삼성 라이온즈</option>
              <option value="롯데 자이언츠">롯데 자이언츠</option>
              <option value="NC 다이노스">NC 다이노스</option>
              <option value="KT 위즈">KT 위즈</option>
            </select>
            <button type="button" class="edit-button">수정하기</button>
          </div>
        </div>
        <div class="form-group">
          <label for="address" class="label-left">주소 설정하기</label>
          <div class="input-with-button">
            <input type="text" id="address" v-model="profile.address" placeholder="주소를 입력하세요" class="short-input" />
            <button type="button" class="edit-button" @click="fetchAddress">주소 검색</button>
          </div>
          <input type="text" id="detail-address" v-model="profile.detailAddress" placeholder="상세 주소를 입력하세요" class="short-input" />
        </div>
      </form>
    </section>

    <!-- 비밀번호 변경 섹션 -->
    <section class="password-change-section">
      <h2>비밀번호 변경</h2>
      <div class="form-group password-group">
        <label for="new-password" class="label-left">새 비밀번호</label>
        <div class="input-with-button">
          <input type="password" id="new-password" v-model="profile.newPassword" class="short-input" />
        </div>
      </div>
      <div class="form-group password-group">
        <label for="confirm-password" class="label-left">새 비밀번호 확인</label>
        <div class="input-with-button">
          <input type="password" id="confirm-password" v-model="profile.confirmPassword" class="short-input" />
        </div>
      </div>
    </section>

    <!-- 저장 및 취소 버튼 -->
    <div class="action-buttons">
      <button @click="saveProfile" class="save-button">저장</button>
      <button @click="cancelEdit" class="cancel-button">취소</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      profile: {
        nickname: '길동이',
        email: 'user@example.com',
        phone: '010-1234-5678',
        newPassword: '',
        confirmPassword: '',
        favoriteTeam: '',
        address: '',
        detailAddress: '',
      },
    };
  },
  methods: {
    saveProfile() {
      // 프로필 저장 로직
      console.log('프로필 저장:', this.profile);
    },
    cancelEdit() {
      // 취소 시 이전 페이지로 이동
      this.$router.push('/mypage');
    },
    fetchAddress() {
      // 주소 설정 API 호출 로직
      console.log('주소 검색 호출');
      // 다음 주소 검색 API 사용 예시
      /* eslint-disable-next-line no-undef */
      new daum.Postcode({
        oncomplete: (data) => {
          this.profile.address = data.address;
        },
      }).open();
    },
  },
};
</script>

<style scoped>
.profile-edit-container {
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  background-color: #fff;
  margin-top: 60px; /* 헤더 높이만큼 마진 추가 */
}

h1, h2 {
  text-align: center;
  margin-bottom: 20px;
  font-size: 1.5rem;
}

.form-group {
  margin-bottom: 15px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.label-left {
  margin-bottom: 5px;
  font-weight: bold;
}

.input-with-button {
  display: flex;
  align-items: center;
  gap: 10px;
}

input[type="text"],
input[type="email"],
input[type="password"],
select {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
  flex: 1;
  width: 70%;
}

.short-input {
  width: 70%;
}

.edit-button {
  background-color: #28a745;
  color: #fff;
  border: none;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
}

.action-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 30px;
}

.save-button {
  background-color: #007bff;
  color: #fff;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.cancel-button {
  background-color: #6c757d;
  color: #fff;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

/* 반응형 스타일 */
@media (max-width: 768px) {
  .profile-edit-container {
    padding: 10px;
  }
  input[type="text"],
  input[type="email"],
  input[type="password"],
  select {
    font-size: 14px;
  }
  .short-input {
    width: 100%;
  }
  .form-group {
    flex-direction: column;
    align-items: flex-start;
  }
  .edit-button {
    align-self: flex-end;
  }
}
</style>
