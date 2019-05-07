package com.cloud.redis.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @Description: 设置从数据源
 * 将数据源注入到 Factory，配置 repository、domian 的位置
 * @Author: ZX
 * @Date: 2019/3/15 11:20
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        // 实体管理bean名称
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        // 事务管理bean名称
        transactionManagerRef = "secondaryTransactionManager",
        // repository包名
        basePackages = {"com.cloud.redis.repository.secondary"})
public class SecondaryConfig {

    /**
     * 该方法仅在需要使用JdbcTemplate对象时选用
     *
     * @param dataSource 注入名为secondaryDataSource的bean
     * @return 数据源JdbcTemplate对象
     */
    @Bean(name = "secondaryJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("secondaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * 扫描spring.jpa.secondary.hibernate开头的配置信息
     *
     * @return Hibernate配置信息
     */
    @Bean(name = "secondaryHibernateProperties")
    @ConfigurationProperties(prefix = "spring.jpa.secondary.hibernate")
    public HibernateProperties hibernateProperties() {
        return new HibernateProperties();
    }

    /**
     * 扫描spring.jpa.secondary开头的配置信息
     *
     * @return jpa配置信息
     */
    @Bean(name = "secondaryJpaProperties")
    @ConfigurationProperties(prefix = "spring.jpa.secondary")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    /**
     * 获取从库实体管理工厂对象
     *
     * @param secondaryDataSource 注入名为secondaryDataSource的数据源
     * @param jpaProperties       注入名为secondaryJpaProperties的jpa配置信息
     * @param builder             注入EntityManagerFactoryBuilder
     * @return 实体管理工厂对象
     */
    @Bean(name = "secondaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("secondaryDataSource") DataSource secondaryDataSource
            , @Qualifier("secondaryJpaProperties") JpaProperties jpaProperties
            , @Qualifier("secondaryHibernateProperties") HibernateProperties hibernateProperties, EntityManagerFactoryBuilder builder) {
        return builder
                // 设置数据源
                .dataSource(secondaryDataSource)
                // 设置jpa配置
                .properties(hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings()))
                // 设置实体包名
                .packages("com.cloud.redis.entity.secondary")
                // 设置持久化单元名，用于@PersistenceContext注解获取EntityManager时指定数据源
                .persistenceUnit("secondaryPersistenceUnit")
                .build();
    }

    /**
     * 获取实体管理对象
     *
     * @param factory 注入名为secondaryEntityManagerFactory的bean
     * @return 实体管理对象
     */
    @Bean(name = "secondaryEntityManager")
    public EntityManager entityManager(@Qualifier("secondaryEntityManagerFactory") EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    /**
     * 获取从库事务管理对象
     *
     * @param factory 注入名为secondaryEntityManagerFactory的bean
     * @return 事务管理对象
     */
    @Bean(name = "secondaryTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("secondaryEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}