<template>
  <div class="stadium-info-container">
    <!-- 대한민국 지도 영역 -->
    <div class="map-section">
      <img src="@/assets/imgs/korea-map.png" alt="Korea Map" class="korea-map" />
      <div v-for="team in teams" :key="team.id" :class="['team-logo', team.className]" @click="selectTeam(team)">
        <img :src="team.logo" :alt="team.name" />
      </div>
    </div>

    <!-- 구단 정보 영역 -->
    <div class="info-section">
      <div v-if="selectedTeam">
        <h2>{{ selectedTeam.name }}</h2>
        <p>{{ selectedTeam.info }}</p>
      </div>
      <div v-else>
        <p>구단을 선택하면 정보가 표시됩니다.</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      teams: [
        { id: 1, name: 'LG 트윈스', logo: require('@/assets/team_logos/lg-logo.png'), className: 'lg-twins', info: '서울을 연고로 하는 LG 트윈스입니다.' },
        { id: 2, name: '두산 베어스', logo: require('@/assets/team_logos/doosan-logo.png'), className: 'doosan-bears', info: '서울을 연고로 하는 두산 베어스입니다.' },
        { id: 3, name: 'SSG 랜더스', logo: require('@/assets/team_logos/ssg-logo.png'), className: 'ssg-landers', info: '인천을 연고로 하는 SSG 랜더스입니다.' },
        { id: 4, name: '키움 히어로즈', logo: require('@/assets/team_logos/kiwoom-logo.png'), className: 'kiwoom-heroes', info: '서울을 연고로 하는 키움 히어로즈입니다.' },
        { id: 5, name: '한화 이글스', logo: require('@/assets/team_logos/hanwha-logo.png'), className: 'hanwha-eagles', info: '대전을 연고로 하는 한화 이글스입니다.' },
        { id: 6, name: 'KIA 타이거즈', logo: require('@/assets/team_logos/kia-logo.png'), className: 'kia-tigers', info: '광주를 연고로 하는 KIA 타이거즈입니다.' },
        { id: 7, name: '삼성 라이온즈', logo: require('@/assets/team_logos/samsung-logo.png'), className: 'samsung-lions', info: '대구를 연고로 하는 삼성 라이온즈입니다.' },
        { id: 8, name: '롯데 자이언츠', logo: require('@/assets/team_logos/lotte-logo.png'), className: 'lotte-giants', info: '부산을 연고로 하는 롯데 자이언츠입니다.' },
        { id: 9, name: 'NC 다이노스', logo: require('@/assets/team_logos/nc-logo.png'), className: 'nc-dinos', info: '창원을 연고로 하는 NC 다이노스입니다.' },
        { id: 10, name: 'KT 위즈', logo: require('@/assets/team_logos/kt-logo.png'), className: 'kt-wiz', info: '수원을 연고로 하는 KT 위즈입니다.' }
      ],
      selectedTeam: null,
    };
  },
  methods: {
    selectTeam(team) {
      this.selectedTeam = team;
    },
  },
};
</script>

<style scoped>
.stadium-info-container {
  display: flex;
  flex-wrap: wrap;
  padding: 20px;
  justify-content: center;
  margin-top: 60px; /* 헤더 높이만큼 마진 추가 */
}

.map-section {
  width: 40%;
  position: relative;
  text-align: center;
}

.korea-map {
  width: 100%;
  max-width: 500px;
}

.team-logo {
  position: absolute;
  cursor: pointer;
  transform: translate(-50%, -50%);
  width: 30px;
  height: 30px;
}

/* 각 구단 로고 위치 설정 */
.lg-twins {
  top: 17%;
  left: 36%;
}
.doosan-bears {
  top: 17%;
  left: 43%;
}
.ssg-landers {
  top: 26%;
  left: 26%;
}
.kiwoom-heroes {
  top: 24%;
  left: 43%;
}
.hanwha-eagles {
  top: 51%;
  left: 41%;
}
.kia-tigers {
  top: 78%;
  left: 34%;
}
.samsung-lions {
  top: 63%;
  left: 59%;
}
.lotte-giants {
  top: 77%;
  left: 67%;
}
.nc-dinos {
  top: 75%;
  left: 58%;
}
.kt-wiz {
  top: 38%;
  left: 40%;
}

.info-section {
  width: 40%;
  padding: 20px;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
}

/* 반응형 스타일 */
@media (max-width: 768px) {
  .stadium-info-container {
    flex-direction: column;
  }
  .map-section, .info-section {
    width: 100%;
  }
  .team-logo {
    width: 20px; /* 로고 크기를 줄여서 반응형에 맞춤 */
    height: 20px;
  }
    /* 각 구단 로고 위치 조정 */
  .lg-twins {
    top: 18%;
    left: 32%;
  }
  .doosan-bears {
    top: 18%;
    left: 40%;
  }
  .ssg-landers {
    top: 25%;
    left: 25%;
  }
  .kiwoom-heroes {
    top: 25%;
    left: 40%;
  }
  .hanwha-eagles {
    top: 51%;
    left: 40%;
  }
  .kia-tigers {
    top: 78%;
    left: 31%;
  }
  .samsung-lions {
    top: 62%;
    left: 59%;
  }
  .lotte-giants {
    top: 78%;
    left: 68%;
  }
  .nc-dinos {
    top: 75%;
    left: 58%;
  }
  .kt-wiz {
    top: 35%;
    left: 38%;
  }
}
</style>
