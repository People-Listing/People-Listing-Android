package com.example.peoplelisting.ui.screens.listpeople.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.peoplelisting.R
import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.ui.screens.main.PeopleListingScreenRoute
import com.example.peoplelisting.ui.theme.AppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Users(
    people: List<PersonDto>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(60.dp),
                shape = FloatingActionButtonDefaults.smallShape,
                onClick = onClick, containerColor =
                MaterialTheme.colorScheme
                    .primary, contentColor
                = MaterialTheme.colorScheme.secondary
            ) {
                Image(
                    modifier = Modifier.padding(10.dp),
                    painter = painterResource(id = R.drawable.ic_plus_icon),
                    contentDescription =
                    null
                )
            }

        }
    ) { _ ->
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            ListUserScreenBody(
                people = people, isRefreshing = isRefreshing,
                onRefresh = onRefresh
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListUserScreenBody(
    modifier: Modifier = Modifier,
    people: List<PersonDto>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit
) {
    val state = rememberPullToRefreshState()
    Box(modifier = modifier.nestedScroll(state.nestedScrollConnection)) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            TotalCount(totalCount = people.count())
            PersonCardList(people = people)
        }
        PullToRefreshContainer(
            state, modifier = Modifier
                .align(Alignment.TopCenter)
        )
        if (state.isRefreshing) {
            LaunchedEffect(key1 = Unit) {
                onRefresh()
            }
        }
        LaunchedEffect(key1 = isRefreshing) {
            if (!isRefreshing) {
                state.endRefresh()
            }
        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ListUserScreenPreview() {
    AppTheme {
        Users(
            listOf(
                PersonDto(name = "Omar", age = 23, profession = "Software Developer", id = "1"),
                PersonDto(name = "Omar", age = 23, profession = "Software Developer", id = "2"),
                PersonDto(name = "Omar", age = 23, profession = "Software Developer", id = "3"),
            ), isRefreshing = false, onRefresh = {}, onClick = {}
        )
    }
}