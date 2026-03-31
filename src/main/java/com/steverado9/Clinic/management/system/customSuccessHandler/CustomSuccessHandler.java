package com.steverado9.Clinic.management.system.customSuccessHandler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Set;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if(roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/adminDashboard");
        } else if (roles.contains("ROLE_DOCTOR")) {
            response.sendRedirect("/doctor/doctorDashboard");
        } else if (roles.contains("ROLE_RECEPTIONIST")) {
            response.sendRedirect("/receptionist/receptionistDashboard");
        } else {
            response.sendRedirect("/patient/patientDashboard");
        }
    }
}
