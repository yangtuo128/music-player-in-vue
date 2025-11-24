<template>
  <div class="cart">
    <!-- 顶部导航 -->
    <div class="cart-header">
      <h1>购物车</h1>
      <button class="manage-btn" @click="toggleManageMode">
        {{ isManageMode ? '完成' : '管理' }}
      </button>
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
        v-for="(item, index) in filteredItems" 
        :key="item.id"
      >
        <!-- 选择框 -->
        <div class="checkbox-wrapper">
          <input 
            type="checkbox" 
            class="item-checkbox"
            :checked="item.checked"
            @change="toggleItemChecked(index)"
            :disabled="!isManageMode"
          >
        </div>

        <!-- 商品图片 -->
        <div class="item-image">
          <img :src="item.image" :alt="item.name">
        </div>

        <!-- 商品信息 -->
        <div class="item-info">
          <h3 class="item-name">{{ item.name }}</h3>
          <p class="item-description">{{ item.description }}</p>
          <div class="item-price">
            <span class="price">¥{{ item.price }}</span>
            <span class="unit">{{ item.unit }}</span>
          </div>
        </div>

        <!-- 商品类型（房型/商品） -->
        <div class="item-type">
          <span class="type-tag">{{ item.type === 'room' ? '房型' : '商品' }}</span>
        </div>
      </div>
    </div>

    <!-- 空购物车提示 -->
    <div class="empty-cart" v-if="filteredItems.length === 0">
      <i class="iconfont icon-gouwuche"></i>
      <p>购物车为空</p>
    </div>

    <!-- 底部操作栏 -->
    <div class="cart-footer" v-if="isManageMode && filteredItems.length > 0">
      <div class="select-all-wrapper">
        <input 
          type="checkbox" 
          class="select-all-checkbox"
          :checked="isAllChecked"
          @change="toggleSelectAll"
        >
        <span>全选</span>
      </div>
      <div class="action-buttons">
        <button class="delete-btn" @click="deleteSelectedItems" :disabled="selectedItems.length === 0">
          删除
        </button>
        <button class="settle-btn" @click="settleSelectedItems" :disabled="selectedItems.length === 0">
          结算
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
      isManageMode: false, // 是否进入管理模式
      currentCategory: 'all', // 当前分类（all/favorite/frequent）
      cartItems: [
        // 模拟数据
        {
          id: 1,
          name: '豪华大床房',
          description: '宽敞明亮，配备舒适大床和独立卫生间',
          price: 299,
          unit: '晚',
          image: 'https://via.placeholder.com/100',
          type: 'room', // room 表示房型，product 表示商品
          favorite: true, // 是否收藏
          frequent: true, // 是否常购
          checked: false // 是否选中（管理模式下）
        },
        {
          id: 2,
          name: '标准双床房',
          description: '经济实惠，适合双人入住',
          price: 199,
          unit: '晚',
          image: 'https://via.placeholder.com/100',
          type: 'room',
          favorite: false,
          frequent: true,
          checked: false
        },
        {
          id: 3,
          name: '酒店特色纪念品',
          description: '精美的酒店特色纪念品',
          price: 59,
          unit: '件',
          image: 'https://via.placeholder.com/100',
          type: 'product',
          favorite: true,
          frequent: false,
          checked: false
        },
        {
          id: 4,
          name: '高级套房',
          description: '豪华套房，配备客厅和卧室',
          price: 599,
          unit: '晚',
          image: 'https://via.placeholder.com/100',
          type: 'room',
          favorite: false,
          frequent: false,
          checked: false
        }
      ]
    };
  },
  computed: {
    // 过滤后的商品列表
    filteredItems() {
      switch (this.currentCategory) {
        case 'favorite':
          return this.cartItems.filter(item => item.favorite);
        case 'frequent':
          return this.cartItems.filter(item => item.frequent);
        default: // all
          return this.cartItems;
      }
    },
    // 选中的商品列表
    selectedItems() {
      return this.filteredItems.filter(item => item.checked);
    },
    // 是否全选
    isAllChecked() {
      return this.filteredItems.length > 0 && this.selectedItems.length === this.filteredItems.length;
    }
  },
  methods: {
    // 切换管理模式
    toggleManageMode() {
      this.isManageMode = !this.isManageMode;
      // 退出管理模式时取消所有选择
      if (!this.isManageMode) {
        this.cartItems.forEach(item => {
          item.checked = false;
        });
      }
    },
    // 切换分类
    switchCategory(category) {
      this.currentCategory = category;
    },
    // 切换商品选中状态
    toggleItemChecked(index) {
      this.filteredItems[index].checked = !this.filteredItems[index].checked;
    },
    // 全选/取消全选
    toggleSelectAll() {
      const isChecked = !this.isAllChecked;
      this.filteredItems.forEach(item => {
        item.checked = isChecked;
      });
    },
    // 删除选中的商品
    deleteSelectedItems() {
      if (this.selectedItems.length === 0) return;
      if (confirm(`确定要删除选中的 ${this.selectedItems.length} 个商品吗？`)) {
        const selectedIds = this.selectedItems.map(item => item.id);
        this.cartItems = this.cartItems.filter(item => !selectedIds.includes(item.id));
      }
    },
    // 结算选中的商品
    settleSelectedItems() {
      if (this.selectedItems.length === 0) return;
      alert(`已结算 ${this.selectedItems.length} 个商品，总价：¥${this.calculateTotalPrice()}`);
    },
    // 计算选中商品的总价
    calculateTotalPrice() {
      return this.selectedItems.reduce((total, item) => total + item.price, 0);
    }
  }
};
</script>

<style scoped lang='less'>
.cart {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding-bottom: 0.6rem; // 为底部菜单留出空间

  // 顶部导航
  .cart-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 0.44rem;
    padding: 0 0.15rem;
    background-color: #fff;
    border-bottom: 1px solid #e4e4e4;

    h1 {
      font-size: 0.18rem;
      font-weight: 500;
      color: #333;
    }

    .manage-btn {
      font-size: 0.16rem;
      color: #d44439;
      background-color: transparent;
      border: none;
      outline: none;
      cursor: pointer;
    }
  }

  // 分类标签
  .category-tabs {
    display: flex;
    background-color: #fff;
    border-bottom: 1px solid #e4e4e4;

    .tab-item {
      flex: 1;
      height: 0.4rem;
      display: flex;
      justify-content: center;
      align-items: center;
      font-size: 0.16rem;
      color: #666;
      position: relative;
      cursor: pointer;

      &.active {
        color: #d44439;

        &::after {
          content: '';
          position: absolute;
          bottom: 0;
          left: 50%;
          transform: translateX(-50%);
          width: 0.3rem;
          height: 0.02rem;
          background-color: #d44439;
        }
      }
    }
  }

  // 购物车列表
  .cart-list {
    padding: 0.1rem;

    .cart-item {
      display: flex;
      align-items: center;
      background-color: #fff;
      border-radius: 0.05rem;
      padding: 0.1rem;
      margin-bottom: 0.1rem;
      box-shadow: 0 0.01rem 0.03rem rgba(0, 0, 0, 0.1);

      .checkbox-wrapper {
        margin-right: 0.1rem;

        .item-checkbox {
          width: 0.18rem;
          height: 0.18rem;
          cursor: pointer;
        }
      }

      .item-image {
        width: 0.8rem;
        height: 0.8rem;
        margin-right: 0.1rem;
        border-radius: 0.05rem;
        overflow: hidden;

        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
        }
      }

      .item-info {
        flex: 1;

        .item-name {
          font-size: 0.16rem;
          color: #333;
          margin-bottom: 0.05rem;
        }

        .item-description {
          font-size: 0.14rem;
          color: #999;
          margin-bottom: 0.05rem;
          line-height: 1.2;
        }

        .item-price {
          display: flex;
          align-items: baseline;

          .price {
            font-size: 0.18rem;
            color: #d44439;
            font-weight: 500;
          }

          .unit {
            font-size: 0.14rem;
            color: #999;
            margin-left: 0.03rem;
          }
        }
      }

      .item-type {
        margin-left: 0.1rem;

        .type-tag {
          display: inline-block;
          padding: 0.03rem 0.08rem;
          font-size: 0.12rem;
          border-radius: 0.03rem;
          background-color: #f0f0f0;
          color: #666;
        }
      }
    }
  }

  // 空购物车提示
  .empty-cart {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 1rem;

    .iconfont {
      font-size: 0.8rem;
      color: #ccc;
      margin-bottom: 0.2rem;
    }

    p {
      font-size: 0.16rem;
      color: #999;
    }
  }

  // 底部操作栏
  .cart-footer {
    position: fixed;
    bottom: 0.5rem; // 为底部菜单留出空间
    left: 0;
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 0.5rem;
    padding: 0 0.15rem;
    background-color: #fff;
    border-top: 1px solid #e4e4e4;

    .select-all-wrapper {
      display: flex;
      align-items: center;

      .select-all-checkbox {
        width: 0.18rem;
        height: 0.18rem;
        margin-right: 0.05rem;
        cursor: pointer;
      }

      span {
        font-size: 0.16rem;
        color: #333;
      }
    }

    .action-buttons {
      display: flex;

      .delete-btn,
      .settle-btn {
        width: 0.8rem;
        height: 0.35rem;
        margin-left: 0.1rem;
        font-size: 0.16rem;
        border: none;
        border-radius: 0.05rem;
        outline: none;
        cursor: pointer;

        &:disabled {
          opacity: 0.5;
          cursor: not-allowed;
        }
      }

      .delete-btn {
        background-color: #fff;
        color: #d44439;
        border: 1px solid #d44439;
      }

      .settle-btn {
        background-color: #d44439;
        color: #fff;
      }
    }
  }
}
</style>