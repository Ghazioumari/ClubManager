package club.esprit.backend.services.libary;

import club.esprit.backend.entities.libary.Resource;
import club.esprit.backend.repository.libary.ResourceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ResourceImp implements IResourceService{
    private ResourceRepo resourceRepo;
    @Override
    public Resource addRessource(Resource resource) {
        return resourceRepo.save(resource);
    }

    @Override
    public List<Resource> getAll() {
        return resourceRepo.findAll();
    }
}
