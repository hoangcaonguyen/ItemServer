package com.example.itemserver.service;

import com.example.itemserver.common.MessageUtils;
import com.example.itemserver.common.ULID;
import com.example.itemserver.dto.ResponseDTO;
import com.example.itemserver.entity.Folder;
import com.example.itemserver.entity.Item;
import com.example.itemserver.repository.FolderRepository;
import com.example.itemserver.repository.ItemRepository;
import com.google.api.services.drive.Drive;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class FolderService {
    private final FolderRepository folderRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final Drive drive;

    public FolderService(FolderRepository folderRepository, ItemRepository itemRepository, ItemService itemService, Drive drive) {
        this.folderRepository = folderRepository;
        this.itemRepository = itemRepository;
        this.itemService = itemService;
        this.drive = drive;
    }

    public ResponseDTO addFolder(String folderName, String id){
        Folder folder = new Folder();
        Assert.notNull(folderName, MessageUtils.getMessage("error.input.null", folderName));
        Folder folder1 = folderRepository.findByFolderName(folderName);
        Assert.isNull(folder1, MessageUtils.getMessage("error.not.found", folder1));
        folder.setFolderName(folderName);
        folder.setId(new ULID().nextULID());
        folder.setOwnerId(id);
        folder.setStatus(1);
        folder.setUpDateTime(System.currentTimeMillis());
        folderRepository.save(folder);
        ResponseDTO responseDTO = successResponse();
        return responseDTO;
    }

    public ResponseDTO updateFolder(String name, String newName){
        Assert.notNull(name, MessageUtils.getMessage("error.input.null", name));
        Assert.notNull(newName, MessageUtils.getMessage("error.input.null", newName));
        Folder folder = folderRepository.findByFolderName(name);
        Assert.notNull(folder, MessageUtils.getMessage("error.not.found", folder));
        folder.setFolderName(newName);
        folder.setUpDateTime(System.currentTimeMillis());
        folderRepository.save(folder);
        ResponseDTO responseDTO = successResponse();
        return responseDTO;
    }

    public ResponseDTO findFolder(String name){
        Assert.notNull(name, MessageUtils.getMessage("error.input.null", name));
        Folder folder = folderRepository.findByFolderName(name);
        ResponseDTO responseDTO = successResponse();
        responseDTO.setResponse(folder);
        return responseDTO;
    }

    public ResponseDTO getAllFolderItem(String folderName) throws IOException {
        Assert.notNull(folderName, MessageUtils.getMessage("error.input.null", folderName));
        Folder folder = folderRepository.findByFolderName(folderName);
        List<Item> items = itemRepository.findAllByFolderId(folder.getId());
//        for (Item item : items){
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//            drive.files().get(item.getId())
//                    .executeMediaAndDownloadTo(outputStream);
//            item.setItemBytes(outputStream.toByteArray());
//        }
        ResponseDTO responseDTO = successResponse();
        responseDTO.setResponse(items);
        return responseDTO;
    }

    public ResponseDTO deleteFolder(String folderId){
        Assert.notNull(folderId, MessageUtils.getMessage("error.notfound", folderId));
        Folder folder = folderRepository.findById(folderId);
        List<Item> items = itemRepository.findAllByFolderId(folder.getId());
        for (Item item : items){
            itemService.deleteItem(item.getId());
        }
        folder.setStatus(0);
        folder.setUpDateTime(System.currentTimeMillis());
        folderRepository.save(folder);
        ResponseDTO responseDTO = successResponse();
        return responseDTO;
    }

    public ResponseDTO getAllFolder(){
        ResponseDTO responseDTO = successResponse();
        responseDTO.setResponse(folderRepository.findAllByStatus(1));
        return responseDTO;
    }

    public ResponseDTO getAllFolderByOwner(String id){
        ResponseDTO responseDTO = successResponse();
        responseDTO.setResponse(folderRepository.findAllByOwnerId(id));
        return responseDTO;
    }

    public int countId (){
        int count = (int)folderRepository.findAll().stream().count();
        return count+1;
    }

    public ResponseDTO successResponse(){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(200);
        responseDTO.setMessage(MessageUtils.getMessage("success.upload"));
        return responseDTO;
    }

}
