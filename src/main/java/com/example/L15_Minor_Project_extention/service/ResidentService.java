package com.example.L15_Minor_Project_extention.service;


import com.example.L15_Minor_Project_extention.Repo.UserRepo;
import com.example.L15_Minor_Project_extention.Repo.VisitRepo;
import com.example.L15_Minor_Project_extention.dto.AllVisitsByPageResponseBody;
import com.example.L15_Minor_Project_extention.dto.VisitDto;
import com.example.L15_Minor_Project_extention.entity.Flat;
import com.example.L15_Minor_Project_extention.entity.User;
import com.example.L15_Minor_Project_extention.entity.Visit;
import com.example.L15_Minor_Project_extention.enums.VisitStatus;
import com.example.L15_Minor_Project_extention.exception.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResidentService {

    @Autowired
    VisitRepo visitRepo;

    @Autowired
    UserRepo userRepo;


    public String updateVisit(Long id, VisitStatus newStatus){
        Visit visit=visitRepo.findById(id).get();
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!visit.getFlat().getId().equals(user.getFlat().getId())){
            throw new BadRequestException("Falt id Visit is different!!!");
        }
        if(VisitStatus.WAITING.equals(visit.getStatus())){
            visit.setStatus(newStatus);
            visitRepo.save(visit);
            return "Done";
        }else{

        }
        return "Error";
    }


    @Transactional
    public List<VisitDto> getPendingVisits(Long user_id){
        List<VisitDto> visitDtoList=new ArrayList<>();
        User user=userRepo.findById(user_id).get();
        if(user!=null){
            Flat flat=user.getFlat();
            List<Visit> visitList=visitRepo.findByStatusAndFlat(VisitStatus.WAITING,flat);
            for(Visit visit:visitList){
                visitDtoList.add(fromVisit(visit));
            }
        }

        return visitDtoList;
    }

    @Transactional
    public AllVisitsByPageResponseBody getAllpendingVisits(Long user_id, Pageable pageable){
        AllVisitsByPageResponseBody allVisitsByPageResponseBody=new AllVisitsByPageResponseBody();
        List<VisitDto> visitDtoList=new ArrayList<>();
        User user=userRepo.findById(user_id).get();
        if(user!=null){
            Flat flat=user.getFlat();
            Page<Visit> Visitpage=visitRepo.findAll(pageable);
            List<Visit> visitList=Visitpage.stream().toList();
            allVisitsByPageResponseBody.setTotalRow(Visitpage.getTotalElements());
            allVisitsByPageResponseBody.setTotalPage(Visitpage.getTotalPages());
            for(Visit visit:visitList){
                visitDtoList.add(fromVisit(visit));
            }
            allVisitsByPageResponseBody.setVisitDtos(visitDtoList);
        }

        return allVisitsByPageResponseBody;

    }




    private VisitDto fromVisit(Visit visit){
        VisitDto visitDto=VisitDto.builder()
                .urlofImage(visit.getImgUrl())
                .purpose(visit.getPurpose())
                .noOfPeople(visit.getNoOfPeople())
                .visitorName(visit.getVisitor().getName())
                .idNumber(visit.getVisitor().getIdNumber())
                .flatNumber(visit.getFlat().getNumber())
                .inTime(visit.getInTime())
                .outTime(visit.getOutTime())
                .build();
        return visitDto;
    }
}
