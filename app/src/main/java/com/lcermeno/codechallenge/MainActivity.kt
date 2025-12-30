package com.lcermeno.codechallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.lcermeno.codechallenge.presentation.search.SearchScreen
import com.lcermeno.codechallenge.presentation.search.SearchViewModel
import com.lcermeno.codechallenge.ui.theme.CodeChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodeChallengeTheme {

                val viewModel by viewModels<SearchViewModel>()
                SearchScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}

