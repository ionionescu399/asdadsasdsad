package com.in28minutes.springboot;

import fr.pilato.elasticsearch.containers.ElasticsearchContainer;
import fr.pilato.elasticsearch.containers.ElasticsearchResource;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.Wait;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServicesApplicationTests {

	@ClassRule
	public static GenericContainer redis =
			new GenericContainer("docker.elastic.co/elasticsearch/elasticsearch:6.0.0")
					.withEnv("http.host","0.0.0.0")
					.withEnv("transport.host","127.0.0.1")
					.withEnv("network.host", "0.0.0.0")
					.withEnv("network.bind_host","0.0.0.0")
					.withEnv("xpack.security.enabled","false")
					.withExposedPorts(9200,9300);


	@Test
	public void contextLoads() throws IOException {
		redis.setPortBindings(Arrays.asList("9200:9200", "9300:9300"));
		redis.start();
		redis.getPortBindings();
		redis.getExposedPorts();
		System.out.println(new RestTemplate().getForEntity("http://"+ redis.getContainerIpAddress() + ":9200" , String.class).getBody());
		redis.stop();
	}

}
