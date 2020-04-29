package cn.masterlover.activiti.runtimeService;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class runtimeServiceTest {

    ProcessEngine processEngine;
    RepositoryService repositoryService;
    RuntimeService runtimeService;

    @Before
    public void init() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
        log.info("get ProcessEngine:  {}", processEngine);

        //生成服务类

        repositoryService = processEngine.getRepositoryService();
        log.info("get repositoryService:  {}", repositoryService);

        runtimeService = processEngine.getRuntimeService();
        log.info("get runtimeService:  {}", runtimeService);
    }


    //测试类方法

    @Test
    public void testDeployment() {
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("process/leave.bpmn").deploy();
        log.info("deployment:id={},name={}", deployment.getId(), deployment.getName());

    }


    @Test
    public void testStartProcess() {
        String leave = "leave";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", "user1");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(leave, map);
        System.out.println(processInstance.getProcessDefinitionId() + "||" + processInstance.getId() + "||" + processInstance.getProcessDefinitionId() + "||" + processInstance.getName() + "||" + processInstance.getStartTime().toGMTString());
    }
}
