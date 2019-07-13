package it.musicLearning;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
        	// Loads mappings and properties from hibernate.cfg.xml
            Configuration configuration=new Configuration().configure("it/musicLearning/hibernate.cfg.xml"); 
            // Builder for standard ServiceRegistry instance
    	    StandardServiceRegistryBuilder registry= new StandardServiceRegistryBuilder();
    	    // Applies configuration settings to the standard ServiceRegistry
    	    registry.applySettings(configuration.getProperties());
    	    // Builds ServiceRegistry from set standard ServiceRegistry
    	    ServiceRegistry serviceRegistry = registry.build();
            // Builds a session factory from the ServiceRegistry
    	    SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    	    return sessionFactory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}