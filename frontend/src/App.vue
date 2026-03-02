<template>
  <div class="container" :class="layoutClass">
    <header v-if="layout !== 'sidebar'">
      <h1>🧰 ToolBox</h1>
      <p>Developer Tools Collection</p>
      <button class="settings-btn" @click="showSettings = true">⚙️</button>
    </header>
    
    <aside v-if="layout === 'sidebar'" class="sidebar">
      <div class="sidebar-header">
        <h2>🔧</h2>
        <button class="settings-btn" @click="showSettings = true">⚙️</button>
      </div>
      <nav>
        <button v-for="tab in tabs" :key="tab.key" :class="{ active: currentTab === tab.key }" @click="currentTab = tab.key">
          <span class="icon">{{ tab.icon }}</span>
          <span class="name">{{ tab.name }}</span>
        </button>
      </nav>
      <div class="sidebar-footer">
        <span>v1.0</span>
      </div>
    </aside>
    
    <main v-if="layout !== 'sidebar'">
      <nav>
        <button v-for="tab in tabs" :key="tab.key" :class="{ active: currentTab === tab.key }" @click="currentTab = tab.key">
          {{ tab.icon }} {{ tab.name }}
        </button>
      </nav>
      
      <div class="content">
        <UuidTool v-if="currentTab === 'uuid'" />
        <TimestampTool v-if="currentTab === 'timestamp'" />
        <PasswordTool v-if="currentTab === 'password'" />
        <StringTool v-if="currentTab === 'string'" />
        <CronTool v-if="currentTab === 'cron'" />
        <UrlTool v-if="currentTab === 'url'" />
        <RegexTool v-if="currentTab === 'regex'" />
        <Base64Tool v-if="currentTab === 'base64'" />
        <JsonTool v-if="currentTab === 'json'" />
      </div>
    </main>
    
    <main v-if="layout === 'sidebar'" class="sidebar-content">
      <UuidTool v-if="currentTab === 'uuid'" />
      <TimestampTool v-if="currentTab === 'timestamp'" />
      <PasswordTool v-if="currentTab === 'password'" />
      <StringTool v-if="currentTab === 'string'" />
      <CronTool v-if="currentTab === 'cron'" />
      <UrlTool v-if="currentTab === 'url'" />
      <RegexTool v-if="currentTab === 'regex'" />
      <Base64Tool v-if="currentTab === 'base64'" />
      <JsonTool v-if="currentTab === 'json'" />
    </main>
    
    <!-- Settings Modal -->
    <div v-if="showSettings" class="modal" @click.self="showSettings = false">
      <div class="modal-content">
        <h2>⚙️ 设置</h2>
        
        <div class="setting-item">
          <label>布局方式：</label>
          <select v-model="layout" @change="saveLayout">
            <option value="top">顶部导航</option>
            <option value="sidebar">左侧边栏</option>
          </select>
        </div>
        
        <button class="close-btn" @click="showSettings = false">关闭</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import UuidTool from './components/UuidTool.vue'
import TimestampTool from './components/TimestampTool.vue'
import PasswordTool from './components/PasswordTool.vue'
import Base64Tool from './components/Base64Tool.vue'
import JsonTool from './components/JsonTool.vue'
import StringTool from './components/StringTool.vue'
import CronTool from './components/CronTool.vue'
import UrlTool from './components/UrlTool.vue'
import RegexTool from './components/RegexTool.vue'

const currentTab = ref('uuid')
const layout = ref('top')
const showSettings = ref(false)

const layoutClass = computed(() => 'layout-' + layout.value)

const tabs = [
  { key: 'uuid', name: 'UUID', icon: '🔢' },
  { key: 'timestamp', name: 'Timestamp', icon: '⏰' },
  { key: 'password', name: 'Password', icon: '🔐' },
  { key: 'string', name: 'String', icon: '✏️' },
  { key: 'cron', name: 'Cron', icon: '📊' },
  { key: 'url', name: 'URL', icon: '🔗' },
  { key: 'regex', name: 'Regex', icon: '🧪' },
  { key: 'base64', name: 'Base64', icon: '🖼️' },
  { key: 'json', name: 'JSON', icon: '📝' }
]

const saveLayout = () => {
  localStorage.setItem('toolbox_layout', layout.value)
}

onMounted(() => {
  layout.value = localStorage.getItem('toolbox_layout') || 'top'
})
</script>

<style>
* { margin: 0; padding: 0; box-sizing: border-box; }
body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; background: #f5f5f5; }

.container { min-height: 100vh; display: flex; flex-direction: column; }
.container.layout-top { }
.container.layout-sidebar { flex-direction: row; }

/* Header */
header { position: relative; text-align: center; padding: 20px; background: #fff; border-bottom: 1px solid #eee; }
header h1 { font-size: 2em; margin-bottom: 5px; }
header p { color: #666; }
.settings-btn { position: absolute; top: 20px; right: 20px; padding: 8px 15px; font-size: 18px; background: none; border: 1px solid #ddd; border-radius: 8px; cursor: pointer; }
.settings-btn:hover { background: #f5f5f5; }

/* Sidebar */
.sidebar { 
  width: 220px; 
  background: linear-gradient(180deg, #1a1a2e 0%, #16213e 100%); 
  min-height: 100vh; 
  padding: 24px 16px; 
  display: flex; 
  flex-direction: column;
  box-shadow: 2px 0 20px rgba(0,0,0,0.15);
}
.sidebar-header { 
  display: flex; 
  justify-content: space-between; 
  align-items: center; 
  padding: 8px 12px 20px; 
  border-bottom: 1px solid rgba(255,255,255,0.08); 
  margin-bottom: 20px; 
}
.sidebar-header h2 { 
  color: #fff; 
  font-size: 1.4em; 
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 8px;
}
.sidebar-header .settings-btn { 
  background: rgba(255,255,255,0.08); 
  border: none; 
  color: rgba(255,255,255,0.6); 
  padding: 10px; 
  border-radius: 10px; 
  font-size: 16px;
  transition: all 0.2s;
}
.sidebar-header .settings-btn:hover { 
  background: rgba(255,255,255,0.15); 
  color: #fff;
  transform: rotate(45deg);
}
.sidebar nav { display: flex; flex-direction: column; gap: 4px; }
.sidebar nav button { 
  text-align: left; 
  padding: 14px 16px; 
  background: transparent; 
  color: rgba(255,255,255,0.55); 
  border: none; 
  border-radius: 12px; 
  font-size: 14px; 
  font-weight: 500;
  transition: all 0.25s cubic-bezier(0.4, 0, 0.2, 1);
  display: flex;
  align-items: center;
  gap: 14px;
  position: relative;
  overflow: hidden;
}
.sidebar nav button::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 3px;
  height: 0;
  background: #4CAF50;
  border-radius: 0 3px 3px 0;
  transition: height 0.25s ease;
}
.sidebar nav button:hover { 
  background: rgba(255,255,255,0.08); 
  color: #fff;
  padding-left: 20px;
}
.sidebar nav button:hover::before {
  height: 60%;
}
.sidebar nav button.active { 
  background: linear-gradient(135deg, rgba(76, 175, 80, 0.2) 0%, rgba(76, 175, 80, 0.1) 100%);
  color: #4CAF50;
  font-weight: 600;
  border: 1px solid rgba(76, 175, 80, 0.3);
}
.sidebar nav button.active::before {
  height: 70%;
}
.sidebar nav button .icon {
  font-size: 18px;
  width: 24px;
  text-align: center;
  filter: grayscale(20%);
  transition: filter 0.2s;
}
.sidebar nav button:hover .icon,
.sidebar nav button.active .icon {
  filter: grayscale(0%);
}
.sidebar-footer {
  margin-top: auto;
  padding-top: 20px;
  border-top: 1px solid rgba(255,255,255,0.08);
  text-align: center;
}
.sidebar-footer span {
  color: rgba(255,255,255,0.3);
  font-size: 12px;
}

/* Main */
main { flex: 1; padding: 20px; }
main nav { display: flex; gap: 10px; margin-bottom: 20px; flex-wrap: wrap; justify-content: center; }
main nav button { padding: 10px 20px; border: none; background: #fff; border-radius: 8px; cursor: pointer; font-size: 14px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); transition: all 0.2s; }
main nav button:hover { transform: translateY(-2px); }
main nav button.active { background: #4CAF50; color: white; }
.content { background: #fff; border-radius: 12px; padding: 20px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }

.sidebar-content { background: #fff; border-radius: 12px; padding: 20px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }

/* Modal */
.modal { position: fixed; top: 0; left: 0; right: 0; bottom: 0; background: rgba(0,0,0,0.5); display: flex; align-items: center; justify-content: center; z-index: 100; }
.modal-content { background: #fff; padding: 30px; border-radius: 12px; width: 300px; }
.modal-content h2 { margin-bottom: 20px; }
.setting-item { margin-bottom: 20px; }
.setting-item label { display: block; margin-bottom: 5px; font-weight: bold; }
.setting-item select { width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px; }
.close-btn { width: 100%; padding: 10px; background: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }

/* Responsive */
@media (max-width: 768px) {
  .container.layout-sidebar { flex-direction: column; }
  .sidebar { 
    width: 100%; 
    border-right: none; 
    border-bottom: 1px solid rgba(255,255,255,0.1); 
    padding: 12px 16px;
    flex-direction: row;
    align-items: center;
    min-height: auto;
  }
  .sidebar-header { 
    padding: 0; 
    border: none; 
    margin: 0; 
    width: auto;
  }
  .sidebar-header h2 { display: none; }
  .sidebar-header .settings-btn { padding: 8px; }
  .sidebar nav { 
    flex-direction: row; 
    overflow-x: auto; 
    gap: 6px;
    flex: 1;
    padding: 0;
  }
  .sidebar nav button { 
    padding: 10px 14px; 
    font-size: 13px;
    white-space: nowrap;
    border-radius: 20px;
  }
  .sidebar nav button .icon { font-size: 16px; }
  .sidebar nav button .name { display: none; }
  .sidebar nav button::before { display: none; }
  .sidebar nav button.active { border: none; background: rgba(76, 175, 80, 0.15); }
  .sidebar-footer { display: none; }
}
</style>