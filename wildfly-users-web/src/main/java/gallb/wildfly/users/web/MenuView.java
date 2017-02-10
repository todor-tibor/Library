/**
 * 
 */
package gallb.wildfly.users.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

/**
 * @author gallb
 *
 */
@Named("menuView")
@SessionScoped
public class MenuView implements Serializable{
	private String url = "user.xhtml"; //contains the url to be displayed in page center

	public String getUrl() {
		return url;
	}

	private void setUrl(String url) {
		this.url = url;
	}
	
	public void user() {
		setUrl("user2.xhtml");
	}
}
