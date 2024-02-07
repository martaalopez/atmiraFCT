package com.project.atmiraFCT.security;

import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private ColaboratorRepository colaboratorRepository;

    @Override
    public UserDetails loadUserByUsername(String email )throws UsernameNotFoundException {
       Colaborator colaborator= colaboratorRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email : " + email));
    return new UserDetailsImpl(colaborator);
    }

}
