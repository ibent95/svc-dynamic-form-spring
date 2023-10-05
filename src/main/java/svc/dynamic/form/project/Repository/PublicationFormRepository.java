package svc.dynamic.form.project.Repository;

import jakarta.persistence.Id;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import svc.dynamic.form.project.Entity.PublicationForm;

/**
 *
 * @author ibent95
 */
@EnableJpaRepositories
public interface PublicationFormRepository extends CrudRepository<PublicationForm, Id> {
	
}
