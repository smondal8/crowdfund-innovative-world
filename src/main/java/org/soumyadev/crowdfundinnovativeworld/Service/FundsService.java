package org.soumyadev.crowdfundinnovativeworld.Service;

import org.soumyadev.crowdfundinnovativeworld.Entity.FundingsEntity;
import org.soumyadev.crowdfundinnovativeworld.Entity.ProjectsEntity;
import org.soumyadev.crowdfundinnovativeworld.Repository.FundsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FundsService {
    @Autowired
    FundsRepository fundsRepository;

    public void makeFund(ProjectsEntity projectEntity, Long amount) {
        FundingsEntity fundingsEntity = new FundingsEntity();
        fundingsEntity.setProjectsEntity(projectEntity);
        fundingsEntity.setAmount(amount);
        fundsRepository.save(fundingsEntity);
    }
}
