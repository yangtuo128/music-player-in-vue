<template>
  <section class="page-wrap" ref="pageWrap">
    <header class="play-control-header">
      <back-btn class="back-btn"></back-btn>
      <div class="song-info">
        <h1 class="song-name ellipsis">{{this.$route.query.name}}</h1>
        <p class="singers ellipsis">{{this.$route.query.singers}}</p>
      </div>
    </header>
    <section class="main">
      <div class="cover-img-box">
        <div class="img-wrap" ref="rotateCD">
          <img alt class="cover-img" v-lazy="this.$route.params.coverImgUrl">
        </div>
      </div>
    </section>
    <section class="play-control-footer">
      <div class="progress-wrap">
        <span class="has-played-time">{{currentTime | formatTime}}</span>
        <span class="total-progress" @click="handleProgressClick" @mousedown="startDrag">
          <span class="has-played-progress" ref="progress"></span>
          <span class="progress-dot" ref="progressDot"></span>
        </span>
        <span class="total-time">{{duration | formatTime}}</span>
      </div>
      <div class="control-panel">
        <button class="star-me-btn" @click="toggleStarStatus">
          <i :class="starBtnStyle"></i>
        </button>
        <button class="play-last-song-btn">
          <i class="iconfont icon-shangyishoushangyige"></i>
        </button>
        <button class="play-stop-btn" @click="toggleStatus">
          <i :class="iconClassname"></i>
        </button>
        <button class="play-next-song-btn">
          <i class="iconfont icon-xiayigexiayishou"></i>
        </button>
        <button class="volume-btn" @click="toggleVolumePanel">
          <i :class="volumeIconClass"></i>
        </button>
      </div>
      <!-- 音量控制面板 -->
      <div class="volume-panel" v-show="showVolumePanel">
        <div class="volume-slider" @click="handleVolumeClick" @mousedown="startVolumeDrag">
          <span class="volume-progress" ref="volumeProgress"></span>
          <span class="volume-dot" ref="volumeDot"></span>
        </div>
      </div>
    </section>
  </section>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import BackBtn from '../../components/BackBtn.vue';
import { getScreenHeight } from '../../utils/utils';
import { toggleStarTheSong } from '../../api/PlayControl/PlayControl';

export default {
  name: 'PlayControl',
  data() {
    return {
      iconClassname: 'iconfont icon-zanting',
      showVolumePanel: false,
      volume: 0.7, // 默认音量70%
      isDragging: false,
      isVolumeDragging: false,
    };
  },
  computed: {
    ...mapGetters([
      'duration',
      'isPlaying',
      'currentTime',
      'likelist',
      'songInfo',
    ]),
    songId() {
      return this.songInfo.id;
    },
    thisSongInLikelist() {
      return this.likelist.indexOf(+this.songId) === -1;
    },
    starBtnStyle() {
      return this.thisSongInLikelist
        ? 'iconfont icon-aixin1'
        : 'iconfont icon-aixin1 red';
    },
    volumeIconClass() {
      if (this.volume === 0) {
        return 'iconfont icon-jingyin';
      } else if (this.volume < 0.5) {
        return 'iconfont icon-yinliang1';
      } else {
        return 'iconfont icon-yinliang2';
      }
    },
    progressPercent() {
      return this.duration > 0 ? (this.currentTime / this.duration) * 100 : 0;
    },
    volumePercent() {
      return this.volume * 100;
    },
  },
  components: {
    BackBtn,
  },
  methods: {
    ...mapActions([
      'getThenSetSongInfo',
      'getThenSetIsPlaying',
      'getThenSetLikelist',
    ]),
    // 设置页面高度为铺满整个屏幕
    setPageHeight() {
      this.$refs.pageWrap.style.height = getScreenHeight();
    },
    toggleToPlayStatus() {
      this.iconClassname = 'iconfont icon-zanting';
      $(this.$refs.rotateCD).addClass('rotate');
    },
    toggleToStopStatus() {
      this.iconClassname = 'iconfont icon-bofang1';
      $(this.$refs.rotateCD).removeClass('rotate');
    },
    toggleStatus() {
      // 切换播放和暂停状态
      this.getThenSetIsPlaying(!this.isPlaying);
    },
    async toggleStarStatus() {
      try {
        const like = this.thisSongInLikelist;
        await toggleStarTheSong(this.songId, like);
        const uid = localStorage.getItem('uid');
        this.getThenSetLikelist(uid);
      } catch (error) {
        if (error.code === 301) {
          this.$router.push('/login');
        } else {
          console.log(error);
        }
      }
    },
    // 进度条点击事件
    handleProgressClick(e) {
      if (this.isDragging) return;
      const progressBar = e.currentTarget;
      const clickX = e.offsetX;
      const barWidth = progressBar.offsetWidth;
      const percent = clickX / barWidth;
      const newTime = percent * this.duration;
      
      // 发送消息给MyAudio组件，设置播放进度
      this.$root.$emit('setAudioTime', newTime);
    },
    // 开始拖拽进度条
    startDrag(e) {
      this.isDragging = true;
      this.drag(e);
      document.addEventListener('mousemove', this.drag);
      document.addEventListener('mouseup', this.stopDrag);
    },
    // 拖拽进度条
    drag(e) {
      if (!this.isDragging) return;
      const progressBar = this.$refs.progress.parentElement;
      const barWidth = progressBar.offsetWidth;
      let clientX = e.clientX;
      const rect = progressBar.getBoundingClientRect();
      
      // 限制拖拽范围在进度条内
      clientX = Math.max(rect.left, Math.min(rect.right, clientX));
      const clickX = clientX - rect.left;
      const percent = clickX / barWidth;
      const newTime = percent * this.duration;
      
      // 更新进度条显示
      this.$refs.progress.style.width = `${percent * 100}%`;
      this.$refs.progressDot.style.left = `${percent * 100}%`;
      
      // 发送消息给MyAudio组件，设置播放进度
      this.$root.$emit('setAudioTime', newTime);
    },
    // 停止拖拽进度条
    stopDrag() {
      this.isDragging = false;
      document.removeEventListener('mousemove', this.drag);
      document.removeEventListener('mouseup', this.stopDrag);
    },
    // 音量按钮点击事件
    toggleVolumePanel() {
      this.showVolumePanel = !this.showVolumePanel;
    },
    // 音量条点击事件
    handleVolumeClick(e) {
      if (this.isVolumeDragging) return;
      const volumeBar = e.currentTarget;
      const clickX = e.offsetX;
      const barWidth = volumeBar.offsetWidth;
      this.volume = Math.max(0, Math.min(1, clickX / barWidth));
      
      // 发送消息给MyAudio组件，设置音量
      this.$root.$emit('setAudioVolume', this.volume);
    },
    // 开始拖拽音量条
    startVolumeDrag(e) {
      this.isVolumeDragging = true;
      this.volumeDrag(e);
      document.addEventListener('mousemove', this.volumeDrag);
      document.addEventListener('mouseup', this.stopVolumeDrag);
    },
    // 拖拽音量条
    volumeDrag(e) {
      if (!this.isVolumeDragging) return;
      const volumeBar = this.$refs.volumeProgress.parentElement;
      const barWidth = volumeBar.offsetWidth;
      let clientX = e.clientX;
      const rect = volumeBar.getBoundingClientRect();
      
      // 限制拖拽范围在音量条内
      clientX = Math.max(rect.left, Math.min(rect.right, clientX));
      const clickX = clientX - rect.left;
      this.volume = Math.max(0, Math.min(1, clickX / barWidth));
      
      // 发送消息给MyAudio组件，设置音量
      this.$root.$emit('setAudioVolume', this.volume);
    },
    // 停止拖拽音量条
    stopVolumeDrag() {
      this.isVolumeDragging = false;
      document.removeEventListener('mousemove', this.volumeDrag);
      document.removeEventListener('mouseup', this.stopVolumeDrag);
    },
  },
  watch: {
    isPlaying(newStatus) {
      // 切换播放和暂停状态
      newStatus ? this.toggleToPlayStatus() : this.toggleToStopStatus();
    },
    currentTime(newTime) {
      // 更新播放进度（非拖拽状态下）
      if (!this.isDragging && this.$refs.progress) {
        this.$refs.progress.style.width = `${(newTime / this.duration) * 100}%`;
        this.$refs.progressDot.style.left = `${(newTime / this.duration) * 100}%`;
      }
    },
    volumePercent(newVal) {
      // 更新音量条显示
      if (this.$refs.volumeProgress) {
        this.$refs.volumeProgress.style.width = `${newVal}%`;
        this.$refs.volumeDot.style.left = `${newVal}%`;
      }
    },
  },
  filters: {
    formatTime(time) {
      // 取整且补零
      const mm = `${Math.floor(time / 60)}`.padStart(2, '0');
      const ss = `${Math.floor(time % 60)}`.padStart(2, '0');
      return `${mm}:${ss}`;
    },
  },
  created() {
    const uid = localStorage.getItem('uid');
    // 选中一个新的歌曲，且设置好了歌曲的url后，就将isPlaying设为true
    // 其他状态都由isPlaying的状态，或者MyAudio组件的currentTime决定
    this.getThenSetSongInfo({
      ...this.$route.query,
      coverImgUrl: this.$route.params.coverImgUrl,
    });
    this.getThenSetIsPlaying(true); // 这行代码可能写在MyAudio的watch里更好
    // 获取用户的收藏歌曲列表以确定爱心按钮的样式
    this.getThenSetLikelist(uid);
  },
  mounted() {
    // 设置页面高度为整屏
    this.setPageHeight();
    
    // 初始化进度条和音量条
    this.$nextTick(() => {
      if (this.$refs.progress) {
        this.$refs.progress.style.width = `${this.progressPercent}%`;
        this.$refs.progressDot.style.left = `${this.progressPercent}%`;
      }
      if (this.$refs.volumeProgress) {
        this.$refs.volumeProgress.style.width = `${this.volumePercent}%`;
        this.$refs.volumeDot.style.left = `${this.volumePercent}%`;
      }
    });
  },
  destroyed() {
    // 移除事件监听器
    document.removeEventListener('mousemove', this.drag);
    document.removeEventListener('mouseup', this.stopDrag);
    document.removeEventListener('mousemove', this.volumeDrag);
    document.removeEventListener('mouseup', this.stopVolumeDrag);
  },
};
</script>

<style scoped lang='less'>
// lazy-load样式
img[lazy="loading"] {
  width: 100%;
  height: 100%;
}

@keyframes infiniteRotate {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

.rotate {
  animation: infiniteRotate 10s infinite linear;
}

.page-wrap {
  background: rgba(0, 0, 0, 0.3);
  .play-control-header {
    position: relative;
    height: 10%;
    .back-btn {
      color: #f1f1f1;
      width: 0.5rem;
      height: 0.5rem;
      text-align: center;
      position: absolute;
      left: 0.01rem;
      top: 20%;
    }
    .song-info {
      .song-name {
        color: #f1f1f1;
        font-size: 0.2rem;
        padding: 0.1rem 0 0;
        text-align: center;
      }
      .singers {
        text-align: center;
        color: lightgray;
        font-size: 0.16rem;
      }
    }
  }
  .main {
    height: 72%;
    box-sizing: border-box;
    display: flex;
    justify-content: space-around;
    align-items: center;
    .cover-img-box {
      width: 80vw;
      height: 80vw;
      background-color: #2e3030;
      border-radius: 50%;
      overflow: hidden;
      .img-wrap {
        width: 70%;
        height: 70%; // 设置高度以让子元素宽高相同
        margin: 15% auto 0;
        border-radius: 50%;
        overflow: hidden;
        .cover-img {
          width: 100%;
          height: 100%; // 不设置高在某些情况下会有BUG
        }
      }
    }
  }
  // 固定定位应该有问题，那要如何设置呢？
  .play-control-footer {
    height: 18%;
    .progress-wrap {
      display: flex;
      align-items: center;
      justify-content: space-around;
      padding: 0 0.1rem;
      font-size: 0.16rem;
      .has-played-time {
        color: #f1f1f1;
      }
      .total-time {
        color: lightgray;
      }
      .total-progress {
        position: relative;
        width: 70%;
        height: 0.03rem;
        background: lightgray;
        cursor: pointer;
        .has-played-progress {
          display: block;
          width: 0;
          height: 100%;
          background-color: #d44439;
        }
        .progress-dot {
          position: absolute;
          top: 50%;
          left: 0;
          transform: translate(-50%, -50%);
          width: 0.1rem;
          height: 0.1rem;
          border-radius: 50%;
          background-color: #d44439;
          cursor: pointer;
        }
      }
    }
    .control-panel {
      display: flex;
      justify-content: space-around;
      > * {
        margin: 0.2rem 0;
        .iconfont {
          font-size: 0.3rem;
          color: #f1f1f1;
        }
      }
      .star-me-btn {
        .iconfont.red {
          color: red;
        }
      }
      .play-stop-btn {
        .iconfont {
          font-size: 0.5rem;
        }
      }
    }
    .volume-panel {
      position: absolute;
      bottom: 1rem;
      right: 0.3rem;
      width: 1.5rem;
      padding: 0.1rem;
      background: rgba(0, 0, 0, 0.5);
      border-radius: 0.05rem;
      .volume-slider {
        position: relative;
        width: 100%;
        height: 0.03rem;
        background: lightgray;
        cursor: pointer;
        .volume-progress {
          display: block;
          width: 70%;
          height: 100%;
          background-color: #d44439;
        }
        .volume-dot {
          position: absolute;
          top: 50%;
          left: 70%;
          transform: translate(-50%, -50%);
          width: 0.1rem;
          height: 0.1rem;
          border-radius: 50%;
          background-color: #d44439;
          cursor: pointer;
        }
      }
    }
  }
}
</style>
