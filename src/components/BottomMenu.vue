<template>
  <section class="bottom-menu">
    <router-link to="/home" class="menu-item">
      <i class="iconfont icon-shouye"></i>
      <span>首页</span>
    </router-link>
    <router-link to="/leader-board" class="menu-item">
      <i class="iconfont icon-paihangbang"></i>
      <span>排行榜</span>
    </router-link>
    <router-link to="/search" class="menu-item">
      <i class="iconfont icon-sousuo"></i>
      <span>搜索</span>
    </router-link>
    <router-link to="/my" class="menu-item">
      <i class="iconfont icon-wode"></i>
      <span>我的</span>
    </router-link>
    <router-link to="/member-center" class="menu-item">
      <i class="iconfont icon-vip"></i>
      <span>会员中心</span>
    </router-link>
  </section>
</template>

<script>
export default {
  name: 'BottomMenu',
};
</script>

<style scoped lang='less'>
.bottom-menu {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 0.5rem;
  background-color: #fff;
  border-top: 1px solid #e4e4e4;
  display: flex;
  justify-content: space-around;
  align-items: center;
  z-index: 998;

  .menu-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    color: #666;
    text-decoration: none;
    font-size: 0.12rem;

    .iconfont {
      font-size: 0.2rem;
      margin-bottom: 0.03rem;
    }

    &.router-link-active {
      color: #d44439;
    }
  }
}
</style>