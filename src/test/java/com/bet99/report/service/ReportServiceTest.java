package com.bet99.report.service;

import com.bet99.report.dto.SummaryDTO;
import com.bet99.report.repository.AccountTranRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
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
}