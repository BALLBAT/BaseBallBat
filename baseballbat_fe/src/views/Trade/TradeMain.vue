<template>
  <div class="second-hand-page-container" style="margin-top: 60px; max-width: 1400px;">
    <!-- 검색 섹션 -->
    <section class="search-section">
      <input type="text" v-model="searchQuery" placeholder="상품명을 검색하세요" class="search-input" />
      <button @click="searchItems" class="search-button">검색</button>
    </section>

    <!-- 거래 물품 목록 섹션 -->
    <section class="item-list-section">
      <h2>거래 물품 목록</h2>
      <!-- 카테고리 선택 -->
      <div class="category-selection">
        <span v-for="category in categories" :key="category" @click="selectCategory(category)" :class="['category-text', { 'selected-category': selectedCategory === category }]">
          {{ category }}
        </span>
      </div>
      <div v-if="items.length" class="item-cards">
        <div v-for="item in filteredItems" :key="item.id" class="item-card">
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
      searchQuery: '',
      selectedCategory: '',
    };
  },
  computed: {
    filteredItems() {
      return this.items.filter(item => {
        const matchesCategory = this.selectedCategory ? item.category === this.selectedCategory : true;
        const matchesSearch = item.name.toLowerCase().includes(this.searchQuery.toLowerCase());
        return matchesCategory && matchesSearch;
      });
    }
  },
  methods: {
    selectCategory(category) {
      this.selectedCategory = category;
    },
    searchItems() {
      // 검색 버튼 클릭 시 로직
      console.log('검색어:', this.searchQuery);
    },
    viewItemDetail(itemId) {
      // 물품 상세 페이지로 이동하는 로직
      console.log('물품 상세 보기:', itemId);
      this.$router.push(`/item/${itemId}`);
    },
    goToRegisterPage() {
      // 물품 등록 페이지로 이동
      this.$router.push('/trade-create');
    }
  },
};
</script>

<style scoped>
.second-hand-page-container {
  padding: 20px;
  margin: 80px auto 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.search-section {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
  gap: 10px;
  width: 100%;
  max-width: 600px;
}

.search-input {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  width: 70%;
}

.search-button {
  background-color: #212529; /* 메인 색상 */
  color: #fff;
  border: none;
  padding: 12px 25px;
  border-radius: 5px;
  cursor: pointer;
}

.item-list-section {
  margin-bottom: 30px;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.category-selection {
  margin-bottom: 20px;
  display: flex;
  justify-content: center;
  gap: 10px;
  flex-wrap: wrap;
}

.category-text {
  cursor: pointer;
  font-size: 1.1rem;
  padding: 5px 10px;
}

.selected-category {
  font-weight: bold;
  text-decoration: underline;
}

.item-cards {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  width: 100%;
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
  .second-hand-page-container {
    flex-direction: column;
    align-items: center;
  }
  .item-list-section {
    flex-direction: column;
    align-items: center;
    width: 100%;
  }
  .item-card {
    width: 100%;
    max-width: 300px;
  }
  .search-input {
    width: 100%;
  }
}
</style>
