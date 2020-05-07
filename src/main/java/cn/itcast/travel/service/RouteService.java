package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {
    PageBean<Route> pageRoutes(int cid, int currentPage, int pageSize, String rname);

    Route findOne(int parseInt);

    int findFav(String rid);
    boolean isF(String rid, int uid);

    void addF(String rid, int uid);

}
