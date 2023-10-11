package svc.dynamic.form.project.Controller.V1.Master;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import svc.dynamic.form.project.Component.ResponseHashMapComponent;
import svc.dynamic.form.project.Component.ResponseIterableComponent;
import svc.dynamic.form.project.Component.ResponseListComponent;
import svc.dynamic.form.project.Component.ResponseMapComponent;
import svc.dynamic.form.project.Component.ResponseObjectComponent;
import svc.dynamic.form.project.Entity.PublicationGeneralType;
import svc.dynamic.form.project.Entity.PublicationStatus;
import svc.dynamic.form.project.Entity.PublicationType;
import svc.dynamic.form.project.Repository.PublicationGeneralTypeRepository;
import svc.dynamic.form.project.Repository.PublicationStatusRepository;
import svc.dynamic.form.project.Repository.PublicationTypeRepository;

@RestController
@RequestMapping(value = "public/api/v1/master")
public class PublicationStatusQueryController {

    @Autowired
    private ResponseObjectComponent responseObject;
    @Autowired
    private ResponseListComponent responseList;
    @Autowired
    private ResponseHashMapComponent responseHashMap;
    @Autowired
    private ResponseIterableComponent responseIterable;

    @Autowired
    private PublicationStatusRepository publicationStatusRepo;
    
    public PublicationStatusQueryController(
        // ResponseObjectComponent responseObject,
        // ResponseListComponent responseList,
        // ResponseHashMapComponent responseHashMap,
        // ResponseIterableComponent responseIterable
        ) {
        // this.responseObject = responseObject;
        // this.responseList = responseList;
        // this.responseHashMap = responseHashMap;
        // this.responseIterable = responseIterable;

        // this.responseObject.info = this.responseList.info = this.responseHashMap.info = this.responseIterable.info = "error";
        // this.responseObject.status = this.responseList.status = this.responseHashMap.status  = this.responseIterable.status = 400;
        // this.responseObject.message = this.responseList.message = this.responseHashMap.message  = this.responseIterable.message = "No prosses is ececute!";
    }

    @RequestMapping(value = "publication-statuses", method = RequestMethod.GET)
    public ResponseEntity<ResponseListComponent> index() {

        try {
            List<PublicationStatus> publicationStatuses = (List<PublicationStatus>) this.publicationStatusRepo.findAll();

            this.responseList.data = publicationStatuses;
            this.responseList.status = 200;
            this.responseList.info = "success";
            this.responseList.message = "Success on get publication status master data!";
        } catch (Exception e) {
            this.responseList.status = 400;
            this.responseList.info = "error";
            this.responseList.message = "Error on get publication status master data!";
        }

        return ResponseEntity.status(this.responseList.status).body(this.responseList);
    }

}
