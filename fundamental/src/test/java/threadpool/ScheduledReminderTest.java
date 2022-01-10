package threadpool;

import com.google.common.util.concurrent.Uninterruptibles;
import concurrent.NamedThreadFactory;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledReminderTest {

  final ScheduledReminder reminder = new ScheduledReminder();

  @Test
  public void test1() {
    // 模拟其他组件同时添加 listener
    Thread t =
        new Thread(
            () -> {
              for (int i = 1; i <= 100; i++) {
                reminder.register(new RemindingListener("listener-" + i));
                Uninterruptibles.sleepUninterruptibly(300, TimeUnit.MILLISECONDS);
              }
            });
    t.setName("Listener-adder");
    t.start();

    Uninterruptibles.sleepUninterruptibly(60, TimeUnit.MINUTES);
  }

  @Test
  public void test2() {
    reminder.listeners.add(new RemindingListener("listener-1"));
    reminder.listeners.add(new RemindingListener("listener-2"));
    reminder.listeners.add(new RemindingListener("listener-3"));

    Uninterruptibles.sleepUninterruptibly(60, TimeUnit.MINUTES);
  }

  static class ScheduledReminder {
    private ScheduledThreadPoolExecutor scheduledExecutor;
    private List<RemindingListener> listeners;

    public ScheduledReminder() {
      this.scheduledExecutor =
          new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("Reminder"));
      this.listeners = new LinkedList<>();
      this.start();
    }

    public void register(RemindingListener l) {
      listeners.add(l);
      System.out.println("Added " + l);
    }

    public void start() {
      scheduledExecutor.scheduleWithFixedDelay(this::remind, 1000, 1000, TimeUnit.MILLISECONDS);
    }

    public void remind() {
      // listeners.forEach(RemindingListener::onReminding);
      System.out.println("Do reminding");
      // throw new RuntimeException("Error on reminding");
      listeners.forEach(
          l -> {
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            l.onReminding();
          });
    }
  }

  static class RemindingListener {
    String name;

    RemindingListener(String name) {
      this.name = name;
    }

    void onReminding() {
      System.out.println(name + " received a reminding.");
    }

    @Override
    public String toString() {
      return name;
    }
  }
}
