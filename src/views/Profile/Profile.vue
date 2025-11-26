<template>
  <div class="profile-container">
    <div class="user-info">
      <div class="avatar">
        <i class="iconfont icon-yonghu"></i>
      </div>
      <div class="user-details">
        <h2 class="username">{{ userInfo.username || '未设置' }}</h2>
        <p class="phone">{{ userInfo.phone || '未绑定手机号' }}</p>
      </div>
      <button class="edit-btn" @click="showEditModal = true">
        <i class="iconfont icon-bianji"></i>
      </button>
    </div>

    <div class="profile-menu">
      <div class="menu-item">
        <i class="iconfont icon-shouji"></i>
        <span>手机号管理</span>
      </div>
      <div class="menu-item">
        <i class="iconfont icon-suo"></i>
        <span>修改密码</span>
      </div>
      <div class="menu-item">
        <i class="iconfont icon-shezhi"></i>
        <span>账户设置</span>
      </div>
      <div class="menu-item" @click="logout">
        <i class="iconfont icon-tuichu"></i>
        <span>退出登录</span>
      </div>
    </div>

    <!-- 编辑个人信息弹窗 -->
    <div class="modal" v-if="showEditModal">
      <div class="modal-content">
        <div class="modal-header">
          <h3>编辑个人信息</h3>
          <button class="close-btn" @click="showEditModal = false">
            <i class="iconfont icon-chahao"></i>
          </button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>用户名:</label>
            <input type="text" v-model="editForm.username" placeholder="请输入用户名">
          </div>
          <div class="form-group">
            <label>邮箱:</label>
            <input type="email" v-model="editForm.email" placeholder="请输入邮箱">
          </div>
          <div class="form-group">
            <label>性别:</label>
            <select v-model="editForm.gender">
              <option value="">请选择</option>
              <option value="male">男</option>
              <option value="female">女</option>
              <option value="other">其他</option>
            </select>
          </div>
        </div>
        <div class="modal-footer">
          <button class="cancel-btn" @click="showEditModal = false">取消</button>
          <button class="save-btn" @click="saveUserInfo">保存</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import { getUserInfo, updateUserInfo } from '../../api/My';

export default {
  data() {
    return {
      userInfo: {},
      showEditModal: false,
      editForm: {
        username: '',
        email: '',
        gender: '',
      },
    };
  },
  computed: {
    ...mapGetters(['loginStatus']),
  },
  methods: {
    ...mapActions(['getThenSetLoginStatus']),
    async getUserInfo() {
      try {
        const uid = localStorage.getItem('uid');
        const res = await getUserInfo(uid);
        this.userInfo = res;
        this.editForm = { ...res };
      } catch (error) {
        console.log('获取用户信息失败:', error);
      }
    },
    async saveUserInfo() {
      try {
        const uid = localStorage.getItem('uid');
        const res = await updateUserInfo(uid, this.editForm);
        this.userInfo = res;
        this.showEditModal = false;
        alert('信息保存成功');
      } catch (error) {
        console.log('保存用户信息失败:', error);
        alert('保存失败');
      }
    },
    logout() {
      localStorage.removeItem('uid');
      this.getThenSetLoginStatus();
      this.$router.push('/login');
    },
  },
  created() {
    this.getUserInfo();
  },
};
</script>

<style scoped lang='less'>
.profile-container {
  padding: 0.2rem;
  background-color: #f5f5f5;
  min-height: 100vh;
}

.user-info {
  display: flex;
  align-items: center;
  padding: 0.2rem;
  background-color: white;
  border-radius: 0.1rem;
  margin-bottom: 0.2rem;
  box-shadow: 0 0 0.1rem rgba(0, 0, 0, 0.1);
}

.avatar {
  width: 0.8rem;
  height: 0.8rem;
  border-radius: 50%;
  background-color: #d44439;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 0.2rem;

  i {
    font-size: 0.4rem;
    color: white;
  }
}

.user-details {
  flex: 1;
}

.username {
  font-size: 0.2rem;
  font-weight: bold;
  margin-bottom: 0.05rem;
}

.phone {
  font-size: 0.14rem;
  color: #999;
}

.edit-btn {
  background: none;
  border: none;
  outline: none;
  cursor: pointer;

  i {
    font-size: 0.2rem;
    color: #d44439;
  }
}

.profile-menu {
  background-color: white;
  border-radius: 0.1rem;
  box-shadow: 0 0 0.1rem rgba(0, 0, 0, 0.1);
}

.menu-item {
  display: flex;
  align-items: center;
  padding: 0.15rem 0.2rem;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
  transition: background-color 0.3s;

  &:last-child {
    border-bottom: none;
  }

  &:hover {
    background-color: #fafafa;
  }

  i {
    font-size: 0.18rem;
    color: #d44439;
    margin-right: 0.15rem;
  }

  span {
    font-size: 0.16rem;
    color: #333;
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
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background-color: white;
  border-radius: 0.1rem;
  width: 90%;
  max-width: 400px;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.2rem;
  border-bottom: 1px solid #f0f0f0;
}

.modal-header h3 {
  margin: 0;
  font-size: 0.18rem;
  color: #333;
}

.close-btn {
  background: none;
  border: none;
  outline: none;
  cursor: pointer;

  i {
    font-size: 0.2rem;
    color: #999;
  }
}

.modal-body {
  padding: 0.2rem;
}

.form-group {
  margin-bottom: 0.2rem;
}

.form-group label {
  display: block;
  font-size: 0.14rem;
  color: #333;
  margin-bottom: 0.05rem;
}

.form-group input,
.form-group select {
  width: 100%;
  height: 0.35rem;
  padding: 0 0.1rem;
  border: 1px solid #e4e4e4;
  border-radius: 0.05rem;
  font-size: 0.14rem;
  outline: none;

  &:focus {
    border-color: #d44439;
  }
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  padding: 0.2rem;
  border-top: 1px solid #f0f0f0;
}

.cancel-btn,
.save-btn {
  padding: 0.08rem 0.2rem;
  border: 1px solid #e4e4e4;
  border-radius: 0.05rem;
  font-size: 0.14rem;
  cursor: pointer;
  margin-left: 0.1rem;
}

.cancel-btn {
  background-color: white;
  color: #333;
}

.save-btn {
  background-color: #d44439;
  color: white;
  border-color: #d44439;
}
</style>
