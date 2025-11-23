<template>
  <div class="search-history left-right-padding-box">
    <back-header title="搜索历史" color="red"></back-header>
    <div class="history-container">
      <div v-if="history.length === 0" class="empty-history">
        <i class="iconfont icon-sousuo"></i>
        <p>暂无搜索历史</p>
      </div>
      <div v-else>
        <div class="title-container">
          <h2 class="title">搜索历史</h2>
          <button class="clear-btn" @click="clearHistory">清除历史</button>
        </div>
        <ul class="history-list">
          <li v-for="(item, index) in history" :key="index" class="item">
            <i class="iconfont icon-sousuo"></i>
            <span class="keyword">{{item.first}}</span>
            <button class="delete-btn" @click="deleteHistory(index)">
              <i class="iconfont icon-chahao"></i>
            </button>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import BackHeader from '../../components/BackHeader.vue';

export default {
  name: 'SearchHistory',
  components: {
    BackHeader,
  },
  data() {
    return {
      history: [],
    };
  },
  created() {
    this.loadHistory();
  },
  methods: {
    loadHistory() {
      const savedHistory = localStorage.getItem('searchHistory');
      if (savedHistory) {
        this.history = JSON.parse(savedHistory);
      }
    },
    clearHistory() {
      this.history = [];
      localStorage.removeItem('searchHistory');
    },
    deleteHistory(index) {
      this.history.splice(index, 1);
      localStorage.setItem('searchHistory', JSON.stringify(this.history));
    },
  },
};
</script>

<style scoped lang='less'>
.search-history {
  padding-top: 0.44rem;
  min-height: 100vh;
  background: white;
  .history-container {
    margin-top: 0.2rem;
    .empty-history {
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-top: 1rem;
      .iconfont {
        font-size: 0.6rem;
        color: #ccc;
        margin-bottom: 0.2rem;
      }
      p {
        font-size: 0.16rem;
        color: #999;
      }
    }
    .title-container {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 0.1rem 0;
      margin-bottom: 0.2rem;
      .title {
        font-size: 0.18rem;
        font-weight: 400;
      }
      .clear-btn {
        font-size: 0.14rem;
        color: #999;
        background: none;
        border: none;
      }
    }
    .history-list {
      .item {
        display: flex;
        align-items: center;
        height: 0.6rem;
        border-bottom: 1px solid #e4e4e4;
        .iconfont {
          font-size: 0.16rem;
          color: #999;
          margin-right: 0.1rem;
        }
        .keyword {
          flex: 1;
          font-size: 0.16rem;
        }
        .delete-btn {
          font-size: 0.14rem;
          color: #999;
          background: none;
          border: none;
          .iconfont {
            margin-right: 0;
          }
        }
      }
    }
  }
}
</style>