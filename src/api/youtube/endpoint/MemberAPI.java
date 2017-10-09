package api.youtube.endpoint;

import api.youtube.entity.Member;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import design.java.rest.RESTFactory;
import design.java.rest.RESTGeneralError;
import design.java.rest.RESTGeneralSuccess;
import design.java.rest.RESTHandle;
import design.java.rest.entity.RESTDocumentSingle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by daolinh on 9/29/17.
 */
public class MemberAPI extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(MemberAPI.class.getSimpleName());
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
        LOGGER.info("Gọi member endpoint, method GET.");
        RESTHandle.passRequest(resp, arrayAccept);

        int action = 1;
        System.out.println(req.getRequestURI());
        String[] arrayURI = req.getRequestURI().split("/");
        String id = "";
        System.out.println(arrayURI.length);
        if (arrayURI.length == 3) {
            id = arrayURI[arrayURI.length - 1];
            action = 2;
        }
        switch (action) {
            case 1:
                int page = 1;
                int limit = 10;
                int totalPage = 1;
                int totalItem = 1;
                try {
                    page = Integer.parseInt(req.getParameter("page"));
                    limit = Integer.parseInt(req.getParameter("limit"));
                } catch (Exception e) {
                    page = 1;
                    limit = 10;
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
                List<Member> list = query.limit(limit).offset((page - 1) * limit).list();
                RESTFactory.make(RESTGeneralSuccess.OK).putData(list).putMeta("totalPage", totalPage)
                        .putMeta("totalItem", totalItem).putMeta("limit", limit).putMeta("page", page).doResponse(resp);
                break;
            case 2:
                Member obj = ofy().load().type(Member.class).id(Long.valueOf(id)).now();
                RESTFactory.make(RESTGeneralSuccess.OK).putData(obj).doResponse(resp);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Gọi member endpoint, method POST.");
        RESTHandle.passRequest(resp, arrayAccept);
        try {
            RESTDocumentSingle document = RESTDocumentSingle.getInstanceFromRequest(req);
            Member obj = document.getData().getInstance(Member.class);
            Member existObj = ofy().load().type(Member.class).filter("username", obj.getUsername()).first().now();
            if(!obj.isValid()){
                RESTFactory.make(RESTGeneralError.BAD_REQUEST).putErrors(RESTGeneralError.BAD_REQUEST.code(), "Dữ liệu không hợp .", "Các trường username, email, password phải lớn hơn 7 ký tự và không tồn tại khoảng trắng.", false).doResponse(resp);
                return;
            }
            if(existObj != null){
                RESTFactory.make(RESTGeneralError.CONFLICT).putErrors(RESTGeneralError.CONFLICT.code(), "Conflict username.", "Username đã tồn tại.", false).doResponse(resp);
                return;
            }
            obj.setId(System.currentTimeMillis());
            obj.setCreatedTimeMLS(Calendar.getInstance().getTimeInMillis());
            obj.setUpdatedTimeMLS(Calendar.getInstance().getTimeInMillis());
            obj.setStatus(1);
            ofy().save().entity(obj).now();
            RESTFactory.make(RESTGeneralSuccess.CREATED).putData(obj).doResponse(resp);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            RESTFactory.make(RESTGeneralError.SERVER_ERROR).doResponse(resp);
        }
    }
}
