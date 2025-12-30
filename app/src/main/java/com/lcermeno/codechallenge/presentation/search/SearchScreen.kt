package com.lcermeno.codechallenge.presentation.search

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState) {

        if (uiState.errorMessage != null) {
            Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.clearErrors()
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                value = uiState.search,
                onValueChange = viewModel::onSearchChanged,
                placeholder = { Text(text = "Search something") }
            )

            if (uiState.loading) {
                CircularProgressIndicator(
                    Modifier.padding(64.dp)
                )
            } else {
                Content(uiState, viewModel)
            }
        }
    }
}

@Composable
private fun Content(
    uiState: SearchState,
    viewModel: SearchViewModel,
) {

    Spacer(modifier = Modifier.height(16.dp))

    Text(text = "City Name", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.ExtraBold)
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = uiState.cityName)
    Spacer(modifier = Modifier.height(16.dp))

    Text(text = "Temperature", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.ExtraBold)
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = uiState.temperature)
    Spacer(modifier = Modifier.height(16.dp))

    Text(text = "WeatherDescription", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.ExtraBold)
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = uiState.weatherDescription)
    Spacer(modifier = Modifier.height(16.dp))


    Button(onClick = {
        viewModel.search()
    }) {
        Text("Search...")
    }
}