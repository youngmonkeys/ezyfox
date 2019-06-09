package com.tvd12.ezyfox.testing.entity;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.testing.CommonBaseTest;

public class EzyObject3Test extends CommonBaseTest {

	@Test
	public void test1() {
		EzyObject object = newObjectBuilder()
				.append("1", new Boolean[][] {{true, false}, {false, true}})
				.append("2", new Byte[][] {{1,2},{3,4}})
				.append("3", new Character[][] {{'a', 'b'}, {'c', 'd'}})
				.append("4", new Double[][] {{1D,2D},{3D,4D}})
				.append("5", new Float[][] {{1F,2F}, {3F,4F}})
				.append("6", new Integer[][] {{1,2}, {3,4}})
				.append("7", new Long[][] {{1L,2L}, {3L,4L}})
				.append("8", new Short[][] {{1,2}, {3,4}})
				.append("9", new String[][] {{"1","2"}, {"3","4"}})
				.build();
		assertEquals((Object)object.get("1", Boolean[][].class), new Boolean[][] {{true, false}, {false, true}});
		assertEquals((Object)object.get("2", Byte[][].class), new Byte[][] {{1,2},{3,4}});
		assertEquals((Object)object.get("3", Character[][].class), new Character[][] {{'a', 'b'}, {'c', 'd'}});
		assertEquals((Object)object.get("4", Double[][].class), new Double[][] {{1D,2D},{3D,4D}});
		assertEquals((Object)object.get("5", Float[][].class), new Float[][] {{1F,2F}, {3F,4F}});
		assertEquals((Object)object.get("6", Integer[][].class), new Integer[][] {{1,2}, {3,4}});
		assertEquals((Object)object.get("7", Long[][].class), new Long[][] {{1L,2L}, {3L,4L}});
		assertEquals((Object)object.get("8", Short[][].class), new Short[][] {{1,2}, {3,4}});
		assertEquals((Object)object.get("9", String[][].class), new String[][] {{"1","2"}, {"3","4"}});
	}
}
