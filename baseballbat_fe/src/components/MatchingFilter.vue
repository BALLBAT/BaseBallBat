<template>
  <div class="matching-container">
    <aside class="matching-filter-section">
      <h2>매칭 조건 검색</h2>
      <form @submit.prevent="emitSearchMatches">
        <div class="form-group">
          <label for="team" class="filter-label">응원팀</label>
          <select id="team" :value="filters.team" @change="updateFilter('team', $event.target.value)">
            <option value="">팀 선택</option>
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
          <label for="date" class="filter-label">날짜</label>
          <input type="date" id="date" :value="filters.date" @change="updateFilter('date', $event.target.value)" />
        </div>
        <div class="form-group">
          <label for="location" class="filter-label">경기장</label>
          <select id="location" :value="filters.location" @change="updateFilter('location', $event.target.value)">
            <option value="">경기장 선택</option>
            <option value="잠실야구장">잠실야구장</option>
            <option value="고척스카이돔">고척스카이돔</option>
            <option value="문학야구장">문학야구장</option>
            <option value="대전한화생명이글스파크">대전한화생명이글스파크</option>
            <option value="광주기아챔피언스필드">광주기아챔피언스필드</option>
            <option value="대구삼성라이온즈파크">대구삼성라이온즈파크</option>
            <option value="사직야구장">사직야구장</option>
            <option value="창원NC파크">창원NC파크</option>
            <option value="수원KT위즈파크">수원KT위즈파크</option>
          </select>
        </div>
        <button type="submit" class="search-button">검색</button>
      </form>
    </aside>
    <section class="create-matching-section">
      <button class="create-matching-button" @click="navigateToCreatePage">매칭 등록하기</button>
    </section>
  </div>
</template>

<script>
export default {
  props: ['filters'],
  methods: {
    updateFilter(key, value) {
      // 부모 컴포넌트에게 필터 값 변경을 요청
      this.$emit('update:filters', { ...this.filters, [key]: value });
    },
    emitSearchMatches() {
      // 검색 이벤트 발생
      this.$emit('search-matches');
    },
    navigateToCreatePage() {
      // 매칭 등록 페이지로 이동
      this.$router.push('/matching-create');
    },
  },
};
</script>

<style scoped>
.matching-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.matching-filter-section {
  width: 300px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 10px;
  margin-bottom: 5px;
}

.form-group {
  margin-bottom: 15px;
  display: flex;
  align-items: center;
}

.filter-label {
  width: 80px;
  margin-right: 10px;
}

.search-button {
  width: 80%;
  background-color: #007bff;
  color: #fff;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
}

.create-matching-section {
  width: 300px;
  margin-top: 5px;
}

.create-matching-button {
  width: 100%;
  background-color: #28a745;
  color: #fff;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
}
</style>
