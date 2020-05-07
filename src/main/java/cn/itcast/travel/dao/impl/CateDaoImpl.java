package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CateDaoImpl {
   JdbcTemplate jp= new JdbcTemplate(JDBCUtils.getDataSource());
   String sql="";
    public List<Category> findAll() {
        sql = "select * from tab_category";
        List<Category> categories = jp.query(sql, new BeanPropertyRowMapper<>(Category.class));
        return categories;
    }
}
