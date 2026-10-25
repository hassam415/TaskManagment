package com.example.taskmanagement;

public class ApiSesson {
    private static String access_Token;

    public static String getAccess_Token() {
        return access_Token;
    }

    public static void setAccess_Token(String access_Token) {
        ApiSesson.access_Token = access_Token;
    }
    public static void clearSession() {
        access_Token = null;
    }
}
