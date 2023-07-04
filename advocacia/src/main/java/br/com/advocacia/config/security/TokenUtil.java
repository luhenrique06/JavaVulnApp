package br.com.advocacia.config.security;

import br.com.advocacia.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TokenUtil {

    private static final String EMISSOR = "Advocacia";
    private static final String TOKEN_HEADER = "Bearer ";
    private static final String TOKEN_KEY = "01234567890123456789012345678901";
    private static final Long DOIS_DIAS = 172800L;

    private TokenUtil(){
        throw new IllegalStateException("Utility class");
    }

    public static AuthToken encodeToken(Usuario usuario){
        Key secretKey = Keys.hmacShaKeyFor(TOKEN_KEY.getBytes());
        String tokenJWT = Jwts.builder().setSubject(usuario.getLogin())
                                        .setIssuer(EMISSOR)
                                        .setExpiration(new Date(System.currentTimeMillis() + DOIS_DIAS))
                                        .signWith(secretKey, SignatureAlgorithm.HS256)
                                        .compact();

        return new AuthToken(TOKEN_HEADER + tokenJWT);
    }

    public static Authentication decodeToken(HttpServletRequest request){
        try {
            String jwtToken = request.getHeader("Authorization");
            jwtToken = jwtToken.replace(TOKEN_HEADER, "");

            Jws<Claims> jwsClaims = Jwts.parserBuilder().setSigningKey(TOKEN_KEY.getBytes()).build().parseClaimsJws(jwtToken);

            String usuario = jwsClaims.getBody().getSubject();
            String emissor = jwsClaims.getBody().getIssuer();
            Date validade = jwsClaims.getBody().getExpiration();

            if (usuario.length() > 0
                    && emissor.equals(EMISSOR)
                    && validade.after(new Date(System.currentTimeMillis()))) {
                return new UsernamePasswordAuthenticationToken("user", null, Collections.emptyList());
            }
        }catch (Exception e){
            Logger.getLogger(TokenUtil.class.getName()).log(Level.SEVERE, "Erro ao decodificar token", e);
        }
        return null;
    }
}