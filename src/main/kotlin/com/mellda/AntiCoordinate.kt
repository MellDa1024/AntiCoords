package com.mellda

import com.lambda.client.plugin.api.Plugin
import com.mellda.modules.AntiCoords

internal object AntiCoordinate : Plugin() {

    override fun onLoad() {
        // Load any modules, commands, or HUD elements here
        modules.add(AntiCoords)
    }

    override fun onUnload() {
        // Here you can unregister threads etc...
    }
}