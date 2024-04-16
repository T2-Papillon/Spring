package com.boogle.papplan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@CrossOrigin("http://localhost:5173/")
public class HomeController {

  @GetMapping
  public ResponseEntity<String> getUsers() {
    return ResponseEntity.ok("OK");
  }

  @GetMapping("test")
  public ResponseEntity<String> getPage() {
    return ResponseEntity.ok("TEST OK");
  }

}
