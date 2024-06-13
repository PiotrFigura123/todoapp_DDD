package pl.piotrFigura.ToDoApp;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public class PostgresTestContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {


    private static final int POSTGRES_PORT = 5432;

    private static final String POSTGIS_TAG = "postgis/postgis:12-3.2";

    private static final DockerImageName POSTGRES_POSTGIS_IMAGE_NAME = DockerImageName
        .parse(POSTGIS_TAG)
        .asCompatibleSubstituteFor("postgres");



    public PostgresTestContainerInitializer() {
    }


    public void initialize(ConfigurableApplicationContext context) {

        PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer(POSTGRES_POSTGIS_IMAGE_NAME);
        postgreSQLContainer.setExposedPorts(List.of(POSTGRES_PORT));
        postgreSQLContainer = postgreSQLContainer.withUsername("admin");
        postgreSQLContainer = postgreSQLContainer.withPassword("password");
        postgreSQLContainer = postgreSQLContainer.withDatabaseName("toDoApp");

        postgreSQLContainer.start();

        TestPropertyValues.of(
            "postgres.database.host=" + postgreSQLContainer.getHost(),
            "postgres.database.port=" + postgreSQLContainer.getMappedPort(POSTGRES_PORT),
            "postgres.database.username=" + postgreSQLContainer.getUsername(),
            "postgres.database.password=" + postgreSQLContainer.getPassword(),
            "postgres.database.name=" + postgreSQLContainer.getDatabaseName()
        ).applyTo(context.getEnvironment());


        log.info("Postgres with PostGIS containter initialized.");
    }

}
