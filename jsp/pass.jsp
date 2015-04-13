<!DOCTYPE html> 
<%if(session.getValue("username")==null) response.sendRedirect("yyy.jsp");%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import = "java.io.*" %>
<%@ page import = "com.mysql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.example.*" %>
<%@ page import="jxl.*" %>
<%@ page import="jxl.read.biff.BiffException" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.*" %>

<meta http-equiv="refresh" target="content" content="0; url=rlike.jsp?videoId=<%out.print(request.getParameter("videoId"));%>&user_name=<%out.print(session.getAttribute("username"));%>&score=<%out.print(request.getParameter("star"));%>">
