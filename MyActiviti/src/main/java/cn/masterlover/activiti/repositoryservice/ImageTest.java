package cn.masterlover.activiti.repositoryservice;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageTest {
    ProcessEngine processEngine;
    RepositoryService repositoryService;

    @Before
    public void init() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
        repositoryService = processEngine.getRepositoryService();
        System.out.println(repositoryService);
    }

    @Test
    public void viewImage() throws IOException {
        String deploymentId = "custom-b1b35e6d-fd80-42cd-be5b-aca81ab18136";
        List<String> deploymentResourceNames = repositoryService.getDeploymentResourceNames(deploymentId);
        String imageName = null;
        for (String deploymentResourceName : deploymentResourceNames) {
            if (deploymentResourceName.contains(".png")) {
                imageName = deploymentResourceName;
            }
        }
        System.out.println("imageName:-" + imageName);
        if (imageName != null) {
            InputStream inputStream = repositoryService.getResourceAsStream(deploymentId, imageName);
            FileUtils.copyInputStreamToFile(inputStream, new File("D:/Project/MyProject/" + imageName));
        }
    }

    @Test
    public void generateDiagram() throws IOException {
        ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator processDiagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        System.out.println("processDiagramGenerator:--" + processDiagramGenerator);
        String processDefinitionId = "custom-f6018d3e-bdfa-4aaf-8f0e-12ce5b616211";
        List<String> heightLightActivities = new ArrayList<>();
        List<String> heightLightFlows = new ArrayList<>();
        heightLightActivities.add("ks");
        heightLightActivities.add("productTraffic");
        heightLightFlows.add("sid-5F52C212-8508-4C9B-9589-BDE40751644D");
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        InputStream inputStream = processDiagramGenerator.generateDiagram(bpmnModel, "PNG", heightLightActivities, heightLightFlows);
        FileUtils.copyInputStreamToFile(inputStream, new File("D:/Project/MyProject/" + "1.png"));
    }

    @Test
    public void customerProcessDiagramGenerator() throws IOException {
        MyProcessDiagramGenerator processDiagramGenerator = new MyProcessDiagramGenerator();
        System.out.println("processDiagramGenerator:--" + processDiagramGenerator);
        String processDefinitionId = "custom-f6018d3e-bdfa-4aaf-8f0e-12ce5b616211";
        List<String> heightLightActivities = new ArrayList<>();
        List<String> heightLightFlows = new ArrayList<>();
        heightLightActivities.add("ks");
        heightLightActivities.add("productTraffic");
        heightLightFlows.add("sid-5F52C212-8508-4C9B-9589-BDE40751644D");
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        InputStream inputStream = processDiagramGenerator.generateDiagram(bpmnModel, "PNG", heightLightActivities, heightLightFlows, "宋体", "宋体", "宋体", null, 1.0D);
        FileUtils.copyInputStreamToFile(inputStream, new File("D:/Project/MyProject/" + "2.png"));
    }
}
