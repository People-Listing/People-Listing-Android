package com.example.peoplelisting.data.model.dto

data class PersonDto(
    val name: String = "",
    val age: Int = -1,
    val profession: String = "",
    override val id: String? = null,
    var isLoading: Boolean = false,
    var widgetType: WidgetType = WidgetType.OTHER
): ListItem(id)
data class SectionTitle(val title: String, override val id: String? = null, val widgetType: WidgetType = WidgetType.OTHER): ListItem
    (id)
abstract class ListItem(open val id: String? = "")

enum class WidgetType {
    MY_WIDGET,
    OTHER,
    CURATED_WIDGET;
}