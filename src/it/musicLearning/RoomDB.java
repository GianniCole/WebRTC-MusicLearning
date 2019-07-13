package it.musicLearning;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.Query;
import org.hibernate.Session;

@Entity
public class RoomDB {
	
	@Id
//	@GeneratedValue
	@Column(name="id",unique=true,nullable=false)
	private int ID;
	
	
	@Column(name="prof_name",unique=false,nullable=false)
	private String ProfName;
	
	@Column(name="prof_email",unique=false,nullable=false)
	private String ProfEmail;
	
	@Column(name="musical_instrument",unique=false,nullable=false)
	private String MusicalInstrument;
	
	@Column(name="level",unique=false,nullable=false)
	private String Level;
	
	@Column(name="n_client",unique=false,nullable=false)
	private int Nclient;
	
	// ------ Constructors -----
	public RoomDB(){}
	
	public RoomDB(int id, String profName, String profEmail, String musicalInstrument, String level,int nClient){
		this.ID = id;
		this.ProfName = profName;
		this.ProfEmail = profEmail;
		this.MusicalInstrument = musicalInstrument;
		this.Level = level;
		this.Nclient = nClient;
			
	}
	
	// -------- Set & Get ----------
	public int getID(){
		return ID;
	}
	
	public void setID(int id){
		ID = id;
	}
	
	public int getNclient(){
		return Nclient;
	}
	
	public void setNclient(int nClient){
		Nclient = nClient;
	}
	
	public String getProfName() {
		return ProfName;
	}

	public void setProfName(String profName) {
		ProfName = profName;
	}
	
	public String getProfEmail() {
		return ProfEmail;
	}

	public void setProfEmail(String profEmail) {
		ProfEmail = profEmail;
	}
	
	public String getMusicalInstrument() {
		return MusicalInstrument;
	}

	public void setMusicalInstrument(String musicalInstrument) {
		MusicalInstrument = musicalInstrument;
	}
	
	public String getLevel() {
		return Level;
	}

	public void setLevel(String level) {
		Level = level;
	}
	
	
	//-----------------  DATABASE ACCESS OPERATION  -----------------

	// Save
	public void SaveRoom() {
		Session session = HibernateUtil.getSessionFactory().openSession();
        try {
        	
			session.getTransaction().setTimeout(2);
        	session.beginTransaction();
        	
        	//Save object to DB
        	session.save(this);
        	
        	
        	session.getTransaction().commit();
        	
		} 
        catch (RuntimeException e) {
        	System.out.println("\n\nException: Room Saving!\n\n");
		    session.getTransaction().rollback();
		    throw e; 
		}
		finally {
		    session.close();
		}
	}	


	// Update
	public void UpdateRoom() {
		Session session = HibernateUtil.getSessionFactory().openSession();
        try {
        	
			session.getTransaction().setTimeout(2);
        	session.beginTransaction();
        	
        	//Update object to DB
        	session.update(this);
        	
        	
        	session.getTransaction().commit();
        }
        catch (RuntimeException e) {
        	System.out.println("\n\nException: Room Updating!\n\n");
		    session.getTransaction().rollback();
		    throw e; 
		}
		finally {
		    session.close();
		}
	}	

	// Delete
	public void DeleteRoom() {
		Session session = HibernateUtil.getSessionFactory().openSession();
        try {
			session.getTransaction().setTimeout(2);
        	session.beginTransaction();
        	
        	//Delete object from DB
        	session.delete(this);

        	session.getTransaction().commit();
		} 
        catch (RuntimeException e) {
        	System.out.println("\n\nException: Room Deleting!\n\n");
		    session.getTransaction().rollback();
		    throw e; 
		}
		finally {
		    session.close();
		}
	}
	
	//Query1 - Recupera tutte le ROOM
	@SuppressWarnings("unchecked")
	public static ArrayList<RoomDB> RetrieveRooms() {
		Session session = HibernateUtil.getSessionFactory().openSession();
        try {
        	Query query;
			session.getTransaction().setTimeout(2);
        	session.beginTransaction();
        	
        	//Query
        	query=session.createQuery("from RoomDB");
        	
        	
			ArrayList <RoomDB> result = (ArrayList<RoomDB>) query.list();
        	
        	session.getTransaction().commit();
        	
        	if(result.isEmpty())
        		return null;
        	else
        		return result;  //restituisce tutte le Room
        	
		} 
        catch (RuntimeException e) {
        	System.out.println("\n\nEccezione: Query1 Room!\n\n");
		    session.getTransaction().rollback();
		    throw e; 
		}
		finally {
		    session.close();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	//Query2 - Recupera Stanza attraverso ID
	public static RoomDB RetrieveRoomByID(int ID) {
		Session session = HibernateUtil.getSessionFactory().openSession();
        try {
        	Query query;
			session.getTransaction().setTimeout(2);
        	session.beginTransaction();
        	
        	//Query
        	query=session.createQuery("from RoomDB WHERE id = :room_id");
        	query.setParameter("room_id",ID);
        	
        	
			ArrayList <RoomDB> result = (ArrayList<RoomDB>) query.list();
        	
        	session.getTransaction().commit();
        	
        	if(result.isEmpty())
        		return null;
        	else
        		return result.get(0);  //restituisce la prima room della lista
        	
		} 
        catch (RuntimeException e) {
        	System.out.println("\n\nEccezione: Query2 Room!\n\n");
		    session.getTransaction().rollback();
		    throw e; 
		}
		finally {
		    session.close();
		}
	}	
	
	//Query3 - Recupera tutte le ROOM
	@SuppressWarnings("unchecked")
	public static RoomDB getLastID() {
		Session session = HibernateUtil.getSessionFactory().openSession();
        try {
        	Query query;
			session.getTransaction().setTimeout(6);
        	session.beginTransaction();
        	
        	//Query
        	query=session.createQuery("from RoomDB WHERE id = (SELECT MAX(id) FROM RoomDB)");
        	
        	ArrayList <RoomDB> result = (ArrayList<RoomDB>) query.list();
        	
        	
        	session.getTransaction().commit();
        	
        	if(result.isEmpty())
        		return null;
        	else        		
        		return result.get(0);  //restituisce la prima room della lista
        
        		
        	
		} 
        catch (RuntimeException e) {
        	System.out.println("\n\nEccezione: Query3 Room!\n\n" + e);
		    session.getTransaction().rollback();
		    throw e; 
		}
		finally {
		    session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	//Query4 - Recupera Stanza attraverso ID
	public static ArrayList<RoomDB> RetrieveAvailableRoom() {
		Session session = HibernateUtil.getSessionFactory().openSession();
        try {
        	Query query;
			session.getTransaction().setTimeout(2);
        	session.beginTransaction();
        	
        	//Query
        	query=session.createQuery("from RoomDB WHERE n_client = 1");        	
        	
			ArrayList <RoomDB> result = (ArrayList<RoomDB>) query.list();
        	
        	session.getTransaction().commit();
        	
        	if(result.isEmpty())
        		return null;
        	else
        		return result;  //restituisce la prima room della lista
        	
		} 
        catch (RuntimeException e) {
        	System.out.println("\n\nEccezione: Query4 Room!\n\n");
		    session.getTransaction().rollback();
		    throw e; 
		}
		finally {
		    session.close();
		}
	}	

}
