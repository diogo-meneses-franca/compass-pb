package br.com.pbcompass.demoparkapi.jwt;

import br.com.pbcompass.demoparkapi.entity.ParkUser;
import br.com.pbcompass.demoparkapi.service.ParkUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final ParkUserService parkUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ParkUser user = parkUserService.findByUsername(username);
        return new JwtUserDetails(user);
    }

    public JwtToken getJwtTokenAuthenticated(String username) throws NoSuchAlgorithmException {
        ParkUser.Role role = parkUserService.findByUsername(username).getRole();
        return JwtUtils.generateJwtToken(username, role.name().substring("ROLE_".length()));
    }
}
