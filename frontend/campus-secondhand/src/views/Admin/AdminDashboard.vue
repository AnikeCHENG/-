<template>
  <div class="admin-dashboard">
    <div class="admin-header">
      <h1>管理员控制面板</h1>
      <p>欢迎回来，{{ user?.username }}</p>
    </div>
    
    <div class="admin-menu">
      <router-link to="/admin/users" class="menu-item">
        <span class="menu-icon">👥</span>
        <span>用户管理</span>
      </router-link>
      <router-link to="/admin/products" class="menu-item">
        <span class="menu-icon">📦</span>
        <span>商品管理</span>
      </router-link>
      <router-link to="/admin/orders" class="menu-item">
        <span class="menu-icon">📋</span>
        <span>订单管理</span>
      </router-link>
      <router-link to="/admin/categories" class="menu-item">
        <span class="menu-icon">📁</span>
        <span>分类管理</span>
      </router-link>
      <router-link to="/admin/messages" class="menu-item">
        <span class="menu-icon">💬</span>
        <span>消息管理</span>
      </router-link>
      <router-link to="/" class="menu-item">
        <span class="menu-icon">🏠</span>
        <span>返回首页</span>
      </router-link>
    </div>
    
    <div class="admin-stats">
      <div class="stat-card">
        <h3>用户总数</h3>
        <p class="stat-number">{{ userCount }}</p>
      </div>
      <div class="stat-card">
        <h3>商品总数</h3>
        <p class="stat-number">{{ productCount }}</p>
      </div>
      <div class="stat-card">
        <h3>订单总数</h3>
        <p class="stat-number">{{ orderCount }}</p>
      </div>
      <div class="stat-card">
        <h3>消息总数</h3>
        <p class="stat-number">{{ messageCount }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const user = ref(JSON.parse(localStorage.getItem('user') || sessionStorage.getItem('user') || '{}'))
const userCount = ref(0)
const productCount = ref(0)
const orderCount = ref(0)
const messageCount = ref(0)

const fetchStats = async () => {
  try {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token')
    
    // 获取统计数据
    const statsResponse = await axios.get('/api/admin/stats', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
    
    if (statsResponse.data && statsResponse.data.data) {
      userCount.value = statsResponse.data.data.userCount || 0
      productCount.value = statsResponse.data.data.productCount || 0
      orderCount.value = statsResponse.data.data.orderCount || 0
      messageCount.value = statsResponse.data.data.messageCount || 0
    }
  } catch (error) {
    console.error('获取统计数据失败:', error)
  }
}

onMounted(() => {
  fetchStats()
})
</script>

<style scoped>
.admin-dashboard {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.admin-header {
  text-align: center;
  margin-bottom: 40px;
}

.admin-header h1 {
  font-size: 2.5rem;
  color: #333;
  margin-bottom: 10px;
}

.admin-header p {
  font-size: 1.2rem;
  color: #666;
}

.admin-menu {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-bottom: 40px;
}

.menu-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 10px;
  text-decoration: none;
  color: #333;
  transition: all 0.3s ease;
  min-width: 150px;
}

.menu-item:hover {
  background-color: #e0e0e0;
  transform: translateY(-5px);
}

.menu-icon {
  font-size: 2rem;
  margin-bottom: 10px;
}

.admin-stats {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  margin-top: 40px;
}

.stat-card {
  background-color: #fff;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.stat-card h3 {
  font-size: 1.2rem;
  color: #666;
  margin-bottom: 10px;
}

.stat-number {
  font-size: 3rem;
  font-weight: bold;
  color: #333;
}
</style>
