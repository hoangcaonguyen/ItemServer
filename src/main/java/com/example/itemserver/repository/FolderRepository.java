package com.example.itemserver.repository;

import com.example.itemserver.entity.Folder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FolderRepository extends MongoRepository<Folder, Integer> {
    @Query("{'folderName': ?0}")
    Folder findByFolderName(String folderName);
    Folder findById(int id);
    List<Folder> findAllByStatus (int status);
    List<Folder> findAllByOwnerId (int id);
}
