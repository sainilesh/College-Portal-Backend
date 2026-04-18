package com.example.System.Repository;

import com.example.System.Entity.GoogleAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoogleAccountRepository extends JpaRepository<GoogleAccount, Long> {

    Optional<GoogleAccount> findByEmail(String email);

    Optional<GoogleAccount> findByUserId(Long userId);
}
