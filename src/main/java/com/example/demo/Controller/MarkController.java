package com.example.demo.Controller;



import com.example.demo.Model.Mark;
import com.example.demo.Service.MarkService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/mark")
@RequiredArgsConstructor
@Slf4j
public class MarkController {
    private final MarkService markService;
    @GetMapping("/getMarks")
    public List<Mark> getMarks(){
        return markService.getMarks();
    }

    @GetMapping("/getMarkById/{id}")
    public Optional<Mark> getMarkById(@PathVariable("id") long id){
        return markService.getMarkById(id);
    }


    @PostMapping("/addMark")
    public ResponseEntity<Map<String , String>> addMark(@RequestBody AddToMarkBody body){
        return markService.addMark(body.getMarkName(),body.getTypeId());
    }

    @DeleteMapping("/deleteMark/{idType}/{idMark}")
    public ResponseEntity<Map<String , Object>> deleteMark(@PathVariable("idType") long idType , @PathVariable("idMark")long idMark){
        return ResponseEntity.ok().body(markService.deleteMark(idType,idMark)) ;
    }
    @PutMapping("/updateName/{idMark}/{newName}")
    public ResponseEntity<Map<String , Object>> updateMark(@PathVariable("idMark") long idMark , @PathVariable("newName") String newName){
        return ResponseEntity.ok().body(markService.updateMark(idMark , newName));
    }

}
@Data
class AddToMarkBody {
    private String markName ;
    private long typeId;
}


