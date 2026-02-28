<template>
  <div class="tool">
    <h2>⏰ Timestamp Converter</h2>
    <div class="tabs">
      <button :class="{ active: mode === 'now' }" @click="mode = 'now'; getNow()">Now</button>
      <button :class="{ active: mode === 'toUnix' }" @click="mode = 'toUnix'">Date → Unix</button>
      <button :class="{ active: mode === 'toDate' }" @click="mode = 'toDate'">Unix → Date</button>
    </div>
    
    <div class="form" v-if="mode === 'now'">
      <button @click="getNow">Refresh</button>
    </div>
    
    <div class="form" v-if="mode === 'toUnix'">
      <input type="datetime-local" v-model="datetime">
      <button @click="convertToUnix">Convert</button>
    </div>
    
    <div class="form" v-if="mode === 'toDate'">
      <input type="number" v-model="unix" placeholder="Unix timestamp">
      <button @click="convertToDate">Convert</button>
    </div>
    
    <div class="result" v-if="result">
      <div v-for="(v, k) in result" :key="k" class="result-item">
        <span class="key">{{ k }}:</span> <span class="value">{{ v }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const mode = ref('now')
const datetime = ref('')
const unix = ref('')
const result = ref(null)

const getNow = async () => {
  const res = await axios.get('/api/timestamp/now')
  result.value = res.data
}
getNow()

const convertToUnix = async () => {
  const res = await axios.get(`/api/timestamp/convert?datetime=${datetime.value}`)
  result.value = res.data
}

const convertToDate = async () => {
  const res = await axios.get(`/api/timestamp/convert?unix=${unix.value}`)
  result.value = res.data
}
</script>

<style scoped>
.tabs { display: flex; gap: 10px; margin: 20px 0; }
.tabs button { padding: 8px 16px; border: 1px solid #ddd; background: #fff; border-radius: 4px; cursor: pointer; }
.tabs button.active { background: #4CAF50; color: white; border-color: #4CAF50; }
.form { display: flex; gap: 10px; margin: 20px 0; }
input { padding: 8px; border: 1px solid #ddd; border-radius: 4px; }
button { padding: 8px 20px; background: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }
.result { background: #f9f9f9; padding: 15px; border-radius: 8px; }
.result-item { padding: 5px 0; }
.key { font-weight: bold; color: #666; }
.value { font-family: monospace; margin-left: 10px; }
</style>