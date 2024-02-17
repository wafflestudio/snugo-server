package com.wafflestudio.snugo.user.model

import com.wafflestudio.snugo.dept.model.Department
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
class User(
	@Id
	val id: String? = null,
	var token: String? = null,
	@Indexed(unique = true)
	val nickname: String,
	val department: Department
)
