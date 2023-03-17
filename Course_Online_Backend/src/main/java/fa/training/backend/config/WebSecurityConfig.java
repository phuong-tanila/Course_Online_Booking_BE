package fa.training.backend.config;


import java.util.Date;

import fa.training.backend.entities.User;
import fa.training.backend.filters.CustomAuthenticationFilter;
import fa.training.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService) // Cung cáp userservice cho spring security
                .passwordEncoder(passwordEncoder()); // cung cấp password encoder
        User u = new User();
        u.setPassword(passwordEncoder().encode("aaa"));
        u.setEmail("aaa@gmail.com");
//        userService.createUser(u);
    }
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager bean
        return super.authenticationManagerBean();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/logout", "/login", "/register")
                    .permitAll()
                .anyRequest()
                    .authenticated()
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/403")
                .and()
                .addFilterBefore(new CustomAuthenticationFilter(userService), UsernamePasswordAuthenticationFilter.class);


//        http.authorizeRequests().antMatchers("/user-info").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
//
//        http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");

//        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

//        http.authorizeRequests()
//                .antMatchers("/", "/logout", "/login")
//                    .permitAll()
//                .anyRequest().authenticated()
//                .and()
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        InMemoryTokenRepositoryImpl memory = new InMemoryTokenRepositoryImpl(); // Ta lưu tạm remember me trong memory
        // (RAM). Nếu cần mình có thể lưu trong
        // database

        memory.createNewToken(new PersistentRememberMeToken("user", "1", "123", new Date()));
        return memory;
    }

}
