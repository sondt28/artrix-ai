package com.son.myapplication.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.son.myapplication.App
import com.son.myapplication.util.Constant.MAX_RESOLUTION
import com.son.myapplication.util.Constant.MIN_RESOLUTION
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import kotlin.math.max

class FileUtil {
    companion object {
        private var instance: FileUtil? = null

        fun getInstance(): FileUtil {
            if (instance == null) {
                instance = FileUtil()
            }

            return instance!!
        }
    }

    fun preProcessPath(path: String): String {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        var bitmap = BitmapFactory.decodeFile(path, options)

        val (height, width) = handleResizeBitmap(options)

        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)

        return saveBitmap(bitmap)
    }

    private fun handleResizeBitmap(options: BitmapFactory.Options): Pair<Int, Int> {
        val (height: Int, width: Int) = options.run { outHeight to outWidth }

        if (height > MAX_RESOLUTION || width > MAX_RESOLUTION) {
            val scale = MAX_RESOLUTION.toDouble() / max(height, width)
            val newHeight = (height * scale).toInt()
            val newWidth = (width * scale).toInt()
            return Pair(newHeight, newWidth)
        }

        if (height < MIN_RESOLUTION || width < MIN_RESOLUTION) {
            val scale = MIN_RESOLUTION.toDouble() / max(height, width)
            val newHeight = (height * scale).toInt()
            val newWidth = (width * scale).toInt()
            return Pair(newHeight, newWidth)
        }

        return Pair(height, width)
    }

    private fun saveBitmap(bmp: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val file =
            File(App.getInstance().getAppContext().cacheDir, "image_${UUID.randomUUID()}.jpg")
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(bytes.toByteArray())

        fileOutputStream.flush()
        fileOutputStream.close()

        return file.absolutePath
    }
}