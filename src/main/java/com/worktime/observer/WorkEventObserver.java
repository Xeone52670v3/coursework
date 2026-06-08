package com.worktime.observer;

import com.worktime.model.WorkEvent;

public interface WorkEventObserver {
    void onEvent(WorkEvent event);
}
