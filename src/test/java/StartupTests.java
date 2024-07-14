

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.rafael.mapper.DozzerMapper;

@SpringBootTest
class StartupTests {

	@Test
	void contextLoads() {
		new DozzerMapper();
	}

}
