package com.example.demo.Controller;


import com.example.demo.Model.History;
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

    @PostMapping("/addIP/{idUser}")
     public ResponseEntity<Map<String , Object>> addIP(@PathVariable("idUser") long idUser , @RequestBody IpBody ip){
        Optional<Mark> mark =markService.getMarkById(ip.getIdMark());
        Optional<Type> type =typeService.getTypeById(ip.getIdType());
        IPAddress newIp;
        if(!mark.isEmpty()){
            newIp = new IPAddress(0 , ip.getAddress(),
                    ip.getDirection(),ip.getBureau(),ip.getNoms(),type.get(),mark.get(),null);
        }
        else
        {
            newIp = new IPAddress(0 , ip.getAddress(),
                    ip.getDirection(),ip.getBureau(),ip.getNoms(),type.get(),null,null);
        }
        return ResponseEntity.ok().body(ipAddressService.addIp(newIp,idUser));
    }

    @PutMapping("/updateIP")
    public ResponseEntity<Map<String , Object>> updateIp(@RequestBody UpdateIpBody ip){
        return ResponseEntity.ok().body(ipAddressService.updateIp(ip));
    }


    @DeleteMapping("/deleteIP/{idUser}/{id}")
    public ResponseEntity<Map<String , Boolean>> deleteIp(@PathVariable("idUser") long idUser ,@PathVariable("id") long id )
    {
        return ResponseEntity.ok().body(ipAddressService.deleteIp(id,idUser));
    }


    @GetMapping("/getHistorys")
    public ResponseEntity<List<History>> getHistorys(){
        return ResponseEntity.ok().body(ipAddressService.getHistorys());
    }
}

