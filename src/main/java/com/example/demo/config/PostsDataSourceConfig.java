// PostsDataSourceConfig.java
package com.example.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.example.demo.posts.domain.Post;
import com.example.demo.posts.repository.PostRepository;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackageClasses = PostRepository.class,        // posts 전용
        entityManagerFactoryRef = "postsEntityManagerFactory",
        transactionManagerRef = "postsTransactionManager"
)
public class PostsDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.posts")
    public DataSource postsDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean postsEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {

        return builder
                .dataSource(postsDataSource())
                .packages(Post.class)                     // posts 엔티티만 스캔
                .persistenceUnit("postsPU")
                .build();
    }

    @Bean
    public PlatformTransactionManager postsTransactionManager(
            @Qualifier("postsEntityManagerFactory") EntityManagerFactory emf) {

        return new JpaTransactionManager(emf);
    }
}
