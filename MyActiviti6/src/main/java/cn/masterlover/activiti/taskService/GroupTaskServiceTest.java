package cn.masterlover.activiti.taskService;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.impl.persistence.entity.GroupEntityImpl;
import org.activiti.engine.impl.persistence.entity.UserEntityImpl;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

@Slf4j
public class GroupTaskServiceTest {
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

    @Test
    public void createGroup() {
        GroupEntityImpl groupEntity = getGroupEntity("dept1", "dept1");
        groupEntity.setRevision(0);
        identityService.saveGroup(groupEntity);
        UserEntityImpl user1 = createUser("user1", "user1");
        user1.setRevision(0);
        UserEntityImpl user2 = createUser("user2", "user2");
        user2.setRevision(0);
        identityService.saveUser(user1);
        identityService.saveUser(user2);
    }

    /**
     * description 生成角色组
     * params [id, name]
     * return org.activiti.engine.impl.persistence.entity.GroupEntityImpl
     * create by heng.wang
     * date: 2018/12/18 0:09
     */
    private GroupEntityImpl getGroupEntity(String id, String name) {
        GroupEntityImpl groupEntity = new GroupEntityImpl();
        groupEntity.setId(id);
        groupEntity.setName(name);
        return groupEntity;
    }

    /**
     * description 生成用户
     * params [id, name]
     * return org.activiti.engine.impl.persistence.entity.UserEntityImpl
     * create by heng.wang
     * date: 2018/12/18 0:09
     */
    private UserEntityImpl createUser(String id, String name) {
        UserEntityImpl userEntity = new UserEntityImpl();
        userEntity.setId(id);
        userEntity.setFirstName(name);
        return userEntity;
    }

    /**
     * description 建立用户与组之间的关系
     * params []
     * return void
     * create by heng.wang
     * date: 2018/12/18 0:09
     */
    @Test
    public void createMemberShip() {
        identityService.createMembership("user1", "dept1");
        identityService.createMembership("user2", "dept2");
    }

    public void taskCandidateGroup() {
        String candidateGroup = "dept1";
        List<Task> list = taskService.createTaskQuery().taskCandidateGroup(candidateGroup).list();
        for (Task task : list) {
            log.info(task.getId());
            log.info(task.getName());
        }
    }
}
