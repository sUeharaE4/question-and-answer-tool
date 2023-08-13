package com.qa.tool.it;

import lombok.EqualsAndHashCode;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;
import org.springframework.test.context.MergedContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;

public class EnableTestContainerContextCustomizerFactory implements ContextCustomizerFactory {

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Inherited
    public @interface EnabledTestContainer {
    }

    @Override
    public ContextCustomizer createContextCustomizer(Class<?> testClass,
            List<ContextConfigurationAttributes> configAttributes) {
        if (!(AnnotatedElementUtils.hasAnnotation(testClass, EnabledTestContainer.class))) {
            return null;
        }
        return new TestContainerContextCustomizer();
    }

    @EqualsAndHashCode
    private static class TestContainerContextCustomizer implements ContextCustomizer {

        @Override
        public void customizeContext(ConfigurableApplicationContext context,
                MergedContextConfiguration mergedConfig) {
            MapPropertySource localstackPropertySource = runLocalStackContainer();
            context.getEnvironment().getPropertySources().addFirst(localstackPropertySource);
            MapPropertySource postgresPropertySource = runPostgresContainer();
            context.getEnvironment().getPropertySources().addFirst(postgresPropertySource);
        }

        private MapPropertySource runPostgresContainer() {
            var postgres =
                    new PostgreSQLContainer<>(DockerImageName.parse("postgres").withTag("14.1"));
            postgres.start();
            Map<String, Object> properties = Map.<String, Object>of("spring.datasource.url",
                    postgres.getJdbcUrl(), "spring.datasource.username", postgres.getUsername(),
                    "spring.datasource.password", postgres.getPassword(),
                    // Prevent any in memory db from replacing the data source
                    // See @AutoConfigureTestDatabase
                    "spring.test.database.replace", "NONE", "spring.flyway.url",
                    postgres.getJdbcUrl(), "spring.flyway.user", postgres.getUsername(),
                    "spring.flyway.password", postgres.getPassword());
            properties.forEach((k, v) -> {
                System.out.println(k + ":" + v.toString());
            });
            return new MapPropertySource("PostgresContainer Test Properties", properties);
        }

        private MapPropertySource runLocalStackContainer() {
            LocalStackContainer localstack =
                    new LocalStackContainer(DockerImageName.parse("localstack/localstack"))
                            .withServices(LocalStackContainer.Service.SQS);
            localstack.start();
            Map<String, Object> properties =
                    Map.<String, Object>of("cloud.aws.credentials.access-key",
                            localstack.getAccessKey(), "cloud.aws.credentials.secret-key",
                            localstack.getSecretKey(), "cloud.sqs.region.static",
                            localstack.getRegion(), "cloud.sqs.endpoint", localstack.getEndpoint());
            properties.forEach((k, v) -> {
                System.out.println(k + ":" + v.toString());
            });
            return new MapPropertySource("LocalstackContainer Test Properties", properties);
        }

    }

}
