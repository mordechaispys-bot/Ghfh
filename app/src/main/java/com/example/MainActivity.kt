package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.AppDatabase
import com.example.data.ToolRepository
import com.example.ui.ToolVerseApp
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.ToolViewModel

@Suppress("UNCHECKED_CAST")
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Room persistence database & repository
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = ToolRepository(database.toolDao())
        
        // Define simple Viewmodel factory inline
        val viewModel: ToolViewModel by viewModels {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ToolViewModel(repository) as T
                }
            }
        }

        enableEdgeToEdge()
        
        setContent {
            MyApplicationTheme {
                ToolVerseApp(viewModel = viewModel)
            }
        }
    }
}
