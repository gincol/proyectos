package cat.vn.owasp.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;

@Configuration
@EnableJpaRepositories(basePackages = "cat.vn.owasp.repository")
@EntityScan(basePackages = { "cat.vn.owasp.model" })
@EnableTransactionManagement
@Profile("loc")
public class LocConfig {

	@Bean
	@Primary
	public DataSource inMemoryDS() throws Exception {
		DataSource embeddedPostgresDS = EmbeddedPostgres.builder().setPort(5432).start().getPostgresDatabase();

		return embeddedPostgresDS;
	}

}