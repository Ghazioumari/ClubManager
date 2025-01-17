package club.esprit.backend.services.libary;

import club.esprit.backend.entities.libary.Resource;
import club.esprit.backend.repository.libary.ResourceRepo;
import club.esprit.backend.services.libary.ResourceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResourceImpTest {

    @Mock
    private ResourceRepo resourceRepo;

    @InjectMocks
    private ResourceImp resourceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddResource() {
        Resource resource = new Resource();
        when(resourceRepo.save(resource)).thenReturn(resource);

        Resource result = resourceService.addRessource(resource);

        assertEquals(resource, result);
        verify(resourceRepo, times(1)).save(resource);
    }

    @Test
    void testGetAll() {
        List<Resource> resources = List.of(new Resource());
        when(resourceRepo.findAll()).thenReturn(resources);

        List<Resource> result = resourceService.getAll();

        assertEquals(resources, result);
        verify(resourceRepo, times(1)).findAll();
    }
}
