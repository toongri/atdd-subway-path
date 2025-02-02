package subway.db.h2.adapter;

import subway.core.common.PersistenceAdapter;
import subway.db.h2.repository.SubwayLineJpaRepository;
import subway.application.out.SubwayLineClosePort;
import subway.domain.SubwayLine;

@PersistenceAdapter
public class SubwayLineClosePersistenceAdapter implements SubwayLineClosePort {

    private final SubwayLineJpaRepository subwayLineJpaRepository;

    public SubwayLineClosePersistenceAdapter(SubwayLineJpaRepository subwayLineJpaRepository) {
        this.subwayLineJpaRepository = subwayLineJpaRepository;
    }

    @Override
    public void closeSubwayLine(SubwayLine.Id id) {
        subwayLineJpaRepository.deleteById(id.getValue());
    }
}
