<template>
  <v-dialog persistent :value="openDialog" max-width="500px">
    <v-form
      lazy-validation
      ref="form"
      v-model="valid">
      <v-card>
        <v-card-title>
          <span class="headline">{{ formTitle }}</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12" sm="6" md="4">
                <v-text-field
                  v-model="editedGenre.genre"
                  label="Title"
                  :rules="[rules.required]"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="close"> Cancel </v-btn>
          <v-btn color="blue darken-1" text @click="save" :disabled="!valid"> Save </v-btn>
        </v-card-actions>
      </v-card>
    </v-form>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "GenrePrompt",
  props: {
    openDialog: Boolean,
    editedGenre: Object,
  },
  data: () => ({
    valid: true,
    rules: {
      required: (value) => !!value || "Required.",
    },
  }),
  computed: {
    formTitle() {
      return !this.editedGenre.id ? "New Genre" : "Edit Genre";
    },
  },
  methods: {
    close() {
      this.$emit("refresh");
    },

    save() {
      if (!this.editedGenre.id) {
        api.genre
          .createGenre(this.editedGenre)
          .then(() => this.$emit("refresh"));
      } else {
        api.genre.editGenre(this.editedGenre).then(() => this.$emit("refresh"));
      }
    },
  },
};
</script>
