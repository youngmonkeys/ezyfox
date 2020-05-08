package com.tvd12.ezyfox.testing.tool;

import com.tvd12.ezyfox.testing.tool.entity.UserEntity;
import com.tvd12.ezyfox.tool.EzySQLTableCreator;
import com.tvd12.ezyfox.tool.data.EzyCaseType;

public class EzySQLTableCreatorTest {

	public static void main(String[] args) {
		EzySQLTableCreator creator = new EzySQLTableCreator(UserEntity.class, EzyCaseType.NORMAL);
		System.out.println(creator.createScript());
	}
	
}
