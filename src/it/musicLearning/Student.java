package it.musicLearning;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


/**
 * Servlet implementation class Student
 */
@WebServlet("/Student")
public class Student extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Student() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
		// DEBUG
		//System.out.println(request.getQueryString());
		System.out.println("DEBUG: [Student_Servlet: doGet] -> doPost");	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("DEBUG: [Student_Servlet: doPost]");
		
		String comand= request.getParameter("comand"); //true or false
				
		String StudentName = request.getParameter("StudentName");
		String StudentEmail = request.getParameter("StudentEmail");
		
		
		if(comand == null || comand.equals("")){
		
			if((StudentName == null || StudentName.equals("")) || (StudentEmail == null || StudentEmail.equals(""))) {
				System.out.println("DEBUG: [Student_Servlet: doPost] -> No Content for Student ");
				response.sendError(HttpServletResponse.SC_NO_CONTENT);
				return;
			}
			
				File input = new File("/var/lib/tomcat8/webapps/MusicLearning/StudentPage.html");
			Document html = (Document) Jsoup.parse(input, "UTF-8");
			
			Element r= html.getElementById("pageTitle");
			
			r.text("Ciao "+ StudentName +"!  Scegli un Professore");
			
			response.setContentType("text/html"); 
			response.setHeader("Cache-Control", "no-cache"); 
			response.getWriter().append(html.toString());
		}
		else{
			switch (comand) {
			case "getProfList":
				ArrayList<RoomDB> rooms= RoomDB.RetrieveAvailableRoom();
				
				
				response.setContentType("text/xml"); 
				response.setHeader("Cache-Control", "no-cache"); 
				response.getWriter().append("<response>");
				
				
				if( rooms != null){
					
					for(RoomDB iter: rooms){
				
						response.getWriter().append("<prof>");
					
							response.getWriter().append("<name>");
								response.getWriter().append(iter.getProfName());
							response.getWriter().append("</name>");
							
							response.getWriter().append("<level>");
								response.getWriter().append(iter.getLevel());
							response.getWriter().append("</level>");
							
							response.getWriter().append("<email>");
								response.getWriter().append(iter.getProfEmail());
							response.getWriter().append("</email>");
					
							response.getWriter().append("<linkImage>");
								response.getWriter().append("img/"+ iter.getMusicalInstrument() +".png");
							response.getWriter().append("</linkImage>");
							
							response.getWriter().append("<room>");
								response.getWriter().append(Integer.toString(iter.getID()));
							response.getWriter().append("</room>");
							
						response.getWriter().append("</prof>");		
					}
				
				}
		
				response.getWriter().append("</response>");
				
				break;
				
			case "call":
				
				RoomDB temp= RoomDB.RetrieveRoomByID(Integer.parseInt(request.getParameter("room")));
				if(temp.getNclient() == 1){
					temp.setNclient(2);
					temp.UpdateRoom();
					
					
					File input = new File("/var/lib/tomcat8/webapps/MusicLearning/WebRtcPage.html");
					Document html = (Document) Jsoup.parse(input, "UTF-8");
					
					Element tmp= html.getElementById("roomID");
					
					tmp.text(request.getParameter("room"));
					
					tmp= html.getElementById("pageTitle");
					
					tmp.text("VideoLezione RealTime di "+ temp.getMusicalInstrument() + ". Difficoltà: "+ temp.getLevel());
					
					response.setContentType("text/html"); 
					response.setHeader("Cache-Control", "no-cache"); 
					response.getWriter().append(html.toString());
					
					
				}
				else{
					
					File input = new File("/var/lib/tomcat8/webapps/MusicLearning/StudentPage.html");
					Document html = (Document) Jsoup.parse(input, "UTF-8");
					
					Element r= html.getElementById("pageTitle");
					
					r.text("Sessione avviata da un altro studente! Scegli un Professore");
					
					response.setContentType("text/html"); 
					response.setHeader("Cache-Control", "no-cache"); 
					response.getWriter().append(html.toString());
		
					
				}
				
				
							
				break;

			default:
				System.out.println("ERROR: [Student_Servlet: doPost] -> Get Professor List");
				response.sendError(HttpServletResponse.SC_NO_CONTENT);
				break;
			}
			
						
			
		}
	}

}
