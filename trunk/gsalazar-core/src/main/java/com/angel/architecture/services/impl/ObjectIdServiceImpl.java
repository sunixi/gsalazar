package com.angel.architecture.services.impl;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.architecture.services.ObjectIdService;
import com.angel.common.helpers.ObjectIdHelper;

/**
 *
 * @author William
 * @since 10/April/2009
 */
public class ObjectIdServiceImpl extends GenericServiceImpl implements ObjectIdService{

    public ObjectId createOne() {
        return ObjectIdHelper.createObjectId();
    }

}