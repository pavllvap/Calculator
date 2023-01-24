package console;

import console.util.ConsoleReader;
import console.util.ConsoleWriter;
import entity.Operation;
import entity.User;
import service.CalculatorService;
import service.UserService;

import java.util.List;
import java.util.Optional;

import static console.util.ConsoleReader.*;
import static console.util.ConsoleWriter.*;


public class ConsoleApplication {

	public static ConsoleSession consoleSession;
	private final UserService userService = new UserService();
	private final CalculatorService calculatorService = new CalculatorService();

	public void run() {
		while (true) {
			if (consoleSession == null) {
				write("Hello Guest!");
				write("1 - Registration, 2 - Authorization, 3 - Exit");
				int i = readInt();
				switch (i) {
					case 1:
						write("Enter name");
						String name = readString();
						write("Enter username");
						String username = readString();
						write("Enter password");
						String pass = readString();
						User user = new User(username, pass, name);
						userService.create(user);
						continue;
					case 2:
						write("Enter username");
						String username2 = readString();
						write("Enter password");
						String pass2 = readString();
						Optional<User> byUsername = userService.findByUsername(username2);
						if (byUsername.isPresent()) {
							User user1 = byUsername.get();
							if (user1.getPassword().equals(pass2)) {
								consoleSession = new ConsoleSession(user1);
								continue;
							} else {
								write("Wrong password!");
							}
						} else {
							write("User not found!");
							continue;
						}
					case 3:
						return;
				}
			} else {
				write("Hello " + consoleSession.getCurrentUser().getName());
				write("1 - Calculator, 2 - History, 3 - Logout, 4 - Exit");
				switch (readInt()) {
					case 1:
						write("Enter num 1");
						double num1 = readDouble();
						write("Enter num 2");
						double num2 = readDouble();
						write("Enter operation type");
						String type = readString();
						Operation operation = new Operation(num1, num2, type, consoleSession.getCurrentUser());
						Optional <Operation> byResult = calculatorService.calculate(operation);
						Operation result = byResult.get();
						write("Result = " + result.getResult());
						continue;
					case 2:
						List<Operation> allByUser = calculatorService.findAllByUser(consoleSession.getCurrentUser());
						for (Operation operation1 : allByUser) {
							write(operation1.toString());
						}
						continue;
					case 3:
						consoleSession = null;
						continue;
					case 4:
						return;
				}
			}
		}
	}
}
