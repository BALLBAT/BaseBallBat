<template>
  <div class="mypage-container">
    <!-- 개인 정보 관리 섹션 -->
    <section class="profile-section">
      <h2>개인 정보</h2>
      <div class="profile-info">
        <p>이름: 홍길동</p>
        <p>이메일: user@example.com</p>
        <p>현재 포인트: 10,000P</p>
        <button @click="editProfile" class="edit-profile-button">프로필 수정</button>
      </div>
    </section>

    <!-- 나의 활동 내역 섹션 -->
    <section class="activity-section">
      <h2>나의 활동 내역</h2>
      <div class="activity-subsections">
        <!-- 함께보기 내역 -->
        <div class="subsection direct-section">
          <h3>함께 보기 내역</h3>
          <ul class="direct-list">
            <li v-for="item in pagedDirectItems" :key="item.id">{{ item.title }}</li>
          </ul>
          <div class="button-container">
            <button @click="previousPage('direct')" :disabled="directPage === 1">이전</button>
            <button @click="nextPage('direct')" :disabled="directPage === maxDirectPage">다음</button>
          </div>
        </div>
        <!-- 중고 거래 내역 -->
        <div class="subsection trade-section">
          <h3>중고거래 내역</h3>
          <ul class="trade-list">
            <li v-for="item in pagedTradeItems" :key="item.id">{{ item.title }}</li>
          </ul>
          <div class="button-container">
            <button @click="previousPage('trade')" :disabled="tradePage === 1">이전</button>
            <button @click="nextPage('trade')" :disabled="tradePage === maxTradePage">다음</button>
          </div>
        </div>
        <!-- 내가 찜한 맛집 리스트 -->
        <div class="subsection favorites-section">
          <h3>내가 찜한 맛집</h3>
          <ul class="favorites-list">
            <li v-for="item in pagedFavoriteItems" :key="item.id">{{ item.title }}</li>
          </ul>
          <div class="button-container">
            <button @click="previousPage('favorites')" :disabled="favoritesPage === 1">이전</button>
            <button @click="nextPage('favorites')" :disabled="favoritesPage === maxFavoritesPage">다음</button>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tradeItems: [
        { id: 1, title: '거래 물품 1' },
        { id: 2, title: '거래 물품 2' },
        // ...더 많은 거래 아이템들
      ],
      favoriteItems: [
        { id: 1, title: '맛집 1' },
        { id: 2, title: '맛집 2' },
        // ...더 많은 맛집 아이템들
      ],
      directItems: [
        { id: 1, title: '직관 동행 1' },
        { id: 2, title: '직관 동행 2' },
        // ...더 많은 직관 아이템들
      ],
      tradePage: 1,
      favoritesPage: 1,
      directPage: 1,
      itemsPerPage: 5,
    };
  },
  computed: {
    pagedTradeItems() {
      const start = (this.tradePage - 1) * this.itemsPerPage;
      return this.tradeItems.slice(start, start + this.itemsPerPage);
    },
    pagedFavoriteItems() {
      const start = (this.favoritesPage - 1) * this.itemsPerPage;
      return this.favoriteItems.slice(start, start + this.itemsPerPage);
    },
    pagedDirectItems() {
      const start = (this.directPage - 1) * this.itemsPerPage;
      return this.directItems.slice(start, start + this.itemsPerPage);
    },
    maxTradePage() {
      return Math.ceil(this.tradeItems.length / this.itemsPerPage);
    },
    maxFavoritesPage() {
      return Math.ceil(this.favoriteItems.length / this.itemsPerPage);
    },
    maxDirectPage() {
      return Math.ceil(this.directItems.length / this.itemsPerPage);
    }
  },
  methods: {
    navigateTo(route) {
      this.$router.push(`/${route}`);
    },
    previousPage(section) {
      if (section === 'trade' && this.tradePage > 1) {
        this.tradePage--;
      } else if (section === 'favorites' && this.favoritesPage > 1) {
        this.favoritesPage--;
      } else if (section === 'direct' && this.directPage > 1) {
        this.directPage--;
      }
    },
    nextPage(section) {
      if (section === 'trade' && this.tradePage < this.maxTradePage) {
        this.tradePage++;
      } else if (section === 'favorites' && this.favoritesPage < this.maxFavoritesPage) {
        this.favoritesPage++;
      } else if (section === 'direct' && this.directPage < this.maxDirectPage) {
        this.directPage++;
      }
    },
    editProfile() {
      // 프로필 수정 로직
    },
  },
};
</script>

<style scoped>
.mypage-container {
  display: flex;
  flex-direction: column;
  padding: 20px;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  margin-top: 60px; /* 헤더 높이만큼 마진 추가 */
}

.profile-section {
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
  width: 60%;
}

.profile-info {
  display: flex;
  justify-content: space-around;
  align-items: center;
  flex-wrap: wrap;
  text-align: center;
  font-size: 1.2rem;
  gap: 15px;
  height: 100%;
}

.profile-info p {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  margin: 0;
}

.edit-profile-button {
  background-color: #007bff; /* 메인 색상 */
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px; /* 둥근 테두리 */
  cursor: pointer;
}

.activity-section {
  margin-top: 20px;
  width: 60%;
}

.activity-subsections {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
}

.subsection {
  width: 33%;
  padding: 20px;
  box-sizing: border-box;
  border: 1px solid #ddd;
  border-radius: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 350px;
}

.button-container {
  display: flex;
  justify-content: space-between;
  margin-top: auto;
}

/* 반응형 스타일 */
@media (max-width: 768px) {
  .activity-subsections {
    flex-direction: column;
  }
  .subsection {
    width: 100%;
  }
}
</style>
