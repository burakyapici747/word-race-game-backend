package com.wordrace.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordrace.constant.SecurityConstant;
import com.wordrace.util.security.JwtHelper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/api/user/login")){
            filterChain.doFilter(request,response);
        }else{
            String authorizationToken = request.getHeader(HttpHeaders.AUTHORIZATION);

            if(authorizationToken != null && authorizationToken.startsWith(SecurityConstant.AUTHORIZATION_TOKEN_PREFIX)){
                authorizationToken = authorizationToken.substring(SecurityConstant.AUTHORIZATION_TOKEN_PREFIX.length());

                try {
                    final DecodedJWT decodedJWT = JwtHelper.decodeJwtToken(authorizationToken);
                    final String username = decodedJWT.getSubject();

                    final UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username,null,null);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request,response);
                }catch (JWTVerificationException e){
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(401);

                    final Map<String,String> errorData = new HashMap<>();
                    errorData.put("error", e.getMessage());
                    errorData.put("stackTrace", Arrays.toString(e.getStackTrace()));
                    errorData.put("path", request.getPathInfo());
                    errorData.put("time", LocalDateTime.now().toString());

                    new ObjectMapper().writeValue(response.getOutputStream(),errorData);
                }
            }else{
                filterChain.doFilter(request,response);
            }
        }
    }
}
