package com.workflow.workflow.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configure the jpa repository.
 */
@Configuration
@EnableJpaRepositories("com.workflow.workflow.repository")
@EnableTransactionManagement
public class DatabaseConfiguration {
}
