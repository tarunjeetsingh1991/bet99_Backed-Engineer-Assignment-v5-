package com.bet99.report.controller;

import com.bet99.report.dto.AccountTranReportDTO;
import com.bet99.report.dto.SummaryDTO;
import com.bet99.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ReportController {

    @Autowired ReportService service;

    @GetMapping("/")
    public String home() {
        return "report";
    }

    @GetMapping("/report")
    public String generate(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) Integer accountId,
            @RequestParam(required = false) String tranType,
            @RequestParam(required = false) String gameId,
            @RequestParam(required = false) String platformTranId,
            @RequestParam(required = false) String gameTranId,
            @RequestParam(required = false, defaultValue = "datetime") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String direction,
            @RequestParam(defaultValue = "1") int page,
            Model model) {

        // convert input to Timestamp
        Timestamp start = Timestamp.valueOf(startDate.replace("T", " ") + ":00");
        Timestamp end = Timestamp.valueOf(endDate.replace("T", " ") + ":00");

        int size = 25; // page size

        // get filtered and paginated data
        List<AccountTranReportDTO> list = service.getReport(
                start, end, accountId, tranType, gameId, platformTranId, gameTranId,
                sortBy, direction, page, size);

        // total record count
        long total = service.count(start, end, accountId, tranType, gameId, platformTranId, gameTranId);

        model.addAttribute("reportList", list);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) total / size));
        model.addAttribute("totalRecords", total);

        // keep form values for pagination & display
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("accountId", accountId);
        model.addAttribute("tranType", tranType);
        model.addAttribute("gameId", gameId);
        model.addAttribute("platformTranId", platformTranId);
        model.addAttribute("gameTranId", gameTranId);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        
        SummaryDTO summary = service.getSummary(
                start, end, accountId, tranType, gameId, platformTranId, gameTranId);

        model.addAttribute("summary", summary);

        return "report";
    }
    
    @GetMapping("/report/csv")
    @ResponseBody
    public void exportCsv(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) Integer accountId,
            @RequestParam(required = false) String tranType,
            @RequestParam(required = false) String gameId,
            @RequestParam(required = false) String platformTranId,
            @RequestParam(required = false) String gameTranId,
            @RequestParam(required = false, defaultValue = "datetime") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String direction,
            HttpServletResponse response) throws Exception {

        Timestamp start = Timestamp.valueOf(startDate.replace("T", " ") + ":00");
        Timestamp end = Timestamp.valueOf(endDate.replace("T", " ") + ":00");

        List<AccountTranReportDTO> list = service.getReport(
                start, end, accountId, tranType, gameId, platformTranId, gameTranId,
                sortBy, direction, 1, Integer.MAX_VALUE);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=report.csv");

        PrintWriter writer = response.getWriter();

        writer.println("ID,Account,Date,Type,PlatformTranId,GameTranId,GameId,Amount,Balance");

        for (AccountTranReportDTO r : list) {
            writer.println(
                    r.getId() + "," +
                    r.getAccountId() + "," +
                    r.getDateTime() + "," +
                    r.getTranType() + "," +
                    r.getPlatformTranId() + "," +
                    r.getGameTranId() + "," +
                    r.getGameId() + "," +
                    r.getAmount() + "," +
                    r.getBalance()
            );
        }

        writer.flush();
    }
}