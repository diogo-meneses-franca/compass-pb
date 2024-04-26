package br.com.pbcompass.demoparkapi.jwt;

import br.com.pbcompass.demoparkapi.entity.ParkUser;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class JwtUserDetails extends User {

    private ParkUser parkUser;

    public JwtUserDetails(ParkUser parkUser) {
        super(parkUser.getUsername(), parkUser.getPassword(), AuthorityUtils.createAuthorityList(parkUser.getRole().name()));
        this.parkUser = parkUser;
    }

    public Long geId() {
        return parkUser.getId();
    }

    public String getRole(){
        return parkUser.getRole().name();
    }
}
