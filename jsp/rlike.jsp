<!DOCTYPE html> 
<%if(session.getValue("username")==null) response.sendRedirect("yyy.jsp");%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.fileupload.disk.*"%>
<%@ page import="org.apache.commons.fileupload.servlet.*"%>
<%@ page import="org.apache.commons.io.FilenameUtils"%>
<%@ page import="java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.mysql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.example.*" %>
<%@ page import="jxl.*" %>
<%@ page import="jxl.read.biff.BiffException" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.*" %>
<Link rel="SHORTCUT ICON"  href="bg/sys_1000_6714256_81641.ico" type="image/x-icon" />
<link rel="Bookmark" href="bg/sys_1000_6714256_81641.ico">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script> 
<script src="http://malsup.github.com/jquery.form.js"></script> 
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">


<%
String rec ="";
rec = "Address/exerec.sh ";
Process process = Runtime.getRuntime().exec(rec);

try{
	Class.forName("com.mysql.xxx.Driver");
	Connection conn = DriverManager.getConnection("xxx:Database Address", "ID", "PW");
	Statement stmt = conn.createStatement();
	Statement stmtv = conn.createStatement();
	Statement stmtu = conn.createStatement();


	String sql = "select count(userId) from user_list";
	ResultSet rs = stmt.executeQuery(sql);
	String sqlv = "select count(videoId) from video_list";
	ResultSet rsv = stmtv.executeQuery(sqlv);
	String sqlu = "select * from user_list where user_name='" + request.getParameter("user_name") + "'";
	ResultSet rsu = stmtu.executeQuery(sqlu);

	rs.next();
	rsv.next();
	rsu.next();

	String cu = rs.getString("count(userId)");
	Integer cui = Integer.parseInt(cu);
	String cv = rsv.getString("count(videoId)");
	Integer cvi = Integer.parseInt(cv);
	int[][] arry;
	arry = new int[cui][cvi];
	FileWriter fw =null;
	BufferedWriter bw =null;
	FileWriter fw2 =null;
	BufferedWriter bw2 =null;

	String u = rsu.getString("userId");

	Statement stmtr = conn.createStatement();
	String sqlr = "select * from rank order by Id desc limit 1";
	ResultSet rsr = stmtr.executeQuery(sqlr);
	rsr.next();
	int Id;
	Id = rsr.getInt("Id");
	Id=Id+1;

	Statement stmti = conn.createStatement();
	String sqli = "insert into rank (Id,userId,videoId, score) values('" + Id + "','" + u + "','" + request.getParameter("videoId") + "', '" + request.getParameter("score")+ "')";
	stmti.executeUpdate(sqli);
	Integer ui = Integer.parseInt(u);
	ui = ui - 1;

	Statement stmtr1 = conn.createStatement();
	String sqlr1 = "select * from rank";
	ResultSet rsr1 = stmtr1.executeQuery(sqlr1);


	while(rsr1.next()){
		int cnt = 0;
		Statement stmtf = conn.createStatement();
		String sqlf = "select * from video_list order by videoId asc";
		ResultSet rsf = stmtf.executeQuery(sqlf);
		while(rsf.next()){
			if(rsr1.getString("videoId").equals(rsf.getString("videoId")) == false){
				cnt=cnt+1;
			}
			else{
				break;
			}
		}
		int uii = Integer.parseInt(rsr1.getString("userId"));
		uii=uii-1;
		arry[uii][cnt] = Integer.parseInt(rsr1.getString("score"));
	}

	rs.close();		rs = null;
	rsv.close();	rsv = null;
	rsu.close();	rsu = null;
	rsr.close();	rsr = null;
	rsr1.close();	rsr1 = null;

	stmt.close();	stmt = null;
	stmtv.close();	stmtv = null;
	stmtu.close();	stmtu = null;
	stmtr.close();	stmtr = null;
	stmti.close();	stmti = null;
	stmtr1.close();	stmtr1 = null;
	conn.close();
		
	try{
		fw = new FileWriter("Address/transa.txt");
		bw = new BufferedWriter(fw);

		for(int k = 0; k < cui ; k++){
			for(int l=0; l<cvi; l++){
				bw.write("" + arry[k][l]+ " ");
			}
			bw.write("\n");
		}
	}
	catch(IOException e){}
	finally{
		try{
			bw.close();
		}
		catch(IOException e){} 
	}
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

<meta http-equiv="refresh" content="0; url=score.jsp?videoId=<%out.print(request.getParameter("videoId"));%>&user_name=<%out.print(request.getParameter("user_name"));%>">
