<template>
  <div>
    <footer class="footer bg-dark text-white mt-5">
      <div class="container py-4">
        <div class="row">
          <div class="col-md-6">
            <h5>직관적으로</h5>
            <p>야구 팬들을 위한 최고의 커뮤니티입니다. 직관적으로 야구를 함께 즐겨보세요!</p>
          </div>
          <div class="col-md-6 text-md-right">
            <ul class="list-unstyled">
              <li><router-link class="text-white" to="/privacy">개인정보 처리방침</router-link></li>
              <li><router-link class="text-white" to="/terms">이용 약관</router-link></li>
              <li><router-link class="text-white" to="/contact">문의하기</router-link></li>
            </ul>
          </div>
        </div>
        <div class="text-center mt-3">
          <small>&copy; 2024 직관적으로. All rights reserved.</small>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: "SiteHeader",
  methods: {
    login() {
      // 로그인 버튼 클릭 시 동작할 로직을 추가합니다.
      console.log("로그인 버튼이 클릭되었습니다!");
    },
  },
};
</script>

<style scoped>
/* 추가적인 커스터마이징 스타일이 필요할 경우 여기에 작성하세요 */
.footer {
  padding: 1.5rem 0;
  background-color: #333;
  color: #fff;
}
.footer a {
  color: #fff;
  text-decoration: none;
  transition: color 0.3s ease;
}
.footer a:hover {
  color: #4CAF50;
}
</style>
