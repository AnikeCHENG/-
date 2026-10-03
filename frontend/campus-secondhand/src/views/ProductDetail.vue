<template>
  <div class="product-detail-page">
    <div class="animated-bg">
      <div class="gradient-sphere sphere-1"></div>
      <div class="gradient-sphere sphere-2"></div>
    </div>
    
    <header class="main-header">
      <div class="header-content">
        <button class="back-btn" @click="goBack">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <line x1="19" y1="12" x2="5" y2="12"/>
            <polyline points="12 19 5 12 12 5"/>
          </svg>
          返回
        </button>
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
        <div class="header-actions">
          <button class="action-btn logout-btn" @click="handleLogout" title="退出登录">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/>
              <polyline points="16 17 21 12 16 7"/>
              <line x1="21" y1="12" x2="9" y2="12"/>
            </svg>
          </button>
        </div>
      </div>
    </header>

    <main class="main-content">
      <div v-if="loading" class="loading-container">
        <div class="loading-spinner"></div>
        <p>加载中...</p>
      </div>
      
      <div v-else-if="error" class="error-container">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <circle cx="12" cy="12" r="10"/>
          <line x1="12" y1="8" x2="12" y2="12"/>
          <line x1="12" y1="16" x2="12.01" y2="16"/>
        </svg>
        <p>{{ error }}</p>
        <button class="retry-btn" @click="fetchProduct">重新加载</button>
      </div>
      
      <div v-else-if="product" class="product-detail">
        <div class="product-gallery">
          <div class="main-image">
            <img :src="currentImage" :alt="product.title" />
          </div>
          <div v-if="productImages.length > 1" class="thumbnail-list">
            <div 
              v-for="(img, idx) in productImages" 
              :key="idx"
              class="thumbnail"
              :class="{ active: currentImage === img }"
              @click="currentImage = img"
            >
              <img :src="img" :alt="`缩略图 ${idx + 1}`" />
            </div>
          </div>
        </div>

        <div class="product-info-section">
          <div class="product-status-badge" :class="getStatusClass(product.status)">
            {{ getStatusText(product.status) }}
          </div>
          
          <h1 class="product-title">{{ product.title }}</h1>
          
          <div class="product-price">
            <span class="price-symbol">¥</span>
            <span class="price-value">{{ formatPrice(product.price) }}</span>
          </div>
          
          <div class="product-meta-row">
            <span class="meta-item">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"/>
                <circle cx="12" cy="10" r="3"/>
              </svg>
              {{ product.location || '校园' }}
            </span>
            <span class="meta-item">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
                <line x1="16" y1="2" x2="16" y2="6"/>
                <line x1="8" y1="2" x2="8" y2="6"/>
                <line x1="3" y1="10" x2="21" y2="10"/>
              </svg>
              {{ formatDate(product.createdTime) }}
            </span>
            <span class="meta-item">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="11" cy="11" r="8"/>
                <line x1="21" y1="21" x2="16.65" y2="16.65"/>
              </svg>
              {{ product.viewCount || 0 }} 次浏览
            </span>
          </div>

          <div class="product-tags">
            <span class="tag">{{ getCategoryLabel(product.category) }}</span>
            <span class="tag">{{ getConditionLabel(product.condition) }}</span>
          </div>

          <div class="product-description-section">
            <h3 class="section-title">商品描述</h3>
            <p class="product-description">{{ product.description || '暂无描述' }}</p>
          </div>

          <div class="seller-section">
            <h3 class="section-title">卖家信息</h3>
            <div class="seller-card">
              <div class="seller-avatar">
                <img :src="seller.avatar || '/sample/phone.svg'" :alt="seller.username" />
              </div>
              <div class="seller-info">
                <div class="seller-name">{{ seller.username || '卖家' }}</div>
                <div class="seller-badges">
                  <span class="seller-badge verified">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                      <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
                      <polyline points="22 4 12 14.01 9 11.01"/>
                    </svg>
                    已认证
                  </span>
                </div>
              </div>
              <button class="contact-seller-btn" @click="contactSeller">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/>
                </svg>
                联系卖家
              </button>
            </div>
          </div>

          <div class="action-buttons">
            <button class="action-btn favorite" @click="toggleFavorite">
              <svg v-if="!isFavorited" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78l1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/>
              </svg>
              <svg v-else viewBox="0 0 24 24" fill="currentColor" stroke="none">
                <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78l1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/>
              </svg>
              {{ isFavorited ? '已收藏' : '收藏' }}
            </button>
            <button v-if="product.status === 0" class="action-btn buy" @click="buyProduct" :disabled="buying">
              <svg v-if="!buying" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z"/>
                <line x1="3" y1="6" x2="21" y2="6"/>
                <path d="M16 10a4 4 0 0 1-8 0"/>
              </svg>
              <div v-else class="loading-spinner small"></div>
              {{ buying ? '处理中...' : '立即购买' }}
            </button>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { getProductDetail } from '../api/product'
import { getProfile, getUserById } from '../api/user'
import { createOrder } from '../api/order'

const router = useRouter()
const route = useRoute()
const product = ref(null)
const seller = ref({})
const loading = ref(true)
const error = ref('')
const currentImage = ref('')
const isFavorited = ref(false)

const productImages = computed(() => {
  if (!product.value?.images) return ['/sample/phone.svg']
  const images = product.value.images.split(',')
  return images.length > 0 ? images : ['/sample/phone.svg']
})

async function fetchProduct() {
  loading.value = true
  error.value = ''
  try {
    const res = await getProductDetail(route.params.id)
    if (res.code === 200 && res.data) {
      product.value = res.data
      currentImage.value = productImages.value[0]
      
      if (product.value.userId) {
        await fetchSellerInfo(product.value.userId)
      }
    }
  } catch (e) {
    error.value = e.message || '加载商品详情失败'
  } finally {
    loading.value = false
  }
}

async function fetchSellerInfo(userId) {
  try {
    const res = await getUserById(userId)
    if (res.code === 200 && res.data) {
      seller.value = res.data
    }
  } catch (e) {
    console.error('Failed to fetch seller info:', e)
  }
}

function go(path) {
  router.push(path)
}

function goBack() {
  router.back()
}

function toggleFavorite() {
  isFavorited.value = !isFavorited.value
}

function contactSeller() {
  const token = localStorage.getItem('token') || sessionStorage.getItem('token')
  if (!token) {
    alert('请先登录后再联系卖家')
    router.push('/login')
    return
  }
  
  if (!seller.value.username) {
    alert('获取卖家信息失败，请稍后重试')
    return
  }
  
  router.push({
    path: '/messages',
    query: { seller: seller.value.username, sellerId: product.value.userId }
  })
}

const buying = ref(false)

async function buyProduct() {
  const token = localStorage.getItem('token') || sessionStorage.getItem('token')
  if (!token) {
    alert('请先登录后再购买商品')
    router.push({
      path: '/login',
      query: { redirect: `/products/${product.value.id}` }
    })
    return
  }
  
  buying.value = true
  try {
    console.log('开始创建订单，商品ID:', product.value.id)
    const res = await createOrder(product.value.id)
    console.log('创建订单响应:', res)
    if (res.code === 200) {
      alert('购买成功！订单已创建')
      router.push('/orders')
    } else {
      alert(res.message || '购买失败，请重试')
    }
  } catch (error) {
    console.error('创建订单错误:', error)
    console.error('错误详情:', error.cause)
    alert('购买失败，请检查网络连接或联系客服')
  } finally {
    buying.value = false
  }
}

function formatPrice(price) {
  if (!price) return '0'
  return parseFloat(price).toFixed(2)
}

function formatDate(timestamp) {
  if (!timestamp) return '未知时间'
  const date = new Date(timestamp)
  const now = new Date()
  const diff = now - date
  const minutes = Math.floor(diff / 60000)
  const hours = Math.floor(diff / 3600000)
  const days = Math.floor(diff / 86400000)
  
  if (minutes < 1) return '刚刚发布'
  if (minutes < 60) return `${minutes}分钟前发布`
  if (hours < 24) return `${hours}小时前发布`
  if (days < 7) return `${days}天前发布`
  return date.toLocaleDateString('zh-CN')
}

function getCategoryLabel(category) {
  const categories = {
    'books': '📚 教材书籍',
    'electronics': '📱 电子产品',
    'transport': '🚴 出行工具',
    'gaming': '🎮 游戏数码',
    'clothing': '👕 服饰穿搭',
    'living': '🏠 生活用品',
    'other': '📦 其他'
  }
  return categories[category] || category || '未分类'
}

function getConditionLabel(condition) {
  const conditions = {
    'new': '全新',
    'like-new': '几乎全新',
    'good': '良好',
    'fair': '一般'
  }
  return conditions[condition] || condition || '未标明'
}

function getStatusClass(status) {
  const classes = {
    0: 'status-available',
    1: 'status-sold',
    2: 'status-offline'
  }
  return classes[status] || 'status-available'
}

function getStatusText(status) {
  const texts = {
    0: '在售',
    1: '已售',
    2: '下架'
  }
  return texts[status] || '在售'
}

async function handleLogout() {
  localStorage.removeItem('token')
  localStorage.removeItem('username')
  sessionStorage.removeItem('justLoggedIn')
  router.push('/login')
}

onMounted(() => {
  fetchProduct()
})
</script>

<style scoped>
.product-detail-page {
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
  width: 600px;
  height: 600px;
  background: linear-gradient(135deg, #10b981, #3b82f6);
  top: -300px;
  right: -200px;
}

.sphere-2 {
  width: 500px;
  height: 500px;
  background: linear-gradient(135deg, #8b5cf6, #ec4899);
  bottom: -200px;
  left: -200px;
  animation-delay: -8s;
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

.back-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 16px;
  background: rgba(255, 255, 255, 0.08);
  border: 1px solid rgba(255, 255, 255, 0.15);
  border-radius: 10px;
  color: rgba(255, 255, 255, 0.8);
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.back-btn:hover {
  background: rgba(255, 255, 255, 0.12);
  border-color: rgba(16, 185, 129, 0.5);
  color: white;
}

.back-btn svg {
  width: 20px;
  height: 20px;
}

.logo-section {
  display: flex;
  align-items: center;
  gap: 12px;
  cursor: pointer;
}

.header-logo {
  width: 40px;
  height: 40px;
}

.brand-name {
  font-size: 22px;
  font-weight: 700;
  background: linear-gradient(135deg, #10b981, #3b82f6);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
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

.action-btn:hover {
  background: rgba(255, 255, 255, 0.15);
  border-color: rgba(255, 255, 255, 0.3);
}

.action-btn svg {
  width: 20px;
  height: 20px;
}

.main-content {
  padding: 120px 24px 60px;
  max-width: 1200px;
  margin: 0 auto;
  position: relative;
  z-index: 1;
}

.loading-container,
.error-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 120px 20px;
  color: rgba(255, 255, 255, 0.6);
}

.loading-spinner {
  width: 50px;
  height: 50px;
  border: 3px solid rgba(255, 255, 255, 0.1);
  border-top-color: #10b981;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 20px;
}

.loading-spinner.small {
  width: 20px;
  height: 20px;
  border-width: 2px;
  margin-bottom: 0;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.error-container svg,
.loading-container svg {
  width: 64px;
  height: 64px;
  margin-bottom: 20px;
  opacity: 0.5;
}

.retry-btn {
  margin-top: 20px;
  padding: 12px 32px;
  background: linear-gradient(135deg, #10b981, #059669);
  border: none;
  border-radius: 12px;
  color: white;
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
}

.retry-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(16, 185, 129, 0.3);
}

.product-detail {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 40px;
}

.product-gallery {
  position: sticky;
  top: 100px;
  height: fit-content;
}

.main-image {
  width: 100%;
  aspect-ratio: 1;
  border-radius: 20px;
  overflow: hidden;
  background: rgba(255, 255, 255, 0.05);
  margin-bottom: 16px;
}

.main-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.thumbnail-list {
  display: flex;
  gap: 12px;
  overflow-x: auto;
  padding-bottom: 8px;
}

.thumbnail {
  width: 80px;
  height: 80px;
  border-radius: 12px;
  overflow: hidden;
  cursor: pointer;
  border: 2px solid transparent;
  transition: all 0.3s ease;
  flex-shrink: 0;
}

.thumbnail:hover {
  border-color: rgba(16, 185, 129, 0.5);
}

.thumbnail.active {
  border-color: #10b981;
}

.thumbnail img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.product-info-section {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.product-status-badge {
  display: inline-flex;
  align-items: center;
  padding: 6px 16px;
  border-radius: 20px;
  font-size: 13px;
  font-weight: 600;
  width: fit-content;
}

.status-available {
  background: linear-gradient(135deg, #10b981, #059669);
  color: white;
}

.status-sold {
  background: linear-gradient(135deg, #ef4444, #dc2626);
  color: white;
}

.status-offline {
  background: rgba(255, 255, 255, 0.2);
  color: rgba(255, 255, 255, 0.8);
}

.product-title {
  font-size: 32px;
  font-weight: 700;
  color: white;
  margin: 0;
  line-height: 1.3;
}

.product-price {
  display: flex;
  align-items: baseline;
  gap: 4px;
}

.price-symbol {
  font-size: 24px;
  font-weight: 700;
  color: #10b981;
}

.price-value {
  font-size: 40px;
  font-weight: 700;
  color: #10b981;
}

.product-meta-row {
  display: flex;
  gap: 24px;
  flex-wrap: wrap;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 14px;
  color: rgba(255, 255, 255, 0.6);
}

.meta-item svg {
  width: 18px;
  height: 18px;
}

.product-tags {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.tag {
  padding: 8px 16px;
  background: rgba(255, 255, 255, 0.08);
  border: 1px solid rgba(255, 255, 255, 0.12);
  border-radius: 20px;
  font-size: 13px;
  color: rgba(255, 255, 255, 0.8);
}

.section-title {
  font-size: 18px;
  font-weight: 600;
  color: white;
  margin: 0 0 12px 0;
}

.product-description-section {
  padding: 20px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 16px;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.product-description {
  font-size: 15px;
  color: rgba(255, 255, 255, 0.7);
  line-height: 1.7;
  margin: 0;
}

.seller-section {
  padding: 20px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 16px;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.seller-card {
  display: flex;
  align-items: center;
  gap: 16px;
}

.seller-avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  overflow: hidden;
  flex-shrink: 0;
  border: 2px solid rgba(16, 185, 129, 0.5);
}

.seller-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.seller-info {
  flex: 1;
}

.seller-name {
  font-size: 18px;
  font-weight: 600;
  color: white;
  margin-bottom: 8px;
}

.seller-badges {
  display: flex;
  gap: 8px;
}

.seller-badge {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  padding: 4px 10px;
  border-radius: 20px;
  font-size: 12px;
}

.seller-badge.verified {
  background: rgba(16, 185, 129, 0.2);
  color: #10b981;
}

.seller-badge svg {
  width: 14px;
  height: 14px;
}

.contact-seller-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 20px;
  background: linear-gradient(135deg, #3b82f6, #1d4ed8);
  border: none;
  border-radius: 12px;
  color: white;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
}

.contact-seller-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(59, 130, 246, 0.3);
}

.contact-seller-btn svg {
  width: 18px;
  height: 18px;
}

.action-buttons {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
}

.action-btn.favorite,
.action-btn.buy {
  width: 100%;
  height: auto;
  padding: 16px 24px;
  border-radius: 16px;
  font-size: 16px;
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.action-btn.favorite {
  background: rgba(255, 255, 255, 0.08);
  border: 1px solid rgba(255, 255, 255, 0.15);
  color: rgba(255, 255, 255, 0.8);
}

.action-btn.favorite:hover {
  background: rgba(255, 255, 255, 0.12);
  border-color: rgba(236, 72, 153, 0.5);
  color: white;
}

.action-btn.favorite svg {
  color: #ec4899;
}

.action-btn.buy {
  background: linear-gradient(135deg, #10b981, #059669);
  border: none;
  color: white;
}

.action-btn.buy:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(16, 185, 129, 0.3);
}

@media (max-width: 900px) {
  .product-detail {
    grid-template-columns: 1fr;
  }
  
  .product-gallery {
    position: static;
  }
  
  .product-title {
    font-size: 24px;
  }
  
  .price-value {
    font-size: 32px;
  }
  
  .main-content {
    padding: 100px 16px 40px;
  }
  
  .header-content {
    padding: 0 16px;
  }
  
  .back-btn span {
    display: none;
  }
}
</style>
