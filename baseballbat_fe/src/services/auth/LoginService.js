import http from "@/utils/http-common";  // http-common.js 파일을 임포트하여 Axios 인스턴스를 사용합니다.
import store from '@/store'; // Vuex 스토어를 임포트

class LoginService {
  // 사용자 로그인
  async login(loginReq) {
    try {
      // 백엔드 API에 로그인 요청을 보냅니다.
      const response = await http.post('/auth/login', loginReq);
      
      // 성공적으로 로그인되면 서버로부터 반환된 토큰을 받습니다.
      const { username, token } = response.data;

      // Vuex 상태에 로그인 정보 저장
      store.dispatch('login', { username, token });

      return { username, token }; // 로그인된 사용자 이름과 토큰을 반환합니다.
    } catch (error) {
      console.error('로그인 오류:', error);
      throw error; // 오류가 발생하면 호출한 곳에서 처리할 수 있도록 던집니다.
    }
  }

  // 토큰 설정 메소드 추가
  setAuthToken(token) {
    if (token) {
      // Authorization 헤더에 토큰 설정
      http.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    } else {
      // 토큰이 없으면 Authorization 헤더 제거
      delete http.defaults.headers.common['Authorization'];
    }
  }

    // 네이버 콜백으로 받은 인증 코드로 서버에 로그인 요청
    async naverLoginCallback({ code, state }) {
      try {
        const response = await http.get(`/naver/callback?code=${code}&state=${state}`);
        return response;
      } catch (error) {
        console.error('네이버 로그인 요청 오류:', error);
        throw error;
      }
    }
}

export default new LoginService();
