package cn.masterlover.activiti.runtimeService;

import cn.masterlover.activiti.repositoryservice.MyUserTaskValidator;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.validation.ProcessValidator;
import org.activiti.validation.ProcessValidatorFactory;
import org.activiti.validation.validator.ValidatorSet;
import org.activiti.validation.validator.ValidatorSetNames;

import java.util.List;

public class MySpringProcessEngineConfigurationExt extends SpringProcessEngineConfiguration {
    @Override
    public void initProcessValidator() {
        ProcessValidator processValidator = new ProcessValidatorFactory().createDefaultProcessValidator();
        List<ValidatorSet> validatorSets = processValidator.getValidatorSets();
        validatorSets.add(createValidatorSet());
    }

    private ValidatorSet createValidatorSet() {
        ValidatorSet validatorSet = new ValidatorSet(ValidatorSetNames.ACTIVITI_EXECUTABLE_PROCESS);
        validatorSet.addValidator(new MyUserTaskValidator());
        return validatorSet;
    }
}
