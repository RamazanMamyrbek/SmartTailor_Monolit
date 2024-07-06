package kg.smarttailor.usersservice.security;

import kg.smarttailor.usersservice.entity.User;
import kg.smarttailor.usersservice.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtUserDetailsService implements UserDetailsService {
    final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userService.getUserByEmail(email);
        if(!user.isPresent()) {
            throw new UsernameNotFoundException("User is not found with email: " + email);
        }
        return new JwtUserDetails(user.get());
    }
}
