package com.example.L15_Minor_Project_extention.config;

import com.example.L15_Minor_Project_extention.Repo.VisitRepo;
import com.example.L15_Minor_Project_extention.entity.Visit;
import com.example.L15_Minor_Project_extention.enums.VisitStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.Date;
import java.util.List;


@Configuration
public class VisitExpirySchedularTask {
    Logger logger= LoggerFactory.getLogger(VisitExpirySchedularTask.class);

    @Autowired
    VisitRepo visitRepo;

    //@Scheduled(fixedDelay = 3000)
    public void MarkVisitExpiry(){
        logger.info("Marking all pending visits to expiry");
        Date date = new Date();
        List<Visit> visitList= visitRepo.findByStatusAndCreatedDateLessThanEqual(VisitStatus.WAITING,date);

        for(Visit visit:visitList){
            visit.setStatus(VisitStatus.EXPIRED);
        }

        visitRepo.saveAll(visitList);
    }
}
