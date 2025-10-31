package com.portfolio.ecommerce.domain.user.repository;

import com.portfolio.ecommerce.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
