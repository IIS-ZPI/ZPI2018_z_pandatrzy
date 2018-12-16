package pl.panda.trzy.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientRestController {

    @GetMapping("/")
    public String index(){
        return "Hello!";
    }
}
