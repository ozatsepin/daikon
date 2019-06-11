package org.talend.spring.aks.compat;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.aws.core.env.stack.config.StackNameProvider;
import org.springframework.cloud.aws.core.env.stack.config.StaticStackNameProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AKSCompatibilityConfiguration.class, //
        webEnvironment = SpringBootTest.WebEnvironment.NONE, //
        properties = "talend.aks.compatibility=true")
public class AKSCompatibilityConfigurationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void shouldUseStaticBeanIdProvider() {
        // When
        final StackNameProvider stackNameProvider = context.getBean(StackNameProvider.class);

        // Then
        assertEquals(StaticStackNameProvider.class, stackNameProvider.getClass());
        assertEquals("<aks context>", stackNameProvider.getStackName());
    }
}