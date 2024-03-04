package com.example.alumniconnect.ui.common

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.core.graphics.createBitmap

internal class PdfRender(
    private val fileDescriptor: ParcelFileDescriptor
) {
    private val pdfRenderer = PdfRenderer(fileDescriptor)
    val pageCount get() = pdfRenderer.pageCount

    val pageLists: List<Page> = List(pdfRenderer.pageCount) {
        Page(
            index = it,
            pdfRenderer = pdfRenderer
        )
    }

    fun close() {
        pageLists.forEach {
            it.recycle()
        }
        pdfRenderer.close()
        fileDescriptor.close()
    }

    class Page(
        val index: Int,
        val pdfRenderer: PdfRenderer
    ) {
        val pageContent = createBitmap()

        private fun createBitmap(): Bitmap {
            val newBitmap: Bitmap
            pdfRenderer.openPage(index).use { currentPage ->
                newBitmap = createBlankBitmap(
                    width = currentPage.width,
                    height = currentPage.height
                )
                currentPage.render(
                    newBitmap,
                    null,
                    null,
                    PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
                )
            }
            return newBitmap
        }

        fun recycle() {
            pageContent.recycle()
        }

        private fun createBlankBitmap(
            width: Int,
            height: Int
        ): Bitmap {
            return createBitmap(
                width,
                height,
                Bitmap.Config.ARGB_8888
            ).apply {
                val canvas = Canvas(this)
                canvas.drawBitmap(this, 0f, 0f, null)
            }
        }
    }
}