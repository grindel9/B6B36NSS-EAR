package cz.cvut.ear.sem.config;

import cz.cvut.ear.sem.model.Authority;
import cz.cvut.ear.sem.security.JwtAuthorizationFilter;
import cz.cvut.ear.sem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/faculty").hasAnyAuthority(Authority.ADMIN.toString())
                .antMatchers("/department").hasAnyAuthority(Authority.ADMIN.toString())
                .antMatchers("/teacher").hasAnyAuthority(Authority.TEACHER.toString())
                .antMatchers("/selected-topic").hasAnyAuthority(Authority.TEACHER.toString(), Authority.STUDENT.toString())
                .antMatchers("/student").hasAnyAuthority(Authority.TEACHER.toString(), Authority.STUDENT.toString())
                .antMatchers("/topic").hasAnyAuthority(Authority.TEACHER.toString(), Authority.STUDENT.toString())
                .antMatchers("/user/*").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
