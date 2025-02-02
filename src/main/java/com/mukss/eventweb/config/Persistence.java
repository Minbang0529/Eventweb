package com.mukss.eventweb.config;
//import java.nio.file.Path;
import java.nio.file.Paths;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Profile("default")
public class Persistence {

	private final static Logger log = LoggerFactory.getLogger(Persistence.class);

	// Persistence objects.
	private final static String PACKAGES = "com.mukss.eventweb.entities";

	// Connection properties.
//	private final static Path DB_PATH = Paths.get(System.getProperty("user.dir"), "db", "manchester-inside-dev");
//	private final static String DB_OPTS = "DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";

	// Hibernate properties.
	private final static boolean H2_SHOW_SQL = true;
//	private final static String H2_USERNAME = "h2";
//	private final static String H2_PASSWORD = "spring";

	@Bean
	public DataSource dataSource() {
		String dbUrl = "jdbc:postgresql://dpg-ck4scsd8ggls739be9m0-a:5432/mukss";


		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl(dbUrl);
		dataSource.setUsername("mukss_user");
		dataSource.setPassword("Xlcc3apYhdkmpLOWPOOt8LSTOhC0zlAQ");

		log.info("Database URL set: " + dbUrl);

		return dataSource;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setDataSource(dataSource());
		bean.setJpaVendorAdapter(jpaVendorAdapter());
		bean.setPackagesToScan(PACKAGES);
		bean.afterPropertiesSet();

		return bean.getObject();
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.POSTGRESQL);
		adapter.setShowSql(H2_SHOW_SQL);
		adapter.setGenerateDdl(true);

		return adapter;
	}

	@Bean
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}
}