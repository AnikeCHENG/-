<template>
  <div class="drafts-page">
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
              <linearGradient id="logoGradDraft" x1="0%" y1="0%" x2="100%" y2="100%">
                <stop offset="0%" style="stop-color:#10b981"/>
                <stop offset="100%" style="stop-color:#3b82f6"/>
              </linearGradient>
            </defs>
            <circle cx="20" cy="20" r="18" fill="url(#logoGradDraft)"/>
            <path d="M13 20 L18 24 L27 16" stroke="white" stroke-width="3" fill="none" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          <span class="brand-name">废旧物品再利用平台</span>
        </div>
        
        <nav class="main-nav">
          <router-link to="/" class="nav-link">首页</router-link>
          <router-link to="/products" class="nav-link">商品</router-link>
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
          <div class="user-avatar">
            <img src="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 40 40'%3E%3Ccircle cx='20' cy='20' r='20' fill='%23333'/%3E%3Ccircle cx='20' cy='16' r='6' fill='%23666'/%3E%3Cpath d='M8 36c0-6.6 5.4-12 12-12s12 5.4 12 12' fill='%23666'/%3E%3C/svg%3E" alt="用户头像" />
          </div>
        </div>
      </div>
    </header>

    <main class="main-content">
      <div class="drafts-container">
        <div class="page-header">
          <h1 class="page-title">草稿箱</h1>
          <p class="page-subtitle">管理您保存的商品草稿</p>
        </div>

        <div v-if="drafts.length === 0" class="empty-state">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
            <path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"/>
            <polyline points="17 21 17 13 7 13 7 21"/>
            <polyline points="7 3 7 8 15 8"/>
          </svg>
          <h2>暂无草稿</h2>
          <p>您还没有保存任何商品草稿</p>
          <button class="btn-primary" @click="go('/post')">去发布商品</button>
        </div>

        <div v-else class="drafts-grid">
          <div v-for="(draft, index) in drafts" :key="index" class="draft-card">
            <div class="draft-images" v-if="draft.images && draft.images.length > 0">
              <img :src="draft.images[0]" :alt="draft.title" />
              <span class="image-count" v-if="draft.images.length > 1">{{ draft.images.length }}</span>
            </div>
            <div class="draft-images placeholder" v-else>
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
                <rect x="3" y="3" width="18" height="18" rx="2" ry="2"/>
                <circle cx="8.5" cy="8.5" r="1.5"/>
                <polyline points="21 15 16 10 5 21"/>
              </svg>
            </div>
            <div class="draft-info">
              <h3 class="draft-title">{{ draft.title || '未命名商品' }}</h3>
              <div class="draft-meta">
                <span class="draft-category">{{ getCategoryName(draft.category) }}</span>
                <span class="draft-condition">{{ draft.condition }}</span>
              </div>
              <p class="draft-price" v-if="draft.price">¥{{ draft.price }}</p>
              <p class="draft-date">保存于 {{ formatDate(draft.savedAt) }}</p>
            </div>
            <div class="draft-actions">
              <button class="btn-edit" @click="editDraft(draft)">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/>
                  <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/>
                </svg>
                编辑
              </button>
              <button class="btn-delete" @click="deleteDraft(index)">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <polyline points="3 6 5 6 21 6"/>
                  <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"/>
                </svg>
                删除
              </button>
            </div>
          </div>
        </div>

        <div v-if="drafts.length > 0" class="clear-all">
          <button class="btn-secondary" @click="clearAllDrafts">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <polyline points="3 6 5 6 21 6"/>
              <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"/>
            </svg>
            清空所有草稿
          </button>
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
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const drafts = ref([])

const categoryNames = {
  books: '📚 教材书籍',
  electronics: '📱 电子产品',
  transport: '🚴 出行工具',
  gaming: '🎮 游戏数码',
  clothing: '👕 服饰穿搭',
  living: '🏠 生活用品',
  other: '📦 其他'
}

function go(path) {
  router.push(path)
}

function getCategoryName(category) {
  return categoryNames[category] || '📦 其他'
}

function formatDate(timestamp) {
  if (!timestamp) return '未知时间'
  const date = new Date(timestamp)
  return `${date.getMonth() + 1}月${date.getDate()}日 ${date.getHours()}:${String(date.getMinutes()).padStart(2, '0')}`
}

function editDraft(draft) {
  localStorage.setItem('editing_draft', JSON.stringify(draft))
  router.push('/post')
}

function deleteDraft(index) {
  if (confirm('确定要删除这个草稿吗？')) {
    const savedDrafts = JSON.parse(localStorage.getItem('product_drafts') || '[]')
    savedDrafts.splice(index, 1)
    localStorage.setItem('product_drafts', JSON.stringify(savedDrafts))
    drafts.value = savedDrafts
  }
}

function clearAllDrafts() {
  if (confirm('确定要清空所有草稿吗？此操作不可恢复。')) {
    localStorage.removeItem('product_drafts')
    drafts.value = []
  }
}

async function handleLogout() {
  localStorage.removeItem('token')
  localStorage.removeItem('username')
  localStorage.removeItem('product_draft')
  localStorage.removeItem('editing_draft')
  sessionStorage.removeItem('justLoggedIn')
  router.push('/login')
}

onMounted(() => {
  drafts.value = JSON.parse(localStorage.getItem('product_drafts') || '[]')
})
</script>

<style scoped>
.drafts-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #0f0f23 0%, #1a1a3e 50%, #2d1b4e 100%);
  position: relative;
  overflow-x: hidden;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
}

.animated-bg {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: hidden;
  z-index: 0;
  pointer-events: none;
}

.gradient-sphere {
  position: absolute;
  border-radius: 50%;
  filter: blur(80px);
  opacity: 0.4;
}

.sphere-1 {
  width: 400px;
  height: 400px;
  background: linear-gradient(135deg, #10b981, #3b82f6);
  top: -100px;
  right: -100px;
  animation: float 8s ease-in-out infinite;
}

.sphere-2 {
  width: 300px;
  height: 300px;
  background: linear-gradient(135deg, #8b5cf6, #ec4899);
  bottom: -50px;
  left: -50px;
  animation: float 10s ease-in-out infinite reverse;
}

.sphere-3 {
  width: 200px;
  height: 200px;
  background: linear-gradient(135deg, #f59e0b, #ef4444);
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  animation: float 12s ease-in-out infinite;
}

@keyframes float {
  0%, 100% { transform: translate(0, 0); }
  50% { transform: translate(20px, -20px); }
}

.main-header {
  position: relative;
  z-index: 10;
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(20px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 1rem 2rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo-section {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  cursor: pointer;
  transition: transform 0.3s;
}

.logo-section:hover {
  transform: scale(1.02);
}

.header-logo {
  width: 40px;
  height: 40px;
}

.brand-name {
  font-size: 1.5rem;
  font-weight: 700;
  background: linear-gradient(135deg, #10b981, #3b82f6);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.main-nav {
  display: flex;
  gap: 2rem;
}

.nav-link {
  color: rgba(255, 255, 255, 0.7);
  text-decoration: none;
  font-weight: 500;
  padding: 0.5rem 0;
  position: relative;
  transition: color 0.3s;
}

.nav-link:hover,
.nav-link.active {
  color: #fff;
}

.nav-link.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 2px;
  background: linear-gradient(90deg, #10b981, #3b82f6);
  border-radius: 1px;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.action-btn {
  background: none;
  border: none;
  color: rgba(255, 255, 255, 0.7);
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 8px;
  transition: all 0.3s;
}

.action-btn:hover {
  background: rgba(255, 255, 255, 0.1);
  color: #fff;
}

.action-btn svg {
  width: 20px;
  height: 20px;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  overflow: hidden;
  border: 2px solid rgba(255, 255, 255, 0.2);
}

.user-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.main-content {
  position: relative;
  z-index: 10;
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.drafts-container {
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(20px);
  border-radius: 24px;
  border: 1px solid rgba(255, 255, 255, 0.1);
  padding: 2rem;
}

.page-header {
  text-align: center;
  margin-bottom: 2rem;
}

.page-title {
  font-size: 2rem;
  font-weight: 700;
  color: #fff;
  margin-bottom: 0.5rem;
}

.page-subtitle {
  color: rgba(255, 255, 255, 0.6);
}

.empty-state {
  text-align: center;
  padding: 4rem 2rem;
  color: rgba(255, 255, 255, 0.6);
}

.empty-state svg {
  width: 80px;
  height: 80px;
  margin-bottom: 1.5rem;
  opacity: 0.5;
}

.empty-state h2 {
  font-size: 1.5rem;
  color: #fff;
  margin-bottom: 0.5rem;
}

.empty-state p {
  margin-bottom: 2rem;
}

.drafts-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1.5rem;
}

.draft-card {
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 16px;
  overflow: hidden;
  transition: all 0.3s;
}

.draft-card:hover {
  transform: translateY(-4px);
  border-color: rgba(255, 255, 255, 0.2);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.3);
}

.draft-images {
  width: 100%;
  height: 180px;
  overflow: hidden;
  position: relative;
}

.draft-images img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.draft-images.placeholder {
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(255, 255, 255, 0.05);
}

.draft-images.placeholder svg {
  width: 60px;
  height: 60px;
  opacity: 0.3;
}

.image-count {
  position: absolute;
  bottom: 8px;
  right: 8px;
  background: rgba(0, 0, 0, 0.6);
  color: #fff;
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 0.75rem;
}

.draft-info {
  padding: 1rem;
}

.draft-title {
  font-size: 1rem;
  font-weight: 600;
  color: #fff;
  margin-bottom: 0.5rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.draft-meta {
  display: flex;
  gap: 0.5rem;
  margin-bottom: 0.5rem;
}

.draft-category,
.draft-condition {
  font-size: 0.75rem;
  padding: 4px 8px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 6px;
  color: rgba(255, 255, 255, 0.7);
}

.draft-price {
  font-size: 1.25rem;
  font-weight: 700;
  color: #10b981;
  margin-bottom: 0.5rem;
}

.draft-date {
  font-size: 0.75rem;
  color: rgba(255, 255, 255, 0.5);
}

.draft-actions {
  display: flex;
  gap: 0.5rem;
  padding: 1rem;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
}

.btn-edit,
.btn-delete {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  padding: 0.75rem;
  border: none;
  border-radius: 8px;
  font-size: 0.875rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s;
}

.btn-edit {
  background: linear-gradient(135deg, #10b981, #3b82f6);
  color: #fff;
}

.btn-edit:hover {
  opacity: 0.9;
  transform: scale(1.02);
}

.btn-delete {
  background: rgba(239, 68, 68, 0.2);
  color: #ef4444;
  border: 1px solid rgba(239, 68, 68, 0.3);
}

.btn-delete:hover {
  background: rgba(239, 68, 68, 0.3);
}

.btn-edit svg,
.btn-delete svg {
  width: 16px;
  height: 16px;
}

.clear-all {
  text-align: center;
  margin-top: 2rem;
}

.btn-secondary {
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.75rem 1.5rem;
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 8px;
  color: rgba(255, 255, 255, 0.7);
  font-size: 0.875rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s;
}

.btn-secondary:hover {
  background: rgba(255, 255, 255, 0.15);
  color: #fff;
}

.btn-secondary svg {
  width: 16px;
  height: 16px;
}

.btn-primary {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0.75rem 1.5rem;
  background: linear-gradient(135deg, #10b981, #3b82f6);
  border: none;
  border-radius: 8px;
  color: #fff;
  font-size: 0.875rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s;
}

.btn-primary:hover {
  opacity: 0.9;
  transform: scale(1.02);
}

.main-footer {
  position: relative;
  z-index: 10;
  padding: 2rem;
  text-align: center;
}

.footer-content {
  color: rgba(255, 255, 255, 0.4);
  font-size: 0.875rem;
}
</style>
