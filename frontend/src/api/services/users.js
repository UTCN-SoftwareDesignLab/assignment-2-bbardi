import authHeader, { BASE_URL, HTTP } from "../http";

export default {
    fetchAll() {
        return HTTP.get(BASE_URL + "/bookstore/users/findall", { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
    deleteUser(user) {
        return HTTP.delete(BASE_URL + "/bookstore/users/delete", { data: user, headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
    createUser(user) {
        return HTTP.post(BASE_URL + "/bookstore/users/create", user, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
    editUser(user) {
        return HTTP.patch(BASE_URL + "/bookstore/users/edit", user, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
    parseRoles(user) {
        return user.roles.join(",");
    }
};