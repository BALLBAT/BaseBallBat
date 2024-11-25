<template>
  <div class="find-id-container">
    <FindIdCommon />
    <p class="info-text">
      이메일 인증을 통해 가입하신 아이디를 찾으실 수 있습니다.
    </p>
  </div>
</template>

<script>
import FindIdCommon from './FindIdCommon.vue';

export default {
  components: {
    FindIdCommon,
  },
};
</script>

<style scoped>
.find-id-container {
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
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
