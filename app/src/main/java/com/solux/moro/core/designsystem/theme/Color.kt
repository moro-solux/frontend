package com.solux.moro.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Basic
 * - 앱 전반에서 자주 쓰는 컬러만 `Colors`에 넣습니다.
 *
 * 사용 예:
 * - MoroTheme.Colors.background
 */

// Grays
val Gray10 = Color(0xFFF2F2F2)
val Gray15 = Color(0xFFEEEEEE)
val Gray20 = Color(0xFFD5D5D5)
val Gray30 = Color(0xFFBDBDBD)
val Gray40 = Color(0xFFA5A5A5)
val Gray50 = Color(0xFF404040)
val Gray60 = Color(0xFF262626)


// Background / Text
val Background = Color(0xFF121212)
val FontColor = Color(0xFFF2F2F2)

@Immutable
data class Colors(
    val background: Color = Background,
    val fontColor: Color = FontColor,

    val gray10: Color = Gray10,
    val gray15: Color = Gray15,
    val gray20: Color = Gray20,
    val gray30: Color = Gray30,
    val gray40: Color = Gray40,
    val gray50: Color = Gray50,
    val gray60: Color = Gray60,
)

val defaultColors = Colors()

/**
 * Palette Tokens
 * Naming: <Palette><Hue><Level>
 *
 * 사용 예:
 * - MoroPalette.Pastel.Purple100
 * - MoroPalette.Vivid.Red300
 * - MoroPalette.Nature.Teal500
 */
object MoroPalette {
    object Pastel {
        val Purple100 = Color(0xFFF5D8E0)
        val Purple200 = Color(0xFFEEB6C6)
        val Purple300 = Color(0xFFE895AB)
        val Purple400 = Color(0xFFDF7DA8)
        val Purple500 = Color(0xFFCD5F80)
        val Purple600 = Color(0xFFB34166)

        val Orange100 = Color(0xFFF7E6BA)
        val Orange200 = Color(0xFFF3D6A5)
        val Orange300 = Color(0xFFEEBB84)
        val Orange400 = Color(0xFFE99F65)
        val Orange500 = Color(0xFFD1814B)
        val Orange600 = Color(0xFFB76B40)

        val Yellow100 = Color(0xFFFCF9CA)
        val Yellow200 = Color(0xFFFAF5A8)
        val Yellow300 = Color(0xFFF9F189)
        val Yellow400 = Color(0xFFF8EE74)
        val Yellow500 = Color(0xFFF1D95B)
        val Yellow600 = Color(0xFFEBC251)

        val Green100 = Color(0xFFD8EFC5)
        val Green200 = Color(0xFFBFE3AA)
        val Green300 = Color(0xFFA8D98F)
        val Green400 = Color(0xFF92CD76)
        val Green500 = Color(0xFF7EC35E)
        val Green600 = Color(0xFF64A743)

        val Cyan100 = Color(0xFFDEEFFD)
        val Cyan200 = Color(0xFFC3E4FC)
        val Cyan300 = Color(0xFFA9D7FB)
        val Cyan400 = Color(0xFF91CAFA)
        val Cyan500 = Color(0xFF7CBDF9)
        val Cyan600 = Color(0xFF5C97C7)

        val Indigo100 = Color(0xFFE7E6F8)
        val Indigo200 = Color(0xFFD2C0D6)
        val Indigo300 = Color(0xFFC5A6DA)
        val Indigo400 = Color(0xFFAF84CC)
        val Indigo500 = Color(0xFF905DB0)
        val Indigo600 = Color(0xFF744493)

        val Gray100 = Color(0xFFF5F5F5)
        val Gray200 = Color(0xFFE0E0E0)
        val Gray300 = Color(0xFFCCCCCC)
        val Gray400 = Color(0xFFB3B3B3)
        val Gray500 = Color(0xFF999999)
        val Gray600 = Color(0xFF7F7F7F)

        val Black = Color(0xFF000000)
        val White = Color(0xFFFFFFFF)
        val White100 = Color(0xFFFAF9F6)
        val Gray2300 = Color(0xFFDDDDDD)
        val Gray2400 = Color(0xFFC0C0C0)
        val Gray2600 = Color(0xFF444444)
    }

    object Vivid {
        val Red100 = Color(0xFFDE3323)
        val Red200 = Color(0xFFB1271A)
        val Red300 = Color(0xFF851A11)
        val Red400 = Color(0xFF580E08)
        val Red500 = Color(0xFFE26F2E)
        val Red600 = Color(0xFFB55923)

        val Yellow100 = Color(0xFF874218)
        val Yellow200 = Color(0xFF5A2C0D)
        val Yellow300 = Color(0xFFFCFE57)
        val Yellow400 = Color(0xFFC9CB44)
        val Yellow500 = Color(0xFF979831)
        val Yellow600 = Color(0xFF65661E)

        val Green100 = Color(0xFF6AC83E)
        val Green200 = Color(0xFF4E962C)
        val Green300 = Color(0xFF32641A)
        val Green400 = Color(0xFF163209)
        val Green500 = Color(0xFF4767F5)
        val Green600 = Color(0xFF2E4C93)

        val Indigo100 = Color(0xFF1D3362)
        val Indigo200 = Color(0xFF0B1931)
        val Indigo300 = Color(0xFF8C25F5)
        val Indigo400 = Color(0xFF6A1BC3)
        val Indigo500 = Color(0xFF481092)
        val Indigo600 = Color(0xFF2F0761)

        val Purple100 = Color(0xFFE04997)
        val Purple200 = Color(0xFFB33A78)
        val Purple300 = Color(0xFF862B5A)
        val Purple400 = Color(0xFF591D3C)
        val Purple500 = Color(0xFF905636)
        val Purple600 = Color(0xFF6D3F27)

        val Cyan100 = Color(0xFF4C2A19)
        val Cyan200 = Color(0xFF26150C)
        val Cyan300 = Color(0xFF6197C7)
        val Cyan400 = Color(0xFF487295)
        val Cyan500 = Color(0xFF304C64)
        val Cyan600 = Color(0xFF172632)

        val Yellow2100 = Color(0xFF979846)
        val Yellow2200 = Color(0xFF727334)
        val Yellow2300 = Color(0xFF4C4D23)
        val Yellow2400 = Color(0xFF252611)
        val Yellow2500 = Color(0xFF78C9CB)
        val Yellow2600 = Color(0xFF599798)

        val Gray100 = Color(0xFF3B6565)
        val Gray200 = Color(0xFF1C3233)
        val Gray300 = Color(0xFF242424)
        val Gray400 = Color(0xFFF7F4F4)
        val Gray500 = Color(0xFF00B3A4)
        val Gray600 = Color(0xFF333333)
    }

    object Nature {
        val Cyan100 = Color(0xFFDFF6FD)
        val Cyan200 = Color(0xFFCBF2FD)
        val Cyan300 = Color(0xFFABE9FE)
        val Cyan400 = Color(0xFF8EDBFE)
        val Cyan500 = Color(0xFF5ABAF4)
        val Cyan600 = Color(0xFF1985C3)

        // (기존 네이밍 유지) 추가 세트
        val Cyan2100 = Color(0xFFDFF6FC)
        val Cyan2200 = Color(0xFFD2F2FC)
        val Cyan2300 = Color(0xFFA2E8FB)
        val Cyan2400 = Color(0xFF68D3F8)
        val Cyan2500 = Color(0xFF40B7E7)
        val Cyan2600 = Color(0xFF0D8ABB)

        val Teal100 = Color(0xFFD7F1F2)
        val Teal200 = Color(0xFFBAEBE6)
        val Teal300 = Color(0xFF9EE2DB)
        val Teal400 = Color(0xFF5BCEC6)
        val Teal500 = Color(0xFF3BB9BF)
        val Teal600 = Color(0xFF10809C)

        val Teal2100 = Color(0xFFD2EEEC)
        val Teal2200 = Color(0xFFC1E6E0)
        val Teal2300 = Color(0xFF8FDBD2)
        val Teal2400 = Color(0xFF6AC6BB)
        val Teal2500 = Color(0xFF3FB9A5)
        val Teal2600 = Color(0xFF11788A)

        val Green100 = Color(0xFFDBE8D6)
        val Green200 = Color(0xFFC6D9B7)
        val Green300 = Color(0xFF99BB7F)
        val Green400 = Color(0xFF699860)
        val Green500 = Color(0xFF4C7B4D)
        val Green600 = Color(0xFF3D6547)

        val Brown100 = Color(0xFFEAE5D7)
        val Brown200 = Color(0xFFE5D6BD)
        val Brown300 = Color(0xFFD3C09C)
        val Brown400 = Color(0xFFB4A17D)
        val Brown500 = Color(0xFF978161)
        val Brown600 = Color(0xFF5F4E3E)

        val Orange100 = Color(0xFFF2DFB6)
        val Orange200 = Color(0xFFF3C386)
        val Orange300 = Color(0xFFF0AA58)
        val Orange400 = Color(0xFFD66D44)
        val Orange500 = Color(0xFFB95145)
        val Orange600 = Color(0xFF884642)

        val Purple100 = Color(0xFFF1CBB0)
        val Purple200 = Color(0xFFEFB481)
        val Purple300 = Color(0xFFE38373)
        val Purple400 = Color(0xFFCE656C)
        val Purple500 = Color(0xFF96547D)
        val Purple600 = Color(0xFF7D466A)
    }
}

