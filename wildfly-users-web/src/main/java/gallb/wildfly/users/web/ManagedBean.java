/**
 * 
 */
package gallb.wildfly.users.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import gallb.wildfly.users.common.IUser;
import gallb.wildfly.users.common.BeanException;
import gallb.wildfly.users.common.IRole;
import model.Role;
import model.User;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
/**
 * @author gallb
 * User manager.
 */
@Named("userbean")

@SessionScoped
public class ManagedBean implements Serializable{

	/**
	 * 
	 */
	private Logger oLogger = Logger.getLogger(ManagedBean.class);
	private static final long serialVersionUID = -4702598250751689454L;
	
	@Inject
	private IUser oUserBean;
	
	private List<User> userList =  new ArrayList<>();
	private User currentUser = null;

	//Setting faces context messages.
	public void error(String message) {
		oLogger.info("**********************Error CALLED***************************");
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", message));
    }
	
	public void info(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", message));
    }
     
    public void warn(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", message));
    }
     
    public void fatal(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", message));
    }
	
	public List<User> getUserList() {
		return userList;
	}
    
    public User getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
    
	public List<User> getAll() {
		oLogger.info("--getAllUsers()--");
		userList = new ArrayList<>();
		try {
			oLogger.info("--getAllUsers()--users queried");
			userList = oUserBean.getAll();
		} catch (BeanException e) {
			this.error("Server internal error.");
		}
		return userList;
	}

	public List<User> search(String p_searchTxt) {
		oLogger.info("--search user--" + p_searchTxt);
		if (p_searchTxt.length() >= 3) {
			userList = new ArrayList<>();
			try {
				userList = oUserBean.search(p_searchTxt);
			} catch (BeanException e) {
				this.error(e.getMessage());
			}
		} else {
			this.error("Keyword too short. Min. 3 characters req.");
		}
		return userList;
	}
	public void store(String p_value) {
		oLogger.info("--store user--");
		oLogger.info("--store param: " + p_value);
		if (p_value.isEmpty()) {
			this.error("Empty field");
		}
		if (p_value == "") {
			this.error("Empty field");
		}
		try {
			User tmpUser = new User();
			tmpUser.setUserName(p_value);
			oUserBean.store(tmpUser);
			userList.add(tmpUser);
			this.info("Succesfully added: " + p_value);
		} catch (BeanException e) {
			this.error(e.getMessage());
		}
	}
	
	public void update(String p_newTxt) {
		oLogger.info("--update user ManagedBean--id:" + currentUser.getUserName() + "new name: " +p_newTxt);
		if ((currentUser != null) && (p_newTxt.length() >= 3)) {
			try {
				currentUser.setUserName(p_newTxt);
				oUserBean.update(currentUser);
				userList = oUserBean.getAll(); 
				oLogger.info("**********************update succesfull************************************");
				this.info("Update succesfull.");
			} catch (BeanException e) {
				oLogger.error(e);
				this.error(e.getMessage());
			}
		} else {
			this.error("New name too short.");
		}
	}
	

//	public boolean remove() {
//		oLogger.info("--remove user by Id ManagedBean--p_id: " + selectedUser);
//		if (selectedUser == "") {
//			this.error("Empty field");
//			return false;
//		} else {
//			try {
//				getUserBean().remove(selectedUser);
//				userList = getUserBean().getAll(); 
//				this.info("Delete succesfull.");
//			} catch (BeanException e) {
//				oLogger.error(e);
//				this.error(e.getMessage());
//			}
//		}
//		return true;
//	}
}
