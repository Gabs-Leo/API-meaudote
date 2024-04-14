package com.gabsleo.meaudote.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabsleo.meaudote.utils.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {
        Response<String> apiResponse = new Response<String>();
        if (request.getAttribute("failed") != null) {
            if(request.getAttribute("failed").equals("expired")) {
                apiResponse.getErrors().add("Unauthorized. Token expired.");
                response.setStatus(401);
            } else {
                apiResponse.getErrors().add("Unauthorized. Token expired.");
                response.setStatus(401);
            }
        } else {
            //apiResponse.getErrors().add(authException.toString().substring(authException.toString().indexOf(":")+2));
            apiResponse.getErrors().add("Unauthorized. Token needed.");
            response.setStatus(401);
        }

        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, apiResponse);
        outputStream.flush();
    }
}
