package com.bet99.report.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AccountTranReportDTO {

    private Long id;
    private Integer accountId;
    private Timestamp dateTime;
    private String tranType;
    private String platformTranId;
    private String gameTranId;
    private String gameId;
    private BigDecimal amount;
    private BigDecimal balance;

    public AccountTranReportDTO(Long id, Integer accountId, Timestamp dateTime,
                                String tranType, String platformTranId,
                                String gameTranId, String gameId,
                                BigDecimal amount, BigDecimal balance) {
        this.id = id;
        this.accountId = accountId;
        this.dateTime = dateTime;
        this.tranType = tranType;
        this.platformTranId = platformTranId;
        this.gameTranId = gameTranId;
        this.gameId = gameId;
        this.amount = amount;
        this.balance = balance;
    }

    public Long getId() { return id; }
    public Integer getAccountId() { return accountId; }
    public Timestamp getDateTime() { return dateTime; }
    public String getTranType() { return tranType; }
    public String getPlatformTranId() { return platformTranId; }
    public String getGameTranId() { return gameTranId; }
    public String getGameId() { return gameId; }
    public BigDecimal getAmount() { return amount; }
    public BigDecimal getBalance() { return balance; }
}