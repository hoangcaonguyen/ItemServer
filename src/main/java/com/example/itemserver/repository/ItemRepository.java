package com.example.itemserver.repository;

import com.example.itemserver.entity.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, Integer> {
    Item findById(int id);
    @Query("{'itemName': ?0}")
    Item findByItemName(String ItemName);
    List<Item> findAllByStatus (int status);
    int countItemsByFolderId (int folderId);
    @Query("{'folderId': ?0}")
    List<Item> findAllByFolderId (int id);
    List<Item> findAllByOwnerId (int id);
}
