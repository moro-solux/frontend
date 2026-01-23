package com.solux.moro.data.mapper

import android.util.Log
import androidx.compose.ui.graphics.Color

data class ColorDefinition(
    val id: Int,
    val theme: String,
    val hex: String
)

object ColorMapper {
    private val colors = listOf(
        ColorDefinition(1, "pastel", "#F5D8E0"),
        ColorDefinition(2, "pastel", "#EEB6C6"),
        ColorDefinition(3, "pastel", "#E895AB"),
        ColorDefinition(4, "pastel", "#DF7DA8"),
        ColorDefinition(5, "pastel", "#CD5F80"),
        ColorDefinition(6, "pastel", "#B34166"),
        ColorDefinition(7, "pastel", "#F7E6BA"),
        ColorDefinition(8, "pastel", "#F3D6A5"),
        ColorDefinition(9, "pastel", "#EEBB84"),
        ColorDefinition(10, "pastel", "#E99F65"),
        ColorDefinition(11, "pastel", "#D1814B"),
        ColorDefinition(12, "pastel", "#B76B40"),
        ColorDefinition(13, "pastel", "#FCF9CA"),
        ColorDefinition(14, "pastel", "#FAF5A8"),
        ColorDefinition(15, "pastel", "#F9F189"),
        ColorDefinition(16, "pastel", "#F8EE74"),
        ColorDefinition(17, "pastel", "#F1D95B"),
        ColorDefinition(18, "pastel", "#EBC251"),
        ColorDefinition(19, "pastel", "#D8EFC5"),
        ColorDefinition(20, "pastel", "#BFE3AA"),
        ColorDefinition(21, "pastel", "#A8D98F"),
        ColorDefinition(22, "pastel", "#92CD76"),
        ColorDefinition(23, "pastel", "#7EC35E"),
        ColorDefinition(24, "pastel", "#64A743"),
        ColorDefinition(25, "pastel", "#DEEFFD"),
        ColorDefinition(26, "pastel", "#C3E4FC"),
        ColorDefinition(27, "pastel", "#A9D7FB"),
        ColorDefinition(28, "pastel", "#91CAFA"),
        ColorDefinition(29, "pastel", "#7CBDF9"),
        ColorDefinition(30, "pastel", "#5C97C7"),
        ColorDefinition(31, "pastel", "#E7E6F8"),
        ColorDefinition(32, "pastel", "#D2C0D6"),
        ColorDefinition(33, "pastel", "#C5A6DA"),
        ColorDefinition(34, "pastel", "#AF84CC"),
        ColorDefinition(35, "pastel", "#905DB0"),
        ColorDefinition(36, "pastel", "#744493"),
        ColorDefinition(37, "pastel", "#F5F5F5"),
        ColorDefinition(38, "pastel", "#E0E0E0"),
        ColorDefinition(39, "pastel", "#CCCCCC"),
        ColorDefinition(40, "pastel", "#B3B3B3"),
        ColorDefinition(41, "pastel", "#999999"),
        ColorDefinition(42, "pastel", "#7F7F7F"),
        ColorDefinition(43, "pastel", "#000000"),
        ColorDefinition(44, "pastel", "#FFFFFF"),
        ColorDefinition(45, "pastel", "#DDDDDD"),
        ColorDefinition(46, "pastel", "#444444"),
        ColorDefinition(47, "pastel", "#FAF9F6"),
        ColorDefinition(48, "pastel", "#C0C0C0"),
        ColorDefinition(49, "vivid", "#DE3323"),
        ColorDefinition(50, "vivid", "#B1271A"),
        ColorDefinition(51, "vivid", "#851A11"),
        ColorDefinition(52, "vivid", "#580E08"),
        ColorDefinition(53, "vivid", "#E26F2E"),
        ColorDefinition(54, "vivid", "#B55923"),
        ColorDefinition(55, "vivid", "#874218"),
        ColorDefinition(56, "vivid", "#5A2C0D"),
        ColorDefinition(57, "vivid", "#FCFE57"),
        ColorDefinition(58, "vivid", "#C9CB44"),
        ColorDefinition(59, "vivid", "#979831"),
        ColorDefinition(60, "vivid", "#65661E"),
        ColorDefinition(61, "vivid", "#6AC83E"),
        ColorDefinition(62, "vivid", "#4E962C"),
        ColorDefinition(63, "vivid", "#32641A"),
        ColorDefinition(64, "vivid", "#163209"),
        ColorDefinition(65, "vivid", "#4767F5"),
        ColorDefinition(66, "vivid", "#2E4C93"),
        ColorDefinition(67, "vivid", "#1D3362"),
        ColorDefinition(68, "vivid", "#0B1931"),
        ColorDefinition(69, "vivid", "#8C25F5"),
        ColorDefinition(70, "vivid", "#6A1BC3"),
        ColorDefinition(71, "vivid", "#481092"),
        ColorDefinition(72, "vivid", "#2F0761"),
        ColorDefinition(73, "vivid", "#E04997"),
        ColorDefinition(74, "vivid", "#B33A78"),
        ColorDefinition(75, "vivid", "#862B5A"),
        ColorDefinition(76, "vivid", "#591D3C"),
        ColorDefinition(77, "vivid", "#905636"),
        ColorDefinition(78, "vivid", "#6D3F27"),
        ColorDefinition(79, "vivid", "#4C2A19"),
        ColorDefinition(80, "vivid", "#26150C"),
        ColorDefinition(81, "vivid", "#6197C7"),
        ColorDefinition(82, "vivid", "#487295"),
        ColorDefinition(83, "vivid", "#304C64"),
        ColorDefinition(84, "vivid", "#172632"),
        ColorDefinition(85, "vivid", "#979846"),
        ColorDefinition(86, "vivid", "#727334"),
        ColorDefinition(87, "vivid", "#4C4D23"),
        ColorDefinition(88, "vivid", "#252611"),
        ColorDefinition(89, "vivid", "#78C9CB"),
        ColorDefinition(90, "vivid", "#599798"),
        ColorDefinition(91, "vivid", "#3B6565"),
        ColorDefinition(92, "vivid", "#1C3233"),
        ColorDefinition(93, "vivid", "#242424"),
        ColorDefinition(94, "vivid", "#F7F4F4"),
        ColorDefinition(95, "vivid", "#00B3A4"),
        ColorDefinition(96, "vivid", "#333333"),
        ColorDefinition(97, "nature", "#DFF6FD"),
        ColorDefinition(98, "nature", "#CBF2FD"),
        ColorDefinition(99, "nature", "#ABE9FE"),
        ColorDefinition(100, "nature", "#8EDBFE"),
        ColorDefinition(101, "nature", "#5ABAF4"),
        ColorDefinition(102, "nature", "#1985C3"),
        ColorDefinition(103, "nature", "#DFF6FC"),
        ColorDefinition(104, "nature", "#D2F2FC"),
        ColorDefinition(105, "nature", "#A2E8FB"),
        ColorDefinition(106, "nature", "#68D3F8"),
        ColorDefinition(107, "nature", "#40B7E7"),
        ColorDefinition(108, "nature", "#0D8ABB"),
        ColorDefinition(109, "nature", "#D7F1F2"),
        ColorDefinition(110, "nature", "#BAEBE6"),
        ColorDefinition(111, "nature", "#9EE2DB"),
        ColorDefinition(112, "nature", "#5BCEC6"),
        ColorDefinition(113, "nature", "#3BB9BF"),
        ColorDefinition(114, "nature", "#10809C"),
        ColorDefinition(115, "nature", "#D2EEEC"),
        ColorDefinition(116, "nature", "#C1E6E0"),
        ColorDefinition(117, "nature", "#8FDBD2"),
        ColorDefinition(118, "nature", "#6AC6BB"),
        ColorDefinition(119, "nature", "#3FB9A5"),
        ColorDefinition(120, "nature", "#11788A"),
        ColorDefinition(121, "nature", "#DBE8D6"),
        ColorDefinition(122, "nature", "#C6D9B7"),
        ColorDefinition(123, "nature", "#99BB7F"),
        ColorDefinition(124, "nature", "#699860"),
        ColorDefinition(125, "nature", "#4C7B4D"),
        ColorDefinition(126, "nature", "#3D6547"),
        ColorDefinition(127, "nature", "#EAE5D7"),
        ColorDefinition(128, "nature", "#E5D6BD"),
        ColorDefinition(129, "nature", "#D3C09C"),
        ColorDefinition(130, "nature", "#B4A17D"),
        ColorDefinition(131, "nature", "#978161"),
        ColorDefinition(132, "nature", "#5F4E3E"),
        ColorDefinition(133, "nature", "#F2DFB6"),
        ColorDefinition(134, "nature", "#F3C386"),
        ColorDefinition(135, "nature", "#F0AA58"),
        ColorDefinition(136, "nature", "#D66D44"),
        ColorDefinition(137, "nature", "#B95145"),
        ColorDefinition(138, "nature", "#884642"),
        ColorDefinition(139, "nature", "#F1CBB0"),
        ColorDefinition(140, "nature", "#EFB481"),
        ColorDefinition(141, "nature", "#E38373"),
        ColorDefinition(142, "nature", "#CE656C"),
        ColorDefinition(143, "nature", "#96547D"),
        ColorDefinition(144, "nature", "#7D466A"),
        )

    private val idMap = colors.associateBy { it.id }

    private val hexMap = colors.associateBy { it.hex.uppercase() }

    private val themeMap = colors.groupBy { it.theme }

    fun toColorFromId(id: Int): Color? {
        val definition = idMap[id] ?: return null
        return toColorFromHex(definition.hex)
    }
    fun toColorFromHex(hex: String?): Color {
        if (hex.isNullOrBlank()) return Color(0xFFFFFFFF)
        return try {
            val formattedHex = if (hex.startsWith("#")) hex else "#$hex"
            val colorInt = android.graphics.Color.parseColor(formattedHex)

            Color(colorInt)
        } catch (e: Exception) {
            Log.e("ColorMapper", "변환 실패 hex: $hex - ${e.message}")
            Color.White // 실패 시 기본값
        }
    }
    fun toIdFromHex(hex: String): Int {
        val upperHex = hex.uppercase()

        return hexMap[upperHex]?.id ?:1
    }

    fun toHexFromId(id: Int): String=
        idMap[id]!!.hex

    fun toIdFromComposeColor(color: Color?): Int {
        if (color == null) return 1
        val hexString = String.format(
            "#%02X%02X%02X",
            (color.red * 255).toInt(),
            (color.green * 255).toInt(),
            (color.blue * 255).toInt()
        )
        return toIdFromHex(hexString)
    }
}