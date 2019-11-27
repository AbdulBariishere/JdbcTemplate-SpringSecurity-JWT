package JdbcTemplate.SpringSecurityJWT.Repository;

import JdbcTemplate.SpringSecurityJWT.models.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository extends AuthenticationRequest {

    @Autowired
    JdbcTemplate jdbcTemplate;
public List checkusername(String username){

    String query="SELECT username as username, password as password from user where username='"+(username)+"'";
    List<AuthenticationRequest> list =jdbcTemplate.query(query, new BeanPropertyRowMapper<>(AuthenticationRequest.class));
    return list;

}
}

