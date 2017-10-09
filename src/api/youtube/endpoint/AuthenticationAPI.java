package api.youtube.endpoint;

import api.youtube.entity.Member;
import api.youtube.entity.MemberLogin;
import com.googlecode.objectify.ObjectifyService;
import design.java.rest.RESTFactory;
import design.java.rest.RESTGeneralError;
import design.java.rest.RESTGeneralSuccess;
import design.java.rest.RESTHandle;
import design.java.rest.entity.RESTDocumentSingle;
import design.java.rest.util.RESTJsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by daolinh on 9/29/17.
 */
public class AuthenticationAPI extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationAPI.class.getSimpleName());
    private static ArrayList<String> arrayAccept = new ArrayList<>();

    static {
        ObjectifyService.register(Member.class);
        arrayAccept.add("GET");
        arrayAccept.add("POST");
        arrayAccept.add("PUT");
        arrayAccept.add("DELETE");
        arrayAccept.add("OPTION");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RESTHandle.doOption(resp, arrayAccept);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("currentMemberID") != null){
            Member member = ofy().load().type(Member.class).id(Long.parseLong(String.valueOf(session.getAttribute("currentMemberID")))).now();
            if (member == null){
                RESTFactory.make(RESTGeneralError.NOT_FOUND)
                        .putErrors(
                                RESTGeneralError.NOT_FOUND.code(),
                                "Sai thông tin tài khoản.",
                                "Tài khoản không tồn tại hoặc đã bị xoá.",
                                false).doResponse(resp);
                return;
            }
            RESTFactory.make(RESTGeneralSuccess.OK).putData(member).doResponse(resp);
        }else{
            RESTFactory.make(RESTGeneralError.FORBIDDEN).doResponse(resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Gọi authentication endpoint, method POST.");
        RESTHandle.passRequest(resp, arrayAccept);
        try {
            RESTDocumentSingle document = RESTDocumentSingle.getInstanceFromRequest(req);
            MemberLogin obj = document.getData().getInstance(MemberLogin.class);
            LOGGER.info("--->"+RESTJsonUtil.GSON.toJson(obj));
            Member existObj = ofy().load().type(Member.class).filter("username", obj.getUsername()).first().now();
            LOGGER.info("--->"+RESTJsonUtil.GSON.toJson(existObj));
            if (existObj != null && existObj.getPassword().equals(obj.getPassword())) {
                HttpSession session = req.getSession();
                session.setAttribute("currentMemberID", existObj.getId());
                RESTFactory.make(RESTGeneralSuccess.OK).putData(obj).doResponse(resp);
            } else {
                RESTFactory.make(RESTGeneralError.INVALID_CREDENTIALS)
                        .putErrors(
                                RESTGeneralError.INVALID_CREDENTIALS.code(),
                                "Đăng nhập thất bại",
                                "Thông tin đăng nhập không chính xác",
                                false).doResponse(resp);
            }

        } catch (Exception e) {
            e.printStackTrace(System.err);
            RESTFactory.make(RESTGeneralError.SERVER_ERROR).doResponse(resp);
        }
    }
}
