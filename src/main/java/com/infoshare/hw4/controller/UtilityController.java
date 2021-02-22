package com.infoshare.hw4.controller;

import com.infoshare.hw4.logic.history.HistoryEntity;
import com.infoshare.hw4.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/utility")
public class UtilityController {
    private final HistoryService historyService;

    public UtilityController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/help")
    public String help() {
        log.info("Asked for help");
        return """
                List of commands:
                Mapping /EURUSD/{year}/{month}/{day}/{hour}/{minute}
                 - get yyyy.mm.dd hh:mm
                 - get yyyy.mm.dd hh
                 - get yyyy.mm.dd
                Mapping /command/{type}/{year}/{month}/{day}/{hour}/{minute}
                 - {get high/low/open/close} yyyy.mm.dd hh:mm
                 - {get high/low/open/close} yyyy.mm.dd hh
                 - {get high/low/open/close} yyyy.mm.dd
                 - {volatility} yyyy.mm.dd-hh:mm
                 - {volatility} yyyy.mm.dd-hh
                 - {volatility} yyyy.mm.dd
                 Mapping /avarange/{type}
                 - average_{minutely}_volatility
                 - average_{hourly}_volatility
                 - average_{daily}_volatility
                 Mapping /most/volatile/{type}
                 - most_volatile_{day}
                 - most_volatile_{hour}
                 Mapping /utility/save_history
                 Mapping /utility/get_history
                 Mapping /utility/reset_history
                """;
    }

    @GetMapping("/save_history")
    public String saveHistoryToFile() {
        if (historyService.save()) {
            log.info("History saved to file");
            return "Success";
        } else {
            log.error("Cannot save history");
            return "Failed";
        }
    }

    @GetMapping("/reset_history")
    public String resetHistory() {
        if (historyService.reset()) {
            log.info("History reset");
            return "Success";
        } else {
            log.error("Cannot reset history");
            return "Failed";
        }
    }

    @GetMapping("/get_history")
    public List<HistoryEntity> getHistory() {
        return historyService.getHistory();
    }

}
