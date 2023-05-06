package com.example.itemserver.controller;

import com.example.itemserver.dto.ResponseDTO;
import com.example.itemserver.entity.Folder;
import com.example.itemserver.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/folder")
public class FolderController {
    @Autowired
    private final FolderService folderService;

    @Autowired
    private RestTemplate restTemplate;

    public static final String url = "http://localhost:8081";

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }
    @PostMapping("/add")
    public ResponseDTO addItem(@RequestPart("name") String name){
        ResponseDTO response = new ResponseDTO();
        response = folderService.addFolder(name);
        return response;
    }

    @PostMapping("/updateFolder")
    public ResponseDTO updateFolder(@RequestPart("name") String name,
                                    @RequestPart("newName") String newName){
        ResponseDTO response = new ResponseDTO();
        response = folderService.updateFolder(name,newName);
        return response;
    }

    @PostMapping("/findFolder")
    public ResponseDTO findFolder(@RequestPart("name") String name){
        ResponseDTO response = new ResponseDTO();
        response = folderService.findFolder(name);
        return response;
    }

    @PostMapping("/getAllFolderItem")
    public ResponseDTO getAllFolderItem(@RequestPart("name") String name){
        ResponseDTO response = new ResponseDTO();
        response = folderService.getAllFolderItem(name);
        return response;
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseDTO deleteFolder(@PathVariable(name = "id") Integer id) {
        ResponseDTO response = new ResponseDTO();
        response = folderService.deleteFolder(id);
        return response;
    }

    @GetMapping("/list")
    public ResponseDTO getAllItem(){
        ResponseDTO response = new ResponseDTO();
        response = folderService.getAllFolder();
        return response;
    }

    @GetMapping(value = "/findByOwner/{id}")
    public ResponseDTO findItem(@PathVariable(name = "id") int ownerId){
        ResponseDTO response = new ResponseDTO();
        response = folderService.getAllFolderByOwner(ownerId);
        return response;
    }
}
