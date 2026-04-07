package com.proyecto.todolistapplication.config;

import com.proyecto.todolistapplication.service.JWTService;
import com.proyecto.todolistapplication.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@NullMarked
@RequiredArgsConstructor

/**
 * JWT authentication filter executed once per request.
 *
 * <p>This filter looks for a Bearer token in the {@code Authorization} header, extracts the username,
 * validates the token, and if everything is correct, it creates an authenticated object and stores it
 * in Spring Security's {@link SecurityContextHolder}. After that, the request continues through the
 * rest of the filter chain.
 *
 * <p>Important behavior:
 * <ul>
 *   <li>If the header is missing or malformed, the request is not blocked here; it simply continues.
 *   <li>If token parsing fails, the request also continues without authentication.
 *   <li>Authentication is only set when no authentication already exists in the security context.
 * </ul>
 */
public class JwtFilter extends OncePerRequestFilter
{
    // Service responsible for parsing JWT data and validating token integrity/expiration.
    private final JWTService jwtService;

    // Service that loads application users (as UserDetails) from persistence.
    private final MyUserDetailsService userDetailsService;

    @Override
    /**
     * Core filter logic executed once for each HTTP request.
     *
     * @param request incoming HTTP request
     * @param response outgoing HTTP response
     * @param filterChain remaining filters that must still run
     */
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        // Read the Authorization header. Expected format: "Bearer <jwt-token>".
        final String authHeader = request.getHeader("Authorization");

        // If there is no Bearer token, do not authenticate here and continue processing normally.
        if (authHeader == null || !authHeader.startsWith("Bearer "))
        {
            filterChain.doFilter(request, response);
            return;
        }

        // Remove "Bearer " prefix and keep only the raw JWT.
        final String token = authHeader.substring(7);
        String username;

        try
        {
            // Extract username (subject) from token claims.
            username = jwtService.extractUsername(token);
        }
        catch (Exception ex)
        {
            // Any parsing/validation exception means the token cannot be trusted.
            // We intentionally continue without authentication instead of failing the request here.
            filterChain.doFilter(request, response);
            return;
        }

        // Authenticate only when a username exists and the current request is still unauthenticated.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            // Load complete user details (password hash, authorities, account flags, etc.).
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validate token against user details (and typically expiration/signature checks).
            if (jwtService.isTokenValid(token, userDetails))
            {
                // Build an authenticated token that Spring Security understands.
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Attach request-specific metadata (remote address, session ID if available, etc.).
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Store authentication in the security context so downstream code sees the user as logged in.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Always continue with the remaining chain.
        filterChain.doFilter(request, response);
    }
}
