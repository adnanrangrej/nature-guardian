package com.github.adnanrangrej.natureguardian.data.remote.model.news


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("content")
    val content: Content = Content(),
    @SerializedName("description")
    val description: Description = Description(),
    @SerializedName("id")
    val id: Id = Id(),
    @SerializedName("image")
    val image: Image = Image(),
    @SerializedName("publishedAt")
    val publishedAt: PublishedAt = PublishedAt(),
    @SerializedName("sourceName")
    val sourceName: SourceName = SourceName(),
    @SerializedName("sourceUrl")
    val sourceUrl: SourceUrl = SourceUrl(),
    @SerializedName("title")
    val title: Title = Title(),
    @SerializedName("url")
    val url: Url = Url()
)