import http from "@/utils/http-common";  // http-common.js 파일을 임포트

class UserService {
  // 회원 가입
  async registerUser(userReq) {
    try {
      const response = await http.post('/user/register', userReq);
      return response.data; // 성공적으로 등록된 사용자 정보 반환 (UserRes 형식)
    } catch (error) {
      console.error('회원 가입 오류:', error);
      throw error; // 오류가 발생하면 호출한 곳에서 처리할 수 있도록 던짐
    }
  }

  // 사용자명으로 사용자 조회
  async getUserByUsername(username) {
    try {
      const response = await http.get(`/user/username/${username}`);
      return response.data; // 조회된 사용자 정보 반환 (UserRes 형식)
    } catch (error) {
      console.error('사용자명으로 사용자 조회 오류:', error);
      throw error;
    }
  }

  // 이메일로 사용자 조회
  async getUserByEmail(email) {
    try {
      const response = await http.get(`/user/email/${email}`);
      return response.data; // 조회된 사용자 정보 반환 (UserRes 형식)
    } catch (error) {
      console.error('이메일로 사용자 조회 오류:', error);
      throw error;
    }
  }

    // 아이디 중복 확인
    async checkUsernameAvailability(username) {
    try {
        const response = await http.get(`/user/check-username/${username}`);
        return response.data; // true면 사용 가능, false면 사용 불가
    } catch (error) {
        console.error('아이디 중복 확인 오류:', error);
        throw error;
    }
    }

  // 이메일 중복 확인
  async checkEmailAvailability(email) {
    try {
      const response = await http.get(`/user/check-email/${email}`);
      return response.data; // true면 사용 가능, false면 사용 불가
    } catch (error) {
      console.error('이메일 중복 확인 오류:', error);
      throw error;
    }
  }

  // 사용자 정보 수정
  async updateUser(userId, updatedUserReq) {
    try {
      const response = await http.put(`/user/${userId}`, updatedUserReq);
      return response.data; // 수정된 사용자 정보 반환 (UserRes 형식)
    } catch (error) {
      console.error('사용자 정보 수정 오류:', error);
      throw error;
    }
  }

  // 사용자 삭제
  async deleteUser(userId) {
    try {
      await http.delete(`/user/${userId}`);
      return true; // 삭제 완료 반환
    } catch (error) {
      console.error('사용자 삭제 오류:', error);
      throw error;
    }
  }
}
export default new UserService();
