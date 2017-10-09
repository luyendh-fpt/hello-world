package api.youtube.controller;

import api.youtube.entity.Member;
import design.java.rest.RESTFactory;
import design.java.rest.RESTGeneralSuccess;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by daolinh on 9/28/17.
 */
public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Member mem = new Member();
        mem.setId(System.currentTimeMillis());
        mem.setEmail("xuanhung2401@gmail.com");
        RESTFactory.make(RESTGeneralSuccess.OK).putData(mem).doResponse(resp);
//        resp.getWriter().println("Hello world.");
    }
}
