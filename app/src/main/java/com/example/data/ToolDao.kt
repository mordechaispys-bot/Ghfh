package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ToolDao {
    @Query("SELECT * FROM tools ORDER BY lastUpdated DESC")
    fun getAllTools(): Flow<List<ToolEntity>>

    @Query("SELECT * FROM tools WHERE id = :id")
    suspend fun getToolById(id: String): ToolEntity?

    @Query("SELECT * FROM tools WHERE isFavorite = 1 ORDER BY lastUpdated DESC")
    fun getFavoriteTools(): Flow<List<ToolEntity>>

    @Query("SELECT * FROM tools WHERE isDownloaded = 1 ORDER BY lastUpdated DESC")
    fun getDownloadedTools(): Flow<List<ToolEntity>>

    @Query("SELECT * FROM tools WHERE isCustomUploaded = 1 ORDER BY lastUpdated DESC")
    fun getCustomUploadedTools(): Flow<List<ToolEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTool(tool: ToolEntity)

    @Update
    suspend fun updateTool(tool: ToolEntity)

    @Query("UPDATE tools SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: String, isFavorite: Boolean)

    @Query("UPDATE tools SET isDownloaded = :isDownloaded, downloadCount = downloadCount + 1 WHERE id = :id")
    suspend fun updateDownloadStatus(id: String, isDownloaded: Boolean)

    @Query("UPDATE tools SET usageCount = usageCount + 1 WHERE id = :id")
    suspend fun incrementUsage(id: String)

    @Query("DELETE FROM tools WHERE id = :id")
    suspend fun deleteToolById(id: String)

    // Review operations
    @Query("SELECT * FROM reviews WHERE toolId = :toolId ORDER BY timestamp DESC")
    fun getReviewsForTool(toolId: String): Flow<List<ReviewEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: ReviewEntity)
}
