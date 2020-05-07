package cn.itcast.travel.service.impl;
import	java.util.ArrayList;

import cn.itcast.travel.dao.impl.CateDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CateService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Set;

public class CateServiceImpl implements CateService {
    @Override
    public List<Category> findAll() {
        Jedis jedis = JedisUtil.getJedis();
        List<Category> list = null;
        Set<Tuple> categories = jedis.zrangeWithScores("categories", 0, -1);
        if(categories==null||categories.size()==0){
             list = new CateDaoImpl().findAll();
            for (int i = 0; i < list.size(); i++) {
                jedis.zadd("categories",list.get(i).getCid(),list.get(i).getCname());
            }
        }else {
            list = new ArrayList<Category> ();
            for (Tuple category : categories) {
                Category c = new Category();
                c.setCname(category.getElement());
                c.setCid((int)category.getScore());
                list.add(c);
            }
        }

        return list;
    }
}
