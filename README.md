
> Курсова робота з дисципліни «Об'єктно-орієнтоване програмування» | Варіант 18 | Кб-31

## Про проєкт

Консольна Java-застосунок для автоматизації обліку робочого часу співробітників. Система фіксує приходи/виходи, відсутності, задачі та генерує звіти. Реалізовано чотири патерни проєктування та повний цикл серіалізації стану.

## Технології

![Java](https://img.shields.io/badge/Java-26-orange?style=flat-square&logo=openjdk)
![OOP](https://img.shields.io/badge/OOP-Patterns-blue?style=flat-square)
![License](https://img.shields.io/badge/license-GPL--3.0-green?style=flat-square)

## Патерни проєктування

| Патерн | Клас | Призначення |
|---|---|---|
| **Singleton** | `EmployeeRepository` | Централізоване сховище співробітників |
| **Abstract Factory** | `EmployeeFactory` + підкласи | Створення об'єктів за типом ролі |
| **Observer** | `StatisticsObserver`, `LateArrivalNotifier` | Сповіщення про події, збір статистики |
| **Memento** | `EmployeeMemento`, `MementoCaretaker` | Серіалізація та відновлення стану |

## Структура проєкту

```
src/main/java/com/worktime/
├── model/          # Сутності: Employee, TimeRecord, WorkTask, Absence
│   └── enums/      # EmployeeRole, AbsenceType, EventType
├── factory/        # Abstract Factory: 4 типи співробітників
├── observer/       # Observer: публікація подій, статистика
├── repository/     # Singleton: сховище співробітників
├── memento/        # Memento: збереження/відновлення стану
├── service/        # WorkTimeService: бізнес-логіка
├── ui/             # ConsoleUI: інтерфейс користувача
├── exception/      # 5 кастомних виключень
└── Main.java
```

## Збірка та запуск

```bash
# Клонувати репозиторій
git clone https://github.com/Xeone52670v3/coursework.git
cd coursework

# Скомпілювати (розроблено на JDK 26, сумісно з 18+)
find src -name "*.java" > sources.txt
mkdir -p out
javac -encoding UTF-8 -d out @sources.txt

# Запустити
java -cp out com.worktime.Main
```

## Функціонал

- ✅ Реєстрація приходу / виходу з фіксацією часу
- ✅ Автоматичне визначення запізнень (після 9:00)
- ✅ Облік відпусток, лікарняних та відгулів
- ✅ Призначення та виконання задач з трекінгом годин
- ✅ Звіти по кожному співробітнику
- ✅ Статистика подій та лог усіх дій
- ✅ Збереження/відновлення стану (Memento + файл)
- ✅ Сповіщення про запізнення в реальному часі

## Ієрархія класів

```
Employee (abstract)
├── Developer   — стек технологій
├── Manager     — відділ
├── HRSpecialist — кількість підопічних
└── Analyst     — спеціалізація
```

---

> ВСП «ФКЗІ ДУІТЗ» · 2026
