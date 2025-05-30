package com.gathi.sch.schedulerAdmin.config;

import com.gathi.sch.schedulerAdmin.svc.JwtService;
import com.gathi.sch.schedulerAdmin.svc.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final UserService userService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService, UserService userService, HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authorizationHeader != null
                && authorizationHeader.startsWith("Bearer ")
                && authentication == null) {

            String jwt = authorizationHeader.substring(7);

            try {
                if (jwtService.isTokenValid(jwt)) {
                    String username = jwtService.extractUsername(jwt);
                    UserDetails userDetails = userService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            username, null, userDetails.getAuthorities());

                    //WebAuthenticationDetailsSource this contains users IP, Session ID
                    //Good for audit purpose
                    //When blocking specific IP addresses
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {

                /*
                * A try-catch block wraps the logic and uses the HandlerExceptionResolver
                * to forward the error to the global exception handler.
                * We will see how it can be helpful to do the exception forwarding.
                 * */
                handlerExceptionResolver.resolveException(request, response, null, e);
            }

        }

        filterChain.doFilter(request, response);
    }
}
