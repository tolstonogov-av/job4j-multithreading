1. Реализовать ThreadPool [#314449]

Пул - это хранилище для ресурсов, которые можно переиспользовать.

Клиент берет ресурс из пула, выполняет свою работу и возвращает обратно в пул.

Общая схема реализации пула.

[0873958f647efb800164d1c958231d8e.png]

1. Инициализация пула должна быть по количеству ядер в системе. 

int size = Runtime.getRuntime().availableProcessors()

Количество нитей всегда одинаковое и равно size. В каждую нить передается блокирующая очередь tasks. В методе run мы должны получить задачу их очереди tasks.

tasks - это блокирующая очередь. Если в очереди нет элементов, то нить переводиться в состоянии Thread.State.WAITING.

Когда приходит новая задача, всем нитям в состоянии Thread.State.WAITING посылается сигнал проснуться и начать работу.

2. Создать метод work(Runnable job) - этот метод должен добавлять задачи в блокирующую очередь tasks.

3. Создать метод shutdown() - этот метод завершит все запущенные задачи.

Каркас класса ThreadPool:

package ru.job4j.pool;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();

    public void work(Runnable job) {

    }

    public void shutdown() {

    }
}

SimpleBlockingQueue - Это класс из задачи - 3. Уровень - Middle / Блок 1. Multithreading / 4. Wait, Notify, NotifyAll / 1. Реализовать шаблон Producer Consumer.

Задание.

1. Реализуйте требуемый в задании функционал.
