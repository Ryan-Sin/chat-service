package com.ryan.sionic.service.command

import java.time.LocalDate

data class GetUserActivityCommand(
    val date: LocalDate = LocalDate.now()
)

data class GenerateReportCommand(
    val date: LocalDate = LocalDate.now()
)