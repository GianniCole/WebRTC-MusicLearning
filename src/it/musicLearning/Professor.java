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
 * Servlet implementation class ProfList
 */
@WebServlet("/Professor")
public class Professor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	private ArrayList<RoomDB> rooms;
	private static int lastID;
	
	
	
	public static synchronized void incrementID() {
        lastID++;
    }
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Professor() {
        super();
        // TODO Auto-generated constructor stub
        try{
	        if (RoomDB.getLastID()!= null)
	        	lastID = RoomDB.getLastID().getID();
	        else{
	        	lastID = 0;
	        	//System.out.println("Else");
	        }
        }
        catch (Exception e) {
			// TODO: handle exception
        	lastID = 0;
        	System.out.println(e);
		}
        
        System.out.println("Servlet Professore istanziata");
     
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
		// DEBUG
		System.out.println(request.getQueryString());
		System.out.println("DEBUG: [Professor_Servlet: doGet] -> doPost");
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				
		System.out.println("DEBUG: [ProfList_Servlet: doPost]");
		
		
		String ProfName = request.getParameter("ProfName");
		String ProfEmail = request.getParameter("ProfEmail");
		String ProfInstrument = request.getParameter("Instrument");
		String ProfLevel = request.getParameter("Level");
		
		
		
		
		if((ProfName == null || ProfName.equals("")) || (ProfEmail == null || ProfEmail.equals(""))
			|| (ProfInstrument == null || ProfInstrument.equals("")) || (ProfLevel == null || ProfLevel.equals(""))	) {
				System.out.println("DEBUG: [Professor_Servlet: doPost] -> No Content for Professor ");
				response.sendError(HttpServletResponse.SC_NO_CONTENT);
				return;
		}
		else{
			incrementID();
			RoomDB room= new RoomDB(lastID, ProfName, ProfEmail, ProfInstrument, ProfLevel,1);		
			room.SaveRoom();
			System.out.println("DEBUG: [Professor_Servlet: doPost] -> Room saved into DB!");
			
			 
		
			File input = new File("/var/lib/tomcat8/webapps/MusicLearning/WebRtcPage.html");
						
			Document html = (Document) Jsoup.parse(input, "UTF-8");
			
			Element tmp= html.getElementById("roomID");
			
			tmp.text(Integer.toString(lastID));
			
			tmp= html.getElementById("pageTitle");
			
			tmp.text("VideoLezione RealTime di "+ ProfInstrument + ". Difficoltà: "+ ProfLevel);
			
			response.setContentType("text/html"); 
			response.setHeader("Cache-Control", "no-cache"); 
			response.getWriter().append(html.toString());
				
		}
		
	}

}
