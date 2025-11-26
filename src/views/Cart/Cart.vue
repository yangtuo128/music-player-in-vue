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
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  background-attachment: fixed;
}

.cart-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.3rem 0.4rem;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-bottom: 1px solid rgba(238, 238, 238, 0.5);
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.header-title {
  font-size: 0.36rem;
  font-weight: 600;
  color: #333;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

.manage-btn {
  padding: 0.15rem 0.3rem;
  background: linear-gradient(135deg, #ff6b6b, #ee5a52);
  color: #fff;
  border: none;
  border-radius: 25px;
  font-size: 0.28rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 8px rgba(228, 97, 89, 0.3);
}

.manage-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(228, 97, 89, 0.5);
}

.category-tabs {
  display: flex;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-bottom: 1px solid rgba(238, 238, 238, 0.5);
  padding: 0 0.4rem;
}

.tab-item {
  flex: 1;
  padding: 0.25rem 0;
  text-align: center;
  font-size: 0.28rem;
  color: #666;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  font-weight: 500;
}

.tab-item.active {
  color: #ff6b6b;
}

.tab-item.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 0.4rem;
  height: 3px;
  background: linear-gradient(135deg, #ff6b6b, #ee5a52);
  border-radius: 3px;
  animation: tabSlide 0.3s ease;
}

@keyframes tabSlide {
  from {
    width: 0;
  }
  to {
    width: 0.4rem;
  }
}

.cart-list {
  flex: 1;
  overflow-y: auto;
  padding: 0.3rem;
}

.cart-item {
  display: flex;
  align-items: center;
  padding: 0.3rem;
  margin-bottom: 0.3rem;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-radius: 15px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.cart-item::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.4), transparent);
  transition: left 0.5s ease;
}

.cart-item:hover::before {
  left: 100%;
}

.cart-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.15);
}

.item-select {
  width: 0.5rem;
  margin-right: 0.3rem;
}

.item-select input[type="checkbox"] {
  width: 0.24rem;
  height: 0.24rem;
  cursor: pointer;
  accent-color: #ff6b6b;
}

.item-image {
  width: 1rem;
  height: 1rem;
  margin-right: 0.3rem;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.item-image:hover {
  transform: scale(1.05);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
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
  font-size: 0.32rem;
  font-weight: 600;
  margin-bottom: 0.15rem;
  color: #333;
  line-height: 1.2;
}

.item-desc {
  font-size: 0.26rem;
  color: #666;
  margin-bottom: 0.15rem;
  line-height: 1.4;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.item-price {
  font-size: 0.3rem;
  color: #ff6b6b;
  font-weight: 600;
  text-shadow: 0 1px 2px rgba(255, 107, 107, 0.3);
}

.item-actions {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.15rem;
}

.action-btn {
  width: 0.5rem;
  height: 0.5rem;
  background: rgba(255, 255, 255, 0.8);
  border: 1px solid rgba(200, 200, 200, 0.3);
  border-radius: 50%;
  font-size: 0.26rem;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  backdrop-filter: blur(5px);
}

.favorite-btn i {
  color: #ff6b6b;
  transition: all 0.3s ease;
}

.favorite-btn:hover {
  background: rgba(255, 107, 107, 0.1);
  border-color: #ff6b6b;
  transform: scale(1.1);
}

.favorite-btn:hover i {
  transform: scale(1.2);
}

.delete-btn i {
  color: #999;
  transition: all 0.3s ease;
}

.delete-btn:hover {
  background: rgba(200, 200, 200, 0.1);
  border-color: #999;
  transform: scale(1.1);
}

.delete-btn:hover i {
  color: #ff6b6b;
  transform: scale(1.2);
}

.empty-cart {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: rgba(255, 255, 255, 0.8);
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
}

.empty-cart i {
  font-size: 1.5rem;
  margin-bottom: 0.3rem;
  animation: emptyCartFloat 2s ease-in-out infinite;
}

@keyframes emptyCartFloat {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-0.2rem);
  }
}

.empty-cart p {
  font-size: 0.3rem;
  font-weight: 500;
}

.cart-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.3rem 0.4rem;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-top: 1px solid rgba(238, 238, 238, 0.5);
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1);
}

.footer-left {
  display: flex;
  align-items: center;
  gap: 0.15rem;
}

.footer-left input[type="checkbox"] {
  width: 0.24rem;
  height: 0.24rem;
  cursor: pointer;
  accent-color: #ff6b6b;
}

.footer-left span {
  font-size: 0.28rem;
  color: #333;
  font-weight: 500;
}

.total-price {
  margin-right: 0.3rem;
  font-size: 0.32rem;
  font-weight: 600;
  color: #ff6b6b;
  text-shadow: 0 1px 2px rgba(255, 107, 107, 0.3);
}

.settle-btn {
  padding: 0.2rem 0.4rem;
  background: linear-gradient(135deg, #ff6b6b, #ee5a52);
  color: #fff;
  border: none;
  border-radius: 25px;
  font-size: 0.3rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 8px rgba(228, 97, 89, 0.3);
  display: flex;
  align-items: center;
  gap: 0.1rem;
}

.settle-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(228, 97, 89, 0.5);
}
</style>
