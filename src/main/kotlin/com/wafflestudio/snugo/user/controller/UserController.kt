package com.wafflestudio.snugo.user.controller

import com.wafflestudio.snugo.common.auth.service.AuthService
import com.wafflestudio.snugo.user.model.RegisterRequest
import com.wafflestudio.snugo.user.model.TokenResponse
import com.wafflestudio.snugo.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/user")
class UserController(
	private val userService: UserService,
	private val auth: AuthService
) {
	@Operation(
		summary = "회원 가입"
	)
	@PostMapping("/register")
	fun register(@RequestBody request: RegisterRequest): TokenResponse {
		return userService.register(request.nickname, request.department)
	}
}
