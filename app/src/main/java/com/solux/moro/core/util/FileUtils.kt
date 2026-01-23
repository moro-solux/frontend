package com.solux.moro.core.util

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object FileUtils {

    // Uri > 실제 파일 변환 함수
    fun uriToFile(context: Context, uri: Uri): File? {
        try {
            // 1. ContentResolver를 통해 Uri에서 데이터를 읽어올 통로를 엽니다.
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri) ?: return null
            if (inputStream == null) return null
            // 2. 앱의 임시 캐시 폴더에 빈 파일을 하나 만듭니다. (이름은 현재시간.jpg)
            val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")

            // 3. 빈 파일에 데이터를 씁니다.
            val outputStream = FileOutputStream(file)
            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output) // 내용 복사
                }
            }

            // 4. 완성된 파일을 반환
            return file

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}