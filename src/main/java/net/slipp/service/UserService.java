package net.slipp.service;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.slipp.UnAuthenticationException;
import net.slipp.domain.User;
import net.slipp.domain.UserRepository;

@Service("userService")
public class UserService {
    @Resource(name = "userRepository")
    private UserRepository userRepository;

    public User login(String userId, String password) throws UnAuthenticationException {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (!maybeUser.isPresent()) {
            throw new UnAuthenticationException();
        }

        User user = maybeUser.get();
        if (!user.matchPassword(password)) {
            throw new UnAuthenticationException();
        }

        return user;
    }
}
