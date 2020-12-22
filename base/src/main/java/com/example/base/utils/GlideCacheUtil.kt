package com.example.base.utils

import android.content.Context
import android.os.Looper
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import java.io.File
import java.math.BigDecimal

object GlideCacheUtil {
    /**
     * 清除圖片磁碟緩存
     */
    fun clearImageDiskCache(context: Context?) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Thread(Runnable { Glide.get(context!!).clearDiskCache() }).start()
            } else {
                Glide.get(context!!).clearDiskCache()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 清除圖片記憶體緩存
     */
    fun clearImageMemoryCache(context: Context?) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context!!).clearMemory()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 清除圖片所有緩存
     */
    fun clearImageAllCache(context: Context) {
        clearImageDiskCache(context)
        clearImageMemoryCache(context)
        val ImageExternalCatchDir = context.externalCacheDir
            .toString() + ExternalPreferredCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR
        deleteFolderFile(ImageExternalCatchDir, true)
    }

    /**
     * 獲取Glide造成的緩存大小
     *
     * @return CacheSize
     */
    fun getCacheSize(context: Context): String {
        try {
            return getFormatSize(
                getFolderSize(
                    File(
                        context.cacheDir
                            .toString() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR
                    )
                ).toDouble()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 獲取指定文件夾內所有文件大小的總和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun getFolderSize(file: File): Long {
        var size: Long = 0
        try {
            val fileList = file.listFiles()
            for (aFileList in fileList) {
                size = if (aFileList.isDirectory) {
                    size + getFolderSize(aFileList)
                } else {
                    size + aFileList.length()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

    /**
     * 刪除指定目錄下的文件，此處用於緩存的刪除
     *
     * @param filePath       filePath
     * @param deleteThisPath deleteThisPath
     */
    private fun deleteFolderFile(
        filePath: String,
        deleteThisPath: Boolean
    ) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                val file = File(filePath)
                if (file.isDirectory) {
                    val files = file.listFiles()
                    for (file1 in files) {
                        deleteFolderFile(file1.absolutePath, true)
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory) {
                        file.delete()
                    } else {
                        if (file.listFiles().size == 0) {
                            file.delete()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 格式化單位
     *
     * @param size size
     * @return size
     */
    private fun getFormatSize(size: Double): String {
        val kiloByte = size / 1024
        if (kiloByte < 1) {
            return size.toString() + "Byte"
        }
        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            val result1 =
                BigDecimal(java.lang.Double.toString(kiloByte))
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "KB"
        }
        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            val result2 =
                BigDecimal(java.lang.Double.toString(megaByte))
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "MB"
        }
        val teraBytes = gigaByte / 1024
        if (teraBytes < 1) {
            val result3 =
                BigDecimal(java.lang.Double.toString(gigaByte))
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "GB"
        }
        val result4 = BigDecimal(teraBytes)
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
    }
}