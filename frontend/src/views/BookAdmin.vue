<template>
  <v-container>
    <v-card justify-center flat class="pa-4">
      <v-card-title>
        Books
        <v-spacer></v-spacer>
        <v-btn color="primary" dark @click="newBook"> New Book </v-btn>
        <v-spacer></v-spacer>
        <v-btn color="primary" dark @click="reportCSV">
          Out of Stock Report(CSV)
        </v-btn>
        <v-spacer></v-spacer>
        <v-btn color="primary" dark @click="reportPDF">
          Out of Stock Report(PDF)
        </v-btn>
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
        :headers="bookHeader"
        :expanded.sync="expanded"
        :items="items"
        :search="search"
        item-key="id"
        show-expand
        class="elevation-1"
      >
        <template v-slot:expanded-item="{ headers, item }">
          <td :colspan="headers.length">
            {{ item.description }}
          </td>
        </template>
        <template v-slot:[`item.actions`]="{ item }">
          <v-icon small class="mr-2" @click="editBook(item)">
            mdi-pencil
          </v-icon>
          <v-icon small @click="deleteBook(item)"> mdi-delete </v-icon>
        </template>
      </v-data-table>
      <BookPrompt
        :openDialog="openDialog"
        :editedBook="selectedBook"
        :genres="genres"
        @refresh="refreshList"
      >
      </BookPrompt>
    </v-card>
  </v-container>
</template>

<script>
import api from "../api";
import BookPrompt from "../components/BookPrompt";

export default {
  name: "BookAdmin",
  components: { BookPrompt },
  data: () => ({
    openDialog: false,
    genres: [],
    selectedBook: {},
    search: "",
    expanded: [],
    bookHeader: [
      {
        text: "Title",
        align: "start",
        value: "title",
      },
      {
        text: "Author",
        value: "author",
      },
      {
        text: "Stock",
        value: "quantity",
      },
      {
        text: "Price",
        value: "price",
      },
      {
        text: "Genres",
        value: "genre",
        sortable: false,
      },
      {
        text: "Actions",
        value: "actions",
        sortable: false,
      },
      {
        text: "",
        value: "data-table-expand",
      },
    ],
    items: [],
  }),
  methods: {
    async refreshList() {
      this.openDialog = false;
      this.genres = await api.genre.fetchAll();
      this.items = await api.books.fetchAll();
    },
    async deleteBook(book) {
      await api.books.deleteBook(book);
      await this.refreshList();
    },
    editBook(book) {
      this.selectedBook = book;
      this.openDialog = true;
    },
    newBook() {
      this.selectedBook = {};
      this.openDialog = true;
    },
    getGenre(genre) {
      return api.genre.getGenre(genre);
    },
    reportCSV() {
      api.reports.getCSVReport();
    },
    reportPDF() {
      api.reports.getPDFReport();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>