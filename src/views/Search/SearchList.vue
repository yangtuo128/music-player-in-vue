<template>
  <section class="container" @click='handleClick'>
    <div class="title-container">
      <h2 class="title">{{title}}</h2>
      <button v-if="(title === '历史记录' || title === '最近搜索') && list.length > 0" class="clear-btn" @click.stop="clearHistory">清除历史</button>
    </div>
    <ul class="search-list">
      <li v-for="(item, index) in list" :key="index" class="item">
        {{item.first}}
        <button v-if="title === '最近搜索'" class="delete-btn" @click.stop="deleteHistory(index)">
          <i class="iconfont icon-chahao"></i>
        </button>
      </li>
    </ul>
  </section>
</template>

<script>
export default {
  name: 'SearchList',
  props: ['list', 'title', 'doAfterUserClick'],
  methods: {
    handleClick(e) {
      const src = e.target;
      if (src.className === 'item' || src.parentNode.className === 'item') {
        const keyword = src.className === 'item' ? src.innerText : src.parentNode.innerText;
        this.doAfterUserClick(keyword);
      }
    },
    clearHistory() {
      this.$emit('clear-history');
    },
    deleteHistory(index) {
      this.$emit('delete-history', index);
    },
  },
};
</script>

<style scoped lang='less'>
.container {
  padding: 0.2rem 0;
  .title {
    padding: 0.1rem 0;
    font-size: .18rem;
  }
  .search-list {
    display: flex;
    flex-wrap: wrap;
    margin-left: .1rem;
    .item {
      position: relative;
      height: .3rem;
      line-height: .24rem;
      padding: 0.03rem 0.15rem;
      margin: 0.05rem 0.05rem 0.05rem 0;
      border-radius: 0.18rem;
      background: #eae1e1;
      color: #666161;
      overflow: hidden;
      transition: all 0.3s ease;
      &:hover {
        padding-right: 0.35rem;
        .delete-btn {
          opacity: 1;
          right: 0.05rem;
        }
      }
      .delete-btn {
        position: absolute;
        top: 50%;
        right: -0.2rem;
        transform: translateY(-50%);
        width: 0.2rem;
        height: 0.2rem;
        line-height: 0.2rem;
        text-align: center;
        border-radius: 50%;
        background: #ccc;
        color: white;
        font-size: 0.12rem;
        opacity: 0;
        transition: all 0.3s ease;
        border: none;
        padding: 0;
        .iconfont {
          font-size: 0.12rem;
        }
      }
    }
  }
}
</style>
