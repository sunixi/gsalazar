/**
 *
 */
package com.angel.dao.generic.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.Closure;
import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.QueryParameterException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.hibernate.hql.ast.QuerySyntaxException;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.angel.dao.generic.base.InstancesActionManager;
import com.angel.dao.generic.base.InstancesFilterManager;
import com.angel.dao.generic.exceptions.GenericDAOException;
import com.angel.dao.generic.exceptions.InvalidQueryException;
import com.angel.dao.generic.exceptions.NotFoundUniqueObjectException;
import com.angel.dao.generic.exceptions.OnDeleteHibernateException;
import com.angel.dao.generic.exceptions.OnPersitHibernateException;
import com.angel.dao.generic.exceptions.UniqueExpectedResultException;
import com.angel.dao.generic.helpers.GenericHibernateDAOConstants;
import com.angel.dao.generic.helpers.HibernateCriteriaHelper;
import com.angel.dao.generic.interfaces.FindAllInstancesAction;
import com.angel.dao.generic.interfaces.FindAllInstancesFilter;
import com.angel.dao.generic.interfaces.GenericDAO;
import com.angel.dao.generic.query.builder.QueryBuilder;
import com.angel.dao.generic.query.params.ParamType;
import com.angel.dao.generic.query.params.QueryConditionParam;

/** Define a data access object pattern. It has behaviour to manipulate business objects with the repository. In this class inherit
 * all Hibernate (database repository) sub class.
 *
 * To create a Generic Hibernate DAO object, you must define parameters like class and its unique identifier class.
 * For example:
 * GenericHibernateDAO<BusinessObject, Long> businessObjectDAO = new BusinessObjectHibernateDAO<Business, Object>();
 *
 * @author William
 *
 */
public class GenericSpringHibernateDAO<T extends Object, Code extends Serializable> extends HibernateDaoSupport implements GenericDAO<T, Code>{

	private Class<T> persistentClass = null;
	private Class<Code> codeClass = null;
    private InstancesActionManager<T> entitiesActionManager;
    private InstancesFilterManager<T> entitiesFilterManager;

	public GenericSpringHibernateDAO(Class<T> persistentClass, Class<Code> codeClass){
    	this(persistentClass);
        this.codeClass = codeClass;
    }

    public GenericSpringHibernateDAO(Class<T> persistentClass){
    	super();
    	this.entitiesActionManager = new InstancesActionManager<T>();
    	this.entitiesFilterManager = new InstancesFilterManager<T>();
        this.persistentClass= persistentClass;
    }


	public final void delete(T entity){
		this.delete(entity, Boolean.TRUE.booleanValue());
	}


	public final void deleteWithoutCommit(T entity){
		this.delete(entity, Boolean.FALSE.booleanValue());
	}

	/** Persist an entity at the repository.
	 *
	 */

	public final T persist(T entity){
		return this.persist(entity, Boolean.TRUE.booleanValue());
	}


	public void commitCurrentTransaction(){
		this.getCurrentSession().getTransaction().begin();
	}


	public void beginTransaction(){
		this.getCurrentSession().beginTransaction();
	}


	public T findUniqueByCode(Code code) {
		return this.findUniqueByCode(code, false);
	}


	public void deleteBy(Code code){
		T entity = this.findUniqueByCode(code);
		if(entity != null){
			this.delete(entity);
		} else {
			throw new NotFoundUniqueObjectException("Object not found [Code: "+ code + " - PersistenceClass: "+ this.getPersistentClass() +"]to delete.");
		}
	}

	/** Remove all entities from list.
	 *
	 * @param entities to delete.
	 */

	public final void deleteAll(Collection<T> entities){
		for(T t: entities){
			this.delete(t);
		}
	}


	public final void deleteAll(){
		this.deleteAll(this.findAll());
	}


	public void doForAll(Closure<T> closure) {
		Collection<T> entities = this.findAll();
		this.doForAll(entities, closure);
	}


	public void doForAll(Collection<T> entities, Closure<T> closure) {
		if(org.apache.commons.collections.CollectionUtils.isNotEmpty(entities)){
			Collection<T> filteredEntities = this.prepareResultCollection(entities);
			CollectionUtils.forAllDo(filteredEntities, closure);
		}
	}


	public Collection<T> filterBy(Predicate<T> predicate) {
		Collection<T> entities = this.findAll();
		return this.filterBy(entities, predicate);
	}


	public Collection<T> filterBy(Collection<T> entities, Predicate<T> predicate) {
		if(org.apache.commons.collections.CollectionUtils.isNotEmpty(entities)){
			Collection<T> filteredEntities = this.prepareResultCollection(entities);
			CollectionUtils.filter(filteredEntities, predicate);
		}
		return entities;
	}



	public Long count(T exampleInstance) {
		Collection<T> entities = this.findAll(exampleInstance);
		return this.getCollectionSize(entities);
	}


	public Collection<? extends Object> collect(Transformer<T, ? extends Object> transformer) {
		Collection<T> entities = this.findAll();
		return this.collect(entities, transformer);
	}


	public Collection<?> collect(Collection<T> entities, Transformer<T, ? extends Object> transformer) {
		if(org.apache.commons.collections.CollectionUtils.isNotEmpty(entities)){
			Collection<T> filteredEntities = this.prepareResultCollection(entities);
			return CollectionUtils.collect(filteredEntities, transformer);
		}
		return entities;
	}


	public Collection<T> persistAll(Collection<T> entities) {
		List<T> entitiesPersisted = new ArrayList<T>();
		for(T entity: entities){
			T entityPersisted = this.persist(entity);
			entitiesPersisted.add(entityPersisted);
		}
		return entitiesPersisted;
	}


	public T findUnique(String property, Object value) {
		Map<String, Object> propertiesValues = new HashMap<String, Object>();
		propertiesValues.put(property, value);
		return this.findUnique(propertiesValues);
	}


	public T findUniqueOrNull(String property, Object value) {
		Map<String, Object> propertiesValues = new HashMap<String, Object>();
		propertiesValues.put(property, value);
		return this.findUniqueOrNull(propertiesValues);
	}


	public T findUniqueOrNull(Map<String, Object> propertiesValues) {
		Collection<T> entities = this.findAll(propertiesValues);
		if(entities != null && entities.size() > 1){
			throw new UniqueExpectedResultException("A unique result expected for query. It found as result [" + entities.size() +"] entities.");
		}
		return (entities != null && entities.size() == 1 ? entities.iterator().next(): null);
	}


	public T persistWithoutCommit(T entity) {
		return this.persist(entity, Boolean.FALSE.booleanValue());
	}


	public final boolean isForPersistentClass(Class<? extends Object> persistentClass){
		return this.getPersistentClass() != null && this.getPersistentClass().equals(persistentClass);
	}


	public final boolean isForCodeClass(Class<? extends Serializable> codeClass){
		return this.getCodeClass() != null && this.getCodeClass().equals(codeClass);
	}

	public Collection<T> findAll(String property, Object value){
		Map<String, Object> propertiesValues = new HashMap<String, Object>();
		propertiesValues.put(property, value);
		Collection<T> instances = this.findAll(propertiesValues);
		return (Collection<T>) this.prepareResultCollection(instances);
	}

	protected Collection<T> prepareResultCollection(Collection<T> entities){
		Collection<T> resultCollection = this.entitiesFilterManager.appliesFilters(entities);
		resultCollection = this.entitiesActionManager.appliesActions(resultCollection);
		return resultCollection;
	}

	protected Collection<T> prepareResultUnique(T entity){
		return null;
	}


	public void addInstacesAction(FindAllInstancesAction<T> action){
		this.entitiesActionManager.addInstancesAction(action);
	}


	public void addInstacesFilter(FindAllInstancesFilter<T> filter){
		this.entitiesFilterManager.addInstancesFilter(filter);
	}


	public void clearFilters(){
		this.entitiesFilterManager.clearFilters();
	}


	public void clearActions(){
		this.entitiesActionManager.clearActions();
	}

	/**
	 * @return the persistentClass
	 */
	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	/**
	 * @param persistentClass the persistentClass to set
	 */
	public void setPersistentClass(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	/**
	 * @return the codeClass
	 */
	public Class<Code> getCodeClass() {
		return codeClass;
	}

	/**
	 * @param codeClass the codeClass to set
	 */
	public void setCodeClass(Class<Code> codeClass) {
		this.codeClass = codeClass;
	}


	public Long count() {
		Long quantity = (Long) this.getCurrentSession()
			.createQuery(GenericHibernateDAOConstants.BASIC_SQL_COUNT_QUERY_CLAUSE + this.getPersistentClassName() + " x").uniqueResult();
		return quantity;
	}

	private String getPersistentClassName() {
		return this.getPersistentClass().getName();
	}


	public Long count(String property, Object value) {
		Collection<T> entities = this.findAllByCriteria(Restrictions.eq(property, value));
		return this.getCollectionSize(entities);
	}

	/** Find objects with a specific criteria. You can find with any criteria, and in this case this message find all
	 * objects at the repository or database.
	 *
	 * @param criterion criterias to find a object's list.
	 * @return a result list objects.
	 */
    @SuppressWarnings("unchecked")
	protected Collection<T> findAllByCriteria(Criterion... criterion) {
    	Criteria crit = this.buildCriteriaFor(criterion);
    	Collection<T> instances = crit.list();
        return this.prepareResultCollection(instances);
	}

	protected Long getCollectionSize(Collection<T> entities){
		return ( entities != null ? Long.valueOf(String.valueOf(entities.size())): 0 );
	}


	public Long countAllDistinct(String property) {
		Long quantityDistinct = (Long) this.getCurrentSession()
		.createQuery(	"	select" +
						"		count(distinct " + property + " )" +
						"	from " +
						"		" + this.getPersistentClassName() +
						"	where " +
						"		" + property + " is not null ")
		.uniqueResult();
		return quantityDistinct;
	}


	public void delete(T entity, boolean commit) {
		this.getCurrentSession().delete(entity);
	}

    /** Creates a criteria with a criterion list.
    *
    * @param criterion criterion's list to add to the criteria.
    * @return a criteria with its criterions.
    */
   private Criteria buildCriteriaFor(Criterion... criterion){
   	Criteria criteria = this.getCurrentSession().createCriteria(this.getPersistentClass());
       for (Criterion c : criterion) {
       	criteria.add(c);
       }
       return criteria;
   }

	@SuppressWarnings("unchecked")
	public void deleteAllBy(T exampleInstance) {
		try{
			Criteria criteria = this.buildCriteriaFor(Example.create(exampleInstance));
			List<? extends T> matchedEntities = criteria.list();
			for(T entity: matchedEntities){
				this.delete(entity);
			}
		} catch(HibernateException e){
			throw new OnDeleteHibernateException(e);
		}
	}

	private Query createQuery(Map<String, Object> properties, String query){
		return HibernateCriteriaHelper.createQuery(this.getCurrentSession(), properties, query, persistentClass);
	}


	public Long executeQuery(Map<String, Object> properties, String query) {
		try {
			Query aQuery = this.createQuery(properties, query);
			return Long.valueOf(aQuery.executeUpdate());
		} catch (QuerySyntaxException e) {
			throw new InvalidQueryException("Cannot execute query because it is a invalid query.", e);
		} catch(QueryParameterException e){
			throw new InvalidQueryException("Cannot execute query because it has invalid parameters names.", e);
		}
	}


	public Collection<T> findAll() {
		Collection<T> instances = this.findAllByCriteria();
		return this.prepareResultCollection(instances);
	}

	@SuppressWarnings("unchecked")
	public Collection<T> findAll(Map<String, Object> propertiesValues) {
		Criteria criteria = this.buildCriteriaWith(propertiesValues);
		Collection<T> entities = criteria.list();
		return this.prepareResultCollection(entities);
	}

	public Collection<T> findAll(T exampleInstance) {
		return this.findAllByCriteria(Example.create(exampleInstance));
	}


	public <C> Collection<C> findAll(Map<String, Object> properties, String query, Class<C> beanClass, FindAllInstancesFilter<C>... filters) {
		try {
			Query aQuery = this.createHQLQuery(properties, query);
			Collection<C> resultList = this.transformResultListQueryToBeanClass(aQuery, beanClass);
			return resultList;
		} catch (QuerySyntaxException e) {
			throw new InvalidQueryException("Cannot execute query because it is a invalid query.", e);
		} catch(QueryParameterException e){
			throw new InvalidQueryException("Cannot execute query because it has invalid parameters names.", e);
		}
	}

	@SuppressWarnings("unchecked")
	private <C> Collection<C> transformResultListQueryToBeanClass(Query query, Class<C> beanClass){
		query.setResultTransformer(Transformers.aliasToBean(beanClass));
        Collection<C> resultList = query.list();
        return resultList;
	}

	@SuppressWarnings("unchecked")
	private <C> C transformUniqueResultQueryToBeanClass(Query query, Class<C> beanClass){
		query.setResultTransformer(Transformers.aliasToBean(beanClass));
        C uniqueResult = (C) query.uniqueResult();
        return uniqueResult;
	}

	private Query createHQLQuery(Map<String, Object> properties, String query){
		Query aQuery = this.getCurrentSession().createQuery(query);
		if(properties != null){
			for(String property: properties.keySet()){
				aQuery = aQuery.setParameter(property, properties.get(property));
			}
		}
		return aQuery;
	}

	@SuppressWarnings("unchecked")

	public Collection<T> findAllByQuery(Map<String, Object> properties, String query) {
		try{
			Query aQuery = this.createQuery(properties, query);
			return this.prepareResultCollection(aQuery.list());
		}catch (QuerySyntaxException e) {
			throw new InvalidQueryException("Cannot execute query because it is a invalid query.", e);
		} catch(QueryParameterException e){
			throw new InvalidQueryException("Cannot execute query because it has invalid parameters names.", e);
		}
	}


	public Collection<?> findAllDistinct(String property) {
		Query query = this.getCurrentSession()
		.createQuery(	"	select" +
						"		distinct " + property +
						"	from " +
						"		" + this.getPersistentClassName() +
						"	where " +
						"		" + property + " is not null ");
	return query.list();
	}


	public T findUnique(Map<String, Object> propertiesValues) {
		Criteria criteria = this.buildCriteriaWith(propertiesValues);
		T uniqueResult = this.findUniqueByCriteria(criteria);
		return uniqueResult;
	}

	/** Build a criteria with a properties values map.
	 *
	 * @param propertiesValues map to create a criteria.
	 * @return a criteria instance with a properties values map.
	 */
	protected Criteria buildCriteriaWith(Map<String,Object> propertiesValues){
		List<Criterion> criterions = new ArrayList<Criterion>();
		Set<String> keysSet = propertiesValues.keySet();
		for(String key: keysSet){
			Object value = propertiesValues.get(key);
			criterions.add(Restrictions.eq(key, value));
		}
		return this.buildCriteriaWith(criterions);
	}

	/** Build a criteria with a criterions list.
	 *
	 * @param criterions to add to criteria.
	 * @return a criteria with a criterions list.
	 */
	protected Criteria buildCriteriaWith(List<Criterion> criterions){
		Criteria criteria = this.buildCriteriaFor();
		for(Criterion c: criterions){
			criteria.add(c);
		}
		return criteria;
	}

	/**  Find a unique instance with a criteria with filters. If it has like result a null object, it throws a NotFoundUniqueObjectException, because
	 * it assumes that expected a unique object.
	 *
	 * @param criteria to find a unqiue result.
	 * @return an unique entity instance.
	 */
	@SuppressWarnings("unchecked")
	protected T findUniqueByCriteria(Criteria criteria){
		T uniqueResult = null;
		try {
			uniqueResult = (T) criteria.uniqueResult();
			if(uniqueResult == null){
				throw new NotFoundUniqueObjectException("Unique object not found at " + this.getPersistentClass().getName() + " using Criteria [" + criteria.toString() + "]");
			}
		} catch(NonUniqueResultException e) {
			throw new UniqueExpectedResultException("A unique result expected for query. It found more than an unique result.");
		}
		return uniqueResult;
	}


	public <C> C findUnique(Map<String, Object> properties, String query, Class<C> beanClass) {
		try{
			Query aQuery = this.createHQLQuery(properties, query);
			C uniqueResult = this.transformUniqueResultQueryToBeanClass(aQuery, beanClass);
			return uniqueResult;
		} catch (QuerySyntaxException e) {
			throw new InvalidQueryException("Cannot execute query because it is a invalid query.", e);
		} catch(QueryParameterException e){
			throw new InvalidQueryException("Cannot execute query because it has invalid parameters names.", e);
		}
	}

	@SuppressWarnings("unchecked")

	public T findUniqueByCode(Code code, boolean lock) {
		if(lock){
			return (T) this.getCurrentSession().load(this.getPersistentClass(), code, LockMode.FORCE);
		} else {
			return (T) this.getCurrentSession().load(this.getPersistentClass(), code, LockMode.NONE);
		}
	}

	private Session getCurrentSession(){
		return super.getSessionFactory().getCurrentSession();
	}

	@SuppressWarnings("unchecked")

	public T findUniqueByQuery(Map<String, Object> properties, String query) {
		try{
			Query aQuery = this.createQuery(properties, query);
			return (T) aQuery.uniqueResult();
		} catch (QuerySyntaxException e) {
			throw new InvalidQueryException("Cannot execute query because it is a invalid query.", e);
		} catch(QueryParameterException e){
			throw new InvalidQueryException("Cannot execute query because it has invalid parameters names.", e);
		}
	}


	public T persist(T entity, boolean commit) {
		try {
			/*if(commit){
				this.beginTransaction();
			}*/
			getCurrentSession().save(entity);

			/*if(commit){
				this.commitCurrentTransaction();
			}*/
		} catch(HibernateException e){
			/*if(commit){
				this.rollbackTransaction();
			}*/
			throw new OnPersitHibernateException(e);
		}
		return entity;
	}

	public void persistOrUpdate(T entity) {
		this.getCurrentSession().saveOrUpdate(entity);
	}

	public void persistOrUpdateAll(Collection<T> entities) {
		this.getCurrentSession().saveOrUpdate(entities);
	}

	public void update(T entity) {
		this.getCurrentSession().update(entity);
	}

	public void merge(T entity) {
		this.getCurrentSession().merge(entity);
	}

	public void mergeAll(Collection<T> entities) {
		for(T entity: entities){
			this.merge(entity);
		}
	}

	public void updateAll(Collection<T> entities) {
		for(T entity: entities){
			this.update(entity);
		}
	}

	public void rollbackTransaction() {
		this.getCurrentSession().getTransaction().rollback();
	}

	public Collection<T> findAllByQueryBuilder(QueryBuilder queryBuilder) {
		com.angel.dao.generic.query.Query query = queryBuilder.buildQuery();
		return this.findAllByQuery(query);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<T> findAllByQuery(com.angel.dao.generic.query.Query query) {
		List<T> entities = null;
		try {
			super.getHibernateTemplate().setFetchSize(query.getFetchSize());
			super.getHibernateTemplate().setMaxResults(query.getMaxResult());
			if(query.hasParams()){
				Query q = this.buildQuery(query);
				entities = q.list();
			} else {
				entities = super.getHibernateTemplate().find(query.getQuery());
			}
		} catch(Exception e){
			throw new GenericDAOException("Error during finding query [" + query.getQuery() + "]", e);
		}
		return entities;
	}

	@SuppressWarnings("unchecked")
	public T findUniqueByQueryBuilder(QueryBuilder queryBuilder) {
		com.angel.dao.generic.query.Query query = queryBuilder.buildQuery();
		List<T> entities = null;
		try {
			Query q = this.buildQuery(query);
			entities = q.list();
		} catch(Exception e){
			throw new GenericDAOException("Error during finding query [" + query.getQuery() + "]", e);
		}
		if(entities.size() > 1){
			throw new GenericDAOException("Not unique result for query [" + query.getQuery() + "].");
		}
		if(entities.isEmpty()){
			throw new GenericDAOException("Query [" + query.getQuery() + "] didn't return a unique and NOT NULL result.");
		}
		return entities.get(0);
	}
	
	@SuppressWarnings("unchecked")
	protected Query buildQuery(com.angel.dao.generic.query.Query query){
		Query q = super.getSession().createQuery(query.getQuery());
		List<QueryConditionParam> conditions = query.getConditions();
		Object[] params = query.getParams();
		for(int i = 0; i < conditions.size(); i++){
			QueryConditionParam qcp = conditions.get(i);
			if(ParamType.OBJECT_PARAMETER == qcp.getParamType()){
				q.setParameter("param_" + i, params[i]);
			} else {
				q.setParameterList("param_" + i, (Collection) params[i]);
			}
		}
		return q;
	}
	
	public <C> Collection<C> findAll(QueryBuilder queryBuilder, Class<C> beanClass) {
		try {
			Query aQuery = this.buildQuery(queryBuilder.buildQuery());
			Collection<C> resultList = this.transformResultListQueryToBeanClass(aQuery, beanClass);
			return resultList;
		} catch (QuerySyntaxException e) {
			throw new InvalidQueryException("Cannot execute query because it is a invalid query.", e);
		} catch(QueryParameterException e){
			throw new InvalidQueryException("Cannot execute query because it has invalid parameters names.", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <C> List<C> findAll(QueryBuilder queryBuilder) {
		try {
			com.angel.dao.generic.query.Query query = queryBuilder.buildQuery();
			List<C> entities = null;
			try {
				Query aQuery = this.buildQuery(query);
				entities = aQuery.list();
			} catch(Exception e){
				throw new GenericDAOException("Error during finding query [" + query.getQuery() + "]", e);
			}
			return entities;
		} catch (QuerySyntaxException e) {
			throw new InvalidQueryException("Cannot execute query because it is a invalid query.", e);
		} catch(QueryParameterException e){
			throw new InvalidQueryException("Cannot execute query because it has invalid parameters names.", e);
		}
	}
}
