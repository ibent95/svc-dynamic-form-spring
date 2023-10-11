package svc.dynamic.form.project.Repository;

import jakarta.persistence.Id;
import org.springframework.data.repository.CrudRepository;

import svc.dynamic.form.project.Entity.PublicationType;

/**
 *
 * @author ibent95
 */
public interface PublicationTypeRepository extends CrudRepository<PublicationType, Id> {

    PublicationType findByUuid(String uuid);

    PublicationType findByPublicationTypeCode(String publicationTypeCode);

}
