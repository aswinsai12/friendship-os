package com.friendshipos.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.friendshipos.model.Memory;
import com.friendshipos.repo.MemoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memory")
public class MemoryController {

    private final MemoryRepo memoryRepo;
    private final Cloudinary cloudinary;

    // 📸 Upload Memory
    @PostMapping("/upload")
    public Memory addMemory(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam MultipartFile image) throws Exception {

        Map uploadResult = cloudinary.uploader().upload(
                image.getBytes(),
                ObjectUtils.asMap("folder", "friendship-os")
        );

        String imageUrl = uploadResult.get("secure_url").toString();

        Memory memory = Memory.builder()
                .title(title)
                .description(description)
                .imageUrl(imageUrl)
                .build();

        return memoryRepo.save(memory);
    }

    // 📜 All Memories
    @GetMapping("/all")
    public List<Memory> getAll() {
        return memoryRepo.findAll();
    }

    // 🗑 Delete Memory
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMemory(@PathVariable Long id) throws Exception {

        Memory memory = memoryRepo.findById(id).orElse(null);
        if (memory == null) return ResponseEntity.notFound().build();

        String publicId = memory.getImageUrl()
                .substring(memory.getImageUrl().lastIndexOf("/") + 1)
                .split("\\.")[0];

        cloudinary.uploader().destroy("friendship-os/" + publicId, ObjectUtils.emptyMap());

        memoryRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
