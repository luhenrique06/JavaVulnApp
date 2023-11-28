package br.com.advocacia.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;



@Configuration
@EnableWebSecurity
public class MySecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/usuario/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/processo/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/arquivo/enviodocumento").permitAll()
                .requestMatchers(HttpMethod.POST, "/enviodocumento").permitAll()
                .anyRequest().authenticated()
                .and()
                .cors();

        http.addFilterBefore(new MyFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    //CORREÇÃO AQUI, NÃO ESQUECER DE COMENTAR A CLASS PASS E MUDAR NO USUARIOSERVICE
    @Bean
    public PasswordEncoder passwordEncoder()  {
        return new BCryptPasswordEncoder();
    }

}
