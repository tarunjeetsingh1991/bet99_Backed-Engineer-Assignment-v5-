package com.bet99.report.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "account_tran")
public class AccountTran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ACCOUNT_ID")
    private Integer accountId;

    @Column(name = "DATETIME")
    private Timestamp datetime;

    @Column(name = "TRAN_TYPE")
    private String tranType;

    @Column(name = "PLATFORM_TRAN_ID")
    private String platformTranId;

    @Column(name = "GAME_TRAN_ID")
    private String gameTranId;

    @Column(name = "GAME_ID")
    private String gameId;

    @Column(name = "AMOUNT_REAL")
    private BigDecimal amountReal;

    @Column(name = "BALANCE_REAL")
    private BigDecimal balanceReal;

    @Column(name = "AMOUNT_RELEASED_BONUS")
    private BigDecimal amountReleasedBonus;

    @Column(name = "AMOUNT_PLAYABLE_BONUS")
    private BigDecimal amountPlayableBonus;

    @Column(name = "AMOUNT_UNDERFLOW")
    private BigDecimal amountUnderflow;

    @Column(name = "AMOUNT_FREE_BET")
    private BigDecimal amountFreeBet;

    @Column(name = "BALANCE_RELEASED_BONUS")
    private BigDecimal balanceReleasedBonus;

    @Column(name = "BALANCE_PLAYABLE_BONUS")
    private BigDecimal balancePlayableBonus;

    public Long getId() { return id; }
    public Integer getAccountId() { return accountId; }
    public Timestamp getDatetime() { return datetime; }
    public String getTranType() { return tranType; }
    public String getPlatformTranId() { return platformTranId; }
    public String getGameTranId() { return gameTranId; }
    public String getGameId() { return gameId; }
    public BigDecimal getAmountReal() { return amountReal; }
    public BigDecimal getBalanceReal() { return balanceReal; }
    public BigDecimal getAmountReleasedBonus() { return amountReleasedBonus; }
    public BigDecimal getAmountPlayableBonus() { return amountPlayableBonus; }
    public BigDecimal getAmountUnderflow() { return amountUnderflow; }
    public BigDecimal getAmountFreeBet() { return amountFreeBet; }
    public BigDecimal getBalanceReleasedBonus() { return balanceReleasedBonus; }
    public BigDecimal getBalancePlayableBonus() { return balancePlayableBonus; }
}