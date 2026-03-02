<template>
  <div class="tool">
    <h2>🤖 AI 对话</h2>
    <div class="chat-box">
      <div v-for="(msg, i) in messages" :key="i" :class="['msg', msg.role]">
        <span class="role">{{ msg.role === 'user' ? '你' : 'AI' }}：</span>
        <span class="content">{{ msg.content }}</span>
      </div>
      <div v-if="loading" class="msg ai">
        <span class="content">思考中...</span>
      </div>
    </div>
    <div class="input-box">
      <input v-model="input" @keyup.enter="send" placeholder="输入消息..." :disabled="loading">
      <button @click="send" :disabled="loading">发送</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const messages = ref([])
const input = ref('')
const loading = ref(false)

const send = async () => {
  if (!input.value.trim() || loading.value) return
  const userMsg = input.value
  messages.value.push({ role: 'user', content: userMsg })
  input.value = ''
  loading.value = true
  
  try {
    const res = await axios.post('/api/ai/chat', { message: userMsg })
    if (res.data.error) {
      messages.value.push({ role: 'ai', content: '错误：' + res.data.error })
    } else {
      messages.value.push({ role: 'ai', content: res.data.reply })
    }
  } catch (e) {
    messages.value.push({ role: 'ai', content: '请求失败：' + e.message })
  }
  loading.value = false
}
</script>

<style scoped>
.tool { display: flex; flex-direction: column; height: 100%; }
.chat-box { flex: 1; overflow-y: auto; padding: 15px; background: #f9f9f9; border-radius: 8px; min-height: 300px; margin-bottom: 15px; }
.msg { padding: 8px 12px; margin-bottom: 8px; border-radius: 8px; max-width: 80%; }
.msg.user { background: #e3f2fd; margin-left: auto; }
.msg.ai { background: #fff; }
.role { font-weight: bold; font-size: 12px; color: #666; }
.input-box { display: flex; gap: 10px; }
.input-box input { flex: 1; padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
.input-box button { padding: 10px 20px; background: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }
.input-box button:disabled { background: #ccc; }
</style>