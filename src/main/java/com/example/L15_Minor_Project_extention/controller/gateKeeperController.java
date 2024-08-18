package com.example.L15_Minor_Project_extention.controller;

import com.example.L15_Minor_Project_extention.dto.VisitDto;
import com.example.L15_Minor_Project_extention.dto.VisitorDto;
import com.example.L15_Minor_Project_extention.service.GatekeeperService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/gatekeeper")
public class gateKeeperController {
    public static Logger logger=LoggerFactory.getLogger(gateKeeperController.class);


    @Autowired
    GatekeeperService gatekeeperService;

    @PostMapping("/createVisitor")
    ResponseEntity<Long> createVisitor(@RequestBody @Valid VisitorDto visitorDto){
        Long user_id=gatekeeperService.createVisitor(visitorDto);
        return ResponseEntity.ok(user_id);
    }

    @GetMapping("/getVisitor")
    ResponseEntity<VisitorDto> getVisitor(@RequestParam String idNumber){
        VisitorDto visitorDto=gatekeeperService.getVisitor(idNumber);
        if(visitorDto==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(visitorDto);
    }

    @PostMapping("/createVisit")
    ResponseEntity<Long> createVisit(@RequestBody @Valid VisitDto visitDto){
        Long id=gatekeeperService.createVisit(visitDto);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/markEntry/{id}")
    ResponseEntity<String> markEntry(@PathVariable Long id){
        return ResponseEntity.ok(gatekeeperService.markEntry(id));
    }



    @PostMapping("/markExit/{id}")
    ResponseEntity<String> markExit(@PathVariable Long id){
        return ResponseEntity.ok(gatekeeperService.markExit(id));
    }


    @PostMapping("/uploadPhoto")
    ResponseEntity<String>  uploadPhoto(@RequestParam("file")MultipartFile file){
        String fileName= UUID.randomUUID()+"_"+file.getOriginalFilename();
        String publicUrl="/content/"+fileName;
        String uplaodPath="E:/placement material/L10-Minor-Project/src/main/resources/Images/"+fileName;
        try {
            file.transferTo(new File(uplaodPath));
        } catch (IOException e) {
            logger.error("Exception while uploading image: {}", e);
            return ResponseEntity.ok("Exception while uploading Image");
        }
        return ResponseEntity.ok(publicUrl);
    }

}
