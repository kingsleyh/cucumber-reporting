package net.masterthought.cucumber.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import net.masterthought.cucumber.generators.integrations.PageTest;
import net.masterthought.cucumber.json.Hook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
class UtilTest extends PageTest {

    @BeforeEach
    void setUp() {
        setUpWithJson(SAMPLE_JSON);
    }

    @Test
    void formatAsPercentage_ReturnsFormattedValue() {

        // given
        final int[][] values = {{1, 3}, {2, 2}, {1, 5}, {0, 5}};
        String[] formatted = {"33.33%", "100.00%", "20.00%", "0.00%"};

        // then
        for (int i = 0; i < values.length; i++) {
            assertThat(Util.formatAsPercentage(values[i][0], values[i][1])).isEqualTo(formatted[i]);
        }
    }

    @Test
    void formatAsPercentage_OnZeroTotal_ReturnsFormattedValue() {

        // given
        final int[] values = {1, 2, 0};

        // when & then
        for (int value : values) {
            assertThat(Util.formatAsPercentage(value, 0)).isEqualTo("0.00%");
        }
    }

    @Test
    void formatAsDecimal_ReturnsFormattedValue() {

        // given
        final int[][] values = {{1, 3}, {2, 2}, {1, 5}, {0, 5}, {0, 0}};
        String[] formatted = {"33.33", "100.00", "20.00", "0.00", "0.00"};

        // when & then
        for (int i = 0; i < values.length; i++) {
            assertThat(Util.formatAsDecimal(values[i][0], values[i][1])).isEqualTo(formatted[i]);
        }
    }

    @Test
    void toValidFileName_RemovesInvalidChars() {

        // given
        final String[] ids = {"simpleFile", "file-dash", "東京", "żółć"};
        final String[] hashes = {"715485773", "784542018", "2148324698", "2159047995"};

        // when & then
        for (int i = 0; i < ids.length; i++) {
            assertThat(Util.toValidFileName(ids[i])).isEqualTo(hashes[i]);
        }
    }

    @Test
    void eliminateEmptyHooks_RemovesEmptyHooks() {

        // given
        Hook[] hooks = features.get(0).getElements()[0].getBefore();

        // when
        List<Hook> reducedHooks = Util.eliminateEmptyHooks(hooks);

        // then
        assertThat(reducedHooks).isEmpty();
    }

    @Test
    public void formatDuration_FormatsDifferentDurations() {
        // Arrange
        final long[] durations = {0, 1000, 60000, 3600000, 86400000};
        String[] formatted = {"0.000", "0.001", "1.000", "60.000", "1440.000"};

        // Act, Assert
        for (int i = 0; i < durations.length; i++) {
            assertThat(Util.formatDuration(durations[i])).isEqualTo(formatted[i]);
        }
    }
}
