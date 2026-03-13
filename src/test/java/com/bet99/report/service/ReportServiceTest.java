package com.bet99.report.service;

import com.bet99.report.dto.AccountTranReportDTO;
import com.bet99.report.dto.SummaryDTO;
import com.bet99.report.entity.AccountTran;
import com.bet99.report.repository.AccountTranRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private AccountTranRepository repository;

    @InjectMocks
    private ReportService service;

    @Test
    void shouldCalculateSummaryCorrectly() {

        when(repository.getSummary(
                any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(new Object[]{
                        new BigDecimal("100"),
                        new BigDecimal("250")
                });

        SummaryDTO summary = service.getSummary(
                Timestamp.valueOf("2025-07-01 00:00:00"),
                Timestamp.valueOf("2025-07-31 23:59:00"),
                null, null, null, null, null);

        verify(repository, times(1))
                .getSummary(any(), any(), any(), any(), any(), any(), any());

        assertAll(
                () -> assertEquals(0, summary.getTotalBet().compareTo(new BigDecimal("100"))),
                () -> assertEquals(0, summary.getTotalWin().compareTo(new BigDecimal("250"))),
                () -> assertEquals(0, summary.getNet().compareTo(new BigDecimal("150")))
        );
    }

    @Test
    void shouldReturnZeroSummaryWhenNull() {

        when(repository.getSummary(
                any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(null);

        SummaryDTO summary = service.getSummary(
                Timestamp.valueOf("2025-07-01 00:00:00"),
                Timestamp.valueOf("2025-07-31 23:59:00"),
                null, null, null, null, null);

        assertEquals(0, summary.getTotalBet().compareTo(BigDecimal.ZERO));
        assertEquals(0, summary.getTotalWin().compareTo(BigDecimal.ZERO));
        assertEquals(0, summary.getNet().compareTo(BigDecimal.ZERO));
    }

    @Test
    void shouldReturnCount() {

        when(repository.countReport(
                any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(5L);

        Long result = service.count(
                Timestamp.valueOf("2025-07-01 00:00:00"),
                Timestamp.valueOf("2025-07-31 23:59:00"),
                null, null, null, null, null);

        assertEquals(5L, result);

        verify(repository, times(1))
                .countReport(any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void shouldReturnReportList() {

        AccountTran tran = mock(AccountTran.class);

        when(tran.getId()).thenReturn(1L);
        when(tran.getAccountId()).thenReturn(1001);
        when(tran.getDatetime()).thenReturn(Timestamp.valueOf("2025-07-10 10:00:00"));
        when(tran.getTranType()).thenReturn("GAME_BET");
        when(tran.getPlatformTranId()).thenReturn("PT1");
        when(tran.getGameTranId()).thenReturn("GT1");
        when(tran.getGameId()).thenReturn("GAME1");
        when(tran.getAmountReal()).thenReturn(new BigDecimal("50"));
        when(tran.getBalanceReal()).thenReturn(new BigDecimal("500"));

        when(repository.findReport(
                any(), any(), any(), any(), any(), any(), any(),
                any(), any(), anyInt(), anyInt()))
                .thenReturn(List.of(tran));

        List<AccountTranReportDTO> result = service.getReport(
                Timestamp.valueOf("2025-07-01 00:00:00"),
                Timestamp.valueOf("2025-07-31 23:59:00"),
                null, null, null, null, null,
                "datetime", "desc",
                1, 25
        );

        assertEquals(1, result.size());

        AccountTranReportDTO dto = result.get(0);
        assertAll(
                () -> assertEquals(1L, dto.getId().longValue()),
                () -> assertEquals(1001, dto.getAccountId().intValue()),
                () -> assertEquals(Timestamp.valueOf("2025-07-10 10:00:00"), dto.getDateTime()),
                () -> assertEquals("GAME_BET", dto.getTranType()),
                () -> assertEquals("PT1", dto.getPlatformTranId()),
                () -> assertEquals("GT1", dto.getGameTranId()),
                () -> assertEquals("GAME1", dto.getGameId()),
                () -> assertEquals(0, dto.getAmount().compareTo(new BigDecimal("50"))),
                () -> assertEquals(0, dto.getBalance().compareTo(new BigDecimal("500")))
        );
    }

    @Test
    void shouldReturnEmptyList() {

        when(repository.findReport(
                any(), any(), any(), any(), any(), any(), any(),
                any(), any(), anyInt(), anyInt()))
                .thenReturn(List.of());

        List<AccountTranReportDTO> result = service.getReport(
                Timestamp.valueOf("2025-07-01 00:00:00"),
                Timestamp.valueOf("2025-07-31 23:59:00"),
                null, null, null, null, null,
                "datetime", "desc",
                1, 25
        );

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldCalculateOffsetCorrectly() {

        when(repository.findReport(
                any(), any(), any(), any(), any(), any(), any(),
                any(), any(), eq(25), eq(25)))
                .thenReturn(List.of());

        service.getReport(
                Timestamp.valueOf("2025-07-01 00:00:00"),
                Timestamp.valueOf("2025-07-31 23:59:00"),
                null, null, null, null, null,
                "datetime", "desc",
                2, 25
        );

        verify(repository, times(1)).findReport(
                any(), any(), any(), any(), any(), any(), any(),
                any(), any(), eq(25), eq(25)
        );
    }
}