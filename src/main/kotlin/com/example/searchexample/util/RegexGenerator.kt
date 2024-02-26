package com.example.searchexample.util

import org.springframework.stereotype.Component

class GetRegExpOptions(
    val initialSearch: Boolean? = true,
    val startsWith: Boolean? = true,
    val endsWith: Boolean? = true,
    val ignoreSpace: Boolean? = true,
    val ignoreCase: Boolean? = true,
    val global: Boolean? = true,
    val fuzzy: Boolean? = true,
    val nonCaptureGroup: Boolean? = true
) {
}
@Component
class RegexGenerator {

    private val BASE = 44032
    private val INITIALS =
        listOf("ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ")
    private val MEDIALS =
        listOf("ㅏ", "ㅐ", "ㅑ", "ㅒ", "ㅓ", "ㅔ", "ㅕ", "ㅖ", "ㅗ", "ㅘ", "ㅙ", "ㅚ", "ㅛ", "ㅜ", "ㅝ", "ㅞ", "ㅟ", "ㅠ", "ㅡ", "ㅢ", "ㅣ")
    private val FINALES = listOf(
        "",
        "ㄱ",
        "ㄲ",
        "ㄳ",
        "ㄴ",
        "ㄵ",
        "ㄶ",
        "ㄷ",
        "ㄹ",
        "ㄺ",
        "ㄻ",
        "ㄼ",
        "ㄽ",
        "ㄾ",
        "ㄿ",
        "ㅀ",
        "ㅁ",
        "ㅂ",
        "ㅄ",
        "ㅅ",
        "ㅆ",
        "ㅇ",
        "ㅈ",
        "ㅊ",
        "ㅋ",
        "ㅌ",
        "ㅍ",
        "ㅎ"
    )
    private val MIXED = mapOf(
        "ㄲ" to listOf("ㄱ", "ㄱ"),
        "ㄳ" to listOf("ㄱ", "ㅅ"),
        "ㄵ" to listOf("ㄴ", "ㅈ"),
        "ㄶ" to listOf("ㄴ", "ㅎ"),
        "ㄺ" to listOf("ㄹ", "ㄱ"),
        "ㄻ" to listOf("ㄹ", "ㅁ"),
        "ㄼ" to listOf("ㄹ", "ㅂ"),
        "ㄽ" to listOf("ㄹ", "ㅅ"),
        "ㄾ" to listOf("ㄹ", "ㅌ"),
        "ㄿ" to listOf("ㄹ", "ㅍ"),
        "ㅀ" to listOf("ㄹ", "ㅎ"),
        "ㅄ" to listOf("ㅂ", "ㅅ"),
        "ㅆ" to listOf("ㅅ", "ㅅ"),
        "ㅘ" to listOf("ㅗ", "ㅏ"),
        "ㅙ" to listOf("ㅗ", "ㅐ"),
        "ㅚ" to listOf("ㅗ", "ㅣ"),
        "ㅝ" to listOf("ㅜ", "ㅓ"),
        "ㅞ" to listOf("ㅜ", "ㅔ"),
        "ㅟ" to listOf("ㅜ", "ㅣ"),
        "ㅢ" to listOf("ㅡ", "ㅣ")
    )
    private val MEDIAL_RANGE = mapOf(
        "ㅗ" to listOf("ㅗ", "ㅚ"),
        "ㅜ" to listOf("ㅜ", "ㅟ"),
        "ㅡ" to listOf("ㅡ", "ㅢ")
    )
    private val PRESENT_ON_KEYBOARD = listOf(
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ',
        'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅛ', 'ㅜ', 'ㅠ', 'ㅡ', 'ㅣ',
    )

    private fun escapeRegExp(str: String?) : String{
        val reRegExpChar = Regex("""[\\^$.*+?()\[\]{}|]""")
        val reHasRegExpChar = Regex(reRegExpChar.pattern)
        return str?.let {
            if (reHasRegExpChar.containsMatchIn(it)) {
                it.replace(reRegExpChar, """\\$0""")
            } else {
                it
            }
        } ?: ""
    }


    private fun genInitialRegex(initial : String, allowOnlyInitial: Boolean=false, initialOffset: Int): String{
        val baseCode = initialOffset * MEDIALS.size * FINALES.size + BASE
        var toRet : String = "[" + if (allowOnlyInitial && initial.length > 1) initial else ""
        toRet += String(Character.toChars(baseCode)) + "-" + String(Character.toChars(baseCode + MEDIALS.size * FINALES.size - 1)) + "]"
        return toRet
    }
    private fun getInitialSearchRegExp(initial: String, allowOnlyInitial: Boolean = false): String {
        val initialOffset = INITIALS.indexOf(initial)

        return if (initialOffset != -1) {
            genInitialRegex(initial, allowOnlyInitial, initialOffset)

        } else {
            initial
        }
    }

    data class Phonemes(
        val initial: String,
        val medial: String,
        val finale: String,
        val initialOffset: Int,
        val medialOffset: Int,
        val finaleOffset: Int
    )

    val FUZZY = "__${"fuzzy".hashCode().toString(36)}__"
    val IGNORE_SPACE = "__${"ignorespace".hashCode().toString(36)}__"

    private fun getPhonemes(lastChar: String): Phonemes {
        var initial = ""
        var initialOffset = -1
        var medialOffset = -1
        var finaleOffset = -1
        var medial = ""
        var finale = ""
        val charCode = lastChar.codePointAt(0)
        if (lastChar.matches(Regex("[가-힣]"))) {
            val tmp = charCode - BASE
            finaleOffset = tmp % FINALES.size
            medialOffset = ((tmp - finaleOffset) / FINALES.size) % MEDIALS.size
            initialOffset = (((tmp - finaleOffset) / FINALES.size) - medialOffset) / MEDIALS.size
            initial = INITIALS[initialOffset]
            medial = MEDIALS[medialOffset]
            finale = FINALES[finaleOffset]
        } else if (lastChar.matches(Regex("[ㄱ-ㅎ]"))) { // Check if char is a Hangul consonant
            initial = lastChar
            initialOffset = INITIALS.joinToString("").indexOf(lastChar)
        }

        return Phonemes(initial, medial, finale, initialOffset, medialOffset, finaleOffset)
    }

    fun getRegExp(
        search: String,
        options: GetRegExpOptions = GetRegExpOptions(false, false, false, false, true, false, false, false)
    ): Regex {
        var frontChars = search.toCharArray().toList()
        val lastChar = frontChars.lastOrNull()?.toString() ?: ""
        var lastCharPattern = ""

        val phonemes = getPhonemes(lastChar)

        if (phonemes.initialOffset != -1) {
            frontChars = frontChars.dropLast(1)
            val (initial, medial, finale, initialOffset, medialOffset) = phonemes
            val baseCode = initialOffset * MEDIALS.size * FINALES.size + BASE

            val patterns = mutableListOf<String>()
            when {
                finale.isNotBlank() -> {
                    patterns.add(lastChar)
                    if (INITIALS.contains(finale)) patterns.add(
                        "${String(Character.toChars(baseCode + medialOffset * FINALES.size))}${
                            getInitialSearchRegExp(
                                finale
                            )
                        }"
                    )
                    if (MIXED[finale] != null) {
                        val mixedFirstIndex = FINALES.joinToString("").indexOf(MIXED[finale]!![0])
                        patterns.add(
                            "${String(Character.toChars(baseCode + medialOffset * FINALES.size + mixedFirstIndex + 1))}${
                                getInitialSearchRegExp(
                                    MIXED[finale]!![1]
                                )
                            }"
                        )
                    }
                }

                medial.isNotBlank() -> {
                    val (from, to) = if (MEDIAL_RANGE.containsKey(medial)) {
                        val (start, end) = MEDIAL_RANGE[medial]!!
                        baseCode + MEDIALS.joinToString("")
                            .indexOf(start) * FINALES.size to baseCode + MEDIALS.joinToString("")
                            .indexOf(end) * FINALES.size + FINALES.size - 1
                    } else {
                        baseCode + medialOffset * FINALES.size to baseCode + medialOffset * FINALES.size + FINALES.size - 1
                    }
                    patterns.add("[" + String(Character.toChars(from)) + "-" + String(Character.toChars(to)) + "]")
                }

                initial.isNotBlank() -> {
                    patterns.add(getInitialSearchRegExp(initial, true))
                    println("Regex : "+ patterns)
                }
            }
            lastCharPattern = if (patterns.size > 1) "(${patterns.joinToString("|")})" else patterns[0]
        }

        val glue = if (options.fuzzy == true) FUZZY else if (options.ignoreSpace == true) IGNORE_SPACE else ""
        val frontCharsPattern = if (options.initialSearch == true) frontChars.joinToString(glue) { char ->
            if (char in 'ㄱ'..'ㅎ') getInitialSearchRegExp(char.toString(), true) else escapeRegExp(char.toString())
        } else escapeRegExp(frontChars.joinToString(glue))
        var pattern =
            "${if (options.startsWith == true) "^" else ""}$frontCharsPattern$glue$lastCharPattern${if (options.endsWith == true) "$" else ""}"
        if (glue.isNotEmpty()) {
            pattern = pattern.replace(FUZZY.toRegex(), ".*").replace(IGNORE_SPACE.toRegex(), "\\s*")
        }
        val regexOptions = buildString {
            if (options.global == true) append(RegexOption.LITERAL)
            if (options.ignoreCase == true) append(RegexOption.IGNORE_CASE)
        }
        return Regex(pattern, RegexOption.valueOf(regexOptions))
    }
}
