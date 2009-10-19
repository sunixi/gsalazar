/**
 *
 */
package com.angel.dao.generic.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.QueryParameterException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.hql.ast.QuerySyntaxException;

import com.angel.dao.generic.exceptions.GenericDAOException;
import com.angel.dao.generic.exceptions.InvalidQueryException;
import com.angel.dao.generic.exceptions.NotFoundUniqueObjectException;
import com.angel.dao.generic.exceptions.OnDeleteHibernateException;
import com.angel.dao.generic.exceptions.OnPersitHibernateException;
import com.angel.dao.generic.exceptions.UniqueExpectedResultException;
import com.angel.dao.generic.helpers.GenericHibernateDAOConstants;
import com.angel.dao.generic.helpers.HibernateCriteriaHelper;
import com.angel.dao.generic.interfaces.FindAllInstancesFilter;
import com.angel.dao.generic.interfaces.TransactionStrategy;
import com.angel.dao.generic.query.builder.QueryBuilder;

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
public class GenericHibernateDAO<T extends Object, Code extends Serializable> extends AbstractGenericDAO<T, Code> {

    private Session session;

    /** Default constructor for implementations sub class.
     *
     */
	public GenericHibernateDAO() {
		super(new HibernateTransactionStrategy());
		this.session = (Session) super.getTransaction().getCurrentSession();
	}

	public GenericHibernateDAO(TransactionStrategy transaction, Class<T> persistentClass, Class<Code> codeClass) {
		super(transaction, persistentClass, codeClass);
		this.session = (Session) transaction.getCurrentSession();
	}

	public GenericHibernateDAO(Class<T> persistentClass, Class<Code> codeClass) {
		super(new HibernateTransactionStrategy(), persistentClass, codeClass);
		this.session = (Session) super.getTransaction().getCurrentSession();
	}

	public GenericHibernateDAO(Session session, Class<T> persistentClass, Class<Code> codeClass) {
		super(new HibernateTransactionStrategy(), persistentClass, codeClass);
		this.session = session;
	}

	public GenericHibernateDAO(Session session) {
		super(new HibernateTransactionStrategy());
		this.session = session;
	}

	/**
	 * @return the session
	 */
	protected Session getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	protected void setSession(Session session) {
		this.session = session;
	}

	public Collection<T> findAll(){
		return this.findAllByCriteria();
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

    /** Find all objects with an order and a criterion lists.
     *
     * @param order parameter to order results.
     * @param criterion criterion list to find all.
     * @return objects list.
     */
    @SuppressWarnings("unchecked")
	protected Collection<T> findAllByCriteria(Order order, Criterion... criterion) {
    	Criteria criteria = this.buildCriteriaFor(criterion);
    	criteria.addOrder(order);
        return criteria.list();
	}

    /** Creates a criteria with a criterion list.
     *
     * @param criterion criterion's list to add to the criteria.
     * @return a criteria with its criterions.
     */
    private Criteria buildCriteriaFor(Criterion... criterion){
        return HibernateCriteriaHelper.buildCriteriaFor(this.getSession(), this.getPersistentClass(), criterion);
    }

    /** Delete a entities' list specificated by criterias.
     *
     * @param criterion
     */
    protected void deleteAllByCriteria(Criterion... criterion){
    	Collection<T> toDelete = this.findAllByCriteria(criterion);
    	this.deleteAll(toDelete);
    }

    public void flush() {
        getSession().flush();
    }

    public void clear() {
        getSession().clear();
    }

	public final void delete(T entity, boolean commit){
		try{
			if(commit){
				this.beginTransaction();
			}
			this.getSession().delete(entity);
			if(commit){
				this.commitCurrentTransaction();
			}
		} catch(HibernateException e){
			throw new OnDeleteHibernateException(e);
		}
	}

	/** Delete all entities which matches with example instance.
	 *
	 * @param exampleInstance to match all entities to deletes.
	 */
	@SuppressWarnings("unchecked")
	public final void deleteAllBy(T exampleInstance){
		try{
			Criteria criteria = this.buildCriteriaFor(Example.create(exampleInstance));
			List<? extends T> matchedEntities = criteria.list();
			this.beginTransaction();
			for(T entity: matchedEntities){
				this.delete(entity);
			}
			this.commitCurrentTransaction();
		} catch(HibernateException e){
			throw new OnDeleteHibernateException(e);
		}
	}

	public final T persist(T entity, boolean commit){
		try {
			if(commit){
				this.beginTransaction();
			}
			this.getSession().save(entity);
			if(commit){
				this.commitCurrentTransaction();
			}
		} catch(HibernateException e){
			throw new OnPersitHibernateException(e);
		}
		return entity;
	}

	/** Find a unique instance with a criterion list as filter. If it has like result a null object, it throws a NotFoundUniqueObjectException, because
	 * it assumes that expected a unique object.
	 *
	 * @param criterion list to filler.
	 * @return instance found.
	 */
	@SuppressWarnings("unchecked")
	protected T findUniqueBy(Criterion... criterion){
		Criteria criteria = this.buildCriteriaFor(criterion);
		T uniqueResult = (T) criteria.uniqueResult();
		if(uniqueResult == null){
			throw new NotFoundUniqueObjectException("Unique object not found at " + this.getPersistentClass().getName() + " using Criteria [" + criteria.toString() + "]");
		}
		return uniqueResult;
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


	public Collection<T> findAll(T exampleInstance) {
		return this.findAllByCriteria(Example.create(exampleInstance));
	}

	@SuppressWarnings("unchecked")

	public T findUniqueByCode(Code code, boolean lock) {
		if(lock){
			return (T) this.getSession().load(this.getPersistentClass(), code, LockMode.FORCE);
		} else {
			return (T) this.getSession().load(this.getPersistentClass(), code, LockMode.NONE);
		}
	}


	public Long count(String property, Object value) {
		Collection<T> entities = this.findAllByCriteria(Restrictions.eq(property, value));
		return super.getCollectionSize(entities);
	}

	@SuppressWarnings("unchecked")

	public Collection<T> findAll(Map<String, Object> propertiesValues) {
		Criteria criteria = this.buildCriteriaWith(propertiesValues);
		Collection<T> entities = criteria.list();
		return this.prepareResultCollection(entities);
	}

	/** Build a criteria with a properties values map.
	 *
	 * @param propertiesValues map to create a criteria.
	 * @return a criteria instance with a properties values map.
	 */
	protected Criteria buildCriteriaWith(Map<String,Object> propertiesValues){
		return HibernateCriteriaHelper.buildCriteriaWith(this.getSession(), this.getPersistentClass(), propertiesValues);
	}

	/** Build a criteria with a criterions list.
	 *
	 * @param criterions to add to criteria.
	 * @return a criteria with a criterions list.
	 */
	protected Criteria buildCriteriaWith(List<Criterion> criterions){
		return HibernateCriteriaHelper.buildCriteriaWith(this.getSession(), this.getPersistentClass(), criterions);
	}


	public T findUnique(Map<String, Object> propertiesValues) {
		Criteria criteria = this.buildCriteriaWith(propertiesValues);
		T uniqueResult = this.findUniqueByCriteria(criteria);
		return uniqueResult;
	}

	/** Find all instances which matches with a hql query.
	 *
	 * @param hqlQuery to execute.
	 * @return an entities instances list.
	 */
	@SuppressWarnings("unchecked")
	protected Collection<T> findAllByHQLQuery(String hqlQuery){
		Query query = this.getSession().createQuery(hqlQuery);
		return query.list();
	}

	/** Find all instances which matches with a sql query.
	 *
	 * @param sqlQuery to execute.
	 * @return an entities instances list.
	 */
	@SuppressWarnings("unchecked")
	protected Collection<T> findAllBySQLQuery(String sqlQuery){
		SQLQuery query = this.getSession().createSQLQuery(sqlQuery);
		return query.list();
	}


	public Long count() {

		Long quantity = (Long) this.getSession()
			.createQuery(GenericHibernateDAOConstants.BASIC_SQL_COUNT_QUERY_CLAUSE + this.getPersistentClassName() + " x").uniqueResult();
		return quantity;
	}


	public Long countAllDistinct(String property) {
		Long quantityDistinct = (Long) this.getSession()
			.createQuery(	"	select" +
							"		count(distinct " + property + " )" +
							"	from " +
							"		" + this.getPersistentClassName() +
							"	where " +
							"		" + property + " is not null ")
			.uniqueResult();
		return quantityDistinct;
	}


	public Collection<?> findAllDistinct(String property) {
		Query query = this.getSession()
			.createQuery(	"	select" +
							"		distinct " + property +
							"	from " +
							"		" + this.getPersistentClassName() +
							"	where " +
							"		" + property + " is not null ");
		return query.list();
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

	private Query createQuery(Map<String, Object> properties, String query){
		return HibernateCriteriaHelper.createQuery(this.getSession(), properties, query, this.getPersistentClass());
	}

	private Query createHQLQuery(Map<String, Object> properties, String query){
		return HibernateCriteriaHelper.createHQLQuery(this.getSession(), properties, query);
	}


	public <C> Collection<C> findAll(Map<String, Object> properties, String query, Class<C> beanClass,  FindAllInstancesFilter<C> ...filters) {
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

	private <C> Collection<C> transformResultListQueryToBeanClass(Query query, Class<C> beanClass){
        return HibernateCriteriaHelper.transformResultListQueryToBeanClass(query, beanClass);
	}

	private <C> C transformUniqueResultQueryToBeanClass(Query query, Class<C> beanClass){
        return HibernateCriteriaHelper.transformUniqueResultQueryToBeanClass(query, beanClass);
	}

	@SuppressWarnings("unchecked")

	public Collection<T> findAllByQuery(Map<String, Object> properties, String query) {
		try{
			Query aQuery = this.createQuery(properties, query);
			return super.prepareResultCollection(aQuery.list());
		}catch (QuerySyntaxException e) {
			throw new InvalidQueryException("Cannot execute query because it is a invalid query.", e);
		} catch(QueryParameterException e){
			throw new InvalidQueryException("Cannot execute query because it has invalid parameters names.", e);
		}
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

	public void persistOrUpdate(T entity) {
		this.getSession().saveOrUpdate(entity);
	}

	public void persistOrUpdateAll(Collection<T> entities) {
		for(T entity: entities){
			this.persistOrUpdate(entity);
		}
	}

	public void merge(T entity){
		this.getSession().merge(entity);
	}

	public void mergeAll(Collection<T> entities){
		for(T entity: entities){
			this.merge(entity);
		}
	}

	public void updateAll(Collection<T> entities){
		for(T entity: entities){
			this.update(entity);
		}
	}

	public void update(T entity){
		this.getSession().update(entity);
	}

	public void rollbackTransaction() {
		this.getSession().getTransaction().rollback();
	}
	
	public Collection<T> findAllByQueryBuilder(QueryBuilder queryBuilder) {
		com.angel.dao.generic.query.Query query = queryBuilder.buildQuery();
		return this.findAllByQuery(query);
	}

	@SuppressWarnings("unchecked")
	public T findUniqueByQueryBuilder(QueryBuilder queryBuilder) {
		com.angel.dao.generic.query.Query query = queryBuilder.buildQuery();
		List<T> entities = null;
		try {
			Session session = (Session) super.getTransaction().getCurrentSession();
			entities = (List<T>) session.createQuery(query.getQuery()).setParameters(query.getParams(), null);
		} catch(Exception e){
			throw new GenericDAOException("Error during finding query [" + query.getQuery() + "]", e);
		}
		if(entities.size() > 1){
			throw new GenericDAOException("Not unique result for query [" + query.getQuery() + "].");
		}
		return entities.get(0);
	}

	@SuppressWarnings("unchecked")
	public Collection<T> findAllByQuery(com.angel.dao.generic.query.Query query) {
		List<T> entities = null;
		try {
			Session session = (Session) super.getTransaction().getCurrentSession();
			entities = (List<T>) session.createQuery(query.getQuery()).setParameters(query.getParams(), null);
		} catch(Exception e){
			throw new GenericDAOException("Error during finding query [" + query.getQuery() + "]", e);
		}
		return entities;
	}
}
