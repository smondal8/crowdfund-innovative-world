package org.soumyadev.crowdfundinnovativeworld.Repository;

import org.soumyadev.crowdfundinnovativeworld.Entity.CredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CredentialRepository extends JpaRepository<CredentialsEntity, Long> {
}
