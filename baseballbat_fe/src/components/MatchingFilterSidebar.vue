<template>
  <div class="matching-filter-sidebar">
    <h2>매칭 조건 검색</h2>
    <form @submit.prevent="applyFilters">
      <div class="form-group">
        <label for="team">구단 선택</label>
        <select id="team" v-model="filters.team">
          <option value="">모든 구단</option>
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
        <label for="date">날짜 선택</label>
        <input type="date" id="date" v-model="filters.date" />
      </div>
      <div class="form-group">
        <label for="location">경기장 선택</label>
        <select id="location" v-model="filters.location">
          <option value="">모든 경기장</option>
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
      <div class="form-group action-buttons">
        <button type="submit" class="apply-button">검색 적용</button>
        <button type="button" @click="$emit('close-sidebar')" class="close-button">닫기</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      filters: {
        team: '',
        date: '',
        location: '',
      },
    };
  },
  methods: {
    applyFilters() {
      // 필터 적용 로직
      console.log('필터 적용:', this.filters);
      this.$emit('apply-filters', this.filters);
      this.$emit('close-sidebar');
    },
  },
};
</script>

<style scoped>
.matching-filter-sidebar {
  width: 300px;
  padding: 20px;
  background-color: #f8f8f8;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  z-index: 1000;
  transform: translateX(-100%);
  transition: transform 0.3s ease-in-out;
}

.matching-filter-sidebar.open {
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

.apply-button {
  background-color: #007bff;
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
