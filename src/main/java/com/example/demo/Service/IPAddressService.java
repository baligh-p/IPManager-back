package com.example.demo.Service;

import com.example.demo.Model.*;
import com.example.demo.Repository.HistoryRepository;
import com.example.demo.Repository.IPAddressRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.RequestBody.UpdateIpBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
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
    private final UserRepository userRepository;
    private final HistoryRepository historyRepository;



    public Optional<IPAddress> getIPById(long id){
        return ipAddressRepository.findById(id);
    }

    public IPAddress getIPByAddress(String name){
        return ipAddressRepository.findByAddress(name);
    }

    public List<IPAddress> getIPByDirection(String direction){
        return ipAddressRepository.findByDirection(direction);
    }



    /*add to history*/
    public Map<String , Object> addIp(IPAddress ip , long idUser){
        Map<String , Object> response = new HashMap<>();
       if(getIPByAddress(ip.getAddress())==null)
       {
           IPAddress  address = ipAddressRepository.save(ip);
           response.put("exist",false);
           response.put("data" , address);
           addToHistory(address.getIdAddress(),idUser,"add");
       }
       else
       {
           response.put("exist",true);
       }
       return response;
    }


    public Map<String , Object> updateIp(UpdateIpBody ip){
        //will generate mark null error
        Map <String  , Object >  response  = new HashMap<>();
        Optional<IPAddress> oldIp = getIPById(ip.getIdAddress());
        oldIp.ifPresentOrElse(value->{
                    if(ip.getBureau() != null) value.setBureau(ip.getBureau());
                    if(ip.getNoms() != null) value.setNoms(ip.getNoms());
                    Optional<Type> type = typeService.getTypeById(ip.getIdType());
                    value.setType(type.get());
                    Optional<Mark> mark = markService.getMarkById(ip.getIdMark());
                    if(!mark.isEmpty())
                    {
                        value.setMark(mark.get());
                    }
                    else
                    {
                        value.setMark(null);
                    }
                    response.put("success" , true);
                    response.put("data",oldIp);
                    addToHistory(ip.getIdAddress(),ip.getIdUser(),"update");
        },()->{
            response.put("success" , false);
            response.put("error_message" , "not found");
        });
        return response;
    }


    public Map<String , Boolean> deleteIp(long id , long idUser){
        Map<String , Boolean> response = new HashMap<>();
        if(getIPById(id).isPresent())
        {
            addToHistory(id,idUser,"delete");
            ipAddressRepository.deleteById(id);
            response.put("success",true);
        }
        else response.put("success",false);
        return response;
    }


    public void addToHistory(long idAddress, long idUser ,String operation ){
        Optional<AppUser> appUser = userRepository.findById(idUser);
        Optional<IPAddress> ip = ipAddressRepository.findById(idAddress);
        historyRepository.save(new History(0,appUser.get(),operation,
                    ip.get().getAddress(),ip.get().getBureau(),ip.get().getDirection(), null));
    }

    public List<History> getHistorys(){
        return historyRepository.findAll(Sort.by(Sort.Direction.DESC,"createdAt"));
    }

}

