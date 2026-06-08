package com.worktime.ui;

import com.worktime.model.enums.AbsenceType;
import com.worktime.observer.LateArrivalNotifier;
import com.worktime.observer.StatisticsObserver;
import com.worktime.service.WorkTimeService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleUI {
    private final WorkTimeService service = new WorkTimeService();
    private final StatisticsObserver stats = new StatisticsObserver();
    private final Scanner sc = new Scanner(System.in);

    public void start() {
        service.subscribe(stats);
        service.subscribe(new LateArrivalNotifier());

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║  Система обліку робочого часу v1.0   ║");
        System.out.println("╚══════════════════════════════════════╝");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();
            try {
                running = handleChoice(choice);
            } catch (Exception e) {
                System.out.println("[ПОМИЛКА] " + e.getMessage());
            }
        }
        System.out.println("До побачення!");
    }

    private void printMenu() {
        System.out.println("\n--- МЕНЮ ---");
        System.out.println(" 1. Додати співробітника");
        System.out.println(" 2. Видалити співробітника");
        System.out.println(" 3. Показати всіх співробітників");
        System.out.println(" 4. Реєстрація приходу (check-in)");
        System.out.println(" 5. Реєстрація виходу (check-out)");
        System.out.println(" 6. Зареєструвати відсутність");
        System.out.println(" 7. Призначити задачу");
        System.out.println(" 8. Позначити задачу виконаною");
        System.out.println(" 9. Звіт по співробітнику");
        System.out.println("10. Статистика подій");
        System.out.println("11. Лог подій");
        System.out.println("12. Зберегти стан (Memento)");
        System.out.println("13. Відновити стан (Memento)");
        System.out.println("14. Зберегти стан у файл");
        System.out.println("15. Завантажити стан з файлу");
        System.out.println(" 0. Вихід");
        System.out.print("Виберіть: ");
    }

    private boolean handleChoice(String choice) throws IOException {
        switch (choice) {
            case "1" -> addEmployee();
            case "2" -> removeEmployee();
            case "3" -> service.printAllEmployees();
            case "4" -> checkIn();
            case "5" -> checkOut();
            case "6" -> registerAbsence();
            case "7" -> assignTask();
            case "8" -> completeTask();
            case "9" -> { System.out.print("ID співробітника: "); service.printEmployeeReport(sc.nextLine().trim()); }
            case "10" -> stats.printStats();
            case "11" -> stats.printLog();
            case "12" -> { System.out.print("ID співробітника: "); service.saveState(sc.nextLine().trim()); }
            case "13" -> { System.out.print("ID співробітника: "); service.restoreState(sc.nextLine().trim()); }
            case "14" -> saveToFile();
            case "15" -> loadFromFile();
            case "0" -> { return false; }
            default -> System.out.println("Невідома команда.");
        }
        return true;
    }

    private void addEmployee() {
        System.out.println("Тип (developer/manager/hr/analyst): ");
        String type = sc.nextLine().trim();
        System.out.print("ID: "); String id = sc.nextLine().trim();
        System.out.print("Ім'я: "); String name = sc.nextLine().trim();
        System.out.print("Email: "); String email = sc.nextLine().trim();
        System.out.print("Додатково (стек/відділ/спеціалізація, або Enter): ");
        String extra = sc.nextLine().trim();
        service.addEmployee(type, id, name, email, extra.isEmpty() ? "-" : extra);
        System.out.println("Додано: " + name);
    }

    private void removeEmployee() {
        System.out.print("ID співробітника для видалення: ");
        service.removeEmployee(sc.nextLine().trim());
        System.out.println("Видалено.");
    }

    private void checkIn() {
        System.out.print("ID: "); String id = sc.nextLine().trim();
        System.out.print("Час (yyyy-MM-ddTHH:mm, або Enter = зараз): ");
        String raw = sc.nextLine().trim();
        LocalDateTime time = raw.isEmpty() ? LocalDateTime.now() : LocalDateTime.parse(raw);
        service.checkIn(id, time);
        System.out.println("Прихід зареєстровано.");
    }

    private void checkOut() {
        System.out.print("ID: "); String id = sc.nextLine().trim();
        System.out.print("Час (yyyy-MM-ddTHH:mm, або Enter = зараз): ");
        String raw = sc.nextLine().trim();
        LocalDateTime time = raw.isEmpty() ? LocalDateTime.now() : LocalDateTime.parse(raw);
        service.checkOut(id, time);
        System.out.println("Вихід зареєстровано.");
    }

    private void registerAbsence() {
        System.out.print("ID: "); String id = sc.nextLine().trim();
        System.out.println("Тип (VACATION/SICK_LEAVE/DAY_OFF): ");
        AbsenceType type = AbsenceType.valueOf(sc.nextLine().trim().toUpperCase());
        System.out.print("З (yyyy-MM-dd): "); LocalDate from = LocalDate.parse(sc.nextLine().trim());
        System.out.print("По (yyyy-MM-dd): "); LocalDate to   = LocalDate.parse(sc.nextLine().trim());
        System.out.print("Причина: "); String reason = sc.nextLine().trim();
        service.registerAbsence(id, type, from, to, reason);
        System.out.println("Відсутність зареєстровано.");
    }

    private void assignTask() {
        System.out.print("ID співробітника: "); String empId  = sc.nextLine().trim();
        System.out.print("ID задачі: ");        String taskId = sc.nextLine().trim();
        System.out.print("Назва задачі: ");     String title  = sc.nextLine().trim();
        service.assignTask(empId, taskId, title);
        System.out.println("Задачу призначено.");
    }

    private void completeTask() {
        System.out.print("ID співробітника: "); String empId  = sc.nextLine().trim();
        System.out.print("ID задачі: ");        String taskId = sc.nextLine().trim();
        System.out.print("Витрачено годин: ");  double hours  = Double.parseDouble(sc.nextLine().trim());
        service.completeTask(empId, taskId, hours);
        System.out.println("Задачу виконано.");
    }

    private void saveToFile() throws IOException {
        System.out.print("ID співробітника: "); String id   = sc.nextLine().trim();
        System.out.print("Шлях до файлу: ");    String path = sc.nextLine().trim();
        service.saveState(id);
        service.saveStateToFile(id, path);
    }

    private void loadFromFile() throws IOException {
        System.out.print("Шлях до файлу: "); String path = sc.nextLine().trim();
        service.loadStateFromFile(path);
    }
}
