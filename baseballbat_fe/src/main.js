import { createApp } from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'

// 부트스트랩 추가
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

// 전역 스타일링 설정 (KBO 폰트)
import '@/assets/css/global.css';

createApp(App).use(store).use(router).mount('#app')
