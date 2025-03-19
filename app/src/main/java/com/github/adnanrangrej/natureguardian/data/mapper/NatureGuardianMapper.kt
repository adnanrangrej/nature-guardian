package com.github.adnanrangrej.natureguardian.data.mapper

import com.github.adnanrangrej.natureguardian.data.remote.model.news.Content
import com.github.adnanrangrej.natureguardian.data.remote.model.news.Description
import com.github.adnanrangrej.natureguardian.data.remote.model.news.Id
import com.github.adnanrangrej.natureguardian.data.remote.model.news.Image
import com.github.adnanrangrej.natureguardian.data.remote.model.news.Item
import com.github.adnanrangrej.natureguardian.data.remote.model.news.NewsApiResponse
import com.github.adnanrangrej.natureguardian.data.remote.model.news.PublishedAt
import com.github.adnanrangrej.natureguardian.data.remote.model.news.SourceName
import com.github.adnanrangrej.natureguardian.data.remote.model.news.SourceUrl
import com.github.adnanrangrej.natureguardian.data.remote.model.news.Title
import com.github.adnanrangrej.natureguardian.data.remote.model.news.Url
import com.github.adnanrangrej.natureguardian.domain.model.news.NewsItem
import com.github.adnanrangrej.natureguardian.domain.model.news.NewsResponse

fun NewsApiResponse.toNewsResponse(): NewsResponse = NewsResponse(
    items = items.map { it.toNewsItem() },
    nextToken = nextToken
)

fun Item.toNewsItem(): NewsItem = NewsItem(
    content = content.value,
    description = description.value,
    id = id.value,
    image = image.value,
    publishedAt = publishedAt.value,
    sourceName = sourceName.value,
    sourceUrl = sourceUrl.value,
    title = title.value,
    url = url.value
)

fun NewsItem.toItem(): Item = Item(
    content = Content(content),
    description = Description(description),
    id = Id(id),
    image = Image(image),
    publishedAt = PublishedAt(publishedAt),
    sourceName = SourceName(sourceName),
    sourceUrl = SourceUrl(sourceUrl),
    title = Title(title),
    url = Url(url)
)