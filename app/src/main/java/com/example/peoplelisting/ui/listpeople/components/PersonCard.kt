package com.example.peoplelisting.ui.listpeople.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peoplelisting.R
import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.ui.theme.AppColor
import com.example.peoplelisting.ui.theme.AppTheme
import com.example.peoplelisting.ui.theme.din

@Composable
fun PersonCard(modifier: Modifier = Modifier, personDto: PersonDto) {
    Box(
        modifier = modifier
            .border(3.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(16.dp))
            .padding(22.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                Modifier
                    .width(150.dp)
                    .padding(end = 32.dp)
            ) {
                Text(
                    text = personDto.name, style = TextStyle(
                        fontFamily = din, fontWeight = FontWeight.Bold,
                        fontSize =
                        20.sp,
                    ), color = AppColor.black, textAlign = TextAlign.Start
                )
                Text(
                    text = pluralStringResource(
                        id = R.plurals.age_years_old,
                        count = personDto.age,
                        personDto.age.toString()
                    ), style = TextStyle(
                        fontFamily = din, fontWeight = FontWeight.Bold,
                        fontSize =
                        16.sp,
                    ), color = AppColor.black, modifier = Modifier.padding(top = 4.dp), textAlign = TextAlign.Center
                )
            }
            Text(
                text = personDto.profession,
                textAlign = TextAlign.Start,
                style = TextStyle(
                    fontFamily = din, fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic
                ), color = AppColor.black
            )
        }
    }
}

@Composable
fun PersonCardList(
    modifier: Modifier = Modifier, people: List<PersonDto>
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp), modifier = modifier.fillMaxSize(), verticalArrangement =
        Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(people, key = { person -> person.id!! }) {
            PersonCard(personDto = it)
        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PersonCardListPreview() {
    AppTheme {
        PersonCardList(
            people = listOf(
                PersonDto(
                    name = "Omar Assidi",
                    age = 23,
                    profession = "Software Engineer",
                    isLoading = false,
                    id = "1"
                ),
                PersonDto(
                    name = "Omar Assidi",
                    age = 23,
                    profession = "Software Engineer",
                    isLoading = false,
                    id = "2"
                ),
                PersonDto(
                    name = "Omar Assidi",
                    age = 23,
                    profession = "Software Engineer",
                    isLoading = false,
                    id = "3"
                ),
                PersonDto(
                    name = "Omar Assidi",
                    age = 23,
                    profession = "Software Engineering",
                    isLoading = false,
                    id = "4"
                )
            )
        )
    }
}