package ar.com.gsalazar.dataGenerators;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import com.angel.architecture.constants.ArchitectureDAOsNames;
import com.angel.architecture.daos.ParameterAreaDAO;
import com.angel.architecture.flex.locator.ObjectLocator;
import com.angel.architecture.persistence.beans.ConfigurationParameter;
import com.angel.architecture.persistence.beans.ParameterArea;
import com.angel.dao.generic.factory.DAOFactory;
import com.angel.dao.generic.interfaces.GenericDAO;
import com.angel.data.generator.interfaces.DataGenerator;

/**
 *
 * @author William
 */
public class ConfigurationParameterDataGenerator implements DataGenerator {
    /**
	 * 
	 */
	private static final long serialVersionUID = -9016187611831969598L;

	@SuppressWarnings("unchecked")
	public Class<? extends DataGenerator>[] getDependecesGenerators() {
        return (Class<? extends DataGenerator>[]) new Class<?>[]{ParameterAreaDataGenerator.class};
    }

    public void generateData(Collection<Object> objects, DAOFactory daoFactory) {
    	ParameterAreaDAO parameterAreaDAO = ObjectLocator.getBeanByName(ArchitectureDAOsNames.PARAMETER_AREA_DAO_NAME);
    	/**
		ParameterArea negocio = parameterAreaDAO.findUniqueByName("Negocio");
		ParameterArea otros = parameterAreaDAO.findUniqueByName("Otros");
    	 */
    	ParameterArea noNegocio = parameterAreaDAO.findUniqueByName("No negocio");

        ConfigurationParameter versionAplicacion = new ConfigurationParameter();
        versionAplicacion.setName("SYSTEM_LAST_VERSION");
        versionAplicacion.setDescription("Ultima version del sistema.");
        versionAplicacion.setDoubleValue(0.91);
        versionAplicacion.setParameterArea(noNegocio);
        objects.add(versionAplicacion);

        ConfigurationParameter fechaUltimaActualizacion = new ConfigurationParameter();
        fechaUltimaActualizacion.setName("SYSTEM_LAST_UPDATE");
        fechaUltimaActualizacion.setDescription("Fecha de la ultima actualizacion del sistema.");
        fechaUltimaActualizacion.setDateValue(new Date());
        fechaUltimaActualizacion.setParameterArea(noNegocio);
        objects.add(fechaUltimaActualizacion);
        
        ConfigurationParameter fechaUltimaActualizacionCV = new ConfigurationParameter();
        fechaUltimaActualizacionCV.setName("CV_LAST_UPDATE");
        fechaUltimaActualizacionCV.setDescription("Fecha de la ultima actualizacion del curriculum vitae.");
        fechaUltimaActualizacionCV.setDateValue(new Date());
        fechaUltimaActualizacionCV.setParameterArea(noNegocio);
        objects.add(fechaUltimaActualizacionCV);
    }

    public Class<? extends Object> getDataGeneratorClass() {
        return ConfigurationParameter.class;
    }

    @SuppressWarnings("unchecked")
	public GenericDAO<Object, Serializable> getGenericDAO() {
        return (GenericDAO<Object, Serializable>) ObjectLocator.getBeanByName(ArchitectureDAOsNames.CONFIGURATION_PARAMETER_DAO_NAME);
    }

	public Class<?> thisDataGeneratorClass() {
		return this.getClass();
	}
}