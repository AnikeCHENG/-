<template>
  <div class="home-page">
    <div class="animated-bg">
      <div class="gradient-sphere sphere-1"></div>
      <div class="gradient-sphere sphere-2"></div>
      <div class="gradient-sphere sphere-3"></div>
      <div class="gradient-sphere sphere-4"></div>
    </div>
    
    <div class="floating-decorations">
      <div class="float-item item-1">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M20.24 12.24a6 6 0 0 0-8.49-8.49L5 10.5V19h8.5z"/>
          <line x1="16" y1="8" x2="2" y2="22"/>
          <line x1="17.5" y1="15" x2="9" y2="15"/>
        </svg>
      </div>
      <div class="float-item item-2">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <circle cx="9" cy="21" r="1"/>
          <circle cx="20" cy="21" r="1"/>
          <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"/>
        </svg>
      </div>
      <div class="float-item item-3">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M12 2L2 7l10 5 10-5-10-5z"/>
          <path d="M2 17l10 5 10-5"/>
          <path d="M2 12l10 5 10-5"/>
        </svg>
      </div>
      <div class="float-item item-4">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/>
        </svg>
      </div>
    </div>

    <header class="main-header">
      <div class="header-content">
        <div class="logo-section">
          <svg class="header-logo" viewBox="0 0 40 40">
            <defs>
              <linearGradient id="headerLogoGrad" x1="0%" y1="0%" x2="100%" y2="100%">
                <stop offset="0%" style="stop-color:#10b981"/>
                <stop offset="100%" style="stop-color:#3b82f6"/>
              </linearGradient>
            </defs>
            <circle cx="20" cy="20" r="18" fill="url(#headerLogoGrad)"/>
            <path d="M13 20 L18 24 L27 16" stroke="white" stroke-width="3" fill="none" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          <span class="brand-name">废旧物品再利用平台</span>
        </div>
        
        <nav class="main-nav">
          <router-link to="/" class="nav-link active">首页</router-link>
          <router-link to="/products" class="nav-link">商品</router-link>
          <router-link to="/post" class="nav-link">发布</router-link>
          <router-link to="/messages" class="nav-link">消息</router-link>
          <router-link to="/profile" class="nav-link">我的</router-link>
          <router-link v-if="user.role === 1" to="/admin" class="nav-link">管理员</router-link>
        </nav>
        
        <div class="header-actions">
          <button class="action-btn search-btn" @click="toggleSearch">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <circle cx="11" cy="11" r="8"/>
              <line x1="21" y1="21" x2="16.65" y2="16.65"/>
            </svg>
          </button>
          
          <div v-if="showSearch" class="search-modal" @click.self="toggleSearch">
            <div class="search-modal-content">
              <div class="search-modal-header">
                <div class="search-modal-bar">
                  <svg class="search-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <circle cx="11" cy="11" r="8"/>
                    <line x1="21" y1="21" x2="16.65" y2="16.65"/>
                  </svg>
                  <input 
                    type="text" 
                    v-model="searchKeyword" 
                    placeholder="搜索商品名称、描述..."
                    @keyup.enter="handleSearch"
                    ref="searchInput"
                    autofocus
                  />
                  <button v-if="searchKeyword" class="clear-btn" @click="clearSearch">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                      <line x1="18" y1="6" x2="6" y2="18"/>
                      <line x1="6" y1="6" x2="18" y2="18"/>
                    </svg>
                  </button>
                  <button class="search-submit-btn" @click="handleSearch">
                    搜索
                  </button>
                </div>
                <button class="close-search-btn" @click="toggleSearch">
                  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <line x1="18" y1="6" x2="6" y2="18"/>
                    <line x1="6" y1="6" x2="18" y2="18"/>
                  </svg>
                </button>
              </div>
              
              <div class="search-modal-body">
                <div v-if="searchHistory.length > 0 && !searchKeyword" class="search-section">
                  <div class="search-section-header">
                    <span>搜索历史</span>
                    <button class="clear-history-btn" @click="clearSearchHistory">清空</button>
                  </div>
                  <div class="search-items">
                    <div 
                      v-for="(item, index) in searchHistory.slice(0, 8)" 
                      :key="`history-${index}`" 
                      class="search-item"
                      @click="selectSearchItem(item)"
                    >
                      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <circle cx="12" cy="12" r="10"/>
                        <polyline points="12 6 12 12 16 14"/>
                      </svg>
                      <span>{{ item }}</span>
                    </div>
                  </div>
                </div>
                
                <div v-if="searchSuggestions.length > 0" class="search-section">
                  <div class="search-section-header">
                    <span>搜索建议</span>
                  </div>
                  <div class="search-items">
                    <div 
                      v-for="(item, index) in searchSuggestions" 
                      :key="`suggestion-${index}`" 
                      class="search-item"
                      @click="selectSearchItem(item)"
                    >
                      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <circle cx="11" cy="11" r="8"/>
                        <line x1="21" y1="21" x2="16.65" y2="16.65"/>
                      </svg>
                      <span>{{ item }}</span>
                    </div>
                  </div>
                </div>
                
                <div v-if="!searchKeyword && searchHistory.length === 0" class="search-empty">
                  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <circle cx="11" cy="11" r="8"/>
                    <line x1="21" y1="21" x2="16.65" y2="16.65"/>
                  </svg>
                  <p>输入关键词搜索商品</p>
                </div>
              </div>
            </div>
          </div>
          <button class="action-btn notify-btn" @click="go('/messages')">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/>
              <path d="M13.73 21a2 2 0 0 1-3.46 0"/>
            </svg>
            <span v-if="notificationStore.unreadCount > 0" class="notify-badge">{{ notificationStore.unreadCount > 99 ? '99+' : notificationStore.unreadCount }}</span>
          </button>
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
      <div class="hero-section">
        <div class="hero-content">
          <h1 class="hero-title">
            <span class="title-line">废旧物品</span>
            <span class="title-highlight">再利用平台</span>
          </h1>
          <p class="hero-subtitle">环保从循环开始，让闲置物品找到新主人</p>
          <div class="hero-stats">
            <div class="stat-item">
              <div class="stat-value">2,580</div>
              <div class="stat-label">活跃用户</div>
            </div>
            <div class="stat-divider"></div>
            <div class="stat-item">
              <div class="stat-value">1,245</div>
              <div class="stat-label">在售商品</div>
            </div>
            <div class="stat-divider"></div>
            <div class="stat-item">
              <div class="stat-value">8,920</div>
              <div class="stat-label">成交订单</div>
            </div>
          </div>
          <div class="hero-actions">
            <button class="hero-btn primary" @click="go('/post')">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="12" y1="5" x2="12" y2="19"/>
                <line x1="5" y1="12" x2="19" y2="12"/>
              </svg>
              发布闲置
            </button>
            <button class="hero-btn secondary" @click="go('/browse')">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="11" cy="11" r="8"/>
                <line x1="21" y1="21" x2="16.65" y2="16.65"/>
              </svg>
              逛一逛
            </button>
          </div>
        </div>
        
        <div class="hero-illustration">
          <div class="illustration-card card-1">
            <div class="card-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M12 2L2 7l10 5 10-5-10-5z"/>
                <path d="M2 17l10 5 10-5"/>
                <path d="M2 12l10 5 10-5"/>
              </svg>
            </div>
            <div class="card-text">循环利用</div>
          </div>
          <div class="illustration-card card-2">
            <div class="card-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M20.24 12.24a6 6 0 0 0-8.49-8.49L5 10.5V19h8.5z"/>
                <line x1="16" y1="8" x2="2" y2="22"/>
              </svg>
            </div>
            <div class="card-text">环保生活</div>
          </div>
          <div class="illustration-card card-3">
            <div class="card-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="12" y1="1" x2="12" y2="23"/>
                <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/>
              </svg>
            </div>
            <div class="card-text">省钱实惠</div>
          </div>
        </div>
      </div>

      <div class="dashboard-row">
        <dashboard-card 
          v-for="(c, i) in cards" 
          :key="i" 
          :title="c.title" 
          :value="c.value" 
          :icon="c.icon" 
          :color="c.color" 
          :clickable="true"
          @click="handleCardClick(c)"
        />
      </div>

      <div class="content-grid">
        <div class="main-column">
          <div class="content-section">
            <div class="section-header">
              <div class="section-title-wrapper">
                <div class="section-icon">
                  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/>
                  </svg>
                </div>
                <h2 class="section-title">最新上架</h2>
              </div>
              <a href="#" class="view-all-link" @click.prevent="viewAllProducts">
                查看全部
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <line x1="5" y1="12" x2="19" y2="12"/>
                  <polyline points="12 5 19 12 12 19"/>
                </svg>
              </a>
            </div>
            <recent-items :items="recentItems" @view="viewProduct" />
          </div>

          <div class="content-section">
            <div class="section-header">
              <div class="section-title-wrapper">
                <div class="section-icon activity-icon">
                  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M22 12h-4l-3 9L9 3l-3 9H2"/>
                  </svg>
                </div>
                <h2 class="section-title">活动与公告</h2>
              </div>
            </div>
            <div class="activities-list">
              <div 
                v-for="(a, idx) in activities" 
                :key="idx" 
                class="activity-item"
                @click="showActivityDetail(a)"
              >
                <div class="activity-date">
                  <div class="date-day">{{ getDay(a.time) }}</div>
                  <div class="date-month">{{ getMonth(a.time) }}</div>
                </div>
                <div class="activity-content">
                  <h4 class="activity-title">{{ a.title }}</h4>
                  <p class="activity-desc">{{ a.desc }}</p>
                </div>
                <div class="activity-badge" :class="idx === 0 ? 'new' : ''">
                  {{ idx === 0 ? '热门' : '公告' }}
                </div>
              </div>
            </div>
          </div>
        </div>

        <aside class="side-column">
          <div class="side-card recommendations-card">
            <div class="side-card-header">
              <div class="side-card-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/>
                </svg>
              </div>
              <h3 class="side-card-title">为你推荐</h3>
            </div>
            <recommendations :items="recommendations" @click="handleRecommendationClick" />
          </div>

          <div class="side-card quick-actions-card">
            <div class="side-card-header">
              <div class="side-card-icon action-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <polygon points="13 2 3 14 12 14 11 22 21 10 12 10 13 2"/>
                </svg>
              </div>
              <h3 class="side-card-title">快速操作</h3>
            </div>
            <div class="quick-buttons">
              <button class="quick-btn" @click="go('/post')">
                <div class="btn-icon publish">
                  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <line x1="12" y1="5" x2="12" y2="19"/>
                    <line x1="5" y1="12" x2="19" y2="12"/>
                  </svg>
                </div>
                <span>发布闲置</span>
              </button>
              <button class="quick-btn" @click="go('/messages')">
                <div class="btn-icon message">
                  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/>
                  </svg>
                </div>
                <span>我的消息</span>
              </button>
              <button class="quick-btn" @click="go('/profile')">
                <div class="btn-icon favorite">
                  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/>
                  </svg>
                </div>
                <span>我的收藏</span>
              </button>
              <button class="quick-btn" @click="go('/profile')">
                <div class="btn-icon order">
                  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
                    <polyline points="14 2 14 8 20 8"/>
                    <line x1="16" y1="13" x2="8" y2="13"/>
                    <line x1="16" y1="17" x2="8" y2="17"/>
                    <polyline points="10 9 9 9 8 9"/>
                  </svg>
                </div>
                <span>我的订单</span>
              </button>
            </div>
          </div>

          <div class="side-card tags-card">
            <div class="side-card-header">
              <div class="side-card-icon tags-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M20.59 13.41l-7.17 7.17a2 2 0 0 1-2.83 0L2 12V2h10l8.59 8.59a2 2 0 0 1 0 2.82z"/>
                  <line x1="7" y1="7" x2="7.01" y2="7"/>
                </svg>
              </div>
              <h3 class="side-card-title">热门分类</h3>
            </div>
            <div class="tags-list">
              <a href="#" class="tag-item">📚 教材书籍</a>
              <a href="#" class="tag-item">📱 电子产品</a>
              <a href="#" class="tag-item">🚴 出行工具</a>
              <a href="#" class="tag-item">🎮 游戏数码</a>
              <a href="#" class="tag-item">👕 服饰穿搭</a>
              <a href="#" class="tag-item">🏠 生活用品</a>
            </div>
          </div>
        </aside>
      </div>
    </main>

    <footer class="main-footer">
      <div class="footer-content">
        <div class="footer-section">
          <div class="footer-logo">
            <svg viewBox="0 0 40 40">
              <circle cx="20" cy="20" r="18" fill="rgba(255,255,255,0.2)"/>
              <path d="M13 20 L18 24 L27 16" stroke="white" stroke-width="3" fill="none" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            <span>废旧物品再利用平台</span>
          </div>
          <p class="footer-desc">废旧物品再利用平台，让闲置物品重新发挥价值</p>
        </div>
        <div class="footer-section">
          <h4>快速链接</h4>
          <div class="footer-links">
            <a href="#">首页</a>
            <a href="#">商品列表</a>
            <a href="#">发布商品</a>
            <a href="#">关于我们</a>
          </div>
        </div>
        <div class="footer-section">
          <h4>帮助中心</h4>
          <div class="footer-links">
            <a href="#">常见问题</a>
            <a href="#">交易指南</a>
            <a href="#">联系我们</a>
            <a href="#">隐私政策</a>
          </div>
        </div>
        <div class="footer-section">
          <h4>关注我们</h4>
          <div class="social-links">
            <a href="#" class="social-link">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M23 3a10.9 10.9 0 0 1-3.14 1.53 4.48 4.48 0 0 0-7.86 3v1A10.66 10.66 0 0 1 3 4s-4 9 5 13a11.64 11.64 0 0 1-7 2c9 5 20 0 20-11.5a4.5 4.5 0 0 0-.08-.83A7.72 7.72 0 0 0 23 3z"/>
              </svg>
            </a>
            <a href="#" class="social-link">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <rect x="2" y="2" width="20" height="20" rx="5" ry="5"/>
                <path d="M16 11.37A4 4 0 1 1 12.63 8 4 4 0 0 1 16 11.37z"/>
                <line x1="17.5" y1="6.5" x2="17.51" y2="6.5"/>
              </svg>
            </a>
            <a href="#" class="social-link">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"/>
                <polyline points="22,6 12,13 2,6"/>
              </svg>
            </a>
          </div>
        </div>
      </div>
      <div class="footer-bottom">
        <p>© 2025 废旧物品再利用平台</p>
      </div>
    </footer>
    
    <div v-if="showActivityModal" class="activity-modal" @click.self="closeActivityModal">
      <div class="activity-modal-content">
        <div class="activity-modal-header">
          <div class="activity-modal-badge" :class="currentActivity.isNew ? 'new' : ''">
            {{ currentActivity.isNew ? '热门' : '公告' }}
          </div>
          <button class="activity-modal-close" @click="closeActivityModal">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <line x1="18" y1="6" x2="6" y2="18"/>
              <line x1="6" y1="6" x2="18" y2="18"/>
            </svg>
          </button>
        </div>
        
        <div class="activity-modal-body">
          <div class="activity-modal-date">
            <div class="activity-modal-day">{{ getDay(currentActivity.time) }}</div>
            <div class="activity-modal-month">{{ getMonth(currentActivity.time) }}</div>
          </div>
          
          <h2 class="activity-modal-title">{{ currentActivity.title }}</h2>
          
          <div class="activity-modal-desc">
            {{ currentActivity.fullDesc || currentActivity.desc }}
          </div>
          
          <div v-if="currentActivity.details" class="activity-modal-details">
            <div v-for="(detail, idx) in currentActivity.details" :key="idx" class="activity-detail-item">
              <div class="detail-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <circle cx="12" cy="12" r="10"/>
                  <polyline points="12 6 12 12 16 14"/>
                </svg>
              </div>
              <div class="detail-content">{{ detail }}</div>
            </div>
          </div>
        </div>
        
        <div class="activity-modal-footer">
          <button class="activity-modal-btn" @click="closeActivityModal">
            关闭
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import DashboardCard from '../components/DashboardCard.vue'
import RecentItems from '../components/RecentItems.vue'
import Recommendations from '../components/Recommendations.vue'
import { ref, computed, onMounted, onUnmounted, nextTick } from 'vue'
import { logout } from '../api/auth'
import { getProductList } from '../api/product'
import { useNotificationStore } from '../stores/notification'

const router = useRouter()
const notificationStore = useNotificationStore()

const showSearch = ref(false)
const searchKeyword = ref('')
const searchHistory = ref([])
const allProducts = ref([])
const searchInput = ref(null)
const showActivityModal = ref(false)
const currentActivity = ref({})
const user = ref(JSON.parse(localStorage.getItem('user') || sessionStorage.getItem('user') || '{"role": 0}'))

function go(path) { router.push(path) }

function handleCardClick(card) {
  switch (card.icon) {
    case 'shopping':
      router.push('/products')
      break
    case 'order':
      router.push('/profile')
      break
    case 'message':
      router.push('/messages')
      break
    case 'user':
      router.push('/profile')
      break
  }
}

function viewProduct(item) {
  if (item.id) {
    router.push(`/products/${item.id}`)
  } else {
    router.push('/products')
  }
}

function viewAllProducts() {
  router.push('/products')
}

function showActivityDetail(activity) {
  currentActivity.value = activity
  showActivityModal.value = true
}

function closeActivityModal() {
  showActivityModal.value = false
  currentActivity.value = {}
}

function handleRecommendationClick(recommendation) {
  router.push({
    path: '/products',
    query: { category: recommendation.category }
  })
}

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

async function toggleSearch() {
  showSearch.value = !showSearch.value
  if (showSearch.value) {
    await nextTick()
    if (searchInput.value) {
      searchInput.value.focus()
    }
    await loadProducts()
  }
}

function clearSearch() {
  searchKeyword.value = ''
  if (searchInput.value) {
    searchInput.value.focus()
  }
}

function selectSearchItem(item) {
  searchKeyword.value = item
  handleSearch()
}

function handleSearch() {
  if (searchKeyword.value.trim()) {
    saveSearchHistory(searchKeyword.value)
    showSearch.value = false
    router.push({
      path: '/products',
      query: { search: searchKeyword.value }
    })
  }
}

function saveSearchHistory(keyword) {
  if (!keyword.trim()) return
  
  const index = searchHistory.value.indexOf(keyword)
  if (index > -1) {
    searchHistory.value.splice(index, 1)
  }
  
  searchHistory.value.unshift(keyword)
  searchHistory.value = searchHistory.value.slice(0, 10)
  
  localStorage.setItem('homeSearchHistory', JSON.stringify(searchHistory.value))
}

function clearSearchHistory() {
  searchHistory.value = []
  localStorage.removeItem('homeSearchHistory')
}

function loadSearchHistory() {
  const saved = localStorage.getItem('homeSearchHistory')
  if (saved) {
    try {
      searchHistory.value = JSON.parse(saved)
    } catch (e) {
      console.error('Failed to parse search history:', e)
    }
  }
}

async function loadProducts() {
  try {
    const res = await getProductList()
    if (res.code === 200 && res.data) {
      allProducts.value = res.data
      // 更新最新上架商品
      recentItems.value = res.data.slice(0, 3).map(item => ({
        id: item.id,
        title: item.title,
        price: `¥${item.price}`,
        img: item.images || ''
      }))
    }
  } catch (e) {
    console.error('Failed to load products:', e)
  }
}

async function handleLogout() {
  try {
    await logout()
  } catch (e) {
    console.log('Logout API call completed')
  }
  localStorage.removeItem('token')
  localStorage.removeItem('username')
  sessionStorage.removeItem('justLoggedIn')
  router.push('/login')
}

const cards = ref([
  { title: '在售商品', value: 128, icon: 'shopping', color: '#ff7a45' },
  { title: '待处理订单', value: 6, icon: 'order', color: '#36b37e' },
  { title: '私信', value: 14, icon: 'message', color: '#5b8ff9' },
  { title: '活跃用户', value: 2040, icon: 'user', color: '#9b6cff' }
])

const recentItems = ref([])

const recommendations = ref([
  { title: '环保小物合集', desc: '省钱又环保的好选择', category: 'living' },
  { title: '闲置电子产品专场', desc: '高价回收/低价购入', category: 'electronics' }
])

const activities = ref([
  { 
    time: '2026-01-01', 
    title: '新年活动：二手换购', 
    desc: '参与换购赢取积分',
    isNew: true,
    fullDesc: '为庆祝新年的到来，平台特别推出二手换购活动！所有用户均可参与，用你的闲置物品换取你需要的物品，还能额外获得平台积分奖励！',
    details: [
      '活动时间：2026年1月1日 - 2026年1月15日',
      '参与方式：发布闲置物品即可获得参与资格',
      '积分奖励：每成功交易一次可获得100积分',
      '积分用途：可用于抵扣平台服务费'
    ]
  },
  { 
    time: '2026-01-03', 
    title: '平台维护公告', 
    desc: '将于周末进行短暂维护',
    isNew: false,
    fullDesc: '为了给用户提供更好的服务体验，平台将于本周末进行系统升级维护。维护期间部分功能可能无法正常使用，请用户提前做好安排。',
    details: [
      '维护时间：2026年1月4日 22:00 - 2026年1月5日 06:00',
      '影响范围：商品发布、消息发送功能',
      '备用方案：维护期间可浏览商品，无法进行交易',
      '补偿措施：维护后所有用户将获得50积分补偿'
    ]
  },
  { 
    time: '2025-12-28', 
    title: '冬季保暖用品', 
    desc: '棉被棉衣低价出售',
    isNew: false,
    fullDesc: '天气转凉，平台特别推出冬季保暖用品专区！各类棉被、棉衣、暖手宝等商品低价出售，让你温暖过冬！',
    details: [
      '专区商品：棉被、羽绒服、毛衣、暖手宝等',
      '优惠活动：专区商品满100减20',
      '品质保证：所有商品均经过平台审核',
      '售后保障：7天无理由退换'
    ]
  }
])

onMounted(() => {
  notificationStore.startPolling(15000)
  loadSearchHistory()
  loadProducts()
})

onUnmounted(() => {
  notificationStore.stopPolling()
})

function getDay(time) {
  return time.split('-')[2]
}

function getMonth(time) {
  const months = ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
  return months[parseInt(time.split('-')[1]) - 1]
}
</script>

<style scoped>
.home-page {
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
  top: 30%;
  left: 30%;
  animation-delay: -16s;
}

.sphere-4 {
  width: 400px;
  height: 400px;
  background: linear-gradient(135deg, #36b37e, #10b981);
  bottom: 10%;
  right: 10%;
  animation-delay: -4s;
}

@keyframes floatBg {
  0%, 100% { transform: translate(0, 0) scale(1); }
  25% { transform: translate(50px, -50px) scale(1.05); }
  50% { transform: translate(-30px, 30px) scale(0.95); }
  75% { transform: translate(30px, 50px) scale(1.02); }
}

.floating-decorations {
  position: fixed;
  inset: 0;
  pointer-events: none;
  z-index: 1;
}

.float-item {
  position: absolute;
  opacity: 0.1;
  animation: floatItem 20s ease-in-out infinite;
}

.float-item svg {
  width: 48px;
  height: 48px;
  stroke: white;
}

.item-1 { top: 20%; left: 5%; animation-delay: 0s; }
.item-2 { top: 40%; right: 8%; animation-delay: -5s; }
.item-3 { bottom: 30%; left: 10%; animation-delay: -10s; }
.item-4 { bottom: 15%; right: 15%; animation-delay: -15s; }

@keyframes floatItem {
  0%, 100% { transform: translateY(0) rotate(0deg); }
  50% { transform: translateY(-30px) rotate(10deg); }
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
}

.header-logo {
  width: 40px;
  height: 40px;
}

.brand-name {
  font-size: 22px;
  font-weight: 700;
  color: #ffffff;
  letter-spacing: 1px;
}

.main-nav {
  display: flex;
  gap: 8px;
}

.nav-link, .router-link {
  padding: 10px 20px;
  color: rgba(255, 255, 255, 0.7);
  text-decoration: none;
  font-size: 15px;
  font-weight: 500;
  border-radius: 10px;
  transition: all 0.3s ease;
  cursor: pointer;
  display: inline-block;
}

.nav-link:hover, .router-link:hover {
  color: #ffffff;
  background: rgba(255, 255, 255, 0.1);
}

.nav-link.active, .router-link.active {
  color: #10b981;
  background: rgba(16, 185, 129, 0.15);
}

.router-link-exact-active {
  color: #10b981;
  background: rgba(16, 185, 129, 0.15);
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 16px;
}

.action-btn {
  width: 42px;
  height: 42px;
  border: none;
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.1);
  color: rgba(255, 255, 255, 0.8);
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
  position: relative;
}

.action-btn:hover {
  background: rgba(255, 255, 255, 0.15);
  color: #ffffff;
}

.logout-btn:hover {
  background: rgba(239, 68, 68, 0.3);
  color: #ef4444;
}

.action-btn svg {
  width: 20px;
  height: 20px;
}

.notify-badge {
  position: absolute;
  top: -4px;
  right: -4px;
  width: 18px;
  height: 18px;
  background: linear-gradient(135deg, #ef4444, #f59e0b);
  border-radius: 50%;
  font-size: 10px;
  font-weight: 600;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
}

.user-avatar {
  width: 42px;
  height: 42px;
  border-radius: 12px;
  overflow: hidden;
  border: 2px solid rgba(255, 255, 255, 0.2);
  cursor: pointer;
  transition: all 0.3s ease;
}

.user-avatar:hover {
  border-color: #10b981;
  transform: scale(1.05);
}

.user-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.search-modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  backdrop-filter: blur(8px);
  z-index: 1000;
  display: flex;
  align-items: flex-start;
  justify-content: center;
  padding-top: 100px;
  animation: fadeIn 0.2s ease;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

.search-modal-content {
  width: 100%;
  max-width: 700px;
  background: linear-gradient(135deg, rgba(20, 20, 40, 0.95), rgba(30, 30, 60, 0.95));
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.15);
  border-radius: 24px;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
  overflow: hidden;
  animation: slideDown 0.3s ease;
}

@keyframes slideDown {
  from { 
    opacity: 0;
    transform: translateY(-20px);
  }
  to { 
    opacity: 1;
    transform: translateY(0);
  }
}

.search-modal-header {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.search-modal-bar {
  flex: 1;
  display: flex;
  align-items: center;
  background: rgba(255, 255, 255, 0.08);
  border: 1px solid rgba(255, 255, 255, 0.15);
  border-radius: 14px;
  padding: 8px 8px 8px 16px;
  transition: all 0.3s ease;
}

.search-modal-bar:focus-within {
  border-color: rgba(16, 185, 129, 0.5);
  background: rgba(255, 255, 255, 0.12);
}

.search-modal-bar .search-icon {
  width: 20px;
  height: 20px;
  color: rgba(255, 255, 255, 0.5);
  margin-right: 12px;
  flex-shrink: 0;
}

.search-modal-bar input {
  flex: 1;
  background: transparent;
  border: none;
  outline: none;
  font-size: 16px;
  color: white;
  padding: 8px 0;
}

.search-modal-bar input::placeholder {
  color: rgba(255, 255, 255, 0.4);
}

.search-modal-bar .clear-btn {
  width: 32px;
  height: 32px;
  background: rgba(255, 255, 255, 0.1);
  border: none;
  border-radius: 8px;
  color: rgba(255, 255, 255, 0.6);
  cursor: pointer;
  margin-right: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
}

.search-modal-bar .clear-btn:hover {
  background: rgba(255, 255, 255, 0.15);
  color: white;
}

.search-modal-bar .clear-btn svg {
  width: 16px;
  height: 16px;
}

.search-submit-btn {
  padding: 10px 20px;
  background: linear-gradient(135deg, #10b981, #059669);
  border: none;
  border-radius: 10px;
  color: white;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
}

.search-submit-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 6px 16px rgba(16, 185, 129, 0.3);
}

.close-search-btn {
  width: 40px;
  height: 40px;
  background: rgba(255, 255, 255, 0.1);
  border: none;
  border-radius: 10px;
  color: rgba(255, 255, 255, 0.7);
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
}

.close-search-btn:hover {
  background: rgba(239, 68, 68, 0.2);
  color: #ef4444;
}

.close-search-btn svg {
  width: 20px;
  height: 20px;
}

.search-modal-body {
  padding: 24px;
  max-height: 500px;
  overflow-y: auto;
}

.search-section {
  margin-bottom: 24px;
}

.search-section:last-child {
  margin-bottom: 0;
}

.search-section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.search-section-header span {
  font-size: 13px;
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

.search-items {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.search-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 16px;
  border-radius: 12px;
  color: rgba(255, 255, 255, 0.8);
  cursor: pointer;
  transition: all 0.2s ease;
}

.search-item:hover {
  background: rgba(255, 255, 255, 0.08);
  color: white;
}

.search-item svg {
  width: 18px;
  height: 18px;
  flex-shrink: 0;
  opacity: 0.5;
}

.search-item span {
  flex: 1;
  font-size: 14px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.search-empty {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px 20px;
  color: rgba(255, 255, 255, 0.5);
}

.search-empty svg {
  width: 48px;
  height: 48px;
  margin-bottom: 16px;
  opacity: 0.3;
}

.search-empty p {
  font-size: 14px;
  margin: 0;
}

.main-content {
  padding-top: 70px;
  position: relative;
  z-index: 10;
}

.hero-section {
  max-width: 1400px;
  margin: 0 auto;
  padding: 60px 24px 80px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 60px;
}

.hero-content {
  flex: 1;
}

.hero-title {
  font-size: 56px;
  font-weight: 800;
  line-height: 1.2;
  margin-bottom: 20px;
}

.title-line {
  color: #ffffff;
  display: block;
}

.title-highlight {
  background: linear-gradient(135deg, #10b981, #3b82f6);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.hero-subtitle {
  font-size: 18px;
  color: rgba(255, 255, 255, 0.6);
  margin-bottom: 40px;
}

.hero-stats {
  display: flex;
  align-items: center;
  gap: 32px;
  margin-bottom: 40px;
}

.stat-item {
  text-align: center;
}

.stat-value {
  font-size: 36px;
  font-weight: 700;
  color: #ffffff;
  margin-bottom: 4px;
}

.stat-label {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.5);
}

.stat-divider {
  width: 1px;
  height: 50px;
  background: rgba(255, 255, 255, 0.2);
}

.hero-actions {
  display: flex;
  gap: 16px;
}

.hero-btn {
  height: 52px;
  padding: 0 28px;
  border: none;
  border-radius: 12px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 10px;
  transition: all 0.3s ease;
}

.hero-btn svg {
  width: 20px;
  height: 20px;
}

.hero-btn.primary {
  background: linear-gradient(135deg, #10b981, #059669);
  color: white;
}

.hero-btn.primary:hover {
  transform: translateY(-3px);
  box-shadow: 0 15px 40px -10px rgba(16, 185, 129, 0.5);
}

.hero-btn.secondary {
  background: rgba(255, 255, 255, 0.1);
  color: white;
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.hero-btn.secondary:hover {
  background: rgba(255, 255, 255, 0.15);
  border-color: rgba(255, 255, 255, 0.3);
}

.hero-illustration {
  position: relative;
  width: 400px;
  height: 350px;
}

.illustration-card {
  position: absolute;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(20px);
  border-radius: 20px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 12px;
  border: 1px solid rgba(255, 255, 255, 0.15);
  animation: floatCard 6s ease-in-out infinite;
}

.illustration-card .card-icon {
  width: 44px;
  height: 44px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.illustration-card .card-icon svg {
  width: 24px;
  height: 24px;
  stroke: white;
}

.illustration-card .card-text {
  font-size: 14px;
  font-weight: 600;
  color: white;
}

.card-1 {
  top: 0;
  left: 0;
  animation-delay: 0s;
}

.card-1 .card-icon {
  background: linear-gradient(135deg, #10b981, #059669);
}

.card-2 {
  top: 80px;
  right: 0;
  animation-delay: -2s;
}

.card-2 .card-icon {
  background: linear-gradient(135deg, #3b82f6, #1d4ed8);
}

.card-3 {
  bottom: 40px;
  left: 50px;
  animation-delay: -4s;
}

.card-3 .card-icon {
  background: linear-gradient(135deg, #f59e0b, #d97706);
}

@keyframes floatCard {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-15px); }
}

.dashboard-row {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 24px;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 40px;
}

.content-grid {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 24px 60px;
  display: grid;
  grid-template-columns: 1fr 360px;
  gap: 24px;
}

.content-section {
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(20px);
  border-radius: 24px;
  padding: 28px;
  border: 1px solid rgba(255, 255, 255, 0.1);
  margin-bottom: 24px;
}

.section-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 24px;
}

.section-title-wrapper {
  display: flex;
  align-items: center;
  gap: 12px;
}

.section-icon {
  width: 44px;
  height: 44px;
  background: linear-gradient(135deg, #10b981, #059669);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.section-icon svg {
  width: 22px;
  height: 22px;
  stroke: white;
}

.activity-icon {
  background: linear-gradient(135deg, #8b5cf6, #6d28d9);
}

.section-title {
  font-size: 20px;
  font-weight: 700;
  color: #ffffff;
  margin: 0;
}

.view-all-link {
  display: flex;
  align-items: center;
  gap: 6px;
  color: rgba(255, 255, 255, 0.6);
  text-decoration: none;
  font-size: 14px;
  transition: all 0.3s ease;
}

.view-all-link:hover {
  color: #10b981;
}

.view-all-link svg {
  width: 16px;
  height: 16px;
}

.activities-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.activity-item {
  display: flex;
  align-items: flex-start;
  gap: 16px;
  padding: 16px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 16px;
  transition: all 0.3s ease;
}

.activity-item:hover {
  background: rgba(255, 255, 255, 0.08);
  transform: translateX(5px);
}

.activity-date {
  width: 60px;
  text-align: center;
  padding: 10px;
  background: linear-gradient(135deg, rgba(16, 185, 129, 0.2), rgba(59, 130, 246, 0.2));
  border-radius: 12px;
}

.date-day {
  font-size: 24px;
  font-weight: 700;
  color: #ffffff;
  line-height: 1;
}

.date-month {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
  margin-top: 4px;
}

.activity-content {
  flex: 1;
}

.activity-title {
  font-size: 16px;
  font-weight: 600;
  color: #ffffff;
  margin: 0 0 6px 0;
}

.activity-desc {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.5);
  margin: 0;
}

.activity-badge {
  padding: 6px 12px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 8px;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
}

.activity-badge.new {
  background: linear-gradient(135deg, rgba(239, 68, 68, 0.2), rgba(245, 158, 11, 0.2));
  color: #f59e0b;
}

.side-column {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.side-card {
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(20px);
  border-radius: 24px;
  padding: 24px;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.side-card-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 20px;
}

.side-card-icon {
  width: 40px;
  height: 40px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.side-card-icon svg {
  width: 20px;
  height: 20px;
  stroke: white;
}

.side-card-title {
  font-size: 17px;
  font-weight: 600;
  color: #ffffff;
  margin: 0;
}

.recommendations-card .side-card-icon {
  background: linear-gradient(135deg, #10b981, #059669);
}

.quick-actions-card .side-card-icon.action-icon {
  background: linear-gradient(135deg, #f59e0b, #d97706);
}

.quick-buttons {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px;
}

.quick-btn {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  padding: 20px 16px;
  background: rgba(255, 255, 255, 0.05);
  border: none;
  border-radius: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.quick-btn:hover {
  background: rgba(255, 255, 255, 0.1);
  transform: translateY(-3px);
}

.quick-btn span {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.8);
}

.btn-icon {
  width: 44px;
  height: 44px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.btn-icon svg {
  width: 22px;
  height: 22px;
  stroke: white;
}

.btn-icon.publish { background: linear-gradient(135deg, #10b981, #059669); }
.btn-icon.message { background: linear-gradient(135deg, #3b82f6, #1d4ed8); }
.btn-icon.favorite { background: linear-gradient(135deg, #ec4899, #be185d); }
.btn-icon.order { background: linear-gradient(135deg, #8b5cf6, #6d28d9); }

.tags-card .side-card-icon {
  background: linear-gradient(135deg, #ec4899, #be185d);
}

.tags-list {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.tag-item {
  padding: 10px 16px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 10px;
  font-size: 13px;
  color: rgba(255, 255, 255, 0.7);
  text-decoration: none;
  transition: all 0.3s ease;
}

.tag-item:hover {
  background: rgba(16, 185, 129, 0.2);
  color: #10b981;
}

.main-footer {
  background: rgba(0, 0, 0, 0.3);
  border-top: 1px solid rgba(255, 255, 255, 0.1);
}

.footer-content {
  max-width: 1400px;
  margin: 0 auto;
  padding: 60px 24px 40px;
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 1fr;
  gap: 60px;
}

.footer-logo {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 16px;
}

.footer-logo svg {
  width: 40px;
  height: 40px;
}

.footer-logo span {
  font-size: 20px;
  font-weight: 700;
  color: #ffffff;
}

.footer-desc {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.5);
  line-height: 1.6;
}

.footer-section h4 {
  font-size: 16px;
  font-weight: 600;
  color: #ffffff;
  margin: 0 0 20px 0;
}

.footer-links {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.footer-links a {
  color: rgba(255, 255, 255, 0.6);
  text-decoration: none;
  font-size: 14px;
  transition: all 0.3s ease;
}

.footer-links a:hover {
  color: #10b981;
}

.social-links {
  display: flex;
  gap: 12px;
}

.social-link {
  width: 40px;
  height: 40px;
  border-radius: 10px;
  background: rgba(255, 255, 255, 0.1);
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
}

.social-link:hover {
  background: #10b981;
}

.social-link svg {
  width: 20px;
  height: 20px;
  stroke: rgba(255, 255, 255, 0.8);
}

.footer-bottom {
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  padding: 20px 24px;
  text-align: center;
}

.footer-bottom p {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.4);
  margin: 0;
}

.activity-modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  backdrop-filter: blur(8px);
  z-index: 1001;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 24px;
  animation: fadeIn 0.2s ease;
}

.activity-modal-content {
  width: 100%;
  max-width: 600px;
  background: linear-gradient(135deg, rgba(20, 20, 40, 0.98), rgba(30, 30, 60, 0.98));
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.15);
  border-radius: 24px;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
  overflow: hidden;
  animation: slideUp 0.3s ease;
  max-height: 90vh;
  display: flex;
  flex-direction: column;
}

@keyframes slideUp {
  from { 
    opacity: 0;
    transform: translateY(30px);
  }
  to { 
    opacity: 1;
    transform: translateY(0);
  }
}

.activity-modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 24px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.activity-modal-badge {
  padding: 6px 16px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
  background: rgba(255, 255, 255, 0.1);
  color: rgba(255, 255, 255, 0.7);
}

.activity-modal-badge.new {
  background: linear-gradient(135deg, rgba(245, 158, 11, 0.2), rgba(239, 68, 68, 0.2));
  color: #f59e0b;
}

.activity-modal-close {
  width: 40px;
  height: 40px;
  background: rgba(255, 255, 255, 0.1);
  border: none;
  border-radius: 10px;
  color: rgba(255, 255, 255, 0.7);
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
}

.activity-modal-close:hover {
  background: rgba(239, 68, 68, 0.2);
  color: #ef4444;
}

.activity-modal-close svg {
  width: 20px;
  height: 20px;
}

.activity-modal-body {
  padding: 24px;
  overflow-y: auto;
  flex: 1;
}

.activity-modal-date {
  display: inline-flex;
  flex-direction: column;
  align-items: center;
  padding: 12px 20px;
  background: linear-gradient(135deg, rgba(139, 92, 246, 0.2), rgba(109, 40, 217, 0.2));
  border-radius: 16px;
  margin-bottom: 20px;
}

.activity-modal-day {
  font-size: 32px;
  font-weight: 700;
  color: white;
  line-height: 1;
}

.activity-modal-month {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.6);
  margin-top: 4px;
}

.activity-modal-title {
  font-size: 24px;
  font-weight: 700;
  color: white;
  margin: 0 0 16px 0;
  line-height: 1.3;
}

.activity-modal-desc {
  font-size: 15px;
  color: rgba(255, 255, 255, 0.7);
  line-height: 1.7;
  margin-bottom: 24px;
}

.activity-modal-details {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.activity-detail-item {
  display: flex;
  align-items: flex-start;
  gap: 12px;
  padding: 12px 16px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.08);
}

.detail-icon {
  width: 32px;
  height: 32px;
  border-radius: 8px;
  background: linear-gradient(135deg, rgba(16, 185, 129, 0.2), rgba(5, 150, 105, 0.2));
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  color: #10b981;
}

.detail-icon svg {
  width: 16px;
  height: 16px;
}

.detail-content {
  flex: 1;
  font-size: 14px;
  color: rgba(255, 255, 255, 0.8);
  line-height: 1.5;
}

.activity-modal-footer {
  padding: 16px 24px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
}

.activity-modal-btn {
  width: 100%;
  padding: 14px 24px;
  background: linear-gradient(135deg, #10b981, #059669);
  border: none;
  border-radius: 12px;
  color: white;
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
}

.activity-modal-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(16, 185, 129, 0.3);
}

@media (max-width: 1200px) {
  .content-grid {
    grid-template-columns: 1fr;
  }
  
  .side-column {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
  }
  
  .dashboard-row {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 900px) {
  .hero-section {
    flex-direction: column;
    text-align: center;
  }
  
  .hero-title {
    font-size: 40px;
  }
  
  .hero-stats {
    justify-content: center;
  }
  
  .hero-actions {
    justify-content: center;
  }
  
  .hero-illustration {
    display: none;
  }
  
  .main-nav {
    display: none;
  }
  
  .footer-content {
    grid-template-columns: 1fr 1fr;
  }
  
  .side-column {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 600px) {
  .hero-title {
    font-size: 32px;
  }
  
  .hero-stats {
    flex-wrap: wrap;
    gap: 20px;
  }
  
  .stat-divider {
    display: none;
  }
  
  .dashboard-row {
    grid-template-columns: 1fr;
  }
  
  .footer-content {
    grid-template-columns: 1fr;
  }
}
</style>
