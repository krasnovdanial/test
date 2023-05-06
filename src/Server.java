import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class Server {
    private TreeMap<Integer, Person> treeMap;
    private int pageSize;
    private int currentPage;

    public Server(List<Person> persons) {
        this.treeMap = new TreeMap<>();
        for (Person p : persons) {
            treeMap.put(p.getId(), p);
        }
        this.pageSize = 10;
        this.currentPage = 0;
    }

    public List<Person> getData(int pageNumber) {
        List<Person> pageData = new ArrayList<>();
        int lowerBound = pageNumber * pageSize + 1;
        int upperBound = lowerBound + pageSize - 1;
        Iterator<Integer> itr = treeMap.keySet().iterator();
        int currentIndex = 0;
        while (itr.hasNext() && currentIndex < upperBound) {
            currentIndex++;
            Integer key = itr.next();
            if (currentIndex >= lowerBound && currentIndex <= upperBound) {
                Person person = treeMap.get(key);
                pageData.add(person);
            }
        }
        this.currentPage = pageNumber;
        return pageData;
    }

    public List<Person> sortByColumn(String columnName) {
        List<Person> data = new ArrayList<>(treeMap.values());
        if (columnName.equals("name")) {
            Collections.sort(data, new Comparator<Person>() {
                @Override
                public int compare(Person p1, Person p2) {
                    return p1.getName().compareTo(p2.getName());
                }
            });
        } else if (columnName.equals("age")) {
            Collections.sort(data, new Comparator<Person>() {
                @Override
                public int compare(Person p1, Person p2) {
                    return p1.getAge() - p2.getAge();
                }
            });
        } else {
            Collections.sort(data, new Comparator<Person>() {
                @Override
                public int compare(Person p1, Person p2) {
                    return p1.getId() - p2.getId();
                }
            });
        }
        treeMap.clear();
        for (int i = 0; i < data.size(); i++) {
            Person person = data.get(i);
            treeMap.put(person.getId(), person);
        }
        return getData(0);
    }
}