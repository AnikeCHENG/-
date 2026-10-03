<template>
  <div class="messages-page">
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
          <a href="#" class="nav-link" @click.prevent="go('/')">首页</a>
          <a href="#" class="nav-link" @click.prevent="go('/products')">商品</a>
          <a href="#" class="nav-link" @click.prevent="go('/post')">发布</a>
          <a href="#" class="nav-link active">消息</a>
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
      <div class="messages-container">
        <div class="messages-sidebar">
          <div class="sidebar-header">
            <h2 class="sidebar-title">消息中心</h2>
            <span v-if="unreadCount > 0" class="unread-badge">{{ unreadCount }}</span>
          </div>
          
          <div class="tab-nav">
            <button 
              class="tab-btn" 
              :class="{ active: activeTab === 'received' }"
              @click="activeTab = 'received'"
            >
              收件箱
            </button>
            <button 
              class="tab-btn" 
              :class="{ active: activeTab === 'sent' }"
              @click="activeTab = 'sent'"
            >
              已发送
            </button>
          </div>

          <div class="conversations-list">
            <div v-if="loading" class="loading-state">
              <div class="loading-spinner"></div>
              <p>加载中...</p>
            </div>
            
            <div v-else-if="conversations.length === 0" class="empty-state">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
                <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/>
              </svg>
              <p>暂无消息</p>
            </div>
            
            <div 
              v-else
              v-for="conv in conversations" 
              :key="conv.userId"
              class="conversation-item"
              :class="{ active: selectedUserId === conv.userId, unread: conv.unreadCount > 0 }"
              @click="selectConversation(conv)"
            >
              <div class="conv-avatar">
                <img :src="conv.avatar || defaultAvatar" :alt="conv.username" />
                <span v-if="conv.unreadCount > 0" class="msg-badge">{{ conv.unreadCount }}</span>
              </div>
              <div class="conv-info">
                <div class="conv-header">
                  <span class="conv-name">{{ conv.username }}</span>
                  <span class="conv-time">{{ formatTime(conv.lastMessageTime) }}</span>
                </div>
                <p class="conv-preview">{{ conv.lastMessage }}</p>
              </div>
            </div>
          </div>
        </div>

        <div class="chat-panel">
          <div v-if="!selectedUserId" class="no-selection">
            <div class="empty-chat">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
                <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/>
              </svg>
              <h3>选择对话</h3>
              <p>选择一个联系人开始聊天</p>
            </div>
          </div>

          <template v-else>
            <div class="chat-header">
              <div class="chat-user-info">
                <div class="chat-avatar">
                  <img :src="currentConversation?.avatar || defaultAvatar" :alt="currentConversation?.username" />
                </div>
                <div class="chat-user-details">
                  <span class="chat-username">{{ currentConversation?.username }}</span>
                  <span class="chat-status online">在线</span>
                </div>
              </div>
              <div class="chat-actions">
                <button class="action-icon-btn" title="查看资料">
                  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                    <circle cx="12" cy="7" r="4"/>
                  </svg>
                </button>
              </div>
            </div>

            <div class="chat-messages" ref="messagesContainer">
              <div 
                v-for="(msg, index) in currentMessages" 
                :key="msg.id || index"
                class="message"
                :class="{ 
                  sent: msg.senderId === currentUserId,
                  received: msg.senderId !== currentUserId 
                }"
              >
                <div class="message-avatar" v-if="msg.senderId !== currentUserId">
                  <img :src="currentConversation?.avatar || defaultAvatar" alt="头像" />
                </div>
                <div class="message-content">
                  <div class="message-bubble">
                    <p>{{ msg.content }}</p>
                  </div>
                  <span class="message-time">{{ formatMessageTime(msg.createdTime) }}</span>
                </div>
              </div>
            </div>

            <div class="chat-input">
              <div class="input-wrapper">
                <button class="input-action" title="添加图片">
                  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <rect x="3" y="3" width="18" height="18" rx="2" ry="2"/>
                    <circle cx="8.5" cy="8.5" r="1.5"/>
                    <polyline points="21 15 16 10 5 21"/>
                  </svg>
                </button>
                <input 
                  type="text"
                  v-model="newMessage"
                  placeholder="输入消息..."
                  @keyup.enter="sendMessage"
                />
                <button class="send-btn" @click="sendMessage" :disabled="!newMessage.trim()">
                  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <line x1="22" y1="2" x2="11" y2="13"/>
                    <polygon points="22 2 15 22 11 13 2 9 22 2"/>
                  </svg>
                  发送
                </button>
              </div>
            </div>
          </template>
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
import { ref, computed, onMounted, nextTick, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { 
  getReceivedMessages, 
  getConversation, 
  getUnreadCount, 
  sendMessage as sendMessageApi,
  markAsRead 
} from '../api/message'
import { useNotificationStore } from '../stores/notification'

const router = useRouter()
const route = useRoute()
const notificationStore = useNotificationStore()
const loading = ref(true)
const activeTab = ref('received')
const conversations = ref([])
const selectedUserId = ref(null)
const currentMessages = ref([])
const newMessage = ref('')
const unreadCount = ref(0)
const messagesContainer = ref(null)
const currentUserId = ref(parseInt(localStorage.getItem('userId') || '0'))

const defaultAvatar = "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 40 40'%3E%3Ccircle cx='20' cy='20' r='20' fill='%23333'/%3E%3Ccircle cx='20' cy='16' r='6' fill='%23666'/%3E%3Cpath d='M8 36c0-6.6 5.4-12 12-12s12 5.4 12 12' fill='%23666'/%3E%3C/svg%3E"

const currentConversation = computed(() => {
  return conversations.value.find(c => c.userId === selectedUserId.value)
})

function go(path) {
  router.push(path)
}

async function fetchMessages() {
  loading.value = true
  try {
    const [receivedRes, unreadRes] = await Promise.all([
      getReceivedMessages(),
      getUnreadCount()
    ])
    
    if (unreadRes.code === 200 && unreadRes.data) {
      const count = typeof unreadRes.data === 'object' ? (unreadRes.data.count || 0) : (unreadRes.data || 0)
      unreadCount.value = count
      notificationStore.setUnreadCount(count)
    }
    
    if (receivedRes.code === 200 && receivedRes.data) {
      const messages = receivedRes.data
      const convMap = new Map()
      
      messages.forEach(msg => {
        const otherId = msg.senderId === currentUserId.value ? msg.receiverId : msg.senderId
        const otherName = msg.senderId === currentUserId.value 
          ? (msg.receiver?.username || '用户' + msg.receiverId)
          : (msg.sender?.username || '用户' + msg.senderId)
        
        if (!convMap.has(otherId)) {
          convMap.set(otherId, {
            userId: otherId,
            username: otherName,
            avatar: defaultAvatar,
            lastMessage: '',
            lastMessageTime: msg.createdTime,
            unreadCount: 0,
            messages: []
          })
        }
        
        const conv = convMap.get(otherId)
        conv.messages.push(msg)
        
        if (msg.content && conv.lastMessage === '') {
          conv.lastMessage = msg.content
          conv.lastMessageTime = msg.createdTime
        }
        
        if (msg.isRead === 0 && msg.receiverId === currentUserId.value) {
          conv.unreadCount++
        }
      })
      
      conversations.value = Array.from(convMap.values())
        .sort((a, b) => new Date(b.lastMessageTime) - new Date(a.lastMessageTime))
    }
  } catch (e) {
    console.error('加载消息失败:', e)
  } finally {
    loading.value = false
  }
}

async function selectConversation(conv) {
  selectedUserId.value = conv.userId
  
  try {
    const res = await getConversation(conv.userId)
    if (res.code === 200 && res.data) {
      currentMessages.value = res.data.sort((a, b) => 
        new Date(a.createdTime) - new Date(b.createdTime)
      )
      
      await nextTick()
      scrollToBottom()
      
      const unreadMessages = currentMessages.value.filter(
        m => m.senderId !== currentUserId.value && m.isRead === 0
      )
      
      const unreadCountToMark = unreadMessages.length
      
      for (const msg of unreadMessages) {
        try {
          await markAsRead(msg.id)
        } catch (e) {
          console.log('标记已读失败')
        }
      }
      
      const newUnreadRes = await getUnreadCount()
      if (newUnreadRes.code === 200) {
        const newCount = typeof newUnreadRes.data === 'object' ? (newUnreadRes.data.count || 0) : (newUnreadRes.data || 0)
        unreadCount.value = newCount
        notificationStore.setUnreadCount(newCount)
      }
    }
  } catch (e) {
    console.error('加载对话失败:', e)
  }
}

async function sendMessage() {
  if (!newMessage.value.trim() || !selectedUserId.value) return
  
  const content = newMessage.value.trim()
  newMessage.value = ''
  
  try {
    const res = await sendMessageApi({
      receiverId: selectedUserId.value,
      content: content
    })
    
    if (res.code === 200) {
      currentMessages.value.push({
        id: res.data?.id || Date.now(),
        senderId: currentUserId.value,
        receiverId: selectedUserId.value,
        content: content,
        createdTime: new Date().toISOString(),
        isRead: 0
      })
      
      await nextTick()
      scrollToBottom()
    } else {
      alert(res.message || '发送失败')
      newMessage.value = content
    }
  } catch (e) {
    alert(e.message || '发送失败')
    newMessage.value = content
  }
}

function scrollToBottom() {
  if (messagesContainer.value) {
    messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
  }
}

function formatTime(time) {
  if (!time) return ''
  const date = new Date(time)
  const now = new Date()
  const diff = now - date
  
  if (diff < 60000) return '刚刚'
  if (diff < 3600000) return Math.floor(diff / 60000) + '分钟前'
  if (diff < 86400000) return Math.floor(diff / 3600000) + '小时前'
  if (diff < 604800000) return Math.floor(diff / 86400000) + '天前'
  
  return date.toLocaleDateString('zh-CN')
}

function formatMessageTime(time) {
  if (!time) return ''
  const date = new Date(time)
  return date.toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' })
}

async function handleLogout() {
  localStorage.removeItem('token')
  localStorage.removeItem('username')
  sessionStorage.removeItem('justLoggedIn')
  router.push('/login')
}

watch(activeTab, () => {
  selectedUserId.value = null
  currentMessages.value = []
})

onMounted(async () => {
  await fetchMessages()
  
  // 检查URL参数中是否有sellerId，如果有，自动选择对应的卖家进行聊天
  const sellerId = route.query.sellerId
  if (sellerId) {
    const sellerIdNum = parseInt(sellerId)
    const sellerConv = conversations.value.find(conv => conv.userId === sellerIdNum)
    if (sellerConv) {
      selectConversation(sellerConv)
    }
  }
})
</script>

<style scoped>
.messages-page {
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
  padding: 100px 24px 60px;
  max-width: 1400px;
  margin: 0 auto;
  position: relative;
  z-index: 1;
  height: calc(100vh - 160px);
}

.messages-container {
  display: grid;
  grid-template-columns: 360px 1fr;
  height: 100%;
  background: rgba(255, 255, 255, 0.06);
  border: 1px solid rgba(255, 255, 255, 0.12);
  border-radius: 24px;
  overflow: hidden;
  backdrop-filter: blur(20px);
}

.messages-sidebar {
  border-right: 1px solid rgba(255, 255, 255, 0.1);
  display: flex;
  flex-direction: column;
}

.sidebar-header {
  padding: 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.sidebar-title {
  font-size: 22px;
  font-weight: 700;
  color: white;
}

.unread-badge {
  background: linear-gradient(135deg, #ef4444, #dc2626);
  color: white;
  font-size: 12px;
  font-weight: 600;
  padding: 4px 10px;
  border-radius: 20px;
}

.tab-nav {
  display: flex;
  padding: 16px 24px;
  gap: 12px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.tab-btn {
  flex: 1;
  padding: 10px 16px;
  background: rgba(255, 255, 255, 0.08);
  border: 1px solid rgba(255, 255, 255, 0.15);
  border-radius: 10px;
  color: rgba(255, 255, 255, 0.7);
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.tab-btn:hover {
  background: rgba(255, 255, 255, 0.12);
  color: white;
}

.tab-btn.active {
  background: linear-gradient(135deg, #10b981, #059669);
  border-color: transparent;
  color: white;
}

.conversations-list {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
}

.loading-state,
.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  color: rgba(255, 255, 255, 0.5);
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 3px solid rgba(255, 255, 255, 0.1);
  border-top-color: #10b981;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 16px;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.empty-state svg {
  width: 48px;
  height: 48px;
  margin-bottom: 16px;
  opacity: 0.5;
}

.conversation-item {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 14px;
  border-radius: 14px;
  cursor: pointer;
  transition: all 0.3s ease;
  margin-bottom: 8px;
}

.conversation-item:hover {
  background: rgba(255, 255, 255, 0.08);
}

.conversation-item.active {
  background: rgba(16, 185, 129, 0.15);
}

.conversation-item.unread .conv-name {
  font-weight: 600;
}

.conv-avatar {
  position: relative;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  overflow: hidden;
  flex-shrink: 0;
}

.conv-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.msg-badge {
  position: absolute;
  top: -4px;
  right: -4px;
  background: #ef4444;
  color: white;
  font-size: 11px;
  font-weight: 600;
  min-width: 18px;
  height: 18px;
  border-radius: 9px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 5px;
}

.conv-info {
  flex: 1;
  min-width: 0;
}

.conv-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 4px;
}

.conv-name {
  font-size: 15px;
  font-weight: 500;
  color: white;
}

.conv-time {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.4);
}

.conv-preview {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.5);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.chat-panel {
  display: flex;
  flex-direction: column;
}

.no-selection {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}

.empty-chat {
  text-align: center;
  color: rgba(255, 255, 255, 0.5);
}

.empty-chat svg {
  width: 80px;
  height: 80px;
  margin-bottom: 20px;
  opacity: 0.4;
}

.empty-chat h3 {
  font-size: 20px;
  font-weight: 600;
  color: white;
  margin-bottom: 8px;
}

.chat-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 24px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.chat-user-info {
  display: flex;
  align-items: center;
  gap: 14px;
}

.chat-avatar {
  width: 46px;
  height: 46px;
  border-radius: 50%;
  overflow: hidden;
}

.chat-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.chat-user-details {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.chat-username {
  font-size: 17px;
  font-weight: 600;
  color: white;
}

.chat-status {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.5);
}

.chat-status.online {
  color: #10b981;
}

.action-icon-btn {
  width: 40px;
  height: 40px;
  border-radius: 10px;
  border: 1px solid rgba(255, 255, 255, 0.15);
  background: rgba(255, 255, 255, 0.08);
  color: white;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
}

.action-icon-btn svg {
  width: 20px;
  height: 20px;
}

.action-icon-btn:hover {
  background: rgba(255, 255, 255, 0.15);
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 24px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.message {
  display: flex;
  gap: 12px;
  max-width: 70%;
}

.message.sent {
  align-self: flex-end;
  flex-direction: row-reverse;
}

.message.received {
  align-self: flex-start;
}

.message-avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  overflow: hidden;
  flex-shrink: 0;
}

.message-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.message-content {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.message.sent .message-content {
  align-items: flex-end;
}

.message.received .message-content {
  align-items: flex-start;
}

.message-bubble {
  padding: 12px 18px;
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.15);
  border-radius: 18px;
  border-top-left-radius: 4px;
}

.message.sent .message-bubble {
  background: linear-gradient(135deg, #10b981, #059669);
  border: none;
  border-top-left-radius: 18px;
  border-top-right-radius: 4px;
}

.message-bubble p {
  font-size: 15px;
  color: white;
  line-height: 1.5;
  margin: 0;
}

.message-time {
  font-size: 11px;
  color: rgba(255, 255, 255, 0.4);
  padding: 0 8px;
}

.chat-input {
  padding: 20px 24px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
}

.input-wrapper {
  display: flex;
  align-items: center;
  gap: 12px;
  background: rgba(255, 255, 255, 0.08);
  border: 1px solid rgba(255, 255, 255, 0.15);
  border-radius: 16px;
  padding: 8px 8px 8px 20px;
}

.input-action {
  width: 40px;
  height: 40px;
  border-radius: 10px;
  border: none;
  background: transparent;
  color: rgba(255, 255, 255, 0.6);
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
}

.input-action svg {
  width: 22px;
  height: 22px;
}

.input-action:hover {
  background: rgba(255, 255, 255, 0.1);
  color: white;
}

.input-wrapper input {
  flex: 1;
  background: transparent;
  border: none;
  outline: none;
  font-size: 15px;
  color: white;
  padding: 12px 0;
}

.input-wrapper input::placeholder {
  color: rgba(255, 255, 255, 0.4);
}

.send-btn {
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

.send-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(16, 185, 129, 0.3);
}

.send-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.send-btn svg {
  width: 18px;
  height: 18px;
}

.main-footer {
  position: relative;
  z-index: 1;
  padding: 30px 24px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  margin-top: auto;
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
