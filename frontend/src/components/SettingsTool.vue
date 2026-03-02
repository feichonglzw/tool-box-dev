<template>
  <div class="tool">
    <h2>⚙️ API 设置</h2>
    <div class="form">
      <div class="field">
        <label>选择服务商：</label>
        <select v-model="provider" @change="updateModels">
          <option value="openai">OpenAI</option>
          <option value="zhipu">智谱AI (ChatGLM)</option>
          <option value="baidu">百度 (ERNIE)</option>
          <option value="aliyun">阿里云 (Qwen)</option>
          <option value="moonshot">月之暗面 (Kimi)</option>
          <option value="01ai">零一万物 (Yi)</option>
        </select>
      </div>
      <div class="field">
        <label>API Key：</label>
        <input type="password" v-model="api_key" placeholder="请输入 API Key">
      </div>
      <div class="field">
        <label>模型：</label>
        <select v-model="model">
          <option v-for="m in models" :key="m" :value="m">{{ m }}</option>
        </select>
      </div>
      <button @click="save">保存设置</button>
    </div>
    <div class="result" v-if="message">
      <p :class="success ? 'success' : 'error'">{{ message }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const provider = ref('openai')
const api_key = ref('')
const model = ref('')
const message = ref('')
const success = ref(false)

const modelOptions = {
  openai: ['gpt-3.5-turbo', 'gpt-4', 'gpt-4o', 'gpt-4-turbo'],
  zhipu: ['glm-4', 'glm-4-flash', 'glm-4-plus', 'glm-3-turbo'],
  baidu: ['ernie-lite-8k', 'ernie-speed-8k', 'ernie-4.0-8k'],
  aliyun: ['qwen-turbo', 'qwen-plus', 'qwen-max'],
  moonshot: ['moonshot-v1-8k', 'moonshot-v1-32k', 'moonshot-v1-128k'],
  '01ai': ['yi-spark', 'yi-plus', 'yi-large']
}

const models = ref(modelOptions.openai)

const updateModels = () => {
  models.value = modelOptions[provider.value] || []
  model.value = models.value[0]
}

onMounted(async () => {
  try {
    const res = await axios.get('/api/settings')
    if (res.data.settings) {
      provider.value = res.data.settings.ai_provider || 'openai'
      api_key.value = res.data.settings.ai_api_key || ''
      model.value = res.data.settings.ai_model || ''
      updateModels()
    }
  } catch (e) {}
})

const save = async () => {
  try {
    await axios.post('/api/settings', {
      ai_provider: provider.value,
      ai_api_key: api_key.value,
      ai_model: model.value
    })
    message.value = '保存成功！'
    success.value = true
  } catch (e) {
    message.value = '保存失败：' + e.message
    success.value = false
  }
}
</script>

<style scoped>
.form { display: flex; flex-direction: column; gap: 15px; margin: 20px 0; }
.field { display: flex; flex-direction: column; gap: 5px; }
.field label { font-weight: bold; }
input, select { padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
button { padding: 10px 20px; background: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }
.result { margin-top: 15px; padding: 10px; border-radius: 4px; }
.success { color: green; }
.error { color: red; }
</style>