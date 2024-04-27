package edu.tcu.cs.superfrogscheduler.system;

public class HttpStatusCode {
    public static final int SUCCESS = 200; // success
    public static final int INVALID_ARGUMENT = 400; // bad request (such as invalid parameters)
    public static final int UNAUTHORIZED = 401; // username or password incorrect
    public static final int FORBIDDEN = 403; // no permission
    public static final int NOT_FOUND = 404; // not found
    public static final int INTERNAL_SERVER_ERROR = 500; // server internal error
    public static final int CONFLICT = 409;

}
