package com.GetOnClouds.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class ClerkJwtAuthFilter extends OncePerRequestFilter {
    @Value("${clerk.issuer}")
    private String clerkIssuer;

    private final ClerkJwksProvider jwksProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getRequestURI().contains("/webhooks")){
            filterChain.doFilter(request,response);
        }

        String authHeader=request.getHeader("Authorization");

        if(authHeader==null || !authHeader.startsWith("Bearer")){
            response.sendError(HttpServletResponse.SC_FORBIDDEN,"Authorization header missing/invalid");
            return;
        }

        try {
            String token=authHeader.substring(7);
            String[] chunks=token.split("\\.");
            if(chunks.length<3){
                response.sendError(HttpServletResponse.SC_FORBIDDEN,"Invalid JWT token format ");
                return;
            }

            String headerJson=new String(Base64.getUrlDecoder().decode(chunks[0]));

        }catch (Exception e){
            throw new RuntimeException(e);
        }

        filterChain.doFilter(request,response);

    }
}
