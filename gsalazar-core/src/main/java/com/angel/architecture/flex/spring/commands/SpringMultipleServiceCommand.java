package com.angel.architecture.flex.spring.commands;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Transformer;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.angel.architecture.exceptions.NonBusinessException;
import com.angel.architecture.flex.spring.commands.impl.MultipleServiceCommand;
import com.angel.common.helpers.ReflectionHelper;

import flex.messaging.services.ServiceException;

/**
 * @author Wiliiam
 */
public class SpringMultipleServiceCommand extends MultipleServiceCommand{

    private final static Logger LOGGER = Logger.getLogger(SpringMultipleServiceCommand.class);

    @SuppressWarnings("unchecked")
	public Object execute() {
        Object service = this.findService();
        Method method = getMethod(service, super.getMethodName());
        CollectionUtils.transform(super.getArguments(),
        new Transformer() {
            public Object transform(Object _object) {
                if(_object instanceof SpringMultipleServiceCommand) {
                    return ((SpringMultipleServiceCommand)_object).getResult();
                }
                return _object;
            }

        });

        try {
            super.setResult(method.invoke(service, super.getArguments().toArray()));
            return super.getResult();
        } catch (Exception e) {
            String message = "Remote method with name [" + this.getMethodName() + "] failed on invocation.";
            RuntimeException nbe = new NonBusinessException(message, e);
            LOGGER.error(message, nbe);
            throw nbe;
        }

    }

    private Object findService() {
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(flex.messaging.FlexContext.getServletConfig().getServletContext());
        String beanName = super.getServiceName();
        try {
            return appContext.getBean(beanName);
        } catch (NoSuchBeanDefinitionException nexc) {
            ServiceException e = new ServiceException();
            String msg = "Spring service named '" + beanName + "' does not exist.";
            e.setMessage(msg);
            e.setRootCause(nexc);
            e.setDetails(msg);
            e.setCode("Server.Processing");
            throw e;
        } catch (BeansException bexc) {
            ServiceException e = new ServiceException();
            String msg = "Unable to create Spring service named '" + beanName + "' ";
            e.setMessage(msg);
            e.setRootCause(bexc);
            e.setDetails(msg);
            e.setCode("Server.Processing");
            throw e;
        }
    }

    /**
     * Retorna el método que necesitamos ejecutar,
     * no hay sobrecarga
     *
     * @param _service Service
     * @param _methodName String nombre del metodo
     * @return Method
     */
    @SuppressWarnings("unchecked")
	protected Method getMethod(Object _service, String _methodName) {
    	Class[] classes = new Class[super.getArguments().size()];
    	int index = 0;
    	for(Object o: super.getArguments()){
    		classes[index] = o.getClass();
    		index++;
    	}
        /*Collection<Class> types = CollectionUtils.transformedCollection(super.getArguments(), new Transformer<Object, Class>(){
                        public Class transform(Object o) {
                            return o.getClass();
                        }
        }
            );*/

        Method method = ReflectionHelper.getMethod(_service, _methodName, classes);
        return method;
        /*
        try {
            Class[] classes = new Class[types.size()];
            int i = 0;
            for(Class c: types){
                classes[i] = c;
                i++;
            }

            Method method = _service.getClass().getMethod(_methodName, classes);
            return method;
        } catch (NoSuchMethodException e) {
            throw new NonBusinessException("Not found method name [" + _methodName + "] for service [Class: " + _service.getClass() + "]", e);
        }*/
        //Method m = ReflectionHelper.getMethod()
        /*for (Method method: methods) {
            Boolean areEqual = true;
            if(method.getParameterTypes() != null && super.getArguments() != null && method.getParameterTypes().length == super.getArguments().size()){
                areEqual = ReflectionHelper.areEqualsTypeClass(method.getParameterTypes(),super.getArguments());
            }
            if(method.getName().equals(_methodName) ) {
                return method;
            }
        }
        throw new RuntimeException("No existe el metodo, cambiar esta exc por otra mas copada");
        */

    }

}
