<template>
  <div class="member-center">
    <!-- 会员信息区域 -->
    <div class="member-info">
      <div class="member-avatar">
        <img :src="memberInfo.avatar" alt="会员头像" />
        <div class="member-level">{{ memberInfo.level }}</div>
      </div>
      <div class="member-details">
        <div class="member-name">{{ memberInfo.name }}</div>
        <div class="member-expire">{{ memberInfo.expireTime }}</div>
      </div>
      <div class="member-status">
        <span :class="['status-icon', memberInfo.status]" />
        <span class="status-text">{{ memberInfo.statusText }}</span>
      </div>
    </div>

    <!-- 会员统计数据 -->
    <div class="member-stats">
      <div class="stat-item">
        <div class="stat-number">{{ stats.playCount }}</div>
        <div class="stat-label">已播放</div>
      </div>
      <div class="stat-item">
        <div class="stat-number">{{ stats.collectCount }}</div>
        <div class="stat-label">已收藏</div>
      </div>
      <div class="stat-item">
        <div class="stat-number">{{ stats.shareCount }}</div>
        <div class="stat-label">已分享</div>
      </div>
      <div class="stat-item">
        <div class="stat-number">{{ stats.followCount }}</div>
        <div class="stat-label">关注</div>
      </div>
    </div>

    <!-- 会员等级进度条 -->
    <div class="level-progress">
      <div class="progress-header">
        <div class="current-level">Lv{{ currentLevel }}</div>
        <div class="progress-text">
          {{ currentExp }}/{{ nextLevelExp }} 经验值
          <span class="progress-percent">({{ progressPercent }}%)</span>
        </div>
      </div>
      <div class="progress-bar">
        <div class="progress-fill" :style="{ width: progressPercent + '%' }" />
      </div>
      <div class="progress-actions">
        <button class="action-btn" @click="showTaskModal = true">每日任务</button>
        <button class="action-btn" @click="showUpgradeModal = true">升级攻略</button>
      </div>
    </div>

    <!-- 会员福利列表 -->
    <div class="member-benefits">
      <div class="benefits-header">
        <h3>会员福利</h3>
        <a href="#" class="view-all">查看全部</a>
      </div>
      <div class="benefits-list">
        <div class="benefit-item" v-for="benefit in benefits" :key="benefit.id">
          <div class="benefit-icon">{{ benefit.icon }}</div>
          <div class="benefit-info">
            <div class="benefit-title">{{ benefit.title }}</div>
            <div class="benefit-desc">{{ benefit.description }}</div>
          </div>
          <div class="benefit-action">
            <button class="use-btn" :disabled="!benefit.available">{{ benefit.available ? '立即使用' : '已使用' }}</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 会员等级说明 -->
    <div class="level-explanation">
      <div class="explanation-header">
        <h3>等级说明</h3>
        <a href="#" class="view-all">查看全部</a>
      </div>
      <div class="level-list">
        <div class="level-item" v-for="level in levels" :key="level.level">
          <div class="level-info">
            <div class="level-number">Lv{{ level.level }}</div>
            <div class="level-name">{{ level.name }}</div>
          </div>
          <div class="level-requirements">
            <div class="exp-requirement">{{ level.expRequired }}经验值</div>
            <div class="benefits-list">
              <span class="benefit-tag" v-for="benefit in level.benefits" :key="benefit">{{ benefit }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 每日任务弹窗 -->
    <modal v-if="showTaskModal" @close="showTaskModal = false" title="每日任务">
      <div class="task-list">
        <div class="task-item" v-for="task in dailyTasks" :key="task.id">
          <div class="task-info">
            <div class="task-title">{{ task.title }}</div>
            <div class="task-desc">{{ task.description }}</div>
            <div class="task-reward">+{{ task.reward }}经验值</div>
          </div>
          <div class="task-status">
            <button class="task-btn" :disabled="task.completed" @click="completeTask(task.id)">
              {{ task.completed ? '已完成' : '去完成' }}
            </button>
          </div>
        </div>
      </div>
    </modal>

    <!-- 升级攻略弹窗 -->
    <modal v-if="showUpgradeModal" @close="showUpgradeModal = false" title="升级攻略">
      <div class="upgrade-guide">
        <div class="guide-item" v-for="guide in upgradeGuides" :key="guide.id">
          <div class="guide-icon">{{ guide.icon }}</div>
          <div class="guide-info">
            <div class="guide-title">{{ guide.title }}</div>
            <div class="guide-desc">{{ guide.description }}</div>
            <div class="guide-reward">+{{ guide.reward }}经验值/{{ guide.unit }}</div>
          </div>
        </div>
      </div>
    </modal>
  </div>
</template>

<script>
export default {
  name: 'MemberCenter',
  data() {
    return {
      memberInfo: {
        avatar: 'https://picsum.photos/seed/member-avatar/100/100.jpg',
        name: '尊贵的会员',
        level: 3,
        expireTime: '2023-12-31',
        status: 'active',
        statusText: '会员有效期内',
      },
      stats: {
        playCount: 1234,
        collectCount: 567,
        shareCount: 89,
        followCount: 123,
      },
      currentLevel: 3,
      currentExp: 750,
      nextLevelExp: 1000,
      benefits: [
        {
          id: 1,
          icon: '🎵',
          title: '无损音质',
          description: '享受高品质无损音乐播放',
          available: true,
        },
        {
          id: 2,
          icon: '📥',
          title: '免费下载',
          description: '每月可免费下载300首音乐',
          available: true,
        },
        {
          id: 3,
          icon: '🚫',
          title: '免广告',
          description: '享受无广告音乐体验',
          available: true,
        },
        {
          id: 4,
          icon: '🎁',
          title: '生日福利',
          description: '生日当月可领取专属礼品',
          available: false,
        },
        {
          id: 5,
          icon: '🎫',
          title: '演唱会优先购票',
          description: '享受演唱会门票优先购买权',
          available: true,
        },
        {
          id: 6,
          icon: '👨💼',
          title: '专属客服',
          description: '24小时专属客服服务',
          available: true,
        },
      ],
      levels: [
        {
          level: 1,
          name: '新手会员',
          expRequired: 0,
          benefits: ['基础音质', '每日推荐'],
        },
        {
          level: 2,
          name: '普通会员',
          expRequired: 500,
          benefits: ['标准音质', '免费下载100首', '个性化推荐'],
        },
        {
          level: 3,
          name: '高级会员',
          expRequired: 1000,
          benefits: ['无损音质', '免费下载300首', '免广告', '生日福利'],
        },
        {
          level: 4,
          name: 'VIP会员',
          expRequired: 2000,
          benefits: ['Hi-Res音质', '免费下载无限首', '免广告', '生日福利', '演唱会优先购票'],
        },
        {
          level: 5,
          name: '至尊VIP',
          expRequired: 5000,
          benefits: ['Hi-Res音质', '免费下载无限首', '免广告', '生日福利', '演唱会优先购票', '专属客服'],
        },
      ],
      dailyTasks: [
        {
          id: 1,
          title: '每日登录',
          description: '每天登录音乐平台',
          reward: 10,
          completed: false,
        },
        {
          id: 2,
          title: '播放音乐',
          description: '播放10首音乐',
          reward: 20,
          completed: false,
        },
        {
          id: 3,
          title: '收藏歌单',
          description: '收藏1个歌单',
          reward: 15,
          completed: false,
        },
        {
          id: 4,
          title: '分享音乐',
          description: '分享1首音乐到社交平台',
          reward: 25,
          completed: false,
        },
      ],
      upgradeGuides: [
        {
          id: 1,
          icon: '🎵',
          title: '每日听歌',
          description: '每天听音乐30分钟以上',
          reward: 30,
          unit: '天',
        },
        {
          id: 2,
          icon: '📚',
          title: '收藏歌单',
          description: '收藏喜欢的歌单',
          reward: 10,
          unit: '个',
        },
        {
          id: 3,
          icon: '📤',
          title: '分享音乐',
          description: '分享音乐给好友',
          reward: 20,
          unit: '次',
        },
        {
          id: 4,
          icon: '🎁',
          title: '购买会员',
          description: '购买或续费会员',
          reward: 100,
          unit: '月',
        },
      ],
      showTaskModal: false,
      showUpgradeModal: false,
    };
  },
  computed: {
    progressPercent() {
      return Math.round((this.currentExp / this.nextLevelExp) * 100);
    },
  },
  methods: {
    completeTask(taskId) {
      const task = this.dailyTasks.find(t => t.id === taskId);
      if (task && !task.completed) {
        task.completed = true;
        this.currentExp += task.reward;
        this.checkLevelUp();
        this.$toast(`任务完成！获得${task.reward}经验值`);
      }
    },
    checkLevelUp() {
      const nextLevel = this.levels.find(l => l.level === this.currentLevel + 1);
      if (nextLevel && this.currentExp >= nextLevel.expRequired) {
        this.currentLevel += 1;
        this.nextLevelExp = this.levels.find(l => l.level === this.currentLevel + 1)?.expRequired || this.nextLevelExp;
        this.$toast(`恭喜升级到Lv${this.currentLevel}！`);
      }
    },
    useBenefit(benefitId) {
      const benefit = this.benefits.find(b => b.id === benefitId);
      if (benefit && benefit.available) {
        benefit.available = false;
        this.$toast(`已使用${benefit.title}福利`);
      }
    },
    saveData() {
      // 保存数据到localStorage
      localStorage.setItem('memberData', JSON.stringify({
        memberInfo: this.memberInfo,
        stats: this.stats,
        currentLevel: this.currentLevel,
        currentExp: this.currentExp,
        nextLevelExp: this.nextLevelExp,
        benefits: this.benefits,
        dailyTasks: this.dailyTasks,
      }));
    },
  },
  mounted() {
    // 从localStorage加载数据
    const savedData = localStorage.getItem('memberData');
    if (savedData) {
      const {
        memberInfo, stats, currentLevel, currentExp, nextLevelExp, benefits, dailyTasks,
      } = JSON.parse(savedData);
      this.memberInfo = memberInfo;
      this.stats = stats;
      this.currentLevel = currentLevel;
      this.currentExp = currentExp;
      this.nextLevelExp = nextLevelExp;
      this.benefits = benefits;
      this.dailyTasks = dailyTasks;
    }
  },
  watch: {
    // 监听数据变化并保存到localStorage
    memberInfo: { handler: 'saveData', deep: true },
    stats: { handler: 'saveData', deep: true },
    currentLevel: 'saveData',
    currentExp: 'saveData',
    nextLevelExp: 'saveData',
    benefits: { handler: 'saveData', deep: true },
    dailyTasks: { handler: 'saveData', deep: true },
  },
};
</script>

<style scoped lang='less'>
.member-center {
  padding: 0.2rem;
  background-color: #f5f5f5;
  min-height: 100vh;
  padding-bottom: 0.7rem; /* 为底部菜单留出空间 */
}

/* 会员信息区域 */
.member-info {
  display: flex;
  align-items: center;
  padding: 0.3rem;
  background-color: #fff;
  border-radius: 0.1rem;
  margin-bottom: 0.2rem;
  box-shadow: 0 0.02rem 0.04rem rgba(0, 0, 0, 0.1);
}

.member-avatar {
  position: relative;
  width: 1rem;
  height: 1rem;
  border-radius: 50%;
  overflow: hidden;
  margin-right: 0.3rem;
  border: 0.03rem solid #d44439;

  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }

  .member-level {
    position: absolute;
    bottom: 0;
    right: 0;
    padding: 0.03rem 0.08rem;
    background-color: #d44439;
    color: #fff;
    font-size: 0.12rem;
    border-radius: 0.1rem;
  }
}

.member-details {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.member-name {
  font-size: 0.2rem;
  font-weight: bold;
  margin-bottom: 0.05rem;
  color: #333;
}

.member-expire {
  font-size: 0.14rem;
  color: #999;
}

.member-status {
  display: flex;
  align-items: center;
  padding: 0.05rem 0.1rem;
  background-color: rgba(76, 175, 80, 0.1);
  border-radius: 0.1rem;

  .status-icon {
    width: 0.08rem;
    height: 0.08rem;
    border-radius: 50%;
    margin-right: 0.05rem;

    &.active {
      background-color: #4caf50;
    }

    &.expired {
      background-color: #f44336;
    }

    &.pending {
      background-color: #ff9800;
    }
  }

  .status-text {
    font-size: 0.14rem;
    color: #4caf50;
  }
}

/* 会员统计数据 */
.member-stats {
  display: flex;
  justify-content: space-around;
  padding: 0.3rem;
  background-color: #fff;
  border-radius: 0.1rem;
  margin-bottom: 0.2rem;
  box-shadow: 0 0.02rem 0.04rem rgba(0, 0, 0, 0.1);
}

.stat-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.stat-number {
  font-size: 0.24rem;
  font-weight: bold;
  margin-bottom: 0.05rem;
  color: #d44439;
}

.stat-label {
  font-size: 0.14rem;
  color: #666;
}

/* 会员等级进度条 */
.level-progress {
  padding: 0.3rem;
  background-color: #fff;
  border-radius: 0.1rem;
  margin-bottom: 0.2rem;
  box-shadow: 0 0.02rem 0.04rem rgba(0, 0, 0, 0.1);
}

.progress-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.15rem;
}

.current-level {
  font-size: 0.18rem;
  font-weight: bold;
  color: #d44439;
}

.progress-text {
  font-size: 0.14rem;
  color: #666;

  .progress-percent {
    color: #d44439;
    font-weight: bold;
  }
}

.progress-bar {
  width: 100%;
  height: 0.06rem;
  background-color: #f0f0f0;
  border-radius: 0.03rem;
  overflow: hidden;
  margin-bottom: 0.15rem;
}

.progress-fill {
  height: 100%;
  background-color: #d44439;
  transition: width 0.3s ease;
}

.progress-actions {
  display: flex;
  gap: 0.1rem;
}

.action-btn {
  padding: 0.06rem 0.15rem;
  background-color: #d44439;
  color: #fff;
  border: none;
  border-radius: 0.05rem;
  font-size: 0.14rem;
  cursor: pointer;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: #c23328;
  }
}

/* 会员福利列表 */
.member-benefits {
  padding: 0.3rem;
  background-color: #fff;
  border-radius: 0.1rem;
  margin-bottom: 0.2rem;
  box-shadow: 0 0.02rem 0.04rem rgba(0, 0, 0, 0.1);
}

.benefits-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.2rem;
}

.benefits-header h3 {
  font-size: 0.18rem;
  font-weight: bold;
  color: #333;
}

.view-all {
  font-size: 0.14rem;
  color: #d44439;
  text-decoration: none;

  &:hover {
    text-decoration: underline;
  }
}

.benefits-list {
  display: flex;
  flex-direction: column;
  gap: 0.15rem;
}

.benefit-item {
  display: flex;
  align-items: center;
  padding: 0.15rem;
  border: 0.01rem solid #f0f0f0;
  border-radius: 0.08rem;
  transition: all 0.3s ease;

  &:hover {
    border-color: #d44439;
    box-shadow: 0 0.02rem 0.08rem rgba(212, 68, 57, 0.1);
  }
}

.benefit-icon {
  width: 0.4rem;
  height: 0.4rem;
  border-radius: 50%;
  background-color: rgba(212, 68, 57, 0.1);
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 0.2rem;
  margin-right: 0.15rem;
}

.benefit-info {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.benefit-title {
  font-size: 0.16rem;
  font-weight: bold;
  margin-bottom: 0.03rem;
  color: #333;
}

.benefit-desc {
  font-size: 0.14rem;
  color: #666;
  line-height: 1.4;
}

.benefit-action {
  margin-left: 0.15rem;
}

.use-btn {
  padding: 0.05rem 0.12rem;
  background-color: #d44439;
  color: #fff;
  border: none;
  border-radius: 0.04rem;
  font-size: 0.14rem;
  cursor: pointer;
  transition: background-color 0.3s ease;

  &:disabled {
    background-color: #ccc;
    cursor: not-allowed;
  }

  &:hover:not(:disabled) {
    background-color: #c23328;
  }
}

/* 会员等级说明 */
.level-explanation {
  padding: 0.3rem;
  background-color: #fff;
  border-radius: 0.1rem;
  margin-bottom: 0.2rem;
  box-shadow: 0 0.02rem 0.04rem rgba(0, 0, 0, 0.1);
}

.explanation-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.2rem;
}

.explanation-header h3 {
  font-size: 0.18rem;
  font-weight: bold;
  color: #333;
}

.level-list {
  display: flex;
  flex-direction: column;
  gap: 0.15rem;
}

.level-item {
  display: flex;
  align-items: center;
  padding: 0.15rem;
  border: 0.01rem solid #f0f0f0;
  border-radius: 0.08rem;
  transition: all 0.3s ease;

  &:hover {
    border-color: #d44439;
    box-shadow: 0 0.02rem 0.08rem rgba(212, 68, 57, 0.1);
  }
}

.level-info {
  display: flex;
  align-items: center;
  margin-right: 0.3rem;
}

.level-number {
  width: 0.3rem;
  height: 0.3rem;
  border-radius: 50%;
  background-color: #d44439;
  color: #fff;
  font-size: 0.14rem;
  font-weight: bold;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 0.1rem;
}

.level-name {
  font-size: 0.16rem;
  font-weight: bold;
  color: #333;
}

.level-requirements {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 0.05rem;
}

.exp-requirement {
  font-size: 0.14rem;
  color: #666;
}

.benefits-list {
  display: flex;
  flex-wrap: wrap;
  gap: 0.05rem;
}

.benefit-tag {
  padding: 0.02rem 0.08rem;
  background-color: rgba(212, 68, 57, 0.1);
  color: #d44439;
  font-size: 0.12rem;
  border-radius: 0.04rem;
}

/* 弹窗样式 */
.task-list, .upgrade-guide {
  max-height: 4rem;
  overflow-y: auto;
}

.task-item, .guide-item {
  display: flex;
  align-items: center;
  padding: 0.15rem;
  border-bottom: 0.01rem solid #f0f0f0;

  &:last-child {
    border-bottom: none;
  }
}

.task-info, .guide-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 0.05rem;
}

.task-title, .guide-title {
  font-size: 0.16rem;
  font-weight: bold;
  color: #333;
}

.task-desc, .guide-desc {
  font-size: 0.14rem;
  color: #666;
  line-height: 1.4;
}

.task-reward, .guide-reward {
  font-size: 0.14rem;
  color: #d44439;
  font-weight: bold;
}

.task-status {
  margin-left: 0.15rem;
}

.task-btn {
  padding: 0.05rem 0.12rem;
  background-color: #d44439;
  color: #fff;
  border: none;
  border-radius: 0.04rem;
  font-size: 0.14rem;
  cursor: pointer;
  transition: background-color 0.3s ease;

  &:disabled {
    background-color: #ccc;
    cursor: not-allowed;
  }

  &:hover:not(:disabled) {
    background-color: #c23328;
  }
}

.guide-icon {
  width: 0.4rem;
  height: 0.4rem;
  border-radius: 50%;
  background-color: rgba(212, 68, 57, 0.1);
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 0.2rem;
  margin-right: 0.15rem;
}
</style>
