package com.example.Configs;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.example")
@PropertySource("classpath:persistence-mysql-and-hibernate.properties")
public class DemoAppConfig implements WebMvcConfigurer {

    @Autowired
    private Environment environment;

    private Logger logger = Logger.getLogger(getClass().getName());

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        return properties;
    }

    @Bean
    public DataSource myDataSource() {
        ComboPooledDataSource myDataSource = new ComboPooledDataSource();
        try {
            myDataSource.setDriverClass(environment.getProperty("jdbc.driver"));
        } catch (PropertyVetoException exc) {
            throw new RuntimeException(exc);
        }
        logger.info(">>> jdbc.url=" + environment.getProperty("jdbc.url"));
        logger.info(">>> jdbc.user=" + environment.getProperty("jdbc.user"));

        myDataSource.setJdbcUrl(environment.getProperty("jdbc.url"));
        myDataSource.setUser(environment.getProperty("jdbc.user"));
        myDataSource.setPassword(environment.getProperty("jdbc.password"));

        myDataSource.setInitialPoolSize(helperMethodConversionStringToInteger("connection.pool.initialPoolSize"));
        myDataSource.setMinPoolSize(helperMethodConversionStringToInteger("connection.pool.minPoolSize"));
        myDataSource.setMaxPoolSize(helperMethodConversionStringToInteger("connection.pool.maxPoolSize"));
        myDataSource.setMaxIdleTime(helperMethodConversionStringToInteger("connection.pool.maxIdleTime"));
        return myDataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(myDataSource());
        sessionFactory.setPackagesToScan(environment.getProperty("hibernate.packagesToScan"));
        sessionFactory.setHibernateProperties(getHibernateProperties());
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager hibernateTransactionManager (@Autowired SessionFactory sessionFactory){
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);
        return hibernateTransactionManager;
    }


    private Integer helperMethodConversionStringToInteger(String string) {
        String propertyValue = environment.getProperty(string);
        if (propertyValue != null) {
            return Integer.parseInt(propertyValue);
        } else {
            throw new RuntimeException("Value not defined.");
        }
    }
}
