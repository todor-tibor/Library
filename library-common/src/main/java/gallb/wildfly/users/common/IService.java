package gallb.wildfly.users.common;

import java.util.List;

import model.BaseEntity;

/**
 * 
 * @author gallb
 * @author kiska
 *
 *         Defines persistence operations, for entities.
 *
 * @param <X>
 *            Specifies entity type.
 */
public interface IService<X extends BaseEntity> {
	/**
	 * Lists all objects of {@code <X>} type that are in the database
	 * 
	 * @return List containing all entities.
	 * @throws LibraryException
	 */
	public List<X> getAll() throws LibraryException;

	/**
	 * Lists one object of {@code <X>} type that is in the database and contains
	 * the {@code p_searchTxt} input string
	 * 
	 * @param p_searchTxt
	 *            String to search for
	 * @return List of entities found, empty list if nothing found.
	 * @throws LibraryException
	 */

	public List<X> search(String p_searchTxt) throws LibraryException;

	/**
	 * Lists one object of {@code <X>} type that is in the database and contains
	 * the {@code p_id} input string, which is the objects unique id.
	 * 
	 * @param p_id
	 *            Id of entity
	 * @return Search result entity, null if nothing found.
	 * @throws LibraryException
	 */
	public X getById(String p_id) throws LibraryException;

	/**
	 * Saves the object specified by {@code p_value} into the database
	 * 
	 * @param p_value
	 *            {@code <X>} with new value.
	 * @return True if operation successful, false if not.
	 * @throws LibraryException
	 */
	public void store(X p_value) throws LibraryException;

	/**
	 * Updates the object specified by {@code p_value}
	 * 
	 * @param p_newTxt
	 *            {@code <X>} with new value.
	 * @return True if operation successful, false if not.
	 * @throws LibraryException
	 */
	public void update(X p_user) throws LibraryException;

	/**
	 * Removes an object defined by the {@code p_id} from the database
	 * 
	 * @param p_id
	 *            Id of entity that should be removed from persistence.
	 * @return True if operation successful, false if not.
	 * @throws LibraryException
	 */
	public void remove(String p_id) throws LibraryException;
}
