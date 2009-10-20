package com.angel.architecture.daos.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.angel.architecture.daos.ActionDAO;
import com.angel.architecture.daos.ClickUserDAO;
import com.angel.architecture.daos.ConfigurationParameterDAO;
import com.angel.architecture.daos.CountryDAO;
import com.angel.architecture.daos.ErrorMessageDAO;
import com.angel.architecture.daos.LanguageDAO;
import com.angel.architecture.daos.RoleDAO;
import com.angel.architecture.daos.TagSearchDAO;
import com.angel.architecture.daos.UserDAO;
import com.angel.architecture.daos.UserRoleDAO;
import com.angel.dao.generic.factory.DAOFactory;
import com.angel.io.daos.ProcessorLogDAO;
import com.angel.io.daos.RowMessageLogDAO;

/**
 *
 * @author William
 * @since 05/April/2009
 */
@Component
public class ArchitectureDAOFactory extends DAOFactory{

    private static ArchitectureDAOFactory INSTANCE;

    @Autowired
    private ActionDAO actionDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private UserRoleDAO userRoleDAO;
    @Autowired
    private LanguageDAO languageDAO;
    @Autowired
    private ConfigurationParameterDAO configurationParameterDAO;
    @Autowired
    private ErrorMessageDAO errorMessageDAO;
    @Autowired
    private CountryDAO countryDAO;
    @Autowired
    private RowMessageLogDAO rowMessageLogDAO;
    @Autowired
    private ProcessorLogDAO processorLogDAO;
    @Autowired
    private ClickUserDAO clickUserDAO;
    @Autowired
    private TagSearchDAO tagSearchDAO;
    
    private ArchitectureDAOFactory() {
        super();
    }

    public synchronized static ArchitectureDAOFactory createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ArchitectureDAOFactory();
        }
        return INSTANCE;
    }

    public UserDAO getUserDAO() {
        return this.userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
    	this.userDAO = userDAO;
    }

    public RoleDAO getRoleDAO() {
        return this.roleDAO;
    }

    public void setRoleDAO(RoleDAO roleDAO) {
    	this.roleDAO = roleDAO;
    }

    public ActionDAO getActionDAO() {
        return this.actionDAO;
    }

    public void setActionDAO(ActionDAO accionDAO) {
    	this.actionDAO = accionDAO;
    }

    public UserRoleDAO getUserRoleDAO() {
        return this.userRoleDAO;
    }

    public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
        this.userRoleDAO = userRoleDAO;
    }

    public ConfigurationParameterDAO getConfigurationParameterDAO() {
        return configurationParameterDAO;
    }

    public void setConfigurationParameterDAO(ConfigurationParameterDAO configurationParameterDAO) {
        this.configurationParameterDAO = configurationParameterDAO;
    }

    public LanguageDAO getLanguageDAO() {
        return languageDAO;
    }

    public void setLanguageDAO(LanguageDAO languageDAO) {
        this.languageDAO = languageDAO;
    }

	/**
	 * @return the errorMessageDAO
	 */
	public ErrorMessageDAO getErrorMessageDAO() {
		return errorMessageDAO;
	}

	/**
	 * @param errorMessageDAO the errorMessageDAO to set
	 */
	public void setErrorMessageDAO(ErrorMessageDAO errorMessageDAO) {
		this.errorMessageDAO = errorMessageDAO;
	}

	/**
	 * @return the countryDAO
	 */
	public CountryDAO getCountryDAO() {
		return countryDAO;
	}

	/**
	 * @param countryDAO the countryDAO to set
	 */
	public void setCountryDAO(CountryDAO countryDAO) {
		this.countryDAO = countryDAO;
	}

	/**
	 * @return the rowMessageLogDAO
	 */
	public RowMessageLogDAO getRowMessageLogDAO() {
		return rowMessageLogDAO;
	}

	/**
	 * @param rowMessageLogDAO the rowMessageLogDAO to set
	 */
	public void setRowMessageLogDAO(RowMessageLogDAO rowMessageLogDAO) {
		this.rowMessageLogDAO = rowMessageLogDAO;
	}

	/**
	 * @return the processorLogDAO
	 */
	public ProcessorLogDAO getProcessorLogDAO() {
		return processorLogDAO;
	}

	/**
	 * @param processorLogDAO the processorLogDAO to set
	 */
	public void setProcessorLogDAO(ProcessorLogDAO processorLogDAO) {
		this.processorLogDAO = processorLogDAO;
	}

	/**
	 * @return the clickUserDAO
	 */
	public ClickUserDAO getClickUserDAO() {
		return clickUserDAO;
	}

	/**
	 * @param clickUserDAO the clickUserDAO to set
	 */
	public void setClickUserDAO(ClickUserDAO clickUserDAO) {
		this.clickUserDAO = clickUserDAO;
	}

	/**
	 * @return the tagSearchDAO
	 */
	public TagSearchDAO getTagSearchDAO() {
		return tagSearchDAO;
	}

	/**
	 * @param tagSearchDAO the tagSearchDAO to set
	 */
	public void setTagSearchDAO(TagSearchDAO tagSearchDAO) {
		this.tagSearchDAO = tagSearchDAO;
	}	
}