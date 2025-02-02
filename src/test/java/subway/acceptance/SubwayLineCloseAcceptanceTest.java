package subway.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.acceptance.apiTester.SubwayLineAcceptanceTest;
import subway.common.annotation.AcceptanceTest;

/**
 * 지하철 노선 제거 인수 테스트를 합니다.
 */
@AcceptanceTest
@DisplayName("지하철 노선 제거 인수 테스트")
public class SubwayLineCloseAcceptanceTest extends SubwayLineAcceptanceTest {

    /**
     * Given 지하철 노선을 생성하고<br>
     * When 생성한 지하철 노선을 삭제하면<br>
     * Then 해당 지하철 노선 정보는 삭제된다<br>
     */
    @Test
    @DisplayName("지하철 노선을 삭제한다.")
    void closeSubwayLine() {
        //given
        지하철_노선_생성("2호선","bg-green-600","교대역", "강남역", 10);
        //when
        지하철_노선_삭제("2호선");
        //then
        지하철_노선_목록_포함_여부_확인("2호선");
    }
}
