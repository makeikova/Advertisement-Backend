package by.bsu.advertisement.service.controller;

import by.bsu.advertisement.service.exception.jwt.JWTTokenMissingException;
import by.bsu.advertisement.service.model.Person;
import by.bsu.advertisement.service.model.PersonRole;
import by.bsu.advertisement.service.model.dto.PersonDto;
import by.bsu.advertisement.service.model.request.CreateNewPersonRequest;
import by.bsu.advertisement.service.model.request.CreateNewRoleRequest;
import by.bsu.advertisement.service.model.request.RoleToUserRequest;
import by.bsu.advertisement.service.service.PersonService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class PersonController {

    private final Environment environment;
    private final ModelMapper modelMapper;
    private final PersonService personService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<PersonDto> getAll(){
        List<Person> all = personService.getAll();
        return all.stream()
                .map(el -> modelMapper.map(el, PersonDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody CreateNewPersonRequest newUserRequest){
        Person createPerson = modelMapper.map(newUserRequest, Person.class);
        personService.save(createPerson);
    }

    @PostMapping("/role")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRole(@RequestBody CreateNewRoleRequest newRoleRequest){
        PersonRole createRole = modelMapper.map(newRoleRequest, PersonRole.class);
        personService.saveRole(createRole);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void createRole(@RequestBody RoleToUserRequest newRoleRequest){
        personService.addRole(newRoleRequest.getUsername(), newRoleRequest.getName());
    }

    @GetMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String jwtSecret = environment.getProperty("jwt.secret.word");
        int jwtAccessTokenExpirationTime = Integer.parseInt(Objects.requireNonNull(environment.getProperty("jwt.expiration.access.token")));
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());

                Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);

                String username = decodedJWT.getSubject();
                Person person = personService.findByUsername(username);

                String accessToken = JWT.create()
                        .withSubject(person.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + jwtAccessTokenExpirationTime * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", person.getRoles().stream().map(PersonRole::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                tokens.put("username", person.getUsername());
                tokens.put("roles", person.getRoles().toString());

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            }catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new JWTTokenMissingException("Refresh Token is missing");
        }
    }

    @DeleteMapping("{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void toggleBlockStatusById(@PathVariable Long userId) {
        personService.toggleBlockStatusById(userId);
    }

    @GetMapping("{username}")
    public PersonDto getUserById(@PathVariable String username) {
        Person person = personService.findByUsername(username);

        return modelMapper.map(person, PersonDto.class);
    }
}
