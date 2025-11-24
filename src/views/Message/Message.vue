<template>
  <div class="message-container">
    <!-- 顶部导航栏 -->
    <div class="message-header">
      <h1 class="message-title">消息</h1>
      <div class="header-buttons">
        <button class="header-btn" @click="clearAllMessages">
          <i class="iconfont icon-qingli"></i>
          清理
        </button>
        <button class="header-btn" @click="managePermissions">
          <i class="iconfont icon-shezhi"></i>
          管理
        </button>
      </div>
    </div>

    <!-- 消息类型标签 -->
    <div class="message-tabs">
      <div class="tab-item" :class="{ active: activeTab === 'orders' }" @click="activeTab = 'orders'">
        订单动态
      </div>
      <div class="tab-item" :class="{ active: activeTab === 'benefits' }" @click="activeTab = 'benefits'">
        粉丝福利
      </div>
      <div class="tab-item" :class="{ active: activeTab === 'notifications' }" @click="activeTab = 'notifications'">
        服务提醒
      </div>
    </div>

    <!-- 消息列表 -->
    <div class="message-list">
      <!-- 已结束的聊天框 -->
      <div class="chat-section">
        <h2 class="section-title">已结束的聊天</h2>
        <div class="chat-item" v-for="chat in endedChats" :key="chat.id">
          <div class="chat-avatar">
            <img :src="chat.avatar" :alt="chat.name">
          </div>
          <div class="chat-info">
            <div class="chat-name-time">
              <span class="chat-name">{{ chat.name }}</span>
              <span class="chat-time">{{ chat.time }}</span>
            </div>
            <div class="chat-last-message">{{ chat.lastMessage }}</div>
          </div>
        </div>
      </div>

      <!-- 最近的消息 -->
      <div class="recent-section">
        <h2 class="section-title">最近的消息</h2>
        <div class="message-item" v-for="message in recentMessages" :key="message.id">
          <div class="message-avatar">
            <img :src="message.avatar" :alt="message.sender">
          </div>
          <div class="message-content">
            <div class="message-header">
              <span class="message-sender">{{ message.sender }}</span>
              <span class="message-time">{{ message.time }}</span>
            </div>
            <div class="message-text">{{ message.content }}</div>
            <div class="message-status" :class="{ unread: message.unread }">
              {{ message.unread ? '未读' : '已读' }}
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 权限管理弹窗 -->
    <div class="modal" v-if="showPermissionModal" @click="showPermissionModal = false">
      <div class="modal-content" @click.stop>
        <div class="modal-header">
          <h2>消息权限管理</h2>
          <button class="close-btn" @click="showPermissionModal = false">
            <i class="iconfont icon-guanbi"></i>
          </button>
        </div>
        <div class="modal-body">
          <div class="permission-item">
            <label class="permission-label">
              <input type="checkbox" v-model="permissions.orders">
              接收订单动态
            </label>
          </div>
          <div class="permission-item">
            <label class="permission-label">
              <input type="checkbox" v-model="permissions.benefits">
              接收粉丝福利
            </label>
          </div>
          <div class="permission-item">
            <label class="permission-label">
              <input type="checkbox" v-model="permissions.notifications">
              接收服务提醒
            </label>
          </div>
        </div>
        <div class="modal-footer">
          <button class="save-btn" @click="savePermissions">保存</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Message',
  data() {
    return {
      activeTab: 'orders',
      showPermissionModal: false,
      permissions: {
        orders: true,
        benefits: true,
        notifications: true,
      },
      endedChats: [
        {
          id: 1,
          name: '客服小助手',
          avatar: 'https://picsum.photos/seed/chat1/40/40.jpg',
          lastMessage: '您的订单已完成，感谢使用',
          time: '2023-10-01 14:30',
        },
        {
          id: 2,
          name: '活动专员',
          avatar: 'https://picsum.photos/seed/chat2/40/40.jpg',
          lastMessage: '您的福利已发放，请查收',
          time: '2023-09-28 09:15',
        },
      ],
      recentMessages: [
        {
          id: 1,
          sender: '系统通知',
          avatar: 'https://picsum.photos/seed/sys1/40/40.jpg',
          content: '您的会员服务已到期，请及时续费',
          time: '2023-10-05 16:45',
          unread: true,
        },
        {
          id: 2,
          sender: '粉丝福利官',
          avatar: 'https://picsum.photos/seed/fan1/40/40.jpg',
          content: '新的粉丝福利已上线，快来领取',
          time: '2023-10-05 10:20',
          unread: true,
        },
        {
          id: 3,
          sender: '订单通知',
          avatar: 'https://picsum.photos/seed/order1/40/40.jpg',
          content: '您的订单已发货，预计明天送达',
          time: '2023-10-04 18:30',
          unread: false,
        },
      ],
    };
  },
  methods: {
    clearAllMessages() {
      if (confirm('确定要清理所有消息吗？')) {
        this.recentMessages = [];
        this.$message.success('消息已清理');
      }
    },
    managePermissions() {
      this.showPermissionModal = true;
    },
    savePermissions() {
      // 这里可以添加保存权限的逻辑
      this.showPermissionModal = false;
      this.$message.success('权限设置已保存');
    },
  },
};
</script>

<style scoped lang='less'>
.message-container {
  min-height: 100vh;
  background-color: #f5f5f5;
}

.message-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.1rem 0.15rem;
  background-color: #fff;
  border-bottom: 1px solid #e4e4e4;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);

  .message-title {
    font-size: 0.18rem;
    font-weight: 600;
    margin: 0;
  }

  .header-buttons {
    display: flex;
    gap: 0.1rem;

    .header-btn {
      display: flex;
      align-items: center;
      gap: 0.05rem;
      padding: 0.05rem 0.1rem;
      border: 1px solid #d44439;
      border-radius: 0.04rem;
      background-color: #d44439;
      color: #fff;
      font-size: 0.14rem;
      cursor: pointer;
      transition: background-color 0.3s;

      &:hover {
        background-color: #c1382e;
      }

      .iconfont {
        font-size: 0.14rem;
      }
    }
  }
}

.message-tabs {
  display: flex;
  background-color: #fff;
  border-bottom: 1px solid #e4e4e4;

  .tab-item {
    flex: 1;
    padding: 0.1rem 0;
    text-align: center;
    font-size: 0.15rem;
    color: #666;
    cursor: pointer;
    transition: color 0.3s;

    &.active {
      color: #d44439;
      border-bottom: 2px solid #d44439;
    }

    &:hover {
      color: #d44439;
    }
  }
}

.message-list {
  padding: 0.1rem;

  .chat-section,
  .recent-section {
    background-color: #fff;
    border-radius: 0.04rem;
    margin-bottom: 0.1rem;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);

    .section-title {
      padding: 0.1rem 0.15rem;
      margin: 0;
      font-size: 0.16rem;
      font-weight: 600;
      border-bottom: 1px solid #f0f0f0;
    }
  }

  .chat-item {
    display: flex;
    align-items: center;
    padding: 0.1rem 0.15rem;
    border-bottom: 1px solid #f0f0f0;
    cursor: pointer;
    transition: background-color 0.3s;

    &:last-child {
      border-bottom: none;
    }

    &:hover {
      background-color: #fafafa;
    }

    .chat-avatar {
      margin-right: 0.1rem;

      img {
        width: 0.4rem;
        height: 0.4rem;
        border-radius: 50%;
      }
    }

    .chat-info {
      flex: 1;

      .chat-name-time {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 0.05rem;

        .chat-name {
          font-size: 0.15rem;
          font-weight: 500;
          color: #333;
        }

        .chat-time {
          font-size: 0.12rem;
          color: #999;
        }
      }

      .chat-last-message {
        font-size: 0.14rem;
        color: #666;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
    }
  }

  .message-item {
    display: flex;
    padding: 0.1rem 0.15rem;
    border-bottom: 1px solid #f0f0f0;
    cursor: pointer;
    transition: background-color 0.3s;

    &:last-child {
      border-bottom: none;
    }

    &:hover {
      background-color: #fafafa;
    }

    .message-avatar {
      margin-right: 0.1rem;

      img {
        width: 0.4rem;
        height: 0.4rem;
        border-radius: 50%;
      }
    }

    .message-content {
      flex: 1;

      .message-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 0.05rem;
        padding: 0;
        border: none;
        box-shadow: none;

        .message-sender {
          font-size: 0.15rem;
          font-weight: 500;
          color: #333;
        }

        .message-time {
          font-size: 0.12rem;
          color: #999;
        }
      }

      .message-text {
        font-size: 0.14rem;
        color: #666;
        line-height: 1.4;
        margin-bottom: 0.05rem;
      }

      .message-status {
        font-size: 0.12rem;
        color: #999;

        &.unread {
          color: #d44439;
        }
      }
    }
  }
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  width: 90%;
  max-width: 0.4rem;
  background-color: #fff;
  border-radius: 0.04rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);

  .modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0.1rem 0.15rem;
    border-bottom: 1px solid #f0f0f0;

    h2 {
      font-size: 0.16rem;
      font-weight: 600;
      margin: 0;
    }

    .close-btn {
      background: none;
      border: none;
      font-size: 0.18rem;
      color: #999;
      cursor: pointer;
      padding: 0;
      width: 0.2rem;
      height: 0.2rem;
      display: flex;
      align-items: center;
      justify-content: center;

      &:hover {
        color: #333;
      }
    }
  }

  .modal-body {
    padding: 0.15rem;

    .permission-item {
      margin-bottom: 0.1rem;

      &:last-child {
        margin-bottom: 0;
      }

      .permission-label {
        display: flex;
        align-items: center;
        gap: 0.05rem;
        font-size: 0.14rem;
        color: #333;
        cursor: pointer;

        input[type="checkbox"] {
          width: 0.14rem;
          height: 0.14rem;
          accent-color: #d44439;
        }
      }
    }
  }

  .modal-footer {
    padding: 0.1rem 0.15rem;
    border-top: 1px solid #f0f0f0;
    display: flex;
    justify-content: flex-end;

    .save-btn {
      padding: 0.05rem 0.15rem;
      border: 1px solid #d44439;
      border-radius: 0.04rem;
      background-color: #d44439;
      color: #fff;
      font-size: 0.14rem;
      cursor: pointer;
      transition: background-color 0.3s;

      &:hover {
        background-color: #c1382e;
      }
    }
  }
}
</style>
