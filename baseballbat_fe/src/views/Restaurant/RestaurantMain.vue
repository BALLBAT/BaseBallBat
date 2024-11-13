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
        <select id="team-select" v-model="selectedTeam" @change="moveToTeamStadium" class="team-select">
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
          <div v-for="category in categories" :key="category" @click="filterByCategory(category)" :class="['category-item', { 'active': selectedCategory === category }]">
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
          <h3>{{ selectedRestaurant.place_name }}</h3>
          <p>주소: {{ selectedRestaurant.address_name }}</p>
          <p>도로명 주소: {{ selectedRestaurant.road_address_name }}</p>
          <p>카테고리: {{ selectedRestaurant.category_group_name }} - {{ selectedRestaurant.category_name }}</p>
          <p>전화번호: {{ selectedRestaurant.phone }}</p>
          <p>거리: {{ selectedRestaurant.distance }}m</p>
          <p>lat: {{selectedRestaurant.y}}, lng: {{selectedRestaurant.x}}</p>
          <a :href="selectedRestaurant.place_url" target="_blank" class="place-link">카카오맵에서 보기</a>
          <button @click="addToFavorites(selectedRestaurant.id)" class="favorites-button">즐겨찾기 추가</button>
        </div>
        <div v-else>
          <p>카테고리를 선택하거나 지도를 클릭해서 맛집 정보를 확인하세요.</p>
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
/* global kakao */
export default {
  data() {
    return {
      searchQuery: '',
      selectedTeam: '',
      selectedCategory: '',
      categories: ['한식', '양식', '중식', '일식', '카페'],
      restaurants: [],
      recommendedRestaurants: [],
      filteredRestaurants: [],
      selectedRestaurant: null,
      map: null,
      ps: null, // 장소 검색 객체
      markers: [], // 생성된 마커를 저장
      teamStadiums: {
        'LG 트윈스': { lat: 37.5121513808403, lng: 127.071909507224 },
        '두산 베어스': { lat: 37.5121513808403, lng: 127.071909507224 },
        'SSG 랜더스': { lat: 37.436998685442084, lng: 126.69327612453377 },
        '키움 히어로즈': { lat: 37.4982338495579, lng: 126.867104761712 },
        '한화 이글스': { lat: 36.3161617310226, lng: 127.431535001435 },
        'KIA 타이거즈': { lat: 35.16820922209541, lng: 126.88911206152956 },
        '삼성 라이온즈': { lat: 35.8410595632468, lng: 128.681659448344 },
        '롯데 자이언츠': { lat: 35.194017568250274, lng: 129.06154402103502 },
        'NC 다이노스': { lat: 35.22280070751199, lng: 128.5820053292696 },
        'KT 위즈': { lat: 37.2997302532973, lng: 127.009772045935 },
      },
    };
  },
  methods: {
    fetchTeamRestaurants() {
      // 선택된 구단의 홈구장 주변 맛집을 불러오는 로직
      console.log('선택된 구단:', this.selectedTeam);
      // 예시로 모든 맛집을 불러오는 대신 구단별 필터링 로직 추가 필요
    },
    searchRestaurants() {
      if (!this.searchQuery) return;

      // 카카오 장소 검색 객체를 이용해 검색
      this.ps.keywordSearch(this.searchQuery, (data, status) => {
        if (status === kakao.maps.services.Status.OK) {
          this.clearMarkers();
          this.restaurants = data;

          data.forEach((place) => {
            this.addMarker(new kakao.maps.LatLng(place.y, place.x), place);
          });
        }
      });
    },
    filterByCategory(category) {
      this.selectedCategory = category;
      const center = this.map.getCenter();
      // 현재 지도의 중심 위치를 기준으로 카테고리 검색
      this.ps.keywordSearch(category, (data, status) => {
        if (status === kakao.maps.services.Status.OK) {
          this.clearMarkers();
          this.restaurants = data;

          data.forEach((place) => {
            this.addMarker(new kakao.maps.LatLng(place.y, place.x), place);
          });
        }
      }, {
        location: new kakao.maps.LatLng(center.getLat(), center.getLng()),
        radius: 5000
      });
    },
    addToFavorites(restaurantId) {
      // 즐겨찾기 추가 로직
      console.log('즐겨찾기 추가:', restaurantId);
    },
    viewRestaurant(restaurantId) {
      // 맛집 상세보기 로직
      console.log('맛집 보기:', restaurantId);
    },
    addMarker(position, place) {
      const marker = new kakao.maps.Marker({
        position,
        map: this.map,
      });

      // 마커 클릭 시 해당 맛집 정보 표시
      kakao.maps.event.addListener(marker, 'click', () => {
        this.selectedRestaurant = place;
      });

      this.markers.push(marker);
    },
    clearMarkers() {
      // 지도에서 모든 마커를 제거
      this.markers.forEach((marker) => marker.setMap(null));
      this.markers = [];
    },
    selectRestaurant(restaurant) {
      this.selectedRestaurant = restaurant;
    },
    moveToTeamStadium() {
      if (this.selectedTeam && this.teamStadiums[this.selectedTeam]) {
        const { lat, lng } = this.teamStadiums[this.selectedTeam];
        const moveLatLng = new kakao.maps.LatLng(lat, lng);
        this.map.setCenter(moveLatLng);
      }
    }
  },
  mounted() {
    // 현재 위치로 지도 초기화
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        const lat = position.coords.latitude;
        const lng = position.coords.longitude;
        const container = document.getElementById('kakao-map');
        const options = {
          center: new kakao.maps.LatLng(lat, lng),
          level: 4,
        };
        this.map = new kakao.maps.Map(container, options);

        // 장소 검색 서비스 객체 생성
        this.ps = new kakao.maps.services.Places();
      });
    } else {
      // Geolocation을 지원하지 않는 경우 기본 위치 설정
      const container = document.getElementById('kakao-map');
      const options = {
        center: new kakao.maps.LatLng(37.5665, 126.9780),
        level: 4,
      };
      this.map = new kakao.maps.Map(container, options);

      // 장소 검색 서비스 객체 생성
      this.ps = new kakao.maps.services.Places();
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

.category-item.active {
  background-color: #007bff;
  color: #fff;
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

.place-link {
  display: block;
  margin-top: 10px;
  color: #007bff;
  text-decoration: none;
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
