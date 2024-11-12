<template>
  <div class="register-item-page-container" style="margin-top: 60px;">
    <h1>중고 거래 물품 등록</h1>

    <!-- 물품 정보 입력 섹션 -->
    <section class="item-info-section">
      <h2>물품 정보</h2>
      <form @submit.prevent="registerItem">
        <div class="form-group">
          <label for="item-title">제목</label>
          <input type="text" id="item-title" v-model="item.title" required />
        </div>

        <div class="form-group">
          <label for="category">카테고리</label>
          <select id="category" v-model="item.category" required>
            <option value="">카테고리를 선택하세요</option>
            <option value="유니폼">유니폼</option>
            <option value="응원 도구">응원 도구</option>
            <option value="기념품">기념품</option>
            <option value="기타">기타</option>
          </select>
        </div>

        <div class="form-group">
          <label for="price">가격 (원)</label>
          <input type="text" id="price" v-model="item.price" required />
        </div>

        <div class="form-group">
          <label for="description">게시글 내용</label>
          <textarea id="description" v-model="item.description" rows="4" required></textarea>
        </div>

        <div class="form-group">
          <label for="images">물품 이미지</label>
          <input type="file" id="images" @change="handleImageUpload" accept="image/*" multiple />
        </div>

        <!-- 등록 버튼 -->
        <div class="action-buttons">
          <button type="submit" class="register-button">등록하기</button>
          <button type="button" @click="cancelRegistration" class="cancel-button">취소</button>
        </div>
      </form>
    </section>
  </div>
</template>

<script>
export default {
  data() {
    return {
      item: {
        title: '',
        category: '',
        price: '',
        description: '',
        images: [],
      },
    };
  },
  methods: {
    registerItem() {
      // 물품 등록 로직
      console.log('물품 등록:', this.item);
    },
    cancelRegistration() {
      // 등록 취소 시 이전 페이지로 이동
      this.$router.push('/trade');
    },
    handleImageUpload(event) {
      const files = Array.from(event.target.files);
      if (files.length) {
        this.item.images = files.map(file => URL.createObjectURL(file));
      }
    },
  },
};
</script>

<style scoped>
.register-item-page-container {
  max-width: 600px;
  margin: 80px auto 0 auto;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  background-color: #fff;
}

h1, h2 {
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
  display: flex;
  flex-direction: column;
}

label {
  margin-bottom: 5px;
  font-weight: bold;
}

input[type="text"],
input[type="file"],
textarea,
select {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.action-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 20px;
}

.register-button {
  background-color: #007bff;
  color: #fff;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.cancel-button {
  background-color: #6c757d;
  color: #fff;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

/* 반응형 스타일 */
@media (max-width: 768px) {
  .register-item-page-container {
    padding: 10px;
  }
  input[type="text"],
  input[type="file"],
  textarea,
  select {
    font-size: 14px;
  }
}
</style>
