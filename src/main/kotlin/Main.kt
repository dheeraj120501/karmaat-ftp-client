package org.example

import java.io.PrintWriter
import java.net.Socket
import  java.io.*

fun main(args: Array<String>) {
        val socket = Socket("localhost",9999)

        val os = socket.getOutputStream()
        val pw = PrintWriter(os,true)

        val path= "/Users/dheerajb/Documents/gurukul-learning/file_transfer_client/gradlew"
        val filename = path.split("/").last()

        val file = File(path)
        val fileInputStream = file.inputStream()
        val fileSize = file.length()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        var bytes = fileInputStream.read(buffer)

        val header = "KTP $filename\nSize $fileSize"

        pw.println(header)

        // To handle Progress Bar
        var byteSent = 0.0
        var perct:Int
        var lastP = -1

        while(bytes >= 0) {
            for (i in 0..<bytes) {
                pw.println(buffer[i])
                perct = ((byteSent/fileSize)*100).toInt()

                // Progress Bar
                byteSent++
                if(perct%10 == 0 && perct > lastP) {
                    lastP = perct
                    println("$filename: [${"*".repeat(perct/10)}${"-".repeat(10-perct/10)}]${perct}% sent")
                }
            }
            bytes = fileInputStream.read(buffer)
        }

        println("$filename send successfully")

        fileInputStream.close()
}