package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class ToolRepository(private val toolDao: ToolDao) {

    val allTools: Flow<List<ToolEntity>> = toolDao.getAllTools()
    val favoriteTools: Flow<List<ToolEntity>> = toolDao.getFavoriteTools()
    val downloadedTools: Flow<List<ToolEntity>> = toolDao.getDownloadedTools()
    val customUploadedTools: Flow<List<ToolEntity>> = toolDao.getCustomUploadedTools()

    suspend fun seedDefaultToolsIfEmpty() {
        val currentToolsList = toolDao.getAllTools().firstOrNull() ?: emptyList()
        if (currentToolsList.isEmpty()) {
            DefaultTools.list.forEach { tool ->
                toolDao.insertTool(tool)
            }
        }
    }

    suspend fun getToolById(id: String): ToolEntity? {
        return toolDao.getToolById(id)
    }

    suspend fun insertTool(tool: ToolEntity) {
        toolDao.insertTool(tool)
    }

    suspend fun toggleFavorite(id: String, isFavorite: Boolean) {
        toolDao.updateFavorite(id, isFavorite)
    }

    suspend fun setDownloaded(id: String, isDownloaded: Boolean) {
        toolDao.updateDownloadStatus(id, isDownloaded)
    }

    suspend fun incrementUsage(id: String) {
        toolDao.incrementUsage(id)
    }

    suspend fun deleteTool(id: String) {
        toolDao.deleteToolById(id)
    }

    // Review Actions
    fun getReviewsForTool(toolId: String): Flow<List<ReviewEntity>> {
        return toolDao.getReviewsForTool(toolId)
    }

    suspend fun submitReview(toolId: String, reviewerName: String, comment: String, rating: Float) {
        val review = ReviewEntity(
            toolId = toolId,
            reviewerName = reviewerName,
            comment = comment,
            rating = rating,
            timestamp = System.currentTimeMillis()
        )
        toolDao.insertReview(review)

        // Update Tool rating and review count
        val tool = toolDao.getToolById(toolId)
        if (tool != null) {
            val totalReviews = tool.reviewsCount + 1
            // calculate new approximate rating simple moving average
            val newRating = ((tool.rating * tool.reviewsCount) + rating) / totalReviews
            val updatedTool = tool.copy(
                reviewsCount = totalReviews,
                rating = newRating
            )
            toolDao.insertTool(updatedTool)
        }
    }
}
