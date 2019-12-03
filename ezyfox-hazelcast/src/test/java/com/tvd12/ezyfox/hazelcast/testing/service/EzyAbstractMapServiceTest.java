package com.tvd12.ezyfox.hazelcast.testing.service;

import static org.mockito.Mockito.*;

import java.util.Map;
import java.util.Set;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.function.EzyExceptionApply;
import com.tvd12.ezyfox.function.EzyExceptionFunction;
import com.tvd12.ezyfox.function.EzyExceptionVoid;
import com.tvd12.ezyfox.function.EzySupplier;
import com.tvd12.ezyfox.hazelcast.factory.EzyMapTransactionFactory;
import com.tvd12.ezyfox.hazelcast.factory.EzySimpleMapTransactionFactory;
import com.tvd12.ezyfox.hazelcast.service.EzyAbstractMapService;
import com.tvd12.ezyfox.hazelcast.testing.HazelcastBaseTest;
import com.tvd12.ezyfox.hazelcast.transaction.EzyMapReturnTransaction;

public class EzyAbstractMapServiceTest extends HazelcastBaseTest {

	@Test
	public void test() throws Exception {
		ChickenMapService service = new ChickenMapService();
		service.setHazelcastInstance(HZ_INSTANCE);
		service.mapLockUpdate("one", () -> {
			System.out.println("hello world");
		});
		try {
			service.mapLockUpdate("one", () -> {
				throw new IllegalStateException("maintain");
			});
		} catch (Exception e) {
			assert e instanceof IllegalStateException;
		}
		assert service.mapLockUpdateAndGet("one", () -> 1).equals(1);
		try {
			service.mapLockUpdateAndGet("one", () -> {
				throw new IllegalStateException("maintain");
			});
		} catch (Exception e) {
			assert e instanceof IllegalStateException;
		}

		service.mapLockUpdateWithException("one", () -> {
			System.out.println("hello world");
		});
		try {
			service.mapLockUpdateWithException("one", () -> {
				throw new IllegalStateException("maintain");
			});
		} catch (Exception e) {
			assert e instanceof IllegalStateException;
		}
		assert service.mapLockUpdateAndGetWithException("one", () -> 1).equals(1);
		try {
			service.mapLockUpdateAndGetWithException("one", () -> {
				throw new IllegalStateException("maintain");
			});
		} catch (Exception e) {
			assert e instanceof IllegalStateException;
		}

		// transaction
		EzyMapTransactionFactory mapTransactionFactory = new EzySimpleMapTransactionFactory(HZ_INSTANCE);
		service.setMapTransactionFactory(mapTransactionFactory);
		assert service.tranGet("one") == null;
		service.tranPut(new Chicken("one", 123));
		assert service.tranGet("one") != null;
		service.tranUpdate("one", c -> {
		});
		service.tranPut(new Chicken("two", 456));
		assert service.tranGet(Sets.newHashSet("one", "two")).size() == 2;
		service.tranUpdateAndGet("one", c -> c.getName()).equals("one");
		assert service.tranUpdateAndGet("no key", c -> c.getName()) == null;
		
		ChickenMap2Service service2 = new ChickenMap2Service();
		service2.setHazelcastInstance(HZ_INSTANCE);
		service2.setMapTransactionFactory(mapTransactionFactory);
		try {
			service2.tranGet("one", new Chicken("one", 1));
		}
		catch (Exception e) {
			assert e instanceof IllegalStateException;
		}
		try {
			service2.tranGet(Sets.newHashSet("one"));
		}
		catch (Exception e) {
			assert e instanceof IllegalStateException;
		}

		Thread.sleep(1000);
	}

	public static class ChickenMapService extends EzyAbstractMapService<String, Chicken> {

		@Override
		protected String getMapName() {
			return "ChickenMapService_Chicken";
		}

		@Override
		public void mapLockUpdate(String key, EzyExceptionVoid applier) {
			super.mapLockUpdate(key, applier);
		}

		@Override
		public <T> T mapLockUpdateAndGet(String key, EzySupplier<T> supplier) {
			return super.mapLockUpdateAndGet(key, supplier);
		}

		@Override
		public void mapLockUpdateWithException(String key, EzyExceptionVoid applier) throws Exception {
			super.mapLockUpdateWithException(key, applier);
		}

		@Override
		public <T> T mapLockUpdateAndGetWithException(String key, EzySupplier<T> supplier) throws Exception {
			return super.mapLockUpdateAndGetWithException(key, supplier);
		}

		public void tranPut(Chicken chicken) throws Exception {
			transactionUpdate(chicken.getName(), c -> {
			}, chicken);
		}

		public Chicken tranGet(String key) throws Exception {
			return transactionGet(key);
		}

		public Chicken tranGet(String key, Chicken defaultValue) throws Exception {
			return transactionGet(key, defaultValue);
		}

		public Map<String, Chicken> tranGet(Set<String> keys) throws Exception {
			return transactionGet(keys);
		}

		public void tranUpdate(String key, EzyExceptionApply<Chicken> applier) throws Exception {
			transactionUpdate(key, applier);
		}

		public <R> R tranUpdateAndGet(String key, EzyExceptionFunction<Chicken, R> applier) throws Exception {
			return transactionUpdateAndGet(key, applier);
		}
	}

	public static class ChickenMap2Service extends EzyAbstractMapService<String, Chicken> {

		@Override
		protected String getMapName() {
			return "ChickenMapService_Chicken_2";
		}
		
		public Chicken tranGet(String key, Chicken defaultValue) throws Exception {
			return transactionGet(key, defaultValue);
		}
		
		public Map<String, Chicken> tranGet(Set<String> keys) throws Exception {
			return transactionGet(keys);
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected <R> EzyMapReturnTransaction<String, Chicken, R> newReturnTransaction() {
			EzyMapReturnTransaction transaction = mock(EzyMapReturnTransaction.class);
			try {
				when(transaction.apply(any())).thenThrow(new IllegalStateException("maintain"));
			}
			catch (Exception e) {
				throw new IllegalStateException(e);
			}
			return transaction;
		}
	}

}
