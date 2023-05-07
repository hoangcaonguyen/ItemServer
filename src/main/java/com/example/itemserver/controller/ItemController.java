package com.example.itemserver.controller;

import com.example.itemserver.dto.ResponseDTO;
import com.example.itemserver.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @Autowired
    private RestTemplate restTemplate;

    public static final String url = "https://userserver-production.up.railway.app";

    @PostMapping(value = "/addItem")
    public ResponseDTO addItem(@RequestPart("file") MultipartFile file,
                               @RequestPart("id") String id,
                               @RequestPart("folder") String folderId){
        ResponseDTO response = new ResponseDTO();
        response = itemService.addItem(file, id, folderId);
        return response;
    }

    @PostMapping(value = "/addItems")
    public ResponseDTO addItems(@RequestPart("file") List<MultipartFile> listFile,
                                @RequestPart("id") String id,
                                @RequestPart("folder") String folderId){
        ResponseDTO response = new ResponseDTO();
        response = itemService.addItems(listFile, id, folderId);
        return response;
    }

    @PostMapping(value = "/findItem")
    public ResponseDTO findItem(@RequestPart("itemName") String itemName){
        ResponseDTO response = new ResponseDTO();
        response = itemService.findItem(itemName);
        return response;
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseDTO deleteItem(@PathVariable(name = "id") int id) {
        ResponseDTO response = new ResponseDTO();
        response = itemService.deleteItem(id);
        return response;
    }

    @PostMapping(value = "/moveItem")
    public ResponseDTO moveItem(@RequestPart("item") int itemId,
                                @RequestPart("folder") int folderId){
        ResponseDTO response = new ResponseDTO();
        response = itemService.moveItem(itemId,folderId);
        return response;
    }
    @GetMapping("/list")
    public ResponseDTO getAllItem(){
        ResponseDTO response = new ResponseDTO();
        response = itemService.getAllItem();
        return response;
    }

    @GetMapping(value = "/findByOwner/{id}")
    public ResponseDTO findItem(@PathVariable(name = "id") int ownerId){
        ResponseDTO response = new ResponseDTO();
        response = itemService.findAllByOwnerId(ownerId);
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
