package org.talend.spring.aks.compat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.aws.core.env.stack.config.StackNameProvider;
import org.springframework.cloud.aws.core.env.stack.config.StaticStackNameProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "talend.aks.compatibility", havingValue = "true")
public class AKSCompatibilityConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(AKSCompatibilityConfiguration.class);

    public AKSCompatibilityConfiguration() {
        LOGGER.info("Enable AKS compatibility configuration for AWS libraries.");
    }

    @Bean
    public StackNameProvider stackProvider() {
        return new StaticStackNameProvider("<aks context>");
    }
}
