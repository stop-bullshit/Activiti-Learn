package cn.masterlover.activiti6.resources;

import cn.masterlover.activiti6.conf.DataSourceConfig;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.UserEntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * description 测试接口
 * params
 * return
 * create by heng.wang
 * date: 2018/12/21 17:42
 */
@RestController
@RequestMapping
public class TestResources {


    @Autowired
    private DataSourceConfig dataSourceConfig;

    @Autowired
    private IdentityService identityService;

    @RequestMapping(value = "/")
    @ResponseBody
    public String test() {
        System.out.println(dataSourceConfig.getDataSource());
        return "Master";
    }

    @RequestMapping(value = "/user")
    @ResponseBody
    public UserEntityImpl getUser(HttpServletRequest request) {
        UserEntityImpl user = new UserEntityImpl();
        String id = request.getParameter("id");
        user.setId(id);
        user.setRevision(0);
        identityService.saveUser(user);
        return user;
    }
}
