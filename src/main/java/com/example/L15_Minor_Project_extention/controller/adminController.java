package com.example.L15_Minor_Project_extention.controller;


import com.example.L15_Minor_Project_extention.dto.AddressDto;
import com.example.L15_Minor_Project_extention.dto.UserDto;
import com.example.L15_Minor_Project_extention.service.AdminService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class adminController {

    private Logger logger= LoggerFactory.getLogger(adminController.class);


    @Autowired
    private AdminService adminService;

    @PostMapping("/createUser")
    ResponseEntity<Long>  createUser(@RequestBody UserDto userDto){
        Long user_id=adminService.createUser(userDto);
        return ResponseEntity.ok(user_id);
    }


    @PostMapping("/user-csv-upload")
    ResponseEntity<List<String>> createUserWithCsv(@RequestParam("file")MultipartFile file){
        logger.info("File uploaded ::{}",file.getOriginalFilename());

        List<String> response=new ArrayList<>();

        try {
            BufferedReader fileReader=new BufferedReader(new InputStreamReader(file.getInputStream()));
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            Iterable<CSVRecord> csvRecords=csvParser.getRecords();
            for(CSVRecord csvRecord:csvRecords){
                UserDto userDto=UserDto.builder()
                        .name(csvRecord.get("name"))
                        .email(csvRecord.get("email"))
                        .phoneNo(csvRecord.get("phoneNo"))
                        .role(csvRecord.get("role"))
                        .idNumber(csvRecord.get("idNumber"))
                        .flatNo(csvRecord.get("flatNo"))
                        .build();
                AddressDto addressDto=AddressDto.builder()
                        .line1(csvRecord.get("line1"))
                        .line2(csvRecord.get("line2"))
                        .city(csvRecord.get("city"))
                        .pincode(csvRecord.get("pincode"))
                        .country(csvRecord.get("country"))
                        .build();
                userDto.setAddressDto(addressDto);

                try{
                    Long id=adminService.createUser(userDto);
                    response.add("Created User "+userDto.getName()+"with id:"+id);

                }catch(Exception e){
                    response.add("Unable to created User "+userDto.getName()+" msg:"+e.getMessage());
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(response);
    }
}
