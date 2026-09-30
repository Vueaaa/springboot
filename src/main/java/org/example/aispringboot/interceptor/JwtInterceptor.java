package org.example.aispringboot.interceptor;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.aispringboot.common.JwtUtil;
import org.example.aispringboot.common.Result;
import org.example.aispringboot.common.ResultCode;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public JwtInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            writeUnauthorizedResponse(response);
            return false;
        }

        String token = authorization.substring(7).trim();
        if (token.isEmpty()) {
            writeUnauthorizedResponse(response);
            return false;
        }

        try {
            request.setAttribute("claims", jwtUtil.parseToken(token));
            return true;
        } catch (JwtException | IllegalArgumentException exception) {
            writeUnauthorizedResponse(response);
            return false;
        }
    }

    private void writeUnauthorizedResponse(HttpServletResponse response)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        response.getWriter().write(
                "{\"code\":\"" + ResultCode.UNAUTHORIZED.getCode()
                        + "\",\"msg\":\"" + ResultCode.UNAUTHORIZED.getMsg()
                        + "\",\"data\":null}"
        );
    }
}
