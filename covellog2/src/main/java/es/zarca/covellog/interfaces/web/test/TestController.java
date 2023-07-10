package es.zarca.covellog.interfaces.web.test;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("testController")
@ViewScoped
public class TestController implements Serializable{
    
private Long menuID;

public Long getMenuID() {
        return menuID;
    }
 
    public void setMenuID(Long menuID) {
        this.menuID = menuID;
    }
}
    