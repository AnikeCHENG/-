<template>
  <div class="register-page">
    <div class="animated-bg">
      <div class="gradient-sphere sphere-1"></div>
      <div class="gradient-sphere sphere-2"></div>
      <div class="gradient-sphere sphere-3"></div>
    </div>
    
    <div class="floating-elements">
      <div class="floating-card card-1">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M16 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
          <circle cx="8.5" cy="7" r="4"/>
          <line x1="20" y1="8" x2="20" y2="14"/>
          <line x1="23" y1="11" x2="17" y2="11"/>
        </svg>
        <span>注册</span>
      </div>
      <div class="floating-card card-2">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M16 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
          <circle cx="9" cy="7" r="4"/>
          <path d="M22 21v-2a4 4 0 0 0-3-3.87"/>
          <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
        </svg>
        <span>社交</span>
      </div>
      <div class="floating-card card-3">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M20.24 12.24a6 6 0 0 0-8.49-8.49L5 10.5V19h8.5z"/>
          <line x1="16" y1="8" x2="2" y2="22"/>
          <line x1="17.5" y1="15" x2="9" y2="15"/>
        </svg>
        <span>环保</span>
      </div>
    </div>

    <div class="register-container">
      <div class="register-card">
        <div class="card-header">
          <div class="logo-container">
            <svg class="logo-icon" viewBox="0 0 60 60">
              <defs>
                <linearGradient id="logoGrad" x1="0%" y1="0%" x2="100%" y2="100%">
                  <stop offset="0%" style="stop-color:#8b5cf6"/>
                  <stop offset="100%" style="stop-color:#ec4899"/>
                </linearGradient>
              </defs>
              <circle cx="30" cy="30" r="28" fill="url(#logoGrad)"/>
              <path d="M20 30 L27 35 L40 24" stroke="white" stroke-width="4" fill="none" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <h1 class="brand-title">加入我们</h1>
          <p class="brand-subtitle">创建您的废旧物品再利用平台账号</p>
        </div>

        <el-form :model="form" :rules="rules" ref="regForm" class="register-form">
          <el-form-item prop="username" class="form-field">
            <div class="input-wrapper">
              <svg class="input-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                <circle cx="12" cy="7" r="4"/>
              </svg>
              <input 
                v-model="form.username" 
                placeholder="请输入用户名" 
                class="modern-input"
                @focus="handleFocus"
                @blur="handleBlur"
              />
              <div class="input-highlight"></div>
            </div>
          </el-form-item>

          <el-form-item prop="email" class="form-field">
            <div class="input-wrapper">
              <svg class="input-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"/>
                <polyline points="22,6 12,13 2,6"/>
              </svg>
              <input 
                v-model="form.email" 
                type="email"
                placeholder="请输入邮箱地址" 
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

          <el-form-item prop="confirm" class="form-field">
            <div class="input-wrapper">
              <svg class="input-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
                <polyline points="22 4 12 14.01 9 11.01"/>
              </svg>
              <input 
                v-model="form.confirm" 
                :type="showConfirmPassword ? 'text' : 'password'" 
                placeholder="请确认密码" 
                class="modern-input"
                @focus="handleFocus"
                @blur="handleBlur"
              />
              <button type="button" class="password-toggle" @click="showConfirmPassword = !showConfirmPassword">
                <svg v-if="!showConfirmPassword" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
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

          <div class="password-strength" v-if="form.password">
            <div class="strength-label">密码强度</div>
            <div class="strength-bar">
              <div class="strength-fill" :class="strengthClass" :style="{ width: strengthWidth }"></div>
            </div>
            <div class="strength-text" :class="strengthClass">{{ strengthText }}</div>
          </div>

          <el-form-item class="submit-item">
            <button type="button" class="submit-btn" @click="onRegister" :loading="loading">
              <span class="btn-text">创 建 账 号</span>
              <svg class="btn-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="5" y1="12" x2="19" y2="12"/>
                <polyline points="12 5 19 12 12 19"/>
              </svg>
            </button>
          </el-form-item>
        </el-form>

        <div class="divider">
          <span>已有账号？</span>
        </div>

        <router-link to="/login" class="login-link">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4"/>
            <polyline points="10 17 15 12 10 7"/>
            <line x1="15" y1="12" x2="3" y2="12"/>
          </svg>
          <span>立即登录</span>
        </router-link>
      </div>

      <div class="benefits-banner">
        <div class="benefit">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M16 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
            <circle cx="8.5" cy="7" r="4"/>
            <path d="M20 8v6"/>
            <path d="M23 11l-3 3-3-3"/>
          </svg>
          <div>
            <div class="benefit-title">免费注册</div>
            <div class="benefit-desc">永久免费使用所有功能</div>
          </div>
        </div>
        <div class="benefit">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
            <circle cx="9" cy="7" r="4"/>
            <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
          </svg>
          <div>
            <div class="benefit-title">社区交流</div>
            <div class="benefit-desc">与同学分享经验心得</div>
          </div>
        </div>
        <div class="benefit">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
            <polyline points="22 4 12 14.01 9 11.01"/>
          </svg>
          <div>
            <div class="benefit-title">安全保障</div>
            <div class="benefit-desc">平台担保，安全交易</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import auth from '../api/auth'

const router = useRouter()
const regForm = ref(null)
const loading = ref(false)
const showPassword = ref(false)
const showConfirmPassword = ref(false)

const form = reactive({ 
  username: '', 
  email: '', 
  password: '', 
  confirm: '' 
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度3-20字符', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度6-20字符', trigger: 'blur' }
  ],
  confirm: [
    { required: true, message: '请确认密码', trigger: 'blur' }
  ],
}

function handleFocus(e) {
  e.target.parentElement.classList.add('focused')
}

function handleBlur(e) {
  if (!e.target.value) {
    e.target.parentElement.classList.remove('focused')
  }
}

const strengthScore = computed(() => {
  const password = form.password
  if (!password) return 0
  
  let score = 0
  if (password.length >= 6) score += 1
  if (password.length >= 10) score += 1
  if (/[A-Z]/.test(password)) score += 1
  if (/[a-z]/.test(password)) score += 1
  if (/[0-9]/.test(password)) score += 1
  if (/[^A-Za-z0-9]/.test(password)) score += 1
  
  return Math.min(score, 5)
})

const strengthClass = computed(() => {
  if (strengthScore.value === 0) return 'none'
  if (strengthScore.value <= 2) return 'weak'
  if (strengthScore.value <= 3) return 'medium'
  return 'strong'
})

const strengthWidth = computed(() => `${(strengthScore.value / 5) * 100}%`)

const strengthText = computed(() => {
  switch (strengthClass.value) {
    case 'weak': return '弱'
    case 'medium': return '中'
    case 'strong': return '强'
    default: return ''
  }
})

function onRegister() {
  regForm.value.validate((valid) => {
    if (valid) {
      if (form.password !== form.confirm) {
        ElMessage.error('两次输入的密码不一致')
        return
      }
      
      loading.value = true
      auth
        .register({ 
          username: form.username, 
          email: form.email, 
          password: form.password, 
          confirmPassword: form.confirm 
        })
        .then((res) => {
          ElMessage.success({
            message: '注册成功！欢迎加入废旧物品再利用平台',
            type: 'success',
            duration: 2000
          })
          setTimeout(() => {
            router.push({ path: '/login' })
          }, 1500)
        })
        .catch((err) => {
          ElMessage.error(err.message || '注册失败，请重试')
        })
        .finally(() => {
          loading.value = false
        })
    }
  })
}
</script>

<style scoped>
.register-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #1a1a3e 0%, #2d1b4e 50%, #3d1a5c 100%);
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
  animation: float 25s ease-in-out infinite;
}

.sphere-1 {
  width: 500px;
  height: 500px;
  background: linear-gradient(135deg, #8b5cf6, #ec4899);
  top: -150px;
  right: -150px;
  animation-delay: 0s;
}

.sphere-2 {
  width: 600px;
  height: 600px;
  background: linear-gradient(135deg, #10b981, #3b82f6);
  bottom: -200px;
  left: -200px;
  animation-delay: -8s;
}

.sphere-3 {
  width: 400px;
  height: 400px;
  background: linear-gradient(135deg, #f59e0b, #ef4444);
  top: 20%;
  left: 50%;
  transform: translateX(-50%);
  animation-delay: -16s;
}

@keyframes float {
  0%, 100% { transform: translate(0, 0) scale(1); }
  33% { transform: translate(-30px, 40px) scale(1.03); }
  66% { transform: translate(40px, -20px) scale(0.97); }
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
  animation: floatCard 18s ease-in-out infinite;
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
  top: 10%;
  right: 8%;
  animation-delay: 0s;
}

.card-2 {
  top: 55%;
  right: 12%;
  animation-delay: -6s;
}

.card-3 {
  bottom: 15%;
  left: 8%;
  animation-delay: -12s;
}

@keyframes floatCard {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-25px); }
}

.register-container {
  position: relative;
  z-index: 10;
  display: flex;
  align-items: center;
  gap: 60px;
}

.register-card {
  width: 440px;
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
  filter: drop-shadow(0 4px 12px rgba(139, 92, 246, 0.4));
}

.brand-title {
  font-size: 32px;
  font-weight: 700;
  color: white;
  margin: 0 0 8px 0;
  letter-spacing: 1px;
}

.brand-subtitle {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.6);
  margin: 0;
}

.register-form {
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
  color: #8b5cf6;
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
  border-color: #8b5cf6;
  box-shadow: 0 0 0 4px rgba(139, 92, 246, 0.1);
}

.input-highlight {
  position: absolute;
  bottom: 0;
  left: 50%;
  width: 0;
  height: 2px;
  background: linear-gradient(90deg, #8b5cf6, #ec4899);
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

.password-strength {
  margin-bottom: 20px;
  padding: 12px 16px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 8px;
}

.strength-label {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
  margin-bottom: 8px;
}

.strength-bar {
  width: 100%;
  height: 4px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 2px;
  overflow: hidden;
  margin-bottom: 6px;
}

.strength-fill {
  height: 100%;
  transition: all 0.3s ease;
  border-radius: 2px;
}

.strength-fill.weak {
  background: linear-gradient(90deg, #ef4444, #f97316);
}

.strength-fill.medium {
  background: linear-gradient(90deg, #f59e0b, #eab308);
}

.strength-fill.strong {
  background: linear-gradient(90deg, #10b981, #059669);
}

.strength-text {
  font-size: 11px;
  font-weight: 500;
}

.strength-text.weak {
  color: #ef4444;
}

.strength-text.medium {
  color: #f59e0b;
}

.strength-text.strong {
  color: #10b981;
}

.submit-item {
  margin-bottom: 24px;
}

.submit-btn {
  width: 100%;
  padding: 16px 32px;
  background: linear-gradient(135deg, #8b5cf6, #7c3aed);
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
  background: linear-gradient(135deg, #7c3aed, #6d28d9);
  opacity: 0;
  transition: opacity 0.3s ease;
}

.submit-btn:hover::before {
  opacity: 1;
}

.submit-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 10px 30px rgba(139, 92, 246, 0.4);
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

.login-link {
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

.login-link:hover {
  background: rgba(255, 255, 255, 0.1);
  border-color: rgba(255, 255, 255, 0.3);
}

.login-link svg {
  width: 20px;
  height: 20px;
}

.benefits-banner {
  display: flex;
  flex-direction: column;
  gap: 24px;
  max-width: 320px;
}

.benefit {
  display: flex;
  align-items: flex-start;
  gap: 16px;
  padding: 24px 28px;
  background: rgba(255, 255, 255, 0.08);
  backdrop-filter: blur(10px);
  border-radius: 16px;
  border: 1px solid rgba(255, 255, 255, 0.1);
  color: white;
  transition: all 0.3s ease;
}

.benefit:hover {
  background: rgba(255, 255, 255, 0.12);
  transform: translateX(-10px);
}

.benefit svg {
  width: 32px;
  height: 32px;
  color: #8b5cf6;
  flex-shrink: 0;
  margin-top: 2px;
}

.benefit-title {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 4px;
}

.benefit-desc {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.7);
  line-height: 1.4;
}

@media (max-width: 1024px) {
  .register-container {
    flex-direction: column;
    gap: 40px;
  }
  
  .benefits-banner {
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: center;
    max-width: none;
  }
  
  .benefit {
    padding: 20px 24px;
    flex: 0 1 calc(50% - 12px);
  }
}

@media (max-width: 480px) {
  .register-card {
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
  
  .benefits-banner {
    display: none;
  }
}
</style>
