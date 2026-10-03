<template>
  <div class="products-page">
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
          <router-link to="/products" class="nav-link active">商品</router-link>
          <router-link to="/post" class="nav-link">发布</router-link>
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
      <div class="page-header">
        <h1 class="page-title">商品列表</h1>
        <p class="page-subtitle">发现校园里的实惠好物</p>
      </div>

      <div class="search-section">
        <div class="search-bar-wrapper">
          <div class="search-bar">
            <svg class="search-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <circle cx="11" cy="11" r="8"/>
              <line x1="21" y1="21" x2="16.65" y2="16.65"/>
            </svg>
            <input 
              type="text" 
              v-model="searchKeyword" 
              placeholder="搜索商品名称、描述..."
              @keyup.enter="handleSearch"
              @focus="showSearchSuggestions = true"
              @blur="hideSearchSuggestions"
            />
            <button v-if="searchKeyword" class="clear-btn" @click="clearSearch">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="18" y1="6" x2="6" y2="18"/>
                <line x1="6" y1="6" x2="18" y2="18"/>
              </svg>
            </button>
            <button class="search-btn" @click="handleSearch">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="11" cy="11" r="8"/>
                <line x1="21" y1="21" x2="16.65" y2="16.65"/>
              </svg>
              搜索
            </button>
          </div>
          
          <div v-if="showSearchSuggestions && (searchSuggestions.length > 0 || searchHistory.length > 0)" class="search-suggestions">
            <div v-if="searchHistory.length > 0" class="suggestion-section">
              <div class="suggestion-header">
                <span>搜索历史</span>
                <button class="clear-history-btn" @click="clearSearchHistory">清空</button>
              </div>
              <div class="suggestion-item" v-for="(item, index) in searchHistory.slice(0, 5)" :key="`history-${index}`" @mousedown="selectSuggestion(item)">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <circle cx="12" cy="12" r="10"/>
                  <polyline points="12 6 12 12 16 14"/>
                </svg>
                <span>{{ item }}</span>
              </div>
            </div>
            
            <div v-if="searchSuggestions.length > 0" class="suggestion-section">
              <div class="suggestion-header">
                <span>搜索建议</span>
              </div>
              <div class="suggestion-item" v-for="(item, index) in searchSuggestions" :key="`suggestion-${index}`" @mousedown="selectSuggestion(item)">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <circle cx="11" cy="11" r="8"/>
                  <line x1="21" y1="21" x2="16.65" y2="16.65"/>
                </svg>
                <span>{{ item }}</span>
              </div>
            </div>
          </div>
        </div>
        
        <div class="advanced-filters">
          <button class="filter-toggle-btn" @click="showAdvancedFilters = !showAdvancedFilters">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <line x1="4" y1="21" x2="4" y2="14"/>
              <line x1="4" y1="10" x2="4" y2="3"/>
              <line x1="12" y1="21" x2="12" y2="12"/>
              <line x1="12" y1="8" x2="12" y2="3"/>
              <line x1="20" y1="21" x2="20" y2="16"/>
              <line x1="20" y1="12" x2="20" y2="3"/>
            </svg>
            高级筛选
          </button>
          
          <div v-if="showAdvancedFilters" class="advanced-filter-options">
            <div class="filter-group">
              <label>价格范围</label>
              <div class="price-range">
                <input 
                  type="number" 
                  v-model="priceMin" 
                  placeholder="最低" 
                  class="price-input" 
                  @input="validatePriceRange"
                  min="0"
                  step="0.01"
                />
                <span>-</span>
                <input 
                  type="number" 
                  v-model="priceMax" 
                  placeholder="最高" 
                  class="price-input" 
                  @input="validatePriceRange"
                  min="0"
                  step="0.01"
                />
              </div>
              <p v-if="priceError" class="error-message">{{ priceError }}</p>
            </div>
            
            <div class="filter-group">
              <label>成色</label>
              <div class="condition-options">
                <button 
                  v-for="cond in conditions" 
                  :key="cond.value"
                  class="condition-btn"
                  :class="{ active: selectedCondition === cond.value }"
                  @click="selectedCondition = selectedCondition === cond.value ? '' : cond.value"
                >
                  {{ cond.label }}
                </button>
              </div>
            </div>
            
            <div class="filter-group">
              <label>排序方式</label>
              <div class="sort-options">
                <button 
                  v-for="sort in sortOptions" 
                  :key="sort.value"
                  class="sort-btn"
                  :class="{ active: selectedSort === sort.value }"
                  @click="selectedSort = sort.value"
                >
                  {{ sort.label }}
                </button>
              </div>
            </div>
            
            <div class="filter-actions">
              <button class="apply-filter-btn" @click="applyFilters">
                应用筛选
              </button>
              <button class="reset-filter-btn" @click="resetFilters">
                重置
              </button>
            </div>
          </div>
        </div>
      </div>

      <div class="filter-section">
        <div class="filter-tabs">
          <button 
            class="filter-tab" 
            :class="{ active: activeCategory === 'all' }"
            @click="selectCategory('all')"
          >
            全部
          </button>
          <button 
            v-for="cat in categories" 
            :key="cat.value"
            class="filter-tab" 
            :class="{ active: activeCategory === cat.value }"
            @click="selectCategory(cat.value)"
          >
            {{ cat.label }}
          </button>
        </div>
      </div>

      <div class="products-section">
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
          <button class="retry-btn" @click="fetchProducts">重新加载</button>
        </div>
        
        <div v-else-if="filteredProducts.length === 0" class="empty-container">
          <svg v-if="searchKeyword || activeCategory !== 'all' || priceMin || priceMax || selectedCondition" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <circle cx="11" cy="11" r="8"/>
            <line x1="21" y1="21" x2="16.65" y2="16.65"/>
          </svg>
          <svg v-else viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <circle cx="9" cy="21" r="1"/>
            <circle cx="20" cy="21" r="1"/>
            <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"/>
          </svg>
          <p v-if="searchKeyword">没有找到包含"{{ searchKeyword }}"的商品</p>
          <p v-else-if="activeCategory !== 'all'">该分类下暂无商品</p>
          <p v-else-if="priceMin || priceMax || selectedCondition">没有符合筛选条件的商品</p>
          <p v-else>暂无商品</p>
          <div class="empty-actions">
            <button v-if="searchKeyword || priceMin || priceMax || selectedCondition" class="clear-filters-btn" @click="clearAllFilters">
              清除所有筛选
            </button>
            <button class="post-btn" @click="go('/post')">发布商品</button>
          </div>
        </div>
        
        <div v-else class="products-grid">
          <div 
            v-for="product in filteredProducts" 
            :key="product.id" 
            class="product-card"
            @click="viewProduct(product.id)"
          >
            <div class="product-image">
              <img v-if="product.images" :src="product.images" :alt="product.title" class="product-img" />
              <div v-else class="image-placeholder">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
                  <rect x="3" y="3" width="18" height="18" rx="2" ry="2"/>
                  <circle cx="8.5" cy="8.5" r="1.5"/>
                  <polyline points="21 15 16 10 5 21"/>
                </svg>
              </div>
              <div class="product-status" :class="getStatusClass(product.status)">
                {{ getStatusText(product.status) }}
              </div>
            </div>
            <div class="product-info">
              <h3 class="product-title">{{ product.title }}</h3>
              <p class="product-desc">{{ product.description }}</p>
              <div class="product-meta">
                <span class="product-category">{{ product.category }}</span>
                <span class="product-condition">{{ product.condition }}</span>
              </div>
              <div class="product-footer">
                <div class="product-price">
                  <span class="price-symbol">¥</span>
                  <span class="price-value">{{ formatPrice(product.price) }}</span>
                </div>
                <span class="product-views">{{ product.viewCount || 0 }}人浏览</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>

    <footer class="main-footer">
      <div class="footer-content">
        <p>© 2025 废旧物品再利用平台</p>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { getProductList } from '../api/product'

const router = useRouter()
const route = useRoute()
const allProducts = ref([])
const loading = ref(true)
const error = ref('')
const searchKeyword = ref('')
const activeCategory = ref('all')
const showAdvancedFilters = ref(false)
const priceMin = ref('')
const priceMax = ref('')
const selectedCondition = ref('')
const selectedSort = ref('newest')
const showSearchSuggestions = ref(false)
const searchHistory = ref([])
const priceError = ref('')

const conditions = [
  { value: 'new', label: '全新' },
  { value: 'like-new', label: '几乎全新' },
  { value: 'good', label: '良好' },
  { value: 'fair', label: '一般' }
]

const sortOptions = [
  { value: 'newest', label: '最新发布' },
  { value: 'price-low', label: '价格从低到高' },
  { value: 'price-high', label: '价格从高到低' },
  { value: 'views', label: '浏览量最多' }
]

const categories = [
  { value: 'books', label: '📚 教材书籍' },
  { value: 'electronics', label: '📱 电子产品' },
  { value: 'transport', label: '🚴 出行工具' },
  { value: 'gaming', label: '🎮 游戏数码' },
  { value: 'clothing', label: '👕 服饰穿搭' },
  { value: 'living', label: '🏠 生活用品' },
  { value: 'other', label: '📦 其他' }
]

const searchSuggestions = computed(() => {
  if (!searchKeyword.value || searchKeyword.value.length < 1) {
    return []
  }
  
  const keyword = searchKeyword.value.toLowerCase()
  const suggestions = new Set()
  
  allProducts.value.forEach(product => {
    if (product.title && product.title.toLowerCase().includes(keyword)) {
      suggestions.add(product.title)
    }
    if (product.description && product.description.toLowerCase().includes(keyword)) {
      const words = product.description.split(/\s+/).filter(word => 
        word.toLowerCase().includes(keyword) && word.length > 1
      )
      words.forEach(word => suggestions.add(word))
    }
  })
  
  return Array.from(suggestions).slice(0, 6)
})

const filteredProducts = computed(() => {
  let result = [...allProducts.value]
  
  // 分类筛选
  if (activeCategory.value !== 'all') {
    result = result.filter(p => p.category === activeCategory.value)
  }
  
  // 搜索关键词筛选
  if (searchKeyword.value) {
    const keyword = searchKeyword.value.toLowerCase()
    result = result.filter(p => 
      (p.title && p.title.toLowerCase().includes(keyword)) ||
      (p.description && p.description.toLowerCase().includes(keyword))
    )
  }
  
  // 价格范围筛选
  if (priceMin.value) {
    const min = parseFloat(priceMin.value)
    if (!isNaN(min)) {
      result = result.filter(p => {
        const price = parseFloat(p.price)
        return !isNaN(price) && price >= min
      })
    }
  }
  
  if (priceMax.value) {
    const max = parseFloat(priceMax.value)
    if (!isNaN(max)) {
      result = result.filter(p => {
        const price = parseFloat(p.price)
        return !isNaN(price) && price <= max
      })
    }
  }
  
  // 成色筛选
  if (selectedCondition.value) {
    result = result.filter(p => p.condition === selectedCondition.value)
  }
  
  // 排序
  if (selectedSort.value) {
    switch (selectedSort.value) {
      case 'newest':
        result.sort((a, b) => {
          const dateA = a.createdTime ? new Date(a.createdTime) : new Date(0)
          const dateB = b.createdTime ? new Date(b.createdTime) : new Date(0)
          return dateB - dateA
        })
        break
      case 'price-low':
        result.sort((a, b) => {
          const priceA = parseFloat(a.price) || 0
          const priceB = parseFloat(b.price) || 0
          return priceA - priceB
        })
        break
      case 'price-high':
        result.sort((a, b) => {
          const priceA = parseFloat(a.price) || 0
          const priceB = parseFloat(b.price) || 0
          return priceB - priceA
        })
        break
      case 'views':
        result.sort((a, b) => (b.viewCount || 0) - (a.viewCount || 0))
        break
    }
  }
  
  return result
})

function go(path) {
  router.push(path)
}

function hideSearchSuggestions() {
  setTimeout(() => {
    showSearchSuggestions.value = false
  }, 200)
}

function selectSuggestion(suggestion) {
  searchKeyword.value = suggestion
  showSearchSuggestions.value = false
  handleSearch()
}

function clearSearch() {
  searchKeyword.value = ''
  showSearchSuggestions.value = false
}

function saveSearchHistory(keyword) {
  if (!keyword.trim()) return
  
  const index = searchHistory.value.indexOf(keyword)
  if (index > -1) {
    searchHistory.value.splice(index, 1)
  }
  
  searchHistory.value.unshift(keyword)
  searchHistory.value = searchHistory.value.slice(0, 10)
  
  localStorage.setItem('searchHistory', JSON.stringify(searchHistory.value))
}

function clearSearchHistory() {
  searchHistory.value = []
  localStorage.removeItem('searchHistory')
}

function loadSearchHistory() {
  const saved = localStorage.getItem('searchHistory')
  if (saved) {
    try {
      searchHistory.value = JSON.parse(saved)
    } catch (e) {
      console.error('Failed to parse search history:', e)
    }
  }
}

async function fetchProducts() {
  loading.value = true
  error.value = ''
  try {
    const res = await getProductList()
    if (res.code === 200 && res.data) {
      allProducts.value = res.data
    }
  } catch (e) {
    error.value = e.message || '加载商品失败'
  } finally {
    loading.value = false
  }
}

async function handleSearch() {
  saveSearchHistory(searchKeyword.value)
  showSearchSuggestions.value = false
}

async function selectCategory(category) {
  activeCategory.value = category
  showAdvancedFilters.value = false
}

function applyFilters() {
  // 验证价格范围
  validatePriceRange()
  // 如果价格范围有效，才关闭筛选面板
  if (!priceError.value) {
    showAdvancedFilters.value = false
  }
}

function resetFilters() {
  priceMin.value = ''
  priceMax.value = ''
  priceError.value = ''
  selectedCondition.value = ''
  selectedSort.value = 'newest'
}

function validatePriceRange() {
  priceError.value = ''
  if (priceMin.value && priceMax.value) {
    const min = parseFloat(priceMin.value)
    const max = parseFloat(priceMax.value)
    if (min > max) {
      priceError.value = '最低价格不能大于最高价格'
    }
  }
}

function clearAllFilters() {
  searchKeyword.value = ''
  activeCategory.value = 'all'
  priceMin.value = ''
  priceMax.value = ''
  priceError.value = ''
  selectedCondition.value = ''
  selectedSort.value = 'newest'
  showAdvancedFilters.value = false
}

function viewProduct(id) {
  router.push(`/products/${id}`)
}

function formatPrice(price) {
  if (!price) return '0'
  return parseFloat(price).toFixed(2)
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
  loadSearchHistory()
  
  if (route.query.search) {
    searchKeyword.value = route.query.search
  }
  
  if (route.query.category) {
    activeCategory.value = route.query.category
  }
  
  fetchProducts()
})

// 监听路由变化，重新加载数据
watch(
  () => route.fullPath,
  () => {
    if (route.query.search) {
      searchKeyword.value = route.query.search
    }
    
    if (route.query.category) {
      activeCategory.value = route.query.category
    }
    
    fetchProducts()
  }
)
</script>

<style scoped>
.products-page {
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
  max-width: 1400px;
  margin: 0 auto;
  position: relative;
  z-index: 1;
}

.page-header {
  text-align: center;
  margin-bottom: 40px;
}

.page-title {
  font-size: 36px;
  font-weight: 700;
  color: white;
  margin-bottom: 8px;
}

.page-subtitle {
  font-size: 16px;
  color: rgba(255, 255, 255, 0.6);
}

.search-section {
  margin-bottom: 30px;
}

.search-bar-wrapper {
  position: relative;
}

.search-bar {
  display: flex;
  align-items: center;
  background: rgba(255, 255, 255, 0.08);
  border: 1px solid rgba(255, 255, 255, 0.15);
  border-radius: 16px;
  padding: 6px 6px 6px 20px;
  backdrop-filter: blur(10px);
  transition: all 0.3s ease;
  margin-bottom: 16px;
}

.search-bar:focus-within {
  border-color: rgba(16, 185, 129, 0.5);
  background: rgba(255, 255, 255, 0.12);
}

.search-icon {
  width: 22px;
  height: 22px;
  color: rgba(255, 255, 255, 0.5);
  margin-right: 12px;
}

.search-bar input {
  flex: 1;
  background: transparent;
  border: none;
  outline: none;
  font-size: 16px;
  color: white;
  padding: 12px 0;
}

.search-bar input::placeholder {
  color: rgba(255, 255, 255, 0.4);
}

.search-bar .search-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 24px;
  background: linear-gradient(135deg, #10b981, #059669);
  border: none;
  border-radius: 12px;
  color: white;
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
}

.search-bar .search-btn svg {
  width: 18px;
  height: 18px;
}

.search-bar .search-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(16, 185, 129, 0.3);
}

.clear-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 36px;
  height: 36px;
  background: rgba(255, 255, 255, 0.1);
  border: none;
  border-radius: 8px;
  color: rgba(255, 255, 255, 0.6);
  cursor: pointer;
  margin-right: 8px;
  transition: all 0.3s ease;
}

.clear-btn:hover {
  background: rgba(255, 255, 255, 0.15);
  color: white;
}

.clear-btn svg {
  width: 18px;
  height: 18px;
}

.search-suggestions {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  margin-top: 8px;
  background: rgba(20, 20, 40, 0.95);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.15);
  border-radius: 16px;
  padding: 12px 0;
  z-index: 1000;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.4);
}

.suggestion-section {
  padding: 8px 0;
}

.suggestion-section + .suggestion-section {
  border-top: 1px solid rgba(255, 255, 255, 0.1);
}

.suggestion-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 4px 16px 12px;
}

.suggestion-header span {
  font-size: 12px;
  font-weight: 600;
  color: rgba(255, 255, 255, 0.5);
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.clear-history-btn {
  font-size: 12px;
  color: rgba(239, 68, 68, 0.8);
  background: none;
  border: none;
  cursor: pointer;
  padding: 4px 8px;
  border-radius: 6px;
  transition: all 0.3s ease;
}

.clear-history-btn:hover {
  background: rgba(239, 68, 68, 0.1);
  color: #ef4444;
}

.suggestion-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 10px 16px;
  color: rgba(255, 255, 255, 0.8);
  cursor: pointer;
  transition: all 0.2s ease;
}

.suggestion-item:hover {
  background: rgba(255, 255, 255, 0.08);
  color: white;
}

.suggestion-item svg {
  width: 18px;
  height: 18px;
  flex-shrink: 0;
  opacity: 0.5;
}

.suggestion-item span {
  flex: 1;
  font-size: 14px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.advanced-filters {
  position: relative;
}

.filter-toggle-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background: rgba(255, 255, 255, 0.08);
  border: 1px solid rgba(255, 255, 255, 0.15);
  border-radius: 10px;
  color: rgba(255, 255, 255, 0.8);
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  margin-bottom: 16px;
}

.filter-toggle-btn:hover {
  background: rgba(255, 255, 255, 0.12);
  border-color: rgba(16, 185, 129, 0.5);
}

.filter-toggle-btn svg {
  width: 18px;
  height: 18px;
}

.advanced-filter-options {
  background: rgba(255, 255, 255, 0.06);
  border: 1px solid rgba(255, 255, 255, 0.12);
  border-radius: 16px;
  padding: 24px;
  margin-bottom: 20px;
  backdrop-filter: blur(10px);
}

.filter-group {
  margin-bottom: 24px;
}

.filter-group:last-child {
  margin-bottom: 0;
}

.filter-group label {
  display: block;
  font-size: 14px;
  font-weight: 600;
  color: white;
  margin-bottom: 12px;
}

.price-range {
  display: flex;
  align-items: center;
  gap: 12px;
}

.price-input {
  flex: 1;
  padding: 10px 16px;
  background: rgba(255, 255, 255, 0.08);
  border: 1px solid rgba(255, 255, 255, 0.15);
  border-radius: 10px;
  color: white;
  font-size: 14px;
  outline: none;
  transition: all 0.3s ease;
}

.price-input:focus {
  border-color: rgba(16, 185, 129, 0.5);
  background: rgba(255, 255, 255, 0.12);
}

.price-input::placeholder {
  color: rgba(255, 255, 255, 0.4);
}

.price-range span {
  color: rgba(255, 255, 255, 0.6);
  font-size: 16px;
}

.error-message {
  color: #ef4444;
  font-size: 12px;
  margin-top: 8px;
  margin-left: 4px;
}

.condition-options,
.sort-options {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.condition-btn,
.sort-btn {
  padding: 8px 16px;
  background: rgba(255, 255, 255, 0.08);
  border: 1px solid rgba(255, 255, 255, 0.15);
  border-radius: 8px;
  color: rgba(255, 255, 255, 0.7);
  font-size: 13px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.condition-btn:hover,
.sort-btn:hover {
  background: rgba(255, 255, 255, 0.12);
  color: white;
}

.condition-btn.active,
.sort-btn.active {
  background: linear-gradient(135deg, #10b981, #059669);
  border-color: transparent;
  color: white;
}

.filter-actions {
  display: flex;
  gap: 12px;
  margin-top: 24px;
  padding-top: 20px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
}

.apply-filter-btn,
.reset-filter-btn {
  flex: 1;
  padding: 12px 24px;
  border: none;
  border-radius: 10px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
}

.apply-filter-btn {
  background: linear-gradient(135deg, #10b981, #059669);
  color: white;
}

.apply-filter-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(16, 185, 129, 0.3);
}

.reset-filter-btn {
  background: rgba(255, 255, 255, 0.1);
  color: rgba(255, 255, 255, 0.8);
  border: 1px solid rgba(255, 255, 255, 0.15);
}

.reset-filter-btn:hover {
  background: rgba(255, 255, 255, 0.15);
  color: white;
}

.filter-section {
  margin-bottom: 30px;
}

.filter-tabs {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.filter-tab {
  padding: 10px 20px;
  background: rgba(255, 255, 255, 0.08);
  border: 1px solid rgba(255, 255, 255, 0.15);
  border-radius: 10px;
  color: rgba(255, 255, 255, 0.7);
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.filter-tab:hover {
  background: rgba(255, 255, 255, 0.15);
  color: white;
}

.filter-tab.active {
  background: linear-gradient(135deg, #10b981, #059669);
  border-color: transparent;
  color: white;
}

.products-section {
  min-height: 400px;
}

.loading-container,
.error-container,
.empty-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 80px 20px;
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

@keyframes spin {
  to { transform: rotate(360deg); }
}

.error-container svg,
.empty-container svg {
  width: 64px;
  height: 64px;
  margin-bottom: 20px;
  opacity: 0.5;
}

.retry-btn,
.post-btn {
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

.retry-btn:hover,
.post-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(16, 185, 129, 0.3);
}

.empty-actions {
  display: flex;
  gap: 12px;
  margin-top: 20px;
}

.clear-filters-btn {
  padding: 12px 32px;
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 12px;
  color: rgba(255, 255, 255, 0.8);
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
}

.clear-filters-btn:hover {
  background: rgba(255, 255, 255, 0.15);
  border-color: rgba(255, 255, 255, 0.3);
  color: white;
  transform: translateY(-2px);
}

.products-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 24px;
}

.product-card {
  background: rgba(255, 255, 255, 0.08);
  border: 1px solid rgba(255, 255, 255, 0.12);
  border-radius: 20px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s ease;
}

.product-card:hover {
  transform: translateY(-8px);
  border-color: rgba(16, 185, 129, 0.4);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.3);
}

.product-image {
  position: relative;
  height: 200px;
  background: rgba(0, 0, 0, 0.3);
}

.product-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.image-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.image-placeholder svg {
  width: 64px;
  height: 64px;
  color: rgba(255, 255, 255, 0.3);
}

.product-status {
  position: absolute;
  top: 12px;
  right: 12px;
  padding: 6px 14px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
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

.product-info {
  padding: 20px;
}

.product-title {
  font-size: 17px;
  font-weight: 600;
  color: white;
  margin-bottom: 8px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.product-desc {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.5);
  margin-bottom: 12px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.product-meta {
  display: flex;
  gap: 10px;
  margin-bottom: 16px;
}

.product-category,
.product-condition {
  padding: 4px 10px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 6px;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.7);
}

.product-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.product-price {
  display: flex;
  align-items: baseline;
  gap: 2px;
}

.price-symbol {
  font-size: 18px;
  font-weight: 700;
  color: #10b981;
}

.price-value {
  font-size: 24px;
  font-weight: 700;
  color: #10b981;
}

.product-views {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.4);
}

.main-footer {
  position: relative;
  z-index: 1;
  padding: 30px 24px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  margin-top: 60px;
}

.footer-content {
  max-width: 1400px;
  margin: 0 auto;
  text-align: center;
}

.footer-content p {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.5);
}
</style>
