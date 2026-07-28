package io.github.tbondetti.test_resource_server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
		properties = {
				"env.auth-server.path=http://localhost:8080"
		}
)
class TestResourceServerApplicationTests {

	@Test
	void contextLoads() {
	}

}
