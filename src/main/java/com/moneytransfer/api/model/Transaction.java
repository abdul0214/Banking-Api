package com.moneytransfer.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "public", name = "transactions")
public class Transaction extends BaseEntity{

    @Column(name="time")
    @Builder.Default
    private OffsetDateTime time = OffsetDateTime.now();

    @Column(name="amount")
    private BigDecimal amount;

    @Column(name="details")
    private String details;

    @Column(name="reference_no")
    private Long referenceNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id", nullable = false)
    @ToString.Exclude
    private Account sourceAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_account_id", nullable = false)
    @ToString.Exclude
    private Account targetAccount;

}