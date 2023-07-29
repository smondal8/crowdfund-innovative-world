package org.soumyadev.crowdfundinnovativeworld.Repository;

import org.soumyadev.crowdfundinnovativeworld.Entity.CredentialsEntity;
import org.soumyadev.crowdfundinnovativeworld.Entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
}
