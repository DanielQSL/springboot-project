package com.company.project.exception;

import com.company.project.enums.ResponseCodeEnum;
import com.company.project.exception.BusinessException;
import com.company.project.model.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * @author DanielQSL
 */
@SuppressWarnings({"rawtypes"})
@Slf4j
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {

    // =========== 客户端异常 =========

    /**
     * HTTP请求方法类型错误
     *
     * @param ex 异常
     * @return 响应结果
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public CommonResponse<Object> handle(HttpRequestMethodNotSupportedException ex) {
        return CommonResponse.fail(ResponseCodeEnum.CLIENT_HTTP_METHOD_ERROR);
    }

    /**
     * 处理客户端URL中的参数类型错误
     *
     * @param ex 异常
     * @return 响应结果
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public CommonResponse handle(BindException ex) {
        String errorMsg = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(";"));
        return CommonResponse.fail(ResponseCodeEnum.CLIENT_PATH_VARIABLE_ERROR, errorMsg);
    }

    /**
     * 处理客户端请求参数错误 @RequestParam
     *
     * @param ex 异常
     * @return 响应结果
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResponse handle(ConstraintViolationException ex) {
        String errorMsg = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(";"));
        return CommonResponse.fail(ResponseCodeEnum.CLIENT_REQUEST_PARAM_CHECK_ERROR, errorMsg);
    }

    /**
     * 处理客户端请求体参数错误 @RequestBody
     *
     * @param ex 异常
     * @return 响应结果
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse handle(MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(";"));
        return CommonResponse.fail(ResponseCodeEnum.CLIENT_REQUEST_BODY_CHECK_ERROR, errorMsg);
    }

    /**
     * 客户端请求缺少必填的参数
     *
     * @param ex 异常
     * @return 响应结果
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public CommonResponse handle(MissingServletRequestParameterException ex) {
        String errorMsg = null;
        if (!StringUtils.EMPTY.equals(ex.getParameterName())) {
            errorMsg = String.format("参数[%s]不能为空", ex.getParameterName());
        }
        if (errorMsg != null) {
            return CommonResponse.fail(ResponseCodeEnum.CLIENT_REQUEST_PARAM_REQUIRED_ERROR, errorMsg);
        }
        return CommonResponse.fail(ResponseCodeEnum.CLIENT_REQUEST_PARAM_REQUIRED_ERROR);
    }

    // =========== 服务端业务异常 =========

    /**
     * 捕获业务异常
     *
     * @param request 请求
     * @param ex      业务异常
     * @return 响应结果
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = BusinessException.class)
    public CommonResponse handle(HttpServletRequest request, final BusinessException ex) {
        log.warn("{} 业务异常", request.getRequestURI(), ex);
        return CommonResponse.fail(ex.getCode(), ex.getMessage());
    }

    // =========== 系统级别未知异常 =========

    /**
     * 捕获其他异常
     *
     * @param request 请求
     * @param ex      异常
     * @return 响应结果
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public CommonResponse handle(HttpServletRequest request, final Exception ex) {
        log.error("{} 系统异常", request.getRequestURI(), ex);
        return CommonResponse.fail(ResponseCodeEnum.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

}

