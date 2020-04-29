package cn.masterlover.activiti6.Intercepter;

import org.activiti.app.security.SecurityUtils;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.UserEntityImpl;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 配置拦截器
 */
public class MasterHandlerIntercept implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        if (servletPath.startsWith("/app") || servletPath.startsWith("/master") || servletPath.startsWith("/idm")) {
            User currentUserObject = SecurityUtils.getCurrentUserObject();
            if (currentUserObject == null) {
                UserEntityImpl user = new UserEntityImpl();
                user.setId("master");
                SecurityUtils.assumeUser(user);
            }
        }
        return true;
    }
}
