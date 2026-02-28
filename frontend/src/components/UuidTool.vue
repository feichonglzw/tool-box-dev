<template>
  <div class="tool">
    <h2>🔢 UUID Generator</h2>
    <div class="form">
      <label>Count:</label>
      <input type="number" v-model="count" min="1" max="100">
      <button @click="generate">Generate</button>
    </div>
    <div class="result" v-if="uuids.length">
      <div v-for="uuid in uuids" :key="uuid" class="uuid-item">{{ uuid }}</div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const count = ref(10)
const uuids = ref([])

const generate = async () => {
  const res = await axios.get(`/api/uuid/generate?count=${count.value}`)
  uuids.value = res.data.uuids
}
generate()
</script>

<style scoped>
.form { display: flex; gap: 10px; margin: 20px 0; align-items: center; }
input { padding: 8px; border: 1px solid #ddd; border-radius: 4px; width: 80px; }
button { padding: 8px 20px; background: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }
.result { background: #f9f9f9; padding: 15px; border-radius: 8px; max-height: 300px; overflow-y: auto; }
.uuid-item { font-family: monospace; padding: 5px 0; border-bottom: 1px solid #eee; }
</style>