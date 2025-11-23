<template>
  <div class="member-center">
    <div class="member-header">
      <div class="member-info">
        <div class="avatar">
          <i class="iconfont icon-yonghu"></i>
        </div>
        <div class="member-details">
          <h2 class="member-name">尊贵的会员</h2>
          <div class="member-level" :class="memberLevel.class">
            {{ memberLevel.name }}
          </div>
        </div>
      </div>
      <div class="member-stats">
        <div class="stat-item">
          <div class="stat-value">{{ totalConsumption }}</div>
          <div class="stat-label">累计消费</div>
        </div>
        <div class="stat-item">
          <div class="stat-value">{{ currentPoints }}</div>
          <div class="stat-label">当前积分</div>
        </div>
        <div class="stat-item">
          <div class="stat-value">{{ remainingDays }}</div>
          <div class="stat-label">剩余天数</div>
        </div>
      </div>
    </div>

    <div class="member-progress">
      <div class="progress-info">
        <span>当前积分: {{ currentPoints }}</span>
        <span>距离下一等级: {{ nextLevelPoints - currentPoints }} 积分</span>
      </div>
      <div class="progress-bar">
        <div class="progress-fill" :style="{ width: progressPercentage + '%' }"></div>
      </div>
      <div class="level-indicators">
        <div class="level-indicator" :class="{ active: i <= memberLevel.id }" v-for="i in 3" :key="i">
          {{ i === 1 ? '白银' : i === 2 ? '黄金' : '黑金' }}
        </div>
      </div>
    </div>

    <div class="member-benefits">
      <h3>会员福利</h3>
      <div class="benefits-list">
        <div class="benefit-item" v-for="benefit in memberLevel.benefits" :key="benefit.id">
          <div class="benefit-icon">
            <i :class="benefit.icon"></i>
          </div>
          <div class="benefit-details">
            <div class="benefit-name">{{ benefit.name }}</div>
            <div class="benefit-desc">{{ benefit.description }}</div>
          </div>
          <button class="benefit-btn" @click="claimBenefit(benefit.id)">
            {{ benefit.claimed ? '已领取' : '立即领取' }}
          </button>
        </div>
      </div>
    </div>

    <div class="level-explanations">
      <h3>等级说明</h3>
      <div class="level-list">
        <div class="level-item" :class="{ active: level.id === memberLevel.id }" v-for="level in levels" :key="level.id">
          <div class="level-icon" :class="level.class">
            {{ level.name }}
          </div>
          <div class="level-requirements">
            <div class="requirement-item">
              <span>积分要求: {{ level.points }} 分</span>
            </div>
            <div class="requirement-item">
              <span>消费要求: {{ level.consumption }} 元</span>
            </div>
          </div>
          <div class="level-benefits-preview">
            <span>{{ level.benefits[0].name }}等{{ level.benefits.length }}项福利</span>
          </div>
        </div>
      </div>
    </div>

    <div class="test-section">
      <h3>测试操作</h3>
      <div class="test-buttons">
        <button class="test-btn" @click="addConsumption(100)">增加100元消费</button>
        <button class="test-btn" @click="addConsumption(500)">增加500元消费</button>
        <button class="test-btn" @click="addConsumption(1000)">增加1000元消费</button>
        <button class="test-btn" @click="resetData">重置数据</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MemberCenter',
  data() {
    return {
      totalConsumption: 0,
      currentPoints: 0,
      remainingDays: 30,
      claimedBenefits: [],
      levels: [
        {
          id: 1,
          name: '白银会员',
          class: 'silver',
          points: 500,
          consumption: 500,
          benefits: [
            {
              id: 1,
              name: '月度代金券',
              description: '每月可领取50元代金券',
              icon: 'icon-youhuiquan',
              claimed: false
            }
          ]
        },
        {
          id: 2,
          name: '黄金会员',
          class: 'gold',
          points: 1000,
          consumption: 1000,
          benefits: [
            {
              id: 2,
              name: '月度代金券',
              description: '每月可领取100元代金券',
              icon: 'icon-youhuiquan',
              claimed: false
            }
          ]
        },
        {
          id: 3,
          name: '黑金会员',
          class: 'black-gold',
          points: 10000,
          consumption: 10000,
          benefits: [
            {
              id: 3,
              name: '免费酒店入住',
              description: '每年可免费入住任意酒店一次',
              icon: 'icon-jiudian',
              claimed: false
            }
          ]
        }
      ]
    };
  },
  computed: {
    memberLevel() {
      // 根据积分判断会员等级
      if (this.currentPoints >= 10000) {
        return this.levels[2];
      } else if (this.currentPoints >= 1000) {
        return this.levels[1];
      } else if (this.currentPoints >= 500) {
        return this.levels[0];
      } else {
        // 非会员
        return {
          id: 0,
          name: '普通用户',
          class: 'normal',
          points: 0,
          consumption: 0,
          benefits: []
        };
      }
    },
    nextLevelPoints() {
      if (this.currentPoints >= 10000) {
        return this.currentPoints;
      } else if (this.currentPoints >= 1000) {
        return 10000;
      } else if (this.currentPoints >= 500) {
        return 1000;
      } else {
        return 500;
      }
    },
    progressPercentage() {
      if (this.currentPoints >= 10000) {
        return 100;
      }
      const currentLevelPoints = this.memberLevel.id === 0 ? 0 : this.memberLevel.points;
      const range = this.nextLevelPoints - currentLevelPoints;
      const progress = this.currentPoints - currentLevelPoints;
      return (progress / range) * 100;
    }
  },
  methods: {
    claimBenefit(benefitId) {
      if (this.claimedBenefits.includes(benefitId)) {
        this.$message.warning('该福利已领取');
        return;
      }
      
      // 模拟领取福利
      this.claimedBenefits.push(benefitId);
      
      // 更新福利状态
      this.levels.forEach(level => {
        level.benefits.forEach(benefit => {
          if (benefit.id === benefitId) {
            benefit.claimed = true;
          }
        });
      });
      
      this.$message.success('福利领取成功');
    },
    addConsumption(amount) {
      this.totalConsumption += amount;
      this.currentPoints += amount; // 假设1元=1积分
      this.$message.success(`已增加${amount}元消费，当前积分：${this.currentPoints}`);
    },
    resetData() {
      this.totalConsumption = 0;
      this.currentPoints = 0;
      this.claimedBenefits = [];
      
      // 重置福利状态
      this.levels.forEach(level => {
        level.benefits.forEach(benefit => {
          benefit.claimed = false;
        });
      });
      
      this.$message.success('数据已重置');
    }
  },
  mounted() {
    // 从localStorage加载数据
    const savedData = localStorage.getItem('memberData');
    if (savedData) {
      const data = JSON.parse(savedData);
      this.totalConsumption = data.totalConsumption || 0;
      this.currentPoints = data.currentPoints || 0;
      this.claimedBenefits = data.claimedBenefits || [];
      
      // 更新福利状态
      this.levels.forEach(level => {
        level.benefits.forEach(benefit => {
          benefit.claimed = this.claimedBenefits.includes(benefit.id);
        });
      });
    }
  },
  watch: {
    // 保存数据到localStorage
    totalConsumption: function() {
      this.saveData();
    },
    currentPoints: function() {
      this.saveData();
    },
    claimedBenefits: function() {
      this.saveData();
    }
  },
  saveData() {
    const data = {
      totalConsumption: this.totalConsumption,
      currentPoints: this.currentPoints,
      claimedBenefits: this.claimedBenefits
    };
    localStorage.setItem('memberData', JSON.stringify(data));
  }
};
</script>

<style scoped lang='less'>
.member-center {
  padding: 0.2rem;
  background-color: #f5f5f5;
  min-height: 100vh;
  padding-bottom: 0.7rem; /* 为底部菜单留出空间 */
}

.member-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  padding: 0.3rem;
  border-radius: 0.1rem;
  margin-bottom: 0.2rem;
}

.member-info {
  display: flex;
  align-items: center;
  margin-bottom: 0.2rem;
}

.avatar {
  width: 0.6rem;
  height: 0.6rem;
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.2);
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 0.2rem;
  font-size: 0.3rem;
}

.member-details {
  flex: 1;
}

.member-name {
  font-size: 0.2rem;
  margin-bottom: 0.05rem;
}

.member-level {
  display: inline-block;
  padding: 0.03rem 0.1rem;
  border-radius: 0.1rem;
  font-size: 0.14rem;
  font-weight: bold;
  
  &.normal {
    background-color: rgba(255, 255, 255, 0.2);
  }
  
  &.silver {
    background-color: #c0c0c0;
  }
  
  &.gold {
    background-color: #ffd700;
    color: #333;
  }
  
  &.black-gold {
    background-color: #000;
  }
}

.member-stats {
  display: flex;
  justify-content: space-around;
  border-top: 1px solid rgba(255, 255, 255, 0.2);
  padding-top: 0.15rem;
}

.stat-item {
  text-align: center;
}

.stat-value {
  font-size: 0.18rem;
  font-weight: bold;
  margin-bottom: 0.03rem;
}

.stat-label {
  font-size: 0.12rem;
  opacity: 0.8;
}

.member-progress {
  background-color: #fff;
  padding: 0.2rem;
  border-radius: 0.1rem;
  margin-bottom: 0.2rem;
}

.progress-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.1rem;
  font-size: 0.14rem;
  color: #666;
}

.progress-bar {
  width: 100%;
  height: 0.08rem;
  background-color: #f0f0f0;
  border-radius: 0.04rem;
  overflow: hidden;
  margin-bottom: 0.15rem;
}

.progress-fill {
  height: 100%;
  background: linear-gradient(90deg, #667eea 0%, #764ba2 100%);
  transition: width 0.3s ease;
}

.level-indicators {
  display: flex;
  justify-content: space-between;
}

.level-indicator {
  font-size: 0.12rem;
  color: #999;
  position: relative;
  
  &::before {
    content: '';
    position: absolute;
    top: -0.15rem;
    left: 50%;
    transform: translateX(-50%);
    width: 0.1rem;
    height: 0.1rem;
    border-radius: 50%;
    background-color: #f0f0f0;
    transition: background-color 0.3s ease;
  }
  
  &.active {
    color: #667eea;
    
    &::before {
      background-color: #667eea;
    }
  }
}

.member-benefits {
  background-color: #fff;
  padding: 0.2rem;
  border-radius: 0.1rem;
  margin-bottom: 0.2rem;
}

.member-benefits h3 {
  font-size: 0.16rem;
  margin-bottom: 0.15rem;
  color: #333;
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
  border: 1px solid #f0f0f0;
  border-radius: 0.08rem;
  transition: box-shadow 0.3s ease;
  
  &:hover {
    box-shadow: 0 0 0.05rem rgba(0, 0, 0, 0.1);
  }
}

.benefit-icon {
  width: 0.4rem;
  height: 0.4rem;
  border-radius: 50%;
  background-color: #f0f8ff;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 0.15rem;
  font-size: 0.2rem;
  color: #667eea;
}

.benefit-details {
  flex: 1;
}

.benefit-name {
  font-size: 0.14rem;
  margin-bottom: 0.03rem;
  color: #333;
}

.benefit-desc {
  font-size: 0.12rem;
  color: #999;
}

.benefit-btn {
  padding: 0.05rem 0.15rem;
  border: 1px solid #667eea;
  border-radius: 0.05rem;
  background-color: #667eea;
  color: #fff;
  font-size: 0.12rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
  
  &:hover:not(:disabled) {
    background-color: #5568d3;
  }
  
  &:disabled {
    background-color: #ccc;
    border-color: #ccc;
    cursor: not-allowed;
  }
}

.level-explanations {
  background-color: #fff;
  padding: 0.2rem;
  border-radius: 0.1rem;
  margin-bottom: 0.2rem;
}

.level-explanations h3 {
  font-size: 0.16rem;
  margin-bottom: 0.15rem;
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
  border: 1px solid #f0f0f0;
  border-radius: 0.08rem;
  transition: all 0.3s ease;
  
  &:hover {
    box-shadow: 0 0 0.05rem rgba(0, 0, 0, 0.1);
  }
  
  &.active {
    border-color: #667eea;
    background-color: rgba(102, 126, 234, 0.05);
  }
}

.level-icon {
  width: 0.4rem;
  height: 0.4rem;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 0.15rem;
  font-size: 0.12rem;
  font-weight: bold;
  color: #fff;
  
  &.normal {
    background-color: #ccc;
  }
  
  &.silver {
    background-color: #c0c0c0;
  }
  
  &.gold {
    background-color: #ffd700;
    color: #333;
  }
  
  &.black-gold {
    background-color: #000;
  }
}

.level-requirements {
  flex: 1;
  margin-right: 0.15rem;
}

.requirement-item {
  font-size: 0.12rem;
  margin-bottom: 0.03rem;
  color: #666;
}

.level-benefits-preview {
  font-size: 0.12rem;
  color: #999;
}

.test-section {
  background-color: #fff;
  padding: 0.2rem;
  border-radius: 0.1rem;
}

.test-section h3 {
  font-size: 0.16rem;
  margin-bottom: 0.15rem;
  color: #333;
}

.test-buttons {
  display: flex;
  flex-wrap: wrap;
  gap: 0.1rem;
}

.test-btn {
  padding: 0.05rem 0.1rem;
  border: 1px solid #667eea;
  border-radius: 0.05rem;
  background-color: #667eea;
  color: #fff;
  font-size: 0.12rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
  
  &:hover {
    background-color: #5568d3;
  }
}
</style>