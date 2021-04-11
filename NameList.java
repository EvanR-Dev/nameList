public class NameList {
	private Node head;	// Head of list
	
	public static void main(String[] args) {
		NameList nameList = new NameList();
		
		nameList.add("Bob");
		nameList.add("Dan");
		nameList.add("Ben");
		System.out.print(nameList.toString());
		System.out.println("------------------------------");
		
		nameList.add("Deb");
		nameList.add("Sarah");
		System.out.print(nameList.toString());
		System.out.println("------------------------------");
		
		nameList.remove("Deb");
		nameList.remove("Sarah");
		System.out.print(nameList.toString());
		System.out.println("------------------------------");
		
		nameList.add("Dewy");
		nameList.add("Donna");
		nameList.add("Deb");
		nameList.add("dewitt");
		nameList.add("Dan");
		System.out.print(nameList.toString());
		System.out.println("------------------------------");
		
		nameList.remove("Ben");
		nameList.remove("Bob");
		System.out.print(nameList.toString());
		System.out.println("------------------------------");
		
		nameList.add("Alex");
		nameList.add("Xaiver");
		System.out.print(nameList.toString());
		System.out.println(nameList.find("Xaiver"));
		System.out.println("------------------------------");
		
		nameList.removeLetter("A");
		nameList.removeLetter("D");
		nameList.removeLetter("X");
		System.out.print(nameList.toString());
		System.out.println("------------------------------");
	}
	
	public void add(String name) {
		// Ignore duplicates or name is not 2 chars long
		if (name.length() < 2 || find(name)) return;
		
		Node node = new Node(name);	// Node to be added
		
		// No letter for names, add letter node
		String firstLetter = name.substring(0,1).toUpperCase();
		if (!find(firstLetter)) {
			Node letterNode = new Node(firstLetter);
			addNode(letterNode);
		}
		addNode(node);	// Add name node
	}
	
	private void addNode(Node n) {
		// Node must be inserted before or at head
		if (head == null || n.name.compareToIgnoreCase(head.name) < 0) {
			n.next = head;		// Link new node to head
			head = n;			// Node becomes new head
			return;
		}
		
		// Traverse starting at node right after head, keep track of node before
		Node prev = head, curr = head.next;		
		while (curr != null) {
			// Node must be inserted before curr node
			if (n.name.compareToIgnoreCase(curr.name) < 0) {
				prev.next = n;		// Link node before curr to n
				n.next = curr;		// Link n to curr
				return;
			}
			curr = curr.next;
			prev = prev.next;
		}
		// Node must be inserted at end
		prev.next = n;	// Link with prev node
	}
	
	public void remove(String name) {
		if (head == null || !find(name)) return;	// Empty list or name is not in list
		
		// Traverse list
		Node prev = head, curr = head.next;
		while (curr != null) {
			if (curr.name.equals(name)) {	// Name is after head
				prev.next = curr.next;		// Link node before curr to node after curr
				break;
			}
			curr = curr.next;
			prev = prev.next;
		}
		
		// Check for deleting letter
		// Head is a letter that must be deleted
		if (head.next == null || isLetter(head.name) && isLetter(head.next.name)) {
			head = head.next;		// New head is next node
			return;
		}
		
		// Check for letter to be removed after removing name
		prev = head;
		curr = head.next;
		while (curr.next != null) {
			// Letter linked to another letter (NOT a name)
			if (isLetter(curr.name) && isLetter(curr.next.name)) {
				prev.next = curr.next;	// Link prev node to node after letter to delete
				return;
			}
			curr = curr.next;
			prev = prev.next;
		}
		
		// Letter to delete is at end of list
		if (isLetter(curr.name) && curr.next == null) {
			prev.next = curr.next;
			return;
		}
	}
	
	private boolean isLetter(String str) {			// Returns if name is a letter
		return str.length() == 1;
	}
	
	public void removeLetter(String letter) {
		if (head == null || !find(letter)) return;	// Empty list or letter is not in list
		
		// Letter is head node
		if (head.name.equals(letter)) {
			// Find next letter node
			while (head.next != null && !isLetter(head.next.name))
				head = head.next;
			head = head.next;
			return;
		}
		
		// Letter is within list
		Node prev = head;
		// Find letter node deleted and store node before it
		while (!prev.next.name.equals(letter))
			prev = prev.next;
		
		// Find next letter node or null to link
		Node curr = prev.next.next;
		while (curr != null && !isLetter(curr.name))
			curr = curr.next;
		prev.next = curr;
	}
	
	public boolean find(String name) {	
		Node curr = head;		// Start at head node
		
		while (curr != null) {
			if (curr.name.equals(name))		// Name found
				return true;
			curr = curr.next;
		}
		return false;		// Name not found
	}
	
	public String toString() {
		String strFormat = "";
		Node curr = head;
		
		while (curr != null) {
			if (isLetter(curr.name))	// Add letter to str
				strFormat += curr.name;
			else	// Add word with indent to list
				strFormat += "  " + curr.name;
			strFormat += "\n";
			curr = curr.next;
		}
		return strFormat;
	}
	
	private class Node {		// Linked list node
		String name;			// Element in node
		Node next;				// Reference to next node
		
		Node(String name) {		// Constructor to create a new node
			this.name = name;	// Set name (elem) of node
		}
	}
}
