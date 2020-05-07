package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private ObjectMapper objectMapper = new ObjectMapper();
    public void queryPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cidStr = req.getParameter("cid");
        String currentPageStr = req.getParameter("currentPage");
        String pageSizeStr = req.getParameter("pageSize");
        String rname = req.getParameter("rname");
        rname =new String(rname.getBytes("iso-8859-1"), "utf-8");

        int cid = 0;
        if (cidStr != null && cidStr.length() > 0&&!"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }
        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }
        if (currentPageStr == null||currentPageStr.length() == 0) {
            currentPageStr = "1";
        }
        int currentPage = Integer.parseInt(currentPageStr);

        PageBean<Route> pb = new RouteServiceImpl().pageRoutes(cid,currentPage, pageSize,rname);
        objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getWriter(),pb);
    }
    public void findOne(HttpServletRequest req,HttpServletResponse resp){
        String rid = req.getParameter("rid");
        Route route = new RouteServiceImpl().findOne(Integer.parseInt(rid));

        try {
            objectMapper.writeValue(resp.getWriter(),route);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void isFavorite(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String rid = req.getParameter("rid");
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            objectMapper.writeValue(resp.getWriter(),false);
            return;
        }
        int uid = user.getUid();
        boolean isF = new RouteServiceImpl().isF(rid,uid);
        objectMapper.writeValue(resp.getWriter(),isF);
    }
    public void addFavorite(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String rid = req.getParameter("rid");
        User user = (User) req.getSession().getAttribute("user");
        new RouteServiceImpl().addF(rid, user.getUid());

    }


}
