package com.example.itemserver.service;

import com.example.itemserver.common.DataUtils;
import com.example.itemserver.common.MessageUtils;
import com.example.itemserver.common.ULID;
import com.example.itemserver.dto.ResponseDTO;
import com.example.itemserver.entity.Folder;
import com.example.itemserver.entity.Item;
import com.example.itemserver.repository.FolderRepository;
import com.example.itemserver.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service

public class ItemService {

    private final ItemRepository itemRepository;
    private final FolderRepository folderRepository;

    public ItemService(ItemRepository itemRepository, FolderRepository folderRepository) {
        this.itemRepository = itemRepository;
        this.folderRepository = folderRepository;
    }

    @Transactional
    public ResponseDTO addItem(MultipartFile file, String id, String folderId){
        ResponseDTO responseDTO = new ResponseDTO();
        Assert.isTrue(DataUtils.notNullOrEmpty(file), MessageUtils.getMessage("error.input.null", file));
        Folder folder = folderRepository.findById(folderId);
        Assert.notNull(folder, MessageUtils.getMessage("error.not.found", folderRepository.findById(folderId)));
        Item item = new Item();
        item.setId(new ULID().nextULID());
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains(". .")){
            responseDTO.setMessage("not a valid file");
        }else {
            try {
                item.setItemName(fileName);
                item.setType(file.getContentType());
                item.setItemData(Base64.getEncoder().encodeToString(file.getBytes()));
                item.setOwnerId(id);
                item.setFolderId(folderId);
                item.setStatus(1);
                item.setUpDateTime(System.currentTimeMillis());
            } catch (IOException e) {
                e.printStackTrace();
            }
            itemRepository.save(item);
            folder.setQuantity(folder.getQuantity()+1);
            folderRepository.save(folder);
            responseDTO = successResponse();
        }
        return responseDTO;
    }

    @Transactional
    public ResponseDTO addItems(List<MultipartFile>  listFile, String id, String folderId){
        ResponseDTO responseDTO = new ResponseDTO();
        for (MultipartFile file: listFile){
            responseDTO = addItem(file, id,folderId);
        }
        return responseDTO;
    }

    public ResponseDTO findItem(String name){
        Assert.notNull(name, MessageUtils.getMessage("error.notfound", name));
        Item item = itemRepository.findByItemName(name);
        ResponseDTO responseDTO = successResponse();
        responseDTO.setResponse(item);
        return responseDTO;
    }

    public ResponseDTO deleteItem(String id){
        Assert.notNull(id, MessageUtils.getMessage("error.notfound", id));
        Item item = itemRepository.findById(id);
        item.setStatus(0);
        item.setUpDateTime(System.currentTimeMillis());
        itemRepository.save(item);
        ResponseDTO responseDTO = successResponse();
        return responseDTO;
    }

    public ResponseDTO moveItem(String id, String folderId){
        ResponseDTO responseDTO = successResponse();
        Assert.notNull(id, MessageUtils.getMessage("error.notfound", id));
        Item item = itemRepository.findById(id);
        Folder folder1 = folderRepository.findById(item.getFolderId());
        Assert.notNull(folderId, MessageUtils.getMessage("error.notfound", folderRepository.findById(folderId)));
        Folder folder2 = folderRepository.findById(folderId);
        item.setFolderId(folderId);
        item.setUpDateTime(System.currentTimeMillis());
        itemRepository.save(item);
        folder1.setQuantity(itemRepository.countItemsByFolderId(item.getFolderId()));
        folder1.setUpDateTime(System.currentTimeMillis());
        folderRepository.save(folder1);
        folder2.setQuantity(itemRepository.countItemsByFolderId(folderId));
        folder2.setUpDateTime(System.currentTimeMillis());
        folderRepository.save(folder2);
        return responseDTO;
    }

    public ResponseDTO getAllItem(){
        ResponseDTO responseDTO = successResponse();
        responseDTO.setResponse(itemRepository.findAllByStatus(1));
        return responseDTO;
    }

    public ResponseDTO findAllByOwnerId(String id){
        ResponseDTO responseDTO = successResponse();
        responseDTO.setResponse(itemRepository.findAllByOwnerId(id));
        return responseDTO;
    }

    public ResponseDTO successResponse(){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(200);
        responseDTO.setMessage(MessageUtils.getMessage("success.upload"));
        return responseDTO;
    }
}
