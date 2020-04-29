package cn.masterlover.activiti.variable;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.task.Task;
import org.junit.Before;

import java.util.HashMap;
import java.util.List;

@Slf4j
public class VariablesTest {
    ProcessEngine processEngine;
    RepositoryService repositoryService;
    RuntimeService runtimeService;
    TaskService taskService;
    HistoryService historyService;
    IdentityService identityService;

    @Before
    public void init() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
        repositoryService = processEngine.getRepositoryService();
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();
        historyService = processEngine.getHistoryService();
        identityService = processEngine.getIdentityService();
    }

    public void setVariablesTest() {
        String assignee = "";
        String id = "";
        List<Task> list = taskService.createTaskQuery().taskAssignee(assignee).list();
        for (Task task : list) {
            log.info(task.getId());
            id = task.getId();
            log.info(task.getName());
        }
        //String 形式
        taskService.setVariable(id, "请假人", "user1");

        //Map形式
        HashMap<String, Object> map = new HashMap<>();
        taskService.setVariables(id, map);
        //Entity 形式
        //序列化之后数据会存在ACT_GE_BYTElARRAY
        VariableEntity variableEntity = new VariableEntity();
        variableEntity.setDay(2);
        variableEntity.setName("user1");
        taskService.setVariable(id, "请假信息", variableEntity);
    }
}
