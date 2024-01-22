import java.util.Arrays;
import java.util.Random;

public class Main {

	public static void main(String[] args) {

		int peopleCount = 100_000;
		Person[] people = new Person[peopleCount];

		for (int i = 0; i < peopleCount; i++) {
			people[i] = new Person("person" + i);
		}

		fillRandom(people);


		System.out.println(Arrays.toString(people));
		long time = System.nanoTime();
		System.out.println(getCelebrity(people));
		System.out.printf("%,d nanoseconds\n", (System.nanoTime() - time));

		time = System.nanoTime();
		System.out.println(getCelebrity2(people));
		System.out.printf("%,d nanoseconds\n", (System.nanoTime() - time));
	}


	private static void fill(Person[] people) {
		//		[person1(friends:[person5, person3]), 
//		person2(friends:[person8, person9, person4, person11]), 
//		person3(friends:[person9]), 
//		person4(friends:[person7, person8]), 
//		person5(friends:[person7, person12, person6, person4]), 
//		person6(friends:[person7, person10]),
//		person7(friends:[person11]), 
//		person8(friends:[person12, person5, person10, person3, person11]), 
//		person9(friends:[person8]), 
//		person10(friends:[person7, person12, person9, person11, person1]), 
//		person11(friends:[person7, person6, person2, person9]), 
//		person12(friends:[person8, person4])]

		people[0].addFriends(people[4], people[2]); //[person0(friends:[person4, person2]), 
		people[1].addFriends(people[7], people[8], people[3], people[10]); //person1(friends:[person7, person8, 
		// person3, person10]),
		people[2].addFriends(people[8]);//person2(friends:[person8]), 
		people[3].addFriends(people[6], people[7]);//person3(friends:[person6, person7]), 
		people[4].addFriends(people[6], people[11], people[5], people[3]); //person4(friends:[person6, person11, person5, person3]), 
		people[5].addFriends(people[6], people[9]);//person5(friends:[person6, person9]),
		people[6].addFriends(people[10]);//	person6(friends:[person10]), 
		people[7].addFriends(people[11], people[4], people[9], people[2], people[10]);//person7(friends:[person11, person4, person9, person2, person10]), 
		people[8].addFriends(people[7]);//person8(friends:[person7]), 
		people[9].addFriends(people[6], people[11], people[8], people[10], people[0]);//person9(friends:[person6, 
		// person11, person8, person10, person0]), 
		people[10].addFriends(people[6], people[5], people[1], people[8]);//person10(friends:[person6, person5, person1, person8]),  
		people[11].addFriends(people[7], people[3]);//person11(friends:[person7, person3])]  
	}


	private static void fillRandom(Person[] people) {
		Random random = new Random();
		int celebrityIndex = random.nextInt(people.length + 1);
//		int celebrityIndex = -1;

		for (int k = 0; k < people.length; k++) {
			int i = random.nextInt(people.length);
			int j = random.nextInt(people.length);
			if (i == celebrityIndex)
				continue;

			people[i].addFriends(people[j]);
		}

		if (celebrityIndex >= 0 && celebrityIndex < people.length) {
			for (Person person : people) {
				person.addFriends(people[celebrityIndex]);
			}
		}
	}


	public static Person getCelebrity(Person[] people) {
		int count = 0;
		if (people == null || people.length == 0) {
			System.out.printf("Tries: %,d\n", count);
			return null;
		}
		
		for (Person p1 : people) {
			boolean found = true;

			for(Person p2 : people) {
				count++;
				if (p1.equals(p2))
					continue;
				if (p1.knows(p2) || !p2.knows(p1)) {
					found = false;
					break;
				}
			}
			
			if (found) {
				System.out.printf("Tries: %,d\n", count);
				return p1;
			}
		}

		System.out.printf("Tries: %,d\n", count);
		return null;
	}


	public static Person getCelebrity2(Person[] people) {
		int count = 0;
		if (people == null || people.length == 0) {
			System.out.printf("Tries: %,d\n", count);
			return null;
		}
		
		int l = 0, r = people.length - 1;
		
		while (l < r) {
			count++;
			if (people[l].knows(people[r])) {
				if (people[r].knows(people[l])) {
					//people[r] is not celebrity
					r--;
				}
				//people[l] is not celebrity
				l++;
			} else if (people[r].knows(people[l])) {
				//people[r] is not celebrity
				r--;
			} else {
				//neither people[l] nor people[r] is celebrity
				l++;
				r--;
			}
		}

		Person celebrity = people[l];

		for(Person person : people) {
			count++;

			if (celebrity.equals(person))
				continue;

			if (celebrity.knows(person) || !person.knows(celebrity)) {
				System.out.printf("Tries: %,d\n", count);
				return null;
			}
		}

		System.out.printf("Tries: %,d\n", count);
		return celebrity;
	}
}