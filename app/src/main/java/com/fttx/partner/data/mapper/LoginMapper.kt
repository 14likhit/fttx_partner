package com.fttx.partner.data.mapper

import com.fttx.partner.data.model.LoginDto
import com.fttx.partner.domain.model.Login

object LoginMapper {

    fun mapLoginDtoToLogin(loginDto: LoginDto): Login {
        return Login(
            status = loginDto.status ?: "",
            message = loginDto.message ?: "",
            userId = loginDto.userId ?: 0
        )

    }
}