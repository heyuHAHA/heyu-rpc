package cluster.loadbalance;

import rpc.Referer;
import rpc.Request;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ActiveWeightLoadBalance<T> extends AbstractLoadBalance<T> {

    @Override
    protected void doSelectToHolder(Request request, List<Referer<T>> refersHolder) {
        List<Referer<T>> referers = getReferers();

        int referSize = referers.size();
        int startIndex = ThreadLocalRandom.current().nextInt(referSize);
        int currentCursor = 0;
        int currentAvailableCursor = 0;

        while (currentAvailableCursor < MAX_REFERER_COUNT && currentCursor < referSize) {
            Referer<T> temp = referers.get(((startIndex + currentCursor) % referSize));
            currentCursor++;

            if (!temp.isAvailable()) {
                continue;
            }
            currentAvailableCursor++;
            refersHolder.add(temp);
        }
        Collections.sort(refersHolder, new LowActivePriorityComparator<T>());
    }

    static class LowActivePriorityComparator<T> implements Comparator<Referer<T>> {
        @Override
        public int compare(Referer<T> o1, Referer<T> o2) {
            return o1.activeRefererCount() - o2.activeRefererCount();
        }
    }
}
