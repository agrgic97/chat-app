<template>
  <ul class="contact-list">
    <li
      v-for="contact in contacts"
      :key="contact.id"
      :class="{ active: active?.id === contact.id }"
      @click="$emit('select', contact)"
    >
      {{ contact.displayName }}
    </li>
  </ul>
</template>

<script setup lang="ts">
import type { UserDto } from '@/types'

defineProps<{
  contacts: UserDto[]
  active: UserDto | null
}>()
defineEmits<{ select: [contact: UserDto] }>()
</script>
