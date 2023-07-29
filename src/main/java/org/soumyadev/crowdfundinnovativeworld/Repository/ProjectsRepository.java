package org.soumyadev.crowdfundinnovativeworld.Repository;

import org.soumyadev.crowdfundinnovativeworld.Entity.ProjectsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectsRepository extends JpaRepository<ProjectsEntity,Long> {
}
