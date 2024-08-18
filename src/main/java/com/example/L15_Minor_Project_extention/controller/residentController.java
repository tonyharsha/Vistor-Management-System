package com.example.L15_Minor_Project_extention.controller;

import com.example.L15_Minor_Project_extention.dto.AllVisitsByPageResponseBody;
import com.example.L15_Minor_Project_extention.dto.VisitDto;
import com.example.L15_Minor_Project_extention.entity.User;
import com.example.L15_Minor_Project_extention.enums.VisitStatus;
import com.example.L15_Minor_Project_extention.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/resident")
public class residentController {

    @Autowired
    ResidentService residentService;

    @PostMapping("/actOnVisit/{id}")
    public ResponseEntity<String> actOnVisit(@PathVariable Long id, @RequestParam VisitStatus visitStatus){
        return ResponseEntity.ok(residentService.updateVisit(id,visitStatus));
    }

    @GetMapping("/pedingvisits")
    public ResponseEntity<List<VisitDto>> getPendingVisits(@AuthenticationPrincipal User user){
        return  ResponseEntity.ok(residentService.getPendingVisits(user.getId()));
    }


    @GetMapping("/getAllPendingVisits")
    public ResponseEntity<AllVisitsByPageResponseBody> getAllPendingVisits(@AuthenticationPrincipal User user, @RequestParam Integer pageno, Integer pagesize){
        Pageable pageable= Pageable.ofSize(pagesize).withPage(pageno);
        return ResponseEntity.ok(residentService.getAllpendingVisits(user.getId(),pageable));
    }



}

