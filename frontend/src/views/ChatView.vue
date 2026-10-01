<template>
  <div class="chat-page">
    <aside class="sidebar">
      <div class="sidebar-header">
        <span>{{ auth.displayName }}</span>
        <button @click="onLogout">Logout</button>
      </div>
      <ContactList :contacts="contacts" :active="activeContact" @select="selectContact" />
    </aside>
    <main class="chat-main">
      <ChatWindow
        v-if="activeContact"
        :contact="activeContact"
        :messages="messages"
        @send="sendMessage"
      />
      <div v-else class="empty-state">Wähle einen Kontakt aus.</div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { onMounted, onBeforeUnmount, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import api from '@/services/api'
import { ChatSocket } from '@/services/websocket'
import type { UserDto, MessageDto } from '@/types'
import ContactList from '@/components/ContactList.vue'
import ChatWindow from '@/components/ChatWindow.vue'

const auth = useAuthStore()
const router = useRouter()

const contacts = ref<UserDto[]>([])
const activeContact = ref<UserDto | null>(null)
const messages = ref<MessageDto[]>([])
let socket: ChatSocket | null = null

onMounted(async () => {
  const { data } = await api.get<UserDto[]>('/users')
  contacts.value = data

  socket = new ChatSocket(auth.token!)
  socket.onMessage((message) => {
    if (
      activeContact.value &&
      (message.from === activeContact.value.username || message.to === activeContact.value.username)
    ) {
      messages.value.push(message)
    }
  })
  socket.connect()
})

onBeforeUnmount(() => {
  socket?.disconnect()
})

async function selectContact(contact: UserDto) {
  activeContact.value = contact
  const { data } = await api.get<MessageDto[]>(`/messages/${contact.username}`)
  messages.value = data
}

function sendMessage(content: string) {
  if (!activeContact.value) return
  socket?.send(activeContact.value.username, content)
}

function onLogout() {
  auth.logout()
  router.push('/login')
}
</script>
