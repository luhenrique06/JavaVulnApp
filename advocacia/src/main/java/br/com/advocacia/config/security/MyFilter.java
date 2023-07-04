package br.com.advocacia.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class MyFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getHeader("Authorization") != null){
            Authentication auth = TokenUtil.decodeToken(request);
            if(auth != null){
                SecurityContextHolder.getContext().setAuthentication(auth);
            }else{
                ErroDTO erro = new ErroDTO(401, "Usuário não autorizado para esse endpoint");
                response.setStatus(erro.getStatus());
                response.setContentType("application/json");
                ObjectMapper mapper = new ObjectMapper();
                response.getWriter().print(mapper.writeValueAsString(erro));
                response.getWriter().flush();
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
