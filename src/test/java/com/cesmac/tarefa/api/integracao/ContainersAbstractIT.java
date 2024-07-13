package com.cesmac.tarefa.api.integracao;

import com.cesmac.tarefa.api.TarefaApiTestContainerInitializer;
import org.flywaydb.core.Flyway;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SuppressWarnings("resource")
@ContextConfiguration(initializers = TarefaApiTestContainerInitializer.class)
@Testcontainers
public class ContainersAbstractIT {

    private static final Integer POSTGRES_PORT = 5432;

    private static final PostgreSQLContainer<?> postgresContainer;

    static {
        postgresContainer =
                new PostgreSQLContainer<>(
                                DockerImageName.parse(
                                                System.getenv("IMAGEM_POSTGRES") != null
                                                        ? System.getenv("IMAGEM_POSTGRES")
                                                        : "postgres:13")
                                        .asCompatibleSubstituteFor("postgres"))
                        .withExposedPorts(POSTGRES_PORT);

        postgresContainer.getPortBindings().add(POSTGRES_PORT + ":" + POSTGRES_PORT);

        postgresContainer.withInitScript("seeds/init.sql");

        postgresContainer.start();

        System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresContainer.getPassword());

        Flyway flyway =
                Flyway.configure()
                        .dataSource(
                                postgresContainer.getJdbcUrl(),
                                postgresContainer.getUsername(),
                                postgresContainer.getPassword())
                        .locations("seeds/migration_integration_test")
                        .load();

        flyway.migrate();
    }
}
