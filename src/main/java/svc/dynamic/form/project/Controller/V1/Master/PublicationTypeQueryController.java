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
import svc.dynamic.form.project.Component.ResponseObjectComponent;
import svc.dynamic.form.project.Entity.PublicationType;
import svc.dynamic.form.project.Repository.PublicationTypeRepository;

@RestController
@RequestMapping(value = "public/api/v1/master")
public class PublicationTypeQueryController {

    @Autowired
    private ResponseObjectComponent responseObject;
    @Autowired
    private ResponseListComponent responseList;
    @Autowired
    private ResponseHashMapComponent responseHashMap;
    @Autowired
    private ResponseIterableComponent responseIterable;

    @Autowired
    private PublicationTypeRepository publicationTypeRepo;
    
    public PublicationTypeQueryController() { }

    @RequestMapping(value = "publication-types", method = RequestMethod.GET)
    public ResponseEntity<ResponseListComponent> index() {

        try {
            List<PublicationType> publicationTypes = (List<PublicationType>) this.publicationTypeRepo.findAll();

            this.responseList.data = publicationTypes;
            this.responseList.status = 200;
            this.responseList.info = "success";
            this.responseList.message = "Success on get publication type master data!";
        } catch (Exception e) {
            this.responseList.status = 400;
            this.responseList.info = "error";
            this.responseList.message = "Error on get publication type master data!";
        }

        return ResponseEntity.status(this.responseList.status).body(this.responseList);
    }

}
