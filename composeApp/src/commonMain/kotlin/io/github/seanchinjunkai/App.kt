package io.github.seanchinjunkai

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.seanchinjunkai.vertexgsoc.GenerativeModel
import io.github.seanchinjunkai.vertexgsoc.RequestOptions
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val api = remember { GenerativeModel(key = "ya29.a0AZYkNZhT-WAut3BzJW_Zcl_lwWh4jsVaG0GXs1g_r_4eCPJ-r8VCVknyhNbeJ7OseXOtLw1taW3aNZJB20VZ3fbusfQAK3KSdyxVcU6AzVrCepgHtu7u4zf1AA3XxdgBzoVH-0TcGtr8A6ZrN7suhb2IKEM0HBC0dBgtLw2WBro0Wu0aCgYKAfsSARESFQHGX2MiYvDtVXGQ_ZGaRC28oDSGSA0182", model = "gemini-1.5-flash-002", RequestOptions(projectId = "gemini-kmp"))}

    val coroutineScope = rememberCoroutineScope()
    var prompt by remember { mutableStateOf("Summarize the benefits of Kotlin Multiplatform") }
    var content by remember { mutableStateOf("") }
    var showProgress by remember { mutableStateOf(false) }

    MaterialTheme {
        Column(Modifier.verticalScroll(rememberScrollState())
            .fillMaxWidth().padding(16.dp)
        ) {
            Row {
                TextField(value = prompt,
                    onValueChange = { prompt = it },
                    modifier = Modifier.weight(7f)
                )
                TextButton(onClick = {
                    if (prompt.isNotBlank()) {
                        coroutineScope.launch {
                            showProgress = true
                            content = generateContent(api, prompt)
                            showProgress = false
                        }
                    }
                },
                    modifier = Modifier.weight(3f)
                        .padding(all = 4.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text("Submit")
                }
            }

            Spacer(Modifier.height(16.dp))
            if (showProgress) {
                CircularProgressIndicator()
            } else {
                Text(content)
            }
        }
    }
}

suspend fun generateContent(api: GenerativeModel, prompt: String): String {
    println("prompt = $prompt")
    val result = api.generateContent(prompt)
    return result.response?.text ?: "NO RESULTS"
}