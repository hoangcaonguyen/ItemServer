package com.example.itemserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "item")
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Item {
    @Id
    private String id;
    @Field(name = "itemName")
    @NonNull
    private String itemName;
    @Field(name = "type")
    private String type;
    @Field(name = "itemData")
    private String itemData;
    @Field(name = "folderId")
    private String folderId;
    @Field(name = "ownerId")
    @Indexed(unique = true)
    @NonNull
    private String ownerId;
    @Field(name = "status")
    private int status;
    @Field(name = "upDateTime")
    public  long upDateTime;
}
