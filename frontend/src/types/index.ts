export interface UserDto {
  id: number
  username: string
  displayName: string
}

export interface MessageDto {
  id: number
  from: string
  to: string
  content: string
  sentAt: string
}

export interface AuthResponse {
  token: string
  username: string
  displayName: string
}
