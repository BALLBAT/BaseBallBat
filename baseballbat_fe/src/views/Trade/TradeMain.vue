<template>
  <div class="trade-page-container" style="margin-top: 60px; max-width: 1200px;">
    <!-- 페이지 소개 섹션 -->
    <section class="intro-section">
      <h1>중고 거래</h1>
      <p>팀 팬들 간에 중고 물품을 거래하고, 새로운 팬 아이템을 찾아보세요!</p>
    </section>

    <!-- 카테고리 선택 섹션 -->
    <section class="category-selection-section">
      <h2>카테고리 선택</h2>
      <div class="categories">
        <button v-for="category in categories" :key="category" @click="selectCategory(category)" class="category-button">
          {{ category }}
        </button>
      </div>
    </section>

    <!-- 물품 목록 섹션 -->
    <section class="item-list-section">
      <h2>거래 물품 목록</h2>
      <div v-if="items.length" class="item-cards">
        <div v-for="item in items" :key="item.id" class="item-card">
          <img :src="item.image" alt="item.name" class="item-image" />
          <h3>{{ item.name }}</h3>
          <p>{{ item.price }}원</p>
          <p>판매자: {{ item.seller }}</p>
          <button @click="viewItemDetail(item.id)" class="view-detail-button">상세 보기</button>
        </div>
      </div>
      <div v-else>
        <p>현재 선택한 카테고리에 물품이 없습니다. 다른 카테고리를 선택해보세요.</p>
      </div>
    </section>

    <!-- 물품 등록 섹션 -->
    <section class="register-item-section">
      <h2>거래 물품 등록하기</h2>
      <button @click="goToRegisterPage" class="register-item-button">물품 등록하기</button>
    </section>
  </div>
</template>

<script>
export default {
  data() {
    return {
      categories: ['유니폼', '응원 도구', '기념품', '기타'],
      items: [],
    };
  },
  methods: {
    selectCategory(category) {
      // 선택된 카테고리의 물품을 불러오는 로직
      console.log('카테고리 선택:', category);
    },
    viewItemDetail(itemId) {
      // 물품 상세 페이지로 이동하는 로직
      console.log('물품 상세 보기:', itemId);
      this.$router.push(`/item/${itemId}`);
    },
    goToRegisterPage() {
      // 물품 등록 페이지로 이동
      this.$router.push('/register-item');
    },
  },
};
</script>

<style scoped>
.trade-page-container {
  padding: 20px;
  margin: 80px auto 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.intro-section {
  text-align: center;
  margin-bottom: 30px;
}

.category-selection-section {
  margin-bottom: 30px;
  width: 100%;
  text-align: center;
}

.categories {
  display: flex;
  justify-content: center;
  gap: 10px;
  flex-wrap: wrap;
}

.category-button {
  background-color: #007bff;
  color: #fff;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
}

.item-list-section {
  margin-bottom: 30px;
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}

.item-card {
  background-color: #fff;
  padding: 15px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 220px;
  margin-bottom: 20px;
  text-align: center;
}

.item-image {
  width: 100%;
  height: 150px;
  object-fit: cover;
  border-radius: 5px;
  margin-bottom: 10px;
}

.view-detail-button {
  background-color: #28a745;
  color: #fff;
  border: none;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
}

.register-item-section {
  text-align: center;
  margin-top: 30px;
}

.register-item-button {
  background-color: #ff8c00;
  color: #fff;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
}

@media (max-width: 768px) {
  .item-list-section {
    flex-direction: column;
    align-items: center;
  }
  .item-card {
    width: 100%;
    max-width: 300px;
  }
}
</style>
