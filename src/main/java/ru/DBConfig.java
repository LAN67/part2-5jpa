package ru;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DBConfig {

    @Autowired
    Environment environment;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(environment.getRequiredProperty("driver_class"));
        dataSource.setUrl(environment.getRequiredProperty("connection_url"));
        dataSource.setUsername(environment.getRequiredProperty("connection_username"));
        dataSource.setPassword(environment.getRequiredProperty("connection_password"));

//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://localhost:5432/part2-5");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("password");

        return dataSource;
    }

}
