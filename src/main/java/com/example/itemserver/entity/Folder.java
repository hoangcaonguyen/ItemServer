package com.example.itemserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "folder")
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Folder {
    @Id
    private String id;
    @Field(name = "folderName")
    @NonNull
    private String folderName;
//    @Field(name = "collection")
//    private List<FolderData> collection;
    @Field(name = "quantity")
    private int quantity;
    @Field(name = "ownerId")
    @Indexed(unique = true)
    @NonNull
    private String ownerId;
    @Field(name = "status")
    private int status;
    @Field(name = "upDateTime")
    public  long upDateTime;
}
