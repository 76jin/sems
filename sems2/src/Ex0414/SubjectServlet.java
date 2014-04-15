package Ex0414;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/sems/subject/list.bit")
@SuppressWarnings("serial")
public class SubjectServlet extends HttpServlet {
	
  static SubjectDao dao;
  static HttpServletResponse response;
  
/*  public static List<SubjectVo> testList(int pageNo, int pageSize) throws Throwable {
			return dao.list(pageNo, pageSize);
	}*/
  
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
		
		this.response = response;
		
		// 1) 요청 정보에서 파라미터 값 꺼내기 ##########
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		
		int pNo = 1;
		int pSize = 10;
		List<SubjectVo> list = null;
		
		try {
			pNo = Integer.parseInt(pageNo);
			pSize = Integer.parseInt(pageSize);
		} catch (NumberFormatException e) {
			// call error 
			System.out.println("숫자 변환 에러라구~~~");
		}

		// DBConnectionPool 준비
		DBConnectionPool dbConnectionPool = new DBConnectionPool();
		
		// SubjectDao 준비
		dao = new MysqlSubjectDao();
		
		// 의존 객체 주입하기.
		((MysqlSubjectDao)dao).setDBConnectionPool(dbConnectionPool);

		// DB로부터 list 얻어오기(번호, 과목명)
		try {
			list = dao.list(pNo, pSize);
    } catch (Throwable e) {
	    e.printStackTrace();
    }
		
		// 2) 클라이언트에게 출력하기 ##################
		String listString = "[";
		boolean isFirst = true;
		for (SubjectVo subject : list) {
			if (isFirst) {
				isFirst = false;
			} else {
				listString += ",";
			}
			
			listString += "{";
			listString += " \"no\": \"" +subject.getNo() + "\"" + "," ;
			listString += " \"description\":" + "\"" + subject.getTitle() + "\"";
			listString += "}";
		}
		
		listString += "]";
		
		System.out.println(listString);
	 
		
		String printList = 
				  "var subjects = " + listString + ";"
				+ "var subjectTable = $(\"#subjectTable\");"
				+ "$.each(subjects, function(index, subject) {"
				+ "  $('<tr></tr>')"
				+ "    .addClass('dataRow')"
				+ "    .append($('<td></td>').html(subject.no))"
				+ "    .append($('<td></td>').html(subject.description)"
				+ "    .appendTo(subjectTable)"
				+ "});";
		
		System.out.println(printList);
	
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(""
				+ "<html>"
				+ "<head>"
				+ "<title>SEMS - List </title>"
				+ "<script src='http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js'></script>"
				+ "<style>"
				+ "#subjectTable"
				+ "</style>"
				+ "</head>");
		
		out.println(
				  "<body>"
				+ "<h1>과목 정보</h1>"
				+ "<table id='subjectTable'>"
				+ "<tr>"
				+ "  <th>번호</th>"
				+ "  <th>과목명</th>"
				+ "</tr>"
				+ "</table>"
				+ "<script>");
		
		for (SubjectVo subject : list) {
			out.println(
					  "$('<tr></tr>').append($('<td></td>').html(\"" + subject.getNo() + "\")).append($('<td></td>').html(\"" + subject.getTitle() + "\")).appendTo(subjectTable);");
		}
		
		out.println("</script></body></html>");
		
		dbConnectionPool.closeAll();
		
	}
}
