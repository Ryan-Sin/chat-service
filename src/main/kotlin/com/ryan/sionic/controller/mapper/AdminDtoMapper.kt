package com.ryan.sionic.controller.mapper

import com.ryan.sionic.controller.dto.UserActivityResponseDto
import com.ryan.sionic.service.command.GenerateReportCommand
import com.ryan.sionic.service.command.GetUserActivityCommand
import com.ryan.sionic.service.info.UserActivityInfo
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class AdminDtoMapper {
    fun mapToGetUserActivityCommand(date: LocalDate? = null): GetUserActivityCommand {
        return GetUserActivityCommand(
            date = date ?: LocalDate.now()
        )
    }
    
    fun mapToGenerateReportCommand(date: LocalDate? = null): GenerateReportCommand {
        return GenerateReportCommand(
            date = date ?: LocalDate.now()
        )
    }
    
    fun mapToUserActivityResponse(info: UserActivityInfo): UserActivityResponseDto {
        return UserActivityResponseDto(
            registrations = info.registrations,
            logins = info.logins,
            chats = info.chats
        )
    }
}