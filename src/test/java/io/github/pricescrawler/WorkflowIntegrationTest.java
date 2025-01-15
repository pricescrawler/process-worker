package io.github.pricescrawler;

import io.github.pricescrawler.util.ContainerIntegrationTest;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;

class WorkflowIntegrationTest extends ContainerIntegrationTest {
    @Autowired
    public RuntimeService runtimeService;

    @ParameterizedTest
    @ValueSource(strings = {"delegate-example", "groovy-example", "generic-example"})
    void shouldExecuteDelegate() {
        String processDefinitionKey = "delegate-example";
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
        assertThat(processInstance).isStarted().task();
    }
}
