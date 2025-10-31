package com.portfolio.ecommerce.domain.user.controller;

import com.portfolio.ecommerce.common.response.ApiResponse;
import com.portfolio.ecommerce.domain.user.dto.UserRequest;
import com.portfolio.ecommerce.domain.user.entity.User;
import com.portfolio.ecommerce.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public ApiResponse<User> create(@Valid @RequestBody UserRequest request) {
    return ApiResponse.success(userService.createUser(request));
  }
}
