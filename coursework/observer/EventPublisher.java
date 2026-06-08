package com.worktime.observer;

import com.worktime.model.WorkEvent;

public interface EventPublisher {
    void subscribe(WorkEventObserver observer);
    void unsubscribe(WorkEventObserver observer);
    void publish(WorkEvent event);
}
