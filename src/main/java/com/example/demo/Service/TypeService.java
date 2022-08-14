package com.example.demo.Service;

import com.example.demo.Model.Type;
import com.example.demo.Repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TypeService {

    private final TypeRepository typeRepository;

    public Map<String, Boolean> addType(Type type){
        Map<String , Boolean> response= new HashMap<>();
        boolean exist = typeExist(type.getTypeName());
        response.put("exist" ,exist);
        if(!exist) typeRepository.save(type);
        return response;
    }
    public Boolean typeExist(String name){
        if(typeRepository.findByTypeName(name)==null) return false ;
        else return true ;
    }
    public List<Type> getTypes(){
        return typeRepository.findAll();
    }
    public Optional<Type> getTypeById(long id){
        return typeRepository.findById(id);
    }
}