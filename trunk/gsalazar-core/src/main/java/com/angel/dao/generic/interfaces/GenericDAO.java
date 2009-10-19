/**
 *
 */
package com.angel.dao.generic.interfaces;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections15.Closure;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;

import com.angel.dao.generic.query.builder.QueryBuilder;

/** Define a data access object type in your application. Its functionality is separate
 * business object from data access objects. Here is the logic to find all, find by all criteria,
 * persist, all your business object.
 *
 * @author William
 *
 */
public interface GenericDAO<T extends Object, Code extends Serializable> {

	/** Find an instance by an unique code.
	 *
	 * @param code to find.
	 * @param lock repository if it is true. If its false, it isn't locked.
	 * @return object instance found by code.
	 */
	public T findUniqueByCode(Code code, boolean lock);

	/** It has the same functionality that findByCode(Code code, boolean lock), but it doesn't lock.
	 *
	 * @param code to find.
	 * @return object instance found by code.
	 */
	public T findUniqueByCode(Code code);

	/** Find all instances.
	 *
	 * @return an instances list.
	 */
	public Collection<T> findAll();

	/** Find an unique entity for a property-value restriction.
	 *
	 * @param property name to filter.
	 * @param value to filter property.
	 * @return an unique entity instance.
	 */
	public T findUnique(String property, Object value);

	/** Find a unique instance filtering by a properties values map. It throws
	 *
	 * @param propertiesValues map to filter.
	 * @return an instance matched all properties values.
	 */
	public T findUnique(Map<String,Object> propertiesValues);

	/** Find a unique  or null instance filtering by a property with a value.
	 *
	 * @param property to find.
	 * @param value to filtering property.
	 * @return a unique or null entity instance which matches with filter.
	 */
	public T findUniqueOrNull(String property, Object value);

	/** Find a unique or null instances filtering by a properties values map.
	 *
	 * @param propertiesValues map to filtering.
	 * @return a unique or null entity instances which matches with properties values map filters.
	 */
	public T findUniqueOrNull(Map<String,Object> propertiesValues);

	/** Find a collection instances filtering by a properties values map.
	 *
	 * @param propertiesValues map to filter.
	 * @return a instances list which matches all properties values.
	 */
	public Collection<T> findAll(Map<String,Object> propertiesValues);

	/** Count all instances.
	 *
	 * @return how many instances.
	 */
	public Long count();

	/** Count instances which matches filtering with a property and its value.
	 *
	 * @param property to filter.
	 * @param value to assign to property.
	 * @return how many instances matches with property - value.
	 */
	public Long count(String property, Object value);

	/** Count instances which matches filtering with an example instance.
	 *
	 * @param exampleInstance to filter.
	 * @return how many instances matches.
	 */
	public Long count(T exampleInstance);

	/** Find instances objects filtering with an example instance.
	 *
	 * @param exampleInstance to filter.
	 * @return a list matched with example instance.
	 */
	public Collection<T> findAll(T exampleInstance);

	/** Persist on repository an entity.
	 *
	 * @param entity to persist on repository.
	 * @return an entity persisted.
	 */
    public T persist(T entity);

    /** Persist on repository an entity without commit.
     *
     * @param entity to persist on repository.
     * @return an entity persisted.
     */
    public T persistWithoutCommit(T entity);

    /** Persist on repository an entity. Then if commit is true it is commited. Otherwise it doesn't is commited.
     *
     * @param entity to persist.
     * @param commit defines if it commits current transaction.
     * @return an entity persisted.
     */
    public T persist(T entity, boolean commit);

    /** Persist on repository a collection entities.
     *
     * @param entities to persist.
     * @return a entities collection persisted.
     */
    public Collection<T> persistAll(Collection<T> entities);

    /** Delete all entities instances.
     *
     */
    public void deleteAll();

    /** Delete all entities.
     *
     * @param entities to delete.
     */
    public void deleteAll(Collection<T> entities);

    /** Delete all instances filtering by an example instance.
     *
     * @param exampleInstance to filtering.
     */
    public void deleteAllBy(T exampleInstance);

    /** Delete an entity instance. Commit after delete entity.
     *
     * @param entity to delete.
     */
    public void delete(T entity);

    /** Delete an entity. It doesn't commit after delete entity.
     *
     * @param entity to delete.
     */
    public void deleteWithoutCommit(T entity);

    /** Delete an entity instance. If commit is true, it commits current transaction. Otherwise it doesn't commit.
     *
     * @param entity to delete.
     * @param commit reference if it commits transaction.
     */
    public void delete(T entity, boolean commit);

    /** Delete an entity filtering with its code.
     *
     * @param code to filter.
     */
    public void deleteBy(Code code);

    /** For all entities do an action.
     *
     * @param closure to do.
     */
    public void doForAll(Closure<T> closure);

    /** Do an action for all entities in the list.
     *
     * @param entities to applies an action.
     * @param closure action to applies.
     */
    public void doForAll(Collection<T> entities, Closure<T> closure);

    /** Filter all entities with a condition or predicate.
     *
     * @param predicate to filter all entities.
     * @return entities list filtered.
     */
    public Collection<T> filterBy(Predicate<T> predicate);

    /** Filter entities list with a condition or predicate.
     *
     * @param entites to filter.
     * @param predicate to applies filtering.
     * @return a entities list filtered.
     */
    public Collection<T> filterBy(Collection<T> entities, Predicate<T> predicate);

    /** Collect for all entities a collection list entities.
     *
     * @param transformer to collect list new entities.
     * @return a collection transformed.
     */
    public Collection<? extends Object> collect(Transformer<T, ? extends Object> transformer);

    /** Collect for entities a collection new list entities.
     *
     * @param entities to transforms.
     * @param transformer to collect list new entities.
     * @return a collection transformed.
     */
    public Collection<?> collect(Collection<T> entities, Transformer<T, ? extends Object> transformer);

    /** Commit current transaction.
     *
     */
    public void commitCurrentTransaction();

    /** Begins a new transaction.
     *
     */
	public void beginTransaction();

	/** Test if the generic DAO is for the persistent class.
	 *
	 * @param persistentClass to tests.
	 * @return true if generic DAO is for persistent class. Otherwise it returns false.
	 */
	public boolean isForPersistentClass(Class<? extends Object> persistentClass);

	/** Test if the generic DAO is for code class.
	 *
	 * @param codeClass to tests.
	 * @return true if the generic DAO is for oode class. Otherwise it returns false.
	 */
	public boolean isForCodeClass(Class<? extends Serializable> codeClass);

	/** Count all distinct object values for a property name.
	 *
	 * @param property name to count its distinct values.
	 * @return quantity all distinct objects values.
	 */
	public Long countAllDistinct(String property);

	/** Find all distinct object values for a property object.
	 *
	 * @param property name to find all its values.
	 * @return a collection object with all its property values.
	 */
	public Collection<?> findAllDistinct(String property);

	/** Find all instances filter by a property value, and ordered its result with a order policy comparator.
	 *
	 * @param property to filter.
	 * @param value to associate to property.
	 * @param orderPolicy to order returns collection.
	 * @return an instances collection ordered by a order policy.
	 */
	public Collection<T> findAll(String property, Object value);

	/** Execute a query with a properties values.
	 *
	 * @param properties values to filtering query.
	 * @param query to execute.
	 * @return quantity instances affected in the query.
	 */
	public Long executeQuery(Map<String, Object> properties, String query);

	/** Find a unique instances with a query. Query is filtering with properties values.
	 *
	 * @param properties values to filtering query.
	 * @param query to execute to find all instances.
	 * @return an unique instance.
	 */
	public T findUniqueByQuery(Map<String, Object> properties, String query);

	/** Find all instances with a query and filtering its result with properties values.
	 *
	 * @param properties values to filter query result.
	 * @param query to execute to find all instances.
	 * @return an instances list.
	 */
	public Collection<T> findAllByQuery(Map<String, Object> properties, String query);

	/** Find all instances from a object class with a query. Query is filtered with a properties values map.
	 * Each field from query must match with field name object class instance.
	 *
	 * @param <C> type instance to returns.
	 * @param properties values to filtering query.
	 * @param query to execute to returns instances.
	 * @param beanClass type to returns.
	 * @param filters
	 * @return an instances collection.
	 */
	public <C extends Object> Collection<C> findAll(Map<String, Object> properties, String query, Class<C> beanClass, FindAllInstancesFilter<C> ...filters);

	/** Find an unique instance from a object class with a query. Query is filtered with a properties values map.
	 * Each field from query must match with field name object class instance.
	 *
	 * @param <C> type instance to returns.
	 * @param properties values to filtering query.
	 * @param query to execute to returns instances.
	 * @param objectClass type to returns.
	 * @return an unique instance.
	 */
	public <C extends Object> C findUnique(Map<String, Object> properties, String query, Class<C> beanClass);

	/** Add a action to find all instances.
	 *
	 * @param action to add.
	 */
	public void addInstacesAction(FindAllInstancesAction<T> action);

	/** Add a filter to find all instances.
	 *
	 * @param filter to add.
	 */
	public void addInstacesFilter(FindAllInstancesFilter<T> filter);

	/** Clear all filters added.
	 *
	 */
	public void clearFilters();

	/** Clear all actions added.
	 *
	 */
	public void clearActions();

	/** Persist or update an entity.
	 *
	 * @param entity to persist or update.
	 */
	public void persistOrUpdate(T entity);

	/** Persist or update a entity's collection.
	 *
	 * @param entities to persist or update.
	 */
	public void persistOrUpdateAll(Collection<T> entities);

	/** Merge an entity.
	 *
	 * @param entity to merge.
	 */
	public void merge(T entity);

	/** Merge an entity's collection.
	 *
	 * @param entities to merge.
	 */
	public void mergeAll(Collection<T> entities);

	/** Update an entity.
	 *
	 * @param entity to update.
	 */
	public void update(T entity);

	/** Update an entity's collection.
	 *
	 * @param entities to update.
	 */
	public void updateAll(Collection<T> entities);

	public void rollbackTransaction();
	
	public Collection<T> findAllByQueryBuilder(QueryBuilder queryBuilder);
	
	public T findUniqueByQueryBuilder(QueryBuilder queryBuilder);
	
	public Collection<T> findAllByQuery(com.angel.dao.generic.query.Query query);
}
