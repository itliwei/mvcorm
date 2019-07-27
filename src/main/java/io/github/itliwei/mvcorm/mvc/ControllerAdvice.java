package io.github.itliwei.mvcorm.mvc;


import io.github.itliwei.mvcorm.mvc.Exception.BusinessException;
import io.github.itliwei.mvcorm.mvc.constants.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

	@ModelAttribute
	void pre(HttpServletRequest request) {
		log.debug("REQUEST URI = " + request.getRequestURI());
	}

	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Resp handleValidationException(ValidationException e, HttpServletRequest request, HttpServletResponse response)  {
		log.error(String.format("remote host %s ,uri %s , referer %s", request.getRemoteHost(), request.getRequestURI(), request.getHeader(HttpHeaders.REFERER)));
		log.error(e.getMessage(), e);
		response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return Resp.error(ErrorCode.SERVER,e.getMessage());

	}

	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.OK)
	public Resp handleBizException(BusinessException e, HttpServletResponse response)  {
		log.error(e.getMessage(), e);
		response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return Resp.error(e.getCode(),e.getMessage());
	}



	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Resp handleNullPointException(NullPointerException e, HttpServletResponse response) {
		log.error(e.getMessage(),e);
		response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return Resp.error(ErrorCode.SERVER,e.getMessage());
	}


	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Resp handleException(Throwable e, HttpServletResponse response)  {
		log.error(e.getMessage(), e);
		response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return Resp.error(ErrorCode.SERVER,e.getMessage());
	}

}
