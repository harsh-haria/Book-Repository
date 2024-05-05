package com.harshharia.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookRepositoryApplication {

//	public final DataSource dataSource;
//
//	public DatabaseApplication(final DataSource dataSource) {
//		this.dataSource = dataSource;
//		System.out.println("Datasource: "+this.dataSource.toString());
//		final JdbcTemplate restTemplate = new JdbcTemplate(this.dataSource);
//		restTemplate.execute("SHOW databases");
//	}

	public static void main(String[] args) {
		SpringApplication.run(BookRepositoryApplication.class, args);
	}


}
