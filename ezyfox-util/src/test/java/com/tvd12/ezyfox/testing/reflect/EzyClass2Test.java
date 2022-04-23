package com.tvd12.ezyfox.testing.reflect;

import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.reflect.EzyClass;
import lombok.Getter;
import lombok.Setter;
import org.testng.annotations.Test;

public class EzyClass2Test {

    @Test
    public void test() {
        EzyClass clazz = new EzyClass(GetAccountCreateIfNotExistRequest.class);
        System.out.println(clazz.getGetterMethods());
    }

    @Setter
    @Getter
    public static class GetAccountCreateIfNotExistRequest {

        protected String id;
        protected boolean createIfNotExist;
        protected long bankAccountId;
        protected long initValue;

        public GetAccountCreateIfNotExistRequest() {
        }

        public GetAccountCreateIfNotExistRequest(String id,
                                                 boolean createIfNotExist,
                                                 long bankAccountId,
                                                 long initValue,
                                                 String descript) {
            this.id = id;
            this.createIfNotExist = createIfNotExist;
            this.bankAccountId = bankAccountId;
            this.initValue = initValue;
        }

        @Override
        public String toString() {
            return new StringBuilder()
                .append("{")
                .append("\"id\":").append(id).append(",")
                .append("\"createIfNotExist\":").append(createIfNotExist).append(",")
                .append("\"bankAccountId\":").append(bankAccountId).append(",")
                .append("\"initValue\":").append(initValue).append(",")
                .append("\"descript\":").append(EzyStrings.quote(""))
                .append("}")
                .toString();
        }
    }
}
