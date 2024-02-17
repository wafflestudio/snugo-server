package com.wafflestudio.snugo.common.error

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.ConstraintViolationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class GlobalExceptionHandler {
	private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

	@ExceptionHandler(
		ConstraintViolationException::class,
		HttpMessageNotReadableException::class,
		HttpMediaTypeNotSupportedException::class,
		HttpRequestMethodNotSupportedException::class,
		MethodArgumentNotValidException::class,
		MethodArgumentTypeMismatchException::class,
		MissingServletRequestParameterException::class,
		IllegalStateException::class
	)
	fun handleHttpMessageBadRequest(
		e: Exception,
		request: HttpServletRequest,
		response: HttpServletResponse,
	): ResponseEntity<Any> {
		e.printStackTrace()
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
	}

	@ExceptionHandler(IllegalArgumentException::class)
	fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<Any> {
		e.printStackTrace()
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.message)
	}

	@ExceptionHandler(Exception::class)
	fun handlerException(e: Exception): ResponseEntity<Any> {
		e.printStackTrace()
		return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
	}

	@ExceptionHandler(BusinessException::class)
	fun handlerBaniException(e: BusinessException): ResponseEntity<ErrorResponse> {
		e.printStackTrace()
		return ResponseEntity.status(e.errorType.httpStatus)
			.body(ErrorResponse(e.errorType.code, e.message?:"no message"))
	}

}
