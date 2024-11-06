<template>
  <div class="matching-register-sidebar">
    <h2>매칭 등록하기</h2>
    <form @submit.prevent="registerMatch">
      <div class="form-group">
        <label for="team">응원 구단 선택</label>
        <select id="team" v-model="newMatch.team">
          <option value="">구단을 선택하세요</option>
          <option value="LG 트윈스">LG 트윈스</option>
          <option value="두산 베어스">두산 베어스</option>
          <option value="SSG 래드스">SSG 래드스</option>
          <option value="키우미 히에로즈">키우미 히에로즈</option>
          <option value="한화 이글스">한화 이글스</option>
          <option value="KIA 타이거즈">KIA 타이거즈</option>
          <option value="삼성 라이온즈">삼성 라이온즈</option>
          <option value="로딤 자이언츠">로딤 자이언츠</option>
          <option value="NC 다이놈스">NC 다이놈스</option>
          <option value="KT 위즈">KT 위즈</option>
        </select>
      </div>
      <div class="form-group">
        <label for="date">경기 날짜 선택</label>
        <input type="date" id="date" v-model="newMatch.date" />
      </div>
      <div class="form-group">
        <label for="location">경기장 선택</label>
        <select id="location" v-model="newMatch.location">
          <option value="">경기장을 선택하세요</option>
          <option value="잠실 야구장">잠실 야구장</option>
          <option value="문학 야구장">문학 야구장</option>
          <option value="고척 스카이돔">고척 스카이돔</option>
          <option value="대전 한화생명이글스파크">대전 한화생명이글스파크</option>
          <option value="광주-기아 챔피언스 필드">광주-기아 챔피언스 필드</option>
          <option value="대구 삼성 라이온즈 파크">대구 삼성 라이온즈 파크</option>
          <option value="부산 사직 야구장">부산 사직 야구장</option>
          <option value="창원 NC 파크">창원 NC 파크</option>
          <option value="수원 KT 위즈 파크">수원 KT 위즈 파크</option>
        </select>
      </div>
      <div class="form-group">
        <label for="totalSpots">모집 인원</label>
        <input type="number" id="totalSpots" v-model="newMatch.totalSpots" min="1" />
      </div>
      <div class="form-group action-buttons">
        <button type="submit" class="register-button">등록하기</button>
        <button type="button" @click="$emit('close-sidebar')" class="close-button">닫기</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      newMatch: {
        team: '',
        date: '',
        location: '',
        totalSpots: 1,
      },
    };
  },
  methods: {
    registerMatch() {
      // 매칭 등록 로직
      console.log('매칭 등록:', this.newMatch);
      this.$emit('register-match', this.newMatch);
      this.$emit('close-sidebar');
    },
  },
};
</script>

<style scoped>
.matching-register-sidebar {
  width: 300px;
  padding: 20px;
  background-color: #f8f8f8;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  position: fixed;
  top: 0;
  bottom: 0;
  right: 0;
  z-index: 1000;
  transform: translateX(100%);
  transition: transform 0.3s ease-in-out;
}

.matching-register-sidebar.open {
  transform: translateX(0);
}

h2 {
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

input,
select {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.action-buttons {
  display: flex;
  justify-content: space-between;
}

.register-button {
  background-color: #28a745;
  color: #fff;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
}

.close-button {
  background-color: #6c757d;
  color: #fff;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
}
</style>
