package fyi.pauli.marker.test

import fyi.pauli.marker.Bootstrap
import fyi.pauli.marker.parseAllModels
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockbukkit.mockbukkit.MockBukkit
import org.mockbukkit.mockbukkit.ServerMock
import kotlin.test.Test

class ModelCoverageTest {

    private lateinit var server: ServerMock
    private lateinit var plugin: Bootstrap

    @BeforeEach
    fun setup() {
        server = MockBukkit.mock()
        plugin = MockBukkit.load(Bootstrap::class.java)
    }

    @AfterEach
    fun teardown() {
        MockBukkit.unmock()
    }

    @Test
    fun convertModels() {
        assertDoesNotThrow {
            parseAllModels(plugin)
        }
    }
}