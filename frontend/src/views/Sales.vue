<template>
  <v-container>
    <v-card justify-center flat class="pa-4">
      <v-card-title>
        Books
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
            {{item.description}}
          </td>
        </template>
        <template v-slot:[`item.actions`]="{item}">
          <v-btn
              small
              @click="sellBook(item)"
          >
            Sell
          </v-btn>
        </template>>
      </v-data-table>
    </v-card>
  </v-container>
</template>

<script>
import api from "../api"

export default {
  name: "Sales",
  data: () => ({
    search: "",
    expanded: [],
    bookHeader: [
      {
        text: "Title",
        sortable: false,
        align: 'start',
        value: 'title',
      },
      {
        text: "Author",
        sortable: false,
        value: "author",
      },
      {
        text: "Stock",
        value: "quantity",
      },
      {
        text: "Price",
        value: "price"
      },
      {
        text: "Genres",
        value: "genre",
        sortable: false
      },
      {
        text: "Actions",
        value: 'actions',
        sortable: false,
      },
      {
        text: '',
        value: 'data-table-expand'
      }
    ],
    items: [],
  }),
  methods:{
    async refreshList(){
      this.items = await api.books.fetchAll()
    },
    async sellBook(item){
      await api.books.sellBook(item);
      await this.refreshList();
    },
  },
  created() {
    this.refreshList();
  },
}
</script>