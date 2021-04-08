import fileDownload from "js-file-download";
import authHeader, { BASE_URL, HTTP } from "../http";

export default{
    getCSVReport(){
        HTTP.get(BASE_URL + "/bookstore/books/report/CSV",{headers: authHeader(),responseType: 'blob'}).then(
            (response) =>{
                fileDownload(response.data,"report.csv");
            }
        )
    },
    getPDFReport(){
        HTTP.get(BASE_URL + "/bookstore/books/report/PDF",{headers: authHeader(),responseType: 'blob'}).then(
            (response) =>{
                fileDownload(response.data,"report.pdf");
            }
        )
    }
};