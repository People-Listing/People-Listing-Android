package com.example.peoplelisting.ui.createpeople.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.peoplelisting.R
import com.example.peoplelisting.ui.createpeople.model.EntryType
import com.example.peoplelisting.ui.createpeople.model.FormEntry
import com.example.peoplelisting.ui.theme.AppTheme

@Composable
fun PersonForm(modifier: Modifier = Modifier, entries: List<FormEntry>, onInfoChanged: (String, EntryType) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(48.dp)) {
        for (entry in entries) {
            InfoEntry(
                hint = stringResource(id = entry.hint),
                info = entry.valueState,
                title = stringResource(id = entry.title),
                keyboardType = entry.keyboardType,
                onInfoChange = {
                    onInfoChanged(it, entry.entryType)
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFF3B4046)
@Composable
private fun PersonFormPreview() {
    AppTheme {
        PersonForm(
            entries = listOf(
                FormEntry(
                    hint = R.string.first_name_hint,
                    title = R.string.first_name,
                    entryType = EntryType.FirstName
                ),
                FormEntry(hint = R.string.last_name_hint, title = R.string.last_name, entryType = EntryType.LastName),
                FormEntry(hint = R.string.age_hint, title = R.string.age, entryType = EntryType.Age),
                FormEntry(
                    hint = R.string.profession_hint, title = R.string.profession, entryType = EntryType.Profession
                )
            )
        ) { _, _ -> }
    }
}