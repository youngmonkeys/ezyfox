package com.tvd12.ezyfox.bean.testing.autobindfunction;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.testing.autobindfunction1.World;
import com.tvd12.ezyfox.bean.testing.autobindfunction2.Hello;
import com.tvd12.ezyfox.bean.testing.autobindfunction2.HelloImpl;
import com.tvd12.ezyfox.bean.testing.autobindfunction3.FooImpl;
import com.tvd12.ezyfox.bean.testing.autobindfunction3.GreetImpl;
import com.tvd12.ezyfox.bean.testing.autobindfunction3.XImpl;
import com.tvd12.ezyfox.bean.testing.autobindfunction3.YImpl;

public class MainTest {

	@Test
	public void test() {
		Hello hello = new HelloImpl();
		EzyBeanContext ctx = EzyBeanContext.builder()
				.addSingleton("hello1", hello)
				.addSingleton("hello", hello)
				.scan("com.tvd12.ezyfox.bean.testing.autobindfunction")
				.scan("com.tvd12.ezyfox.bean.testing.autobindfunction2")
				.build();
		World world = (World)ctx.getBean(World.class);
		assert world.getHello() != null;
		GreetImpl greet = (GreetImpl)ctx.getBean(GreetImpl.class);
		assert greet != null;
		assert greet.getFoo() != null;
		assert ((FooImpl)greet.getFoo()).getBar() != null;
		assert greet.getX() != null;
		assert ((XImpl)greet.getX()).getY() != null;
		assert ((YImpl)((XImpl)greet.getX()).getY()).getZ() != null;
	}
	
}
