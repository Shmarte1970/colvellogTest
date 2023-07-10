package es.zarca.covellog.interfaces.web.app.controller;

import es.zarca.covellog.interfaces.web.app.model.Dialogo;
import es.zarca.covellog.interfaces.web.helpers.StringUtil;
import java.io.Serializable;
import java.util.Stack;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author francisco
 */
@Named("dialogosController")
@ViewScoped
public class DialogosController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(DialogosController.class.getName());

    private Stack<Dialogo> dialogos;
    private String nextDialogoId;
    
    public DialogosController() {
        dialogos = new Stack<>();
        nextDialogoId = StringUtil.textoAleatorio(8);
    }

    public String nuevoDialogo(String plantilla, Object model) {
        Dialogo dialogo = new Dialogo(nextDialogoId, plantilla, model);
        System.out.println("Nuevo dialogo: " + dialogo.getId() + " => " + plantilla);
        nextDialogoId = StringUtil.textoAleatorio(8);
        System.out.println("Nuevo dialogo => nuevo id: " + nextDialogoId);
        dialogos.push(dialogo);
        return dialogo.getId();
    }
    
    public void finalizarDialogo() {
        //dialogos.pop();
    }

    public String getNextDialogoId() {
        return nextDialogoId;
    }

    public Dialogo getHeap() {
        
        if (!dialogos.empty()) {
            return dialogos.peek();
        }
        System.out.println("GETHEAP VACIO");
        return null;
    }
    
    public Stack<Dialogo> getDialogos() {
        return dialogos;
    }

}