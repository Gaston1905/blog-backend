package blog.backend.security.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blog.backend.security.services.DataService;
import blog.backend.security.services.dto.ServerData;

@RestController
@RequestMapping("/services/public")
public class PublicRestController {

    private static final Logger LOG = LoggerFactory.getLogger(PublicRestController.class);

    private final DataService dataService;

    // @Autowired
    public PublicRestController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/data/all")
    public ResponseEntity<ServerData> getData() {
        LOG.info("getData: ");
        return ResponseEntity.ok().body(dataService.getSecuredData("Public"));
    }
    
}
