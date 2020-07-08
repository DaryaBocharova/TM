package ru.bocharova.se.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@RestController
public class ErrorPageController {

    private static final String ERROR_CODE = "errorCode";
    private static final String ERROR_MESSAGE = "errorMessage";

    @RequestMapping(value = "error")
    public ModelAndView pageError(
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @NotNull final ModelAndView errorPage = new ModelAndView("errorPage");
        @NotNull final int httpErrorCode = getErrorCode(req);
        @NotNull final String errorMessage = resp.toString();
        errorPage.addObject(ERROR_CODE, String.valueOf(httpErrorCode));
        errorPage.addObject(ERROR_MESSAGE, errorMessage);
        return errorPage;
    }

    private int getErrorCode(@NotNull final HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }

}
