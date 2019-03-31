package com.tvd12.ezyfox.elasticsearch.testing;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import com.tvd12.ezyfox.elasticsearch.EzyEsCaller;
import com.tvd12.ezyfox.elasticsearch.EzyEsRestClientProxy;
import com.tvd12.ezyfox.elasticsearch.EzyEsSimpleCaller;
import com.tvd12.ezyfox.elasticsearch.action.EzyEsSimpleIndexAction;
import com.tvd12.ezyfox.elasticsearch.testing.data.TestPerson;

public class IndexTestPersonTest {

	public static void main(String[] args) {
		new IndexTestPersonTest().test();
	}
	
	public void test() {
		RestHighLevelClient highLevelClient = new RestHighLevelClient(
		        RestClient.builder(
		                new HttpHost("localhost", 9200, "http")));
		EzyEsCaller client = EzyEsSimpleCaller.builder()
				.scanIndexedClasses("com.tvd12.ezyfox.elasticsearch.testing.data")
				.clientProxy(new EzyEsRestClientProxy(highLevelClient))
				.build();
		BulkResponse response = client.call(new EzyEsSimpleIndexAction()
				.object(new TestPerson(
						"itprono3@gmail.com",
						new TestPerson.Name("Chào Thế Giới", "Hello Earth"),
						"0123456", 
						27)));
		System.out.println("response: " + response.iterator().next().getType());
		try {
			highLevelClient.close();
		}
		catch(Exception e) {
		}
	}
	
}
