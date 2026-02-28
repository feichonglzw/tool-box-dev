<template>
  <div class="tool">
    <h2>🔐 Password Generator</h2>
    <div class="form">
      <div class="field">
        <label>Length:</label>
        <input type="number" v-model="length" min="4" max="128">
      </div>
      <div class="checkboxes">
        <label><input type="checkbox" v-model="options.lowercase"> Lowercase</label>
        <label><input type="checkbox" v-model="options.uppercase"> Uppercase</label>
        <label><input type="checkbox" v-model="options.digits"> Digits</label>
        <label><input type="checkbox" v-model="options.special"> Special</label>
      </div>
      <button @click="generate">Generate</button>
    </div>
    <div class="result" v-if="password">
      <div class="password">{{ password }}</div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import axios from 'axios'

const length = ref(16)
const options = reactive({ lowercase: true, uppercase: true, digits: true, special: false })
const password = ref('')

const generate = async () => {
  const params = new URLSearchParams({
    length: length.value,
    lowercase: options.lowercase,
    uppercase: options.uppercase,
    digits: options.digits,
    special: options.special
  })
  const res = await axios.get(`/api/password/generate?${params}`)
  password.value = res.data.password
}
generate()
</script>

<style scoped>
.form { display: flex; flex-direction: column; gap: 15px; margin: 20px 0; }
.field { display: flex; align-items: center; gap: 10px; }
.checkboxes { display: flex; gap: 15px; flex-wrap: wrap; }
.checkboxes label { display: flex; gap: 5px; cursor: pointer; }
input[type="number"] { padding: 8px; border: 1px solid #ddd; border-radius: 4px; width: 80px; }
button { padding: 10px 20px; background: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
.result { background: #f9f9f9; padding: 20px; border-radius: 8px; text-align: center; }
.password { font-family: monospace; font-size: 20px; word-break: break-all; }
</style>