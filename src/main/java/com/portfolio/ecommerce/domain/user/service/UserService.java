package com.portfolio.ecommerce.domain.user.service;

import com.portfolio.ecommerce.domain.user.dto.UserRequest;
import com.portfolio.ecommerce.domain.user.entity.User;
import com.portfolio.ecommerce.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User createUser(UserRequest request) {

    User user = User.builder()
        .name(request.getName())
        .email(request.getEmail())
        .password(request.getPassword())
        .build();

    userRepository.save(user);
    return user;
  }
}
