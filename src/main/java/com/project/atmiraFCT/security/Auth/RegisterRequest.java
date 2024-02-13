package com.project.atmiraFCT.security.Auth;


import com.project.atmiraFCT.model.domain.*;
import com.project.atmiraFCT.security.User.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

        private String id_alias;


        private String email;


        private Boolean isActive;


        private Date relaseDate;


        private Integer hours;


        private Boolean guards;


        private Boolean expense;


        private String name;


        private String surname;


        private String password;


        private String responsible;


        private WorkPlace workPlace;


}

