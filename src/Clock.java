import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Clock {
    private long tick;
    private final Map<Long, List<Event>> eventQueue;

    public Clock() {
        this.tick = 0;
        this.eventQueue = new HashMap<>();
    }

    public void tick(Global g) {
        this.tick++;
        for(Long stamp : eventQueue.keySet()) {
            if(tick == stamp) {
                eventQueue.remove(stamp).forEach(event -> event.method.apply(g));
                return;
            }
        }
    }

    public void queueEvent(String eventName, int numTurns, Method event) {
        long forTick = tick + numTurns;
        if(!this.eventQueue.containsKey(forTick)) {
            this.eventQueue.put(forTick, new ArrayList<Event>());
        }
        this.eventQueue.get(forTick).add(new Event(eventName, event));
    }

    public String toString() {
        StringBuilder out = new StringBuilder("Clocker on tick " + tick + ", with queued events:\n");
        for(Map.Entry<Long, List<Event>> entry : eventQueue.entrySet().stream().sorted((eX, eY) -> Math.toIntExact((eX.getKey() - eY.getKey()))).toList()) { //this is a beast
            for(Event event : entry.getValue()) {
                out.append("\"" + event.eventName + "\" in " + (entry.getKey() - tick) + "t\n");
            }
        }
        return out.toString();
    }

    private record Event(String eventName, Method method) {}
}
