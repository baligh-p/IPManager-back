package com.example.demo.Controller;



import com.example.demo.Model.Type;
import com.example.demo.Service.TypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/type")
@RequiredArgsConstructor
@Slf4j
public class TypeController {

    private final TypeService typeService;

    @PostMapping("/addType")
    public ResponseEntity<Map<String , Boolean>> addType(@RequestBody Type type){
        return ResponseEntity.ok().body(typeService.addType(type));
    }

    @GetMapping("/getTypes")
    public ResponseEntity<List<Type>> getTypes(){
        return ResponseEntity.ok().body(typeService.getTypes());
    }

    @GetMapping("/getTypeById/{id}")
    public ResponseEntity<Optional<Type>> getTypeById(@PathVariable("id") long id){
         return ResponseEntity.ok().body(typeService.getTypeById(id));
    }
    @GetMapping("/getStat")
    public ResponseEntity<Map<String , Object>> getStat(){

        return ResponseEntity.ok().body(typeService.getStat());
    }
}