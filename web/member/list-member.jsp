<%@ page import="java.util.List" %>
<%@ page import="api.youtube.entity.Member" %><%--
  Created by IntelliJ IDEA.
  User: daolinh
  Date: 10/4/17
  Time: 2:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Member> listMember =  (List<Member>) request.getAttribute("listMember");
%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <%
        if(listMember != null && listMember.size() > 0){
    %>
        <ul>
    <%
            for(Member mem: listMember){
    %>
            <li>
                <img src="/img/icon.png" alt="">
                <%= mem.getUsername()%> - <%=mem.getFullName()%>
            </li>
    <%
            }
    %>
        </ul>
    <%
        }
    %>
</body>
</html>
