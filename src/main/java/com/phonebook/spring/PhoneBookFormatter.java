package com.phonebook.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.lang.String.join;

/**
 * Responsible for formatting in {@code PhoneBook} application
 */
@Component
public class PhoneBookFormatter {

    // comes from application.properties file
    @Value("${lowerCaseNames}")
    private boolean lowerCaseNames = false;
    @Value("${columnsSeparator}")
    private char columnsSeparator = '|';

    /**
     * output level
     */
    public enum Level {
        INFO("[INFO]"),
        ERROR("[ERROR]");

        private String level;

        Level(String level) {
            this.level = level;
        }

        @Override
        public String toString() {
            return this.level;
        }
    }

    /**
     * @param data to print send to stdout
     */
    public void show(Map<String, Set<String>> data) {
        data.forEach((k, v) -> System.out.println(format("%-20.20s%s%-10s", k, this.columnsSeparator, join(", ",
                v = lowerCaseNames ? v.stream().map(e -> e.toLowerCase()).collect(Collectors.toSet()) : v))));
    }

    /**
     * add {@code Level.INFO} message to stout
     *
     * @param message
     */
    public void info(String message) {
        System.out.println(format("\u001B[32m%s: %s\u001B[0m", Level.INFO, message));
    }

    /**
     * add {@code Level.ERROR} message to stout
     *
     * @param message
     */
    public void error(String message) {
        System.out.println(format("\u001B[31m%s: %s\u001B[0m", Level.ERROR, message));
    }

    /**
     * add {@code Level.ERROR} cause to stout
     *
     * @param cause of an error
     */
    public void error(Throwable cause) {
        error(cause.getMessage());
    }

    /*************************
     * getters required for bean properties to be injected
     *************************/

    /**
     * @param lowerCaseNames
     */
    public void setLowerCaseNames(boolean lowerCaseNames) {
        this.lowerCaseNames = lowerCaseNames;
    }

    /**
     * @param columnsSeparator
     */
    public void setColumnsSeparator(char columnsSeparator) {
        this.columnsSeparator = columnsSeparator;
    }
}
