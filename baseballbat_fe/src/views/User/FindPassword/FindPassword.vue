<template>
  <div class="find-password-container">
    <FindPasswordCommon />
    <p class="info-text">이메일 인증을 통해 비밀번호를 찾으실 수 있습니다.</p>
  </div>
</template>

<script>
import FindPasswordCommon from './FindPasswordCommon.vue';

export default {
  name: 'FindPassword',
  components: {
    FindPasswordCommon,
  },
};
</script>

<style scoped>
.find-password-container {
  padding: 30px;
  width: 100%;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.info-text {
  margin-top: 20px;
  font-size: 14px;
  color: #666;
}
</style>
