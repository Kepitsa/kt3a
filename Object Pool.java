// Порождающий паттерн: Пул объектов (Object Pool)

// Описание:
// Пул объектов - это порождающий паттерн, который используется для управления 
// группой повторно используемых объектов. Этот паттерн помогает избегать 
// затрат на создание и уничтожение объектов, обеспечивая кэширование объектов 
// для повторного использования

// Применимость:
// Когда создание и уничтожение объектов являются дорогостоящими операциями
// Когда система должна управлять большим количеством однотипных объектов
// Когда требуется улучшить производительность за счет повторного использования объектов

// Класс объекта, который будет управляться пулом
class PooledObject {
    // Пример данных объекта
    private String data;

    public PooledObject(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

// Класс пула объектов
class ObjectPool {
    // Список доступных объектов
    private List<PooledObject> availableObjects = new ArrayList<>();
    // Список занятых объектов
    private List<PooledObject> inUseObjects = new ArrayList<>();

    // Метод для получения объекта из пула
    public synchronized PooledObject acquireObject() {
        if (availableObjects.isEmpty()) {
            // Если нет доступных объектов, создать новый
            PooledObject newObj = new PooledObject("New Object");
            inUseObjects.add(newObj);
            return newObj;
        } else {
            // Если есть доступные объекты, взять первый из списка
            PooledObject obj = availableObjects.remove(0);
            inUseObjects.add(obj);
            return obj;
        }
    }

    // Метод для возврата объекта в пул
    public synchronized void releaseObject(PooledObject obj) {
        inUseObjects.remove(obj);
        availableObjects.add(obj);
    }

    // Метод для проверки количества доступных объектов
    public synchronized int getAvailableObjectsCount() {
        return availableObjects.size();
    }

    // Метод для проверки количества занятых объектов
    public synchronized int getInUseObjectsCount() {
        return inUseObjects.size();
    }
}

// Пример использования пула объектов
public class Main {
    public static void main(String[] args) {
        ObjectPool pool = new ObjectPool();

        // Получить объект из пула
        PooledObject obj1 = pool.acquireObject();
        System.out.println("Acquired Object 1: " + obj1.getData());

        // Вернуть объект в пул
        pool.releaseObject(obj1);
        System.out.println("Released Object 1");

        // Проверить количество доступных и занятых объектов
        System.out.println("Available Objects: " + pool.getAvailableObjectsCount());
        System.out.println("In Use Objects: " + pool.getInUseObjectsCount());
    }
}

// Преимущества:
// 1. Повышение производительности за счет повторного использования объектов
// 2. Снижение затрат на создание и уничтожение объектов
// 3. Упрощение управления ресурсами в системе

// Недостатки:
// 1. Усложнение кода за счет необходимости управления пулом объектов
// 2. Потенциальные проблемы с потокобезопасностью при использовании в многопоточных системах
// 3. Возможно, потребуется дополнительная логика для инициализации и сброса состояния объектов
