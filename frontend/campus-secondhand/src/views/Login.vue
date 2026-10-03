<template>
  <div class="login-page">
    <div class="animated-bg">
      <div class="gradient-sphere sphere-1"></div>
      <div class="gradient-sphere sphere-2"></div>
      <div class="gradient-sphere sphere-3"></div>
    </div>
    
    <div class="floating-elements">
      <div class="floating-card card-1">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M20.24 12.24a6 6 0 0 0-8.49-8.49L5 10.5V19h8.5z"/>
          <line x1="16" y1="8" x2="2" y2="22"/>
          <line x1="17.5" y1="15" x2="9" y2="15"/>
        </svg>
        <span>环保</span>
      </div>
      <div class="floating-card card-2">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <circle cx="12" cy="12" r="10"/>
          <path d="M12 6v6l4 2"/>
        </svg>
        <span>便捷</span>
      </div>
      <div class="floating-card card-3">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
          <circle cx="9" cy="7" r="4"/>
          <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
          <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
        </svg>
        <span>社交</span>
      </div>
    </div>

    <div class="login-container">
      <div class="login-card">
        <div class="card-header">
          <div class="logo-container">
            <svg class="logo-icon" viewBox="0 0 60 60">
              <defs>
                <linearGradient id="logoGrad" x1="0%" y1="0%" x2="100%" y2="100%">
                  <stop offset="0%" style="stop-color:#10b981"/>
                  <stop offset="100%" style="stop-color:#3b82f6"/>
                </linearGradient>
              </defs>
              <circle cx="30" cy="30" r="28" fill="url(#logoGrad)"/>
              <path d="M20 30 L27 35 L40 24" stroke="white" stroke-width="4" fill="none" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <h1 class="brand-title">废旧物品再利用平台</h1>
          <p class="brand-subtitle">废旧物品再利用平台</p>
        </div>

        <el-form :model="form" :rules="rules" ref="loginForm" class="login-form">
          <el-form-item prop="username" class="form-field">
            <div class="input-wrapper">
              <svg class="input-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                <circle cx="12" cy="7" r="4"/>
              </svg>
              <input 
                v-model="form.username" 
                placeholder="请输入用户名或学号" 
                class="modern-input"
                @focus="handleFocus"
                @blur="handleBlur"
              />
              <div class="input-highlight"></div>
            </div>
          </el-form-item>

          <el-form-item prop="password" class="form-field">
            <div class="input-wrapper">
              <svg class="input-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <rect x="3" y="11" width="18" height="11" rx="2" ry="2"/>
                <path d="M7 11V7a5 5 0 0 1 10 0v4"/>
              </svg>
              <input 
                v-model="form.password" 
                :type="showPassword ? 'text' : 'password'" 
                placeholder="请输入密码" 
                class="modern-input"
                @focus="handleFocus"
                @blur="handleBlur"
              />
              <button type="button" class="password-toggle" @click="showPassword = !showPassword">
                <svg v-if="!showPassword" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
                  <circle cx="12" cy="12" r="3"/>
                </svg>
                <svg v-else viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"/>
                  <line x1="1" y1="1" x2="23" y2="23"/>
                </svg>
              </button>
              <div class="input-highlight"></div>
            </div>
          </el-form-item>

          <div class="form-options">
            <label class="remember-me">
              <input type="checkbox" v-model="form.remember" />
              <span class="checkmark"></span>
              <span class="label-text">记住我</span>
            </label>
            <router-link to="/forgot" class="forgot-link">忘记密码？</router-link>
          </div>

          <el-form-item class="submit-item">
            <button type="button" class="submit-btn" @click="onSubmit" :loading="loading">
              <span class="btn-text">登 录</span>
              <svg class="btn-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="5" y1="12" x2="19" y2="12"/>
                <polyline points="12 5 19 12 12 19"/>
              </svg>
            </button>
          </el-form-item>
        </el-form>

        <div class="divider">
          <span>还没有账号？</span>
        </div>

        <router-link to="/register" class="register-link">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M16 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
            <circle cx="8.5" cy="7" r="4"/>
            <line x1="20" y1="8" x2="20" y2="14"/>
            <line x1="23" y1="11" x2="17" y2="11"/>
          </svg>
          <span>立即注册</span>
        </router-link>
      </div>

      <div class="features-banner">
        <div class="feature">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
            <polyline points="22 4 12 14.01 9 11.01"/>
          </svg>
          <span>安全交易</span>
        </div>
        <div class="feature">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <circle cx="12" cy="12" r="10"/>
            <polyline points="12 6 12 12 16 14"/>
          </svg>
          <span>快速响应</span>
        </div>
        <div class="feature">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/>
          </svg>
          <span>环保低碳</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import auth from '../api/auth'

const router = useRouter()
const route = useRoute()
const loginForm = ref(null)
const loading = ref(false)
const showPassword = ref(false)

const form = reactive({
  username: '',
  password: '',
  remember: false,
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
}

function handleFocus(e) {
  e.target.parentElement.classList.add('focused')
}

function handleBlur(e) {
  if (!e.target.value) {
    e.target.parentElement.classList.remove('focused')
  }
}

function onSubmit() {
  loginForm.value.validate((valid) => {
    if (valid) {
      loading.value = true
      const username = form.username?.trim()
      const password = form.password
      
      auth.login({ username, password })
        .then((res) => {
          console.log('Login response:', res)
          const token = res?.token
          const user = res?.user
          if (token && user) {
            if (form.remember) {
              localStorage.setItem('token', token)
              localStorage.setItem('user', JSON.stringify(user))
            } else {
              sessionStorage.setItem('token', token)
              sessionStorage.setItem('user', JSON.stringify(user))
            }
            ElMessage.success({
              message: '登录成功，欢迎回来！',
              type: 'success',
              duration: 2000
            })
            const redirect = route.query.redirect || '/'
            router.push(redirect)
          } else {
            ElMessage.error('登录返回缺少token或用户信息')
          }
        })
        .catch((err) => {
          ElMessage.error(err.message || '登录失败，请检查用户名和密码')
        })
        .finally(() => {
          loading.value = false
        })
    }
  })
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #0f0f23 0%, #1a1a3e 50%, #2d1b4e 100%);
  position: relative;
  overflow: hidden;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
}

.animated-bg {
  position: absolute;
  inset: 0;
  overflow: hidden;
}

.gradient-sphere {
  position: absolute;
  border-radius: 50%;
  filter: blur(80px);
  opacity: 0.5;
  animation: float 20s ease-in-out infinite;
}

.sphere-1 {
  width: 600px;
  height: 600px;
  background: linear-gradient(135deg, #10b981, #3b82f6);
  top: -200px;
  right: -200px;
  animation-delay: 0s;
}

.sphere-2 {
  width: 500px;
  height: 500px;
  background: linear-gradient(135deg, #8b5cf6, #ec4899);
  bottom: -150px;
  left: -150px;
  animation-delay: -7s;
}

.sphere-3 {
  width: 400px;
  height: 400px;
  background: linear-gradient(135deg, #f59e0b, #ef4444);
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  animation-delay: -14s;
}

@keyframes float {
  0%, 100% { transform: translate(0, 0) scale(1); }
  25% { transform: translate(30px, -30px) scale(1.05); }
  50% { transform: translate(-20px, 20px) scale(0.95); }
  75% { transform: translate(20px, 30px) scale(1.02); }
}

.floating-elements {
  position: absolute;
  inset: 0;
  pointer-events: none;
}

.floating-card {
  position: absolute;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  border-radius: 16px;
  padding: 16px 24px;
  display: flex;
  align-items: center;
  gap: 12px;
  color: white;
  border: 1px solid rgba(255, 255, 255, 0.2);
  animation: floatCard 15s ease-in-out infinite;
}

.floating-card svg {
  width: 24px;
  height: 24px;
}

.floating-card span {
  font-weight: 500;
  font-size: 14px;
}

.card-1 {
  top: 15%;
  right: 10%;
  animation-delay: 0s;
}

.card-2 {
  top: 60%;
  right: 15%;
  animation-delay: -5s;
}

.card-3 {
  bottom: 20%;
  left: 10%;
  animation-delay: -10s;
}

@keyframes floatCard {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-20px); }
}

.login-container {
  position: relative;
  z-index: 10;
  display: flex;
  align-items: center;
  gap: 60px;
}

.login-card {
  width: 420px;
  background: rgba(255, 255, 255, 0.08);
  backdrop-filter: blur(20px);
  border-radius: 24px;
  padding: 48px 40px;
  border: 1px solid rgba(255, 255, 255, 0.15);
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
}

.card-header {
  text-align: center;
  margin-bottom: 40px;
}

.logo-container {
  margin-bottom: 20px;
}

.logo-icon {
  width: 80px;
  height: 80px;
  filter: drop-shadow(0 4px 12px rgba(16, 185, 129, 0.4));
}

.brand-title {
  font-size: 32px;
  font-weight: 700;
  color: white;
  margin: 0 0 8px 0;
  letter-spacing: 2px;
}

.brand-subtitle {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.6);
  margin: 0;
}

.login-form {
  margin-bottom: 24px;
}

.form-field {
  margin-bottom: 24px;
}

.input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.input-icon {
  position: absolute;
  left: 16px;
  width: 20px;
  height: 20px;
  color: rgba(255, 255, 255, 0.5);
  transition: color 0.3s ease;
  z-index: 1;
}

.input-wrapper.focused .input-icon {
  color: #10b981;
}

.modern-input {
  width: 100%;
  padding: 16px 16px 16px 48px;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 12px;
  color: white;
  font-size: 15px;
  transition: all 0.3s ease;
  outline: none;
}

.modern-input::placeholder {
  color: rgba(255, 255, 255, 0.4);
}

.modern-input:focus {
  background: rgba(255, 255, 255, 0.1);
  border-color: #10b981;
  box-shadow: 0 0 0 4px rgba(16, 185, 129, 0.1);
}

.input-highlight {
  position: absolute;
  bottom: 0;
  left: 50%;
  width: 0;
  height: 2px;
  background: linear-gradient(90deg, #10b981, #3b82f6);
  transition: all 0.3s ease;
  transform: translateX(-50%);
}

.input-wrapper.focused .input-highlight {
  width: calc(100% - 32px);
}

.password-toggle {
  position: absolute;
  right: 16px;
  background: none;
  border: none;
  cursor: pointer;
  padding: 4px;
  color: rgba(255, 255, 255, 0.5);
  transition: color 0.3s ease;
}

.password-toggle:hover {
  color: rgba(255, 255, 255, 0.8);
}

.password-toggle svg {
  width: 20px;
  height: 20px;
}

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
}

.remember-me {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  color: rgba(255, 255, 255, 0.7);
  font-size: 14px;
}

.remember-me input {
  display: none;
}

.checkmark {
  width: 18px;
  height: 18px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
}

.remember-me input:checked + .checkmark {
  background: linear-gradient(135deg, #10b981, #3b82f6);
  border-color: transparent;
}

.remember-me input:checked + .checkmark::after {
  content: '✓';
  color: white;
  font-size: 12px;
  font-weight: bold;
}

.forgot-link {
  color: #10b981;
  text-decoration: none;
  font-size: 14px;
  transition: color 0.3s ease;
}

.forgot-link:hover {
  color: #34d399;
}

.submit-item {
  margin-bottom: 24px;
}

.submit-btn {
  width: 100%;
  padding: 16px 32px;
  background: linear-gradient(135deg, #10b981, #059669);
  border: none;
  border-radius: 12px;
  color: white;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.submit-btn::before {
  content: '';
  position: absolute;
  inset: 0;
  background: linear-gradient(135deg, #059669, #047857);
  opacity: 0;
  transition: opacity 0.3s ease;
}

.submit-btn:hover::before {
  opacity: 1;
}

.submit-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 10px 30px rgba(16, 185, 129, 0.4);
}

.submit-btn .btn-text {
  position: relative;
  z-index: 1;
}

.btn-arrow {
  width: 20px;
  height: 20px;
  transition: transform 0.3s ease;
}

.submit-btn:hover .btn-arrow {
  transform: translateX(4px);
}

.divider {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 20px;
}

.divider::before,
.divider::after {
  content: '';
  flex: 1;
  height: 1px;
  background: rgba(255, 255, 255, 0.2);
}

.divider span {
  color: rgba(255, 255, 255, 0.5);
  font-size: 13px;
}

.register-link {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  padding: 14px;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 12px;
  color: white;
  text-decoration: none;
  font-size: 15px;
  transition: all 0.3s ease;
}

.register-link:hover {
  background: rgba(255, 255, 255, 0.1);
  border-color: rgba(255, 255, 255, 0.3);
}

.register-link svg {
  width: 20px;
  height: 20px;
}

.features-banner {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.feature {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px 28px;
  background: rgba(255, 255, 255, 0.08);
  backdrop-filter: blur(10px);
  border-radius: 16px;
  border: 1px solid rgba(255, 255, 255, 0.1);
  color: white;
  transition: all 0.3s ease;
}

.feature:hover {
  background: rgba(255, 255, 255, 0.12);
  transform: translateX(10px);
}

.feature svg {
  width: 28px;
  height: 28px;
  color: #10b981;
}

.feature span {
  font-size: 16px;
  font-weight: 500;
}

@media (max-width: 1024px) {
  .login-container {
    flex-direction: column;
    gap: 40px;
  }
  
  .features-banner {
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: center;
  }
  
  .feature {
    padding: 16px 20px;
  }
}

@media (max-width: 480px) {
  .login-card {
    width: 100%;
    max-width: 360px;
    padding: 32px 24px;
  }
  
  .brand-title {
    font-size: 26px;
  }
  
  .floating-card {
    display: none;
  }
}
</style>
