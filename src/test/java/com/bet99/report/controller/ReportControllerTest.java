package com.bet99.report.controller;

import com.bet99.report.dto.AccountTranReportDTO;
import com.bet99.report.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReportService service;

    @InjectMocks
    private ReportController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void home_shouldReturnReportView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("report"));
    }


    @Test
    void exportCsv_shouldReturnCsvFile() throws Exception {

        List<AccountTranReportDTO> reportList = Arrays.asList(
                new AccountTranReportDTO(
                        1L,
                        1001,
                        Timestamp.valueOf("2025-07-10 10:15:00"),
                        "GAME_BET",
                        "PT100",
                        "GT200",
                        "GAME01",
                        new BigDecimal("50.00"),
                        new BigDecimal("450.00")
                ),
                new AccountTranReportDTO(
                        2L,
                        1001,
                        Timestamp.valueOf("2025-07-10 10:20:00"),
                        "GAME_WIN",
                        "PT101",
                        "GT201",
                        "GAME01",
                        new BigDecimal("120.00"),
                        new BigDecimal("570.00")
                )
        );

        when(service.getReport(
                any(Timestamp.class),
                any(Timestamp.class),
                any(),
                any(),
                any(),
                any(),
                any(),
                anyString(),
                anyString(),
                eq(1),
                eq(Integer.MAX_VALUE)
        )).thenReturn(reportList);

        mockMvc.perform(get("/report/csv")
                        .param("startDate", "2025-07-01T00:00")
                        .param("endDate", "2025-07-31T23:59")
                        .param("sortBy", "datetime")
                        .param("direction", "desc"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=report.csv"))
                .andExpect(content().contentType("text/csv"))
                .andExpect(content().string(containsString(
                        "ID,Account,Date,Type,PlatformTranId,GameTranId,GameId,Amount,Balance"
                )))
                .andExpect(content().string(containsString(
                        "1,1001,2025-07-10 10:15:00.0,GAME_BET,PT100,GT200,GAME01,50.00,450.00"
                )))
                .andExpect(content().string(containsString(
                        "2,1001,2025-07-10 10:20:00.0,GAME_WIN,PT101,GT201,GAME01,120.00,570.00"
                )));
    }
}