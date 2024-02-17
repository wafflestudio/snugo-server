package com.wafflestudio.snugo.dept.controller

import com.wafflestudio.snugo.dept.model.Department
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DepartmentController {
	@Operation(
		summary = "단과대 리스트 조회 (회원가입 시 단과대 리스트 불러올 때 사용)"
	)
	@GetMapping("/v1/departments")
	fun getDepartments(): List<String> {
		return Department.entries.map { it.value }
	}
}
