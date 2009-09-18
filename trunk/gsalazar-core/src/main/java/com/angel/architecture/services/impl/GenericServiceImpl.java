package com.angel.architecture.services.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import com.angel.architecture.context.ExecutionContext;
import com.angel.architecture.dtos.SessionUserDTO;
import com.angel.architecture.exceptions.NonBusinessException;
import com.angel.architecture.flex.locator.ObjectLocator;
import com.angel.architecture.persistence.base.PersistentObject;
import com.angel.architecture.services.interfaces.GenericService;
import com.angel.common.helpers.ReflectionHelper;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * Clase que brinda utilidades para todas las implementaciones de los servicios de la aplicacion.
 *
 * @author ALeinvand
 * @version $Revision: 1.3.20.9 $
 */
public abstract class GenericServiceImpl implements GenericService{

    /**
     *  Esta variable permite que cada service tenga su dao correspondiente. el problema es que hay que
     * realizar un casteo para adquirir la funcionalidad especifica del dao que se desea, lo que se puede
     * hacer sino es sobreescribir el metodo y realizar el casteo dentro del mismo
     */
    @SuppressWarnings("unchecked")
	private GenericDAO genericDAO;

    public GenericServiceImpl(){
        super();
        //this.initialize();
    }

    protected void initialize(){
    	try {
	    	Class<?> objectClass = this.getClass().getSuperclass();
	    	boolean isAnnotationPresent = objectClass.isAnnotationPresent(com.angel.architecture.annotations.services.GenericDAO.class);
	    	if(isAnnotationPresent){
	    		com.angel.architecture.annotations.services.GenericDAO genericDAO = objectClass.getAnnotation(com.angel.architecture.annotations.services.GenericDAO.class);
	    		boolean containsBean = ObjectLocator.contains(genericDAO.daoName());
	    		if(containsBean){
	    			Object dao = ObjectLocator.getBeanByName(genericDAO.daoName());
	    			//Field[] fields = ReflectionHelper.getFieldsDeclaredFor(objectClass);
	    			Field genericDAOField = ReflectionHelper.getFieldByName(objectClass, "genericDAO");
	    			if(genericDAOField.get(this) == null){
	    				genericDAOField.setAccessible(true);
	    				ReflectionHelper.setFieldValue(this, genericDAOField, dao);
	    				genericDAOField.setAccessible(false);
	    			}
	    		}
	    	}
    	} catch(Throwable e){
    		throw new NonBusinessException("", e);
    	}
    }

    public SessionUserDTO getSessionUserDTO(){
		return ExecutionContext.getContext().getSessionUserDTO();
	}

    @SuppressWarnings("unchecked")
	public GenericDAO getGenericDAO() {
        return genericDAO;
    }

    @SuppressWarnings("unchecked")
	public void setGenericDAO(GenericDAO dao){
        this.genericDAO = dao;
    }

    @SuppressWarnings({"unchecked"})
    public Object findUniqueByID(Serializable id) {
        return this.getGenericDAO().findUniqueByCode(id);
    }

    @SuppressWarnings({"unchecked"})
    //@Secured(rol = {"admin"})
    public Collection<Object> findAll() {
        return this.getGenericDAO().findAll();
    }

    @SuppressWarnings({"unchecked"})
    //public void create(BusinessObject businessObject) {
    public PersistentObject create(PersistentObject persistentObject) {
    	persistentObject.updateNullAttributes();
        return (PersistentObject) this.getGenericDAO().persist(persistentObject);
    }

    @SuppressWarnings({"unchecked"})
    public void remove(Object persistentObject) {
        this.getGenericDAO().delete(persistentObject);
    }

    @SuppressWarnings({"unchecked"})
    public Object update(Object persistentObject) {
        Object object = persistentObject;
        this.getGenericDAO().update(object);
        return persistentObject;
    }
}