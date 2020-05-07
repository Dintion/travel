package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author lc
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
   public void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String checkCode = req.getParameter("check");
       String checkCode_server = (String) req.getSession().getAttribute("CHECKCODE_SERVER");
       ResultInfo resultInfo = new ResultInfo();
       if (checkCode_server!=null&&checkCode_server.equals(checkCode)) {
           String username = req.getParameter("username");
           boolean flag = new UserServiceImpl().findUserByUsername(username);
           if (flag) {
               User user = new User();
               Map<String, String[]> map = req.getParameterMap();
               try {
                   BeanUtils.populate(user, map);
               } catch (Exception e) {
                   e.printStackTrace();
               }
               flag =  new UserServiceImpl().saveUser(user);
               if (flag) {
                   resultInfo.setFlag(true);
               }else {
                   resultInfo.setFlag(false);
                   resultInfo.setErrorMsg("注册失败");
               }
           }else {
               resultInfo.setFlag(false);
               resultInfo.setErrorMsg("用户已存在");
           }
       }else {
           resultInfo.setFlag(false);
           resultInfo.setErrorMsg("验证码不正确");
       }
       ObjectMapper mapper = new ObjectMapper();
       String s = mapper.writeValueAsString(resultInfo);
       resp.getWriter().write(s);
    }
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        User u =  new UserServiceImpl().findUserByUsernameAndPwd(user);
        ResultInfo resultInfo = new ResultInfo();
        if (u == null) {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名或密码错误");
        }else if("N".equals(u.getStatus())){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户未激活");
        }else {
            resultInfo.setFlag(true);
            req.getSession().setAttribute("user",u);
        }
        ObjectMapper objectMapper= new ObjectMapper();
        objectMapper.writeValue(resp.getWriter(),resultInfo);
    }
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect("login.html");
    }
    public void findOne(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object user = req.getSession().getAttribute("user");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getWriter(), user);
    }
    public void active(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf8");
        String code = req.getParameter("code");
        String msg="";
        if (code == null) {
            msg = "激活码为空";
        }else {
            boolean flag =  new UserServiceImpl().activeCode(code);
            if (flag) {
                msg="激活成功点<a href='login.html'>此</a>登录";
            }else {
                msg = "激活失败";
            }
        }
        resp.getWriter().write(msg);
    }
}
