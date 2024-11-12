<template>
  <div class="restaurant-page-container" style="margin-top: 60px;">
    <!-- 맛집 검색 섹션 -->
    <section class="search-section">
      <input type="text" v-model="searchQuery" placeholder="맛집을 검색하세요" class="search-input" />
      <button @click="searchRestaurants" class="search-button">검색</button>
    </section>

    <!-- 구단 선택 및 카테고리 필터 섹션 -->
    <section class="team-and-category-section">
      <div class="team-selection-section">
        <label for="team-select" class="team-label">구단 선택:</label>
        <select id="team-select" v-model="selectedTeam" @change="fetchTeamRestaurants" class="team-select">
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
      </div>
      <div class="category-section">
        <div class="category-items">
          <div v-for="category in categories" :key="category" @click="filterByCategory(category)" class="category-item">
            {{ category }}
          </div>
        </div>
      </div>
    </section>

    <!-- 맛집 지도 및 정보 섹션 -->
    <section class="restaurant-map-info-section">
      <div class="map-container">
        <div id="kakao-map" class="kakao-map"></div>
      </div>
      <div class="restaurant-info-container">
        <div v-if="selectedRestaurant">
          <h3>{{ selectedRestaurant.name }}</h3>
          <p>주요 메뉴: {{ selectedRestaurant.mainMenu }}</p>
          <p>평점: {{ selectedRestaurant.rating }} / 5</p>
          <button @click="addToFavorites(selectedRestaurant.id)" class="favorites-button">즐겨찾기 추가</button>
        </div>
        <div v-else>
          <p>지도를 클릭하여 맛집 정보를 확인하세요.</p>
        </div>
      </div>
    </section>

    <!-- 추천 맛집 섹션 -->
    <section class="recommended-section">
      <h2>추천 맛집</h2>
      <div v-if="recommendedRestaurants.length" class="recommended-cards">
        <div v-for="restaurant in recommendedRestaurants" :key="restaurant.id" class="recommended-card">
          <h3>{{ restaurant.name }}</h3>
          <p>주요 메뉴: {{ restaurant.mainMenu }}</p>
          <button @click="viewRestaurant(restaurant.id)" class="view-button">자세히 보기</button>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchQuery: '',
      selectedTeam: '',
      categories: ['한식', '양식', '중식', '일식', '디저트', '카페'],
      restaurants: [],
      recommendedRestaurants: [],
      filteredRestaurants: [],
      selectedRestaurant: null,
    };
  },
  methods: {
    fetchTeamRestaurants() {
      // 선택된 구단의 홈구장 주변 맛집을 불러오는 로직
      console.log('선택된 구단:', this.selectedTeam);
      // 예시로 모든 맛집을 불러오는 대신 구단별 필터링 로직 추가 필요
    },
    searchRestaurants() {
      // 검색 로직
      console.log('검색어:', this.searchQuery);
    },
    filterByCategory(category) {
      // 카테고리 필터 로직
      console.log('카테고리 선택:', category);
    },
    addToFavorites(restaurantId) {
      // 즐겨찾기 추가 로직
      console.log('즐겨찾기 추가:', restaurantId);
    },
    viewRestaurant(restaurantId) {
      // 맛집 상세보기 로직
      console.log('맛집 보기:', restaurantId);
    },
    selectRestaurant(restaurant) {
      this.selectedRestaurant = restaurant;
    },
  },
mounted() {
  // 카카오 지도 API 초기화 로직
  /* eslint-disable no-undef */
  if (typeof kakao !== 'undefined') {
    const container = document.getElementById('kakao-map');
    const options = {
      center: new kakao.maps.LatLng(37.5665, 126.9780),
      level: 4,
    };
    const map = new kakao.maps.Map(container, options);

    // 지도 클릭 이벤트 설정
    kakao.maps.event.addListener(map, 'click', (mouseEvent) => {
      const latlng = mouseEvent.latLng;
      console.log('클릭한 위치:', latlng);
      // 예시: 클릭한 위치에 따른 맛집 정보를 업데이트
      // this.selectRestaurant(...);
    });
  }
},
};
</script>

<style scoped>
.restaurant-page-container {
  padding: 20px;
  max-width: 1200px;
  margin: 80px auto 0 auto; /* 헤더와의 간격 추가 */
}

.search-section {
  display: flex;
  justify-content: center;
  width: 100%;
  margin-bottom: 20px;
}

.search-input {
  width: 60%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.search-button {
  background-color: #212529;
  color: #fff;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
  margin-left: 10px;
}

.team-and-category-section {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 10px;
  gap: 10px;
  flex-wrap: wrap;
}

.team-selection-section {
  display: flex;
  align-items: center;
}

.team-label {
  margin-right: 10px;
  font-weight: bold;
}

.team-select {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.category-section {
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
}

.category-items {
  display: flex;
  gap: 10px;
}

.category-item {
  background-color: #f8f9fa;
  color: #333;
  padding: 10px 15px;
  border-radius: 20px;
  cursor: pointer;
  margin: 5px;
}

.restaurant-map-info-section {
  display: flex;
  gap: 20px;
  margin-bottom: 30px;
}

.map-container {
  width: 50%;
  height: 400px;
}

.kakao-map {
  width: 100%;
  height: 100%;
}

.restaurant-info-container {
  width: 50%;
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.favorites-button {
  background-color: #007bff;
  color: #fff;
  border: none;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 10px;
}

.recommended-section {
  margin-bottom: 30px;
}

.recommended-cards {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}

.recommended-card {
  background-color: #fff;
  padding: 15px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 30%;
  margin-bottom: 20px;
  text-align: center;
}

.view-button {
  background-color: #28a745;
  color: #fff;
  border: none;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 10px;
}

/* 반응형 스타일 */
@media (max-width: 768px) {
  .search-input {
    width: 80%;
  }
  .restaurant-map-info-section {
    flex-direction: column;
  }
  .map-container,
  .restaurant-info-container {
    width: 100%;
  }
  .recommended-card {
    width: 100%;
  }
}
</style>
