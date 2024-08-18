package com.example.L15_Minor_Project_extention.Repo;

import com.example.L15_Minor_Project_extention.entity.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FlatRepo extends JpaRepository<Flat,Long> {
        Flat findByNumber(String number);
}
