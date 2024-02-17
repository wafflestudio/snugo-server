package com.wafflestudio.snugo.record.model

data class RecordPageResponse(
	val result: List<RecordDto>,
	val total_count: Long,
	val hasNext: Boolean
)
