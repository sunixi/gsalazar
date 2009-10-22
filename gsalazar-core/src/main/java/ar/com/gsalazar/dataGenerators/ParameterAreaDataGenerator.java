package ar.com.gsalazar.dataGenerators;

import java.io.Serializable;
import java.util.Collection;

import com.angel.architecture.flex.locator.ObjectLocator;
import com.angel.architecture.persistence.beans.ParameterArea;
import com.angel.dao.generic.factory.DAOFactory;
import com.angel.dao.generic.interfaces.GenericDAO;
import com.angel.data.generator.interfaces.DataGenerator;

/**
 *
 * @author William
 */
public class ParameterAreaDataGenerator implements DataGenerator {
    /**
	 * 
	 */
	private static final long serialVersionUID = -9016187611831969598L;

	@SuppressWarnings("unchecked")
	public Class<? extends DataGenerator>[] getDependecesGenerators() {
        return (Class<? extends DataGenerator>[]) new Class[0];
    }

    public void generateData(Collection<Object> objects, DAOFactory daoFactory) {
        ParameterArea negocio = new ParameterArea();
        negocio.setName("Negocio");
        negocio.setDescription("Parametros de configuracion relacionadas al negocio.");

        ParameterArea noNegocio = new ParameterArea();
        noNegocio.setName("No negocio");
        noNegocio.setDescription("Parametros de configuracion no relacionados al negocio.");

        ParameterArea otros = new ParameterArea();
        otros.setName("Otros");
        otros.setDescription("Parametros de configuracion para otras areas.");


        objects.add(negocio);
        objects.add(noNegocio);
        objects.add(otros);
    }

    public Class<? extends Object> getDataGeneratorClass() {
        return ParameterArea.class;
    }

    @SuppressWarnings("unchecked")
	public GenericDAO<Object, Serializable> getGenericDAO() {
        return (GenericDAO<Object, Serializable>) ObjectLocator.getBeanByName("parameterAreaDAO");
    }

	public Class<?> thisDataGeneratorClass() {
		return this.getClass();
	}
}