package pl.panda.trzy.analysis;

import pl.panda.trzy.nbp.Rate;

import java.util.List;
import java.util.ListIterator;

public class AnalysisApiImpl implements AnalysisApi {

    @Override
    public SessionsCount countSessions(List<Rate> rates) {
        Integer rise = 0;
        Integer fall = 0;
        Integer noChange = 0;
        ListIterator<Rate> it = rates.listIterator();
        while (it.hasNext()) {
            Rate currentRate = it.next();
            if (it.hasNext()) {
                Rate nexRate = rates.get(it.nextIndex());
                int compared = currentRate.getMid().compareTo(nexRate.getMid());
                if (compared < 0) {
                    rise++;
                } else if(compared > 0) {
                    fall++;
                } else {
                    noChange++;
                }
            }
        }
        return new SessionsCount(rise, fall, noChange);
    }
}
