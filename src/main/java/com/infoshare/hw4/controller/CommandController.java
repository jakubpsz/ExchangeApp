package com.infoshare.hw4.controller;

import com.infoshare.hw4.service.AverageService;
import com.infoshare.hw4.service.CommandWithParametersService;
import com.infoshare.hw4.service.GetCommandService;
import com.infoshare.hw4.service.MostVolatileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class CommandController {
    private final GetCommandService getService;
    private final CommandWithParametersService commandWithParametersService;
    private final MostVolatileService mostVolatileService;
    private final AverageService averageService;


    public CommandController(GetCommandService getService, CommandWithParametersService commandWithParametersService,
                             MostVolatileService mostVolatileService, AverageService averageService) {
        this.getService = getService;
        this.commandWithParametersService = commandWithParametersService;
        this.mostVolatileService = mostVolatileService;
        this.averageService = averageService;

    }

    @GetMapping(value = {"/EURUSD/{year}/{month}/{day}/{hour}/{minute}",
            "/EURUSD/{year}/{month}/{day}/{hour}",
            "/EURUSD/{year}/{month}/{day}"})
    public List<String> getCommandResults(@PathVariable Map<String, String> parameters) {
        String command = "get ";
        command = extractCommandBody(parameters, command);
        return getService.getGetCommandData(command);
    }

    @GetMapping(value = {"/command/{type}/{year}/{month}/{day}/{hour}/{minute}",
            "/command/{type}/{year}/{month}/{day}/{hour}",
            "/command/{type}/{year}/{month}/{day}"})
    public BigDecimal getWithTypeResults(@PathVariable Map<String, String> parameters) {
        String command = parameters.get("type") + " ";
        command = extractCommandBody(parameters, command);
        return commandWithParametersService.getParametersData(command);
    }

    @GetMapping("/average/{type}")
    public BigDecimal getAverageResults(@PathVariable String type) {
        String command = "average_" + type + "_volatility";
        return averageService.getAverage(command);
    }

    @GetMapping("/most/volatile/{type}")
    public String getMostVolatileResult(@PathVariable String type) {
        return mostVolatileService.getData(type);
    }

    private String extractCommandBody(Map<String, String> parameters, String command) {
        command += parameters.get("year") + "." + parameters.get("month") + "."
                + parameters.get("day") + " ";
        if (parameters.containsKey("hour")) {
            command += parameters.get("hour");
        }
        if (parameters.containsKey("minute")) {
            command = command + ":" + parameters.get("minute");
        }
        return command;
    }
}
