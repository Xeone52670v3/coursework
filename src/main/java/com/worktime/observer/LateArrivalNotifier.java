package com.worktime.observer;

import com.worktime.model.WorkEvent;
import com.worktime.model.enums.EventType;

public class LateArrivalNotifier implements WorkEventObserver {
    @Override
    public void onEvent(WorkEvent event) {
        if (event.getType() == EventType.LATE_ARRIVAL) {
            System.out.println("[СПОВІЩЕННЯ] Запізнення: " + event.getDescription());
        }
    }
}
