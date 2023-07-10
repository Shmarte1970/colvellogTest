/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.domain.services.notificacions.internal;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author francisco
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(es.zarca.covellog.domain.services.notificacions.internal.GenericResource.class);
        /*resources.add(service.PaisFacadeREST.class);
        resources.add(service.PoblacioFacadeREST.class);
        resources.add(service.ProvinciaFacadeREST.class);*/
        resources.add(es.zarca.covellog.interfaces.web.app.controller.FamiliaProductoFacadeREST.class);
        resources.add(es.zarca.covellog.interfaces.web.app.controller.TipoProductoClaseFacadeREST.class);
        resources.add(es.zarca.covellog.interfaces.web.app.controller.TipoProductoDocumentoFacadeREST.class);
        resources.add(es.zarca.covellog.interfaces.web.app.controller.TipoProductoFacadeREST.class);
        resources.add(es.zarca.covellog.interfaces.web.app.controller.UnidadMedidaFacadeREST.class);
        resources.add(es.zarca.covellog.resources.JavaEE8Resource.class);
    }
    
}
