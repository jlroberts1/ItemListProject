package com.jlroberts1.itemlistproject.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jlroberts1.itemlistproject.R

@Composable
fun GroupListItem(
    groupId: Int,
    onNavigateToItems: (Int) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        onClick = { onNavigateToItems(groupId) },
        colors = getGroupColor(groupId)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.group_label, groupId),
                fontSize = 24.sp
            )
        }
    }
}

@Preview
@Composable
fun GroupListItemPreview() {
    GroupListItem(
        groupId = 1,
        onNavigateToItems = {}
    )
}

@Composable
fun OneListItem(
    itemId: Int,
    name: String
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(16.dp),
                text = itemId.toString()
            )
            Text(text = name)
        }
    }
}

@Preview
@Composable
fun OneListItemPreview() {
    OneListItem(
        itemId = 1,
        name = "Item 1"
    )
}

fun getGroupColor(listId: Int): CardColors {
    return cardColorList[(listId - 1) % cardColorList.size]
}

private val cardColorList = listOf(
    CardColors(
        containerColor = Color(0xFFE57373),
        contentColor = Color(0xFF000000),
        disabledContentColor = Color(0xFF000000),
        disabledContainerColor = Color(0xFFE57373),
    ),
    CardColors(
        containerColor = Color(0xFF81C784),
        contentColor = Color(0xFF000000),
        disabledContentColor = Color(0xFF000000),
        disabledContainerColor = Color(0xFF81C784),
    ),
    CardColors(
        containerColor = Color(0xFF64B5F6),
        contentColor = Color(0xFF000000),
        disabledContentColor = Color(0xFF000000),
        disabledContainerColor = Color(0xFF64B5F6),
    ),
    CardColors(
        containerColor = Color(0xFFFFD54F),
        contentColor = Color(0xFF000000),
        disabledContentColor = Color(0xFF000000),
        disabledContainerColor = Color(0xFFFFD54F),
    )
)
