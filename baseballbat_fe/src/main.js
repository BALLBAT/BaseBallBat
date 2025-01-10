import { createApp } from 'vue';
import App from './App.vue';
import './registerServiceWorker';
import router from './router';
import store from './store';

// 부트스트랩 추가
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

// 전역 스타일링 설정 (KBO 폰트)
import '@/assets/css/global.css';

// Google OAuth 라이브러리 추가
import GoogleAuth from 'vue3-google-oauth2';

const app = createApp(App);

store.dispatch('checkLoginState');

// Google OAuth2 설정 객체
const gAuthOptions = {
  clientId: '69828466810-cho87reh7vfoke4ef242tr6g610bsupj.apps.googleusercontent.com',  // 발급받은 클라이언트 ID를 입력하세요
  scope: 'profile email',
  prompt: 'consent'
};

// GoogleAuth 설정을 Vue 앱에 등록
app.use(GoogleAuth, gAuthOptions);

// 기타 플러그인 사용 및 애플리케이션 마운트
app.use(store).use(router).mount('#app');
