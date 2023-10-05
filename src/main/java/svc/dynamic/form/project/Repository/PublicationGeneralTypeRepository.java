package svc.dynamic.form.project.Repository;

import jakarta.persistence.Id;
import org.springframework.data.repository.CrudRepository;
import svc.dynamic.form.project.Entity.PublicationGeneralType;

/**
 *
 * @author ibent95
 */
public interface PublicationGeneralTypeRepository extends CrudRepository<PublicationGeneralType, Id> {
	
}
