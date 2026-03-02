package com.bet99.report.service;

import com.bet99.report.dto.AccountTranReportDTO;
import com.bet99.report.dto.SummaryDTO;
import com.bet99.report.entity.AccountTran;
import com.bet99.report.repository.AccountTranRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private AccountTranRepository repository;

    @Transactional(readOnly = true)
    public List<AccountTranReportDTO> getReport(
            Timestamp start, Timestamp end,
            Integer accountId,
            String tranType,
            String gameId,
            String platformTranId,
            String gameTranId,
            String sortBy,
            String direction,
            int page,
            int size) {

        int offset = (page - 1) * size;

        List<AccountTran> list = repository.findReport(
                start, end, accountId, tranType, gameId, platformTranId, gameTranId, sortBy, direction, offset, size);

        return list.stream().map(a ->
                new AccountTranReportDTO(
                        a.getId(),
                        a.getAccountId(),
                        a.getDatetime(),
                        a.getTranType(),
                        a.getPlatformTranId(),
                        a.getGameTranId(),
                        a.getGameId(),
                        a.getAmountReal(),
                        a.getBalanceReal()
                )).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Long count(
            Timestamp start, Timestamp end,
            Integer accountId,
            String tranType,
            String gameId,
            String platformTranId,
            String gameTranId) {

        return repository.countReport(start, end, accountId, tranType, gameId, platformTranId, gameTranId);
    }
    
    @Transactional(readOnly = true)
    public SummaryDTO getSummary(
            Timestamp start, Timestamp end,
            Integer accountId,
            String tranType,
            String gameId,
            String platformTranId,
            String gameTranId) {

        Object[] result = repository.getSummary(
                start, end, accountId, tranType, gameId, platformTranId, gameTranId);

        BigDecimal totalBet = BigDecimal.ZERO;
        BigDecimal totalWin = BigDecimal.ZERO;

        if (result != null && result.length >= 2) {
            if (result[0] instanceof BigDecimal)
                totalBet = (BigDecimal) result[0];

            if (result[1] instanceof BigDecimal)
                totalWin = (BigDecimal) result[1];
        }

        BigDecimal net = totalWin.subtract(totalBet);

        return new SummaryDTO(totalBet, totalWin, net);
    }
}