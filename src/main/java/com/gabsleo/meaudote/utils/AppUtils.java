package com.gabsleo.meaudote.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppUtils {
    public static List<String> publicPaths = Arrays.asList(
            "/api/v1/auth/**",
            "/v3/api-docs/**", "/swagger-ui/**",
            "/api/v1/testing", "/api/v1/testing/**",
            "/api/v1/pets/**"
    );
    public static List<String> userPaths = Arrays.asList(
            "/api/v1/users/current"
    );
    public static List<String> adimnPaths = Arrays.asList(
            "/api/v1/admin"
    );
}
