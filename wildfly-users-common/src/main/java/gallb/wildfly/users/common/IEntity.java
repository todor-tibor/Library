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
	 * @throws BeanException 
	 */
	public List<X> getAll() throws BeanException;
	
	/**
	 * 
	 * @param p_searchTxt String to search for
	 * @return List of of entities found, empty list if nothing found.
	 * @throws BeanException 
	 */
	public List<X> search(String p_searchTxt) throws BeanException;
	
	/**
	 * 
	 * @param p_id Id of entity
	 * @return Search result entity, null if nothing found.
	 * @throws BeanException 
	 */
	public X getById(int p_id) throws BeanException;
	
	/**
	 * 
	 * @param p_value String with new value.
	 * @return True if operation successful, false if not.
	 * @throws BeanException 
	 */
	public void store(X p_value) throws BeanException;
	
	/**
	 * 
	 * @param p_newTxt String with new value.
	 * @return True if operation successful, false if not.
	 * @throws BeanException 
	 */
	public void update(X p_user) throws BeanException;
	
	/**
	 * 
	 * @param p_id Id of entity that should be removed from persistence.
	 * @return True if operation successful, false if not.
	 * @throws BeanException 
	 */
	public void remove(int p_id) throws BeanException;
}
