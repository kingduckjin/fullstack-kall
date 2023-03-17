package com.project.kall.config.jwt;

import com.project.kall.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/***
 * 권한이 필요한 것들 ( 토큰검사를 해야하는 것들 Mapping uri을 지정해주세요. )
 * /api/admin/valid/* ==>
 */
@Slf4j
@WebFilter(urlPatterns = {"/api/admin/valid/*", "/api/user/valid/*"})
public class FilterImpl implements Filter {

    private final JwtService jwtService = new JwtService();


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        log.info("Dofilter");

        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        log.info("{}",jwtService.getTokenInfo(token).get("id"));
        if (token == null || !jwtService.validateToken(token)) {
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        } else {
            request.setAttribute("id", jwtService.getTokenInfo(token).get("id"));
            request.setAttribute("role", jwtService.getTokenInfo(token).get("role"));
            chain.doFilter(request, response);
        }
    }


}
