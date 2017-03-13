package rcplibrary.controller;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.edu.library.IAuthorService;
import com.edu.library.model.Author;

public class AuthorController {

	private IAuthorService oAuthorBean = null;

	private IAuthorService getRoleBean() {
		if (this.oAuthorBean == null) {
			try {
				InitialContext jndi = new InitialContext();
				this.oAuthorBean = (IAuthorService) jndi.lookup(IAuthorService.jndiNAME);
			} catch (NamingException e) {
				System.out.println("baj van");
				e.printStackTrace();
				return null;
			}
		}
		return this.oAuthorBean;
	}

	public List<Author> getAll() {
		try {
			return getRoleBean().getAll();
		} catch (Exception e) {
			System.out.println("baj van");
			e.printStackTrace();
			return new ArrayList<>();
		}

	}
}
