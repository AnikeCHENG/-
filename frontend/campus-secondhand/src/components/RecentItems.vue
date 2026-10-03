<template>
  <div class="recent-list">
    <div class="item" v-for="(it, idx) in items" :key="idx" @click="$emit('view', it)">
      <div class="thumb">
        <img :src="it.img" :alt="it.title" />
      </div>
      <div class="info">
        <div class="title">{{ it.title }}</div>
        <div class="meta">{{ it.price }} · 发布于校园</div>
      </div>
      <div class="view-btn">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <circle cx="11" cy="11" r="8"/>
          <line x1="21" y1="21" x2="16.65" y2="16.65"/>
        </svg>
      </div>
    </div>
    <div v-if="!items || items.length===0" class="empty">暂无商品，快去发布第一个吧！</div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue'
const props = defineProps({ items: { type: Array, default: () => [] } })
</script>

<style scoped>
.recent-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 12px;
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.03);
  border: 1px solid rgba(255, 255, 255, 0.08);
  cursor: pointer;
  transition: all 0.3s ease;
}

.item:hover {
  background: rgba(255, 255, 255, 0.08);
  border-color: rgba(16, 185, 129, 0.3);
  transform: translateX(4px);
}

.thumb {
  width: 72px;
  height: 72px;
  border-radius: 10px;
  overflow: hidden;
  flex-shrink: 0;
  background: rgba(255, 255, 255, 0.05);
  display: flex;
  align-items: center;
  justify-content: center;
}

.thumb img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.info {
  flex: 1;
  min-width: 0;
}

.title {
  font-weight: 600;
  font-size: 15px;
  color: #ffffff;
  margin-bottom: 4px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.meta {
  color: rgba(255, 255, 255, 0.5);
  font-size: 13px;
}

.view-btn {
  width: 36px;
  height: 36px;
  border-radius: 8px;
  background: linear-gradient(135deg, rgba(16, 185, 129, 0.2), rgba(59, 130, 246, 0.2));
  display: flex;
  align-items: center;
  justify-content: center;
  color: #10b981;
  transition: all 0.3s ease;
  flex-shrink: 0;
}

.item:hover .view-btn {
  background: linear-gradient(135deg, #10b981, #059669);
  color: white;
  transform: scale(1.1);
}

.view-btn svg {
  width: 18px;
  height: 18px;
}

.empty {
  color: rgba(255, 255, 255, 0.4);
  padding: 24px;
  text-align: center;
  font-size: 14px;
}
</style>
