package p12.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class MultiQueueImpl<T, Q> implements MultiQueue<T, Q>{
    private final Map<Q, Queue<T>> code;

    public MultiQueueImpl() {
        code=new HashMap<>();
    }

    @Override
    public Set<Q> availableQueues() {
        Set<Q> ris=new HashSet<>();
        for (Q q : code.keySet()) {
            ris.add(q);
        }
        return ris;
    }

    @Override
    public void openNewQueue(Q queue) {
        if(code.containsKey(queue)) {
            throw new IllegalArgumentException("Già presente");
        }
        Queue<T> a=new LinkedList<>();
        code.put(queue, a);
    }

    @Override
    public boolean isQueueEmpty(Q queue) {
        if(code.containsKey(queue)) {
            return code.get(queue).isEmpty();
        } else {
            throw new IllegalArgumentException("Coda inesistente");
        }
    }

    @Override
    public void enqueue(T elem, Q queue) {
        if(code.containsKey(queue)) {
            code.get(queue).add(elem);
        } else {
            throw new IllegalArgumentException("La coda non esiste");  
        }
    }

    @Override
    public T dequeue(Q queue) {
        if(code.containsKey(queue)) {
            return code.get(queue).poll();
        } else {
            throw new IllegalArgumentException("La Coda non esiste");
        }
    }

    @Override
    public Map<Q, T> dequeueOneFromAllQueues() {
        Map<Q, T> mappa=new HashMap<>();
        Set<Q> setChiavi=new HashSet<>();
        setChiavi=code.keySet();
        for (Q q : setChiavi) {
            mappa.put(q, code.get(q).poll());
        }
        return mappa;
    }

    @Override
    public Set<T> allEnqueuedElements() {
        Set<Q> setChiavi = new HashSet<>();
        setChiavi=code.keySet();
        Set<T> elementi=new HashSet<>();
        for (Q q : setChiavi) {
            elementi.addAll(code.get(q));
        }
        return elementi;
    }

    @Override
    public List<T> dequeueAllFromQueue(Q queue) {
        if(code.containsKey(queue)) {
            List<T> ris=new ArrayList<>();
            ris.addAll(code.get(queue));
            code.get(queue).clear();
            return ris;
        } else {
            throw new IllegalArgumentException("la coda non esiste");
        }
    }

    @Override
    public void closeQueueAndReallocate(Q queue) {
        if(code.containsKey(queue)) {
            if(code.size()!=1)  {
                Queue coda=new LinkedList<>();
                coda=code.get(queue);
                code.remove(queue);
                for (Q chiave : code.keySet()) {
                    code.get(chiave).addAll(coda);
                }
            } else {
                throw new IllegalStateException("non ci sono altre code in cui inserire i valori");
            }
        } else {
            throw new IllegalArgumentException("la coda non esiste");
        }
    }

}
