<template>
  <v-app-bar image="https://media0.giphy.com/media/v1.Y2lkPTc5MGI3NjExbHp5NTVuODBwZ3gyYjM1MTVmcnd4bzQ3dHY5ZjkzZXZkMjM1eXcwMSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/tzIOQszke4Zt6/giphy.gif">
    <v-app-bar-nav-icon @click="$emit('toggle-drawer')"></v-app-bar-nav-icon>
    <v-toolbar-title>Web</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-btn icon to="/">
      <v-icon>mdi-export</v-icon>
    </v-btn>
  </v-app-bar>
</template>