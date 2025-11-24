<template>
  <div class="cart-container">
    <!-- 顶部导航 -->
    <div class="cart-header">
      <h1>购物车</h1>
      <div class="header-right">
        <button @click="toggleManageMode" class="manage-btn">
          {{ isManageMode ? '完成' : '管理' }}
        </button>
      </div>
    </div>

    <!-- 分类标签 -->
    <div class="cart-tabs">
      <div 
        v-for="tab in tabs" 
        :key="tab.id"
        class="tab-item"
        :class="{ active: activeTab === tab.id }"
        @click="switchTab(tab.id)"
      >
        {{ tab.name }}
      </div>
    </div>

    <!-- 购物车列表 -->
    <div class="cart-list">
      <!-- 全选按钮 -->
      <div class="select-all" v-if="isManageMode">
        <input 
          type="checkbox" 
          id="select-all"
          v-model="selectAll"
          @change="handleSelectAll"
        >
        <label for="select-all">全选</label>
      </div>

      <!-- 商品列表 -->
      <div 
        v-for="item in filteredItems" 
        :key="item.id"
        class="cart-item"
      >
        <!-- 选择框 -->
        <div class="item-select" v-if="isManageMode">
          <input 
            type="checkbox"
            :id="'item-' + item.id"
            v-model="selectedItems"
            :value="item.id"
          >
          <label :for="'item-' + item.id"></label>
        </div>

        <!-- 商品信息 -->
        <div class="item-info">
          <img :src="item.image" alt="" class="item-image">
          <div class="item-details">
            <h3 class="item-name">{{ item.name }}</h3>
            <p class="item-description">{{ item.description }}</p>
            <div class="item-price">¥{{ item.price }}</div>
          </div>
        </div>

        <!-- 商品类型标识 -->
        <div 
          class="item-type"
          :class="item.type === 'room' ? 'room-type' : 'product-type'"
        >
          {{ item.type === 'room' ? '房型' : '商品' }}
        </div>
      </div>
    </div>

    <!-- 空购物车提示 -->
    <div class="empty-cart" v-if="filteredItems.length === 0">
      <img src="../../assets/loading.jpg" alt="空购物车" class="empty-image">
      <p>购物车是空的</p>
      <button class="go-shopping-btn">去购物</button>
    </div>

    <!-- 底部操作栏 -->
    <div class="cart-footer" v-if="isManageMode && selectedItems.length > 0">
      <div class="footer-left">
        <span>已选 {{ selectedItems.length }} 件</span>
      </div>
      <div class="footer-right">
        <button class="delete-btn" @click="batchDelete">删除</button>
        <button class="checkout-btn" @click="batchCheckout">结算</button>
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
      activeTab: 1,
      selectAll: false,
      selectedItems: [],
      tabs: [
        { id: 1, name: '全部' },
        { id: 2, name: '收藏' },
        { id: 3, name: '常购' }
      ],
      // 模拟购物车数据
      cartItems: [
        {
          id: 1,
          name: '豪华大床房',
          description: '宽敞明亮，配备舒适大床和现代化设施',
          price: 399,
          image: 'https://via.placeholder.com/100x100/FF6B6B/FFFFFF?text=Room',
          type: 'room',
          isFavorite: true,
          isFrequentlyBought: false
        },
        {
          id: 2,
          name: '标准双人房',
          description: '经济实惠，适合双人入住',
          price: 299,
          image: 'https://via.placeholder.com/100x100/4ECDC4/FFFFFF?text=Room',
          type: 'room',
          isFavorite: false,
          isFrequentlyBought: true
        },
        {
          id: 3,
          name: '精美茶具套装',
          description: '高品质陶瓷茶具，送礼自用皆宜',
          price: 199,
          image: 'https://via.placeholder.com/100x100/45B7D1/FFFFFF?text=Product',
          type: 'product',
          isFavorite: true,
          isFrequentlyBought: true
        },
        {
          id: 4,
          name: '纯棉床上用品',
          description: '柔软舒适，亲肤透气',
          price: 299,
          image: 'https://via.placeholder.com/100x100/96CEB4/FFFFFF?text=Product',
          type: 'product',
          isFavorite: false,
          isFrequentlyBought: false
        }
      ]
    };
  },
  computed: {
    filteredItems() {
      switch (this.activeTab) {
        case 1: // 全部
          return this.cartItems;
        case 2: // 收藏
          return this.cartItems.filter(item => item.isFavorite);
        case 3: // 常购
          return this.cartItems.filter(item => item.isFrequentlyBought);
        default:
          return this.cartItems;
      }
    }
  },
  methods: {
    toggleManageMode() {
      this.isManageMode = !this.isManageMode;
      if (!this.isManageMode) {
        this.selectedItems = [];
        this.selectAll = false;
      }
    },
    switchTab(tabId) {
      this.activeTab = tabId;
      this.selectedItems = [];
      this.selectAll = false;
    },
    handleSelectAll() {
      if (this.selectAll) {
        this.selectedItems = this.filteredItems.map(item => item.id);
      } else {
        this.selectedItems = [];
      }
    },
    batchDelete() {
      if (confirm(`确定要删除选中的 ${this.selectedItems.length} 件商品吗？`)) {
        this.cartItems = this.cartItems.filter(item => !this.selectedItems.includes(item.id));
        this.selectedItems = [];
        this.selectAll = false;
        alert('删除成功');
      }
    },
    batchCheckout() {
      alert(`已选择 ${this.selectedItems.length} 件商品，准备结算`);
      // 这里可以添加结算逻辑
    }
  }
};
</script>

<style scoped lang='less'>
.cart-container {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding-bottom: 0.5rem; /* 为底部菜单留出空间 */
}

.cart-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.2rem 0.3rem;
  background-color: #fff;
  border-bottom: 1px solid #e4e4e4;
  position: sticky;
  top: 0;
  z-index: 999;

  h1 {
    font-size: 0.32rem;
    font-weight: bold;
    margin: 0;
  }

  .manage-btn {
    padding: 0.08rem 0.2rem;
    background-color: #FF6B6B;
    color: #fff;
    border: none;
    border-radius: 0.04rem;
    font-size: 0.28rem;
    cursor: pointer;
  }
}

.cart-tabs {
  display: flex;
  background-color: #fff;
  margin-bottom: 0.2rem;
  border-bottom: 1px solid #e4e4e4;

  .tab-item {
    flex: 1;
    padding: 0.2rem 0;
    text-align: center;
    font-size: 0.28rem;
    color: #666;
    position: relative;
    cursor: pointer;

    &.active {
      color: #FF6B6B;

      &::after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 50%;
        transform: translateX(-50%);
        width: 0.4rem;
        height: 0.02rem;
        background-color: #FF6B6B;
      }
    }
  }
}

.cart-list {
  background-color: #fff;
}

.select-all {
  display: flex;
  align-items: center;
  padding: 0.2rem 0.3rem;
  border-bottom: 1px solid #e4e4e4;

  input[type="checkbox"] {
    margin-right: 0.1rem;
    width: 0.24rem;
    height: 0.24rem;
  }

  label {
    font-size: 0.28rem;
    color: #333;
  }
}

.cart-item {
  display: flex;
  align-items: center;
  padding: 0.2rem 0.3rem;
  border-bottom: 1px solid #f0f0f0;
  position: relative;

  &:last-child {
    border-bottom: none;
  }
}

.item-select {
  margin-right: 0.2rem;

  input[type="checkbox"] {
    width: 0.24rem;
    height: 0.24rem;
  }
}

.item-info {
  flex: 1;
  display: flex;
  align-items: center;

  .item-image {
    width: 1rem;
    height: 1rem;
    border-radius: 0.08rem;
    margin-right: 0.2rem;
    object-fit: cover;
  }

  .item-details {
    flex: 1;

    .item-name {
      font-size: 0.3rem;
      color: #333;
      margin-bottom: 0.05rem;
    }

    .item-description {
      font-size: 0.24rem;
      color: #999;
      margin-bottom: 0.05rem;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }

    .item-price {
      font-size: 0.32rem;
      color: #FF6B6B;
      font-weight: bold;
    }
  }
}

.item-type {
  padding: 0.05rem 0.1rem;
  border-radius: 0.04rem;
  font-size: 0.2rem;
  color: #fff;

  &.room-type {
    background-color: #4ECDC4;
  }

  &.product-type {
    background-color: #45B7D1;
  }
}

.empty-cart {
  text-align: center;
  padding: 1rem 0;

  .empty-image {
    width: 2rem;
    height: 2rem;
    margin-bottom: 0.3rem;
    opacity: 0.5;
  }

  p {
    font-size: 0.28rem;
    color: #999;
    margin-bottom: 0.3rem;
  }

  .go-shopping-btn {
    padding: 0.1rem 0.3rem;
    background-color: #FF6B6B;
    color: #fff;
    border: none;
    border-radius: 0.04rem;
    font-size: 0.28rem;
    cursor: pointer;
  }
}

.cart-footer {
  position: fixed;
  bottom: 0.5rem; /* 为底部菜单留出空间 */
  left: 0;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.2rem 0.3rem;
  background-color: #fff;
  border-top: 1px solid #e4e4e4;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1);

  .footer-left {
    font-size: 0.28rem;
    color: #333;
  }

  .footer-right {
    display: flex;
    gap: 0.2rem;

    .delete-btn {
      padding: 0.08rem 0.2rem;
      background-color: #999;
      color: #fff;
      border: none;
      border-radius: 0.04rem;
      font-size: 0.28rem;
      cursor: pointer;
    }

    .checkout-btn {
      padding: 0.08rem 0.2rem;
      background-color: #FF6B6B;
      color: #fff;
      border: none;
      border-radius: 0.04rem;
      font-size: 0.28rem;
      cursor: pointer;
    }
  }
}
</style>