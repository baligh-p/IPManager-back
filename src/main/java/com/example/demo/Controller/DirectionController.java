package com.example.demo.Controller;


import com.example.demo.Model.Direction;
import com.example.demo.Service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dir")
@RequiredArgsConstructor
@Slf4j
public class DirectionController {

    private final DirectionService directionService;

    @GetMapping("/getDir")
    public ResponseEntity<List<Direction>> getDirections(){
        return ResponseEntity.ok().body(directionService.getDirections());
    }

    @PostMapping("/addDir")
    public ResponseEntity<Map<String , Object>> getDirections(@RequestBody Direction dir){
        return ResponseEntity.ok().body(directionService.addDirection(dir));
    }
    @PutMapping("/updateName/{idDirection}/{name}")
    public ResponseEntity<Map<String , Object>>updateDirectionName(@PathVariable("name") String name,@PathVariable("idDirection") long idDirection){
        return ResponseEntity.ok().body(directionService.updateDirectionName(idDirection,name));
    }
    @DeleteMapping("/deleteDirection/{directionId}")
    public ResponseEntity<Map<String , Object>> deleteDirectionAndHerIps(@PathVariable("directionId") long directionId){
        return ResponseEntity.ok().body(directionService.deleteDirection(directionId));
    }
}
