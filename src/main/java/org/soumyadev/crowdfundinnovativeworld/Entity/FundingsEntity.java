package org.soumyadev.crowdfundinnovativeworld.Entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="fundings")
public class FundingsEntity implements Serializable {
    public static final long serialVersionID = 1L;
    @Id
    @Column(name = "fund_id",unique = true, nullable = false)
    @SequenceGenerator(name = "FUND_ID_GENERATOR", sequenceName = "fundings_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FUND_ID_GENERATOR")
    private Long fundId;
    @Column(name = "amount")
    private long amount;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "project_id" ,referencedColumnName = "project_id",nullable = false)
    private ProjectsEntity projectsEntity;

}