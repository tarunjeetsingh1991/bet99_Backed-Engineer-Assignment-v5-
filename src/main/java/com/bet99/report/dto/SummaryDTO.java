package com.bet99.report.dto;

import java.math.BigDecimal;

public class SummaryDTO {

    private BigDecimal totalBet;
    private BigDecimal totalWin;
    private BigDecimal net;

    public SummaryDTO(BigDecimal totalBet, BigDecimal totalWin, BigDecimal net) {
        this.totalBet = totalBet;
        this.totalWin = totalWin;
        this.net = net;
    }

    public BigDecimal getTotalBet() { return totalBet; }
    public BigDecimal getTotalWin() { return totalWin; }
    public BigDecimal getNet() { return net; }
}