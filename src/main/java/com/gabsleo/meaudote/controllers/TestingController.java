package com.gabsleo.meaudote.controllers;

import com.gabsleo.meaudote.utils.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/testing")
@CrossOrigin("*")
public class TestingController {
    @GetMapping
    public ResponseEntity<Response<String>> helloWorld(){
        return ResponseEntity.ok(new Response<>("Hello World!", new ArrayList<>()));
    }

    @GetMapping("/t2")
    public ResponseEntity<Response<String>> xdd(){
        return ResponseEntity.ok(new Response<>("xdd", new ArrayList<>()));
    }
}
