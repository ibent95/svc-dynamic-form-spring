package svc.dynamic.form.project.Repository;

import jakarta.persistence.Id;
import org.springframework.data.repository.CrudRepository;
import svc.dynamic.form.project.Entity.Publication;

/**
 *
 * @author ibent95
 */
public interface PublicationRepository extends CrudRepository<Publication, Id> {
	
}
