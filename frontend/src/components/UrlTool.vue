<template>
  <div class="tool">
    <h2>🔗 URL 短链接</h2>
    <div class="form">
      <input v-model="url" placeholder="输入要缩短的 URL" />
      <button @click="shorten">生成短链接</button>
    </div>
    <div class="result" v-if="shortUrl">
      <div class="short">
        <input :value="shortUrl" readonly />
        <button @click="copy">复制</button>
      </div>
    </div>
    <div class="history" v-if="history.length">
      <h3>历史记录：</h3>
      <div v-for="h in history" :key="h.short" class="item" @click="expand(h.short)">
        {{ h.short }} → {{ h.original }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const url = ref('')
const shortUrl = ref('')
const history = ref([])

const shorten = async () => {
  if (!url.value) return
  const res = await axios.post('/api/url/shorten', { url: url.value })
  if (res.data.success) {
    shortUrl.value = res.data.shortUrl
    history.value.unshift({ short: res.data.shortUrl, original: url.value })
  }
}

const copy = () => {
  navigator.clipboard.writeText(shortUrl.value)
  alert('已复制!')
}

const expand = async (short) => {
  const code = short.split('/').pop()
  const res = await axios.get('/api/url/expand', { params: { code } })
  if (res.data.success) {
    alert('原始 URL: ' + res.data.originalUrl)
  }
}
</script>

<style scoped>
.form { display: flex; gap: 10px; margin: 20px 0; }
input { flex: 1; padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
button { padding: 10px 20px; background: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }
.result { background: #f9f9f9; padding: 15px; border-radius: 8px; margin-bottom: 20px; }
.short { display: flex; gap: 10px; }
.short input { flex: 1; }
.history { margin-top: 20px; }
.item { padding: 8px; cursor: pointer; border-bottom: 1px solid #eee; }
.item:hover { background: #f5f5f5; }
</style>