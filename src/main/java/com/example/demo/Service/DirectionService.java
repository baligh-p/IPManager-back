package com.example.demo.Service;



import com.example.demo.Model.Direction;
import com.example.demo.Model.History;
import com.example.demo.Model.IPAddress;
import com.example.demo.Repository.DirectionRepository;
import com.example.demo.Repository.HistoryRepository;
import com.example.demo.Repository.IPAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DirectionService {
    private final DirectionRepository directionRepository;
    private final IPAddressService ipAddressService ;
    private final HistoryRepository historyRepository;
    private final IPAddressRepository ipAddressRepository;
    public List<Direction> getDirections(){
        return directionRepository.findAll();
    }

    public Map<String , Object> addDirection(Direction direction){
        Map<String , Object> map = new HashMap<>();
        if(directionRepository.findByDirectionName(direction.getDirectionName())==null){
            directionRepository.save(direction);
            map.put("success",  true);
        }
        else
        {
            map.put("success",  false);
        }
        return map;
    }
    public Map<String , Object> updateDirectionName(long idDirection , String directionName){
        Optional<Direction> direction = directionRepository.findById(idDirection);
        Map<String , Object > response = new HashMap<>();
        direction.ifPresentOrElse(element->{
            response.put("success",true);
            List<IPAddress> ips = ipAddressRepository.findAll();
            ips.forEach(ip->{
                if(ip.getDirection().equals(direction.get().getDirectionName()))
                {
                    ip.setDirection(directionName);
                }
            });
            List<History> historys = historyRepository.findAll();
            historys.forEach(history->{
                if(history.getDirection().equals(direction.get().getDirectionName()))
                {
                    history.setDirection(directionName);
                }
            });

            element.setDirectionName(directionName);
            },()->{
            response.put("success" , false );
        });
        return response;
    }
    public Map<String , Object> deleteDirection(long idDirection){
        Map<String , Object> response = new HashMap<>();
        Optional<Direction> direction = directionRepository.findById(idDirection);
        direction.ifPresentOrElse(element->{
            response.put("success",true);
            ipAddressService.deleteIpByDirectionName(element.getDirectionName());
            directionRepository.deleteById(idDirection);
        },()->{
            response.put("success",false);
        });
        return response;
    }
}
