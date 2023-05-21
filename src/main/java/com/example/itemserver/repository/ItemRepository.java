package com.example.itemserver.repository;

import com.example.itemserver.entity.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, Integer> {
    Item findById(String id);
    @Query("{'itemName': ?0}")
    Item findByItemName(String ItemName);
    List<Item> findAllByStatus (int status);
    int countItemsByFolderId (String folderId);
    @Query("{'folderId': ?0}")
    List<Item> findAllByFolderId (String id);
    List<Item> findAllByOwnerId (String id);
}
