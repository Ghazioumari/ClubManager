package club.esprit.backend.services.libary;

import club.esprit.backend.entities.libary.Resource;

import java.util.List;

public interface IResourceService {
    public Resource addRessource(Resource resource);

    public List<Resource> getAll();
}
