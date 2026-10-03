<template>
  <div class="admin-messages">
    <h1>消息管理</h1>
    
    <div class="message-search">
      <input 
        type="text" 
        v-model="searchQuery" 
        placeholder="搜索消息..."
        class="search-input"
      />
      <button @click="fetchMessages" class="search-btn">搜索</button>
    </div>
    
    <div class="message-list">
      <div class="message-item" v-for="message in messages" :key="message.id">
        <div class="message-header">
          <h3>消息 #{{ message.id }}</h3>
          <span class="message-date">{{ formatDate(message.createdAt) }}</span>
        </div>
        <div class="message-info">
          <p><strong>发送者:</strong> {{ message.sender?.username || '未知用户' }}</p>
          <p><strong>接收者:</strong> {{ message.receiver?.username || '未知用户' }}</p>
          <p><strong>内容:</strong> {{ message.content }}</p>
        </div>
        <div class="message-actions">
          <button @click="deleteMessage(message.id)" class="btn-danger">
            删除消息
          </button>
        </div>
      </div>
    </div>
    
    <!-- 分页 -->
    <div v-if="total > 0" class="pagination">
      <button 
        class="pagination-button" 
        @click="changePage(1)" 
        :disabled="page === 1"
      >
        首页
      </button>
      <button 
        class="pagination-button" 
        @click="changePage(page - 1)" 
        :disabled="page === 1"
      >
        上一页
      </button>
      <span class="pagination-info">
        第 {{ page }} 页，共 {{ totalPages }} 页，总计 {{ total }} 条
      </span>
      <button 
        class="pagination-button" 
        @click="changePage(page + 1)" 
        :disabled="page === totalPages"
      >
        下一页
      </button>
      <button 
        class="pagination-button" 
        @click="changePage(totalPages)" 
        :disabled="page === totalPages"
      >
        末页
      </button>
    </div>
    
    <div v-if="messages.length === 0" class="no-messages">
      <p>暂无消息</p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'

const messages = ref([])
const searchQuery = ref('')
const page = ref(1)
const pageSize = ref(10)
const total = ref(0)

const totalPages = computed(() => {
  return Math.ceil(total.value / pageSize.value)
})

const fetchMessages = async () => {
  try {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token')
    const response = await axios.get('/api/admin/messages', {
      headers: {
        Authorization: `Bearer ${token}`
      },
      params: {
        search: searchQuery.value,
        page: page.value,
        pageSize: pageSize.value
      }
    })
    if (response.data && response.data.data) {
      messages.value = response.data.data.items
      total.value = response.data.data.total
    }
  } catch (error) {
    console.error('获取消息失败:', error)
  }
}

const deleteMessage = async (messageId) => {
  if (confirm('确定要删除这个消息吗？')) {
    try {
      const token = localStorage.getItem('token') || sessionStorage.getItem('token')
      await axios.delete(`/api/admin/messages/${messageId}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      })
      // 重新获取消息列表
      fetchMessages()
    } catch (error) {
      console.error('删除消息失败:', error)
    }
  }
}

const changePage = (newPage) => {
  if (newPage >= 1 && newPage <= totalPages.value) {
    page.value = newPage
    fetchMessages()
  }
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleString()
}

onMounted(() => {
  fetchMessages()
})
</script>

<style scoped>
.admin-messages {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.admin-messages h1 {
  font-size: 2rem;
  color: #333;
  margin-bottom: 30px;
}

.message-search {
  margin-bottom: 30px;
  display: flex;
  gap: 10px;
}

.search-input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 16px;
}

.search-btn {
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.search-btn:hover {
  background-color: #45a049;
}

.message-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.message-item {
  background-color: #f9f9f9;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.message-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.message-header h3 {
  font-size: 1.2rem;
  color: #333;
  margin: 0;
}

.message-date {
  font-size: 14px;
  color: #666;
}

.message-info {
  margin-bottom: 15px;
}

.message-info p {
  margin: 5px 0;
  color: #666;
}

.message-actions {
  display: flex;
  gap: 10px;
}

.btn-danger {
  padding: 8px 16px;
  background-color: #f44336;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
}

.btn-danger:hover {
  background-color: #da190b;
}

/* 分页样式 */
.pagination {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  margin-top: 30px;
  padding-top: 20px;
  border-top: 1px solid #eee;
}

.pagination-button {
  padding: 8px 16px;
  background-color: #f5f5f5;
  color: #333;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s ease;
}

.pagination-button:hover:not(:disabled) {
  background-color: #e0e0e0;
  transform: translateY(-1px);
}

.pagination-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.pagination-info {
  font-size: 14px;
  color: #666;
  font-weight: 500;
}

.no-messages {
  text-align: center;
  padding: 50px;
  color: #999;
  font-size: 1.2rem;
}
</style>