package com.angel.architecture.persistence.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.CallbackException;
import org.hibernate.Session;
import org.hibernate.classic.ValidationFailure;

import com.angel.architecture.persistence.interfaces.Persistent;
import com.angel.common.helpers.ObjectIdHelper;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;

/**
 *  Esta es la clase persistente de las cuales deberian extender todas las clases de negocio que
 * deberian ser persistentes.
 *
 * @author ALeinvand
 * @version $Revision: 1.16.4.11 $
 */
@MappedSuperclass
public abstract class PersistentObject extends BusinessObject implements Persistent{

    private static final long serialVersionUID = 1L;

	private static final Log LOGGER = LogFactory.getLog(PersistentObject.class);

	@Version
    @Column(nullable = false)
    private Long versionNumber;

    @Column(nullable = false, updatable = false)
    private Date creationDate = new Date();

    @Column(nullable = true)
    private Date updateDate;

    @Transient
    private boolean isSynchronizing = false;

    /**
     * METODO SOBRESCRITO: COMENTARIO AQUI.
     *
     * @see org.hibernate.classic.Lifecycle#onSave(org.hibernate.Session)
     */
    public final boolean onSave(Session arg0) throws CallbackException {
        this.updateNullAttributes();
        LOGGER.debug("Saving persistent object for class [" + this.getClass() + "] with object id [" + this.getIdAsString() + "].");
        return NO_VETO;
    }

    /**
     * METODO SOBRESCRITO: COMENTARIO AQUI.
     *
     * @see org.hibernate.classic.Lifecycle#onUpdate(org.hibernate.Session)
     */
    public final boolean onUpdate(Session arg0) throws CallbackException {
        this.setUpdateDate(new Date());
        LOGGER.debug("Updating persistent object for class [" + this.getClass() + "] with object id [" + this.getIdAsString() + "].");
        return NO_VETO;
    }

    /**
     * METODO SOBRESCRITO: COMENTARIO AQUI.
     *
     * @see org.hibernate.classic.Lifecycle#onDelete(org.hibernate.Session)
     */
    public final boolean onDelete(Session arg0) throws CallbackException {
        LOGGER.debug("Deleting persistent object for class [" + this.getClass() + "] with object id [" + this.getIdAsString() + "].");
        return NO_VETO;
    }

    /**
     * METODO SOBRESCRITO: COMENTARIO AQUI.
     *
     * @see org.hibernate.classic.Lifecycle#onLoad(org.hibernate.Session, java.io.Serializable)
     */
    public final void onLoad(Session arg0, Serializable arg1) {
        LOGGER.debug("Loading persistent object for class [" + this.getClass() + "] with object id [" + this.getIdAsString() + "].");
    }

    /**
     * Este metodo realiza validaciones sobre un Elemento antes de ser persistido o modificado
     *
     * @see org.hibernate.classic.Validatable#validate()
     */
    public void validate() throws ValidationFailure {
        LOGGER.debug("Validating persistent object for class [" + this.getClass() + "] with object id [" + this.getIdAsString() + "].");
    }

    /**
     * Este metodo es llamado desde el generic dao y desde el hibernate interceptor.
     * utiliza el template method, para agregarle funcionalidad al sincronizar.
     *
     */
    public final synchronized void synchronizedState() {
        if(!isSynchronizing){
            isSynchronizing=true;
            this.sincronizar();
            isSynchronizing=false;
        }
    }

    protected void sincronizar(){

    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public final void updateNullAttributes() {
        if(this.getCreationDate() == null){
            this.setCreationDate(new Date());
        }
        if(this.getId() == null){
        	this.setId(ObjectIdHelper.createObjectId());
        }
    }
}