package com.example.salesmanagementsystem.usersecurity.jwt;


import com.example.salesmanagementsystem.exceptions.ClientException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String username = null;
        String authToken = null;

        if (header != null && header.startsWith("Bearer")) {
            authToken = header.replace("Bearer ", "");
            try {
                username = tokenProvider.getUsernameFromJWTToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("An error has occurred while fetching username from token", e);
                throw new IllegalArgumentException(e.getMessage());
            } catch (ExpiredJwtException e) {
                logger.warn("The token has expired", e);
                throw new JwtException(e.getMessage());
            } catch (SignatureException e) {
                logger.error("Authentication failed. Username or password not valid");
                throw new JwtException(e.getMessage());
            }
        }
        log.info("Received username: {}", username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (userDetails == null){
                    throw new ClientException("User not found", HttpStatus.BAD_REQUEST);
                }
                log.info("User details --> {}", userDetails);
                if (tokenProvider.validateJWTToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication =
                            tokenProvider.getAuthenticationToken(authToken,
                                    SecurityContextHolder.getContext().getAuthentication(),
                                    userDetails);

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.info("authenticated user " + username + " setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ClientException exception){
                log.info("User not found");
                response.sendError(Integer.parseInt(exception.getHttpStatus().toString()), exception.getMessage());
            }

        }
        filterChain.doFilter(request, response);
    }
}
