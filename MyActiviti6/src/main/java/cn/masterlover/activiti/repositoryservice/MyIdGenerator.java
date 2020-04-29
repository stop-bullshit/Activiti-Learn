package cn.masterlover.activiti.repositoryservice;

import org.activiti.engine.impl.cfg.IdGenerator;

import java.util.UUID;

public class MyIdGenerator implements IdGenerator {
    @Override
    public String getNextId() {
        return "custom-" + UUID.randomUUID().toString();
    }
}
