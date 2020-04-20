package utils

fun generateString(count: Int = 10) = buildString {
    for (i in 0..count)
        append(('A'..'Z').random())
}

