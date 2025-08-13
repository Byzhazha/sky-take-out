package com.sky.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 定义密码编码器，让你的代码可以继续使用
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置URL的访问规则
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // 放行API文档和员工登录页面，不需要登录
                        .requestMatchers(
                                "/admin/**",
                                "/webjars/**",
                                "/swagger-resources/**",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/favicon.ico",
                                "/admin/employee/login",
                                "/admin/employee/login", // 管理员登录
                                "/user/user/login",      // 用户登录
                                "/user/shop/**"         // 用户端店铺状态
                        ).permitAll()
                        // 其他所有请求都需要登录认证
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}