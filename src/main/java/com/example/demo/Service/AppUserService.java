package com.example.demo.Service;

import com.example.demo.Model.AppUser;
import com.example.demo.Repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class AppUserService implements UserDetailsService {

    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username.strip());
        if(user == null)
        {
            throw new UsernameNotFoundException("user not found");
        }
        else{
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole()));
            return new User(user.getUsername().strip(),user.getPassword(),authorities);
        }
    }
    /*username already used exception*/
    public AppUser saveUser(AppUser user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername().strip());
        return userRepository.save(user);
    }

    public List<AppUser> getUsers(){
        return userRepository.findAll();
    }

    /*not found exception*/
    public Optional<AppUser> getUser(long userId){
        return userRepository.findById(userId);
    }
    public AppUser getUserByUsername(String username){
        return userRepository.findByUsername(username.strip());
    }

    public Map<String , Object > updatePassword(long userId , String oldPwd,String newPwd ){
        Map<String , Object > response = new HashMap<>();
        Optional<AppUser> appUser = userRepository.findById(userId);
        log.info(appUser.isPresent()+"");
        if(appUser.isPresent()&&passwordEncoder.matches(oldPwd , appUser.get().getPassword()))
        {
            appUser.get().setPassword(passwordEncoder.encode(newPwd));
            response.put("success",true);
        }
        else
        {
            response.put("success",false);
        }
        return response;
    }
}
