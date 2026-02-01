package com.friendshipos.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.friendshipos.model.Memory;
import com.friendshipos.repo.MemoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
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

    // 📸 Upload Memory (Image Optional)
    @PostMapping("/upload")
    public ResponseEntity<?> uploadMemory(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile image) {

        String imageUrl = null;

        if (image != null && !image.isEmpty()) {
            try {
                Map uploadResult = cloudinary.uploader().upload(
                        image.getBytes(),
                        ObjectUtils.emptyMap()
                );
                imageUrl = uploadResult.get("secure_url").toString();
            } catch (Exception e) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Image upload failed");
            }
        }

        Memory memory = new Memory();
        memory.setTitle(title);
        memory.setDescription(description);
        memory.setImageUrl(imageUrl); // NULL allowed

        memoryRepo.save(memory); // ✅ FIXED

        return ResponseEntity.ok("Memory saved");
    }

    // 📜 All Memories
    @GetMapping("/all")
    public List<Memory> getAll() {
        return memoryRepo.findAll();
    }

    // 🗑 Delete Memory (Safe version)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMemory(@PathVariable Long id) throws Exception {

        Memory memory = memoryRepo.findById(id).orElse(null);
        if (memory == null) return ResponseEntity.notFound().build();

        // Only delete from Cloudinary if image exists
        if (memory.getImageUrl() != null && memory.getImageUrl().contains("cloudinary")) {
            String publicId = memory.getImageUrl()
                    .substring(memory.getImageUrl().lastIndexOf("/") + 1)
                    .split("\\.")[0];

            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        }

        memoryRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
