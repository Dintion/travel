package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    int findUserByUsername(String username);

    boolean addUser(User user);

    boolean findUserByCode(String code);

    void updateStatus(String code);

    User findUserByUP(User user);
}
