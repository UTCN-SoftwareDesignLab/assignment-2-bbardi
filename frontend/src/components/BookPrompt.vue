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
                  v-model="editedBook.title"
                  label="Title"
                  :rules="[rules.required, rules.string]"
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-text-field
                  v-model="editedBook.author"
                  label="Author"
                  :rules="[rules.required, rules.string]"
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-text-field
                  v-model="editedBook.price"
                  label="Price"
                  :rules="[rules.required, rules.float]"
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-text-field
                  v-model="editedBook.quantity"
                  label="Quantity"
                  :rules="[rules.required, rules.numeric]"
                ></v-text-field>
              </v-col>
              <v-col cols="24" sm="12" md="8">
                <v-text-field
                  v-model="editedBook.description"
                  label="Description"
                  :rules="[rules.required]"
                ></v-text-field>
              </v-col>
              <v-col cols="24" sm="12" md="8">
                <v-autocomplete
                  solo
                  :items="genres"
                  :item-text="getGenre"
                  v-model="editedBook.genre"
                  :rules="[rules.required]"
                >
                </v-autocomplete>
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
  name: "BookPrompt",
  props: {
    openDialog: Boolean,
    editedBook: Object,
    genres: Array,
  },
  data: () => ({
    rules: {
      required: (value) => !!value || "Required.",
      numeric: (value) => /^\d+$/.test(value) || "Must be an positive integer",
      float: (value) => /^\d*\.?\d*$/.test(value) || "Must be a number",
      string: (value) =>
        /^[A-Z].+$/.test(value) || "Must start with a capital letter",
    },
    valid: true,
  }),
  computed: {
    formTitle() {
      return !this.editedBook.id ? "New Book" : "Edit Book";
    },
  },
  methods: {
    getGenre(genre) {
      return api.genre.getGenre(genre);
    },
    close() {
      this.$emit("refresh");
    },

    save() {
      if (!this.editedBook.id) {
        api.books.createBook(this.editedBook).then(() => this.$emit("refresh"));
      } else {
        api.books.editBook(this.editedBook).then(() => this.$emit("refresh"));
      }
    },
  },
};
</script>
