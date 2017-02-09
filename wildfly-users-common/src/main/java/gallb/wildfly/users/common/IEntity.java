package gallb.wildfly.users.common;

import java.util.List;

import model.BaseEntity;
import model.User;
/**
 * 
 * @author gallb
 *
 * Defines persistence operations, for entities.
 *
 * @param <X> Specifies entity type.
 */
public interface IEntity<X extends BaseEntity> {
	/**
	 * 
	 * @return List containing all entities.
	 * @throws LibraryException 
	 */
	public List<X> getAll() throws LibraryException;
	
	/**
	 * 
	 * @param p_searchTxt String to search for
	 * @return List of of entities found, empty list if nothing found.
	 * @throws LibraryException 
	 */
	public List<X> search(String p_searchTxt) throws LibraryException;
	
	/**
	 * 
	 * @param p_id Id of entity
	 * @return Search result entity, null if nothing found.
	 * @throws LibraryException 
	 */
	public X getById(int p_id) throws LibraryException;
	
	/**
	 * 
	 * @param p_value String with new value.
	 * @return True if operation successful, false if not.
	 * @throws LibraryException 
	 */
	public void store(X p_value) throws LibraryException;
	
	/**
	 * 
	 * @param p_newTxt String with new value.
	 * @return True if operation successful, false if not.
	 * @throws LibraryException 
	 */
	public void update(X p_user) throws LibraryException;
	
	/**
	 * 
	 * @param p_id Id of entity that should be removed from persistence.
	 * @return True if operation successful, false if not.
	 * @throws LibraryException 
	 */
	public void remove(int p_id) throws LibraryException;
}
