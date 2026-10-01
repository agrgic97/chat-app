<template>
  <div class="chat-window">
    <header>{{ contact.displayName }}</header>
    <div class="messages" ref="scrollArea">
      <div
        v-for="message in messages"
        :key="message.id"
        :class="['message', message.from === contact.username ? 'incoming' : 'outgoing']"
      >
        {{ message.content }}
      </div>
    </div>
    <form class="composer" @submit.prevent="onSend">
      <input v-model="draft" placeholder="Nachricht schreiben..." />
      <button type="submit">Senden</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, nextTick } from 'vue'
import type { UserDto, MessageDto } from '@/types'

const props = defineProps<{
  contact: UserDto
  messages: MessageDto[]
}>()
const emit = defineEmits<{ send: [content: string] }>()

const draft = ref('')
const scrollArea = ref<HTMLElement | null>(null)

watch(
  () => props.messages.length,
  async () => {
    await nextTick()
    scrollArea.value?.scrollTo({ top: scrollArea.value.scrollHeight })
  }
)

function onSend() {
  if (!draft.value.trim()) return
  emit('send', draft.value.trim())
  draft.value = ''
}
</script>
