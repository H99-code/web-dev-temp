<template>
  <v-container class="chat-container">
    <v-card elevation="14" height="400px">
      <v-card-title class="chat-header">Santa Claus Chat</v-card-title>
      <v-card-text class="chat-messages" ref="chatMessages">
        <div
            v-for="(msg, index) in messages"
            :key="index"
            :class="{'my-message': msg.isMine, 'received-message': !msg.isMine}"
            ref="messageItems"
        >
          <span>{{ msg.text }}</span>
        </div>
      </v-card-text>
      <v-card-actions class="chat-input">
        <v-text-field
            variant="outlined"
            v-model="newMessage"
            label="Nachricht..."
            outlined
            dense
            @keyup.enter="sendMessage"
            class="flex-grow-1"
        ></v-text-field>
        <v-btn variant="text" color="primary" @click="sendMessage">Senden</v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      newMessage: "",
      messages: [{ text: "Willkommen Ho Ho Ho!", isMine: false }],
    };
  },
  methods: {
    sendMessage() {
      if (this.newMessage.trim() !== "") {
        const userMessage = this.newMessage;

        this.messages.push({text: this.newMessage, isMine: true});
        this.newMessage = "";


        this.$nextTick(() => {
          this.scrollToBottom();
        });
        if (userMessage.toLowerCase() === "hello") {
          this.messages.push({text: "Hallo!", isMine: false});
        } else {
          this.messages.push({text: "Gerade keine Zeit sorry!", isMine: false})
        }
        const reminderDate = new Date('2025-02-16T22:10:00'); //(JJJJ-MM-TTTHH:MM:SS)
        const today = new Date();


        if (reminderDate.getFullYear() === today.getFullYear() &&
            reminderDate.getMonth() === today.getMonth() &&
            reminderDate.getDate() === today.getDate() &&
            reminderDate.getHours() === today.getHours() &&
            reminderDate.getMinutes() === today.getMinutes() &&
            reminderDate.getSeconds() === today.getSeconds()) {
          this.messages.push({text: "Vergiss deine Geschenke nicht", isMine: false});
        }
      }
    },
    scrollToBottom() {
      this.$nextTick(() => {
        setTimeout(() => {
          const chatBox = this.$refs.chatMessages;
          const messages = this.$refs.messageItems;
          if (chatBox && messages && messages.length) {
            messages[messages.length - 1].scrollIntoView({ behavior: "smooth" });
          }
        }, );
      });
    },
  },
};
</script>

<style scoped>
.chat-header {
  background-color: rebeccapurple;
  color: white;
  padding: 10px;
  font-weight: bold;
  text-align: center;
}

.chat-messages {
  height: 250px;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  padding: 10px;
}

.my-message, .received-message {
  max-width: 70%;
  padding: 8px 12px;
  margin: 5px;
  border-radius: 18px;
  word-wrap: break-word;
}

.my-message {
  background-color: #2582d3;
  color: white;
  align-self: flex-end;
  margin-left: auto;
}

.received-message {
  background-color: #bf4343;
  align-self: flex-start;
}
</style>
