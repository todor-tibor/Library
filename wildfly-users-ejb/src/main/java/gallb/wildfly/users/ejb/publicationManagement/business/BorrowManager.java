/**
 * 
 */
package gallb.wildfly.users.ejb.publicationManagement.business;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import gallb.wildfly.users.common.IEntity;
import gallb.wildfly.users.common.LibraryException;
import gallb.wildfly.users.ejb.exception.EjbException;
import model.Author;
import model.Borrow;

/**
 * @author nagys
 *
 */
public class BorrowManager implements IEntity<Borrow>{
	
	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager oEntityManager;
	private Logger oLogger = Logger.getLogger(Borrow.class);


	@Override
	public List<Borrow> getAll() throws LibraryException {
		try{
			return oEntityManager.createNamedQuery("Borrow.findAll", Borrow.class).getResultList();
		}catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
		
	}

	@Override
	public List<Borrow> search(String p_searchTxt) throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Borrow getById(String p_id) throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void store(Borrow p_value) throws LibraryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Borrow p_user) throws LibraryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String p_id) throws LibraryException {
		// TODO Auto-generated method stub
		
	}

}
