package blueship.vehicle.service.impl;

import blueship.vehicle.dto.UserDto;
import blueship.vehicle.entity.User;
import blueship.vehicle.repository.UserRepository;
import blueship.vehicle.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserRepository userRepository;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getActiveUser() {
        List<User> users = userRepository.findAllByStatus(1);
        List<UserDto> rtv = new ArrayList<>();
        if (users != null) {
            users.stream().forEach(user -> {
                UserDto userDto = new UserDto();
                BeanUtils.copyProperties(user, userDto);
                rtv.add(userDto);
            });
        }
        logger.info("UserServiceImpl#getActiveUser --- return data size:{}", rtv.size());
        return rtv;
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        logger.info("UserServiceImpl#saveUser --- Before save: UserDto: {}", userDto);
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
//        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user = userRepository.save(user);
        BeanUtils.copyProperties(user, userDto);
        logger.info("UserServiceImpl#saveUser --- After save: UserDto: {}", userDto);
        return userDto;
    }
}
