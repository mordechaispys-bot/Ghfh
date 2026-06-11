package com.example.api

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ToolGenerator {

    private const val TAG = "ToolGenerator"

    private val systemInstructionText = """
        You are ToolVerse AI, the ultimate global creator of sandboxed, single-screen, highly interactive and visually stunning web tools.
        Your goal is to build fully-functional, responsive, clean client-side utility applications using ONLY HTML, CSS, and vanilla JS.
        
        CRITICAL RULES:
        1. You must respond ONLY with a valid JSON document matching this schema:
           {
             "name": "The tool name in Hebrew (e.g. 'מחשבון קלוריות מהיר')",
             "description": "A clear, beautifully written Hebrew description of the tool and its features",
             "category": "The most appropriate category among: 'פיננסים', 'לימודים', 'בריאות', 'עסקים', 'משחקים', 'פרודוקטיביות', 'AI', 'תכנות', 'עיצוב', 'מדיה'",
             "tags": ["tag1", "tag2", "tag3"],
             "index_html": "Just the pure HTML code (no markdown formatting, no outer wrappers if unnecessary, clean structure)",
             "style_css": "The styling code. Make sure to use modern look: dark beautiful theme (dark mode), neon accents, spacious padding, elegant typography, smooth active states, completely responsive for mobile screens, 48dp+ interactive touch targets",
             "script_js": "The full working JavaScript logic, fully interactive with zero mock features. Implement error checks, dynamic inputs and beautiful outputs. Ensure all buttons has proper onClick handlers defined or target-selective listeners."
           }
        
        2. Visuals must be exquisite (NEVER plain gray backgrounds, use gradients or dark slate palettes with bright accents like lime, emerald, aquamarine, neon blue, or light orange).
        3. Since the target audience searches in Hebrew, you MUST translate and build all visible UI labels, instructions, values, and results in HEBREW, utilizing Right-to-Left (direction: rtl) where appropriate.
        4. The tool must be fully offline-capable. Do not request external script modules, assets, or call external APIs unless essential. Everything must be calculated locally inside index_html, style_css, and script_js!
        5. Double check that the HTML, CSS, and JS connect perfectly. Use inline styles/scripts dynamically or trust that ToolVerse sandbox builds them seamlessly.
    """.trimIndent()

    suspend fun generateTool(promptText: String): GeneratedToolResponse? = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            Log.e(TAG, "Gemini API Key is missing or default placeholder")
            return@withContext null
        }

        val request = GenerateContentRequest(
            contents = listOf(
                Content(
                    parts = listOf(
                        Part(text = "צור כלי אינטראקטיבי ומלא עבור הבקשה הבאה: $promptText")
                    )
                )
            ),
            generationConfig = GenerationConfig(
                responseMimeType = "application/json",
                temperature = 0.7f
            ),
            systemInstruction = Content(
                parts = listOf(Part(text = systemInstructionText))
            )
        )

        try {
            val response = RetrofitClient.service.generateContent(apiKey, request)
            val jsonText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
            if (jsonText != null) {
                Log.d(TAG, "Received response: $jsonText")
                // Parse using Moshi adapter
                val adapter = RetrofitClient.moshi.adapter(GeneratedToolResponse::class.java)
                return@withContext adapter.fromJson(jsonText)
            } else {
                Log.e(TAG, "Empty text candidates returned from Gemini API")
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception during Tool Generation: ", e)
            null
        }
    }

    private val remixSystemInstructionText = """
        You are ToolVerse AI Remix Engine, the ultimate global editor and builder of sandboxed, single-screen interactive web tools.
        Your goal is to modify and upgrade an existing HTML, CSS, and JS web tool based on the user's requested improvements (which are in Hebrew).
        
        CRITICAL RULES:
        1. You must respond ONLY with a valid JSON document matching this schema:
           {
             "name": "The updated tool name in Hebrew (e.g. 'מחשבון פיננסי אולטרה פרו')",
             "description": "The updated beautiful Hebrew description reflecting the new features built",
             "category": "The most appropriate category among: 'פיננסים', 'לימודים', 'בריאות', 'עסקים', 'משחקים', 'פרודוקטיביות', 'AI', 'תכנות', 'עיצוב', 'מדיה'",
             "tags": ["tag1", "tag2"],
             "index_html": "The updated HTML code",
             "style_css": "The updated styling CSS. Retain the modern dark theme with vibrant neon highlights, excellent RTL margins, and proper accessibility sizing",
             "script_js": "The updated JavaScript with full interactivity, new features connected properly"
           }
        
        2. Deliver beautiful, clean, integrated code. Do NOT return any markdown wrapping around the JSON.
        3. Translate any newly created elements or labels to HEBREW, fully respecting RTL.
        4. The tools must remain completely offline competent with no dependence on external scripts or assets.
    """.trimIndent()

    suspend fun remixTool(
        title: String,
        description: String,
        category: String,
        tags: String,
        html: String,
        css: String,
        js: String,
        instruction: String
    ): GeneratedToolResponse? = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            Log.e(TAG, "Gemini API Key is missing or default placeholder")
            return@withContext null
        }

        val prompt = """
            Here are the details of the CURRENT tool:
            - Name: $title
            - Description: $description
            - Category: $category
            - Tags: $tags
            
            Current HTML:
            ```html
            $html
            ```
            
            Current CSS:
            ```css
            $css
            ```
            
            Current JS:
            ```js
            $js
            ```
            
            USER REMIX / IMPROVEMENT INSTRUCTION:
            $instruction
            
            Please modify the HTML, CSS, and JS code according to the instruction. Retain the styling aesthetic and ensure everything continues working seamlessly together. Return only the JSON document as specified.
        """.trimIndent()

        val request = GenerateContentRequest(
            contents = listOf(
                Content(
                    parts = listOf(
                        Part(text = prompt)
                    )
                )
            ),
            generationConfig = GenerationConfig(
                responseMimeType = "application/json",
                temperature = 0.6f
            ),
            systemInstruction = Content(
                parts = listOf(Part(text = remixSystemInstructionText))
            )
        )

        try {
            val response = RetrofitClient.service.generateContent(apiKey, request)
            val jsonText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
            if (jsonText != null) {
                Log.d(TAG, "Received remix response: $jsonText")
                val adapter = RetrofitClient.moshi.adapter(GeneratedToolResponse::class.java)
                return@withContext adapter.fromJson(jsonText)
            } else {
                Log.e(TAG, "Empty text candidates returned for Remix from Gemini")
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception during Tool Remix: ", e)
            null
        }
    }
}
