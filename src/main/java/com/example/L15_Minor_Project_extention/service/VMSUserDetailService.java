package com.example.L15_Minor_Project_extention.service;

import com.example.L15_Minor_Project_extention.Repo.UserRepo;
import com.example.L15_Minor_Project_extention.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class VMSUserDetailService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.findByEmail(username);
        if(user==null){
            throw  new UsernameNotFoundException("User does'nt exists!!!");
        }
        return  user;
    }
}
