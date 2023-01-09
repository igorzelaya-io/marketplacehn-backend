package com.marketplacehn.controller;

import com.marketplacehn.entity.Item;
import com.marketplacehn.response.BaseResponse;
import com.marketplacehn.response.Response;
import com.marketplacehn.service.ItemService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST Controller for Item entity.
 * @author Igor A. Zelaya (igorz@marketplacehn.com)
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    @NonNull
    private final ItemService itemService;

    @GetMapping("/{itemId}")
    public ResponseEntity<? extends Response<Item>> findItemById(@PathVariable("itemId") final String itemId) {
        BaseResponse<Item> response = new BaseResponse<>();
        Item retrievedItem = itemService.findItemById(itemId);
        return response.buildResponseEntity(HttpStatus.OK, "Item retrieved.", retrievedItem);
    }

    @PostMapping
    public ResponseEntity<? extends Response<Item>> saveItem(@RequestBody(required = true) Item item) {
        BaseResponse<Item> response = new BaseResponse<>();
        Item savedItem = itemService.saveItem(item);
        return response.buildResponseEntity(HttpStatus.OK, "Item upserted successfully.", savedItem);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<? extends Response<String>> deleteItemById(@PathVariable("itemId") final String itemId) {
        BaseResponse<String> response = new BaseResponse<>();
        itemService.deleteItemById(itemId);
        return response.buildResponseEntity(HttpStatus.OK, "Item deleted successfully.", itemId);
    }

}
