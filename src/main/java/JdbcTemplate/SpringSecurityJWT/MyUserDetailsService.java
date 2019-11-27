package JdbcTemplate.SpringSecurityJWT;
import JdbcTemplate.SpringSecurityJWT.Repository.UserRepository;
import JdbcTemplate.SpringSecurityJWT.models.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationRequest authenticationRequest;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

       List<AuthenticationRequest> list=userRepository.checkusername(s);
       String username=list.get(0).getUsername();
        String password= list.get(0).getPassword();
        System.out.println("username "+username + "password  "+password);
        return new User(username,password, new ArrayList<>());
    }
}