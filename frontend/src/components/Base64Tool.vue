<template>
  <div class="tool">
    <h2>🖼️ Base64 Encoder/Decoder</h2>
    <div class="tabs">
      <button :class="{ active: mode === 'encode' }" @click="mode = 'encode'">Encode</button>
      <button :class="{ active: mode === 'decode' }" @click="mode = 'decode'">Decode</button>
    </div>
    <div class="form">
      <textarea v-model="input" :placeholder="mode === 'encode' ? 'Text to encode...' : 'Base64 to decode...'" rows="4"></textarea>
      <button @click="process">{{ mode === 'encode' ? 'Encode' : 'Decode' }}</button>
    </div>
    <div class="result" v-if="output">
      <div class="output">{{ output }}</div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const mode = ref('encode')
const input = ref('')
const output = ref('')

const process = async () => {
  const endpoint = mode.value === 'encode' ? '/api/base64/encode' : '/api/base64/decode'
  const res = await axios.get(`${endpoint}?text=${encodeURIComponent(input.value)}`)
  if (res.data.error) {
    output.value = 'Error: ' + res.data.error
  } else {
    output.value = res.data.output
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
.output { font-family: monospace; word-break: break-all; }
</style>