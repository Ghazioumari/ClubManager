package club.esprit.backend.services.libary;

import club.esprit.backend.entities.libary.Files;
import club.esprit.backend.repository.libary.FilesRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FileService implements IFileService {
    private FilesRepo filesRepo;
    @Override
    public Files addFile(Files files) {
        Files files1 = filesRepo.findByName(files.getName());
        if (files1 == null){
            return filesRepo.save(files);
        }
        return files1;
    }

    @Override
    public Files getByFileName(String fileName) {
        return filesRepo.findByName(fileName);
    }
}
