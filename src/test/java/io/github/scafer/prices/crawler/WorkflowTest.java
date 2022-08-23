package io.github.scafer.prices.crawler;

import io.github.scafer.prices.crawler.util.EmbeddedMongoDBServer;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
class WorkflowTest extends AbstractProcessEngineRuleTest {

    private static EmbeddedMongoDBServer embeddedMongoDBServer;

    @Autowired
    public RuntimeService runtimeService;

    @BeforeAll
    static void setup() {
        embeddedMongoDBServer = EmbeddedMongoDBServer.create().start();
    }

    @AfterAll
    static void tearDown() {
        embeddedMongoDBServer.stop();
    }

    @ParameterizedTest
    @ValueSource(strings = {"delegate-example", "groovy-example", "generic-example"})
    void shouldExecuteDelegate() {
        String processDefinitionKey = "delegate-example";
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
        assertThat(processInstance).isStarted().task();
    }
}
