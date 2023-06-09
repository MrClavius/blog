package pl.chlopickipiotr.blog.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;


@Controller
public class MainExceptionHandler implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        int status = (int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status == HttpStatus.NOT_FOUND.value()) {
            return "errors/error-404";
        } else if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return "errors/error-500";
        }
        return "errors/error";
    }

}


