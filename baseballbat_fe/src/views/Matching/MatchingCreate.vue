<template>
  <div class="register-match-page-container" style="margin-top: 60px;">
    <h1>직관 매칭 등록하기</h1>

    <!-- 매칭 정보 입력 섹션 -->
    <section class="match-info-section">
      <h2>매칭 정보</h2>
      <form @submit.prevent="registerMatch" class="split-form">
        <div class="left-section">
          <div class="form-group">
            <label for="team">응원 팀</label>
            <select id="team" v-model="match.team" required>
              <option value="">응원할 팀을 선택하세요</option>
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
          </div>

          <div class="form-group">
            <label for="date">경기 날짜</label>
            <input type="date" id="date" v-model="match.date" required />
          </div>

          <div class="form-group">
            <label for="location">경기 장소</label>
            <select id="location" v-model="match.location" required>
              <option value="">경기 장소를 선택하세요</option>
              <option value="잠실 야구장">잠실 야구장</option>
              <option value="고척 스카이돔">고척 스카이돔</option>
              <option value="문학 야구장">문학 야구장</option>
              <option value="대전 한화생명이글스파크">대전한화생명이글스파크</option>
              <option value="사직 야구장">사직 야구장</option>
              <option value="광주 기아챔피언스필드">광주 기아챔피언스필드</option>
              <option value="대구 삼성라이온즈파크">대구 삼성라이온즈파크</option>
              <option value="창원 NC파크">창원 NC파크</option>
              <option value="수원 KT위즈파크">수원 KT위즈파크</option>
            </select>
          </div>

          <div class="form-group">
            <label for="totalSpots">모집 인원</label>
            <input type="text" id="totalSpots" v-model="match.totalSpots" required />
          </div>
        </div>

        <div class="right-section">
          <div class="form-group">
            <label for="title">게시물 제목</label>
            <input type="text" id="title" v-model="match.title" required />
          </div>

          <div class="form-group">
            <label for="description">게시글 내용</label>
            <textarea id="description" v-model="match.description" rows="8" required></textarea>
          </div>
        </div>
      </form>

      <!-- 등록 버튼 -->
      <div class="action-buttons">
        <button type="submit" class="register-button">등록하기</button>
        <button type="button" @click="cancelRegistration" class="cancel-button">취소</button>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  data() {
    return {
      match: {
        team: '',
        date: '',
        location: '',
        totalSpots: '',
        title: '',
        description: '',
      },
    };
  },
  methods: {
    registerMatch() {
      // 매칭 등록 로직
      console.log('매칭 등록:', this.match);
    },
    cancelRegistration() {
      // 등록 취소 시 이전 페이지로 이동
      this.$router.push('/matching');
    },
  },
};
</script>

<style scoped>
.register-match-page-container {
  max-width: 800px;
  margin: 100px auto 0 auto;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  background-color: #fff;
}

h1, h2 {
  text-align: center;
  margin-bottom: 20px;
}

.split-form {
  display: flex;
  justify-content: space-between;
}

.left-section,
.right-section {
  width: 48%;
}

.form-group {
  margin-bottom: 15px;
  display: flex;
  flex-direction: column;
}

label {
  margin-bottom: 5px;
  font-weight: bold;
}

input[type="text"],
input[type="date"],
input[type="number"],
textarea,
select {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

textarea {
  resize: vertical;
}

.action-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 20px;
}

.register-button {
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
  .register-match-page-container {
    padding: 10px;
  }
  .split-form {
    flex-direction: column;
  }
  .left-section,
  .right-section {
    width: 100%;
  }
  input[type="text"],
  input[type="date"],
  input[type="number"],
  textarea,
  select {
    font-size: 14px;
  }
}
</style>
