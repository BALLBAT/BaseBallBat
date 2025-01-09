import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  // 직관적으로 첫 화면
  {
    path: '/',
    name: 'home',
    component: () => import("../views/Home/HomePage.vue")
  },
  // 메인 페이지
  {
    path: '/main',
    name: 'mainpage',
    component: () => import("../views/Main/MainPage.vue")
  },

  
// 유저 관련 페이지

  // 로그인 페이지
  {
    path: '/login',
    name: 'loginpage',
    component: () => import("../views/User/LoginPage.vue")
  },
  // 추가 정보 입력 페이지
  {
    path: '/additional-info',
    name: 'additionalinfopage',
    component: () => import("../views/User/AddInfo.vue")
  },
  // 네이버 콜백 페이지
  {
    path: '/naver/callback',
    name: 'NaverCallback',
    component: () => import("../views/User/CallBack/NaverCallBack.vue")
  },
  // 회원가입 페이지
  {
    path: '/register',
    name: 'registerpage',
    component: () => import("../views/User/RegisterPage.vue")
  },
  // ID 찾기 공통 페이지
  {
    path: '/find-id-common',
    name: 'findidcommon',
    component: () => import("../views/User/FindId/FindIdCommon.vue")
  },
  // ID 찾기 페이지
  {
    path: '/find-id',
    name: 'findid',
    component: () => import("../views/User/FindId/FindId.vue")
  },
  // ID 찾기 팝업 페이지
  {
    path: '/find-id-popup',
    name: 'findidpopup',
    component: () => import("../views/User/FindId/FindIdPopup.vue")
  },
  // Password 찾기 공통 페이지
  {
    path: '/find-password-common',
    name: 'findpasswordcommon',
    component: () => import("../views/User/FindPassword/FindPasswordCommon.vue")
  },
  // Password 찾기 페이지
  {
    path: '/find-password',
    name: 'findpassword',
    component: () => import("../views/User/FindPassword/FindPassword.vue")
  },
  // Password 찾기 팝업 페이지
  {
    path: '/find-password-popup',
    name: 'findpasswordpopup',
    component: () => import("../views/User/FindPassword/FindPasswordPopup.vue")
  },
  // 마이페이지
  {
    path: '/mypage',
    name: 'mypage',
    component: () => import("../views/User/MyPage.vue")
  },
  // 프로필 수정 페이지
  {
    path: '/profile-edit',
    name: 'profileedit',
    component: () => import("../views/User/ProfileEdit.vue")
  },

// 매칭 게시판

  // 매칭게시판 메인 페이지
  {
    path: '/matching',
    name: 'matchingmain',
    component: () => import("../views/Matching/MatchingMain.vue")
  },
  // 매칭 게시판 상세 페이지
  {
    path: '/matching-detail',
    name: 'matchingdetail',
    component: () => import("../views/Matching/MatchingDetail.vue")
  },
  // 매칭 게시판 등록 페이지
  {
    path: '/matching-create',
    name: 'matchingcreate',
    component: () => import("../views/Matching/MatchingCreate.vue")
  },

// 중고거래 관련 페이지

  // 중고거래 메인 페이지
  {
    path: '/trade',
    name: 'trademain',
    component: () => import("../views/Trade/TradeMain.vue")
  },
  // 중고거래 상세 페이지
  {
    path: '/trade-detail',
    name: 'tradedetail',
    component: () => import("../views/Trade/TradeDetail.vue")
  },
  // 중고거래 물품 등록 페이지
  {
    path: '/trade-create',
    name: 'tradecreate',
    component: () => import("../views/Trade/TradeCreate.vue")
  },

// 결제 페이지

  // 결제창

 // 맛집 관련 페이지
  
  // 맛집 메인 페이지
  {
    path: '/restaurant',
    name: 'restaurant',
    component: () => import("../views/Restaurant/RestaurantMain.vue")
  },

// 구단 정보 페이지

  // 구단 정보 소개 메인 페이지
  {
    path: '/teaminfo',
    name: 'teaminfo',
    component: () => import("../views/Teams/TeamInfoMain.vue")
  },
    // 구단 정보 소개 상세 페이지
    {
      path: '/teaminfo-detail',
      name: 'teaminfodetail',
      component: () => import("../views/Teams/TeamInfoDetail.vue")
    },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
