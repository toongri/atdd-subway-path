package subway.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import subway.acceptance.apiTester.SubwayLineAcceptanceTest;
import subway.acceptance.apiTester.SubwaySectionAddApiTester;
import subway.acceptance.apiTester.SubwaySectionCloseApiTester;
import subway.application.query.response.StationResponse;
import subway.application.query.response.SubwayLineResponse;
import subway.common.annotation.AcceptanceTest;

/**
 * 지하철 노선 추가 인수 테스트를 합니다.
 */
@AcceptanceTest
@DisplayName("지하철 노선 구간 삭제 인수 테스트")
public class SubwaySectionCloseAcceptanceTest extends SubwayLineAcceptanceTest {

    @Autowired private SubwaySectionCloseApiTester deleteTester;
    @Autowired private SubwaySectionAddApiTester addTester;

    private StationResponse 서울대입구역;
    private StationResponse 강남역;
    private StationResponse 성수역;

    @BeforeEach
    protected void setUp() {
        서울대입구역 = 지하철_역_생성("서울대입구역").as(StationResponse.class);
        강남역 = 지하철_역_생성("강남역").as(StationResponse.class);
        성수역 = 지하철_역_생성("성수역").as(StationResponse.class);

    }

    /**
     * Given 지하철 노선을 생성하고<br>
     * Given 지하철 노선에 구간을 추가 되었다면<br>
     * When 지하철 노선의 하행 종점역을 삭제했을 때<br>
     * Then 지하철 노션 상세 조회에서 추가된 노선을 확인할 수 있다.<br>
     */
    @Test
    @DisplayName("지하철 노선의 하행 종점역을 삭제하면 노선 상세 조회에서 확인 불가능")
    void addSubwayLineSection() {
        //given
        SubwayLineResponse 이호선 = 지하철_노선_생성("이호선", "red", 서울대입구역, 강남역, 5).as(SubwayLineResponse.class);
        addTester.노선에_구간을_추가한다(이호선, 강남역, 성수역, 8);
        //when
        deleteTester.노선에_구간을_삭제한다(이호선, 성수역);

        //then
        지하철_노선_구간_미포함_확인(이호선, 성수역);
    }

    /**
     * Given 지하철 노선을 생성하고<br>
     * Given 지하철 노선에 구간을 추가 되었다면<br>
     * When 지하철 노선의 하행 종점역이 아닌 곳을 삭제할 때<br>
     * Then 에러가 발생한다.<br>
     */
    @Test
    @DisplayName("지하철 노선의 하행 종점역이 아닌 곳을 삭제하면 에러 발생")
    void throwWhenNotDownStation() {
        //given
        SubwayLineResponse 이호선 = 지하철_노선_생성("이호선", "red", 서울대입구역, 강남역, 5).as(SubwayLineResponse.class);
        addTester.노선에_구간을_추가한다(이호선, 강남역, 성수역, 8);

        //when
        ExtractableResponse<Response> response = deleteTester.노선에_구간을_삭제한다(이호선, 강남역);

        //then
        deleteTester.삭제하는_구간의_하행역이_하행_종점역이_아니면_에러_발생(response);

    }

    /**
     * Given 지하철 노선의 구간이 하나라면<br>
     * When 구간을 삭제할 때<br>
     * Then 에러가 발생한다.<br>
     */
    @Test
    @DisplayName("지하철 노선의 구간이 하나일 때 구간을 삭제하면 에러 발생")
    void throwWhenExistsDownStation() {
        //given
        SubwayLineResponse 이호선 = 지하철_노선_생성("이호선", "red", 서울대입구역, 강남역, 5).as(SubwayLineResponse.class);

        //when
        ExtractableResponse<Response> response = deleteTester.노선에_구간을_삭제한다(이호선, 강남역);

        //then
        deleteTester.구간이_하나뿐인_노선은_삭제_시도_시에_에러_발생(response);

    }
}
