package com.example.demo.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.Model.AppUser;
import com.example.demo.Service.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final AppUserService appUserService ;

    @GetMapping("/user/getUsers")
    public ResponseEntity<List<AppUser>> getUsers(){
        return ResponseEntity.ok().body(appUserService.getUsers()) ;
    }

    @GetMapping("/user/getUser/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") long userId){
        Optional<AppUser> user = appUserService.getUser(userId);
        if(user.isEmpty()){
            Map<String,String> map = new HashMap<>();
            map.put("success","false");
            map.put("error" ,"user not found");
            return ResponseEntity.ok().body(map);
        }
        else
        {
            Map<String , Object > response = new HashMap<>();
            response.put("id",user.get().getId());
            response.put("username",user.get().getUsername());
            response.put("role",user.get().getRole());
            return ResponseEntity.ok().body(response);
        }
    }
    @GetMapping("/user/getUserByName/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username){
        AppUser user =appUserService.getUserByUsername(username.strip());
        if(user != null)
        {
            Map<String , Object > response = new HashMap<>();
            response.put("id",user.getId());
            response.put("username",user.getUsername());
            response.put("role",user.getRole());
            return ResponseEntity.ok().body(response);
        }
        else
        {
            Map<String,String> map = new HashMap<>();
            map.put("success","false");
            map.put("error" ,"user not found");
            return ResponseEntity.ok().body(map);
        }
    }


    @PostMapping("/saveUser")
    public ResponseEntity<?> saveUser(@RequestBody AppUser user) {
        AppUser oldUser = appUserService.getUserByUsername(user.getUsername().strip());
        if(oldUser == null){
            return ResponseEntity.ok().body(appUserService.saveUser(user));
        }
        else
        {
            Map<String,String> map = new HashMap<>();
            map.put("success","false");
            map.put("error" ,"username already used");
            return ResponseEntity.ok().body(map);
        }
    }
    @GetMapping("/tkn/refresh")
    public void RefreshToken(HttpServletRequest request , HttpServletResponse response) throws IOException {
        String authorisationHeader = request.getHeader(AUTHORIZATION) ;
        if(authorisationHeader != null && authorisationHeader.startsWith("Bearer "))
        {
            try{
                String refresh_token = authorisationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build() ;
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                AppUser user = appUserService.getUserByUsername(username.strip());
                List<String> roles = new ArrayList<>() ;
                roles.add(user.getRole());
                String access_token = JWT.create()
                        .withSubject(user.getUsername().strip())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", roles)
                        .sign(algorithm);

                Map <String , String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);
            }catch (Exception exception){
                Map <String , String> error = new HashMap<>();
                error.put("type","token");
                error.put("error",exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }
        }
        else
        {
            Map <String , String> error = new HashMap<>();
            error.put("error","refresh token missing");
            error.put("type","token");
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),error);
        }
    }

}