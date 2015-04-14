/*****************************
Simple version
Only include delete video and resort database
******************************/

<%
	try{
        Class.forName("com.mysql.xxx.Driver");
        Connection conn = DriverManager.getConnection("xxx:Address", "root", "2012moea");
        Statement stmt = conn.createStatement();
		String sql = "select * from video_list where videoId='" + request.getParameter("fileId") +"'";
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		String filename = "";
		filename = rs.getString("file_name");

		int nowID = rs.getInt("ID");
		int delID = nowID+1;
		int i;
		Statement stmtcnt = conn.createStatement();
		String sqlcnt = "select count(ID) from video_list";
		ResultSet rscnt = stmtcnt.executeQuery(sqlcnt);
		rscnt.next();
		String cnt = rscnt.getString("count(ID)");
		Integer cnti = Integer.parseInt(cnt);

		Statement stmtID = conn.createStatement();
		for(i=nowID; i<cnti; i++){
				String sqlID = "update video_list set ID='"+i+"' where ID='"+ delID +"'";
				stmtID.executeUpdate(sqlID);
				delID++;
		}

		Statement stmt2 = conn.createStatement();
        String sql2 = "delete from video_list where videoId='" + request.getParameter("fileId") +"'";
        stmt2.executeUpdate(sql2);

		Statement stmt3 = conn.createStatement();
        String sql3 = "delete from rank where videoId='" + request.getParameter("fileId") +"'";
        stmt3.executeUpdate(sql3);

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
<meta http-equiv="refresh" content="0; url=liketxt.jsp">
