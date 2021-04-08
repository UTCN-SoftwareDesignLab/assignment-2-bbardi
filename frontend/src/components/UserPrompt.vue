<template>
  <v-dialog persistent :value="openDialog" max-width="500px">
    <v-form lazy-validation v-model="valid" ref="form">
      <v-card>
        <v-card-title>
          <span class="headline">{{ formTitle }}</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12" sm="6" md="4">
                <v-text-field
                  v-model="editedUser.email"
                  label="Email Address"
                  :rules="[rules.required, rules.email]"
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-text-field
                  v-model="editedUser.username"
                  label="Username"
                  :rules="[rules.required]"
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-text-field
                  v-model="editedUser.password"
                  label="Password"
                  type="password"
                  :rules="isRequired"
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-checkbox
                  v-model="editedUser.roles"
                  label="Admin"
                  value="ADMIN"
                  :rules="[rules.selectedone]"
                ></v-checkbox>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-checkbox
                  v-model="editedUser.roles"
                  label="Employee"
                  value="EMPLOYEE"
                  :rules="[rules.selectedone]"
                ></v-checkbox>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="close"> Cancel </v-btn>
          <v-btn color="blue darken-1" text @click="save" :disabled="!valid">
            Save
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-form>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "UserPrompt",
  props: {
    openDialog: Boolean,
    editedUser: Object,
    genres: Array,
  },
  data: () => ({
    rules: {
      required: (value) => !!value || "Required.",
      email: (value) => /\S+@\S+\.\S+/.test(value) || "Must be a valid email.",
      selectedone: (value) => (!!value && value.length > 0) || "At least one must be selected.",
    },
    valid: true,
  }),
  computed: {
    isRequired() {
      return !this.editedUser.id ? [this.rules.required] : [];
    },
    formTitle() {
      return !this.editedUser.id ? "New User" : "Edit User";
    },
  },
  methods: {
    close() {
      this.$emit("refresh");
    },

    save() {
      if (!this.editedUser.id) {
        api.users.createUser(this.editedUser).then(() => this.$emit("refresh"));
      } else {
        api.users.editUser(this.editedUser).then(() => this.$emit("refresh"));
      }
    },
  },
};
</script>
