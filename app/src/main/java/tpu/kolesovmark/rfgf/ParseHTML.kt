package tpu.kolesovmark.rfgf

import android.content.Context
import android.util.Log
import android.view.View
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element


object ParseHTML {

    fun parse(html: String): MutableList<String> {
        val doc: Document = Jsoup.parse(html)
        val content: Element = doc.getElementById("results")
        Log.d("Parsed_Element", content.html().toString())
        return getItems(content)

    }

    fun getItems(content: Element): MutableList<String> {
        var listOfTds = mutableListOf<String>()
        var listOfMaterial = mutableListOf<String>()
        var tds = content.getElementsByTag("td")
        for (td in tds) {
            listOfTds.add(td.text())
        }
        var i = 0
        while(i < listOfTds.size) {
            try {
                val o = listOfTds[i].toInt()
                if (o < 3000) {
                    if (o < 300) {
                        listOfMaterial.add(listOfTds[i+1])
                    }
                    if (o > 300) {
                        Log.d("NumberParsed", o.toString())
                        listOfMaterial.add(listOfTds[i+1])
                    }

                }
            } catch (_: Exception) {

            }
            Log.d("MyList_", listOfTds[i])
            i++

        }
        i = 0
        while (i < listOfMaterial.size) {
            Log.d("MyList", listOfMaterial[i])
            i++
        }
        return listOfMaterial
    }


}