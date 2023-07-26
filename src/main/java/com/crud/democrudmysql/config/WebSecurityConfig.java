package com.crud.democrudmysql.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()
            .loginPage("/login")
            .permitAll(false)
            .and()
            .authorizeRequests()
            .antMatchers("/admin/**")
            .hasAnyRole("ADMIN")
            .antMatchers("/cursos/*","/usuario/**")
            .authenticated()
            .anyRequest()
            .permitAll()
            .and()
            .rememberMe().key("rememberMeKey").tokenValiditySeconds(3600)
            .userDetailsService(userDetailsService)
            .and()
            .logout(logout->logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout",HttpMethod.GET.name())).logoutSuccessUrl("/"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
