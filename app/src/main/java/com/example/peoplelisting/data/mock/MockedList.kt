package com.example.peoplelisting.data.mock

import com.example.peoplelisting.data.model.dto.CardWidgetItem
import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.data.model.dto.SectionTitle
import com.example.peoplelisting.data.model.dto.WidgetType

object MockedList {
    val items = listOf(
        SectionTitle("My Widgets", "TITLE-1", WidgetType.MY_WIDGET),
        PersonDto(name = "Omar Assidi", age = 24, profession = "Software Engineer", id = "id-1", widgetType = WidgetType.MY_WIDGET),
        CardWidgetItem(heading = "Due Bill", "Electricity Bill", widgetType = WidgetType.MY_WIDGET, id = "card-1"),
        CardWidgetItem(heading = "Due Bill", "Phone Bill", widgetType = WidgetType.MY_WIDGET, id = "card-2"),
        SectionTitle("Curated Widgets", "TITLE-2", WidgetType.CURATED_WIDGET),
        PersonDto(name = "Donald Trump", age = 60, profession = "Former U.S President", id = "id-2", widgetType =
        WidgetType.CURATED_WIDGET),
        PersonDto(name = "John Snow", age = 34, profession = "King in the North", id = "id-3", widgetType =
        WidgetType.CURATED_WIDGET),

    )
}