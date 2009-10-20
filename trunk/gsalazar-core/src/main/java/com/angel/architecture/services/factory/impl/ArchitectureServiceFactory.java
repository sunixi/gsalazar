package com.angel.architecture.services.factory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.angel.architecture.services.ActionService;
import com.angel.architecture.services.ClickUserService;
import com.angel.architecture.services.ErrorMessageService;
import com.angel.architecture.services.InitializerConfigurationService;
import com.angel.architecture.services.LanguageService;
import com.angel.architecture.services.MultipleService;
import com.angel.architecture.services.ObjectIdService;
import com.angel.architecture.services.RoleService;
import com.angel.architecture.services.SecurityService;
import com.angel.architecture.services.TagSearchService;
import com.angel.architecture.services.UserService;
import com.angel.architecture.services.factory.ServiceFactory;

/**
 * COMENTARIO AQUI.
 *
 * @author William
 * @since 05/April/2009
 *
 */
@Component
public class ArchitectureServiceFactory implements ServiceFactory {

    private static ArchitectureServiceFactory INSTANCE;

    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private ObjectIdService objectIdService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ActionService actionService;
    @Autowired
    private MultipleService multipleService;
    @Autowired
    private ErrorMessageService errorMessageService;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private InitializerConfigurationService initializerConfigurationService;
    @Autowired
    private ClickUserService clickUserService;
    @Autowired
    private TagSearchService tagSearchService;

    private ArchitectureServiceFactory() {
        super();
    }

    public static ArchitectureServiceFactory createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ArchitectureServiceFactory();
            return INSTANCE;
        }
        return INSTANCE;
    }

    public ObjectIdService getObjectIdService() {
        return this.objectIdService;
    }

    public void setObjectIdService(ObjectIdService objectIdService) {
    	this.objectIdService = objectIdService;
    }

    public UserService getUserService() {
        return this.userService;
    }

    public void setUserService(UserService userService) {
    	this.userService = userService;
    }

    public SecurityService getSecurityService() {
        return this.securityService;
    }

    public void setSecurityService(SecurityService securityService) {
    	this.securityService = securityService;
    }

    public RoleService getRoleService() {
        return this.roleService;
    }

    public void setRoleService(RoleService roleService) {
    	this.roleService = roleService;
    }

    public ActionService getActionService() {
        return this.actionService;
    }

    public void setActionService(ActionService actionService) {
    	this.actionService = actionService;
    }

    public MultipleService getMultipleService() {
        return this.multipleService;
    }

    public void setMultipleService(MultipleService multipleService) {
    	this.multipleService = multipleService;
    }

	/**
	 * @return the errorMessageService
	 */
	public ErrorMessageService getErrorMessageService() {
		return this.errorMessageService;
	}

	/**
	 * @param errorMessageService the errorMessageService to set
	 */
	public void setErrorMessageService(ErrorMessageService errorMessageService) {
		this.errorMessageService = errorMessageService;
	}

	/**
	 * @return the languageService
	 */
	public LanguageService getLanguageService() {
		return this.languageService;
	}

	/**
	 * @param languageService the languageService to set
	 */
	public void setLanguageService(LanguageService languageService) {
		this.languageService = languageService;
	}

	/**
	 * @return the initializerConfigurationService
	 */
	public InitializerConfigurationService getInitializerConfigurationService() {
		return initializerConfigurationService;
	}

	/**
	 * @param initializerConfigurationService the initializerConfigurationService to set
	 */
	public void setInitializerConfigurationService(
			InitializerConfigurationService initializerConfigurationService) {
		this.initializerConfigurationService = initializerConfigurationService;
	}

	/**
	 * @return the clickUserService
	 */
	public ClickUserService getClickUserService() {
		return clickUserService;
	}

	/**
	 * @param clickUserService the clickUserService to set
	 */
	public void setClickUserService(ClickUserService clickUserService) {
		this.clickUserService = clickUserService;
	}

	/**
	 * @return the tagSearchService
	 */
	public TagSearchService getTagSearchService() {
		return tagSearchService;
	}

	/**
	 * @param tagSearchService the tagSearchService to set
	 */
	public void setTagSearchService(TagSearchService tagSearchService) {
		this.tagSearchService = tagSearchService;
	}
}