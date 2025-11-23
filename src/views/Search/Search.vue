<template>
  <div class="search-box left-right-padding-box" ref="searchBox">
    <back-header color="red" :input='true' :doAfterUserEnter="startSearch"></back-header>
    <search-list v-if="!showResultPageFlag" :doAfterUserClick='startSearch' :list="hots" title="热门搜索" class="hot-search"></search-list>
    <div v-if="!showResultPageFlag" class="history-entry">
      <router-link to="/search-history" class="history-link">
        <span>查看全部搜索历史</span>
        <i class="iconfont icon-arrow-right"></i>
      </router-link>
    </div>
    <search-list v-if="!showResultPageFlag && history.length > 0" :doAfterUserClick='startSearch' :list="history.slice(0, 10)" title="最近搜索" @clear-history="clearHistory" @delete-history="deleteHistory"></search-list>
    <search-result-list v-else-if="showResultPageFlag" :tracks="songList" class="search-result-list"></search-result-list>
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
      hots: [],
      showResultPageFlag: false,
      history: [],
      songList: [],
    };
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
      this.history = [];
      localStorage.removeItem('searchHistory');
    },
    deleteHistory(index) {
      this.history.splice(index, 1);
      localStorage.setItem('searchHistory', JSON.stringify(this.history));
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
      const isExist = this.history.some(item => item.first === theValueToSearch);
      if (!isExist) {
        this.history.push({ first: theValueToSearch });
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
  .hot-search {
    margin-top: 0.44rem;
  }
  .history-entry {
    padding: 0.1rem 0;
    margin: 0.1rem 0;
    .history-link {
      display: flex;
      justify-content: space-between;
      align-items: center;
      color: #333;
      text-decoration: none;
      .iconfont {
        font-size: 0.14rem;
        color: #999;
      }
    }
  }
  .search-result-list {
    padding-top: 0.44rem;
  }
}
</style>
