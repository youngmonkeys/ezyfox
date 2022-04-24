package com.tvd12.ezyfox.database.testing;

import com.tvd12.ezyfox.database.testing.repo.PersonRepo;
import com.tvd12.ezyfox.database.testing.repo.UserRepo;
import com.tvd12.ezyfox.database.util.EzyRepositoryAnnotations;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyRepositoryAnnotationsTest extends BaseTest {

    @Test
    public void test() {
        assert EzyRepositoryAnnotations.getRepoName(new PersonRepo()).equals("personRepo");
        assert EzyRepositoryAnnotations.getRepoName(new UserRepo()).equals("user");
    }

    @Override
    public Class<?> getTestClass() {
        return EzyRepositoryAnnotations.class;
    }
}
