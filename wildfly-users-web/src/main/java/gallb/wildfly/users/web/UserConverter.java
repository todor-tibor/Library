package gallb.wildfly.users.web;


import java.util.List;
import java.util.Optional;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import model.User;

@FacesConverter("userConverter")
public class UserConverter implements Converter {
 
//	@Inject
//	private UserMB userMB;
	
	private Logger oLogger = Logger.getLogger(UserConverter.class);

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		oLogger.info("**************Converter: value=" + value);
		oLogger.info("**************Converter: fc=" + fc.getClass().getName());
		User tmpUser = new User();
		if(value != null && value.trim().length() > 0) {
            try {
            	oLogger.info(fc.getExternalContext().getApplicationMap().get("userbean").getClass().getName()); 
            	UserMB userMB = (UserMB) fc.getExternalContext().getApplicationMap().get("userbean");
                 oLogger.info("**************Converter: userMB class type " + userMB.getClass().getName());
                 tmpUser = userMB.getAll().stream().filter(e -> e.getUserName().equals(value)).findFirst().get();
                List<User> usrList = userMB.getAll();
                 for (int i = 0; i < usrList.size(); i++) {
					if (usrList.get(i).getUserName().equals(value)) {
						tmpUser = usrList.get(i);
						
				}
					oLogger.info("**************Converter: " + tmpUser.getUserName());
	                return tmpUser;
				} 			
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid user."));
            }
        }
        else {
            return null;
        }
		return tmpUser;
    }
 
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((User) object).getUserName());
        }
        else {
            return null;
        }
    }   
}