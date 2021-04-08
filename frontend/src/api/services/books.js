import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  fetchAll() {
    return HTTP.get(BASE_URL + "/bookstore/books/findall", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  deleteBook(book) {
    return HTTP.delete(BASE_URL + "/bookstore/books/delete", { data: book, headers: authHeader() }).then(
      (response) => {
        return response.data;
      },
      (error) => {
        alert(error.response.data.message);
      }
    );
  },
  sellBook(book) {
    return HTTP.post(BASE_URL + "/bookstore/books/sell", book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      },
      (error) => {
        alert(error.response.data.message);
      }
    );
  },
  createBook(book) {
    return HTTP.post(BASE_URL + "/bookstore/books/create", book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      },
      (error) => {
        alert(error.response.data.message);
      }
    );
  },
  editBook(book) {
    return HTTP.patch(BASE_URL + "/bookstore/books/edit", book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      },
      (error) => {
        alert(error.response.data.message);
      }
    );
  }
};