import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Person {
	private String name;
	private Set<Person> friends;
	
	public Person(String name) {
		this.name = name;
		this.friends = new HashSet<>();
	}
	
	public void addFriends(Person... friends) {
		for (Person friend : friends) {
			if (!equals(friend))
				this.friends.add(friend);
		}
	}
	
	public boolean knows(Person person) {
		return friends.contains(person);
	}


	@Override
	public String toString() {
		return "%s(friends:%s)".formatted(name, friends.stream().map(it-> it.name).collect(Collectors.toList()));
	}
}
