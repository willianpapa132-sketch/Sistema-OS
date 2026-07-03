package jpa.repository.demo.auth.authaplication;


import jakarta.validation.Valid;
import jpa.repository.demo.auth.domain.AuthenticationDTO;
import jpa.repository.demo.auth.domain.LoginResponseDTO;
import jpa.repository.demo.auth.domain.RegistroDTO;
import jpa.repository.demo.auth.domain.Role;
import jpa.repository.demo.auth.domain.User;
import jpa.repository.demo.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationControler {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    TokenService tokenService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data  ) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User)auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping ("/registro")
    public ResponseEntity registro(@RequestBody @Valid RegistroDTO data  ) {
        if(userRepository.findByNome(data.login())!= null){ return ResponseEntity.badRequest().build();}
        String password = passwordEncoder.encode(data.password());
        User newUser = new User(data.login(), password , Role.User);
        userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }
}
