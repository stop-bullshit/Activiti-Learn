package cn.masterlover.activiti6.conf;

import org.activiti.content.storage.api.ContentStorage;
import org.activiti.content.storage.fs.FileSystemContentStorage;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.runtime.Clock;
import org.activiti.form.api.FormRepositoryService;
import org.activiti.form.api.FormService;
import org.activiti.form.engine.impl.FormRepositoryServiceImpl;
import org.activiti.form.engine.impl.FormServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@ComponentScan
public class BeanConfig {

    @Bean
    public org.activiti.content.storage.api.ContentStorage contentStorage() {
        ContentStorage contentStorage = new FileSystemContentStorage(new File("/"), 1024, 1);
        return contentStorage;
    }

    @Bean
    public org.activiti.engine.runtime.Clock clock() {
        Clock clock = new DefaultClockImpl();
        return clock;
    }

    @Bean
    public org.activiti.form.api.FormRepositoryService formRepositoryService() {
        FormRepositoryService formRepositoryService = new FormRepositoryServiceImpl();
        return formRepositoryService;
    }

    @Bean
    public org.activiti.form.api.FormService formService() {
        FormService formService = new FormServiceImpl();
        return formService;
    }

}
