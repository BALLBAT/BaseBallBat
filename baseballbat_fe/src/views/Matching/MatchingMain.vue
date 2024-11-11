<template>
  <div class="matching-page-container" style="margin-top: 60px; display: flex; gap: 20px;">
    <!-- 매칭 필터 사이드바 -->
    <MatchingFilter :filters="filters" @update:filters="updateFilters" @search-matches="searchMatches" />
    
    <div class="main-content">
      <!-- 매칭 소개 섹션 -->
      <section class="intro-section">
        <h1>함께 직관할 팬을 찾아보세요!</h1>
        <p>같은 팀을 응원하며 즐거운 직관 경험을 함께할 사람을 찾고 매칭하세요.</p>
      </section>
  
      <!-- 매칭 목록 섹션 -->
      <section class="matching-list-section">
        <h2>매칭 목록</h2>
        <div v-if="matches.length" class="match-cards">
          <div v-for="match in matches" :key="match.id" class="match-card">
            <h3>{{ match.team }} vs {{ match.opponent }}</h3>
            <p>경기 날짜: {{ match.date }}</p>
            <p>경기 장소: {{ match.location }}</p>
            <p>참여 인원: {{ match.participants }} / {{ match.totalSpots }}</p>
            <button @click="joinMatch(match.id)" :disabled="match.isFull" class="join-button">
              {{ match.isFull ? '매칭 마감' : '참여하기' }}
            </button>
          </div>
        </div>
        <div v-else>
          <p>조건에 맞는 매칭이 없습니다. 다른 조건으로 검색해보세요.</p>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import MatchingFilter from '@/components/MatchingFilter.vue';

export default {
  components: {
    MatchingFilter,
  },
  data() {
    return {
      filters: {
        team: '',
        date: '',
        location: '',
      },
      matches: [],
    };
  },
  methods: {
    updateFilters(newFilters) {
      this.filters = newFilters;
    },
    searchMatches() {
      // 매칭 검색 로직
      console.log('매칭 검색:', this.filters);
    },
    joinMatch(matchId) {
      // 매칭 참여 로직
      console.log('매칭 참여:', matchId);
    },
  },
};
</script>

<style scoped>
.matching-page-container {
  padding: 10px;
  max-width: 1400px;
  margin: 40px auto 0 auto; /* 헤더와의 간격 줄이기 */
  display: flex;
  gap: 20px;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 20px;
  align-items: center;
  text-align: center;
}

.intro-section {
  text-align: center;
  margin-bottom: 30px;
  width: 100%;
}

.matching-list-section {
  width: 100%;
  max-width: 800px;
  padding: 0 20px;
  box-sizing: border-box;
  text-align: center;
}

.match-card {
  background-color: #fff;
  padding: 15px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
  width: 100%;
  max-width: 600px;
}

.search-button, .join-button {
  background-color: #007bff;
  color: #fff;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
}

.matching-filter-section {
  width: 300px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 10px;
}

@media (max-width: 768px) {
  .matching-page-container {
    flex-direction: column;
    align-items: center;
    padding-top: 1px;
  }
  .main-content {
    width: 100%;
  }
  .matching-list-section {
    width: 100%;
    padding: 0;
  }
  .matching-filter-section {
    width: 100%;
    max-width: 300px;
    margin: 0 auto;
  }
}
  .main-content {
    width: 100%;
  }
  .matching-list-section {
    width: 100%;
    padding: 0;
  }
  .matching-filter-section {
    width: 100%;
    max-width: 300px;
    margin: 0 auto;
  }
</style>
