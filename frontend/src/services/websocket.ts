import { Client, type IMessage } from '@stomp/stompjs'
import SockJS from 'sockjs-client'
import type { MessageDto } from '@/types'

type MessageHandler = (message: MessageDto) => void

export class ChatSocket {
  private client: Client
  private handlers: MessageHandler[] = []

  constructor(token: string) {
    this.client = new Client({
      webSocketFactory: () => new SockJS(import.meta.env.VITE_WS_URL || '/ws'),
      connectHeaders: {
        Authorization: `Bearer ${token}`
      },
      reconnectDelay: 5000,
      onConnect: () => {
        this.client.subscribe('/user/queue/messages', (frame: IMessage) => {
          const payload: MessageDto = JSON.parse(frame.body)
          this.handlers.forEach((handler) => handler(payload))
        })
      }
    })
  }

  connect() {
    this.client.activate()
  }

  disconnect() {
    this.client.deactivate()
  }

  onMessage(handler: MessageHandler) {
    this.handlers.push(handler)
  }

  send(to: string, content: string) {
    this.client.publish({
      destination: '/app/chat.send',
      body: JSON.stringify({ to, content })
    })
  }
}
