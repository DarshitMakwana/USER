package com.userservice.userservice.service.serviceImpl;

import com.userservice.userservice.dto.UserRequestDto;
import com.userservice.userservice.dto.UserResponseDto;
import com.userservice.userservice.exception.ResourceNotFound;
import com.userservice.userservice.model.Address;
import com.userservice.userservice.model.Role;
import com.userservice.userservice.model.User;
import com.userservice.userservice.repository.RoleRepository;
import com.userservice.userservice.repository.UserRepository;
import com.userservice.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        try {
            User user = populateUserEntity(userRequestDto);
            User savedUser = userRepository.save(user);
            return userEntityToUserResponseDto(savedUser);
        } catch (Exception e) {
            throw new RuntimeException("Issue happened while saving User ");
        }
    }

    private UserResponseDto userEntityToUserResponseDto(User savedUser) {

        return UserResponseDto.builder()
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .roleName(savedUser.getRole().getRoleName())
                .street(savedUser.getAddress().getStreet())
                .city(savedUser.getAddress().getCity())
                .zip(savedUser.getAddress().getZip())
                .state(savedUser.getAddress().getState())
                .build();
    }

    private User populateUserEntity(UserRequestDto userRequestDto) {

        Optional<Role> optionalRole = roleRepository.findByRoleName(userRequestDto.getRoleName());

        if (optionalRole.isPresent()) {
            User user = new User();
            Role role = optionalRole.get();
            user.setEmail(userRequestDto.getEmail());
            user.setFirstName(userRequestDto.getFirstName());
            user.setLastName(userRequestDto.getLastName());
            user.setAddress(Address.builder()
                    .street(userRequestDto.getStreet())
                    .city(userRequestDto.getCity())
                    .state(userRequestDto.getState())
                    .zip(userRequestDto.getZip())
                    .build());
            user.setActive(Boolean.TRUE);
            user.setRole(role);
            return user;
        } else {
            throw new ResourceNotFound(userRequestDto.getRoleName() + " Role Does not exits in system");
        }
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return userEntityToUserResponseDto(user);
        } else {
            throw new ResourceNotFound("Their is No user with this id " + id);
        }
    }

    @Override
    public String deleteUserByEmail(String email) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getActive()) {
                user.setActive(Boolean.FALSE);
                User deactivedUser = userRepository.save(user);
                if (!deactivedUser.getActive()) {
                    return email + " is Deactived";
                } else {
                    throw new RuntimeException("Some issue happend while deactiving the user " + email);
                }
            } else {
                throw new RuntimeException("The user " + email + " is Already deactived");
            }
        } else {
            throw new ResourceNotFound("User with email : " + email + " is Not present");
        }
    }


    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserResponseDto updateUser(UserRequestDto userRequestDto) {

        Optional<User> optionalUser = userRepository.findByEmail(userRequestDto.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(userRequestDto.getFirstName() != null ? userRequestDto.getFirstName() : user.getFirstName());
            user.setLastName(userRequestDto.getLastName() != null ? userRequestDto.getLastName() : user.getLastName());
            user.setEmail(userRequestDto.getEmail() != null ? userRequestDto.getEmail() : user.getEmail());

            user.getAddress().setZip(userRequestDto.getZip() != null ? userRequestDto.getZip() : user.getAddress().getZip());
            user.getAddress().setCity(userRequestDto.getCity() != null ? userRequestDto.getCity() : user.getAddress().getCity());
            user.getAddress().setState(userRequestDto.getState() != null ? userRequestDto.getState() : user.getAddress().getState());
            user.getAddress().setStreet(userRequestDto.getStreet() != null ? userRequestDto.getStreet() : user.getAddress().getStreet());

            User updatedUser = userRepository.save(user);

            return userEntityToUserResponseDto(updatedUser);
        } else {
            throw new ResourceNotFound("User with email " + userRequestDto.getEmail() + " not found");
        }
    }

}
