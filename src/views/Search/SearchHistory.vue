<template>
  <div class="search-history-wrap">
    <back-header title="搜索历史" color="red"></back-header>

    <!-- 顶部菜单栏分类 -->
    <div class="category-tabs">
      <div
        v-for="(tab, index) in categoryTabs"
        :key="index"
        class="tab-item"
        :class="{ active: activeTab === index }"
        @click="switchTab(index)"
      >
        {{ tab.name }}
      </div>

      <!-- 搜索管理按钮 -->
      <div class="search-management">
        <button class="management-btn" @click="showManagementMenu = !showManagementMenu">
          搜索管理
          <i class="iconfont icon-arrow-down" :class="{ rotate: showManagementMenu }"></i>
        </button>

        <!-- 管理菜单 -->
        <div v-if="showManagementMenu" class="management-menu">
          <button class="menu-item" @click="clearCurrentHistory">
            清除当前分类历史
          </button>
          <button class="menu-item" @click="clearAllHistory">
            清除全部历史
          </button>
        </div>
      </div>
    </div>

    <!-- 搜索历史列表 -->
    <div class="history-content">
      <div v-if="currentHistory.length === 0" class="empty-history">
        <i class="iconfont icon-search"></i>
        <p>暂无搜索历史</p>
      </div>
      <ul v-else class="history-list">
        <li
          v-for="(item, index) in currentHistory"
          :key="index"
          class="history-item"
        >
          <div class="item-info" @click="goToSearch(item.keyword)">
            <i class="iconfont icon-history"></i>
            <span class="keyword">{{ item.keyword }}</span>
            <span class="search-time">{{ formatDate(new Date(item.searchTime)) }}</span>
          </div>
          <button class="delete-btn" @click.stop="deleteHistory(index)">
            <i class="iconfont icon-chahao"></i>
          </button>
        </li>
      </ul>
    </div>

    <!-- 浏览记录 -->
    <div v-if="activeTab === 2" class="browse-history">
      <h3 class="browse-title">浏览记录</h3>
      <div v-if="browseHistory.length === 0" class="empty-browse">
        <i class="iconfont icon-eye"></i>
        <p>暂无浏览记录</p>
      </div>
      <ul v-else class="browse-list">
        <li
          v-for="(item, index) in browseHistory"
          :key="index"
          class="browse-item"
        >
          <div class="item-info" @click="goToDetail(item)">
            <img :src="item.coverImgUrl" alt="商品图片" class="item-img">
            <div class="item-details">
              <p class="item-name">{{ item.name }}</p>
              <p class="item-desc">{{ item.desc }}</p>
              <p class="browse-time">{{ formatDate(new Date(item.browseTime)) }}</p>
            </div>
          </div>
          <button class="delete-btn" @click.stop="deleteBrowseHistory(index)">
            <i class="iconfont icon-chahao"></i>
          </button>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import BackHeader from '../../components/BackHeader.vue';
import { formatDate } from '../../utils/utils';

export default {
  name: 'SearchHistory',
  components: {
    BackHeader,
  },
  data() {
    return {
      categoryTabs: [
        { name: '商品', type: 'product' },
        { name: '店铺', type: 'shop' },
        { name: '搜索', type: 'search' },
      ],
      activeTab: 0,
      showManagementMenu: false,
      searchHistory: [],
      browseHistory: [],
    };
  },
  created() {
    this.loadHistory();
    // 添加模拟数据
    this.addMockData();
  },
  computed: {
    currentHistory() {
      // 根据当前选中的标签过滤搜索历史
      return this.searchHistory.filter(item => item.type === this.categoryTabs[this.activeTab].type);
    },
  },
  methods: {
    loadHistory() {
      // 从localStorage加载搜索历史
      const savedSearchHistory = localStorage.getItem('searchHistory');
      if (savedSearchHistory) {
        this.searchHistory = JSON.parse(savedSearchHistory);
      }

      // 从localStorage加载浏览记录
      const savedBrowseHistory = localStorage.getItem('browseHistory');
      if (savedBrowseHistory) {
        this.browseHistory = JSON.parse(savedBrowseHistory);
      }
    },
    addMockData() {
      // 如果没有搜索历史，添加模拟数据
      if (this.searchHistory.length === 0) {
        this.searchHistory = [
          { first: '周杰伦', type: 'search', searchTime: new Date(Date.now() - 3600000).toISOString() },
          { first: '林俊杰', type: 'search', searchTime: new Date(Date.now() - 7200000).toISOString() },
          { first: '陈奕迅', type: 'search', searchTime: new Date(Date.now() - 10800000).toISOString() },
          { first: '华为手机', type: 'product', searchTime: new Date(Date.now() - 14400000).toISOString() },
          { first: '小米手机', type: 'product', searchTime: new Date(Date.now() - 18000000).toISOString() },
          { first: '苹果手机', type: 'product', searchTime: new Date(Date.now() - 21600000).toISOString() },
          { first: '华为官方旗舰店', type: 'shop', searchTime: new Date(Date.now() - 25200000).toISOString() },
          { first: '小米官方旗舰店', type: 'shop', searchTime: new Date(Date.now() - 28800000).toISOString() },
          { first: '苹果官方旗舰店', type: 'shop', searchTime: new Date(Date.now() - 32400000).toISOString() },
        ];
        this.saveHistory();
      }

      // 如果没有浏览记录，添加模拟数据
      if (this.browseHistory.length === 0) {
        this.browseHistory = [
          {
            id: 1,
            name: '华为Mate 40 Pro',
            desc: '麒麟9000芯片，5G网络，6.76英寸OLED屏幕',
            coverImgUrl: 'https://via.placeholder.com/100x100',
            type: 'product',
            browseTime: new Date(Date.now() - 3600000).toISOString(),
          },
          {
            id: 2,
            name: '小米11 Ultra',
            desc: '骁龙888芯片，5G网络，6.81英寸AMOLED屏幕',
            coverImgUrl: 'https://via.placeholder.com/100x100',
            type: 'product',
            browseTime: new Date(Date.now() - 7200000).toISOString(),
          },
          {
            id: 3,
            name: '苹果iPhone 12',
            desc: 'A14仿生芯片，5G网络，6.1英寸OLED屏幕',
            coverImgUrl: 'https://via.placeholder.com/100x100',
            type: 'product',
            browseTime: new Date(Date.now() - 10800000).toISOString(),
          },
        ];
        this.saveHistory();
      }
    },
    saveHistory() {
      // 保存搜索历史到localStorage
      localStorage.setItem('searchHistory', JSON.stringify(this.searchHistory));

      // 保存浏览记录到localStorage
      localStorage.setItem('browseHistory', JSON.stringify(this.browseHistory));
    },
    switchTab(index) {
      this.activeTab = index;
      this.showManagementMenu = false;
    },
    clearCurrentHistory() {
      // 清除当前分类的搜索历史
      this.searchHistory = this.searchHistory.filter(item => item.type !== this.categoryTabs[this.activeTab].type);
      this.saveHistory();
      this.showManagementMenu = false;
    },
    clearAllHistory() {
      // 清除全部搜索历史
      this.searchHistory = [];
      this.browseHistory = [];
      this.saveHistory();
      this.showManagementMenu = false;
    },
    deleteHistory(index) {
      // 删除单个搜索历史记录
      this.searchHistory.splice(index, 1);
      this.saveHistory();
    },
    deleteBrowseHistory(index) {
      // 删除单个浏览记录
      this.browseHistory.splice(index, 1);
      this.saveHistory();
    },
    goToSearch(keyword) {
      // 跳转到搜索页面并进行搜索
      this.$router.push({ name: 'Search', query: { keyword } });
    },
    goToDetail(item) {
      // 跳转到商品或店铺详情页
      if (item.type === 'product') {
        this.$router.push({ name: 'ProductDetail', params: { id: item.id } });
      } else if (item.type === 'shop') {
        this.$router.push({ name: 'ShopDetail', params: { id: item.id } });
      }

      // 更新浏览时间
      item.browseTime = formatDate(new Date());
      this.saveHistory();
    },

  },
};
</script>

<style scoped lang='less'>
.search-history-wrap {
  padding-top: 0.44rem;
  min-height: 100vh;
  background-color: #f5f5f5;

  .category-tabs {
  display: flex;
  align-items: center;
  background-color: white;
  border-bottom: 1px solid #e4e4e4;
  padding: 0 0.2rem;
  margin-bottom: 0.2rem;

  .tab-item {
    flex: 1;
    padding: 0.15rem 0;
    text-align: center;
    font-size: 0.16rem;
    color: #666;
    border-bottom: 2px solid transparent;
    transition: all 0.3s ease;

    &.active {
      color: #d44439;
      border-bottom-color: #d44439;
    }
  }

  .search-management {
    position: relative;
    width: 0.8rem;
    margin-left: 0.2rem;
    flex-shrink: 0;

    .management-btn {
      width: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      background-color: transparent;
      border: none;
      font-size: 0.14rem;
      color: #666;
      padding: 0.05rem 0;
      cursor: pointer;

      .iconfont {
        font-size: 0.12rem;
        margin-left: 0.05rem;
        transition: transform 0.3s ease;

        &.rotate {
          transform: rotate(180deg);
        }
      }
    }

    .management-menu {
      position: absolute;
      top: 100%;
      right: 0;
      width: 1.2rem;
      background-color: white;
      border: 1px solid #e4e4e4;
      border-radius: 0.04rem;
      box-shadow: 0 0 0.1rem rgba(0, 0, 0, 0.1);
      z-index: 100;

      .menu-item {
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: white;
        border: none;
        font-size: 0.14rem;
        color: #666;
        padding: 0.1rem 0;
        cursor: pointer;

        &:first-child {
          border-bottom: 1px solid #e4e4e4;
        }

        &:hover {
          background-color: #f5f5f5;
        }
      }
    }
  }
}

  .history-content {
    padding: 0 0.2rem;

    .empty-history {
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 0.5rem 0;
      color: #999;

      .iconfont {
        font-size: 0.4rem;
        margin-bottom: 0.1rem;
      }
    }

    .history-list {
      background-color: white;
      border-radius: 0.04rem;
      overflow: hidden;

      .history-item {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0.15rem;
        border-bottom: 1px solid #f5f5f5;

        &:last-child {
          border-bottom: none;
        }

        .item-info {
          display: flex;
          align-items: center;
          flex: 1;

          .iconfont {
            font-size: 0.16rem;
            color: #999;
            margin-right: 0.1rem;
          }

          .keyword {
            flex: 1;
            font-size: 0.14rem;
            color: #333;
            margin-right: 0.1rem;
          }

          .search-time {
            font-size: 0.12rem;
            color: #999;
          }
        }

        .delete-btn {
          width: 0.2rem;
          height: 0.2rem;
          border-radius: 50%;
          background-color: #ccc;
          color: white;
          border: none;
          font-size: 0.12rem;
          display: flex;
          align-items: center;
          justify-content: center;
          cursor: pointer;

          .iconfont {
            font-size: 0.1rem;
          }
        }
      }
    }
  }

  .browse-history {
    padding: 0 0.2rem;
    margin-top: 0.2rem;

    .browse-title {
      font-size: 0.16rem;
      font-weight: bold;
      color: #333;
      margin-bottom: 0.1rem;
    }

    .empty-browse {
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 0.5rem 0;
      color: #999;

      .iconfont {
        font-size: 0.4rem;
        margin-bottom: 0.1rem;
      }
    }

    .browse-list {
      background-color: white;
      border-radius: 0.04rem;
      overflow: hidden;

      .browse-item {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0.15rem;
        border-bottom: 1px solid #f5f5f5;

        &:last-child {
          border-bottom: none;
        }

        .item-info {
          display: flex;
          align-items: center;
          flex: 1;

          .item-img {
            width: 0.4rem;
            height: 0.4rem;
            border-radius: 0.04rem;
            margin-right: 0.1rem;
          }

          .item-details {
            flex: 1;

            .item-name {
              font-size: 0.14rem;
              color: #333;
              margin-bottom: 0.05rem;
            }

            .item-desc {
              font-size: 0.12rem;
              color: #666;
              margin-bottom: 0.05rem;
            }

            .browse-time {
              font-size: 0.12rem;
              color: #999;
            }
          }
        }

        .delete-btn {
          width: 0.2rem;
          height: 0.2rem;
          border-radius: 50%;
          background-color: #ccc;
          color: white;
          border: none;
          font-size: 0.12rem;
          display: flex;
          align-items: center;
          justify-content: center;
          cursor: pointer;

          .iconfont {
            font-size: 0.1rem;
          }
        }
      }
    }
  }
}
</style>
