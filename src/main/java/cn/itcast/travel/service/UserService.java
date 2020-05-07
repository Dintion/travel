package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    boolean findUserByUsername(String username);
    boolean saveUser(User user);
    boolean activeCode(String code);

    User findUserByUsernameAndPwd(User user);
}
