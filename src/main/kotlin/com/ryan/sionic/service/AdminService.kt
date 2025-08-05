package com.ryan.sionic.service

import com.ryan.sionic.persistence.ChatRepository
import com.ryan.sionic.persistence.UserRepository
import com.ryan.sionic.service.command.GenerateReportCommand
import com.ryan.sionic.service.command.GetUserActivityCommand
import com.ryan.sionic.service.info.UserActivityInfo
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import java.time.ZoneId

@Service
class AdminService(
    private val userRepository: UserRepository,
    private val chatRepository: ChatRepository
) {
    fun getUserActivity(command: GetUserActivityCommand): UserActivityInfo {
        val date = command.date
        
        val startInstant = date.atStartOfDay(ZoneId.systemDefault()).toInstant()
        val endInstant = date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).minusNanos(1).toInstant()
        
        val registrations = userRepository.countByCreatedAtBetween(startInstant, endInstant)
        val chats = chatRepository.countByCreatedAtBetween(startInstant, endInstant)
        
        val logins = 0L
        return UserActivityInfo(
            registrations = registrations,
            logins = logins,
            chats = chats
        )
    }

    fun generateReport(command: GenerateReportCommand): ByteArray {
        val date = command.date
        
        val startInstant = date.atStartOfDay(ZoneId.systemDefault()).toInstant()
        val endInstant = date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).minusNanos(1).toInstant()
        
        val chats = chatRepository.findByCreatedAtBetween(startInstant, endInstant)
        val outputStream = ByteArrayOutputStream()
        val writer = OutputStreamWriter(outputStream)
        
        CSVPrinter(writer, CSVFormat.DEFAULT
            .withHeader("Chat ID", "User Email", "User Name", "Thread ID", "Question", "Answer", "Created At")
        ).use { printer ->
            chats.forEach { chat ->
                printer.printRecord(
                    chat.id,
                    chat.thread.user.email,
                    chat.thread.user.name,
                    chat.thread.id,
                    chat.question,
                    chat.answer,
                    chat.createdAt
                )
            }
        }
        
        return outputStream.toByteArray()
    }
}