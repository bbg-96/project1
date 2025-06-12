package com.example.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.example.demo.users.domain.User;
import com.example.demo.users.repository.UserRepository;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackageClasses = UserRepository.class,        // users 전용
        entityManagerFactoryRef = "usersEntityManagerFactory",
        transactionManagerRef = "usersTransactionManager"
)
public class UsersDataSourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.users")
    public DataSource usersDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean usersEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {

        return builder
                .dataSource(usersDataSource())
                .packages(User.class)                     // users 엔티티만 스캔
                .persistenceUnit("usersPU")
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager usersTransactionManager(
            @Qualifier("usersEntityManagerFactory") EntityManagerFactory emf) {

        return new JpaTransactionManager(emf);
    }
}
