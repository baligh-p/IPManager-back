package com.example.demo.Service;

import com.example.demo.Model.Mark;
import com.example.demo.Model.Type;
import com.example.demo.Repository.MarkRepository;
import com.example.demo.Repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MarkService {
    private final MarkRepository markRepository ;
    private final TypeRepository typeRepository;
    public Mark getMarkByName(String name){
        return markRepository.findByMarkName(name);
    }


    public ResponseEntity<Map<String , String>> addMark(String markName , long typeId){
        Optional<Type> type = typeRepository.findById(typeId);
        Map<String , String > response = new HashMap<>();
        type.ifPresentOrElse(value->{
            Mark mark = getMarkByName(markName);
            if(mark != null)
            {
                int len = value.getMarks().stream().filter((element)->element
                        .getMarkName().toUpperCase().equals(markName.toUpperCase())).toArray().length;
                if(len == 0)
                {
                    value.getMarks().add(mark);
                    response.put("success" , "true");
                }
                else
                {
                    response.put("success" , "true");
                    response.put("message", "exist");
                }
            }
            else
            {
                Mark newMark = new Mark(0 , markName   ,null);
                value.getMarks().add(newMark);
                markRepository.save(newMark);
                response.put("success" , "true");
                response.put("message", "new mark");
            }
        },()->{
            response.put("success" , "false");
            response.put("error" , "no type found");
        });
        return ResponseEntity.ok().body(response);
    }
    public List<Mark> getMarks(){
        return markRepository.findAll();
    }
    public Optional<Mark> getMarkById(long id){
        return markRepository.findById(id);
    }

    public Map<String , Object> deleteMark(long idType ,long idMark ){
        Map<String , Object> response = new HashMap<>();
        Optional<Type> type = typeRepository.findById(idType);
        type.ifPresentOrElse((element)->{
            if(element.getMarks()==null)
            {
                response.put("success",false);
                response.put("error_message","mark not found");
            }
            else
            {
                element.setMarks(element.getMarks().stream().filter((mark)->mark.getIdMark()!=idMark).collect(Collectors.toList()));
                response.put("success",true);
            }
        },()->{
            response.put("success",false);
            response.put("error_message","type not found");
        });
        return response;
    }
    
    public Map<String , Object> updateMark(long idMark , String newName){
        Map<String , Object> response = new HashMap<>() ;
        Optional<Mark> mark = markRepository.findById(idMark);
        mark.ifPresentOrElse((element)->{
            response.put("success",true);
            element.setMarkName(newName);
        },()->{
            response.put("success",false);
        });

        return response;
    }
    
    
}
