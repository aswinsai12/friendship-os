package com.friendshipos.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.util.*;

import com.friendshipos.model.Message;
import com.friendshipos.repo.MessageRepo;
@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageRepo repo;
    private final Path uploadPath = Paths.get("uploads/messages");

    // 📤 Add Message (WRITE)
    @CacheEvict(value = "messagesCache", allEntries = true)
    @PostMapping("/add")
    public Message addMessage(
            @RequestParam String senderName,
            @RequestParam String text,
            @RequestParam MultipartFile image
    ) throws Exception {

        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Files.copy(image.getInputStream(), uploadPath.resolve(fileName));

        Message msg = new Message();
        msg.setSenderName(senderName);
        msg.setText(text);
        msg.setImageName(fileName);

        return repo.save(msg);
    }

    // 📥 Get Messages (READ HEAVY)
    @Cacheable(value = "messagesCache")
    @GetMapping("/all")
    public List<Message> getAll() {
        return repo.findAll();  // limit rows!
    }

    // 🖼 Image Download (NO CACHE)
    @GetMapping("/image/{file}")
    public org.springframework.core.io.Resource getImage(@PathVariable String file) throws Exception {
        return new org.springframework.core.io.UrlResource(uploadPath.resolve(file).toUri());
    }
}
