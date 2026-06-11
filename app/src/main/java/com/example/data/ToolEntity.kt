package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tools")
data class ToolEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val category: String,
    val tags: String, // comma-separated
    val html: String,
    val css: String,
    val js: String,
    val rating: Float = 4.5f,
    val reviewsCount: Int = 12,
    val downloadCount: Int = 24,
    val usageCount: Int = 45,
    val lastUpdated: String = "11/06/2026",
    val isBuiltIn: Boolean = false,
    val isFavorite: Boolean = false,
    val isDownloaded: Boolean = false,
    val isCustomUploaded: Boolean = false,
    val creatorName: String = "ToolVerse AI",
    val creatorAvatar: String = "🤖",
    val isSponsored: Boolean = false,
    val businessLink: String? = null,
    val bannerUrl: String? = null,
    val qrCodeData: String? = null,
    val versionHistoryJson: String = "v1.0 - הגרסה הראשונית לשירות"
)

@Entity(tableName = "reviews")
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val toolId: String,
    val reviewerName: String,
    val comment: String,
    val rating: Float,
    val timestamp: Long = System.currentTimeMillis()
)
