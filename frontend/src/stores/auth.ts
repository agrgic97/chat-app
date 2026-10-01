import { defineStore } from 'pinia'
import api from '@/services/api'
import type { AuthResponse } from '@/types'

interface AuthState {
  token: string | null
  username: string | null
  displayName: string | null
}

export const useAuthStore = defineStore('auth', {
  state: (): AuthState => ({
    token: localStorage.getItem('chat_token'),
    username: localStorage.getItem('chat_username'),
    displayName: localStorage.getItem('chat_display_name')
  }),
  getters: {
    isAuthenticated: (state) => !!state.token
  },
  actions: {
    async login(username: string, password: string) {
      const { data } = await api.post<AuthResponse>('/auth/login', { username, password })
      this.setSession(data)
    },
    async register(username: string, password: string, displayName: string) {
      const { data } = await api.post<AuthResponse>('/auth/register', { username, password, displayName })
      this.setSession(data)
    },
    setSession(data: AuthResponse) {
      this.token = data.token
      this.username = data.username
      this.displayName = data.displayName
      localStorage.setItem('chat_token', data.token)
      localStorage.setItem('chat_username', data.username)
      localStorage.setItem('chat_display_name', data.displayName)
    },
    logout() {
      this.token = null
      this.username = null
      this.displayName = null
      localStorage.removeItem('chat_token')
      localStorage.removeItem('chat_username')
      localStorage.removeItem('chat_display_name')
    }
  }
})
