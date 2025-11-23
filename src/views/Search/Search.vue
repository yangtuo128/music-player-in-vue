<template>
  <div class="search-box left-right-padding-box" ref="searchBox">
    <back-header color="red" :input='true' :doAfterUserEnter="startSearch"></back-header>
    
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
        <router-link to="/search-history" class="management-btn">
          搜索管理
          <i class="iconfont icon-arrow-right"></i>
        </router-link>
      </div>
    </div>
    
    <search-list v-if="!showResultPageFlag" :doAfterUserClick='startSearch' :list="hots" title="热门搜索" class="hot-search"></search-list>
    <search-list v-if="!showResultPageFlag" :doAfterUserClick='startSearch' :list="currentHistory" title="历史记录" @clear-history="clearHistory"></search-list>
    <search-result-list v-else :tracks="songList" class="search-result-list"></search-result-list>
  </div>
</template>

<script>
import BackHeader from '../../components/BackHeader.vue';
import SearchList from './SearchList.vue';
import SearchResultList from './SearchResultList.vue';
import { getHotSearch } from '../../api/Search/Search';
import { getSearchResult } from '../../api/SearchResult';

export default {
  name: 'Search',
  components: {
    BackHeader,
    SearchList,
    SearchResultList,
  },
  data() {
    return {
      categoryTabs: [
        { name: '商品', type: 'product' },
        { name: '店铺', type: 'shop' },
        { name: '搜索', type: 'search' }
      ],
      activeTab: 0,
      hots: [],
      showResultPageFlag: false,
      history: [],
      songList: [],
    };
  },
  computed: {
    currentHistory() {
      // 根据当前选中的标签过滤搜索历史
      return this.history.filter(item => item.type === this.categoryTabs[this.activeTab].type);
    }
  },
  created() {
    this.getData();
    this.loadHistory();
  },
  methods: {
    saveHistory() {
      localStorage.setItem('searchHistory', JSON.stringify(this.history));
    },
    loadHistory() {
      const savedHistory = localStorage.getItem('searchHistory');
      if (savedHistory) {
        this.history = JSON.parse(savedHistory);
      }
    },
    clearHistory() {
      // 根据当前选中的标签清除搜索历史
      const currentType = this.categoryTabs[this.activeTab].type;
      this.history = this.history.filter(item => item.type !== currentType);
      this.saveHistory();
    },
    switchTab(index) {
      this.activeTab = index;
    },
    async getData() {
      const data = await getHotSearch();
      this.hots = data.result.hots;
    },
    switchToResult() {
      this.showResultPageFlag = true;
    },
    startSearch(theValueToSearch) {
      // debugger;
      this.switchToResult();
      this.getSearchResultData(theValueToSearch);
      // push一个对象进history数组的原因是，为了使得history和hots的格式一致，这样这两部分才能共用一个SearchList组件
      // 检查是否已经存在相同的搜索历史
      const currentType = this.categoryTabs[this.activeTab].type;
      const isExist = this.history.some(item => item.first === theValueToSearch && item.type === currentType);
      if (!isExist) {
        this.history.push({ 
          first: theValueToSearch, 
          type: currentType,
          searchTime: new Date().toISOString()
        });
        this.saveHistory();
      }
    },
    async getSearchResultData(theValueToSearch) {
      const data = await getSearchResult(theValueToSearch);
      this.songList = data.result.songs;
    },
  },
  // 该局部路由守卫的作用是让每次跳转进搜索界面时，都显示热门搜索和历史记录，而不是搜索结果
  beforeRouteLeave(to, from, next) {
    this.showResultPageFlag = false;
    next();
  },
};
</script>

<style scoped lang='less'>
.search-box {
  position: absolute;
  z-index: 999;
  top: 0;
  left: 0;
  width: 100%;
  background: white;
  
  .category-tabs {
    display: flex;
    align-items: center;
    background-color: white;
    border-bottom: 1px solid #e4e4e4;
    padding: 0 0.2rem;
    
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
      width: 0.8rem;
      
      .management-btn {
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: transparent;
        border: none;
        font-size: 0.14rem;
        color: #666;
        padding: 0.05rem 0;
        cursor: pointer;
        text-decoration: none;
        
        .iconfont {
          font-size: 0.12rem;
          margin-left: 0.05rem;
        }
      }
    }
  }
  
  .hot-search {
    margin-top: 0.44rem;
  }
  
  .search-result-list {
    padding-top: 0.44rem;
  }
}
</style>
