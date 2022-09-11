package com.example.demo.Service;

import com.example.demo.Model.IPAddress;
import com.example.demo.Model.Type;
import com.example.demo.Repository.IPAddressRepository;
import com.example.demo.Repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TypeService {

    private final TypeRepository typeRepository;
    private final IPAddressRepository ipAddressRepository;

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
    public Map<String , Object> getStat(){
        Map<String , Object> response =new HashMap<>();
        List<Type> types = typeRepository.findAll();
        List<IPAddress> ips =ipAddressRepository.findAll().stream().filter((element)->element.getType()!=null).collect(Collectors.toList());
        //.stream().filter((element)->element.getMark()!=null&&element.getType()!=null).collect(Collectors.toList());
        types.forEach((type)->{
            List<Map<String , Integer>> lst = new ArrayList<>();
            ips.forEach((ip)->{
                if(ip.getType().getTypeName()==type.getTypeName())
                {
                    List<Map<String , Integer>>  result;
                    if(ip.getMark()!=null)
                    {
                        result = lst.stream().filter((element)->element.containsKey(ip.getMark().getMarkName())).collect(Collectors.toList());
                    }else
                    {
                        result = lst.stream().filter((element)->element.containsKey("null")).collect(Collectors.toList());
                    }
                    if(result.size()!=0){
                        if(ip.getMark()!=null)
                        {
                            result.get(0).put(ip.getMark().getMarkName(),result.get(0).get(ip.getMark().getMarkName())+1);
                        }
                        else
                        {
                            result.get(0).put("null",result.get(0).get("null")+1);
                        }
                    }else
                    {
                        if(ip.getMark()==null)
                        {
                            Map<String , Integer> newMap = new HashMap<>();
                            newMap.put("null",1);
                            lst.add(newMap);
                        }else
                        {
                            Map<String , Integer> newMap = new HashMap<>();
                            newMap.put(ip.getMark().getMarkName(),1);
                            lst.add(newMap);
                        }
                    }
                }
            });
            response.put(type.getTypeName(),lst);
        });

        return response;
    }
}