package com.cesmac.tarefa.api.integracao;

import org.flywaydb.core.Flyway;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@ContextConfiguration(initializers = ContainersAbstractIT.TestContainerInitializar.class)
@Testcontainers()
public class ContainersAbstractIT {

    public static PostgreSQLContainer<?> postgresSQLContainer =
            new PostgreSQLContainer<>(
                    DockerImageName.parse(
                                    System.getenv("IMAGEM_POSTGRES") != null
                                            ? System.getenv("IMAGEM_POSTGRES")
                                            : "postgres:13")
                            .asCompatibleSubstituteFor("postgres"));

    static {
        postgresSQLContainer.withInitScript("seeds/init.sql").start();
        Flyway flyway =
                Flyway.configure()
                        .dataSource(
                                postgresSQLContainer.getJdbcUrl(),
                                postgresSQLContainer.getUsername(),
                                postgresSQLContainer.getPassword())
                        .locations("seeds/migration_integration_test")
                        .load();

        flyway.migrate();
    }

    public static class TestContainerInitializar
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + postgresSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgresSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgresSQLContainer.getPassword());
        }
    }
}
