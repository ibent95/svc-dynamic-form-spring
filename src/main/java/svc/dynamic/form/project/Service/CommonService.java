package svc.dynamic.form.project.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import svc.dynamic.form.project.Component.ResponseHashMapComponent;
import svc.dynamic.form.project.Component.ResponseIterableComponent;
import svc.dynamic.form.project.Component.ResponseListComponent;
import svc.dynamic.form.project.Component.ResponseObjectComponent;

/**
 *
 * @author ibent95
 */
@Service
public class CommonService {

	@Autowired
    private ResponseObjectComponent responseObject;
    @Autowired
    private ResponseListComponent responseList;
    @Autowired
    private ResponseHashMapComponent responseHashMap;
    @Autowired
    private ResponseIterableComponent responseIterable;

	Map<String, Integer> paginator;

	public CommonService() {
		this.paginator = new HashMap();
		this.paginator.put("limit", 0);
		this.paginator.put("offset", 0);
		this.paginator.put("page_index", 0);
	}

	public Map setGetPaginator(@RequestParam Map<String, String> request) {

		Integer limit = Integer.parseInt(request.get("limit"));
		Integer offset = Integer.parseInt(request.get("offset"));
		Integer pageIndex = (request.containsKey("page_index"))
			? Integer.parseInt(request.get("page_index"))
			: null;

		if (pageIndex != null) {
			offset = limit * pageIndex; // Can also (pageNumber - 1)
		}

		this.paginator.put("limit", limit);
		this.paginator.put("offset", offset);
		this.paginator.put("page_index", pageIndex);

		return this.paginator;
	}

	public Long createUUIDShort() {
		return new Random().nextLong(Long.MAX_VALUE);
	}

	public String createUUID() {
		return UUID.randomUUID().toString();
	}

}
