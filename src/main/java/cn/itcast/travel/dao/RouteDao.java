package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;

import java.util.List;

public interface RouteDao {
    public int findTotalCount(int cid, String rname);
    public List<Route> findRoutePage(int cid, int start, int pageSize, String rname);

    Route findOne(int rid);

    List<RouteImg> findRouteImg(int rid);

    Seller findSeller(int sid);

    boolean isF(int parseInt, int uid);

    void addF(int parseInt, int uid);

    int numFav(int rid);
}
