package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    JdbcTemplate jp = new JdbcTemplate(JDBCUtils.getDataSource());
    String sql = "";

    @Override
    public int findUserByUsername(String username) {
        sql = "select count(*) from tab_user where username=?";
        Integer integer = jp.queryForObject(sql, Integer.class, username);
        return integer.intValue();
    }

    @Override
    public boolean addUser(User user) {
        //1.定义sql
        sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        //2.执行sql
        try {
            jp.update(sql, user.getUsername(),
                    user.getPassword(),
                    user.getName(),
                    user.getBirthday(),
                    user.getSex(),
                    user.getTelephone(),
                    user.getEmail(),
                    user.getStatus(),
                    user.getCode()
            );
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean findUserByCode(String code) {
        sql = "select count(*) from tab_user where code =?";
        Integer integer = jp.queryForObject(sql, Integer.class, code);
        if (integer == 1) {
            return true;
        }
        return false;
    }

    @Override
    public void updateStatus(String code) {
        sql = "update tab_user set status = 'Y' where code=?";
        jp.update(sql,code);
    }

    @Override
    public User findUserByUP(User user) {
        User u = null;
        try {
            sql = "select *from tab_user where username=? and password=?";
            u = jp.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), user.getUsername(), user.getPassword());
        } catch (DataAccessException e) {
        }
        return u;
    }
}
