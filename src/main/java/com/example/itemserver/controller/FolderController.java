package com.example.itemserver.controller;

import com.example.itemserver.dto.ResponseDTO;
import com.example.itemserver.entity.Folder;
import com.example.itemserver.service.AuthorizationService;
import com.example.itemserver.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/folder")
public class FolderController {
    @Autowired
    private final FolderService folderService;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AuthorizationService authorizationService;

    public static final String url = "https://userserver-production.up.railway.app";

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }
    @PostMapping("/add")
    public ResponseDTO addItem(@RequestPart("name") String name,
                               @RequestPart("id") String id,
                               @RequestHeader(name = "Authorization") String token){
        if(!authorizationService.authorization(token)) return ResponseDTO.authFailed();

        ResponseDTO response = new ResponseDTO();
        response = folderService.addFolder(name, id);
        return response;
    }

    @PostMapping("/updateFolder")
    public ResponseDTO updateFolder(@RequestPart("id") String id,
                                    @RequestPart("newName") String newName,
                                    @RequestHeader(name = "Authorization") String token){
        if(!authorizationService.authorization(token)) return ResponseDTO.authFailed();

        ResponseDTO response = new ResponseDTO();
        response = folderService.updateFolder(id,newName);
        return response;
    }

    @PostMapping("/findFolder")
    public ResponseDTO findFolder(@RequestPart("name") String name,
                                  @RequestHeader(name = "Authorization") String token){
        if(!authorizationService.authorization(token)) return ResponseDTO.authFailed();

        ResponseDTO response = new ResponseDTO();
        response = folderService.findFolder(name);
        return response;
    }

    @PostMapping("/getAllFolderItem")
    public ResponseDTO getAllFolderItem(@RequestPart("name") String name,
                                        @RequestHeader(name = "Authorization") String token) throws IOException {
        if(!authorizationService.authorization(token)) return ResponseDTO.authFailed();

        ResponseDTO response = new ResponseDTO();
        response = folderService.getAllFolderItem(name);
        return response;
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseDTO deleteFolder(@PathVariable(name = "id") String id,
                                    @RequestHeader(name = "Authorization") String token) {
        if(!authorizationService.authorization(token)) return ResponseDTO.authFailed();

        ResponseDTO response = new ResponseDTO();
        response = folderService.deleteFolder(id);
        return response;
    }

    @GetMapping("/list")
    public ResponseDTO getAllItem(@RequestHeader(name = "Authorization") String token){
        if(!authorizationService.authorization(token)) return ResponseDTO.authFailed();

        ResponseDTO response = new ResponseDTO();
        response = folderService.getAllFolder();
        return response;
    }

    @GetMapping(value = "/findByOwner/{id}")
    public ResponseDTO findItem(@PathVariable(name = "id") String ownerId){
//        if(!authorizationService.authorization(token)) return ResponseDTO.authFailed();

        ResponseDTO response = new ResponseDTO();
        response = folderService.getAllFolderByOwner(ownerId);
        return response;
    }

    public ResponseEntity<String> getData(String functionUrl){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(url + functionUrl , HttpMethod.GET, entity, String.class);
        return response;
    }
}
