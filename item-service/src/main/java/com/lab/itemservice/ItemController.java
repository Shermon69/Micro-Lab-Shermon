package com.lab.itemservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/items")
public class ItemController {

    // Simple in-memory list (no database needed)
    private final List<Map<String, Object>> items = new ArrayList<>();
    private int idCounter = 1;

    public ItemController() {
        // Pre-populate with sample data
        items.add(Map.of("id", idCounter++, "name", "Book"));
        items.add(Map.of("id", idCounter++, "name", "Laptop"));
        items.add(Map.of("id", idCounter++, "name", "Phone"));
    }

    // GET /items — return all items
    @GetMapping
    public List<Map<String, Object>> getItems() {
        return items;
    }

    // POST /items — add a new item
    @PostMapping
    public ResponseEntity<Map<String, Object>> addItem(@RequestBody Map<String, Object> item) {
        item.put("id", idCounter++);
        items.add(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    // GET /items/{id} — get item by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getItem(@PathVariable int id) {
        return items.stream()
                .filter(i -> i.get("id").equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
