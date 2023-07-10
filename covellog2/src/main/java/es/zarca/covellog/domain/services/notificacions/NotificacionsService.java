/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.domain.services.notificacions;

import es.zarca.covellog.domain.model.usuarios.Usuario;


/**
 *
 * @author Francisco Torralbo
 */
public interface NotificacionsService {
    
    public void enviarNotificacions();
    public void afegirNotificacio(Usuario destinatari, String titol, String detall);
    
}
