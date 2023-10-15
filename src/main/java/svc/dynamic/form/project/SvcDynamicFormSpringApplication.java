package svc.dynamic.form.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import svc.dynamic.form.project.Configuration.AppProperties;
import svc.dynamic.form.project.Configuration.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProperties.class,
	AppProperties.class
})
public class SvcDynamicFormSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(SvcDynamicFormSpringApplication.class, args);
	}

}
