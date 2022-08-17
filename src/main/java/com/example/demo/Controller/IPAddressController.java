package com.example.demo.Controller;


import com.example.demo.Model.IPAddress;
import com.example.demo.Model.Mark;
import com.example.demo.Model.Type;
import com.example.demo.RequestBody.IpBody;
import com.example.demo.RequestBody.UpdateIpBody;
import com.example.demo.Service.IPAddressService;
import com.example.demo.Service.MarkService;
import com.example.demo.Service.TypeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/ip")
@RequiredArgsConstructor
@Slf4j
public class IPAddressController {
    private final IPAddressService ipAddressService;
    private final MarkService markService;
    private final TypeService typeService;

    @GetMapping("/getIPs/{direction}")
    public ResponseEntity<List<IPAddress>> getIPsByDirection(@PathVariable("direction") String direction) {
        return ResponseEntity.ok().body(ipAddressService.getIPByDirection(direction));
    }

    @GetMapping("/getIPByAddress/{address}")
    public ResponseEntity<IPAddress> getByAddress(@PathVariable("address") String address) {
        return ResponseEntity.ok().body(ipAddressService.getIPByAddress(address));
    }

    @PostMapping("/addIP")
     public ResponseEntity<Map<String , Object>> addIP(@RequestBody IpBody ip){
        Optional<Mark> mark =markService.getMarkById(ip.getIdMark());
        Optional<Type> type =typeService.getTypeById(ip.getIdType());
        IPAddress newIp;
        if(!mark.isEmpty()){
            newIp = new IPAddress(0 , ip.getAddress(),
                    ip.getDirection(),ip.getBureau(),ip.getNoms(),type.get(),mark.get(),new ArrayList<>(),null);
        }
        else
        {
            newIp = new IPAddress(0 , ip.getAddress(),
                    ip.getDirection(),ip.getBureau(),ip.getNoms(),type.get(),null,new ArrayList<>(),null);
        }
        return ResponseEntity.ok().body(ipAddressService.addIp(newIp));
    }

    @PutMapping("/updateIP")
    public ResponseEntity<Map<String , Object>> updateIp(@RequestBody UpdateIpBody ip){
        return ResponseEntity.ok().body(ipAddressService.updateIp(ip));
    }


    @DeleteMapping("/deleteIP/{id}")
    public ResponseEntity<Map<String , Boolean>> deleteIp(@PathVariable long id)
    {
        return ResponseEntity.ok().body(ipAddressService.deleteIp(id));
    }
}

