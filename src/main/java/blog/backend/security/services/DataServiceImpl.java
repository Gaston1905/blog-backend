package blog.backend.security.services;

import org.springframework.stereotype.Service;

import blog.backend.security.services.dto.ServerData;

@Service
public class DataServiceImpl implements DataService {
    
    @Override
    public ServerData getSecuredData(String source) {
        return new ServerData(source, "Some Data", System.currentTimeMillis());
    }
}
