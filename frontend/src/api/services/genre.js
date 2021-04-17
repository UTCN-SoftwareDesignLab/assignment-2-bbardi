import authHeader, { BASE_URL, HTTP } from "../http";

export default {
    fetchAll() {
        return HTTP.get(BASE_URL + "/bookstore/genres", { headers: authHeader() }).then(
            (response) => {
                return response.data
            }
        )
    },
    createGenre(genre) {
        return HTTP.post(BASE_URL + "/bookstore/genres", genre, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
    editGenre(genre) {
        return HTTP.patch(BASE_URL + `/bookstore/genres/${genre.id}`, genre, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
    deleteGenre(genre) {
        return HTTP.delete(BASE_URL + `/bookstore/genres/${genre.id}`, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
}