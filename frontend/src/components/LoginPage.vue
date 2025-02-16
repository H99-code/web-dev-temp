<template>
  <v-container fill-height>
    <v-row class="d-flex justify-center" :style="{ marginTop: '10vh' }">
      <v-col xs="12" sm="8" md="6" lg="4" xl="3" class="d-flex justify-center">
        <v-card elevation="22" class="gif-background" width="100%" color="red">
          <v-col>
            <v-text-field
                v-model="username"
                label="Username"
                :rules="usernameRules"
                variant="solo-filled"
            ></v-text-field>
          </v-col>

          <v-col>
            <v-text-field
                v-model="password"
                label="Password"
                :rules="passwordRules"
                variant="solo-filled"
                type="password"
            ></v-text-field>
          </v-col>

          <v-alert v-if="loginError" type="error" class="mx-4">
            Falscher Benutzername oder Passwort!
          </v-alert>

          <v-alert v-if="isLocked" type="warning" class="mx-4">
            Zu viele Fehlversuche! Warte 1 Minute.
          </v-alert>

          <v-row>
            <v-col>
              <v-btn color="green" @click="goToRegister">Register</v-btn>
            </v-col>
            <v-col class="d-flex justify-end">
              <v-btn :disabled="!username || !password || isLocked" color="blue" @click="handleLogin">
                Login
              </v-btn>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { users } from "@/data/userData";

export default {
  data() {
    return {
      username: '',
      password: '',
      failedAttempts: 0,
      isLocked: false,
      loginError: false,
      usernameRules: [
        v => !!v || 'Username is required',
        v => (v && v.length >= 3) || 'Username must be länger als 3 Zeichen'
      ],
      passwordRules: [
        v => !!v || 'Password is required',
        v => (v && v.length >= 3) || 'Password must be länger als 3 Zeichen'
      ]
    };
  },
  created() {

    const lockData = localStorage.getItem('lockStatus');
    if (lockData) {
      const {timestamp, locked} = JSON.parse(lockData);
      const elapsedTime = Date.now() - timestamp;
      if (locked && elapsedTime < 60000) {
        this.isLocked = true;
        setTimeout(() => this.resetLock(), 60000 - elapsedTime);
      } else {
        localStorage.removeItem('lockStatus');
      }
    }
  },
  methods: {
    goToRegister() {
      this.$router.push("/register")
    },
    handleLogin() {
      if (this.isLocked) {
        alert("Zu viele Versuche! Warte 1 Minute.");
        return;
      }

      const user = users.find(u => u.username === this.username && u.password === this.password);

      if (user) {
        this.failedAttempts = 0;
        this.loginError = false;
        localStorage.removeItem('lockStatus');


        localStorage.setItem('userId', user.id);


        if (user.role === "admin") {
          this.$router.push('/home');
        } else {
          this.$router.push('/home');
        }
      } else {
        this.failedAttempts++;
        this.loginError = true;

        if (this.failedAttempts >= 3) {
          this.isLocked = true;
          alert('Zu viele Fehlversuche! Dein Konto ist für 1 Minute gesperrt.');


          localStorage.setItem('lockStatus', JSON.stringify({timestamp: Date.now(), locked: true}));

          setTimeout(() => this.resetLock(), 60000);
        }
      }
    }
  }
}
</script>

<style scoped>
.gif-background {
  background-image: url('https://media3.giphy.com/media/v1.Y2lkPTc5MGI3NjExbTB0aGdqMmhvMTFyaHc0NzF0anNnYWQxbGl6a2UzNngyMDFkNWw4MSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/p4sfWzAfXm04LkNFsf/giphy.gif');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  height: 100%;
  padding: 20px;
}
</style>
