package svc.dynamic.form.project.Repository;

import jakarta.persistence.Id;
import org.springframework.data.repository.CrudRepository;

import svc.dynamic.form.project.Entity.PublicationStatus;

/**
 *
 * @author ibent95
 */
public interface PublicationStatusRepository extends CrudRepository<PublicationStatus, Id> {

    PublicationStatus findByPublicationStatusCode(String publicationStatusCode);

}
