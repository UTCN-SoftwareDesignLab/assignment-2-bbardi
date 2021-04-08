<template>
  <v-container>
    <v-card justify-center flat class="pa-4">
      <v-card-title>
        Genres
        <v-spacer></v-spacer>
        <v-btn color="primary" dark @click="newGenre"> New Genre </v-btn>
        <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
        ></v-text-field>
      </v-card-title>
      <!--<v-toolbar dense floating>
        <v-text-field prepend-icon="mdi-magnify" hide-details single-line>
        </v-text-field>
      </v-toolbar>-->

      <v-data-table
        :headers="genreHeader"
        :items="items"
        :search="search"
        item-key="id"
        class="elevation-1"
      >
        <template v-slot:[`item.actions`]="{ item }">
          <v-icon small class="mr-2" @click="editGenre(item)">
            mdi-pencil
          </v-icon>
          <v-icon small @click="deleteGenre(item)"> mdi-delete </v-icon>
        </template>
      </v-data-table>
      <GenrePrompt
        :openDialog="openDialog"
        :editedGenre="selectedGenre"
        @refresh="refreshList"
      >
      </GenrePrompt>
    </v-card>
  </v-container>
</template>

<script>
import api from "../api";
import GenrePrompt from "../components/GenrePrompt";

export default {
  name: "GenreAdmin",
  components: { GenrePrompt },
  data: () => ({
    openDialog: false,
    selectedGenre: {},
    search: "",
    expanded: [],
    genreHeader: [
      {
        text: "Genre",
        sortable: false,
        align: "start",
        value: "genre",
      },
      {
        text: "Actions",
        value: "actions",
        sortable: false,
      },
    ],
    items: [],
  }),
  methods: {
    async refreshList() {
      this.openDialog = false;
      this.items = await api.genre.fetchAll();
    },
    async deleteGenre(genre) {
      await api.genre.deleteGenre(genre);
      await this.refreshList();
    },
    editGenre(genre) {
      this.selectedGenre = genre;
      this.openDialog = true;
    },
    newGenre() {
      this.selectedGenre = {};
      this.openDialog = true;
    },
  },
  created() {
    this.refreshList();
  },
};
</script>