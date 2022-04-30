package com.mellda.modules

import com.mellda.AntiCoordinate
import com.lambda.client.module.Category
import com.lambda.client.plugin.api.PluginModule
import com.lambda.client.manager.managers.MessageManager.newMessageModifier
import kotlin.math.min
import com.lambda.client.util.text.MessageSendHelper
import java.util.regex.Pattern

internal object AntiCoords : PluginModule(
    name = "AntiCoords",
    category = Category.CHAT,
    description = "Detects Coordinate in your chat and block it from leaking base's coords.",
    modulePriority = 300,
    pluginMain = AntiCoordinate

) {
    private val detectMode by setting("DetectMode", DetectMode.STRICT)

    private enum class DetectMode {
        STRICT, SMART
    }

    private val modifier = newMessageModifier(
        modifier = {
            val message = selectmode(it.packet.message)
            message.substring(0, min(256, message.length))
        }
    )

    init {
        onEnable {
            modifier.enable()
        }

        onDisable {
            modifier.disable()
        }
    }

    private fun selectmode(message: String): String = when (detectMode) {
        DetectMode.STRICT -> detectStrict(message)
        DetectMode.SMART -> detectSmart(message)
    }

    private fun detectStrict(message: String): String {
        return if (Pattern.compile("(\\d+)+", Pattern.CASE_INSENSITIVE).matcher(message).find()) {
            MessageSendHelper.sendWarningMessage("$chatName Number Detected, Canceling...\nOriginal Message : $message")
            ""
        }
        else {
            return message
        }
    }

    private fun detectSmart(message: String): String {
        return if (Pattern.compile("(\\s*-?\\d+){2,4}", Pattern.CASE_INSENSITIVE).matcher(message).find()) {
            MessageSendHelper.sendWarningMessage("$chatName Coordinate Detected, Canceling...\nOriginal Message : $message")
            ""
        }
        else {
            return message
        }
    }
}