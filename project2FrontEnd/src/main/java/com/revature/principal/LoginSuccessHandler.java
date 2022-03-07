package com.revature.principal;

import com.revature.model.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * Redirects user based on role if authentication is successful
     * @param request - The HTTP request
     * @param response - The HTTP response
     * @param authentication - Authentication handler
     * @throws IOException - if authentication fails
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

        String redirectURL = request.getContextPath();

        if (userDetails.getRole() == Role.PATIENT)
            redirectURL += "/patient/" + userDetails.getUserId();

        if (userDetails.getRole() == Role.PHYSICIAN)
            redirectURL += "/doctor/" + userDetails.getUserId();

        if (userDetails.getRole() == Role.PHARMACIST)
            redirectURL += "/pharmacy";

        response.sendRedirect(redirectURL);
    }
}
