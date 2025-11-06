<template>
  <section class="login-wrap">
    <back-header title="手机号注册" color="red"></back-header>
    <form action @submit="tryToRegister">
      <div class="phone-number-container">
        <label>
          <i class="iconfont icon-shouji"></i>
          <span class="country-code">+86</span>
        </label>
        <input
          class="tel-input"
          type="tel"
          autofocus
          v-model="tel"
          required
          pattern="^1[34578]\d{9}"
        >
        <button class="clean-btn" @click="cleanTel">
          <i class="iconfont icon-chahao"></i>
        </button>
      </div>
      <div class="password-container">
        <label for>
          <i class="iconfont icon-suo"></i>
        </label>
        <input
          class="password-input"
          type="password"
          placeholder="请设置密码"
          v-model="password"
          required
        >
      </div>
      <div class="password-container">
        <label for>
          <i class="iconfont icon-suo"></i>
        </label>
        <input
          class="password-input"
          type="password"
          placeholder="请确认密码"
          v-model="confirmPassword"
          required
        >
      </div>
      <div class="login-btn-container">
        <input class="login-btn" type="submit" value="注册">
      </div>
    </form>
    <reminder :word="reminderWord" :ifShow="reminderFlag" :callback="resetReminder"></reminder>
  </section>
</template>

<script>
import { mapActions } from 'vuex';
import BackHeader from '../../components/BackHeader.vue';
import Reminder from '../../components/Reminder.vue';
import { register } from '../../api/Login';

export default {
  data() {
    return {
      tel: null,
      password: null,
      confirmPassword: null,
      reminderFlag: false,
      reminderWord: '',
    };
  },
  methods: {
    ...mapActions(['getThenSetLoginStatus']),
    cleanTel() {
      this.tel = null;
    },
    async tryToRegister(e) {
      e.preventDefault();
      
      if (this.password !== this.confirmPassword) {
        this.reminderFlag = true;
        this.reminderWord = '两次密码输入不一致';
        return;
      }
      
      try {
        const res = await register(this.tel, this.password);
        localStorage.setItem('uid', res.id);
        await this.getThenSetLoginStatus();
        this.$router.replace('/profile');
      } catch (error) {
        this.reminderFlag = true;
        this.reminderWord = error.response ? error.response.data : '注册失败';
      }
    },
    resetReminder() {
      this.reminderFlag = false;
    },
  },
  components: {
    BackHeader,
    Reminder,
  },
};
</script>

<style scoped lang='less'>
.login-wrap {
  padding-top: 0.44rem;
  .phone-number-container,
  .password-container {
    position: relative;
    border-bottom: 1px solid #e4e4e4;
    padding: 0.05rem 0;
    margin: 0.2rem 0.2rem;
  }
  .tel-input,
  .password-input {
    font-size: 16px;
    border: none;
    outline: none;
    width: 80%;
    margin-left: 0.1rem;
  }
  .clean-btn {
    position: absolute;
    right: 0;
    top: 50%;
    transform: translateY(-50%);
    background: none;
    border: none;
    outline: none;
    cursor: pointer;
  }
  .login-btn-container {
    margin: 0.4rem 0.2rem;
  }
  .login-btn {
    width: 100%;
    height: 0.4rem;
    background-color: #d44439;
    color: white;
    border: none;
    border-radius: 0.05rem;
    font-size: 16px;
    cursor: pointer;
  }
}
</style>