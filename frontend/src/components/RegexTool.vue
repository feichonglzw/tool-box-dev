<template>
  <div class="tool">
    <h2>🧪 正则测试</h2>
    <div class="form">
      <input v-model="pattern" placeholder="正则表达式" />
      <textarea v-model="text" placeholder="测试文本" rows="4"></textarea>
      <button @click="test">测试</button>
    </div>
    <div class="result" v-if="result">
      <div class="count">匹配到 {{ result.matchCount }} 个结果</div>
      <div v-for="(m, i) in result.matches" :key="i" class="match">
        {{ i + 1 }}. {{ m }}
      </div>
    </div>
    <div class="common">
      <h3>常用正则：</h3>
      <div class="tags">
        <span v-for="(p, name) in commonPatterns" :key="name" @click="pattern = p" class="tag">
          {{ name }}
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const pattern = ref('')
const text = ref('')
const result = ref(null)
const commonPatterns = ref({})

const test = async () => {
  if (!pattern.value || !text.value) return
  const res = await axios.post('/api/regex/test', { pattern: pattern.value, text: text.value })
  result.value = res.data
}

onMounted(async () => {
  const res = await axios.get('/api/regex/common')
  commonPatterns.value = res.data.patterns
})
</script>

<style scoped>
.form { display: flex; flex-direction: column; gap: 10px; margin: 20px 0; }
input, textarea { padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-family: monospace; }
button { padding: 10px 20px; background: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }
.result { background: #f9f9f9; padding: 15px; border-radius: 8px; margin: 20px 0; }
.count { font-weight: bold; margin-bottom: 10px; }
.match { padding: 5px 0; border-bottom: 1px solid #eee; }
.common { margin-top: 20px; }
.tags { display: flex; flex-wrap: wrap; gap: 8px; }
.tag { padding: 5px 10px; background: #e0e0e0; border-radius: 4px; cursor: pointer; font-size: 12px; }
.tag:hover { background: #ccc; }
</style>