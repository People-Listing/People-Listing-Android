package com.example.peoplelisting.data.model.dto

data class CardWidgetItem(
    val heading: String = "",
    val subHeading: String = "",
    override var widgetType: WidgetType = WidgetType.OTHER,
    override val id: String? = null
): ListItem(id, widgetType)
