package storage

import Dataset
import astminer.common.model.PathContext
import java.io.File
import java.io.PrintWriter

class XCode2SeqPathStorage<LabelType>(
    override val directoryPath: String,
    override val noTypes: Boolean
) : XPathContextsStorage<LabelType> {

    private val separator = ","

    private val datasetPathsFiles = mutableMapOf<Dataset, File>()
    private val datasetFileWriters = mutableMapOf<Dataset, PrintWriter>()

    init {
        File(directoryPath).mkdirs()
        Dataset.values().forEach {
            val file = File("$directoryPath/java-psi.${it.folderName}.c2s")
            file.createNewFile()
            datasetPathsFiles[it] = file
            datasetFileWriters[it] = PrintWriter(file)
        }
    }

    private fun pathContextToString(pathContext: PathContext): String {
        val path = pathContext.orientedNodeTypes.joinToString("|") { it.typeLabel }
        return listOf(pathContext.startToken, path, pathContext.endToken).joinToString(separator)
    }

    private fun xPathContextToString(xPathContext: XPathContext): String {
        val pathContextString = pathContextToString(xPathContext.pathContext)
        return if (noTypes) pathContextString
        else listOf(
                normalizeTokenType(xPathContext.startTokenType),
                pathContextString,
                normalizeTokenType(xPathContext.endTokenType)
        ).joinToString(separator)
    }

    override fun store(xLabeledPathContexts: XLabeledPathContexts<LabelType>, dataset: Dataset) {
        val xPathContextsString = xLabeledPathContexts.xPathContexts.joinToString(" ") {
            xPathContextToString(it)
        }
        datasetFileWriters[dataset]?.println("${xLabeledPathContexts.label} $xPathContextsString")
    }

    override fun close() {
        datasetFileWriters.forEach {
            it.value.close()
        }
    }

    private fun normalizeTokenType(tokenType: String): String =
            tokenType.replace(",", ";").replace(" ", "_")
}
