package com.example.restapijwt.controller;

import com.example.restapijwt.config.EmailConfig;
import com.example.restapijwt.dto.LoginDTO;
import com.example.restapijwt.security.JwtProvider;
import com.example.restapijwt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    final JwtProvider jwtProvider;
    final AuthService authService;
    final EmailConfig emailConfig;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        //login qiladi Tizimda bor bo'lsa token generate qilishimz kerak

        UserDetails userDetails = authService.loadUserByUsername(loginDTO.getName());
        String token = jwtProvider.generateToken(loginDTO.getName());


        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("Salom do'st");
        message.setSubject("Title bu akasi!");
        message.setSentDate(new Date());
        message.setTo("jafarbek1997@gmail.com");
        message.setFrom("pdp.uz@gmail.com");

        JavaMailSender mailSender = emailConfig.send();
        mailSender.send(message);

        return ResponseEntity.ok().body(token);
    }
}
