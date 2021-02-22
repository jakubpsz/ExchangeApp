package com.infoshare.hw4.logic.commands;

import lombok.Getter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Getter
public class CommandBody {
    public static final String DATE_PATTERN = "yyyy.MM.dd";
    public static final String DATA_DIVIDER = "[ :]";
    private final Parameters parameters;
    private final String name;

    public CommandBody(String input) {
        String[] values = input.split(DATA_DIVIDER);
        this.name = findCommand(values);
        this.parameters = findCommandParameters(values);
    }

    private String findCommand(String[] values) {
        StringBuilder stringBuilder = new StringBuilder();
        if (values[0].contains("_")) {
            return values[0];
        }
        for (String value : values) {
            if (isNormalString(value)) {
                stringBuilder.append(value);
                stringBuilder.append(" ");
            } else {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                break;
            }
        }
        return stringBuilder.toString();
    }

    private Parameters findCommandParameters(String[] values) {
        Parameters parameters = new Parameters();
        boolean endOfCommand = false;
        if (values[0].contains("_")) {
            return parameters;
        }
        for (String value : values) {
            if (!isNormalString(value)) {
                endOfCommand = true;
            }
            if (endOfCommand) {
                setCommandParameters(parameters, value);
            }
        }
        extractGetCommandType(values, parameters);
        return parameters;
    }

    private void setCommandParameters(Parameters parameters, String value) {
        if (parameters.getDay() == null) {
            parameters.setDay(LocalDate.parse(value, DateTimeFormatter.ofPattern(DATE_PATTERN)));
        } else if (parameters.getHour() == -1) {
            parameters.setHour(Integer.parseInt(value));
        } else {
            parameters.setMinute(Integer.parseInt(value));
        }
    }

    private void extractGetCommandType(String[] values, Parameters parameters) {
        if (Arrays.asList(values).contains("open")) {
            parameters.setCommandType(GetCommandType.OPEN);
        } else if (Arrays.asList(values).contains("close")) {
            parameters.setCommandType(GetCommandType.CLOSE);
        } else if (Arrays.asList(values).contains("high")) {
            parameters.setCommandType(GetCommandType.HIGH);
        } else if (Arrays.asList(values).contains("low")) {
            parameters.setCommandType(GetCommandType.LOW);
        }
    }

    private boolean isNormalString(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        dateFormat.setLenient(true);
        try {
            dateFormat.parse(date.trim());
        } catch (ParseException pe) {
            return true;
        }
        return false;
    }
}
