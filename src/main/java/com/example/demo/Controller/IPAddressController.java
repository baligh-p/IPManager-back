package com.example.demo.Controller;


import com.example.demo.Service.IPAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ip")
@RequiredArgsConstructor
@Slf4j
public class IPAddressController {
    private final IPAddressService ipAddressService  ;



}
