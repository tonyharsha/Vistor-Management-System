package com.example.L15_Minor_Project_extention.service;


import com.example.L15_Minor_Project_extention.Repo.FlatRepo;
import com.example.L15_Minor_Project_extention.Repo.UserRepo;
import com.example.L15_Minor_Project_extention.dto.AddressDto;
import com.example.L15_Minor_Project_extention.dto.UserDto;
import com.example.L15_Minor_Project_extention.entity.Address;
import com.example.L15_Minor_Project_extention.entity.Flat;
import com.example.L15_Minor_Project_extention.entity.User;
import com.example.L15_Minor_Project_extention.enums.Role;
import com.example.L15_Minor_Project_extention.enums.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AdminService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FlatRepo flatRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Long createUser(UserDto userDto){

        User user= User.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .phoneNo(userDto.getPhoneNo())
                .role(Role.valueOf(userDto.getRole()))
                .status(UserStatus.ACTIVE)
                .idNumber(userDto.getIdNumber())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
        if(userDto.getAddressDto()!=null){
            AddressDto addressDto=userDto.getAddressDto();
            Address address=Address.builder()
                    .line1(addressDto.getLine1())
                    .line2(addressDto.getLine2())
                    .city(addressDto.getCity())
                    .pincode(addressDto.getPincode())
                    .country(addressDto.getCountry())
                    .build();
            user.setAddress(address);
        }

        if(userDto.getFlatNo()!=null){
            Flat flat=flatRepo.findByNumber(userDto.getFlatNo());

            if(flat==null){

            }

            user.setFlat(flat);
        }



       Long id=userRepo.save(user).getId();
       return id;
    }
}
