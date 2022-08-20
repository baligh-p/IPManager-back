package com.example.demo.Service;



import com.example.demo.Model.Direction;
import com.example.demo.Repository.DirectionRepository;
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

}
