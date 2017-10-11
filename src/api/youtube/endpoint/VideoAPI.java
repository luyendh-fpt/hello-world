package api.youtube.endpoint;

import api.youtube.entity.Member;
import api.youtube.entity.MemberCredential;
import api.youtube.entity.Playlist;
import api.youtube.entity.Video;
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
public class VideoAPI extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(VideoAPI.class.getSimpleName());
    private static ArrayList<String> arrayAccept = new ArrayList<>();

    static {
        ObjectifyService.register(Video.class);
        ObjectifyService.register(Playlist.class);
        ObjectifyService.register(Member.class);
        ObjectifyService.register(MemberCredential.class);
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
        LOGGER.info("Gọi playlist endpoint, method GET.");
        RESTHandle.passRequest(resp, arrayAccept);

        MemberCredential credential = MemberCredential.loadCredential(req.getHeader("Authorization"));
        if (credential == null) {
            RESTFactory.make(RESTGeneralError.FORBIDDEN)
                    .putErrors(
                            RESTGeneralError.FORBIDDEN.code(),
                            "Token không hợp lệ.",
                            "Không tồn tại thông tin token. Token hết hạn hoặc đã bị xoá.",
                            false).doResponse(resp);
            return;
        }

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
                Query<Video> query = ofy().load().type(Video.class).filter("createdBy", credential.getUserId()).filter("status", 1).order("-createdTimeMLS");
                totalItem = query.count();
                totalPage = totalItem / limit;
                /*
                 * Thêm một vào tổng số trang nếu chia dư.
                 */
                if (totalItem % limit > 0) {
                    totalPage++;
                }
                List<Video> list = query.limit(limit).offset((page - 1) * limit).list();
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
        LOGGER.info("Gọi playlist endpoint, method POST.");
        RESTHandle.passRequest(resp, arrayAccept);
        MemberCredential credential = MemberCredential.loadCredential(req.getHeader("Authorization"));
        if (credential == null) {
            RESTFactory.make(RESTGeneralError.FORBIDDEN)
                    .putErrors(
                            RESTGeneralError.FORBIDDEN.code(),
                            "Token không hợp lệ.",
                            "Không tồn tại thông tin token. Token hết hạn hoặc đã bị xoá.",
                            false).doResponse(resp);
            return;
        }

        try {
            RESTDocumentSingle document = RESTDocumentSingle.getInstanceFromRequest(req);
            Video obj = document.getData().getInstance(Video.class);
            if (!obj.isValid()) {
                RESTFactory.make(RESTGeneralError.BAD_REQUEST).putErrors(RESTGeneralError.BAD_REQUEST.code(), "Dữ liệu không hợp .", "Trường name phải lớn hơn 7 ký tự.", false).doResponse(resp);
                return;
            }
            obj.setCreatedTimeMLS(Calendar.getInstance().getTimeInMillis());
            obj.setUpdatedTimeMLS(Calendar.getInstance().getTimeInMillis());
            obj.setCreatedBy(credential.getUserId());
            obj.setStatus(1);
            ofy().save().entity(obj).now();
            RESTFactory.make(RESTGeneralSuccess.CREATED).putData(obj).doResponse(resp);
            return;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            RESTFactory.make(RESTGeneralError.SERVER_ERROR).doResponse(resp);
            return;
        }
    }
}
