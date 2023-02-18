import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.context.PrimeFacesContext;
import org.primefaces.csp.CspState;

@Named("helloworld")
@SessionScoped
public class HelloWorld implements Serializable {

	private String firstName = "John";
	  private String lastName = "Doe";
	
	  public String getFirstName() {
		    return firstName;
		  }

		  public void setFirstName(String firstName) {
		    this.firstName = firstName;
		  }

		  public String getLastName() {
		    return lastName;
		  }

		  public void setLastName(String lastName) {
		    this.lastName = lastName;
		  }

		  public String showGreeting() {
		    return "Hello " + firstName + " " + lastName + "!";
		  } 
	  
	  
   public String getMessage() {
	  return "Hello Nonce: " + getNonce() ;
   }
  
   public String getNonce() {
	   FacesContext context = FacesContext.getCurrentInstance();
	   return PrimeFacesContext.getCspState(context).getNonce();
   }

}

