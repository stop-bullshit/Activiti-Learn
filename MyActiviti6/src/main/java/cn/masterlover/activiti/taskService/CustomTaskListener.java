package cn.masterlover.activiti.taskService;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;

public class CustomTaskListener implements TaskListener {

    /**
     * description 任务监听器方式设置执行人
     * params [delegateTask]
     * return void
     * create by heng.wang
     * date: 2018/12/16 23:43
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        String assignee = "user1";
        delegateTask.setAssignee(assignee);
        //框架BUG 历史记录表不保存监听器设置的执行人 手动保存
        CommandContext commandContext = Context.getCommandContext();
        commandContext.getHistoryManager().recordTaskAssigneeChange(delegateTask.getId(), assignee);
    }

}
