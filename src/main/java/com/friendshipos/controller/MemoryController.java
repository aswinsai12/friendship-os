package com.friendshipos.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.friendshipos.model.Memory;
import com.friendshipos.repo.MemoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memory")
@CrossOrigin // important if frontend hosted separately
public class MemoryController {

    private final MemoryRepo memoryRepo;
    private final Cloudinary cloudinary;

    // ================= UPLOAD MEMORY =================
    @PostMapping("/upload")
    public ResponseEntity<?> uploadMemory(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile image) {

        String imageUrl = null;

        if (image != null && !image.isEmpty()) {
            try {
                // Validate it's an image
                String type = image.getContentType();
                if (type == null || !type.startsWith("image/")) {
                    return ResponseEntity.badRequest().body("Only image files allowed");
                }

                // 🔥 HEIC/HEIF SAFE UPLOAD (auto convert to JPG)
                Map uploadResult = cloudinary.uploader().upload(
        image.getBytes(),
        ObjectUtils.asMap(
                "folder", "friendship-os",
                "resource_type", "image",
                "format", "jpg",              // ⭐ FORCE JPG
                "transformation", "f_auto"    // ⭐ Auto optimize
        )
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
        memory.setImageUrl(imageUrl); // can be null

        memoryRepo.save(memory);

        return ResponseEntity.ok("Memory saved");
    }

    // ================= GET ALL =================
    @GetMapping("/all")
    public List<Memory> getAll() {
        return memoryRepo.findAll();
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMemory(@PathVariable Long id) throws Exception {

        Memory memory = memoryRepo.findById(id).orElse(null);
        if (memory == null) return ResponseEntity.notFound().build();

        // delete from Cloudinary only if image exists
        if (memory.getImageUrl() != null) {
            String publicId = memory.getImageUrl()
                    .substring(memory.getImageUrl().lastIndexOf("/") + 1)
                    .split("\\.")[0];

            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        }

        memoryRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
