package aoc22

import readInput

fun Char.parseToCommand(): String {
    return when (this) {
        '$' -> "Command"
        '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> "File"
        'd' -> "Directory"
        else -> throw Exception("nei nei - $this")
    }
}

fun Pair<Int, String>.cdToFolder(cdDestination: String): Pair<Int, String> {
    return when (cdDestination) {
        "/" -> 0 to "/"
        ".." -> (this.first - 1) to this.second.dropLast(1).dropLastWhile { it != '/' }.let {
            it.ifEmpty { "/" }
        }
        else -> this.first + 1 to this.second + "$cdDestination/"
    }
}

fun String.getParentFolder(): String {
    return if (this == "/" || this == "") "/"
    else this.dropLast(1).dropLastWhile { it != '/' }
}

fun String.increaseSizeOfParentFolder(folders: MutableMap<String, Int>, size: Int) {
    val parentFolder = this.getParentFolder()
    if (this == "/") {
        folders["/"] = folders["/"]?.plus(size)!!
        return
    } else {
        try {
            if (folders[parentFolder] == null) {
                folders[parentFolder] = size
            } else {
                if (parentFolder != "/") folders[parentFolder] = folders[parentFolder]?.plus(size)!!
            }
            parentFolder.increaseSizeOfParentFolder(folders, size)
        } catch (e: Exception) {
            println("Nullpointer på $this, parent: $parentFolder")
        }
    }
}

fun day7part1(input: List<String>): Int {
    var currentFolder = 0 to "/"

    val inputIt = input.iterator()

    val folderSizes: MutableMap<String, Int> = mutableMapOf()
    var nextCommand = ""

    while (inputIt.hasNext()) {
        if (nextCommand != "") {
            val next = nextCommand.split(" ")
            if (next[1] == "cd") {
                currentFolder = currentFolder.cdToFolder(next[2])
                nextCommand = ""
            }
        }

        val currentLine = inputIt.next()
        val current = currentLine.split(" ")
        when (current[0].first().parseToCommand()) {
            "Command" -> {
                if (current[1] == "cd") {
                    currentFolder = currentFolder.cdToFolder(current[2])
                }
                if (current[1] == "ls") {
                    var files = mutableListOf<List<String>>()
                    var folders = mutableListOf<List<String>>()
                    var keepChecking = true
                    while (inputIt.hasNext() && keepChecking) {
                        val nextLine = inputIt.next()
                        if (nextLine.startsWith("$")) {
                            nextCommand = nextLine
                            keepChecking = false
                        } else {
                            val content = nextLine.split(" ")
                            if (content[0].first().parseToCommand() == "File") {
                                files.add(content)
                            }
                            if (content[0].first().parseToCommand() == "Directory") {
                                folders.add(content)
                            }
                        }
                    }
                    val folderSize = files.sumOf { it[0].toInt() }
                    if (folderSizes[currentFolder.second] == null) {
                        folderSizes[currentFolder.second] = folderSize
                    } else {
                        folderSizes[currentFolder.second] = folderSizes[currentFolder.second]?.plus(folderSize)!!
                    }
                    if (currentFolder.second != "/") currentFolder.second.increaseSizeOfParentFolder(folderSizes, folderSize)
                }
            }
            else -> throw Exception("Feil ved input: ${current[0].first().parseToCommand()}")
        }
    }
    val outFolders = folderSizes.filter { it.value <= 100000 }

    return outFolders.values.sum()
}

fun day7part2(input: List<String>): Int {
    var currentFolder = 0 to "/"
    var totalFileSize = 0

    val inputIt = input.iterator()

    val folderSizes: MutableMap<String, Int> = mutableMapOf()
    var nextCommand = ""

    while (inputIt.hasNext()) {
        if (nextCommand != "") {
            val next = nextCommand.split(" ")
            if (next[1] == "cd") {
                currentFolder = currentFolder.cdToFolder(next[2])
                nextCommand = ""
            }
        }

        val currentLine = inputIt.next()
        val current = currentLine.split(" ")
        when (current[0].first().parseToCommand()) {
            "Command" -> {
                if (current[1] == "cd") {
                    currentFolder = currentFolder.cdToFolder(current[2])
                }
                if (current[1] == "ls") {
                    var files = mutableListOf<List<String>>()
                    var folders = mutableListOf<List<String>>()
                    var keepChecking = true
                    while (inputIt.hasNext() && keepChecking) {
                        val nextLine = inputIt.next()
                        if (nextLine.startsWith("$")) {
                            nextCommand = nextLine
                            keepChecking = false
                        } else {
                            val content = nextLine.split(" ")
                            if (content[0].first().parseToCommand() == "File") {
                                files.add(content)
                                totalFileSize += content[0].toInt()
                            }
                            if (content[0].first().parseToCommand() == "Directory") {
                                folders.add(content)
                            }
                        }
                    }
                    val folderSize = files.sumOf { it[0].toInt() }
                    if (folderSizes[currentFolder.second] == null) {
                        folderSizes[currentFolder.second] = folderSize
                    } else {
                        folderSizes[currentFolder.second] = folderSizes[currentFolder.second]?.plus(folderSize)!!
                    }
                    currentFolder.second.increaseSizeOfParentFolder(folderSizes, folderSize)
                }
            }
            else -> throw Exception("Feil ved input: ${current[0].first().parseToCommand()}")
        }
    }
    val toDelete = folderSizes.filter { it.value >= 4965705 }.values.min()
    return toDelete
}

fun main() {
    val input = readInput("Day07")
    println(day7part1(input))
    println(day7part2(input))
}
