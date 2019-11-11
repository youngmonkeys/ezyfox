package com.tvd12.ezyfox.morphia.repository;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import com.mongodb.WriteResult;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.database.query.EzyFindAndModifyOptions;
import com.tvd12.ezyfox.database.query.EzyUpdateOperations;
import com.tvd12.ezyfox.exception.UnimplementedOperationException;
import com.tvd12.ezyfox.function.EzyApply;
import com.tvd12.ezyfox.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.morphia.EzyDatastoreAware;
import com.tvd12.ezyfox.morphia.query.impl.EzySimpleFindAndModifyOptions;
import com.tvd12.ezyfox.morphia.query.impl.EzySimpleUpdateOperations;
import com.tvd12.ezyfox.reflect.EzyGenerics;
import com.tvd12.ezyfox.util.EzyLoggable;

import dev.morphia.Datastore;
import dev.morphia.DeleteOptions;
import dev.morphia.FindAndModifyOptions;
import dev.morphia.UpdateOptions;
import dev.morphia.query.FindOptions;
import dev.morphia.query.Query;
import dev.morphia.query.UpdateOperations;
import dev.morphia.query.internal.MorphiaCursor;
import lombok.Setter;

public abstract class EzyDatastoreRepository<I, E> 
		extends EzyLoggable
		implements EzyMongoRepository<I, E>, EzyDatastoreAware {

	@Setter
	@EzyAutoBind
	protected Datastore datastore;
	
	@Override
	public long count() {
		Query<E> query = newQuery();
		long count = query.count();
		return count;
	}

	@Override
	public void save(E entity) {
		datastore.save(entity);
	}

	@Override
	public void save(Iterable<E> entities) {
		datastore.save(entities);
	}

	@Override
	public E findById(I id) {
		E e = findByField("_id", id);
		return e;
	}
	
	@Override
	public E findByField(String field, Object value) {
		Query<E> query = newQuery(field, value);
		E e = query.first();
		return e;
	}
	
	@Override
	public List<E> findListByIds(Collection<I> ids) {
		Query<E> query = newQuery().field("_id").in(ids);
		MorphiaCursor<E> cursor = query.find();
		List<E> list = cursor.toList();
		return list;
	}
	
	@Override
	public List<E> findListByField(String field, Object value) {
		Query<E> query = newQuery(field, value);
		MorphiaCursor<E> cursor = query.find();
		List<E> list = cursor.toList();
		return list;
	}
	
	@Override
	public List<E> findListByField(String field, Object value, int skip, int limit) {
		FindOptions options = new FindOptions().skip(skip).limit(limit);
		Query<E> query = newQuery(field, value);
		MorphiaCursor<E> cursor = query.find(options);
		List<E> list = cursor.toList();
		return list;
	}

	@Override
	public List<E> findAll() {
		Query<E> query = newQuery();
		MorphiaCursor<E> cursor = query.find();
		List<E> list = cursor.toList();
		return list;
	}
	
	@Override
	public List<E> findAll(int skip, int limit) {
		FindOptions options = new FindOptions().skip(skip).limit(limit);
		Query<E> query = newQuery();
		MorphiaCursor<E> cursor = query.find(options);
		List<E> list = cursor.toList();
		return list;
	}
	
	@Override
	public void updateOneById(I id, E entity) {
		updateOneById(id, entity, false);
	}
	
	@Override
	public void updateOneById(I id, E entity, boolean upsert) {
		Query<E> query = newQuery("_id", id);
		datastore.updateFirst(query, entity, upsert);
	}
	
	@Override
	public void updateOneById(I id, EzyApply<EzyUpdateOperations<E>> operations) {
		updateOneById(id, operations, false);
	}
	
	@Override
	public void updateOneById(I id, EzyApply<EzyUpdateOperations<E>> operations, boolean upsert) {
		updateOneByQuery(newQuery("_id", id), operations, upsert);
	}
	
	@Override
	public void updateOneByField(String field, Object value, E entity) {
		updateOneByField(field, value, entity, false);
	}
	
	@Override
	public void updateOneByField(String field, Object value, E entity, boolean upsert) {
		Query<E> query = newQuery(field, value);
		datastore.updateFirst(query, entity, upsert);
	}
	
	@Override
	public void updateOneByField(String field, Object value, EzyApply<EzyUpdateOperations<E>> operations) {
		updateOneByField(field, value, operations, false);
	}
	
	@Override
	public void updateOneByField(String field, Object value, EzyApply<EzyUpdateOperations<E>> operations, boolean upsert) {
		updateOneByQuery(newQuery(field, value), operations, upsert);
	}
	
	@Override
	public void updateManyByField(String field, Object value, EzyApply<EzyUpdateOperations<E>> operations) {
		UpdateOperations<E> realOperations = datastore.createUpdateOperations(getEntityType());
		EzyUpdateOperations<E> proxyOperations = new EzySimpleUpdateOperations<>(realOperations);
		operations.apply(proxyOperations);
		UpdateOptions realOptions = new UpdateOptions().multi(true).upsert(false);
		datastore.update(newQuery(field, value), realOperations, realOptions);
	}
	
	private void updateOneByQuery(Query<E> query, EzyApply<EzyUpdateOperations<E>> operations, boolean upsert) {
		UpdateOperations<E> realOperations = datastore.createUpdateOperations(getEntityType());
		EzyUpdateOperations<E> proxyOperations = new EzySimpleUpdateOperations<>(realOperations);
		operations.apply(proxyOperations);
		UpdateOptions updateOptions = new UpdateOptions()
				.upsert(true)
				.multi(false);
		datastore.update(query, realOperations, updateOptions);
	}

	@Override
	public E findAndModifyById(I id, EzyApply<EzyUpdateOperations<E>> operations) {
		return findAndModifyByQuery(newQuery("_id", id), operations);
	}
	
	@Override
	public E findAndModifyById(I id, EzyApply<EzyUpdateOperations<E>> operations, EzyApply<EzyFindAndModifyOptions> options) {
		return findAndModifyByQuery(newQuery("_id", id), operations, options);
	}
	
	@Override
	public E findAndModifyByField(String field, Object value, EzyApply<EzyUpdateOperations<E>> operations) {
		return findAndModifyByQuery(newQuery(field, value), operations);
	}
	
	@Override
	public E findAndModifyByField(String field, Object value, EzyApply<EzyUpdateOperations<E>> operations,
			EzyApply<EzyFindAndModifyOptions> options) {
		return findAndModifyByQuery(newQuery(field, value), operations, options);
	}
	
	private E findAndModifyByQuery(Query<E> query, EzyApply<EzyUpdateOperations<E>> operations) {
		UpdateOperations<E> realOperations = datastore.createUpdateOperations(getEntityType());
		EzyUpdateOperations<E> proxyOperations = new EzySimpleUpdateOperations<>(realOperations);
		operations.apply(proxyOperations);
		return datastore.findAndModify(query, realOperations);
	}
	
	private E findAndModifyByQuery(Query<E> query, EzyApply<EzyUpdateOperations<E>> operations, EzyApply<EzyFindAndModifyOptions> options) {
		UpdateOperations<E> realOperations = datastore.createUpdateOperations(getEntityType());
		EzyUpdateOperations<E> proxyOperations = new EzySimpleUpdateOperations<>(realOperations);
		FindAndModifyOptions realOptions = new FindAndModifyOptions();
		EzyFindAndModifyOptions proxyOptions = new EzySimpleFindAndModifyOptions(realOptions);
		operations.apply(proxyOperations);
		options.apply(proxyOptions);
		return datastore.findAndModify(query, realOperations, realOptions);
	}
	
	@Override
	public void delete(I id) {
		datastore.delete(newQuery("_id", id), new DeleteOptions().copy());
	}

	@Override
	public int deleteByIds(Collection<I> ids) {
		WriteResult result = datastore.delete(newQuery().field("_id").in(ids), new DeleteOptions().copy());
		return result.getN();
	}
	
	@Override
	public int deleteAll() {
		WriteResult result = datastore.delete(newQuery());
		return result.getN();
	}
	
	protected Query<E> newQuery() {
		return datastore.createQuery(getEntityType());
	}
	
	protected Query<E> newQuery(String field, Object value) {
		return newQuery().field(field).equal(value);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Class<E> getEntityType() {
		try {
			Type genericSuperclass = getClass().getGenericSuperclass();
			Class[] genericArgs = EzyGenerics.getTwoGenericClassArguments(genericSuperclass);
			return genericArgs[1];
		}
		catch (Exception e) {
			throw new UnimplementedOperationException("class " + getClass().getName() + " hasn't implemented method 'getEntityType'", e);
		}
	}
}
