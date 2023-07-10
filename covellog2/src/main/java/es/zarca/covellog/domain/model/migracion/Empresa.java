package es.zarca.covellog.domain.model.migracion;

import java.util.Date;

/**
 *
 * @author francisco
 */
public class Empresa {
    private Integer id;
    private String codiClient;
    private Integer potencialId;
    private Integer comercialId;
    private String nom;
    private String cif;
    private Integer tipusViaId;
    private String direccio;
    private String direccio2;
    private String localitat;
    private String codiPostal;
    private String provincia;
    private String paisId;
    private String atencioc;
    private Integer cTipusViaId;
    private String cDireccio;
    private String cDireccio2;
    private String cLocalitat;
    private String cCodiPostal;
    private String cProvincia;
    private String cPaisId;
    private String telefon1;
    private String telefon2;
    private String fax;
    private String email;
    private String emailFact;
    private String swift;
    private String iban;
    private String ccorrent;
    private String compteTransfer;
    private String observacions;
    private Integer formaPagllogId;
    private Integer venciLlogId;
    private Integer diaVenciLlog;
    private Boolean entAvancat;
    private Boolean devAvancat;
    private Integer mesosAvancat;
    private Boolean munAvancat;
    private Boolean desAvancat;
    private Integer tipusFacturacioId;
    private Integer formaPagCompId;
    private Integer venciCompId;
    private Integer diaVenciComp;
    private String tipusTarifa;
    private String tipus;
    private Boolean esClient;
    private Boolean esProveidor;
    private Boolean esDepot;
    private Boolean esTransportista;
    private String contacte;
    private Date dataSincro;
    private Integer usuarioCreadorId;
    private Date dataCreacio;
    private Integer usuarioModificadorId;
    private Date dataModificacio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodiClient() {
        return codiClient;
    }

    public void setCodiClient(String codiClient) {
        this.codiClient = codiClient;
    }

    public Integer getPotencialId() {
        return potencialId;
    }

    public void setPotencialId(Integer potencialId) {
        this.potencialId = potencialId;
    }

    public Integer getComercialId() {
        return comercialId;
    }

    public void setComercialId(Integer comercialId) {
        this.comercialId = comercialId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public Integer getTipusViaId() {
        return tipusViaId;
    }

    public void setTipusViaId(Integer tipusViaId) {
        this.tipusViaId = tipusViaId;
    }

    public String getDireccio() {
        return direccio;
    }

    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }

    public String getDireccio2() {
        return direccio2;
    }

    public void setDireccio2(String direccio2) {
        this.direccio2 = direccio2;
    }

    public String getLocalitat() {
        return localitat;
    }

    public void setLocalitat(String localitat) {
        this.localitat = localitat;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPaisId() {
        return paisId;
    }

    public void setPaisId(String paisId) {
        this.paisId = paisId;
    }

    public String getAtencioc() {
        return atencioc;
    }

    public void setAtencioc(String atencioc) {
        this.atencioc = atencioc;
    }

    public Integer getcTipusViaId() {
        return cTipusViaId;
    }

    public void setcTipusViaId(Integer cTipusViaId) {
        this.cTipusViaId = cTipusViaId;
    }

    public String getcDireccio() {
        return cDireccio;
    }

    public void setcDireccio(String cDireccio) {
        this.cDireccio = cDireccio;
    }

    public String getcDireccio2() {
        return cDireccio2;
    }

    public void setcDireccio2(String cDireccio2) {
        this.cDireccio2 = cDireccio2;
    }

    public String getcLocalitat() {
        return cLocalitat;
    }

    public void setcLocalitat(String cLocalitat) {
        this.cLocalitat = cLocalitat;
    }

    public String getcProvincia() {
        return cProvincia;
    }

    public void setcProvincia(String cProvincia) {
        this.cProvincia = cProvincia;
    }

    public String getcPaisId() {
        return cPaisId;
    }

    public void setcPaisId(String cPaisId) {
        this.cPaisId = cPaisId;
    }

    public String getTelefon1() {
        return telefon1;
    }

    public void setTelefon1(String telefon1) {
        this.telefon1 = telefon1;
    }

    public String getTelefon2() {
        return telefon2;
    }

    public void setTelefon2(String telefon2) {
        this.telefon2 = telefon2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailFact() {
        return emailFact;
    }

    public void setEmailFact(String emailFact) {
        this.emailFact = emailFact;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getCcorrent() {
        return ccorrent;
    }

    public void setCcorrent(String ccorrent) {
        this.ccorrent = ccorrent;
    }

    public String getCompteTransfer() {
        return compteTransfer;
    }

    public void setCompteTransfer(String compteTransfer) {
        this.compteTransfer = compteTransfer;
    }

    public String getObservacions() {
        return observacions;
    }

    public void setObservacions(String observacions) {
        this.observacions = observacions;
    }

    public Integer getFormaPagllogId() {
        return formaPagllogId;
    }

    public void setFormaPagllogId(Integer formaPagllogId) {
        this.formaPagllogId = formaPagllogId;
    }

    public Integer getVenciLlogId() {
        return venciLlogId;
    }

    public void setVenciLlogId(Integer venciLlogId) {
        this.venciLlogId = venciLlogId;
    }

    public Integer getDiaVenciLlog() {
        return diaVenciLlog;
    }

    public void setDiaVenciLlog(Integer diaVenciLlog) {
        this.diaVenciLlog = diaVenciLlog;
    }

    public Boolean getEntAvancat() {
        return entAvancat;
    }

    public void setEntAvancat(Boolean entAvancat) {
        this.entAvancat = entAvancat;
    }

    public Boolean getDevAvancat() {
        return devAvancat;
    }

    public void setDevAvancat(Boolean devAvancat) {
        this.devAvancat = devAvancat;
    }

    public Integer getMesosAvancat() {
        return mesosAvancat;
    }

    public void setMesosAvancat(Integer mesosAvancat) {
        this.mesosAvancat = mesosAvancat;
    }

    public Boolean getMunAvancat() {
        return munAvancat;
    }

    public void setMunAvancat(Boolean munAvancat) {
        this.munAvancat = munAvancat;
    }

    public Boolean getDesAvancat() {
        return desAvancat;
    }

    public void setDesAvancat(Boolean desAvancat) {
        this.desAvancat = desAvancat;
    }

    public Integer getTipusFacturacioId() {
        return tipusFacturacioId;
    }

    public void setTipusFacturacioId(Integer tipusFacturacioId) {
        this.tipusFacturacioId = tipusFacturacioId;
    }

    public Integer getFormaPagCompId() {
        return formaPagCompId;
    }

    public void setFormaPagCompId(Integer formaPagCompId) {
        this.formaPagCompId = formaPagCompId;
    }

    public Integer getVenciCompId() {
        return venciCompId;
    }

    public void setVenciCompId(Integer venciCompId) {
        this.venciCompId = venciCompId;
    }

    public Integer getDiaVenciComp() {
        return diaVenciComp;
    }

    public void setDiaVenciComp(Integer diaVenciComp) {
        this.diaVenciComp = diaVenciComp;
    }

    public String getTipusTarifa() {
        return tipusTarifa;
    }

    public void setTipusTarifa(String tipusTarifa) {
        this.tipusTarifa = tipusTarifa;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public Boolean getEsClient() {
        return esClient;
    }

    public void setEsClient(Boolean esClient) {
        this.esClient = esClient;
    }

    public Boolean getEsProveidor() {
        return esProveidor;
    }

    public void setEsProveidor(Boolean esProveidor) {
        this.esProveidor = esProveidor;
    }

    public Boolean getEsDepot() {
        return esDepot;
    }

    public void setEsDepot(Boolean esDepot) {
        this.esDepot = esDepot;
    }

    public Boolean getEsTransportista() {
        return esTransportista;
    }

    public void setEsTransportista(Boolean esTransportista) {
        this.esTransportista = esTransportista;
    }

    public String getContacte() {
        return contacte;
    }

    public void setContacte(String contacte) {
        this.contacte = contacte;
    }

    public Date getDataSincro() {
        return dataSincro;
    }

    public void setDataSincro(Date dataSincro) {
        this.dataSincro = dataSincro;
    }

    public Integer getUsuarioCreadorId() {
        return usuarioCreadorId;
    }

    public void setUsuarioCreadorId(Integer usuarioCreadorId) {
        this.usuarioCreadorId = usuarioCreadorId;
    }

    public Date getDataCreacio() {
        return dataCreacio;
    }

    public void setDataCreacio(Date dataCreacio) {
        this.dataCreacio = dataCreacio;
    }

    public Integer getUsuarioModificadorId() {
        return usuarioModificadorId;
    }

    public void setUsuarioModificadorId(Integer usuarioModificadorId) {
        this.usuarioModificadorId = usuarioModificadorId;
    }

    public Date getDataModificacio() {
        return dataModificacio;
    }

    public void setDataModificacio(Date dataModificacio) {
        this.dataModificacio = dataModificacio;
    }

    public String getCodiPostal() {
        return codiPostal;
    }

    public void setCodiPostal(String codiPostal) {
        this.codiPostal = codiPostal;
    }

    public String getcCodiPostal() {
        return cCodiPostal;
    }

    public void setcCodiPostal(String cCodiPostal) {
        this.cCodiPostal = cCodiPostal;
    }
    
    
    
}
