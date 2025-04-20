package com.github.adnanrangrej.natureguardian.data.mapper

import com.github.adnanrangrej.natureguardian.data.local.entity.species.CommonNameEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.ConservationActionEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.FullSpeciesDetails
import com.github.adnanrangrej.natureguardian.data.local.entity.species.HabitatEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.LocationEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.SpeciesDetailEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.SpeciesEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.SpeciesImageEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.ThreatEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.UseTradeEntity
import com.github.adnanrangrej.natureguardian.data.remote.model.chatbot.ChatBotResponse
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
import com.github.adnanrangrej.natureguardian.domain.model.chatbot.ChatBotMessage
import com.github.adnanrangrej.natureguardian.domain.model.news.NewsItem
import com.github.adnanrangrej.natureguardian.domain.model.news.NewsResponse
import com.github.adnanrangrej.natureguardian.domain.model.species.CommonName
import com.github.adnanrangrej.natureguardian.domain.model.species.ConservationAction
import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies
import com.github.adnanrangrej.natureguardian.domain.model.species.Habitat
import com.github.adnanrangrej.natureguardian.domain.model.species.Location
import com.github.adnanrangrej.natureguardian.domain.model.species.Species
import com.github.adnanrangrej.natureguardian.domain.model.species.SpeciesDetail
import com.github.adnanrangrej.natureguardian.domain.model.species.SpeciesImage
import com.github.adnanrangrej.natureguardian.domain.model.species.Threat
import com.github.adnanrangrej.natureguardian.domain.model.species.UseTrade

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

fun SpeciesEntity.toSpecies(): Species = Species(
    internalTaxonId = internalTaxonId,
    scientificName = scientificName,
    redlistCategory = redlistCategory,
    redlistCriteria = redlistCriteria,
    kingdomName = kingdomName,
    phylumName = phylumName,
    className = className,
    orderName = orderName,
    familyName = familyName,
    genusName = genusName,
    speciesEpithet = speciesEpithet,
    doi = doi,
    populationTrend = populationTrend,
    hasImage = hasImage,
    isBookmarked = isBookmarked
)

fun SpeciesDetailEntity.toSpeciesDetail(): SpeciesDetail = SpeciesDetail(
    speciesId = speciesId,
    description = description,
    conservationActionsDescription = conservationActionsDescription,
    habitatDescription = habitatDescription,
    useTradeDescription = useTradeDescription,
    threatsDescription = threatsDescription,
    populationDescription = populationDescription
)

fun CommonNameEntity.toCommonName(): CommonName = CommonName(
    commonNameId = commonNameId,
    speciesId = speciesId,
    commonName = commonName,
    language = language,
    isMain = isMain
)

fun ConservationActionEntity.toConservationAction(): ConservationAction = ConservationAction(
    conservationActionId = conservationActionId,
    speciesId = speciesId,
    code = code,
    actionName = actionName
)

fun HabitatEntity.toHabitat(): Habitat = Habitat(
    habitatId = habitatId,
    speciesId = speciesId,
    code = code,
    habitatName = habitatName,
    majorImportance = majorImportance,
    season = season,
    suitability = suitability
)

fun LocationEntity.toLocation(): Location = Location(
    locationId = locationId,
    speciesId = speciesId,
    latitude = latitude,
    longitude = longitude
)

fun ThreatEntity.toThreat(): Threat = Threat(
    threatId = threatId,
    speciesId = speciesId,
    code = code,
    threatName = threatName,
    stressCode = stressCode,
    stressName = stressName,
    severity = severity
)

fun UseTradeEntity.toUseTrade(): UseTrade = UseTrade(
    useTradeId = useTradeId,
    speciesId = speciesId,
    code = code,
    useTradeName = useTradeName,
    international = international,
    national = national
)

fun SpeciesImageEntity.toSpeciesImage(): SpeciesImage = SpeciesImage(
    imageId = imageId,
    speciesId = speciesId,
    imageUrl = imageUrl
)

fun FullSpeciesDetails.toDetailedSpecies(): DetailedSpecies = DetailedSpecies(
    species = species.toSpecies(),
    details = details?.toSpeciesDetail(),
    commonNames = commonNames.map { it.toCommonName() },
    conservationActions = conservationActions.map { it.toConservationAction() },
    habitats = habitats.map { it.toHabitat() },
    locations = locations.map { it.toLocation() },
    threats = threats.map { it.toThreat() },
    useTrade = useTrade.map { it.toUseTrade() },
    images = images.map { it.toSpeciesImage() }
)

fun ChatBotResponse.toChatBotMessage(): ChatBotMessage = ChatBotMessage(
    statusCode = statusCode,
    requestId = requestId,
    isError = body.isError,
    errorMessage = body.error,
    responseText = body.response?.text,
    role = body.response?.role
)