<!DOCTYPE html> 

/***********************************************
simple version
Only contain how to add new data to database
************************************************/

<%

int userId = 0;
int videoId = 0;
int ID = 0;

try{
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/moea_Database", "root", "2012moea");
        Statement stmt = conn.createStatement();

		String sql2 = "";		
		sql2 = "insert into video_list (videoId, video_name, ownerId, count, description, file_name, upload_time, ID) values('...')";
        stmt.executeUpdate(sql2);
		
}
catch(ClassNotFoundException e){
        out.println("Driver Class not found");
        e.printStackTrace();
}
catch(SQLException ex){
        out.println("SQL Exception");
        ex.printStackTrace();
}

<meta http-equiv="refresh" target="content" content="0; url=liketxt.jsp">
