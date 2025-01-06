/**
 * Created by developer on 06-01-2025.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.data.repository.impl

import com.psyluckco.firebase.MessageService
import com.psyluckco.firebase.UserRepository
import com.psyluckco.sqwads.core.data.repository.MessageRepository
import com.psyluckco.sqwads.core.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.ZoneId
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageService: MessageService,
    private val userRepository: UserRepository
) : MessageRepository {

    override suspend fun loadAllMessagesOfUser(): Flow<List<Message>> {
        return messageService.loadAllMessages().map {
            firebaseMessages -> firebaseMessages.map {
                    val sentUserId = it.sentBy?.id ?: ""

                    Message(
                        id = it.id,
                        text = it.text,
                        sentAt = it.sentAt?.toDate()?.toInstant()
                            ?.atZone(ZoneId.systemDefault())
                            ?.toLocalDateTime(),
                        score = it.score,
                        fromCurrentUser = sentUserId == userRepository.getLoggedInUser().id,
                        sentBy = userRepository.getUserInfo(sentUserId).name
                    )
            }.filter {
                message -> message.fromCurrentUser
            }
        }
    }
}