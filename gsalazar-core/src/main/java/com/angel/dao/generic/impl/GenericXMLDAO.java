/**
 *
 */
package com.angel.dao.generic.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.transaction.NotSupportedException;

import com.angel.dao.generic.interfaces.FindAllInstancesFilter;
import com.angel.dao.generic.interfaces.TransactionStrategy;
import com.angel.dao.generic.query.Query;
import com.angel.dao.generic.query.builder.QueryBuilder;

/**
 * @author William
 *
 */
public class GenericXMLDAO<T extends Object, Code extends Serializable> extends AbstractGenericDAO<T, Code> {

	public GenericXMLDAO(TransactionStrategy transactionStrategy) {
		super(transactionStrategy);
	}

	public GenericXMLDAO(Class<T> persistentClass, Class<Code> codeClass) {
		super(null, persistentClass, codeClass);
	}

	public GenericXMLDAO(TransactionStrategy transactionStrategy, Class<T> persistentClass, Class<Code> codeClass) {
		super(transactionStrategy, persistentClass, codeClass);
	}


	public Long count(String property, Object value) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}


	public void delete(T entity, boolean commit) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}


	public void deleteAllBy(T exampleInstance) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}


	public List<T> findAll() {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}


	public List<T> findAll(Map<String, Object> propertiesValues) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}


	public List<T> findAll(T exampleInstance) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}


	public T findUnique(Map<String, Object> propertiesValues) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}


	public T persist(T entity, boolean commit) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}


	public Long count() {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}


	public Long countAllDistinct(String property) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}


	public Collection<?> findAllDistinct(String property) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}


	public T findUniqueByCode(Code code, boolean lock) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}


	public Long executeQuery(Map<String, Object> properties, String query) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}


	public <C> Collection<C> findAll(Map<String, Object> properties, String query, Class<C> objectClass,  FindAllInstancesFilter<C> ...filters) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}


	public List<T> findAllByQuery(Map<String, Object> properties, String query) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}


	public <C> C findUnique(Map<String, Object> properties, String query, Class<C> objectClass) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}


	public T findUniqueByQuery(Map<String, Object> properties, String query) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}

	public void merge(T entity) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}

	public void mergeAll(Collection<T> entities) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}

	public void persistOrUpdate(T entity) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}

	public void persistOrUpdateAll(Collection<T> entities) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));	}

	public void update(T entity) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}

	public void updateAll(Collection<T> entities) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}

	public void rollbackTransaction() {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}

	public Collection<T> findAllByQueryBuilder(QueryBuilder queryBuilder) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}
	public T findUniqueByQueryBuilder(QueryBuilder queryBuilder) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}

	public Collection<T> findAllByQuery(Query query) {
		throw new RuntimeException(new NotSupportedException("Not supported funcionality."));
	}
}
