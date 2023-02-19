import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
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
	  return "Hello CSP World";
   }
  

}

