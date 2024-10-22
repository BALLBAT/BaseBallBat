// HomePage
// 야구장에 꽉 들어찬 관중 사진과 반투명한 배경을 덮고 Join us 버튼 만들어서 메인 페이지 진입할 수 있도록 만들기
<template lang="">
  <div class="hero-section">
    <div class="overlay">
      <div class="hero-content">
        <h1>야구, 함께 보러가요!</h1>
        <button @click="joinUs" class="join-btn">시작하기</button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "HeroSection",
  methods: {
    joinUs() {
      this.$router.push({ name: 'mainpage' });
    },
  },
};
</script>
<style scoped>
/* 기본 리셋 스타일 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body {
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
  overflow-x: hidden; /* 수평 스크롤 방지 */
}

/* Hero Section 스타일 */
.hero-section {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  align-items: center; /* 수직 가운데 정렬 */
  justify-content: center; /* 수평 가운데 정렬 */
  background: url('@/assets/imgs/baseball_stadium.jpg') no-repeat center center/cover;
}

/* 오버레이 */
.overlay {
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(0, 0, 0, 0.5); /* 검은색 반투명 오버레이 */
  width: 100%;
  height: 100%;
}

/* 콘텐츠 스타일 */
.hero-content {
  text-align: center;
  color: white;
}

.hero-content h1 {
  font-size: 2.5rem; /* 기본 폰트 크기 */
  margin-bottom: 20px;
}

.join-btn {
  background-color: #4CAF50; /* 버튼 색상: 녹색 */
  color: white;
  border: none;
  padding: 1em 2em; /* 유연한 단위 사용 */
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
  border-radius: 8px; /* 테두리를 둥글게 설정 */
}

.join-btn:hover {
  background-color: #45a049; /* 버튼에 호버 시 색상 변경: 더 어두운 녹색 */
}

/* 반응형 웹 디자인을 위한 미디어 쿼리 */
@media (max-width: 768px) {
  .hero-content h1 {
    font-size: 2rem;
  }
  
  .join-btn {
    padding: 0.8em 1.6em;
    font-size: 0.9rem;
  }
}

@media (max-width: 480px) {
  .hero-content h1 {
    font-size: 1.5rem;
  }

  .join-btn {
    padding: 0.6em 1.2em;
    font-size: 0.8rem;
  }
}
</style>
