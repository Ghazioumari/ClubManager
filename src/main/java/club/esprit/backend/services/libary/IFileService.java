package club.esprit.backend.services.libary;

import club.esprit.backend.entities.libary.Files;

public interface IFileService {
    public Files addFile(Files files);

    public  Files getByFileName(String fileName);
}
