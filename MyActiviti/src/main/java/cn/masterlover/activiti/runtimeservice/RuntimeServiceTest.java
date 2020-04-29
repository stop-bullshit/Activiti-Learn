package cn.masterlover.activiti.runtimeservice;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class RuntimeServiceTest {
    ProcessEngine processEngine;
    RepositoryService repositoryService;
    RuntimeService runtimeService;
    TaskService taskService;
    HistoryService historyService;

    @Before
    public void init() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
        repositoryService = processEngine.getRepositoryService();
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();
        historyService = processEngine.getHistoryService();
        System.out.println(repositoryService);
    }

    /**
     * description 部署流程
     * params []
     * return void
     * create by Shadow
     * date: 2018/12/2 21:13
     */
    @Test
    public void deploy() {
        String resource = "process/leave.bpmn.xml";
        String category = "leave";
        Deployment deploy = repositoryService.createDeployment().addClasspathResource(resource).category(category).deploy();
        String id = deploy.getId();
        System.out.println(id);
    }

    /**
     * description 启动流程
     * params []
     * return void
     * create by Shadow
     * date: 2018/12/2 21:13
     */
    @Test
    public void startProcess() {
        ProcessInstance leave = runtimeService.startProcessInstanceByKey("leave");
        String processDefinitionId = leave.getProcessDefinitionId();
        BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(processDefinitionId);
        Process process = bpmnModel.getProcesses().get(0);
        List<UserTask> userTaskList = process.findFlowElementsOfType(UserTask.class);
        UserTask startUserTask = userTaskList.get(0);
        String Path = startUserTask.getFormKey();
        System.out.println(Path);


        taskService.deleteTask("taskId");


        System.out.println("======================================");
//        System.out.println(leave.getProcessDefinitionKey());
//        System.out.println(leave.getProcessDefinitionName());
//        System.out.println(leave.getId());
    }


    @Test
    public void getFormKey() {
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//        TaskService taskService = processEngine.getTaskService();
//        ProcessInstance leave = runtimeService.startProcessInstanceByKey("leave");
//        leave.getProcessInstanceId();
//        leave.getProcessDefinitionId();
//        return "";
    }

    /**
     * description 查询个人任务
     * params []
     * return void
     * create by Shadow
     * date: 2018/12/2 21:27
     */
    @Test
    public void taskQuery() {
        String assignee = "张三";
        List<Task> taskQuery = taskService.createTaskQuery().taskAssignee(assignee).list();
        for (Task task : taskQuery) {
            System.out.println(task.getId());
            System.out.println(task.getTaskDefinitionKey());
            System.out.println(task.getName());
        }
    }

    /**
     * description 完成任务
     * params []
     * return void
     * create by Shadow
     * date: 2018/12/2 21:57
     */
    @Test
    public void completeTask() {
        //userTask1:custom-fb11dccc-a3af-44cf-8fa0-67168a2b72c7
        //userTask3:custom-e7ef1553-73ce-428f-8a2f-098b20c23b1d  ExecutionID:custom-8061e155-b285-42d6-b74e-c539baaf2b32
        //userTask3End:custom-7d60cba5-feff-49cc-9567-325e29c8f510  ExecutionID:custom-8061e155-b285-42d6-b74e-c539baaf2b32
        String taskId = "custom-7d60cba5-feff-49cc-9567-325e29c8f510";//ACT_RU_TASK ID_
        taskService.complete(taskId);
    }


    @Test
    public void queryHistoryProcess() {
        historyService.createHistoricTaskInstanceQuery().finished().list();
        historyService.createHistoricProcessInstanceQuery().list();
    }
}
