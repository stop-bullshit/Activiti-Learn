package cn.masterlover.activiti.historyService;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

@Slf4j
public class HistoryServiceTest {
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

    /**
     * description 查询历史流程记录
     * params []
     * return void
     * create by heng.wang
     * date: 2018/12/18 21:41
     */
    @Test
    public void queryHistoryProcessInstance() {
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().finished().list();
        for (HistoricProcessInstance historicProcessInstance : list) {
            log.info(historicProcessInstance.getId());
        }
    }


    /**
     * description 查询所有流程节点
     * params []
     * return void
     * create by heng.wang
     * date: 2018/12/18 21:51
     */
    @Test
    public void ActivityInstanceQuery() {
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().list();
        for (HistoricActivityInstance historicActivityInstance : list) {
//            log.info(historicActivityInstance.getId());
            log.info(historicActivityInstance.getActivityName());
        }
    }

    /**
     * description 查询任务节点
     * params []
     * return void
     * create by heng.wang
     * date: 2018/12/18 21:56
     */
    @Test
    public void TaskInstanceQuery() {
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().list();
        for (HistoricTaskInstance historicActivityInstance : list) {
//            log.info(historicActivityInstance.getId());
            log.info(historicActivityInstance.getName());
        }
    }

    /**
     * description 查询所有的流程变量
     * params []
     * return void
     * create by heng.wang
     * date: 2018/12/18 21:58
     */
    @Test
    public void queryHistoryVariable() {
        List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().list();
        for (HistoricVariableInstance historicVariableInstance : list) {
            log.info(historicVariableInstance.getProcessInstanceId());
            log.info(historicVariableInstance.getVariableName());
            log.info(String.valueOf(historicVariableInstance.getValue()));
        }
    }


    /**
     * description 查询流程实例历史日志
     * params []
     * return void
     * create by heng.wang
     * date: 2018/12/18 22:06
     */
    @Test
    public void queryHistoryInstanceLog() {
        String procId = "42501";
        ProcessInstanceHistoryLog processInstanceHistoryLog = historyService.createProcessInstanceHistoryLogQuery(procId).singleResult();
        log.info(String.valueOf(processInstanceHistoryLog));
    }
}
