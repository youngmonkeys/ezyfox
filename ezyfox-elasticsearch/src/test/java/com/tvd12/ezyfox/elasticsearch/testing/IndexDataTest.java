package com.tvd12.ezyfox.elasticsearch.testing;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import com.tvd12.ezyfox.elasticsearch.EzyEsCaller;
import com.tvd12.ezyfox.elasticsearch.EzyEsRestClientProxy;
import com.tvd12.ezyfox.elasticsearch.EzyEsSimpleCaller;
import com.tvd12.ezyfox.elasticsearch.action.EzyEsActionTypes;
import com.tvd12.ezyfox.elasticsearch.action.EzyEsSimpleIndexAction;
import com.tvd12.ezyfox.elasticsearch.handler.EzyEsIndexActionHandler;
import com.tvd12.ezyfox.elasticsearch.testing.data.Person;

public class IndexDataTest {

	public static void main(String[] args) {
		new IndexDataTest().test();
	}
	
	public void test() {
		RestHighLevelClient highLevelClient = new RestHighLevelClient(
		        RestClient.builder(
		                new HttpHost("localhost", 9200, "http")));
		EzyEsCaller client = EzyEsSimpleCaller.builder()
				.scanIndexedClasses("com.tvd12.ezyfox.elasticsearch.testing.data")
				.clientProxy(new EzyEsRestClientProxy(highLevelClient))
				.addActionHandler(EzyEsActionTypes.INDEX, new EzyEsIndexActionHandler())
				.build();
		client.call(new EzyEsSimpleIndexAction()
				.object(new Person("dungtv", "itprono3@gmail.com", "0123456", 27)));
		try {
			highLevelClient.close();
		}
		catch(Exception e) {
		}
	}
	
}
