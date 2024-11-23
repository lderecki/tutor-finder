package pl.lderecki.viewcontroller.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;

@Configuration
public class MainDatasourceConfiguration extends AbstractR2dbcConfiguration {

    private final PropertiesManager propertiesManager;

    public MainDatasourceConfiguration(PropertiesManager propertiesManager) {
        this.propertiesManager = propertiesManager;
    }

    @Bean
    @Override
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host(propertiesManager.getDbHost())
                        .port(propertiesManager.getDbPort())
                        .database(propertiesManager.getDbName())
                        .username(propertiesManager.getDbUser())
                        .password(propertiesManager.getDbPassword())
                        .build());
    }

    @Bean
    public ReactiveTransactionManager reactiveTransactionManager() {
        return new R2dbcTransactionManager(connectionFactory());
    }

    @Bean
    public TransactionalOperator transactionalOperator() {
        return TransactionalOperator.create(reactiveTransactionManager());
    }

    @Bean
    public R2dbcEntityTemplate r2dbcEntityTemplate() {

        return new R2dbcEntityTemplate(connectionFactory());
    }
}
