package com.angel.architecture.services;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.architecture.services.interfaces.GenericService;

/**
 *  Esta interface expone todos los a utilizar relacionados con los object id
 *
 *  @see com.angel.arquitectura.model.objectId.ObjectId
 *
 * @author ALeinvand
 * @version $Revision$
 */
public interface ObjectIdService extends GenericService{

    /**
     * @return un ObjectId unico
     */
    public ObjectId createOne();

}