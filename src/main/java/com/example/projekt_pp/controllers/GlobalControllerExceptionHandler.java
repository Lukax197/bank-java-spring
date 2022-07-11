package com.example.projekt_pp.controllers;

import com.example.projekt_pp.configuration.ProfileNames;
import com.example.projekt_pp.exceptions.KlienciNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.context.annotation.Profile;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.servlet.http.HttpServletRequest;

@Log4j2
@ControllerAdvice
@Profile(ProfileNames.NORMAL_CONTROLLER)
public class GlobalControllerExceptionHandler {


    @ExceptionHandler(KlienciNotFoundException.class)
    public String handleKlientNotFoundError(Model model, HttpServletRequest req, Exception ex){

        log.error("Request: "+ req.getRequestURL()+" raised "+ex);
        model.addAttribute("exception", ex);
        model.addAttribute("url", req.getRequestURL());

        return "errors/KlienciException";
    }

    @ExceptionHandler(JDBCConnectionException.class)
    public String handleJDBCNotFoundError(Model model, HttpServletRequest req, Exception ex){

        log.error("Request: "+ req.getRequestURL()+" raised "+ex);
        model.addAttribute("exception", ex);
        model.addAttribute("url", req.getRequestURL());

        return "errors/JDBCException";
    }



}
