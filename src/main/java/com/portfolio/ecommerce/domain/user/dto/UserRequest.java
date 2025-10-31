package com.portfolio.ecommerce.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {

  @NotBlank(message = "이름은 필수값입니다.")
  String name;
  @NotBlank(message = "비밀번호는 필수값입니다.")
  String password;
  @NotBlank(message = "이메일은 필수값입니다.")
  @Email(message = "이메일 형식으로 작성해주세요.")
  String email;
}
