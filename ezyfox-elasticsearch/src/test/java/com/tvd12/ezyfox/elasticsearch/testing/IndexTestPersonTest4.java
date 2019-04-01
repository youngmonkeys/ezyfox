package com.tvd12.ezyfox.elasticsearch.testing;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import com.tvd12.ezyfox.elasticsearch.EzyEsCaller;
import com.tvd12.ezyfox.elasticsearch.EzyEsRestClientProxy;
import com.tvd12.ezyfox.elasticsearch.EzyEsSimpleCaller;
import com.tvd12.ezyfox.elasticsearch.action.EzyEsAction;
import com.tvd12.ezyfox.elasticsearch.action.EzyEsSimpleIndexAction;
import com.tvd12.ezyfox.elasticsearch.callback.EzyEsActionCallback;
import com.tvd12.ezyfox.elasticsearch.testing.data.TestPerson2;

public class IndexTestPersonTest4 {

	public static void main(String[] args) throws Exception {
		new IndexTestPersonTest4().test();
	}
	
	public void test() throws Exception {
		RestHighLevelClient highLevelClient = new RestHighLevelClient(
		        RestClient.builder(
		                new HttpHost("localhost", 9200, "http")));
		EzyEsCaller client = EzyEsSimpleCaller.builder()
				.scanIndexedClasses("com.tvd12.ezyfox.elasticsearch.testing.data")
				.clientProxy(new EzyEsRestClientProxy(highLevelClient))
				.build();
		client.start();
		client.async(new EzyEsSimpleIndexAction()
				.object(new TestPerson2(
						"itprono5@gmail.com",
						new TestPerson2.Name("Chào Thế Giới", "Hello Planet"),
						new String[] {"01234566", "78966"}, 
						28)),
				new EzyEsActionCallback<BulkResponse>() {
					@Override
					public void onSuccess(EzyEsAction action, BulkResponse response) {
						System.out.println("response: " + response.iterator().next().getType());
					}
				});
//		
		try {
//			highLevelClient.close();
		}
		catch(Exception e) {
		}
	}
	
}
