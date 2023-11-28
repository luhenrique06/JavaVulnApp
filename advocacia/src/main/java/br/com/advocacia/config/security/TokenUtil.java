package br.com.advocacia.config.security;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import br.com.advocacia.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

public class TokenUtil {



    private String EMISSOR = "Advocacia";
    private String TOKEN_HEADER = "Bearer ";
    private String TOKEN_KEY = "01234567890123456789012345678901";
    private Long DOIS_DIAS = 172800L;

    
    public TokenUtil(){};


    public  String encodeToken(Optional<Usuario>  usuario){
        Key secretKey = Keys.hmacShaKeyFor(TOKEN_KEY.getBytes());
        String role = usuario.get().getIsAdmin() ? "admin" : "user";
        String tokenJWT = Jwts.builder().setSubject(usuario.get().getLogin())
                                        .claim("role", role )
                                        .setIssuer(EMISSOR)
                                        .setExpiration(new Date(System.currentTimeMillis() + DOIS_DIAS))
                                        .signWith(secretKey, SignatureAlgorithm.HS256)
                                        .compact();

        return TOKEN_HEADER + tokenJWT;
    }

    public  Authentication decodeToken(HttpServletRequest request){
        try {
            String jwtToken = request.getHeader("Authorization");
            jwtToken = jwtToken.replace(TOKEN_HEADER, "");
            Key secretKey = Keys.hmacShaKeyFor(TOKEN_KEY.getBytes());

            //ESSA LINHA É A CORREÇÃO DO JWT SEM ASSINATURA
            Jws<Claims> jwt = Jwts.parserBuilder().setSigningKey(TOKEN_KEY.getBytes()).build().parseClaimsJws(jwtToken);
           
            //Jwt jwt = Jwts.parser().setSigningKey(secretKey).parse(jwtToken);

            String body = jwt.getBody().toString();
  

            String subject = extractValue(body, "sub=([\\w|-]+)");
            String role = extractValue(body, "role=([\\w|-]+)");

            
            return new UsernamePasswordAuthenticationToken(subject, role, Collections.emptyList());
            
        }catch (Exception e){
            Logger.getLogger(TokenUtil.class.getName()).log(Level.SEVERE, "Erro ao decodificar token", e);
        }
        return null;
    }

    private static String extractValue(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        java.util.regex.Matcher  matcher = pattern.matcher(input);

        return matcher.find() ? matcher.group(1) : null;
    }
}