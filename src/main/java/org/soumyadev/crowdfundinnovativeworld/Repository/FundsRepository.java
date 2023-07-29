package org.soumyadev.crowdfundinnovativeworld.Repository;

import org.soumyadev.crowdfundinnovativeworld.Entity.FundingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundsRepository extends JpaRepository<FundingsEntity,Long> {
}
