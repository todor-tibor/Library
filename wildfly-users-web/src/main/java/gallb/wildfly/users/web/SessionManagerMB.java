package gallb.wildfly.users.web;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

@ManagedBean(name="amibeanunk")
@SessionScoped
public class SessionManagerMB implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 5581520559417627350L;

	@Inject
	private UserMB userMB;
	
	@Inject LocaleManager localeManager;
	
	
	private String localeCode;

	private static Map<String,Object> countries;
	static{
		countries = new LinkedHashMap<String,Object>();
		countries.put("English", Locale.ENGLISH); //label, value
		countries.put("German", Locale.GERMAN);
	}

	public Map<String, Object> getCountriesInMap() {
		return countries;
	}


	public String getLocaleCode() {
		return localeCode;
	}


	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	//value change event listener
	public void countryLocaleCodeChanged(){

	//	String newLocaleValue = e.getNewValue().toString();

		//loop country map to compare the locale code
                for (Map.Entry<String, Object> entry : countries.entrySet()) {

        //	   if(entry.getValue().toString().equals(newLocaleValue)){

        		FacesContext.getCurrentInstance()
        			.getViewRoot().setLocale(new Locale(localeCode.toLowerCase(), localeCode.toUpperCase()));

        //	  }
               }
	}
	
	public void germanNow() {
		System.out.println("Before: " + FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		Locale tmpLoc = new Locale("de", "DE");
		FacesContext.getCurrentInstance().getViewRoot().setLocale(tmpLoc);
		localeManager.setUserLocale(tmpLoc);
		System.out.println("After: " + FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
	}
	
	public void englishNow() {
		System.out.println("Before: " + FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		Locale tmpLoc = new Locale("de", "DE");
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("en", "US"));
		localeManager.setUserLocale(tmpLoc);
		System.out.println("After: " + FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
	}
}
