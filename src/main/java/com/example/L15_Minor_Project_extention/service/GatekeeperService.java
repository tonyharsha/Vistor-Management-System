package com.example.L15_Minor_Project_extention.service;

import com.example.L15_Minor_Project_extention.Repo.FlatRepo;
import com.example.L15_Minor_Project_extention.Repo.VisitRepo;
import com.example.L15_Minor_Project_extention.Repo.VisitorRepo;
import com.example.L15_Minor_Project_extention.dto.AddressDto;
import com.example.L15_Minor_Project_extention.dto.VisitDto;
import com.example.L15_Minor_Project_extention.dto.VisitorDto;
import com.example.L15_Minor_Project_extention.entity.*;
import com.example.L15_Minor_Project_extention.enums.VisitStatus;
import com.example.L15_Minor_Project_extention.exception.BadRequestException;
import com.example.L15_Minor_Project_extention.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class GatekeeperService {

    @Autowired
    VisitorRepo visitorRepo;

    @Autowired
    FlatRepo flatRepo;

    @Autowired
    VisitRepo visitRepo;

    @Autowired
    RedisTemplate<String,VisitorDto> redisTemplate;

    public Long createVisitor(VisitorDto visitordto){
        Visitor visitor = Visitor.builder()
                .email(visitordto.getEmail())
                .name(visitordto.getName())
                .phoneNo(visitordto.getPhoneNo())
                .idNumber(visitordto.getIdNumber())
                .build();
        if(visitordto.getAddress()!=null){
            AddressDto addressDto=visitordto.getAddress();
            Address address=Address.builder()
                    .line1(addressDto.getLine1())
                    .line2(addressDto.getLine2())
                    .city(addressDto.getCity())
                    .pincode(addressDto.getPincode())
                    .country(addressDto.getCountry())
                    .build();
            visitor.setAddress(address);
        }

        visitor=visitorRepo.save(visitor);
        return  visitor.getId();
    }

    public VisitorDto getVisitor(String idNumber){
        String key="visitor ::"+idNumber;

        VisitorDto visitorDto=redisTemplate.opsForValue().get(key);
        if(visitorDto!=null){
            return  visitorDto;
        }
        Visitor visitor=visitorRepo.findByIdNumber(idNumber);
        if(visitor!=null){
            visitorDto=VisitorDto.builder()
                    .email(visitor.getEmail())
                    .name(visitor.getName())
                    .phoneNo(visitor.getPhoneNo())
                    .idNumber(visitor.getIdNumber())
                    .build();
            redisTemplate.opsForValue().set(key,visitorDto,60, TimeUnit.HOURS);
        }
        else{
            throw new NotFoundException("Visitor Not Found");
        }
        return visitorDto;
    }

    public Long createVisit(VisitDto visitDto){

        Visitor visitor=visitorRepo.findByIdNumber(visitDto.getIdNumber());
        if(visitor==null){
            throw new BadRequestException("visitor not found");
        }

        Flat flat=flatRepo.findByNumber(visitDto.getFlatNumber());
        if(flat==null){
            throw new BadRequestException("Flat not found");
        }

        User gatekeeper = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Visit visit=Visit.builder()
                .imgUrl(visitDto.getUrlofImage())
                .purpose(visitDto.getPurpose())
                .visitor(visitor)
                .flat(flat)
                .status(VisitStatus.WAITING)
                .noOfPeople(visitDto.getNoOfPeople())
                .createdBy(gatekeeper)
                .build();
        visit=visitRepo.save(visit);
        return visit.getId();
    }

    public String markEntry(Long id){
        Visit visit=visitRepo.findById(id).get();

        if(visit==null){
            throw new BadRequestException("visit does not exists");
        }

        User gatekeeper = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!gatekeeper.getId().equals(visit.getCreatedBy().getId())){
            throw new BadRequestException("This GateKeeper is different");
        }

        if(visit.getStatus().equals(VisitStatus.APPROVED)){
            visit.setInTime(new Date());
            visitRepo.save(visit);
            return "Done";
        }else{
            throw new BadRequestException("Unable to mark the visit");
        }


    }

    public String markExit(Long id){
        Visit visit=visitRepo.findById(id).get();

        if(visit==null){
            throw new BadRequestException("visit does not exits");
        }

        if(visit.getStatus().equals(VisitStatus.APPROVED) && visit.getInTime()!=null){
            visit.setOutTime(new Date());
            visit.setStatus(VisitStatus.COMPLETED);
            visitRepo.save(visit);
            return "Done";
        }else {
            throw new BadRequestException("Unable to mark the visit");
        }

    }
}
