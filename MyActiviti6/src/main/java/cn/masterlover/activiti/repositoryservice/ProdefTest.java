package cn.masterlover.activiti.repositoryservice;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ProdefTest {

    ProcessEngine processEngine;
    RepositoryService repositoryService;

    @Before
    public void init() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
        repositoryService = processEngine.getRepositoryService();
        System.out.println(repositoryService);
    }

    @Test
    public void query() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion()
                .asc()
                .list();
        for (ProcessDefinition processDefinition : list) {
            System.out.println("ProcessDefinition:[" + processDefinition.getId() + "]--[" + processDefinition.getName() + "]--[" + processDefinition.getKey() + "]--[" + processDefinition.getVersion() + "]");
        }
    }

    @Test
    public void delete() {
        String deploymentId = "10001";
        repositoryService.deleteDeployment(deploymentId);
    }

    /**
     * description
     * params []
     * return void
     * create by Shadow
     * date: 2018/12/2 2:41
     */
    @Test
    public void nativeDeployementQuery() {
        String sql = "select * from ACT_RE_DEPLOYMENT";
        List<Deployment> deploymentList = repositoryService.createNativeDeploymentQuery().sql(sql).list();
        for (Deployment deployment : deploymentList) {
            System.out.println("Found:["+deployment.getId()+"]-["+deployment.getKey()+"]-["+deployment.getName()+"]");
        }
    }
}
