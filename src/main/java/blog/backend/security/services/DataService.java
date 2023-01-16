package blog.backend.security.services;

import blog.backend.security.services.dto.ServerData;

/**
 * Service providing protected data
 */

public interface DataService {
    
    /**
     * Get data for source.
     * @Param source source identifier or name.
     * @return
     */

     ServerData getSecuredData(String source);
}
