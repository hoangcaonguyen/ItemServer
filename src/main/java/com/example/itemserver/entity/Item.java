package com.example.itemserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    private int id;
    @Field(name = "itemName")
    @NonNull
    private String itemName;
    @Field(name = "type")
    private String type;
    @Field(name = "itemData")
    private String itemData;
    @Field(name = "folderId")
    private int folderId;
    @Field(name = "ownerId")
    @Indexed(unique = true)
    @NonNull
    private int ownerId;
    @Field(name = "status")
    private int status;
    @Field(name = "upDateTime")
    public  long upDateTime;
}
