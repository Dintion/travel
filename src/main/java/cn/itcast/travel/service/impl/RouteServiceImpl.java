package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private final RouteDao rp = new RouteDaoImpl();


    @Override
    public PageBean<Route> pageRoutes(int cid, int currentPage, int pageSize, String rname) {
        PageBean<Route> pb = new PageBean<>();
        RouteDaoImpl rd = new RouteDaoImpl();
        int totalCount = rd.findTotalCount(cid,rname);
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        pb.setTotalCount(totalCount);
        pb.setTotalPage(totalPage);
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        int start = (currentPage - 1) * pageSize;
        List<Route> routePage = new RouteDaoImpl().findRoutePage(cid, start, pageSize,rname);
        pb.setList(routePage);
        return pb;
    }

    /**
     * 根据rid查询route
     * @param rid
     * @return Route对象
     */
    @Override
    public Route findOne(int rid) {
        Route route = rp.findOne(rid);
        route.setRouteImgList(rp.findRouteImg(route.getRid()));
        route.setSeller(rp.findSeller(route.getSid()));
        route.setCount(new RouteDaoImpl().numFav(rid));
        return route;
    }


    @Override
    public void addF(String rid, int uid) {
        new RouteDaoImpl().addF(Integer.parseInt(rid),uid);
    }

    @Override
    public int findFav(String rid) {
        return 0;
    }

    @Override
    public boolean isF(String rid, int uid) {
        return new RouteDaoImpl().isF(Integer.parseInt(rid),uid);
    }
}
