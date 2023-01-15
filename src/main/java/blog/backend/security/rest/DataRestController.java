package blog.backend.security.rest;

import blog.backend.security.services.DataService;
import blog.backend.security.services.dto.ServerData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("services/data")
public class DataRestController {
    
    private static final Logger LOG = LoggerFactory.getLogger(DataRestController.class);

    private final DataService dataService;

    @Autowired
    public DataRestController (DataService DataService) {
        this.dataService = dataService;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/users/all")
    public ResponseEntity<ServerData> getDataForUsers(Authentication authentication) {
        LOG.info("getData: authentication={}", authentication.getName());
        authentication.getAuthorities().forEach(a ->
            LOG.info(" authority={}", a.getAuthority())
        );
        return ResponseEntity.ok().body(dataService.getSecuredData("secured for USER/ADMIN" + authentication.getName()));
        
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admins/all")
    public ResponseEntity<ServerData> getDataFormAdmins(Authentication authentication) {
        LOG.info("getData = authentication={}", authentication.getName());
        authentication.getAuthorities().forEach(a ->
        LOG.info(" authority={}", a.getAuthority())
        );
        return ResponseEntity.ok().body(dataService.getSecuredData("Secured for ADMIN " + authentication.getName()));
    }
}
