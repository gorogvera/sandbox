import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeApplicationContext;

@Named
@ViewScoped
public class CspView implements Serializable {

    private boolean cspEnabled;
    private String userSuppliedInput;

    public CspView() {
        cspEnabled = PrimeApplicationContext.getCurrentInstance(FacesContext.getCurrentInstance()).getConfig().isCsp();
        userSuppliedInput = "<b>Huhu</b><script>window.cspScriptExecuted=true;alert('XSS');</script>";
        
    }

    public void check() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Success", "PASS"));
        
    }

    public String getUserSuppliedInput() {
        return userSuppliedInput;
    }

    public void setUserSuppliedInput(String userSuppliedInput) {
        this.userSuppliedInput = userSuppliedInput;
    }

    public boolean isCspEnabled() {
        return true;
    }

    public void executeScript() {
        PrimeFaces.current().executeScript("alert('PASS');");
    }
   
}
