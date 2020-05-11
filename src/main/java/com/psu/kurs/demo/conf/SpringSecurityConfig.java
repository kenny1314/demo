package com.psu.kurs.demo.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/getimgp/**", "/getimg/**", "/getimgg/**", "/css/**", "/img/**", "/403").permitAll()
                .antMatchers("/registration", "/", "/index", "/genres", "/listplatforms", "/delivery", "/about", "/testDB", "/game/**", "/platform/**",
                        "/testinput", "/testDB/**", "/basket", "/delProdBask/**").permitAll()
                .antMatchers("/createOrder").permitAll()
                .antMatchers("/accountAdmin").permitAll()
                .antMatchers("/accountUser").permitAll()
                .antMatchers("/t").permitAll()
                .antMatchers("/e").permitAll()
                .antMatchers("/downWord").permitAll()
                .antMatchers("/errfind").permitAll()
                .antMatchers("/downExel").permitAll()
                .antMatchers("/laba", "/searchq/**", "/productq").permitAll()
                .antMatchers("/searchq2/**").permitAll()
                .antMatchers("/searchq2").permitAll()

                .antMatchers("/api/platforms").permitAll()
                .antMatchers("/getGameByPlatform/**").permitAll()
                .antMatchers("/getGameByGenre/**").permitAll()
                .antMatchers("/addGenres", "/delgame/**", "/delplatform/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/")
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

    // создаем пользоватлелей, admin и user
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}1234").roles("USER")
                .and()
                .withUser("courier").password("{noop}1234").roles("COURIER")
                .and()
                .withUser("admin").password("{noop}1234").roles("ADMIN");
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        return bCryptPasswordEncoder;
//    }
}