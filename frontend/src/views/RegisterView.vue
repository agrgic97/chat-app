<template>
  <div class="auth-page">
    <form class="auth-form" @submit.prevent="onSubmit">
      <h1>Registrieren</h1>
      <label>
        Username
        <input v-model="username" required />
      </label>
      <label>
        Anzeigename
        <input v-model="displayName" />
      </label>
      <label>
        Password
        <input v-model="password" type="password" required minlength="6" />
      </label>
      <p v-if="error" class="error">{{ error }}</p>
      <button type="submit" :disabled="loading">Registrieren</button>
      <p>Schon ein Konto? <router-link to="/login">Login</router-link></p>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const username = ref('')
const displayName = ref('')
const password = ref('')
const error = ref('')
const loading = ref(false)
const router = useRouter()
const auth = useAuthStore()

async function onSubmit() {
  error.value = ''
  loading.value = true
  try {
    await auth.register(username.value, password.value, displayName.value)
    router.push('/chat')
  } catch (e: any) {
    error.value = e?.response?.data?.error || 'Registrierung fehlgeschlagen'
  } finally {
    loading.value = false
  }
}
</script>
