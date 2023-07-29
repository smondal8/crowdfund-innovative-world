package org.soumyadev.crowdfundinnovativeworld.Repository;

import org.soumyadev.crowdfundinnovativeworld.Entity.CredentialsEntity;
import org.soumyadev.crowdfundinnovativeworld.Entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
    Optional<UsersEntity> findByUserId(String id);
}
