<template>
  <div class="container">
    <header>
      <h1>🧰 ToolBox</h1>
      <p>Developer Tools Collection</p>
    </header>
    
    <nav>
      <button v-for="tab in tabs" :key="tab.key" :class="{ active: currentTab === tab.key }" @click="currentTab = tab.key">
        {{ tab.icon }} {{ tab.name }}
      </button>
    </nav>
    
    <main>
      <UuidTool v-if="currentTab === 'uuid'" />
      <TimestampTool v-if="currentTab === 'timestamp'" />
      <PasswordTool v-if="currentTab === 'password'" />
      <Base64Tool v-if="currentTab === 'base64'" />
      <JsonTool v-if="currentTab === 'json'" />
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import UuidTool from './components/UuidTool.vue'
import TimestampTool from './components/TimestampTool.vue'
import PasswordTool from './components/PasswordTool.vue'
import Base64Tool from './components/Base64Tool.vue'
import JsonTool from './components/JsonTool.vue'

const currentTab = ref('uuid')

const tabs = [
  { key: 'uuid', name: 'UUID', icon: '🔢' },
  { key: 'timestamp', name: 'Timestamp', icon: '⏰' },
  { key: 'password', name: 'Password', icon: '🔐' },
  { key: 'base64', name: 'Base64', icon: '🖼️' },
  { key: 'json', name: 'JSON', icon: '📝' }
]
</script>

<style>
.container { max-width: 800px; margin: 0 auto; padding: 20px; }
header { text-align: center; margin-bottom: 30px; }
header h1 { font-size: 2.5em; margin-bottom: 10px; }
header p { color: #666; }
nav { display: flex; gap: 10px; margin-bottom: 20px; flex-wrap: wrap; justify-content: center; }
nav button { padding: 10px 20px; border: none; background: #fff; border-radius: 8px; cursor: pointer; font-size: 14px; transition: all 0.2s; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
nav button:hover { transform: translateY(-2px); }
nav button.active { background: #4CAF50; color: white; }
main { background: #fff; border-radius: 12px; padding: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
</style>