package edu.bbardisoftwaredesign.bookstore;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String BOOK_STORE = API_PATH + "/bookstore";

    //Paths common for CRUD parts of the project
    public static final String FIND_ALL = "/findall";
    public static final String CREATE = "/create";
    public static final String DELETE = "/delete";
    public static final String EDIT = "/edit";

    //users paths
    public static final String USERS = BOOK_STORE + "/users";

    //books paths
    public static final String BOOKS = BOOK_STORE + "/books";
    public static final String SELL_BOOK = "/sell";
    public static final String EXPORT_REPORT = "/report/{type}";
    //genre paths
    public static final String GENRES = BOOK_STORE + "/genres";

    //auth paths
    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign_in";
    public static final String SIGN_UP = "/sign_up";
}
