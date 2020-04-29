package cn.masterlover.activiti.repositoryservice;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.UserTask;
import org.activiti.validation.ValidationError;
import org.activiti.validation.validator.ProcessLevelValidator;

import java.util.List;

public class MyUserTaskValidator extends ProcessLevelValidator {
    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {
        List<UserTask> userTaskList = process.findFlowElementsOfType(UserTask.class);
        for (UserTask userTask : userTaskList) {
            if (userTask.getAssignee() == null || "".equals(userTask.getAssignee())) {
                addError(errors, "[" + userTask.getId() + "]:" + "没有处理人", process, userTask, "任务节点没有处理人");
            }
        }
    }
}
