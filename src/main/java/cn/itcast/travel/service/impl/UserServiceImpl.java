package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    @Override
    public boolean findUserByUsername(String username) {
      int count =  new UserDaoImpl().findUserByUsername(username);
      if (count > 0) {
          return false;
      }
        return true;
    }

    @Override
    public boolean saveUser(User user) {
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        MailUtils.sendMail(user.getEmail(),"<a href='http://localhost/travel/activeUserServlet?code="+user.getCode()+"'>请点击激活</a>", "【黑马旅游网】激活邮件");
        return new UserDaoImpl().addUser(user);
    }

    @Override
    public boolean activeCode(String code) {
        boolean flag = new UserDaoImpl().findUserByCode(code);
        if (flag) {
            new UserDaoImpl().updateStatus(code);
            return true;
        }
        return false;
    }

    @Override
    public User findUserByUsernameAndPwd(User user) {
        return new UserDaoImpl().findUserByUP(user);
    }

}
