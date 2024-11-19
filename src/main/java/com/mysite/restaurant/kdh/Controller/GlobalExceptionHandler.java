package com.mysite.restaurant.kdh.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // HttpMessageNotReadableException 예외 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJsonParseException(HttpMessageNotReadableException ex) {
        // 예외가 발생했을 때 클라이언트에 적절한 응답을 반환합니다.`
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)  // 400 Bad Request
                .body("잘못된 데이터 형식이 전송되었습니다. 유효한 숫자 값을 입력해주세요.");
    }

    // 다른 예외를 처리할 수 있는 핸들러를 추가할 수 있습니다.
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNumberFormatException(NumberFormatException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)  // 400 Bad Request
                .body("숫자 형식에 맞지 않는 값이 전송되었습니다.");
    }

    // 모든 예외를 처리하는 기본 핸들러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)  // 500 Internal Server Error
                .body("서버에서 오류가 발생했습니다.");
    }
}