<template>
  <div class="tool">
    <h2>⏰ Cron 表达式</h2>
    <div class="form">
      <input v-model="expr" placeholder="* * * * * (分 时 日 月 周)" />
      <button @click="parse">解析</button>
    </div>
    <div class="result" v-if="result">
      <pre>{{ result }}</pre>
    </div>
    <div class="examples">
      <h3>常见示例：</h3>
      <div class="example" v-for="ex in examples" :key="ex.expr" @click="expr = ex.expr; parse()">
        <b>{{ ex.expr }}</b> - {{ ex.desc }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const expr = ref('0 9 * * *')
const result = ref('')

const parse = async () => {
  if (!expr.value) return
  const res = await axios.get('/api/cron/parse', { params: { expr: expr.value } })
  result.value = res.data.description + '\n下次执行: ' + res.data.nextRun
}

const examples = [
  { expr: '0 9 * * *', desc: '每天早上9点' },
  { expr: '0 0 * * 1', desc: '每周一午夜' },
  { expr: '*/5 * * * *', desc: '每5分钟' },
  { expr: '0 8 * * 1-5', desc: '工作日早上8点' }
]
</script>

<style scoped>
.form { display: flex; gap: 10px; margin: 20px 0; }
input { flex: 1; padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-family: monospace; }
button { padding: 10px 20px; background: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }
.result { background: #f9f9f9; padding: 15px; border-radius: 8px; margin-bottom: 20px; }
pre { white-space: pre-wrap; }
.examples { margin-top: 20px; }
.example { padding: 8px; cursor: pointer; border-bottom: 1px solid #eee; }
.example:hover { background: #f5f5f5; }
</style>