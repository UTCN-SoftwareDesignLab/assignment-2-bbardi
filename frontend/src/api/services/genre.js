import authHeader, { BASE_URL, HTTP } from "../http";

export default {
    fetchAll() {
        return HTTP.get(BASE_URL + "/bookstore/genres/findall", { headers: authHeader() }).then(
            (response) => {
                return response.data
            }
        )
    },
    getGenre(genre) {
        return genre.genre
    },
    createGenre(genre) {
        return HTTP.post(BASE_URL + "/bookstore/genres/create", genre, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
    editGenre(genre) {
        return HTTP.patch(BASE_URL + "/bookstore/genres/edit", genre, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
    deleteGenre(genre) {
        return HTTP.delete(BASE_URL + "/bookstore/genres/delete", { data: genre, headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
}