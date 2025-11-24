<template>
  <div class="cart-container">
    <!-- 顶部导航 -->
    <div class="cart-header">
      <div class="header-title">购物车</div>
      <div class="header-right">
        <button class="manage-btn" @click="toggleManageMode">
          {{ isManageMode ? '完成' : '管理' }}
        </button>
      </div>
    </div>

    <!-- 分类标签 -->
    <div class="category-tabs">
      <div
        class="tab-item"
        :class="{ active: currentCategory === 'all' }"
        @click="switchCategory('all')"
      >
        全部
      </div>
      <div
        class="tab-item"
        :class="{ active: currentCategory === 'favorite' }"
        @click="switchCategory('favorite')"
      >
        收藏
      </div>
      <div
        class="tab-item"
        :class="{ active: currentCategory === 'frequent' }"
        @click="switchCategory('frequent')"
      >
        常购
      </div>
    </div>

    <!-- 购物车列表 -->
    <div class="cart-list">
      <div
        class="cart-item"
        v-for="item in filteredItems"
        :key="item.id"
      >
        <!-- 选择框 -->
        <div class="item-select">
          <input
            type="checkbox"
            :checked="item.selected"
            @change="toggleItemSelect(item.id)"
          >
        </div>

        <!-- 商品图片 -->
        <div class="item-image">
          <img :src="item.image" :alt="item.name">
        </div>

        <!-- 商品信息 -->
        <div class="item-info">
          <div class="item-name">{{ item.name }}</div>
          <div class="item-desc">{{ item.description }}</div>
          <div class="item-price">¥{{ item.price }}</div>
        </div>

        <!-- 操作按钮 -->
        <div class="item-actions">
          <button class="action-btn favorite-btn" @click="toggleFavorite(item.id)">
            <i :class="item.isFavorite ? 'iconfont icon-shoucang1' : 'iconfont icon-shoucang' "></i>
          </button>
          <button class="action-btn delete-btn" @click="deleteItem(item.id)">
            <i class="iconfont icon-shanchu"></i>
          </button>
        </div>
      </div>
    </div>

    <!-- 空购物车提示 -->
    <div class="empty-cart" v-if="filteredItems.length === 0">
      <i class="iconfont icon-gouwuche"></i>
      <p>购物车是空的</p>
    </div>

    <!-- 底部操作栏 -->
    <div class="cart-footer" v-if="filteredItems.length > 0">
      <div class="footer-left">
        <input
          type="checkbox"
          :checked="isAllSelected"
          @change="toggleAllSelect"
        >
        <span>全选</span>
      </div>
      <div class="footer-right">
        <div class="total-price">
          合计: ¥{{ totalPrice }}
        </div>
        <button class="settle-btn" @click="settle">
          结算 ({{ selectedCount }})
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Cart',
  data() {
    return {
      isManageMode: false,
      currentCategory: 'all',
      cartItems: [
        {
          id: 1,
          name: '豪华大床房',
          description: '面积: 30㎡ | 楼层: 5-10层 | 床型: 1.8m大床',
          price: 299,
          image: 'https://via.placeholder.com/80',
          selected: false,
          isFavorite: true,
          isFrequent: true,
        },
        {
          id: 2,
          name: '商务双床房',
          description: '面积: 35㎡ | 楼层: 11-15层 | 床型: 1.2m双床',
          price: 329,
          image: 'https://via.placeholder.com/80',
          selected: false,
          isFavorite: false,
          isFrequent: true,
        },
        {
          id: 3,
          name: '行政套房',
          description: '面积: 60㎡ | 楼层: 16-20层 | 床型: 2m大床',
          price: 599,
          image: 'https://via.placeholder.com/80',
          selected: false,
          isFavorite: true,
          isFrequent: false,
        },
      ],
    };
  },
  computed: {
    // 过滤当前分类的商品
    filteredItems() {
      switch (this.currentCategory) {
        case 'favorite':
          return this.cartItems.filter(item => item.isFavorite);
        case 'frequent':
          return this.cartItems.filter(item => item.isFrequent);
        default:
          return this.cartItems;
      }
    },
    // 全选状态
    isAllSelected() {
      return this.filteredItems.length > 0 && this.filteredItems.every(item => item.selected);
    },
    // 已选商品数量
    selectedCount() {
      return this.filteredItems.filter(item => item.selected).length;
    },
    // 总价
    totalPrice() {
      return this.filteredItems
        .filter(item => item.selected)
        .reduce((total, item) => total + item.price, 0)
        .toFixed(2);
    },
  },
  methods: {
    // 切换管理模式
    toggleManageMode() {
      this.isManageMode = !this.isManageMode;
    },
    // 切换分类
    switchCategory(category) {
      this.currentCategory = category;
    },
    // 切换商品选择状态
    toggleItemSelect(itemId) {
      const item = this.cartItems.find(item => item.id === itemId);
      if (item) {
        item.selected = !item.selected;
      }
    },
    // 全选/取消全选
    toggleAllSelect(e) {
      const isChecked = e.target.checked;
      this.filteredItems.forEach((item) => {
        item.selected = isChecked;
      });
    },
    // 切换收藏状态
    toggleFavorite(itemId) {
      const item = this.cartItems.find(item => item.id === itemId);
      if (item) {
        item.isFavorite = !item.isFavorite;
      }
    },
    // 删除商品
    deleteItem(itemId) {
      this.cartItems = this.cartItems.filter(item => item.id !== itemId);
    },
    // 结算
    settle() {
      const selectedItems = this.filteredItems.filter(item => item.selected);
      if (selectedItems.length === 0) {
        alert('请选择要结算的商品');
        return;
      }
      alert(`结算成功！共 ${selectedItems.length} 件商品，总价 ¥${this.totalPrice}`);
    },
  },
};
</script>

<style scoped>
.cart-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f5f5f5;
}

.cart-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.2rem;
  background-color: #fff;
  border-bottom: 1px solid #eee;
}

.header-title {
  font-size: 0.32rem;
  font-weight: bold;
}

.manage-btn {
  padding: 0.1rem 0.2rem;
  background-color: #d44439;
  color: #fff;
  border: none;
  border-radius: 0.05rem;
  font-size: 0.28rem;
}

.category-tabs {
  display: flex;
  background-color: #fff;
  border-bottom: 1px solid #eee;
}

.tab-item {
  flex: 1;
  padding: 0.2rem;
  text-align: center;
  font-size: 0.28rem;
  color: #666;
  cursor: pointer;
  transition: color 0.3s;
}

.tab-item.active {
  color: #d44439;
  border-bottom: 2px solid #d44439;
}

.cart-list {
  flex: 1;
  overflow-y: auto;
  padding: 0.2rem;
}

.cart-item {
  display: flex;
  align-items: center;
  padding: 0.2rem;
  margin-bottom: 0.2rem;
  background-color: #fff;
  border-radius: 0.05rem;
  box-shadow: 0 0.02rem 0.05rem rgba(0, 0, 0, 0.1);
}

.item-select {
  width: 0.4rem;
  margin-right: 0.2rem;
}

.item-image {
  width: 0.8rem;
  height: 0.8rem;
  margin-right: 0.2rem;
  border-radius: 0.05rem;
  overflow: hidden;
}

.item-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.item-info {
  flex: 1;
}

.item-name {
  font-size: 0.3rem;
  font-weight: bold;
  margin-bottom: 0.1rem;
}

.item-desc {
  font-size: 0.24rem;
  color: #666;
  margin-bottom: 0.1rem;
}

.item-price {
  font-size: 0.28rem;
  color: #d44439;
  font-weight: bold;
}

.item-actions {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.action-btn {
  width: 0.4rem;
  height: 0.4rem;
  margin-bottom: 0.1rem;
  background-color: transparent;
  border: none;
  font-size: 0.24rem;
  cursor: pointer;
}

.favorite-btn i {
  color: #d44439;
}

.delete-btn i {
  color: #999;
}

.empty-cart {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: #999;
}

.empty-cart i {
  font-size: 1rem;
  margin-bottom: 0.2rem;
}

.cart-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.2rem;
  background-color: #fff;
  border-top: 1px solid #eee;
}

.footer-left {
  display: flex;
  align-items: center;
}

.footer-left input {
  margin-right: 0.1rem;
}

.total-price {
  margin-right: 0.2rem;
  font-size: 0.3rem;
  font-weight: bold;
  color: #d44439;
}

.settle-btn {
  padding: 0.15rem 0.3rem;
  background-color: #d44439;
  color: #fff;
  border: none;
  border-radius: 0.05rem;
  font-size: 0.28rem;
}
</style>
