package com.tvd12.ezyfox.mongodb.testing.bean;

import java.util.Collection;
import java.util.List;

import com.tvd12.ezyfox.database.query.EzyFindAndModifyOptions;
import com.tvd12.ezyfox.database.query.EzyUpdateOperations;
import com.tvd12.ezyfox.function.EzyApply;
import com.tvd12.ezyfox.mongodb.EzyMongoRepository;

public class ExEzyMongoRepository implements EzyMongoRepository<Integer, Person> {

	@Override
	public long count() {
		return 0;
	}

	@Override
	public void save(Person entity) {
	}

	@Override
	public void save(Iterable<Person> entities) {
	}

	@Override
	public Person findById(Integer id) {
		return null;
	}

	@Override
	public List<Person> findListByIds(Collection<Integer> ids) {
		return null;
	}

	@Override
	public Person findByField(String field, Object value) {
		return null;
	}

	@Override
	public List<Person> findListByField(String field, Object value) {
		return null;
	}

	@Override
	public List<Person> findListByField(String field, Object value, int skip, int limit) {
		return null;
	}

	@Override
	public List<Person> findAll() {
		return null;
	}

	@Override
	public List<Person> findAll(int skip, int limit) {
		return null;
	}

	@Override
	public void updateOneById(Integer id, Person entity) {
	}

	@Override
	public void updateOneById(Integer id, Person entity, boolean upsert) {
	}

	@Override
	public void updateOneById(Integer id, EzyApply<EzyUpdateOperations<Person>> operations) {
	}

	@Override
	public void updateOneById(Integer id, EzyApply<EzyUpdateOperations<Person>> operations, boolean upsert) {
	}

	@Override
	public void updateOneByField(String field, Object value, Person entity) {
	}

	@Override
	public void updateOneByField(String field, Object value, Person entity, boolean upsert) {
	}

	@Override
	public void updateOneByField(String field, Object value, EzyApply<EzyUpdateOperations<Person>> operations) {
	}

	@Override
	public void updateOneByField(String field, Object value, EzyApply<EzyUpdateOperations<Person>> operations,
	        boolean upsert) {
	}

	@Override
	public void updateManyByField(String field, Object value, EzyApply<EzyUpdateOperations<Person>> operations) {
	}

	@Override
	public int deleteAll() {
		return 0;
	}

	@Override
	public void delete(Integer id) {
	}

	@Override
	public int deleteByIds(Collection<Integer> ids) {
		return 0;
	}

	@Override
	public Person findAndModifyById(Integer id, EzyApply<EzyUpdateOperations<Person>> operations) {
		return null;
	}

	@Override
	public Person findAndModifyById(Integer id, EzyApply<EzyUpdateOperations<Person>> operations,
	        EzyApply<EzyFindAndModifyOptions> options) {
		return null;
	}

	@Override
	public Person findAndModifyByField(String field, Object value, EzyApply<EzyUpdateOperations<Person>> operations) {
		return null;
	}

	@Override
	public Person findAndModifyByField(String field, Object value, EzyApply<EzyUpdateOperations<Person>> operations,
	        EzyApply<EzyFindAndModifyOptions> options) {
		return null;
	}

	protected Class<Person> getEntityType() {
		return Person.class;
	}
}
