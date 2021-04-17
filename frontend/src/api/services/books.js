import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  fetchAll() {
    return HTTP.get(BASE_URL + "/bookstore/books", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  deleteBook(book) {
    return HTTP.delete(BASE_URL + `/bookstore/books/${book.id}`, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      },
      (error) => {
        alert(error.response.data.message);
      }
    );
  },
  sellBook(book) {
    return HTTP.post(BASE_URL + `/bookstore/books/${book.id}/sell`,null, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      },
      (error) => {
        alert(error.response.data.message);
      }
    );
  },
  createBook(book) {
    return HTTP.post(BASE_URL + "/bookstore/books", book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      },
      (error) => {
        alert(error.response.data.message);
      }
    );
  },
  editBook(book) {
    return HTTP.patch(BASE_URL + `/bookstore/books/${book.id}`, book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      },
      (error) => {
        alert(error.response.data.message);
      }
    );
  }
};