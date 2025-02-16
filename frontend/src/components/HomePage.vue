<template>
  <div>
    <TopBar @toggle-drawer="drawer = !drawer" />
    <NavigationDrawer v-model="drawer"></NavigationDrawer>
    <!--<WelcomeDialog :dialog="openWelcomeDialog"></WelcomeDialog> -->

    <!-- Main Content -->
    <v-container>
      <v-row>
        <v-col cols="8">
          <v-card class="elevation-13">
            <v-tabs v-model="activeTab">
              <!-- Dynamische Tabs für Benutzer -->
              <v-tab v-for="user in users" :key="user.id">
                {{ user.personalInfo.firstName }}
              </v-tab>
            </v-tabs>
            <v-table>
              <thead>
              <tr>
                <th class="text-left">Item</th>
                <th class="text-left">Price</th>
                <th class="text-left">Link</th>
                <th class="text-left">Type</th>
                <th class="text-left">Status</th>
                <th class="text-left">Select</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(item, index) in filteredItems" :key="index">
                <td>{{ item.item }}</td>
                <td>{{ item.price[0] }} - {{ item.price[1] }} €</td>
                <td><a :href="item.link" target="_blank">{{ item.link }}</a></td>
                <td>{{ item.type }}</td>
                <td><v-chip>{{ item.status }}</v-chip></td>
                <td>
                  <v-checkbox v-model="selectedItems" :value="index"></v-checkbox>
                </td>
              </tr>
              </tbody>
            </v-table>
          </v-card>
        </v-col>

        <!-- Actions Panel -->
        <v-col cols="4">
          <v-card class="pa-4" elevation="13">
            <v-card-title>Actions</v-card-title>
            <v-card-text>
              <v-btn color="orange" block class="mb-2" @click="openAddItemDialog = true">
                ADD ITEM
              </v-btn>
              <v-btn color="red" block class="mb-2" @click="deleteSelectedItems">
                DELETE SELECTED
              </v-btn>
              <v-btn color="green" block class="mb-2" @click="buySelectedItems">
                BUY SELECTED
              </v-btn>
            </v-card-text>
          </v-card>
          <v-col cols="6"></v-col>
          <v-card class="pa-4">
            <ChatView ref="chatComponent" />
          </v-card>
        </v-col>
      </v-row>
    </v-container>

    <!-- Dialog: Add Item -->
    <v-dialog v-model="openAddItemDialog" max-width="500px">
      <v-card elevation="12" class="pa-4">
        <v-card-title class="text-center">Add Item</v-card-title>
        <v-card-text>
          <v-text-field v-model="newItem.item" label="Item" :rules="itemRules"></v-text-field>
          <v-text-field v-model="newItem.link" label="Link" :rules="linkRules"></v-text-field>

          <v-range-slider
              v-model="newItem.price"
              :min="5"
              :max="45"
              step="5"
              thumb-label="always"
          ></v-range-slider>

          <v-autocomplete
              v-model="newItem.type"
              clearable
              label="Type"
              :items="['Type 1', 'Type 2']"
              :rules="typeRules"
          ></v-autocomplete>
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn color="red" @click="openAddItemDialog = false">Cancel</v-btn>
          <v-btn color="green" @click="addItem">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import ChatView from "@/components/ChatView.vue";
import TopBar from "@/components/TopBar.vue";
import NavigationDrawer from "@/components/NavigationDrawer.vue";
//import WelcomeDialog from "@/components/WelcomeDialog.vue";
import { users } from "@/data/userData";

export default {
  data() {
    return {
      activeTab: 0, // Der aktuelle Benutzer-Tab, basierend auf Index
      openAddItemDialog: false,
      newItem: { item: "", price: [5, 30], link: "", type: "", status: "New" },
      selectedItems: [],
      users, // Hier sind alle Benutzer gespeichert
      openWelcomeDialog: true,
      drawer: true,
      itemRules: [v => !!v || "Item Name is required"],
      typeRules: [v => !!v || "Item Type is required"],
      linkRules: [v => !!v || "Item Link is required"],
    };
  },
  components: {
    //WelcomeDialog,
    NavigationDrawer,
    ChatView,
    TopBar,
  },
  computed: {
    // Dynamische Filterung der Items basierend auf dem aktuellen Tab (Benutzer)
    filteredItems() {
      const currentUser = this.users[this.activeTab];
      return currentUser.items || []; // Es wird das 'items' Feld des aktuellen Benutzers verwendet
    }
  },
  methods: {
    // Methode zum Hinzufügen eines neuen Items
    addItem() {
      const currentUser = this.users[this.activeTab];
      currentUser.items.push({ ...this.newItem }); // Item wird dem aktuellen Benutzer zugewiesen
      this.resetNewItem();
      this.openAddItemDialog = false;
    },
    // Methode zum Löschen der ausgewählten Items
    deleteSelectedItems() {
      const currentUser = this.users[this.activeTab];
      currentUser.items = currentUser.items.filter((_, index) => !this.selectedItems.includes(index));
      this.selectedItems = [];
    },
    // Methode zum Markieren der ausgewählten Items als 'Bought'
    buySelectedItems() {
      const currentUser = this.users[this.activeTab];
      currentUser.items.forEach((item, index) => {
        if (this.selectedItems.includes(index)) {
          item.status = "Bought"; // Status ändern, wenn das Item ausgewählt wurde
        }
      });
      this.selectedItems = [];
    },
    // Rücksetzen des Formulars für ein neues Item
    resetNewItem() {
      this.newItem = { item: "", price: [], link: "", type: "", status: "" };
    }
  }
}
</script>
