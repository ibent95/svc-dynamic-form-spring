package svc.dynamic.form.project.Controller.V1;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import svc.dynamic.form.project.Component.ResponseHashMapComponent;
import svc.dynamic.form.project.Component.ResponseIterableComponent;
import svc.dynamic.form.project.Component.ResponseListComponent;
import svc.dynamic.form.project.Component.ResponseObjectComponent;
import svc.dynamic.form.project.Entity.Publication;
import svc.dynamic.form.project.Repository.PublicationRepository;
import svc.dynamic.form.project.Service.CommonService;

@RestController
@RequestMapping(value = "public/api/v1")
public class PublicationQueryController {

    @Autowired
    private ResponseObjectComponent responseObject;
    @Autowired
    private ResponseListComponent responseList;
    @Autowired
    private ResponseHashMapComponent responseHashMap;
    @Autowired
    private ResponseIterableComponent responseIterable;

    @Autowired
    private CommonService commonSvc;

    @Autowired
    private PublicationRepository publicationRepo;

    public PublicationQueryController() { }

    @RequestMapping(value = "publication", method = RequestMethod.GET)
    public ResponseEntity<ResponseHashMapComponent> main() {

		this.responseHashMap.status = 200;
		this.responseHashMap.info = "success";
		this.responseHashMap.message = "Welcome to Dynamic Forms service in Spring Boot 3.";
		this.responseHashMap.data = new HashMap<String, Object>();
		this.responseHashMap.data.put("messages", "This is Publication API service.");
		this.responseHashMap.data.put("date", new Date());

		return ResponseEntity.status(this.responseHashMap.status).body(this.responseHashMap);
    }

    @RequestMapping(value = "publications", method = RequestMethod.GET)
    public ResponseEntity<ResponseListComponent> index(@RequestParam Map request) {

        try {
            Map paginator = this.commonSvc.setGetPaginator(request);

            Long publicationsTotalCount = this.publicationRepo.count();
            List<Publication> publicationsData =
                (List<Publication>) this.publicationRepo.findAllWithLimitOffset(
                    (int) paginator.get("limit"),
                    (int) paginator.get("offset")
                );

            this.responseList.count = publicationsTotalCount;
            this.responseList.data = publicationsData;
            this.responseList.status = 200;
            this.responseList.info = "success";
            this.responseList.message = "Success on get publications data!";
        } catch (Exception e) {
            this.responseList.status = 400;
            this.responseList.info = "error";
            this.responseList.message = "Error on get publications data!";
        }

        return ResponseEntity.status(this.responseList.status).body(this.responseList);
    }

    @RequestMapping(value = "publications/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObjectComponent> getByUuid(@PathVariable String uuid) {

        try {
            Publication publicationData = this.publicationRepo.findByUuid(uuid);

            this.responseObject.data = publicationData;
            this.responseObject.status = 200;
            this.responseObject.info = "success";
            this.responseObject.message = "Success on get publication data by UUID: " + uuid + ".";
        } catch (Exception e) {
            this.responseObject.status = 400;
            this.responseObject.info = "error";
            this.responseObject.message = "Error on get publications data by UUID: " + uuid + ".";
        }

        return ResponseEntity.status(this.responseObject.status).body(this.responseObject);
    }

}
