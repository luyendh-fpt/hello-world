package api.youtube.controller;

import api.youtube.endpoint.MemberAPI;
import api.youtube.entity.Member;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by daolinh on 10/4/17.
 */
public class ListMemberController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(MemberAPI.class.getSimpleName());

    static {
        ObjectifyService.register(Member.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Gọi list member, method GET.");
        int page = 1;
        int limit = 100;
        int totalPage = 1;
        int totalItem = 1;
        try {
            page = Integer.parseInt(req.getParameter("page"));
            limit = Integer.parseInt(req.getParameter("limit"));
        } catch (Exception e) {
            page = 1;
            limit = 100;
        }
        Query<Member> query = ofy().load().type(Member.class).filter("status", 1);
        totalItem = query.count();
        totalPage = totalItem / limit;
                /*
                 * Thêm một vào tổng số trang nếu chia dư.
                 */
        if (totalItem % limit > 0) {
            totalPage++;
        }
        List<Member> list = query.limit(limit).offset((page - 1) * limit).order("-createdTimeMLS").list();
        req.setAttribute("listMember", list);
        req.getRequestDispatcher("list-member.jsp").forward(req, resp);
    }
}
