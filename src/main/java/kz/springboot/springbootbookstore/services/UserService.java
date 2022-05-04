package kz.springboot.springbootbookstore.services;

import kz.springboot.springbootbookstore.entities.Users;
import kz.springboot.springbootbookstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Users getUserByEmail(String email);

    Users createUser(Users user);

    Users saveUser(Users user);
}
