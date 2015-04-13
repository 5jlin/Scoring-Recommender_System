<%if(session.getValue("username")==null) response.sendRedirect("xxx.jsp");%>
<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.mysql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.example.*" %>
<%@ page import="jxl.*" %>
<%@ page import="jxl.read.biff.BiffException" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html; charset=GB2312" language="java" import="java.util.*" import="java.sql.*" %>


<html>

<body>

	<script>

		var flag = new Array(5);
		var i;

		for(i=0; i<5; i++){
			flag[i]=true;
		}

		function resetFlag(){
			for(i=0; i<5; i++){
				flag[i]=true;
			}
			grayStar(5);
		}

		function yellowStar(a){
			for(i=1; i<=a; i++){
				document.getElementById('star'+i).src= "assert/img/star1.jpg";		
			}
		}

		function setFlag(a, b){
			for(i=0; i<a; i++){
				flag[i]=false;
			}  
			window.location.assign("pass.jsp?videoId=" + a +"&star=" + score);
		}

		function grayStar(a){
			if(flag[a-1]){
				for(i=1; i<=a; i++){
					document.getElementById('star'+i).src= "assert/img/star0.jpg"
				}	
			}
		}

		function setStar(a){
			for(i=1; i<=a; i++){
				document.getElementById('setstar'+i).src= "assert/img/star1.jpg";          
			}
		}

	</script>


	<%
	try{
		Class.forName("com.mysql.xxx.Driver");
		Connection conn = DriverManager.getConnection("xxx:Database Address", "ID", "PW");
		Statement stmt = conn.createStatement();
		String sql = "select * from video_list, user_list where videoId='" + request.getParameter("videoId") + "' and userId=ownerId";
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		int count = rs.getInt("count") + 1;
		String sql2 = "update video_list set count='" + count + "' where videoId='" + request.getParameter("videoId") + "'";
		Statement stmt2 = conn.createStatement();
		stmt2.executeUpdate(sql2);
		
		int cnt=1;
		Statement stmtf = conn.createStatement();
		String sqlf = "select * from video_list order by videoId asc";
		ResultSet rsf = stmtf.executeQuery(sqlf);
		while(rsf.next()){
			if(request.getParameter("videoId").equals(rsf.getString("videoId")) == false){
				cnt=cnt+1;
			}else {
				break;
			}
		}

		String sqlu = "select * from user_list where user_name='" + (String)session.getAttribute("username") + "'";
		Statement stmtu = conn.createStatement();
		ResultSet rsu = stmtu.executeQuery(sqlu);
		rsu.next();
		String u = rsu.getString("userId");



		String scoreSQL = "select score from rank where videoId='" + request.getParameter("videoId") + "' and userId='"+u+"'";
		Statement scorestmt = conn.createStatement();
		ResultSet scorers = scorestmt.executeQuery(scoreSQL);
		if(scorers.next()){
			int sc = scorers.getInt("score");
	%>
			<input type="image" src="assert/img/star0.jpg" width="50" height="50" id="setstar1" />
			<input type="image" src="assert/img/star0.jpg" width="50" height="50" id="setstar2" />
			<input type="image" src="assert/img/star0.jpg" width="50" height="50" id="setstar3"/>
			<input type="image" src="assert/img/star0.jpg" width="50" height="50" id="setstar4"/>
			<input type="image" src="assert/img/star0.jpg" width="50" height="50" id="setstar5"/>

			<script type="text/javascript">
			var score = "<%=sc%>";
			setStar(score);
			</script><br>
			<a class="btn btn-warning"
			href="rdislike.jsp?videoId=<%out.print(rs.getString("videoId"));%>&userId=<%out.print(u);%>">Clear </a>


		<%}else{%>

		    <input type="image" src="assert/img/star0.jpg" width="50" height="50" id="star1" 
			onmouseover="yellowStar(1)" onmouseout="grayStar(1)" onclick="setFlag(1, <%out.print(rs.getString("videoId"));%>)"/>
			<input type="image" src="assert/img/star0.jpg" width="50" height="50" id="star2" 
			onmouseover="yellowStar(2)" onmouseout="grayStar(2)" onclick="setFlag(2, <%out.print(rs.getString("videoId"));%>)"/>
			<input type="image" src="assert/img/star0.jpg" width="50" height="50" id="star3"
			onmouseover="yellowStar(3)" onmouseout="grayStar(3)" onclick="setFlag(3, <%out.print(rs.getString("videoId"));%>)"/>
			<input type="image" src="assert/img/star0.jpg" width="50" height="50" id="star4"
			onmouseover="yellowStar(4)" onmouseout="grayStar(4)" onclick="setFlag(4, <%out.print(rs.getString("videoId"));%>)"/>
			<input type="image" src="assert/img/star0.jpg" width="50" height="50" id="star5"
			onmouseover="yellowStar(5)" onmouseout="grayStar(5)" onclick="setFlag(5, <%out.print(rs.getString("videoId"));%>)"/>
			<br>

			<a class="btn btn-warning">Clear </a>
		<%}%>
			<br>	

			
		rs.close();		rs = null;
		rsf.close();	rsf = null;
		rsu.close();	rsu = null;
		scorers.close();scorers = null;
		stmt.close();	stmt = null;
		stmt2.close();	stmt2 = null;
		stmtf.close();	stmtf = null;
		stmtu.close();	stmtu = null;
		scorestmt.close();scorestmt = null;
		conn.close();

	<%
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

	<hr>
	<jsp:include page="footer.jsp"/>
	
</body>
</html>
