<template>
  <div>로그인 중...</div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'NaverCallback',
  async mounted() {
    // URL에서 네이버의 인증 코드와 상태 값을 가져옵니다.
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');
    const state = urlParams.get('state');

    if (code) {
      try {
        // 네이버 로그인 인증 코드를 백엔드 서버로 전달하여 처리합니다.
        const response = await axios.get('http://localhost:8000/api/naver/callback', {
          params: {
            code: code,
            state: state
          }
        });

        // 백엔드에서 JWT 토큰을 발급받아 저장하고, 메인 페이지로 이동합니다.
        const token = response.data;
        localStorage.setItem('authToken', token);
        this.$router.push('/main');
      } catch (error) {
        console.error('네이버 로그인 오류:', error);
        alert('네이버 로그인에 실패했습니다.');
        this.$router.push('/login');
      }
    } else {
      alert('로그인에 실패했습니다.');
      this.$router.push('/login');
    }
  },
};
</script>
