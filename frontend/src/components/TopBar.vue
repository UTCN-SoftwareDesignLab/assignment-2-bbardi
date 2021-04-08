<template>
  <v-card color="grey lighten-4" flat tile>
    <v-toolbar>
      <v-toolbar-title>Bookstore</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items class="hidden-sm-and-down">
        <v-btn @click="goToSales">Sales</v-btn>
        <v-btn v-show="isAdmin" @click="goToUsers">Users</v-btn>
        <v-btn v-show="isAdmin" @click="goToBooks">Books</v-btn>
        <v-btn v-show="isAdmin" @click="goToGenres">Genres</v-btn>
      </v-toolbar-items>
      <v-spacer></v-spacer>
      <v-btn icon @click="logout">
        <v-icon>logout</v-icon>
      </v-btn>
    </v-toolbar>
  </v-card>
</template>

<script>
import router from "../router";
export default {
  name: "TopBar",
  methods: {
    goToSales(){
      router.push("/sales");
    },
    goToUsers(){
      router.push("/users");
    },
    goToBooks(){
      router.push("/books");
    },
    goToGenres(){
      router.push("/genres");
    },
    logout() {
      this.$store.dispatch("auth/logout");
      router.push("/");
    },
  },
  computed: {
    isAdmin: function (){
      return this.$store.getters["auth/isAdmin"]
    }
  }
};
</script>

<style scoped></style>