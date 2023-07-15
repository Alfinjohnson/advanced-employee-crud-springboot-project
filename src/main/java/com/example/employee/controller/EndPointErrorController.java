package com.example.employee.controller;

import com.example.employee.utility.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.employee.utility.APPConst.getCurrentTime;

@RestController
public class EndPointErrorController implements ErrorController {

    @lombok.Getter
    private static final String ERROR_PATH = "/error";

    /**
     * @apiNote Invalid endpoint request error handling methods */
    @RequestMapping(ERROR_PATH)
    @Hidden
    public ResponseEntity<ApiResponse<String>> handleError() {
        // Custom error response
        String errorMessage = "Invalid endpoint requested";
        ApiResponse<String> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setMessage(errorMessage);
        response.setData("");
        response.setTimestamp(getCurrentTime());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


}
