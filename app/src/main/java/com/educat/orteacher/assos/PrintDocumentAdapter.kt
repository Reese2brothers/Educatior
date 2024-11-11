package com.educat.orteacher.assos

import android.content.Context
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import com.educat.orteacher.assos.database.MathematicsPoints
import java.io.FileOutputStream
import java.io.PrintWriter

class PrintDocumentAdapter(private val context: Context, private val dataToPrint: List<MathematicsPoints>) :
    PrintDocumentAdapter() {
    override fun onLayout(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes?,
        cancellationSignal: android.os.CancellationSignal?,
        callback: LayoutResultCallback?,
        extras: Bundle?
    ) {
        if (cancellationSignal?.isCanceled == true) {
            callback?.onLayoutCancelled()
            return
        }
        val info = PrintDocumentInfo.Builder("My Data Print.pdf")
            .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
            .setPageCount(1)
            .build()
        callback?.onLayoutFinished(info, newAttributes != oldAttributes)
    }

    override fun onWrite(
        pages: Array<out PageRange>?,
        destination: ParcelFileDescriptor?,
        cancellationSignal: android.os.CancellationSignal?,
        callback: WriteResultCallback?
    ) {
        try {
            //val printDocumentInfo = PageRange.toPageRange(pages)
            val outputStream = FileOutputStream(destination!!.fileDescriptor)
            val printWriter = PrintWriter(outputStream)
            for (item in dataToPrint) {
                printWriter.println("${item.name}, ${item.mon}, ${item.tue}, ${item.wen}, ${item.thu}," +
                        "${item.fri}, ${item.sat}, ")
            }

            printWriter.flush()
            printWriter.close()
            callback?.onWriteFinished(pages)
        } catch (e: Exception) {
            callback?.onWriteFailed(e.toString())
        }
    }
}