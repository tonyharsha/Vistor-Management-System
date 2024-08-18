package com.example.L15_Minor_Project_extention.Repo;

import com.example.L15_Minor_Project_extention.entity.Flat;
import com.example.L15_Minor_Project_extention.entity.Visit;
import com.example.L15_Minor_Project_extention.enums.VisitStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface VisitRepo extends JpaRepository<Visit,Long> {
    List<Visit> findByStatusAndFlat(VisitStatus visitStatus, Flat flat);

    List<Visit> findByStatusAndCreatedDateLessThanEqual(VisitStatus visitStatus, Date date);
}
