package com.example.demo.Service;

import com.example.demo.Model.IPAddress;
import com.example.demo.Model.Mark;
import com.example.demo.Model.Type;
import com.example.demo.Repository.IPAddressRepository;
import com.example.demo.RequestBody.UpdateIpBody;
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
public class IPAddressService {

    private final IPAddressRepository ipAddressRepository;
    private final TypeService typeService;
    private final MarkService markService;
    public List<IPAddress> getIPs(){
        return ipAddressRepository.findAll();
    }

    public Optional<IPAddress> getIPById(long id){
        return ipAddressRepository.findById(id);
    }

    public IPAddress getIPByAddress(String name){
        return ipAddressRepository.findByAddress(name);
    }



    /*add to history*/
    public Map<String , Object> addIp(IPAddress ip){
        Map<String , Object> response = new HashMap<>();
       if(getIPByAddress(ip.getAddress())==null)
       {
           response.put("exist",false);
           response.put("data" , ipAddressRepository.save(ip));
       }
       else
       {
           response.put("exist",true);
       }
       return response;
    }


    public Map<String , Object> updateIp(UpdateIpBody ip){
        Map <String  , Object >  response  = new HashMap<>();
        Optional<IPAddress> oldIp = getIPById(ip.getIdAddress());
        oldIp.ifPresentOrElse(value->{
            if (getIPByAddress(ip.getAddress())==null) {
                if(ip.getAddress() != null)
                {
                    value.setAddress(ip.getAddress());
                }
                if(ip.getBureau() != null) value.setBureau(ip.getBureau());
                if(ip.getNoms() != null) value.setNoms(ip.getNoms());
                if(ip.getDirection() != null) value.setDirection(ip.getDirection());
                if(ip.getIdType()!=0)
                {
                    Optional<Type> type = typeService.getTypeById(ip.getIdType());
                    value.setType(type.get());
                }
                if(ip.getIdMark()!=0)
                {
                    Optional<Mark> mark = markService.getMarkById(ip.getIdMark());
                    value.setMark(mark.get());
                }
                response.put("success" , true);
                response.put("data",oldIp);
            }
            else
            {
                response.put("success" , false);
                response.put("error_message","address used");
            }
        },()->{
            response.put("success" , false);
            response.put("error_message" , "not found");
        });
        return response;
    }


    public Map<String , Boolean> deleteIp(long id){
        Map<String , Boolean> response = new HashMap<>();
        if(getIPById(id).isPresent())
        {
            ipAddressRepository.deleteById(id);
            response.put("success",true);
        }
        else response.put("success",false);
        return response;
    }

}

