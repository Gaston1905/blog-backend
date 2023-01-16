package blog.backend.security.services;

import java.security.Key;
import java.util.Optional;

import blog.backend.security.services.dto.UserId;

/**
 * KeyStore service responsible for user key management
 */

public interface KeyStoreService {

    
        /** 
         * Get private key of CA.
         * @return
         */
    
         Key getCertificationAuthorityKey();
    
         /**
          * Create key for given user and store it in cache)
          * @Param userId
          * @return
          */

        Key createUserKey(UserId userId);
      
            /**
             * Get user key from cache)
             * @Param userId
             * @return
            */

        Optional<Key> getUserKey(UserId userId);
        
                    /**
                     * Remove user key from cache).
                     * @param userId
                     * @return
                    */
        
        boolean removeUserKey(UserId userId);
}
