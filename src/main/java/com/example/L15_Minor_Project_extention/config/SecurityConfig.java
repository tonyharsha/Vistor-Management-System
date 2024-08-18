package com.example.L15_Minor_Project_extention.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        //return NoOpPasswordEncoder.getInstance(); //This is when encoding is not required and is not a best pratice.
        return new BCryptPasswordEncoder();
    }



  /*  public static void main(String args[]){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123"));
    }
   */

    /*
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails userDetails1= User.builder()
                .username("Harsha")
                .password("$2a$10$ilXG9EGd9Cooc9Yf8awJzus.BThnHPPxuEpuRVfKDhrknLBOgTT.W")
                .build();
        UserDetails userDetails2=User.builder()
                .username("Ramu")
                .password("$2a$10$uUg0MslYGTHllD/AXMSyn.qm/sbHHkqwGxkkXdMaUSY5jqLzfNcdO")
                .build();

        return new InMemoryUserDetailsManager(userDetails1,userDetails2);
    }

     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()). //This line is disabiling csrf token
                authorizeHttpRequests((auth)->{
            auth.requestMatchers("/admin/**").hasAuthority("ADMIN")
                    .requestMatchers("/gatekeeper/**").hasAuthority("GATEKEEPER")
                    .requestMatchers("/resident/**").hasAuthority("RESIDENT")
                    .requestMatchers("/public/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated();
        })
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults()); //This line ensure we can procced testing/verfication via postman. and server to server call

                return httpSecurity.build();

    }
}
