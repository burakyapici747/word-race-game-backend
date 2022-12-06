package com.wordrace.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordrace.util.security.JwtHelper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import java.util.Map;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(final AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        final String username = request.getParameter("username");
        final String password = request.getParameter("password");

        final UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username,password);

        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        final String username = authentication.getName();
        final String accessToken = JwtHelper.generateJwtToken(username);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(200);

        final Map<String,String> responseData = new HashMap<>();

        responseData.put("access_token",accessToken);

        new ObjectMapper().writeValue(response.getOutputStream(),responseData);
    }
}
