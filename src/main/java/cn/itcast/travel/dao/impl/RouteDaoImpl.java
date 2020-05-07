package cn.itcast.travel.dao.impl;

import java.util.ArrayList;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    String sql = "";

    @Override
    public int numFav(int rid) {
        sql="select count(*) from tab_favorite where rid=?";
       int count = jp.queryForObject(sql, Integer.class, rid);
        return count;
    }

    @Override
    public void addF(int rid, int uid) {
        sql="insert into tab_favorite(rid,date,uid) values(?,?,?) ";
        jp.update(sql,rid, new Date(),uid);
    }

    private JdbcTemplate jp = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public boolean isF(int rid, int uid) {
        sql= "select count(*) from tab_favorite where rid=? and uid =?";
        int num = 0;
        try {
            num = jp.queryForObject(sql, Integer.class, rid, uid);
        }catch (NumberFormatException e) {

        }

        if (num!=0){
            return true;
        }
        return false;
    }

    @Override
    public int findTotalCount(int cid, String rname) {
        //String sql = "select count(*) from tab_route where cid = ?";
        //1.定义sql模板
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();//条件们
        //2.判断参数是否有值
        if(cid != 0){
            sb.append( " and cid = ? ");

            params.add(cid);//添加？对应的值
        }

        if(rname != null && rname.length() > 0&&!"null".equals(rname)){
            sb.append(" and rname like ? ");

            params.add("%"+rname+"%");
        }

        sql = sb.toString();

        Integer integer = jp.queryForObject(sb.toString(), Integer.class, params.toArray());
        return integer;
    }

    @Override
    public List<Route> findRoutePage(int cid, int start, int pageSize, String rname) {
        //String sql = "select * from tab_route where cid = ? and rname like ?  limit ? , ?";
        String sql = " select * from tab_route where 1 = 1 ";
        //1.定义sql模板
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();//条件们
        //2.判断参数是否有值
        if(cid != 0){
            sb.append( " and cid = ? ");

            params.add(cid);//添加？对应的值
        }
        if(rname != null && rname.length() > 0&&!"null".equals(rname)){
            sb.append(" and rname like ? ");

            params.add("%"+rname+"%");
        }
        sb.append(" limit ? , ? ");//分页条件
        sql = sb.toString();
        params.add(start);
        params.add(pageSize);
        List<Route> queryRoutes = jp.query(sb.toString(), new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        return queryRoutes;
    }

    @Override
    public Route findOne(int rid) {
        sql = "select * from tab_route where rid=?";
        return jp.queryForObject(sql,new BeanPropertyRowMapper<Route> (Route.class),rid);
}

    @Override
    public List<RouteImg> findRouteImg(int rid) {
        sql = "select * from tab_route_img where rid=?";
        return jp.query(sql,new BeanPropertyRowMapper<> (RouteImg.class),rid);

    }

    @Override
    public Seller findSeller(int sid) {
        sql = "select * from tab_seller where sid=?";
        return jp.queryForObject(sql,new BeanPropertyRowMapper<> (Seller.class),sid);
    }
}
