package com.example.demo.Service;

import com.example.demo.Model.AppUser;
import com.example.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class AppUserService implements UserDetailsService {

    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);
        if(user == null)
        {
            throw new UsernameNotFoundException("user not found");
        }
        else{
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole()));
            return new User(user.getUsername(),user.getPassword(),authorities);
        }
    }
    /*username already used exception*/
    public AppUser saveUser(AppUser user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
        return userRepository.findByUsername(username);
    }
}
