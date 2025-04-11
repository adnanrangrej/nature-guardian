package com.github.adnanrangrej.natureguardian.data.local.processor

import android.content.Context
import android.util.Log
import androidx.core.text.HtmlCompat
import com.opencsv.CSVReader
import com.opencsv.CSVReaderBuilder
import com.opencsv.exceptions.CsvException
import java.io.InputStreamReader

fun String.parseFromHtml(): String {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
}

fun <T> parseCsvFile(
    filePath: String,
    skipHeader: Boolean = true,
    context: Context,
    mapRow: (Array<String>) -> T,
): List<T> {

    val results = mutableListOf<T>()
    var reader: CSVReader? = null

    try {
        val inputStream = context.assets.open(filePath)
        val inputStreamReader = InputStreamReader(inputStream, Charsets.UTF_8)

        reader = CSVReaderBuilder(inputStreamReader)
            .withSkipLines(if (skipHeader) 1 else 0)
            .build()

        var nextLine: Array<String>?

        while (reader.readNext().also { nextLine = it } != null) {
            nextLine?.let {
                try {
                    results.add(mapRow(it))
                } catch (e: Exception) {
                    Log.e("parseCsvFile", "Error mapping row: ${it.joinToString(",")}", e)
                }
            }
        }
    } catch (e: CsvException) {
        Log.e("ParseCsvFile", "CSV Parsing error for file: $filePath", e)
        throw e
    } catch (e: Exception) {
        Log.e("ParseCsvFile", "Error reading file: $filePath", e)
        throw e
    } finally {
        try {
            reader?.close()
        } catch (e: Exception) {
            Log.e("ParseCsvFile", "Error closing CSVReader", e)
        }
    }
    return results
}