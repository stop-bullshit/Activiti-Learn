package cn.masterlover.activiti.taskService;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Before;

import java.util.List;


@Slf4j
public class TaskServiceTest {
    ProcessEngine processEngine;
    RepositoryService repositoryService;
    RuntimeService runtimeService;
    TaskService taskService;
    HistoryService historyService;

    @Before
    public void init() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
//        log.info("get ProcessEngine:  {}", processEngine);

        //生成服务类

        repositoryService = processEngine.getRepositoryService();
//        log.info("get repositoryService:  {}", repositoryService);

        runtimeService = processEngine.getRuntimeService();
//        log.info("get runtimeService:  {}", runtimeService);

        taskService = processEngine.getTaskService();
        historyService = processEngine.getHistoryService();
    }

    //分配组任务
    //个人任务: TYPE=participant  参与者
    //组任务: TYPE=candidate  候选者  与 participant 参与者 互斥

/**********************************************************************/


    /**
     * description 查询个人任务
     * params []
     * return void
     * create by heng.wang
     * date: 2018/12/16 23:15
     */
    public void taskAssignee() {
        String assignee = "";
        List<Task> list = taskService.createTaskQuery().taskAssignee(assignee).list();
        for (Task task : list) {
            log.info(task.getId());
            log.info(task.getAssignee());
            log.info(task.getName());
        }
    }

    /**
     * description 查询组任务
     * params []
     * return void
     * create by heng.wang
     * date: 2018/12/17 19:27
     */
    public void taskCandidateUser() {
        String candidateUser = "";
        List<Task> list = taskService.createTaskQuery().taskCandidateUser(candidateUser).list();
        for (Task task : list) {
            log.info(task.getId());
        }
    }

    /**
     * description 查询那些人可以处理任务
     * params []
     * return void
     * create by heng.wang
     * date: 2018/12/17 22:30
     */
    public void getIdentityLinksForTask() {
        String taskId = "";
        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(taskId);
        for (IdentityLink identityLink : identityLinksForTask) {
            log.info(identityLink.getUserId());
            log.info(identityLink.getProcessInstanceId());
            log.info(identityLink.getType());
        }
    }

    /**
     * description 删除处理人
     * params []
     * return void
     * create by heng.wang
     * date: 2018/12/17 23:15
     */
    public void deleteCandidateUser() {
        String userId = "";
        String taskId = "";
        taskService.deleteCandidateUser(taskId, userId);
    }

    /**
     * description 历史权限表
     * params []
     * return void
     * create by heng.wang
     * date: 2018/12/17 22:34
     */
    public void getHistoricIdentityLinksForTask() {
        String taskId = "";
        List<HistoricIdentityLink> historicIdentityLinksForTask = historyService.getHistoricIdentityLinksForTask(taskId);
        for (HistoricIdentityLink historicIdentityLink : historicIdentityLinksForTask) {
            log.info(historicIdentityLink.getUserId());
            log.info(historicIdentityLink.getTaskId());
            log.info(historicIdentityLink.getType());
            log.info(historicIdentityLink.getProcessInstanceId());
        }

    }

    /**
     * description 拾取任务/领取任务
     * params []
     * return void
     * create by heng.wang
     * date: 2018/12/17 22:12
     */
    public void claimTask() {
        String taskId = "";
        String userId = "";
        taskService.claim(taskId, userId);
    }


    /**
     * description 归还任务
     * params []
     * return void
     * create by heng.wang
     * date: 2018/12/17 22:40
     */
    public void unclaim() {
        String takId = "";
        taskService.unclaim(takId);
    }


    /**
     * description 完成任务
     * params []
     * return void
     * create by heng.wang
     * date: 2018/12/16 23:26
     */
    public void completeMyTask() {
        String taskId = "";
        taskService.complete(taskId);
    }
/***********************************************************************/

//分配角色组


}
