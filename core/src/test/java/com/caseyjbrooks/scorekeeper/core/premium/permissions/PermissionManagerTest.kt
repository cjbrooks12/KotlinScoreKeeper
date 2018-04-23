package com.caseyjbrooks.scorekeeper.core.premium.permissions

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class PermissionManagerTest {

    lateinit var underTest: PermissionManager

    @BeforeEach
    fun setup() {
        underTest = PermissionManager(setOf(
                Permission("games", "games/**"),
                Permission("games/tally"),
                Permission("games/7wonders", "games/7wonders/**"),
                Permission("games/7wonders/base"),
                Permission("games/7wonders/duel"),
                Permission("games/7wonders/babel")
        ))
    }

    @ParameterizedTest
    @MethodSource("getTestInputsArguments")
    fun testTallyPermission(action: String, permissableActions: Array<String>, result: Int, success: Boolean) {
        assertThat(underTest.test(action, permissableActions.toList()), `is`(result))
        assertThat(underTest.can(action, permissableActions.toList()), `is`(success))
    }

    companion object {
        @JvmStatic fun getTestInputsArguments(): Stream<Arguments> {
            return listOf(
                    Arguments { arrayOf("",                     arrayOf("games/tally"),   -1, false) },
                    Arguments { arrayOf("games/tally",          arrayOf("games/tally"),    1, true)  },
                    Arguments { arrayOf("games/7wonders",       arrayOf("games/7wonders"), 1, true)  },
                    Arguments { arrayOf("games/7wonders/base",  arrayOf("games/7wonders"), 2, true)  },
                    Arguments { arrayOf("games/7wonders/duel",  arrayOf("games/7wonders"), 2, true)  },
                    Arguments { arrayOf("games/7wonders/babel", arrayOf("games/7wonders"), 2, true)  },
                    Arguments { arrayOf("games/7wonders/babel", arrayOf("games"),          2, true)  },
                    Arguments { arrayOf("games/7wonders/babel", arrayOf(""),              -2, false) }
            ).stream()
        }
    }

}