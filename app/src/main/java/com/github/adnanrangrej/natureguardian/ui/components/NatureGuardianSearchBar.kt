package com.github.adnanrangrej.natureguardian.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.ui.theme.NatureGuardianTheme

@Composable
fun NatureGuardianSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    placeHolderText: String
) {
    CustomSearchBar(
        modifier = modifier,
        query = query,
        onQueryChange = onQueryChange,
        placeHolderText = placeHolderText
    )
}

@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    placeHolderText: String
) {
    Box(modifier = modifier) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            value = query,
            onValueChange = onQueryChange,
            shape = RoundedCornerShape(24.dp),
            placeholder = { Text(placeHolderText) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = { onQueryChange("") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "clear"
                        )
                    }
                }
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )
    }
}

@Preview
@Composable
private fun NatureGuardianSearchBarPreview() {
    NatureGuardianTheme {
        NatureGuardianSearchBar(
            query = "",
            onQueryChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeHolderText = "Search species..."
        )
    }
}