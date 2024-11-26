import http from "@/utils/http-common";  // http-common.js 파일을 임포트하여 Axios 인스턴스를 사용합니다.

class LoginService {
  // 사용자 로그인
  async login(loginReq) {
    try {
      // 백엔드 API에 로그인 요청을 보냅니다.
      const response = await http.post('/auth/login', loginReq);
      
      // 성공적으로 로그인되면 서버로부터 반환된 토큰을 받습니다.
      const { username, token } = response.data;

      // 여기서 토큰을 로컬 스토리지 등에 저장하거나 상태 관리를 통해 보관할 수 있습니다.
      localStorage.setItem('userToken', token);

      return { username, token }; // 로그인된 사용자 이름과 토큰을 반환합니다.
    } catch (error) {
      console.error('로그인 오류:', error);
      throw error; // 오류가 발생하면 호출한 곳에서 처리할 수 있도록 던집니다.
    }
  }
}

export default new LoginService();
