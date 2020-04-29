package cn.masterlover.activiti.repositoryservice;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.*;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.validation.ProcessValidator;
import org.activiti.validation.ProcessValidatorFactory;
import org.activiti.validation.ValidationError;
import org.activiti.validation.validator.ValidatorSet;
import org.activiti.validation.validator.ValidatorSetNames;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ProcessTest {
    ProcessEngine processEngine;
    RepositoryService repositoryService;

    @Before
    public void init() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
        repositoryService = processEngine.getRepositoryService();
        System.out.println(repositoryService);
    }


    @Test
    public void deployProcess() {
        String resources = "cn/masterlover/activiti/repositoryservice/travel_transportation.bpmn20.xml";
        repositoryService.createDeployment().name("测试流程").addClasspathResource(resources).deploy();

    }

    @Test
    public void findFlowElementOfType() {
        BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel("myProcess_1:1:5004");
        Process process = bpmnModel.getProcesses().get(0);
        List<UserTask> userTaskList = process.findFlowElementsOfType(UserTask.class);
        for (UserTask userTask : userTaskList) {
            System.out.println(userTask.toString());
            userTask.getFormKey();
        }
    }


    @Test
    public void generateBpmnModel() {
        BpmnModel bpmnModel = new BpmnModel();
        Process process = new Process();
        process.setId("TestProcess");
        process.setName("TestProcessName");
        process.setExecutable(true);

//        generate开始节点
        StartEvent startEvent1 = generateStartEvent("startEvent1", "开始");
        //        添加开始节点
        process.addFlowElement(startEvent1);

//        generate用户任务
        UserTask userTask1 = generateUserTask("userTask1", "提交申请");
        UserTask userTask2 = generateUserTask("userTask2", "部门领导审批");
        UserTask userTask3 = generateUserTask("userTask3", "经理审批");
        //        添加用户任务
        process.addFlowElement(userTask1);
        process.addFlowElement(userTask2);
        process.addFlowElement(userTask3);

//        generate排他网关
        ExclusiveGateway exclusiveGateway = generateExclusiveGateway("exclusiveGateway1", "排他网关");
        //        添加排他网关
        process.addFlowElement(exclusiveGateway);

//        generate结束节点
        EndEvent endEvent1 = generateEndEvent("endEvent1", "end1");
        EndEvent endEvent2 = generateEndEvent("endEvent2", "end2");
        //        添加结束节点
        process.addFlowElement(endEvent1);
        process.addFlowElement(endEvent2);

//        generate连线

        SequenceFlow sequenceFlow1 = generateSequenceFlow("flow1", null, "startEvent1", "userTask1", null, process);
//                添加连线
        process.addFlowElement(sequenceFlow1);
        SequenceFlow sequenceFlow2 = generateSequenceFlow("flow2", null, "userTask1", "exclusiveGateway1", null, process);
//                添加连线
        process.addFlowElement(sequenceFlow2);

        SequenceFlow sequenceFlow3 = generateSequenceFlow("flow3", "小于三天", "exclusiveGateway1", "userTask2", "${day>3}", process);
//                添加连线
        process.addFlowElement(sequenceFlow3);

        SequenceFlow sequenceFlow4 = generateSequenceFlow("flow4", "大于三天", "exclusiveGateway1", "userTask3", "${day<3}", process);
//                添加连线
        process.addFlowElement(sequenceFlow4);

        SequenceFlow sequenceFlow5 = generateSequenceFlow("flow5", null, "userTask2", "endEvent1", null, process);
//                添加连线
        process.addFlowElement(sequenceFlow5);

        SequenceFlow sequenceFlow6 = generateSequenceFlow("flow6", null, "userTask3", "endEvent2", null, process);
//                添加连线
        process.addFlowElement(sequenceFlow6);

//        添加排他网关出线信息
        List<SequenceFlow> outgoingFlows = new ArrayList<>();
        outgoingFlows.add(sequenceFlow3);
        outgoingFlows.add(sequenceFlow4);
        exclusiveGateway.setOutgoingFlows(outgoingFlows);


        bpmnModel.addProcess(process);


        //转换成XML
        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        byte[] convertToXML = bpmnXMLConverter.convertToXML(bpmnModel);
        String bmpnXML = new String(convertToXML, StandardCharsets.UTF_8);
        System.out.println(bmpnXML);


        //部署BPMNModel
        String resourceName = "customer.bpmn";
        Deployment deploy = repositoryService.createDeployment().addBpmnModel(resourceName, bpmnModel).deploy();
        System.out.println(deploy);
    }

    @Test
    public void validateBpmnModel() {
        BpmnModel bpmnModel = new BpmnModel();
        Process process = new Process();
        process.setId("TestProcess");
        process.setName("TestProcessName");
        process.setExecutable(true);

//        generate开始节点
        StartEvent startEvent1 = generateStartEvent("startEvent1", "开始");
        //        添加开始节点
        process.addFlowElement(startEvent1);

//        generate用户任务
        UserTask userTask1 = generateUserTask("userTask1", "提交申请");
        UserTask userTask2 = generateUserTask("userTask2", "部门领导审批");
        UserTask userTask3 = generateUserTask("userTask3", "经理审批");
        //        添加用户任务
        process.addFlowElement(userTask1);
        process.addFlowElement(userTask2);
        process.addFlowElement(userTask3);

//        generate排他网关
        ExclusiveGateway exclusiveGateway = generateExclusiveGateway("exclusiveGateway1", "排他网关");
        //        添加排他网关
        process.addFlowElement(exclusiveGateway);

//        generate结束节点
        EndEvent endEvent1 = generateEndEvent("endEvent1", "end1");
        EndEvent endEvent2 = generateEndEvent("endEvent2", "end2");
        //        添加结束节点
        process.addFlowElement(endEvent1);
        process.addFlowElement(endEvent2);

//        generate连线

        SequenceFlow sequenceFlow1 = generateSequenceFlow("flow1", null, "startEvent1", "userTask1", null, process);
//                添加连线
        process.addFlowElement(sequenceFlow1);
        SequenceFlow sequenceFlow2 = generateSequenceFlow("flow2", null, "userTask1", "exclusiveGateway1", null, process);
//                添加连线
        process.addFlowElement(sequenceFlow2);

        SequenceFlow sequenceFlow3 = generateSequenceFlow("flow3", "小于三天", "exclusiveGateway1", "userTask2", "${day>3}", process);
//                添加连线
        process.addFlowElement(sequenceFlow3);

        SequenceFlow sequenceFlow4 = generateSequenceFlow("flow4", "大于三天", "exclusiveGateway1", "userTask3", "${day<3}", process);
//                添加连线
        process.addFlowElement(sequenceFlow4);

        SequenceFlow sequenceFlow5 = generateSequenceFlow("flow5", null, "userTask2", "endEvent1", null, process);
//                添加连线
        process.addFlowElement(sequenceFlow5);

        SequenceFlow sequenceFlow6 = generateSequenceFlow("flow6", null, "userTask3", "endEvent2", null, process);
//                添加连线
        process.addFlowElement(sequenceFlow6);

//        添加排他网关出线信息
        List<SequenceFlow> outgoingFlows = new ArrayList<>();
        outgoingFlows.add(sequenceFlow3);
        outgoingFlows.add(sequenceFlow4);
        exclusiveGateway.setOutgoingFlows(outgoingFlows);


        bpmnModel.addProcess(process);

        //转换成XML
        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        byte[] convertToXML = bpmnXMLConverter.convertToXML(bpmnModel);
        String bmpnXML = new String(convertToXML, StandardCharsets.UTF_8);
        System.out.println(bmpnXML);

        //校验BPMNModel
        ProcessValidatorFactory processValidatorFactory = new ProcessValidatorFactory();
        ProcessValidator processValidator = processValidatorFactory.createDefaultProcessValidator();
        List<ValidatorSet> validatorSets = processValidator.getValidatorSets();
        validatorSets.add(createValidatorSet());
        List<ValidationError> validate = processValidator.validate(bpmnModel);
        for (ValidationError validationError : validate) {
            System.out.println("Problem ------->" + validationError.getProblem());
            System.out.println("Warn ------->" + validationError.isWarning());
        }
        System.out.println(validate.size());


//        部署BPMNModel
        String resourceName = "customer.bpmn";
        Deployment deploy = repositoryService.createDeployment().addBpmnModel(resourceName, bpmnModel).deploy();
        System.out.println(deploy);
    }

    /**
     * description 自定义校验器
     * params []
     * return org.activiti.validation.validator.ValidatorSet
     * create by Shadow
     * date: 2018/12/1 23:29
     */
    private ValidatorSet createValidatorSet() {
        ValidatorSet validatorSet = new ValidatorSet(ValidatorSetNames.ACTIVITI_EXECUTABLE_PROCESS);
        validatorSet.addValidator(new MyUserTaskValidator());
        return validatorSet;
    }


    /**
     * description generate连线
     * params [ID, Name, sourceRef, targetRef, conditionExpression]
     * return org.activiti.bpmn.model.SequenceFlow
     * create by Shadow
     * date: 2018/12/1 20:47
     */
    private SequenceFlow generateSequenceFlow(String ID, String Name, String sourceRef, String targetRef, String conditionExpression, Process process) {
        SequenceFlow sequenceFlow = new SequenceFlow();
        sequenceFlow.setId(ID);
        sequenceFlow.setName(Name == null ? "" : Name);
        sequenceFlow.setSourceFlowElement(process.getFlowElement(sourceRef));
        sequenceFlow.setTargetFlowElement(process.getFlowElement(targetRef));
        sequenceFlow.setConditionExpression(conditionExpression == null ? "" : conditionExpression);
        sequenceFlow.setSourceRef(sourceRef);
        sequenceFlow.setTargetRef(targetRef);
        return sequenceFlow;
    }

    /**
     * description generate结束节点
     * params [ID, Name]
     * return org.activiti.bpmn.model.EndEvent
     * create by Shadow
     * date: 2018/12/1 20:40
     */
    private EndEvent generateEndEvent(String ID, String Name) {
        EndEvent endEvent = new EndEvent();
        endEvent.setId(ID);
        endEvent.setName(Name);
        return endEvent;
    }

    /**
     * description generate排他网关
     * params [ID, Name]
     * return org.activiti.bpmn.model.ExclusiveGateway
     * create by Shadow
     * date: 2018/12/1 20:40
     */
    private ExclusiveGateway generateExclusiveGateway(String ID, String Name) {
        ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
        exclusiveGateway.setId(ID);
        exclusiveGateway.setName(Name);
        return exclusiveGateway;
    }

    /**
     * description generate用户任务
     * params [ID, Name]
     * return org.activiti.bpmn.model.UserTask
     * create by Shadow
     * date: 2018/12/1 20:41
     */
    private UserTask generateUserTask(String ID, String Name) {
        UserTask userTask = new UserTask();
        userTask.setId(ID);
        userTask.setName(Name);
        return userTask;
    }

    /**
     * description generate开始节点
     * params [ID, Name]
     * return org.activiti.bpmn.model.StartEvent
     * create by Shadow
     * date: 2018/12/1 20:41
     */
    private StartEvent generateStartEvent(String ID, String Name) {
        StartEvent startEvent = new StartEvent();
        startEvent.setId(ID);
        startEvent.setName(Name);
        return startEvent;
    }
}
