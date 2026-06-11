package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ToolEntity
import com.example.data.ToolRepository
import com.example.api.ToolGenerator
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

sealed interface GenerationState {
    object Idle : GenerationState
    object Generating : GenerationState
    data class Success(val tool: ToolEntity) : GenerationState
    data class Error(val message: String) : GenerationState
}

class ToolViewModel(private val repository: ToolRepository) : ViewModel() {

    // Filter properties
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    // Status profiles
    val allTools: StateFlow<List<ToolEntity>> = repository.allTools
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteTools: StateFlow<List<ToolEntity>> = repository.favoriteTools
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val downloadedTools: StateFlow<List<ToolEntity>> = repository.downloadedTools
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val customTools: StateFlow<List<ToolEntity>> = repository.customUploadedTools
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtered lists combining search + category
    val filteredTools: StateFlow<List<ToolEntity>> = combine(
        allTools, _searchQuery, _selectedCategory
    ) { tools, query, cat ->
        tools.filter { tool ->
            val matchesCategory = cat == null || tool.category == cat
            val matchesSearch = query.isEmpty() ||
                    tool.name.contains(query, ignoreCase = true) ||
                    tool.description.contains(query, ignoreCase = true) ||
                    tool.tags.contains(query, ignoreCase = true) ||
                    tool.category.contains(query, ignoreCase = true)

            matchesCategory && matchesSearch
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Selected state for runner
    private val _selectedTool = MutableStateFlow<ToolEntity?>(null)
    val selectedTool: StateFlow<ToolEntity?> = _selectedTool.asStateFlow()

    // Generation state
    private val _generationState = MutableStateFlow<GenerationState>(GenerationState.Idle)
    val generationState: StateFlow<GenerationState> = _generationState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.seedDefaultToolsIfEmpty()
        }
    }

    fun search(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: String?) {
        _selectedCategory.value = category
    }

    fun selectTool(tool: ToolEntity?) {
        _selectedTool.value = tool
        if (tool != null) {
            viewModelScope.launch {
                repository.incrementUsage(tool.id)
            }
        }
    }

    fun toggleFavorite(toolId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.toggleFavorite(toolId, isFavorite)
            // If selected tool is currently viewed, update its state
            if (_selectedTool.value?.id == toolId) {
                _selectedTool.value = _selectedTool.value?.copy(isFavorite = isFavorite)
            }
        }
    }

    fun toggleDownload(toolId: String, isDownloaded: Boolean) {
        viewModelScope.launch {
            repository.setDownloaded(toolId, isDownloaded)
            // Update currently viewed tool state
            if (_selectedTool.value?.id == toolId) {
                _selectedTool.value = _selectedTool.value?.copy(
                    isDownloaded = isDownloaded,
                    downloadCount = if (isDownloaded) _selectedTool.value!!.downloadCount + 1 else _selectedTool.value!!.downloadCount
                )
            }
        }
    }

    fun deleteTool(toolId: String) {
        viewModelScope.launch {
            repository.deleteTool(toolId)
            if (_selectedTool.value?.id == toolId) {
                _selectedTool.value = null
            }
        }
    }

    fun submitReview(toolId: String, name: String, comment: String, rating: Float) {
        viewModelScope.launch {
            val finalName = name.ifBlank { "משתמש אנונימי" }
            repository.submitReview(toolId, finalName, comment, rating)
            // Refresh details
            val updated = repository.getToolById(toolId)
            if (updated != null && _selectedTool.value?.id == toolId) {
                _selectedTool.value = updated
            }
        }
    }

    fun getReviewsForTool(toolId: String): Flow<List<com.example.data.ReviewEntity>> {
        return repository.getReviewsForTool(toolId)
    }

    fun clearGenerationState() {
        _generationState.value = GenerationState.Idle
    }

    fun generateToolWithAi(prompt: String) {
        if (prompt.isBlank()) return
        _generationState.value = GenerationState.Generating

        viewModelScope.launch {
            val response = ToolGenerator.generateTool(prompt)
            if (response != null) {
                val toolId = "ai_" + UUID.randomUUID().toString().take(6)
                val newTool = ToolEntity(
                    id = toolId,
                    name = response.name,
                    description = response.description,
                    category = response.category,
                    tags = response.tags.joinToString(","),
                    html = response.index_html,
                    css = response.style_css,
                    js = response.script_js,
                    isBuiltIn = false,
                    isDownloaded = true, // AI created tools are saved as downloaded locally
                    creatorName = "ToolVerse AI ✨",
                    creatorAvatar = "✨",
                    lastUpdated = "עודכן הרגע"
                )
                repository.insertTool(newTool)
                _generationState.value = GenerationState.Success(newTool)
            } else {
                _generationState.value = GenerationState.Error(
                    "לא ניתן היה לג'נרט את הכלי. ודא שהגדרת מפתח API של Gemini ושחיבור האינטרנט שלך פעיל."
                )
            }
        }
    }

    fun uploadCustomTool(
        title: String,
        desc: String,
        cat: String,
        tagsCsv: String,
        htmlStr: String,
        cssStr: String,
        jsStr: String,
        author: String
    ): Boolean {
        if (title.isBlank() || htmlStr.isBlank()) return false

        viewModelScope.launch {
            val customId = "user_" + UUID.randomUUID().toString().take(6)
            val newTool = ToolEntity(
                id = customId,
                name = title,
                description = desc.ifBlank { "כלי שהועלה על ידי המשתמש $author" },
                category = cat.ifBlank { "פרודוקטיביות" },
                tags = tagsCsv,
                html = htmlStr,
                css = cssStr.ifBlank { "body { padding: 16px; font-family: sans-serif; }" },
                js = jsStr,
                isBuiltIn = false,
                isDownloaded = true,
                isCustomUploaded = true,
                creatorName = author.ifBlank { "יוצר עצמאי" },
                creatorAvatar = "👨‍💻",
                lastUpdated = "הועלה הרגע"
            )
            repository.insertTool(newTool)
        }
        return true
    }
}
