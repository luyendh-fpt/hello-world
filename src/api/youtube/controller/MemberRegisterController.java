package api.youtube.controller;

import api.youtube.entity.Member;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by daolinh on 10/4/17.
 */
public class MemberRegisterController extends HttpServlet {

    static {
        ObjectifyService.register(Member.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String birthDay = req.getParameter("birthDay");
        String gender = req.getParameter("gender");
        String avatar = req.getParameter("avatar");
        Member member = new Member();
        member.setId(System.currentTimeMillis());
        member.setUsername(username);
        member.setFullName(fullName);
        member.setEmail(email);
        // Xử lý birthday.
        member.setPassword(password);
        member.setGender(Integer.parseInt(gender));
        member.setAvatar(avatar);
        member.setCreatedTimeMLS(System.currentTimeMillis());
        member.setUpdatedTimeMLS(System.currentTimeMillis());
        member.setStatus(1);
        if (member.isValid()) {
            ofy().save().entity(member).now();
            resp.getWriter().println("Success");
        } else {
            resp.sendError(400, "Thông tin đăng ký không hợp lệ, " +
                    "username, email, password " +
                    "phải có độ dài hơn 7 ký tự và không có khoảng trắng.");
        }
    }
}
