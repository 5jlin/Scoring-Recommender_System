<%if(session.getValue("username")==null) response.sendRedirect("yyy.jsp");%>
<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.mysql.*" %>

<%
        String rec ="";
        rec = "Address/exerec.sh ";
        Process process = Runtime.getRuntime().exec(rec);

try{
        Class.forName("com.mysql.xxx.Driver");
        Connection conn = DriverManager.getConnection("xxx:Database Address", "ID", "PW");
		Statement stmt = conn.createStatement();
        String sql = "delete from rank where videoId='" + request.getParameter("videoId") +"' and userId='"+request.getParameter("userId")+"'";
        stmt.executeUpdate(sql);
		stmt.close();	
		stmt = null;
		conn.close();
}
catch(ClassNotFoundException e){
        out.println("Driver Class not found");
        e.printStackTrace();
}
catch(SQLException ex){
        out.println("SQL Exception");
        ex.printStackTrace();
}

%>
<meta http-equiv="refresh" content="0; url=liketxt.jsp?">
