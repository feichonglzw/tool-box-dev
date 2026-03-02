<template>
  <div class="tool">
    <h2>✏️ String Quote</h2>
    <div class="form">
      <textarea v-model="input" placeholder="输入字符串，每行一个" rows="6"></textarea>
      <div class="options">
        <label>引号类型：</label>
        <select v-model="quote">
          <option value="'">单引号 '</option>
          <option value='"'>双引号 "</option>
        </select>
        <label class="checkbox">
          <input type="checkbox" v-model="addComma"> 行末加逗号
        </label>
      </div>
      <button @click="process">转换</button>
    </div>
    <div class="result" v-if="output">
      <textarea v-model="output" rows="6" readonly></textarea>
      <button @click="copyResult" class="copy-btn">📋 一键复制</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const input = ref('')
const quote = ref("'")
const addComma = ref(true)
const output = ref('')

const process = async () => {
  if (!input.value) return
  const res = await axios.get('/api/string/quote', {
    params: { text: input.value, quote: quote.value, addComma: addComma.value }
  })
  output.value = res.data.output
}

const copyResult = () => {
  const textarea = document.querySelector('.result textarea')
  if (textarea) {
    textarea.select()
    document.execCommand('copy')
    alert('已复制！')
  }
}
</script>

<style scoped>
.form { display: flex; flex-direction: column; gap: 10px; margin: 20px 0; }
textarea { padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-family: monospace; resize: vertical; }
.options { display: flex; gap: 15px; align-items: center; flex-wrap: wrap; }
.options label { display: flex; align-items: center; gap: 5px; }
.checkbox { margin-left: auto; }
select { padding: 6px; border: 1px solid #ddd; border-radius: 4px; }
button { padding: 10px 20px; background: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }
.result { background: #f9f9f9; padding: 15px; border-radius: 8px; position: relative; }
.result textarea { background: #fff; }
.copy-btn { margin-top: 10px; }
</style>