package com.tvd12.ezyfox.elasticsearch.testing;

import java.util.List;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import com.tvd12.ezyfox.elasticsearch.EzyEsCaller;
import com.tvd12.ezyfox.elasticsearch.EzyEsRestClientProxy;
import com.tvd12.ezyfox.elasticsearch.EzyEsSimpleCaller;
import com.tvd12.ezyfox.elasticsearch.action.EzyEsSimpleSearchAction;
import com.tvd12.ezyfox.elasticsearch.testing.data.Person;
import com.tvd12.ezyfox.elasticsearch.testing.data.TestPerson;

public class SearchTestPersonTest {

	public static void main(String[] args) {
		new SearchTestPersonTest().test();
	}
	
	public void test() {
		RestHighLevelClient highLevelClient = new RestHighLevelClient(
		        RestClient.builder(
		                new HttpHost("localhost", 9200, "http")));
		EzyEsCaller client = EzyEsSimpleCaller.builder()
				.scanIndexedClasses("com.tvd12.ezyfox.elasticsearch.testing.data")
				.clientProxy(new EzyEsRestClientProxy(highLevelClient))
				.build();
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("test");
		List<Person> persons = client.call(new EzyEsSimpleSearchAction()
				.searchRequest(searchRequest)
				.responseItemType(TestPerson.class));
		System.out.println(persons);
		try {
			highLevelClient.close();
		}
		catch(Exception e) {
		}
	}
	
}
