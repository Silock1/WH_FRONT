package com.warehouse_accounting.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/login";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Vaadin обрабатывает CSRF внутренне
        http.csrf().disable()
                // Ограничить доступ к нашему приложени
                .authorizeRequests()
                // Разрешить все внутренние запросы Vaadin.
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
                //форма логин/пароль от Vaadin
                .mvcMatchers(HttpMethod.GET, "/") .permitAll()
                .regexMatchers(HttpMethod.POST, "/").permitAll()
                // Разрешить все запросы от зарегистрированных пользователей.
                .anyRequest().authenticated()
                // Настройте страницу входа.
                .and().formLogin()
                .loginPage(LOGIN_PROCESSING_URL).permitAll()
                .successHandler(new LoginSuccessHandler())
                // Настроить выход
                .and().logout()
                .logoutSuccessUrl(LOGOUT_SUCCESS_URL);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/api/v1/auth/login",
                // JS на стороне клиента
                "/VAADIN/**",
                // стандартный URI фавиконки
                "/favicon.ico",
                // стандарт исключения роботов
                "/robots.txt",
                // манифест веб-приложения
                "/manifest.webmanifest",
                "/sw.js",
                "/offline.html",
                // значки и изображения
                "/icons/**",
                "/images/**",
                "/styles/**",
                // (режим разработки) Консоль отладки H2
                "/h2-console/**");
    }


}
