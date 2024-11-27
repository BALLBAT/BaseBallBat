<template>
  <div>로그인 중...</div>
</template>

<script>
import LoginService from '@/services/auth/LoginService';

export default {
  name: 'NaverCallback',
  async mounted() {
    // URL에서 네이버의 인증 코드와 상태 값을 가져옵니다.
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');
    const state = urlParams.get('state');

    if (code) {
      try {
        // 네이버 로그인 요청을 서버로 전달
        const response = await LoginService.naverLoginCallback({ code, state });
        
        // 성공적으로 로그인한 경우 토큰을 저장하고 메인 페이지로 이동
        const { token, username } = response.data;
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
