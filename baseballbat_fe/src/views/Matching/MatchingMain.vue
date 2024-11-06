<template>
  <div class="matching-page-container" style="margin-top: 60px;">
    <!-- 매칭 소개 섹션 -->
    <section class="intro-section">
      <h1>함께 직관할 팬을 찾아보세요!</h1>
      <p>같은 팀을 응원하며 즐거운 직관 경험을 함께할 사람을 찾고 매칭하세요.</p>
    </section>

    <!-- 매칭 조건 검색 사이드바 (전체 화면에서는 보이고, 축소되면 버튼으로 열기) -->
    <MatchingFilterSidebar v-if="!isMobile || isFilterSidebarOpen" @close-sidebar="toggleSidebar('filter')" />

    <!-- 매칭 등록 사이드바 (전체 화면에서는 보이고, 축소되면 버튼으로 열기) -->
    <MatchingRegisterSidebar v-if="!isMobile || isRegisterSidebarOpen" @close-sidebar="toggleSidebar('register')" />

    <!-- 매칭 조건 검색 사이드바 버튼 (화면이 축소될 때만 보임) -->
    <button v-if="isMobile" @click="toggleSidebar('filter')" class="toggle-sidebar-button">매칭 조건 검색</button>

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

    <!-- 매칭 등록 사이드바 버튼 (화면이 축소될 때만 보임) -->
    <button v-if="isMobile" @click="toggleSidebar('register')" class="toggle-sidebar-button">매칭 등록하기</button>

    <!-- 추천 매칭 섹션 -->
    <section class="recommended-section">
      <h2>추천 매칭</h2>
      <div v-if="recommendedMatches.length" class="recommended-cards">
        <div v-for="match in recommendedMatches" :key="match.id" class="recommended-card">
          <h3>{{ match.team }} vs {{ match.opponent }}</h3>
          <p>경기 날짜: {{ match.date }}</p>
          <button @click="joinMatch(match.id)" class="join-button">참여하기</button>
        </div>
      </div>
    </section>

    <!-- 후기 섹션 -->
    <section class="review-section">
      <h2>직관 후기</h2>
      <div v-if="reviews.length" class="review-cards">
        <div v-for="review in reviews" :key="review.id" class="review-card">
          <p>{{ review.content }}</p>
          <p>- {{ review.author }}</p>
        </div>
      </div>
      <div v-else>
        <p>아직 후기가 없습니다. 첫 후기를 작성해보세요!</p>
      </div>
    </section>
  </div>
</template>

<script>
import MatchingFilterSidebar from '@/components/MatchingFilterSidebar.vue';
import MatchingRegisterSidebar from '@/components/MatchingRegisterSidebar.vue';

export default {
  components: {
    MatchingFilterSidebar,
    MatchingRegisterSidebar,
  },
  data() {
    return {
      filters: {
        team: '',
        date: '',
        location: '',
      },
      matches: [],
      recommendedMatches: [],
      reviews: [],
      isFilterSidebarOpen: false,
      isRegisterSidebarOpen: false,
      isMobile: window.innerWidth <= 768,
    };
  },
  methods: {
    searchMatches() {
      // 매칭 검색 로직
      console.log('매칭 검색:', this.filters);
    },
    joinMatch(matchId) {
      // 매칭 참여 로직
      console.log('매칭 참여:', matchId);
    },
    goToRegisterPage() {
      // 매칭 등록 페이지로 이동
      this.$router.push('/register-match');
    },
    toggleSidebar(sidebar) {
      if (sidebar === 'filter') {
        this.isFilterSidebarOpen = !this.isFilterSidebarOpen;
      } else if (sidebar === 'register') {
        this.isRegisterSidebarOpen = !this.isRegisterSidebarOpen;
      }
    },
    handleResize() {
      this.isMobile = window.innerWidth <= 768;
    },
  },
  mounted() {
    window.addEventListener('resize', this.handleResize);
  },
  beforeUnmount() {
    window.removeEventListener('resize', this.handleResize);
  },
};
</script>

<style scoped>
.matching-page-container {
  padding: 20px;
  max-width: 1200px;
  margin: 80px auto 0 auto; /* 헤더와의 간격 추가 */
  display: flex;
  flex-wrap: wrap;
}

.intro-section {
  text-align: center;
  margin-bottom: 30px;
  width: 100%;
}

.toggle-sidebar-button {
  background-color: #007bff;
  color: #fff;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
  margin-bottom: 20px;
}

.matching-list-section {
  margin-bottom: 30px;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}

.match-card, .recommended-card, .review-card {
  background-color: #fff;
  padding: 15px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.recommended-section, .review-section {
  margin-bottom: 30px;
  width: 100%;
}

.search-button, .join-button, .register-button {
  background-color: #007bff;
  color: #fff;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
}

@media (max-width: 768px) {
  .matching-page-container {
    padding: 10px;
    flex-direction: column;
  }
  .toggle-sidebar-button {
    width: 100%;
  }
  .matching-list-section {
    width: 100%;
  }
  .intro-section, .recommended-section, .review-section {
    width: 100%;
  }
}
</style>
