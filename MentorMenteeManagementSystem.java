import java.util.*;

public class MentorMenteeManagementSystem {

    static Scanner scanner = new Scanner(System.in);

    static ArrayList<Mentor> mentors = new ArrayList<>();
    static ArrayList<Mentee> mentees = new ArrayList<>();

    static HashMap<String, LinkedList<Mentee>> assignRequests = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Welcome to Mentor-Mentee Management System");

        boolean exit = false;
        while (!exit) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Mentor Sign Up");
            System.out.println("2. Mentee Sign Up");
            System.out.println("3. Mentor Sign In");
            System.out.println("4. Mentee Sign In");
            System.out.println("5. Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    mentorSignUp();
                    break;
                case "2":
                    menteeSignUp();
                    break;
                case "3":
                    mentorSignIn();
                    break;
                case "4":
                    menteeSignIn();
                    break;
                case "5":
                    exit = true;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static void mentorSignUp() {
        System.out.println("\nMentor Sign Up:");
        String name = getUserInput("Enter Name: ");
        String email = getUserInput("Enter Email: ");
        String password = getUserInput("Enter Password: ");
        String mentorId = getUserInput("Enter Mentor ID (unique): ");

        if (getMentorById(mentorId) != null) {
            System.out.println("Mentor ID already exists. Sign Up failed.");
            return;
        }

        String subject = getUserInput("Enter Subject: ");
        Mentor mentor = new Mentor(name, email, password, mentorId, subject);
        mentors.add(mentor);
        System.out.println("Mentor signed up successfully!");
    }

    private static void menteeSignUp() {
        System.out.println("\nMentee Sign Up:");
        String name = getUserInput("Enter Name: ");
        String email = getUserInput("Enter Email: ");
        String password = getUserInput("Enter Password: ");
        String rollNo = getUserInput("Enter Roll No (unique): ");

        if (getMenteeByRollNo(rollNo) != null) {
            System.out.println("Roll No already exists. Sign Up failed.");
            return;
        }

        Mentee mentee = new Mentee(name, email, password, rollNo);
        mentees.add(mentee);
        System.out.println("Mentee signed up successfully!");
    }

    private static void mentorSignIn() {
        System.out.println("\nMentor Sign In:");
        String mentorId = getUserInput("Enter Mentor ID: ");
        String password = getUserInput("Enter Password: ");

        Mentor mentor = getMentorById(mentorId);
        if (mentor == null) {
            System.out.println("Mentor ID not found.");
            return;
        }
        if (!mentor.password.equals(password)) {
            System.out.println("Incorrect password.");
            return;
        }

        System.out.println("Mentor sign in successful. Welcome, " + mentor.name + "!");
        mentorMenu(mentor);
    }

    private static void menteeSignIn() {
        System.out.println("\nMentee Sign In:");
        String rollNo = getUserInput("Enter Roll No: ");
        String password = getUserInput("Enter Password: ");

        Mentee mentee = getMenteeByRollNo(rollNo);
        if (mentee == null) {
            System.out.println("Roll No not found.");
            return;
        }
        if (!mentee.password.equals(password)) {
            System.out.println("Incorrect password.");
            return;
        }

        System.out.println("Mentee sign in successful. Welcome, " + mentee.name + "!");
        menteeMenu(mentee);
    }

    private static void mentorMenu(Mentor mentor) {
        boolean logout = false;
        while (!logout) {
            System.out.println("\nMentor Menu:");
            System.out.println("1. View Assign Requests");
            System.out.println("2. View All Mentees Sorted");
            System.out.println("3. View Assigned Mentees");
            System.out.println("4. Search Mentees by Roll No");
            System.out.println("5. Send Message to Mentee");
            System.out.println("6. View Messages");
            System.out.println("7. Logout");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    viewAndManageRequests(mentor);
                    break;
                case "2":
                    viewAllMenteesSorted();
                    break;
                case "3":
                    viewAssignedMentees(mentor);
                    break;
                case "4":
                    searchMenteeByRollNoBinary();
                    break;
                case "5":
                    sendMessageToMentee(mentor);
                    break;
                case "6":
                    mentor.readAllMessages();
                    break;
                case "7":
                    logout = true;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void viewAssignedMentees(Mentor mentor) {
        if (mentor.mentees.isEmpty()) {
            System.out.println("You have no assigned mentees.");
            return;
        }
        System.out.println("Assigned Mentees:");

        // Calculate column widths
        int nameWidth = "Name".length();
        int rollNoWidth = "Roll No".length();
        int emailWidth = "Email".length();
        for (Mentee m : mentor.mentees) {
            if (m.name.length() > nameWidth) nameWidth = m.name.length();
            if (m.rollNo.length() > rollNoWidth) rollNoWidth = m.rollNo.length();
            if (m.email.length() > emailWidth) emailWidth = m.email.length();
        }

        // Create format strings
        String formatString = String.format("| %%-%ds | %%-%ds | %%-%ds |%n", nameWidth, rollNoWidth, emailWidth);
        String line = "+"
                + "-".repeat(nameWidth + 2) + "+"
                + "-".repeat(rollNoWidth + 2) + "+"
                + "-".repeat(emailWidth + 2) + "+";

        System.out.println(line);
        System.out.printf(formatString, "Name", "Roll No", "Email");
        System.out.println(line);
        for (Mentee m : mentor.mentees) {
            System.out.printf(formatString, m.name, m.rollNo, m.email);
        }
        System.out.println(line);
    }

    private static void sendMessageToMentee(Mentor mentor) {
        if (mentor.mentees.isEmpty()) {
            System.out.println("No mentees to message.");
            return;
        }
        System.out.println("Your Assigned Mentees:");
        for (Mentee m : mentor.mentees) {
            System.out.println("- Roll No: " + m.rollNo + ", Name: " + m.name);
        }
        String rollNo = getUserInput("Enter Roll No of the mentee to message: ");
        Mentee mentee = null;
        for (Mentee m : mentor.mentees) {
            if (m.rollNo.equals(rollNo)) {
                mentee = m;
                break;
            }
        }
        if (mentee == null) {
            System.out.println("Mentee not found in your assigned mentees.");
            return;
        }
        System.out.print("Enter message: ");
        String message = scanner.nextLine();

        mentor.sendMessage(mentee, message);
        System.out.println("Message sent to " + mentee.name + ".");
    }

    private static void menteeMenu(Mentee mentee) {
        boolean logout = false;
        while (!logout) {
            System.out.println("\nMentee Menu:");
            System.out.println("1. View Mentors Sorted");
            System.out.println("2. Send Assign Request to Mentor");
            System.out.println("3. View Assigned Mentors");
            System.out.println("4. Search Mentor by Mentor ID");
            System.out.println("5. Send Message to Mentor");
            System.out.println("6. View Messages");
            System.out.println("7. Logout");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    viewMentorsSorted();
                    break;
                case "2":
                    sendAssignRequest(mentee);
                    break;
                case "3":
                    viewAssignedMentors(mentee);
                    break;
                case "4":
                    searchMentorByIdBinary();
                    break;
                case "5":
                    sendMessageToMentor(mentee);
                    break;
                case "6":
                    mentee.readAllMessages();
                    break;
                case "7":
                    logout = true;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void sendMessageToMentor(Mentee mentee) {
        if (mentee.assignedMentors.isEmpty()) {
            System.out.println("No mentors to message.");
            return;
        }
        System.out.println("Your Assigned Mentors:");
        for (Mentor m : mentee.assignedMentors) {
            System.out.println("- Mentor ID: " + m.mentorId + ", Name: " + m.name);
        }
        String mentorId = getUserInput("Enter Mentor ID to message: ");
        Mentor mentor = null;
        for (Mentor m : mentee.assignedMentors) {
            if (m.mentorId.equals(mentorId)) {
                mentor = m;
                break;
            }
        }
        if (mentor == null) {
            System.out.println("Mentor not found in your assigned mentors.");
            return;
        }
        System.out.print("Enter message: ");
        String message = scanner.nextLine();

        mentee.sendMessage(mentor, message);
        System.out.println("Message sent to " + mentor.name + ".");
    }

    private static void viewAndManageRequests(Mentor mentor) {
        LinkedList<Mentee> requests = assignRequests.get(mentor.mentorId);
        if (requests == null || requests.isEmpty()) {
            System.out.println("No assign requests at the moment.");
            return;
        }

        System.out.println("Assign Requests for you:");
        while (!requests.isEmpty()) {
            Mentee mentee = requests.peek(); // peek from front (queue behavior)
            System.out.println("Request from Mentee - Name: " + mentee.name + ", Roll No: " + mentee.rollNo);
            System.out.println("Do you want to Accept or Reject the request? (A/R)");
            String decision = scanner.nextLine().trim().toUpperCase();
            if (decision.equals("A")) {
                // Assign mentee to this mentor (add to mentor's mentee list)
                mentor.mentees.add(mentee);
                // Add mentor to mentee's assigned mentors list to track assignments
                mentee.assignedMentors.add(mentor);
                System.out.println("Request accepted. Mentee assigned.");
                requests.poll(); // remove from queue
            } else if (decision.equals("R")) {
                System.out.println("Request rejected.");
                requests.poll(); // remove from queue
            } else {
                System.out.println("Invalid input. Please enter 'A' or 'R'.");
            }
        }
        assignRequests.put(mentor.mentorId, requests);
    }

    private static void viewAllMenteesSorted() {
        if (mentees.isEmpty()) {
            System.out.println("No mentees available.");
            return;
        }
        System.out.println("Sort mentees by: 1. Name  2. Roll No");
        String choice = scanner.nextLine();

        ArrayList<Mentee> sortedList = new ArrayList<>(mentees);
        if (choice.equals("1")) {
            sortedList = mergeSortMenteesByName(sortedList);
            System.out.println("Mentees sorted by Name:");
        } else if (choice.equals("2")) {
            sortedList = mergeSortMenteesByRollNo(sortedList);
            System.out.println("Mentees sorted by Roll No:");
        } else {
            System.out.println("Invalid choice. Showing unsorted mentees.");
        }

        // Calculate column widths
        int nameWidth = "Name".length();
        int rollNoWidth = "Roll No".length();
        int emailWidth = "Email".length();
        for (Mentee m : sortedList) {
            if (m.name.length() > nameWidth) nameWidth = m.name.length();
            if (m.rollNo.length() > rollNoWidth) rollNoWidth = m.rollNo.length();
            if (m.email.length() > emailWidth) emailWidth = m.email.length();
        }

        // Create format strings
        String formatString = String.format("| %%-%ds | %%-%ds | %%-%ds |%n", nameWidth, rollNoWidth, emailWidth);
        String line = "+"
                + "-".repeat(nameWidth + 2) + "+"
                + "-".repeat(rollNoWidth + 2) + "+"
                + "-".repeat(emailWidth + 2) + "+";

        // Print table header and rows
        System.out.println(line);
        System.out.printf(formatString, "Name", "Roll No", "Email");
        System.out.println(line);
        for (Mentee m : sortedList) {
            System.out.printf(formatString, m.name, m.rollNo, m.email);
        }
        System.out.println(line);
    }

    private static ArrayList<Mentee> mergeSortMenteesByName(ArrayList<Mentee> list) {
        if (list.size() < 2) return list;
        int mid = list.size() / 2;
        ArrayList<Mentee> left = new ArrayList<>(list.subList(0, mid));
        ArrayList<Mentee> right = new ArrayList<>(list.subList(mid, list.size()));

        left = mergeSortMenteesByName(left);
        right = mergeSortMenteesByName(right);

        return mergeMenteesByName(left, right);
    }

    private static ArrayList<Mentee> mergeMenteesByName(ArrayList<Mentee> left, ArrayList<Mentee> right) {
        ArrayList<Mentee> merged = new ArrayList<>();
        int i = 0, j = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).name.compareToIgnoreCase(right.get(j).name) <= 0) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }
        while (i < left.size()) merged.add(left.get(i++));
        while (j < right.size()) merged.add(right.get(j++));
        return merged;
    }

    private static ArrayList<Mentee> mergeSortMenteesByRollNo(ArrayList<Mentee> list) {
        if (list.size() < 2) return list;
        int mid = list.size() / 2;
        ArrayList<Mentee> left = new ArrayList<>(list.subList(0, mid));
        ArrayList<Mentee> right = new ArrayList<>(list.subList(mid, list.size()));

        left = mergeSortMenteesByRollNo(left);
        right = mergeSortMenteesByRollNo(right);

        return mergeMenteesByRollNo(left, right);
    }

    private static ArrayList<Mentee> mergeMenteesByRollNo(ArrayList<Mentee> left, ArrayList<Mentee> right) {
        ArrayList<Mentee> merged = new ArrayList<>();
        int i = 0, j = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).rollNo.compareToIgnoreCase(right.get(j).rollNo) <= 0) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }
        while (i < left.size()) merged.add(left.get(i++));
        while (j < right.size()) merged.add(right.get(j++));
        return merged;
    }

    private static void viewMentorsSorted() {
        if (mentors.isEmpty()) {
            System.out.println("No mentors available.");
            return;
        }
        System.out.println("Sort mentors by: 1. Name  2. Subject  3. Mentor ID");
        String choice = scanner.nextLine();

        ArrayList<Mentor> sortedList = new ArrayList<>(mentors);
        if (choice.equals("1")) {
            quickSortMentorsByName(sortedList, 0, sortedList.size() - 1);
            System.out.println("Mentors sorted by Name:");
        } else if (choice.equals("2")) {
            quickSortMentorsBySubject(sortedList, 0, sortedList.size() - 1);
            System.out.println("Mentors sorted by Subject:");
        } else if (choice.equals("3")) {
            quickSortMentorsById(sortedList, 0, sortedList.size() - 1);
            System.out.println("Mentors sorted by Mentor ID:");
        } else {
            System.out.println("Invalid choice. Showing unsorted mentors.");
        }

        // Calculate column widths
        int nameWidth = "Name".length();
        int subjectWidth = "Subject".length();
        int mentorIdWidth = "Mentor ID".length();
        int emailWidth = "Email".length();
        for (Mentor m : sortedList) {
            if (m.name.length() > nameWidth) nameWidth = m.name.length();
            if (m.subject.length() > subjectWidth) subjectWidth = m.subject.length();
            if (m.mentorId.length() > mentorIdWidth) mentorIdWidth = m.mentorId.length();
            if (m.email.length() > emailWidth) emailWidth = m.email.length();
        }

        // Create format strings
        String formatString = String.format("| %%-%ds | %%-%ds | %%-%ds | %%-%ds |%n", nameWidth, subjectWidth, mentorIdWidth, emailWidth);
        String line = "+"
                + "-".repeat(nameWidth + 2) + "+"
                + "-".repeat(subjectWidth + 2) + "+"
                + "-".repeat(mentorIdWidth + 2) + "+"
                + "-".repeat(emailWidth + 2) + "+";

        // Print table header and rows
        System.out.println(line);
        System.out.printf(formatString, "Name", "Subject", "Mentor ID", "Email");
        System.out.println(line);
        for (Mentor m : sortedList) {
            System.out.printf(formatString, m.name, m.subject, m.mentorId, m.email);
        }
        System.out.println(line);
    }

    private static void quickSortMentorsByName(ArrayList<Mentor> list, int low, int high) {
        if (low < high) {
            int pi = partitionByName(list, low, high);
            quickSortMentorsByName(list, low, pi - 1);
            quickSortMentorsByName(list, pi + 1, high);
        }
    }

    private static int partitionByName(ArrayList<Mentor> list, int low, int high) {
        Mentor pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).name.compareToIgnoreCase(pivot.name) <= 0) {
                i++;
                Mentor temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        Mentor temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        return i + 1;
    }

    private static void quickSortMentorsBySubject(ArrayList<Mentor> list, int low, int high) {
        if (low < high) {
            int pi = partitionBySubject(list, low, high);
            quickSortMentorsBySubject(list, low, pi - 1);
            quickSortMentorsBySubject(list, pi + 1, high);
        }
    }

    private static int partitionBySubject(ArrayList<Mentor> list, int low, int high) {
        Mentor pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).subject.compareToIgnoreCase(pivot.subject) <= 0) {
                i++;
                Mentor temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        Mentor temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        return i + 1;
    }

    private static void quickSortMentorsById(ArrayList<Mentor> list, int low, int high) {
        if (low < high) {
            int pi = partitionById(list, low, high);
            quickSortMentorsById(list, low, pi - 1);
            quickSortMentorsById(list, pi + 1, high);
        }
    }

    private static int partitionById(ArrayList<Mentor> list, int low, int high) {
        Mentor pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).mentorId.compareToIgnoreCase(pivot.mentorId) <= 0) {
                i++;
                Mentor temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        Mentor temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        return i + 1;
    }

    private static void sendAssignRequest(Mentee mentee) {
        System.out.println("Available Mentors:");
        for (Mentor m : mentors) {
            System.out.println("Mentor ID: " + m.mentorId + ", Name: " + m.name + ", Subject: " + m.subject);
        }
        System.out.print("Enter Mentor ID to send assign request: ");
        String mentorId = scanner.nextLine().trim();

        Mentor mentor = getMentorById(mentorId);
        if (mentor == null) {
            System.out.println("Mentor ID not found.");
            return;
        }

        LinkedList<Mentee> requests = assignRequests.getOrDefault(mentorId, new LinkedList<>());

        boolean alreadyRequested = requests.stream().anyMatch(m -> m.rollNo.equals(mentee.rollNo));
        boolean alreadyAssigned = mentor.mentees.stream().anyMatch(m -> m.rollNo.equals(mentee.rollNo));
        if (alreadyRequested) {
            System.out.println("You have already sent a request to this mentor.");
            return;
        }
        if (alreadyAssigned) {
            System.out.println("You are already assigned to this mentor.");
            return;
        }

        requests.offer(mentee);
        assignRequests.put(mentorId, requests);
        System.out.println("Assign request sent to Mentor " + mentor.name + ".");
    }

    private static void searchMenteeByRollNoBinary() {
        if (mentees.isEmpty()) {
            System.out.println("No mentees available.");
            return;
        }
        System.out.print("Enter Roll No to search (binary search): ");
        String rollNo = scanner.nextLine().trim();

        // First sort mentees by rollNo
        ArrayList<Mentee> sortedList = new ArrayList<>(mentees);
        sortedList = mergeSortMenteesByRollNo(sortedList);

        int index = binarySearchMenteeByRollNo(sortedList, rollNo);
        if (index == -1) {
            System.out.println("Mentee with Roll No " + rollNo + " not found.");
            return;
        }

        Mentee m = sortedList.get(index);

        // Display mentee info in table format
        int nameWidth = Math.max("Name".length(), m.name.length());
        int rollNoWidth = Math.max("Roll No".length(), m.rollNo.length());
        int emailWidth = Math.max("Email".length(), m.email.length());

        String formatString = String.format("| %%-%ds | %%-%ds | %%-%ds |%n", nameWidth, rollNoWidth, emailWidth);
        String line = "+"
                + "-".repeat(nameWidth + 2) + "+"
                + "-".repeat(rollNoWidth + 2) + "+"
                + "-".repeat(emailWidth + 2) + "+";

        System.out.println(line);
        System.out.printf(formatString, "Name", "Roll No", "Email");
        System.out.println(line);
        System.out.printf(formatString, m.name, m.rollNo, m.email);
        System.out.println(line);
    }

    private static int binarySearchMenteeByRollNo(List<Mentee> list, String rollNo) {
        int low = 0, high = list.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            Mentee midMentee = list.get(mid);

            int cmp = midMentee.rollNo.compareToIgnoreCase(rollNo);
            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    private static void viewAssignedMentors(Mentee mentee) {
        if (mentee.assignedMentors.isEmpty()) {
            System.out.println("You have not been assigned any mentors yet.");
            return;
        }

        // Calculate column widths
        int nameWidth = "Name".length();
        int subjectWidth = "Subject".length();
        int mentorIdWidth = "Mentor ID".length();
        int emailWidth = "Email".length();
        for (Mentor m : mentee.assignedMentors) {
            if (m.name.length() > nameWidth) nameWidth = m.name.length();
            if (m.subject.length() > subjectWidth) subjectWidth = m.subject.length();
            if (m.mentorId.length() > mentorIdWidth) mentorIdWidth = m.mentorId.length();
            if (m.email.length() > emailWidth) emailWidth = m.email.length();
        }

        // Create format string
        String formatString = String.format("| %%-%ds | %%-%ds | %%-%ds | %%-%ds |%n", nameWidth, subjectWidth, mentorIdWidth, emailWidth);
        String line = "+"
                + "-".repeat(nameWidth + 2) + "+"
                + "-".repeat(subjectWidth + 2) + "+"
                + "-".repeat(mentorIdWidth + 2) + "+"
                + "-".repeat(emailWidth + 2) + "+";

        // Print table header and rows
        System.out.println(line);
        System.out.printf(formatString, "Name", "Subject", "Mentor ID", "Email");
        System.out.println(line);
        for (Mentor m : mentee.assignedMentors) {
            System.out.printf(formatString, m.name, m.subject, m.mentorId, m.email);
        }
        System.out.println(line);
    }

    private static void searchMentorByIdBinary() {
        if (mentors.isEmpty()) {
            System.out.println("No mentors available.");
            return;
        }
        System.out.print("Enter Mentor ID to search (binary search): ");
        String mentorId = scanner.nextLine().trim();

        ArrayList<Mentor> sortedMentors = new ArrayList<>(mentors);
        quickSortMentorsById(sortedMentors, 0, sortedMentors.size() - 1);

        int index = binarySearchMentorById(sortedMentors, mentorId);
        if (index == -1) {
            System.out.println("Mentor with ID " + mentorId + " not found.");
            return;
        }

        Mentor m = sortedMentors.get(index);

        // Display mentor info in table format
        int nameWidth = Math.max("Name".length(), m.name.length());
        int subjectWidth = Math.max("Subject".length(), m.subject.length());
        int mentorIdWidth = Math.max("Mentor ID".length(), m.mentorId.length());
        int emailWidth = Math.max("Email".length(), m.email.length());

        String formatString = String.format("| %%-%ds | %%-%ds | %%-%ds | %%-%ds |%n", nameWidth, subjectWidth, mentorIdWidth, emailWidth);
        String line = "+"
                + "-".repeat(nameWidth + 2) + "+"
                + "-".repeat(subjectWidth + 2) + "+"
                + "-".repeat(mentorIdWidth + 2) + "+"
                + "-".repeat(emailWidth + 2) + "+";

        System.out.println(line);
        System.out.printf(formatString, "Name", "Subject", "Mentor ID", "Email");
        System.out.println(line);
        System.out.printf(formatString, m.name, m.subject, m.mentorId, m.email);
        System.out.println(line);
    }

    private static int binarySearchMentorById(List<Mentor> list, String mentorId) {
        int low = 0, high = list.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            Mentor midMentor = list.get(mid);

            int cmp = midMentor.mentorId.compareToIgnoreCase(mentorId);
            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    private static Mentor getMentorById(String mentorId) {
        for (Mentor mentor : mentors) {
            if (mentor.mentorId.equals(mentorId)) {
                return mentor;
            }
        }
        return null;
    }

    private static Mentee getMenteeByRollNo(String rollNo) {
        for (Mentee mentee : mentees) {
            if (mentee.rollNo.equals(rollNo)) {
                return mentee;
            }
        }
        return null;
    }

    static abstract class User {
        String name;
        String email;
        String password;

        // Messages received by user - map from user (sender) to list of messages
        HashMap<User, ArrayList<String>> messageBox = new HashMap<>();

        User(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }

        // Method to send message to another user
        void sendMessage(User receiver, String message) {
            receiver.receiveMessage(this, message);
        }

        // Method to receive message from another user
        void receiveMessage(User sender, String message) {
            ArrayList<String> messages = messageBox.getOrDefault(sender, new ArrayList<>());
            messages.add(message);
            messageBox.put(sender, messages);
        }

        // Method to read messages from a specific sender
        void readMessagesFrom(User sender) {
            ArrayList<String> messages = messageBox.get(sender);
            if (messages == null || messages.isEmpty()) {
                System.out.println("No messages from " + sender.name + ".");
            } else {
                System.out.println("Messages from " + sender.name + ":");
                for (String msg : messages) {
                    System.out.println("- " + msg);
                }
            }
        }

        // Method to read all messages from all senders
        void readAllMessages() {
            if (messageBox.isEmpty()) {
                System.out.println("No messages.");
                return;
            }
            for (User sender : messageBox.keySet()) {
                readMessagesFrom(sender);
            }
        }
    }

    static class Mentor extends User {
        String mentorId;
        String subject;
        ArrayList<Mentee> mentees = new ArrayList<>();

        Mentor(String name, String email, String password, String mentorId, String subject) {
            super(name, email, password);
            this.mentorId = mentorId;
            this.subject = subject;
        }
    }

    static class Mentee extends User {
        String rollNo;
        ArrayList<Mentor> assignedMentors = new ArrayList<>();

        Mentee(String name, String email, String password, String rollNo) {
            super(name, email, password);
            this.rollNo = rollNo;
        }
    }
}