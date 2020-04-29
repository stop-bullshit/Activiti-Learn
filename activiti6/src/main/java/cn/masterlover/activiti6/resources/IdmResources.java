package cn.masterlover.activiti6.resources;

import org.activiti.app.model.idm.ChangePasswordRepresentation;
import org.activiti.app.model.idm.GroupRepresentation;
import org.activiti.app.model.idm.UserRepresentation;
import org.activiti.app.security.SecurityUtils;
import org.activiti.app.service.exception.NotFoundException;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping
public class IdmResources {

    @Autowired
    private IdentityService identityService;

    /**
     * description index界面的权限验证
     * params [request]
     * return java.lang.String
     * create by heng.wang
     * date: 2018/12/21 17:42
     */
    @RequestMapping(value = "/authenticate")
    @ResponseBody
    public String authenticate(HttpServletRequest request) {
        return "{\"login\":\"admin\"}";
    }

    /**
     * description 权限接口
     * params [request]
     * return java.lang.String
     * create by heng.wang
     * date: 2018/12/21 17:41
     */
    @RequestMapping(value = "/account")
    @ResponseBody
    public String account(HttpServletRequest request) {
        return "{\"id\":\"admin\",\"firstName\":null,\"lastName\":\"Administrator\",\"email\":\"admin\",\"fullName\":\" Administrator\",\"groups\":[{\"id\":\"ROLE_ADMIN\",\"name\":\"Superusers\",\"type\":\"security-role\"}]}";
    }

    /**
     * description 个人信息
     * params []
     * return org.activiti.app.model.idm.UserRepresentation
     * create by heng.wang
     * date: 2018/12/21 17:41
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public UserRepresentation getProfile() {
        User user = SecurityUtils.getCurrentUserObject();
        UserRepresentation userRepresentation = new UserRepresentation(user);
        List<Group> groups = identityService.createGroupQuery().groupMember(user.getId()).list();
        for (Group group : groups) {
            userRepresentation.getGroups().add(new GroupRepresentation(group));
        }

        return userRepresentation;
    }

    /**
     * description 修改密码接口
     * params [changePasswordRepresentation]
     * return void
     * create by heng.wang
     * date: 2018/12/21 17:40
     */
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/profile-password", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public void changePassword(@RequestBody ChangePasswordRepresentation changePasswordRepresentation) {
        User user = identityService.createUserQuery().userId(SecurityUtils.getCurrentUserId()).singleResult();
        if (!user.getPassword().equals(changePasswordRepresentation.getOriginalPassword())) {
            throw new NotFoundException();
        }
        user.setPassword(changePasswordRepresentation.getNewPassword());
        identityService.saveUser(user);
    }

}
