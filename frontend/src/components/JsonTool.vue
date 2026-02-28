<template>
  <div class="tool">
    <h2>📝 JSON Formatter</h2>
    <div class="tabs">
      <button :class="{ active: mode === 'format' }" @click="mode = 'format'">Format</button>
      <button :class="{ active: mode === 'compress' }" @click="mode = 'compress'">Compress</button>
      <button :class="{ active: mode === 'validate' }" @click="mode = 'validate'">Validate</button>
    </div>
    <div class="form">
      <textarea v-model="input" placeholder='{"key": "value"}' rows="8"></textarea>
      <button @click="process">{{ mode === 'format' ? 'Format' : mode === 'compress' ? 'Compress' : 'Validate' }}</button>
    </div>
    <div class="result" v-if="output">
      <pre>{{ output }}</pre>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const mode = ref('format')
const input = ref('')
const output = ref('')

const process = async () => {
  let data
  try {
    data = JSON.parse(input.value)
  } catch (e) {
    output.value = 'Invalid JSON: ' + e.message
    return
  }
  
  try {
    const endpoint = mode.value === 'format' ? '/api/json/format' : mode.value === 'compress' ? '/api/json/compress' : '/api/json/validate'
    const res = await axios.post(endpoint, { data })
    if (res.data.error) {
      output.value = 'Error: ' + res.data.error
    } else if (res.data.valid !== undefined) {
      output.value = res.data.valid ? '✅ Valid JSON' : '❌ Invalid: ' + res.data.error
    } else {
      output.value = res.data.formatted || res.data.compressed
    }
  } catch (e) {
    output.value = 'Error: ' + e.message
  }
}
</script>

<style scoped>
.tabs { display: flex; gap: 10px; margin: 20px 0; }
.tabs button { padding: 8px 16px; border: 1px solid #ddd; background: #fff; border-radius: 4px; cursor: pointer; }
.tabs button.active { background: #4CAF50; color: white; border-color: #4CAF50; }
.form { display: flex; flex-direction: column; gap: 10px; margin: 20px 0; }
textarea { padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-family: monospace; resize: vertical; }
button { padding: 10px 20px; background: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }
.result { background: #f9f9f9; padding: 15px; border-radius: 8px; }
pre { font-family: monospace; white-space: pre-wrap; word-break: break-all; }
</style>