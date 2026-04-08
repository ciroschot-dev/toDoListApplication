package com.proyecto.todolistapplication.config;

import com.proyecto.todolistapplication.service.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@NullMarked

public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler
{

    private final JWTService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException
    {

        // 1. Read OAuth2 user data returned by providers like Google/GitHub.
        // We first try "email" (common for Google) and then fall back to "login" (common for GitHub).
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email"); // Or "login" for GitHub

        if (email == null)
        {
            email = oAuth2User.getAttribute("login");
        }

        // 2. Generate this application's JWT using the resolved identifier.
        // This token is what the frontend will use for authenticated requests.
        String token = jwtService.generateToken(email);

        // 3. Redirect to the frontend callback URL and include the token as a query param.
        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/todo-app")
                .queryParam("token", token)
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}