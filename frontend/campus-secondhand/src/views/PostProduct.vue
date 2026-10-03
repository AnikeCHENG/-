<template>
  <div class="post-page">
    <div class="animated-bg">
      <div class="gradient-sphere sphere-1"></div>
      <div class="gradient-sphere sphere-2"></div>
      <div class="gradient-sphere sphere-3"></div>
    </div>
    
    <header class="main-header">
      <div class="header-content">
        <div class="logo-section" @click="go('/')">
          <svg class="header-logo" viewBox="0 0 40 40">
            <defs>
              <linearGradient id="logoGrad" x1="0%" y1="0%" x2="100%" y2="100%">
                <stop offset="0%" style="stop-color:#10b981"/>
                <stop offset="100%" style="stop-color:#3b82f6"/>
              </linearGradient>
            </defs>
            <circle cx="20" cy="20" r="18" fill="url(#logoGrad)"/>
            <path d="M13 20 L18 24 L27 16" stroke="white" stroke-width="3" fill="none" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          <span class="brand-name">废旧物品再利用平台</span>
        </div>
        
        <nav class="main-nav">
          <router-link to="/" class="nav-link">首页</router-link>
          <router-link to="/products" class="nav-link">商品</router-link>
          <router-link to="/post" class="nav-link active">发布</router-link>
          <router-link to="/messages" class="nav-link">消息</router-link>
          <router-link to="/profile" class="nav-link">我的</router-link>
        </nav>
        
        <div class="header-actions">
          <button class="action-btn logout-btn" @click="handleLogout" title="退出登录">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/>
              <polyline points="16 17 21 12 16 7"/>
              <line x1="21" y1="12" x2="9" y2="12"/>
            </svg>
          </button>
          <div class="user-avatar" @click="go('/profile')" style="cursor: pointer;">
            <img src="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 40 40'%3E%3Ccircle cx='20' cy='20' r='20' fill='%23333'/%3E%3Ccircle cx='20' cy='16' r='6' fill='%23666'/%3E%3Cpath d='M8 36c0-6.6 5.4-12 12-12s12 5.4 12 12' fill='%23666'/%3E%3C/svg%3E" alt="用户头像" />
          </div>
        </div>
      </div>
    </header>

    <main class="main-content">
      <div class="form-container">
        <div class="form-header">
          <h1 class="form-title">发布闲置</h1>
          <p class="form-subtitle">让闲置物品找到新主人</p>
        </div>

        <form @submit.prevent="handleSubmit" class="post-form">
          <div class="form-section">
            <h2 class="section-title">基本信息</h2>
            
            <div class="form-group">
              <label class="form-label">商品标题 <span class="required">*</span></label>
              <input 
                type="text" 
                v-model="form.title"
                class="form-input"
                placeholder="请输入商品标题，最多50个字符"
                maxlength="50"
                required
              />
              <span class="char-count">{{ form.title.length }}/50</span>
            </div>

            <div class="form-row">
              <div class="form-group">
                <label class="form-label">商品分类 <span class="required">*</span></label>
                <select v-model="form.category" class="form-select" required>
                  <option value="">请选择分类</option>
                  <option value="books">📚 教材书籍</option>
                  <option value="electronics">📱 电子产品</option>
                  <option value="transport">🚴 出行工具</option>
                  <option value="gaming">🎮 游戏数码</option>
                  <option value="clothing">👕 服饰穿搭</option>
                  <option value="living">🏠 生活用品</option>
                  <option value="other">📦 其他</option>
                </select>
              </div>

              <div class="form-group">
                <label class="form-label">商品成色 <span class="required">*</span></label>
                <select v-model="form.condition" class="form-select" required>
                  <option value="">请选择成色</option>
                  <option value="全新">全新未使用</option>
                  <option value="几乎全新">几乎全新</option>
                  <option value="轻微使用痕迹">轻微使用痕迹</option>
                  <option value="明显使用痕迹">明显使用痕迹</option>
                  <option value="有磨损">有磨损/瑕疵</option>
                </select>
              </div>
            </div>

            <div class="form-group">
              <label class="form-label">商品价格 <span class="required">*</span></label>
              <div class="price-input-wrapper">
                <span class="price-symbol">¥</span>
                <input 
                  type="number" 
                  v-model="form.price"
                  class="form-input price-input"
                  placeholder="0.00"
                  min="0"
                  step="0.01"
                  required
                />
              </div>
            </div>

            <div class="form-group">
              <label class="form-label">原价（可选）</label>
              <div class="price-input-wrapper">
                <span class="price-symbol">¥</span>
                <input 
                  type="number" 
                  v-model="form.originalPrice"
                  class="form-input price-input"
                  placeholder="0.00"
                  min="0"
                  step="0.01"
                />
              </div>
              <p class="form-hint">填写原价可以显示折扣信息</p>
            </div>
          </div>

          <div class="form-section">
            <h2 class="section-title">商品描述</h2>
            
            <div class="form-group">
              <label class="form-label">详细描述 <span class="required">*</span></label>
              <textarea 
                v-model="form.description"
                class="form-textarea"
                placeholder="请详细描述商品的状态、使用时间、转手原因等，让买家更了解商品情况..."
                rows="6"
                maxlength="500"
                required
              ></textarea>
              <span class="char-count">{{ form.description.length }}/500</span>
            </div>
          </div>

          <div class="form-section">
            <h2 class="section-title">商品图片</h2>
            
            <div class="form-group">
              <label class="form-label">上传图片</label>
              <div class="image-upload">
                <div 
                  v-for="(img, index) in form.images" 
                  :key="index" 
                  class="uploaded-image"
                >
                  <img :src="img" :alt="'商品图片' + (index + 1)" />
                  <button type="button" class="remove-image" @click="removeImage(index)">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                      <line x1="18" y1="6" x2="6" y2="18"/>
                      <line x1="6" y1="6" x2="18" y2="18"/>
                    </svg>
                  </button>
                </div>
                <label class="upload-button" v-if="form.images.length < 9">
                  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"/>
                    <polyline points="17 8 12 3 7 8"/>
                    <line x1="12" y1="3" x2="12" y2="15"/>
                  </svg>
                  <span>上传图片</span>
                  <input 
                    type="file" 
                    accept="image/*" 
                    @change="handleImageUpload"
                    hidden
                  />
                </label>
              </div>
              <p class="form-hint">最多可上传9张图片，支持JPG、PNG格式</p>
            </div>
          </div>

          <div class="form-section tips-section">
            <h2 class="section-title">发布须知</h2>
            <ul class="tips-list">
              <li>
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
                  <polyline points="22 4 12 14.01 9 11.01"/>
                </svg>
                <span>商品信息需真实准确，不得虚假宣传</span>
              </li>
              <li>
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
                  <polyline points="22 4 12 14.01 9 11.01"/>
                </svg>
                <span>建议上传实物图片，展示商品真实状态</span>
              </li>
              <li>
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
                  <polyline points="22 4 12 14.01 9 11.01"/>
                </svg>
                <span>定价合理，描述详细，更容易成交</span>
              </li>
              <li>
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
                  <polyline points="22 4 12 14.01 9 11.01"/>
                </svg>
                <span>遵守平台规则，文明交易</span>
              </li>
            </ul>
          </div>

          <div class="form-actions">
            <button type="button" class="btn-secondary" @click="saveDraft">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"/>
                <polyline points="17 21 17 13 7 13 7 21"/>
                <polyline points="7 3 7 8 15 8"/>
              </svg>
              保存草稿
            </button>
            <button type="submit" class="btn-primary" :disabled="submitting">
              <span v-if="!submitting">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <line x1="22" y1="2" x2="11" y2="13"/>
                  <polygon points="22 2 15 22 11 13 2 9 22 2"/>
                </svg>
                发布商品
              </span>
              <span v-else class="loading">
                <div class="btn-spinner"></div>
                发布中...
              </span>
            </button>
          </div>
        </form>
      </div>
    </main>

    <footer class="main-footer">
      <div class="footer-content">
        <p>© 2025 废旧物品再利用平台</p>
      </div>
    </footer>

    <div v-if="showSuccess" class="success-modal">
      <div class="modal-content">
        <div class="success-icon">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
            <polyline points="22 4 12 14.01 9 11.01"/>
          </svg>
        </div>
        <h2>发布成功！</h2>
        <p>您的商品已成功发布，等待买家咨询</p>
        <div class="modal-actions">
          <button class="btn-secondary" @click="go('/products')">返回商品列表</button>
          <button class="btn-primary" @click="continuePost">继续发布</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { createProduct } from '../api/product'

const router = useRouter()
const submitting = ref(false)
const showSuccess = ref(false)

const form = reactive({
  title: '',
  category: '',
  condition: '',
  price: '',
  originalPrice: '',
  description: '',
  images: []
})

function go(path) {
  router.push(path)
}

function handleImageUpload(event) {
  const file = event.target.files[0]
  if (!file) return
  
  if (form.images.length >= 9) {
    alert('最多只能上传9张图片')
    return
  }
  
  const reader = new FileReader()
  reader.onload = (e) => {
    const img = new Image()
    img.onload = () => {
      const canvas = document.createElement('canvas')
      const ctx = canvas.getContext('2d')
      
      let width = img.width
      let height = img.height
      const maxSize = 800
      
      if (width > height) {
        if (width > maxSize) {
          height = Math.round(height * (maxSize / width))
          width = maxSize
        }
      } else {
        if (height > maxSize) {
          width = Math.round(width * (maxSize / height))
          height = maxSize
        }
      }
      
      canvas.width = width
      canvas.height = height
      ctx.drawImage(img, 0, 0, width, height)
      
      const compressedDataUrl = canvas.toDataURL('image/jpeg', 0.7)
      form.images.push(compressedDataUrl)
    }
    img.src = e.target.result
  }
  reader.readAsDataURL(file)
  
  event.target.value = ''
}

function removeImage(index) {
  form.images.splice(index, 1)
}

async function handleSubmit() {
  if (!form.title || !form.category || !form.condition || !form.price || !form.description) {
    alert('请填写所有必填项')
    return
  }

  submitting.value = true
  
  try {
    const res = await createProduct({
      title: form.title,
      category: form.category,
      condition: form.condition,
      price: parseFloat(form.price),
      originalPrice: form.originalPrice ? parseFloat(form.originalPrice) : null,
      description: form.description,
      images: form.images.join(',')
    })
    
    if (res.code === 200) {
      showSuccess.value = true
      form.title = ''
      form.category = ''
      form.condition = ''
      form.price = ''
      form.originalPrice = ''
      form.description = ''
      form.images = []
      setTimeout(() => {
        router.push('/products')
      }, 1500)
    } else {
      alert(res.message || '发布失败，请重试')
    }
  } catch (e) {
    alert(e.message || '发布失败，请重试')
  } finally {
    submitting.value = false
  }
}

function saveDraft() {
  const draft = {
    ...form,
    savedAt: Date.now()
  }
  const drafts = JSON.parse(localStorage.getItem('product_drafts') || '[]')
  drafts.unshift(draft)
  localStorage.setItem('product_drafts', JSON.stringify(drafts))
  localStorage.removeItem('product_draft')
  alert('草稿已保存到草稿箱')
}

function continuePost() {
  showSuccess.value = false
  form.title = ''
  form.category = ''
  form.condition = ''
  form.price = ''
  form.originalPrice = ''
  form.description = ''
  form.images = []
}

async function handleLogout() {
  localStorage.removeItem('token')
  localStorage.removeItem('username')
  localStorage.removeItem('product_draft')
  sessionStorage.removeItem('justLoggedIn')
  router.push('/login')
}

onMounted(() => {
  const draft = localStorage.getItem('product_draft')
  if (draft) {
    try {
      const data = JSON.parse(draft)
      Object.assign(form, data)
    } catch (e) {
      console.log('读取草稿失败')
    }
  }
})
</script>

<style scoped>
.post-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #0f0f23 0%, #1a1a3e 50%, #2d1b4e 100%);
  position: relative;
  overflow-x: hidden;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
}

.animated-bg {
  position: fixed;
  inset: 0;
  pointer-events: none;
  z-index: 0;
}

.gradient-sphere {
  position: absolute;
  border-radius: 50%;
  filter: blur(100px);
  opacity: 0.3;
  animation: floatBg 25s ease-in-out infinite;
}

.sphere-1 {
  width: 800px;
  height: 800px;
  background: linear-gradient(135deg, #10b981, #3b82f6);
  top: -400px;
  right: -200px;
}

.sphere-2 {
  width: 600px;
  height: 600px;
  background: linear-gradient(135deg, #8b5cf6, #ec4899);
  bottom: -200px;
  left: -200px;
  animation-delay: -8s;
}

.sphere-3 {
  width: 500px;
  height: 500px;
  background: linear-gradient(135deg, #f59e0b, #ef4444);
  top: 40%;
  left: 40%;
  animation-delay: -16s;
}

@keyframes floatBg {
  0%, 100% { transform: translate(0, 0) scale(1); }
  25% { transform: translate(50px, -50px) scale(1.05); }
  50% { transform: translate(-30px, 30px) scale(0.95); }
  75% { transform: translate(30px, 50px) scale(1.02); }
}

.main-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 100;
  background: rgba(15, 15, 35, 0.8);
  backdrop-filter: blur(20px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.header-content {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 24px;
  height: 70px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo-section {
  display: flex;
  align-items: center;
  gap: 12px;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.logo-section:hover {
  transform: scale(1.02);
}

.header-logo {
  width: 42px;
  height: 42px;
}

.brand-name {
  font-size: 22px;
  font-weight: 700;
  background: linear-gradient(135deg, #10b981, #3b82f6);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.main-nav {
  display: flex;
  gap: 8px;
}

.nav-link {
  padding: 10px 20px;
  color: rgba(255, 255, 255, 0.7);
  text-decoration: none;
  font-size: 15px;
  font-weight: 500;
  border-radius: 10px;
  transition: all 0.3s ease;
}

.nav-link:hover {
  color: white;
  background: rgba(255, 255, 255, 0.1);
}

.nav-link.active {
  color: white;
  background: rgba(16, 185, 129, 0.2);
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 12px;
}

.action-btn {
  width: 42px;
  height: 42px;
  border-radius: 10px;
  border: 1px solid rgba(255, 255, 255, 0.15);
  background: rgba(255, 255, 255, 0.05);
  color: white;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
}

.action-btn svg {
  width: 20px;
  height: 20px;
}

.action-btn:hover {
  background: rgba(255, 255, 255, 0.15);
  border-color: rgba(255, 255, 255, 0.3);
}

.user-avatar {
  width: 42px;
  height: 42px;
  border-radius: 50%;
  overflow: hidden;
  border: 2px solid rgba(16, 185, 129, 0.5);
}

.user-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.main-content {
  padding: 120px 24px 60px;
  max-width: 900px;
  margin: 0 auto;
  position: relative;
  z-index: 1;
}

.form-container {
  background: rgba(255, 255, 255, 0.06);
  border: 1px solid rgba(255, 255, 255, 0.12);
  border-radius: 24px;
  padding: 40px;
  backdrop-filter: blur(20px);
}

.form-header {
  text-align: center;
  margin-bottom: 40px;
}

.form-title {
  font-size: 32px;
  font-weight: 700;
  color: white;
  margin-bottom: 8px;
}

.form-subtitle {
  font-size: 16px;
  color: rgba(255, 255, 255, 0.6);
}

.post-form {
  display: flex;
  flex-direction: column;
  gap: 32px;
}

.form-section {
  padding-bottom: 28px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.form-section:last-child {
  border-bottom: none;
  padding-bottom: 0;
}

.section-title {
  font-size: 18px;
  font-weight: 600;
  color: white;
  margin-bottom: 24px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.section-title::before {
  content: '';
  width: 4px;
  height: 20px;
  background: linear-gradient(180deg, #10b981, #3b82f6);
  border-radius: 2px;
}

.form-group {
  margin-bottom: 24px;
  position: relative;
}

.form-group:last-child {
  margin-bottom: 0;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 24px;
}

.form-label {
  display: block;
  font-size: 14px;
  font-weight: 500;
  color: rgba(255, 255, 255, 0.9);
  margin-bottom: 10px;
}

.required {
  color: #ef4444;
  margin-left: 4px;
}

.form-input,
.form-select,
.form-textarea {
  width: 100%;
  padding: 14px 18px;
  background: rgba(255, 255, 255, 0.08);
  border: 1px solid rgba(255, 255, 255, 0.15);
  border-radius: 12px;
  font-size: 15px;
  color: white;
  transition: all 0.3s ease;
}

.form-input:focus,
.form-select:focus,
.form-textarea:focus {
  outline: none;
  border-color: rgba(16, 185, 129, 0.5);
  background: rgba(255, 255, 255, 0.12);
}

.form-input::placeholder,
.form-textarea::placeholder {
  color: rgba(255, 255, 255, 0.4);
}

.form-select {
  cursor: pointer;
  appearance: none;
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='rgba(255,255,255,0.5)' stroke-width='2'%3E%3Cpolyline points='6 9 12 15 18 9'/%3E%3C/svg%3E");
  background-repeat: no-repeat;
  background-position: right 16px center;
  background-size: 18px;
}

.form-select option {
  background: #1a1a3e;
  color: white;
}

.form-textarea {
  resize: vertical;
  min-height: 150px;
}

.price-input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.price-symbol {
  position: absolute;
  left: 18px;
  font-size: 20px;
  font-weight: 600;
  color: rgba(255, 255, 255, 0.7);
}

.price-input {
  padding-left: 45px;
  font-size: 24px;
  font-weight: 600;
}

.char-count {
  position: absolute;
  right: 14px;
  bottom: 14px;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.4);
}

.form-hint {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.5);
  margin-top: 8px;
}

.image-upload {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}

.uploaded-image {
  position: relative;
  width: 100px;
  height: 100px;
  border-radius: 12px;
  overflow: hidden;
}

.uploaded-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.remove-image {
  position: absolute;
  top: 4px;
  right: 4px;
  width: 24px;
  height: 24px;
  background: rgba(0, 0, 0, 0.6);
  border: none;
  border-radius: 50%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
}

.remove-image svg {
  width: 14px;
  height: 14px;
  color: white;
}

.remove-image:hover {
  background: rgba(239, 68, 68, 0.8);
}

.upload-button {
  width: 100px;
  height: 100px;
  border: 2px dashed rgba(255, 255, 255, 0.3);
  border-radius: 12px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
}

.upload-button svg {
  width: 28px;
  height: 28px;
  color: rgba(255, 255, 255, 0.5);
  margin-bottom: 6px;
}

.upload-button span {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.5);
}

.upload-button:hover {
  border-color: rgba(16, 185, 129, 0.5);
  background: rgba(16, 185, 129, 0.1);
}

.upload-button:hover svg,
.upload-button:hover span {
  color: #10b981;
}

.tips-section {
  background: rgba(16, 185, 129, 0.08);
  border: 1px solid rgba(16, 185, 129, 0.2);
  border-radius: 16px;
  padding: 24px;
  margin-bottom: 0;
}

.tips-list {
  list-style: none;
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.tips-list li {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 14px;
  color: rgba(255, 255, 255, 0.8);
}

.tips-list svg {
  width: 20px;
  height: 20px;
  color: #10b981;
  flex-shrink: 0;
}

.form-actions {
  display: flex;
  gap: 16px;
  justify-content: flex-end;
  padding-top: 10px;
}

.btn-primary,
.btn-secondary {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 14px 32px;
  border-radius: 12px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn-primary {
  background: linear-gradient(135deg, #10b981, #059669);
  border: none;
  color: white;
}

.btn-primary:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 8px 24px rgba(16, 185, 129, 0.4);
}

.btn-primary:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.btn-primary svg {
  width: 20px;
  height: 20px;
}

.btn-spinner {
  width: 18px;
  height: 18px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-top-color: white;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.btn-secondary {
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  color: white;
}

.btn-secondary:hover {
  background: rgba(255, 255, 255, 0.15);
  border-color: rgba(255, 255, 255, 0.3);
}

.btn-secondary svg {
  width: 18px;
  height: 18px;
}

.main-footer {
  position: relative;
  z-index: 1;
  padding: 30px 24px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  margin-top: 60px;
}

.footer-content {
  max-width: 900px;
  margin: 0 auto;
  text-align: center;
}

.footer-content p {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.5);
}

.success-modal {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.8);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 200;
  backdrop-filter: blur(10px);
}

.modal-content {
  background: rgba(30, 30, 60, 0.95);
  border: 1px solid rgba(255, 255, 255, 0.15);
  border-radius: 24px;
  padding: 48px;
  text-align: center;
  max-width: 420px;
}

.success-icon {
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, #10b981, #059669);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 24px;
}

.success-icon svg {
  width: 40px;
  height: 40px;
  color: white;
}

.modal-content h2 {
  font-size: 26px;
  font-weight: 700;
  color: white;
  margin-bottom: 10px;
}

.modal-content p {
  font-size: 15px;
  color: rgba(255, 255, 255, 0.6);
  margin-bottom: 32px;
}

.modal-actions {
  display: flex;
  gap: 12px;
  justify-content: center;
}

.modal-actions .btn-primary,
.modal-actions .btn-secondary {
  padding: 12px 28px;
  font-size: 15px;
}
</style>
