package com.steverado9.Clinic.management.system.repository;

import com.steverado9.Clinic.management.system.entity.User;
import com.steverado9.Clinic.management.system.enums.Role;
import com.steverado9.Clinic.management.system.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
