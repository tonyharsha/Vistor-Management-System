package com.example.L15_Minor_Project_extention.Repo;

import com.example.L15_Minor_Project_extention.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    public User findByEmail(String email);
}
