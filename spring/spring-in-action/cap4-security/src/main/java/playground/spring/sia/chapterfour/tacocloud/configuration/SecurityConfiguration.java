package playground.spring.sia.chapterfour.tacocloud.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;

    public SecurityConfiguration(@Autowired DataSource dataSource) {

        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//        auth.
//           inMemoryAuthentication().
//                withUser("alice").
//                password("alice123").
//                authorities("ROLE_USER").
//                and().
//                withUser("bob").
//                password("bob123").
//                authorities("ROLE_USER");

        auth.jdbcAuthentication().dataSource(dataSource);
    }

}
