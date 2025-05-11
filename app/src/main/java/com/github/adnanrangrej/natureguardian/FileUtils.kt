package com.github.adnanrangrej.natureguardian

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object FileUtils {
    fun getFileFromUri(context: Context, uri: Uri): File? {
        var tempFile: File? = null
        try {
            val fileName = getFileName(context, uri) ?: "temp_upload_${System.currentTimeMillis()}"

            val cacheSubdir = File(context.cacheDir, "temp_uploads")
            if (!cacheSubdir.exists()) {
                cacheSubdir.mkdirs()
            }
            tempFile = File(cacheSubdir, fileName)

            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(tempFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            } ?: return null

            return tempFile

        } catch (e: IOException) {
            e.printStackTrace()
            tempFile?.delete() // Clean up if error occurs
            return null
        } catch (e: SecurityException) {
            e.printStackTrace()
            tempFile?.delete()
            return null
        } catch (e: Exception) {
            e.printStackTrace()
            tempFile?.delete()
            return null
        }
    }

    private fun getFileName(context: Context, uri: Uri): String? {
        var fileName: String? = null
        if (uri.scheme == "content") {
            try {
                context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        if (displayNameIndex != -1) {
                            fileName = cursor.getString(displayNameIndex)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (fileName == null) {
            fileName = uri.lastPathSegment
            if (fileName != null) {
                // Basic sanitization and ensure it's not overly long or just a path
                fileName = fileName.takeLast(100).replace("[^a-zA-Z0-9._-]".toRegex(), "_")
            }
        }
        return if (fileName.isNullOrEmpty()) "unknown_file" else fileName
    }
}