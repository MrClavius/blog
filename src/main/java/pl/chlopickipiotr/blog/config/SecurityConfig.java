package pl.chlopickipiotr.blog.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT u.email,u.password,true FROM AUSER u WHERE u.email=?")
                .authoritiesByUsernameQuery("SELECT u.email,r.role_name FROM AUSER u JOIN USER_ROLE ur ON u.id = ur.user_entity_id JOIN role r ON ur.role_set_id = r.id WHERE u.email=?")
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/form").hasAnyRole("USER")
                .antMatchers("/delete-post/*").hasAnyRole("ADMIN")
                .anyRequest()
                .permitAll()
                .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .formLogin()
                .loginPage("/login-form")
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/login-submit")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutSuccessUrl("/")
        ;
    }

}
