package com.worktime.observer;

import com.worktime.model.WorkEvent;
import com.worktime.model.enums.EventType;

import java.util.*;

public class StatisticsObserver implements WorkEventObserver {
    private final List<WorkEvent> eventLog = new ArrayList<>();
    private final Map<EventType, Integer> eventCounts = new EnumMap<>(EventType.class);
    private final Map<String, Integer> lateArrivals = new HashMap<>();

    @Override
    public void onEvent(WorkEvent event) {
        eventLog.add(event);
        eventCounts.merge(event.getType(), 1, Integer::sum);

        if (event.getType() == EventType.LATE_ARRIVAL) {
            lateArrivals.merge(event.getEmployeeId(), 1, Integer::sum);
        }
    }

    public void printStats() {
        System.out.println("\n========== СТАТИСТИКА ПОДІЙ ==========");
        System.out.println("Загальна кількість подій: " + eventLog.size());
        System.out.println("\nРозбивка за типами:");
        eventCounts.forEach((type, count) ->
                System.out.printf("  %-25s : %d%n", type, count));

        if (!lateArrivals.isEmpty()) {
            System.out.println("\nЗапізнення (кількість по співробітниках):");
            lateArrivals.forEach((id, count) ->
                    System.out.println("  " + id + " — " + count + " разів"));
        }
        System.out.println("======================================\n");
    }

    public void printLog() {
        System.out.println("\n========== ЛОГ ПОДІЙ ==========");
        eventLog.forEach(System.out::println);
        System.out.println("================================\n");
    }

    public List<WorkEvent> getEventLog()              { return Collections.unmodifiableList(eventLog); }
    public Map<EventType, Integer> getEventCounts()   { return Collections.unmodifiableMap(eventCounts); }
    public int getTotalEvents()                       { return eventLog.size(); }
}
