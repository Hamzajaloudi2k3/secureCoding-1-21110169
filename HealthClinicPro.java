package SecureCode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class HealthClinicPro {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int count = 0;
        while (count != 3) {
            String username, password;

            System.out.println("Enter username ");
            username = sc.nextLine();
            MyLogger.writeToLog("the user name :"+username+" tried to log in");
            System.out.println("Enter Password");
            password = sc.nextLine();

            String hashedPassword = getHash(password);

            String userType;
            System.out.println("Enter user type");

            userType = sc.nextLine();
            ReadFile(username, hashedPassword, userType);

            count++;
        }

    }

   public  static String getHash(String value) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            result = encode(md.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("The Algorithm doesn't exist");
        }
        return result;
    }

    private static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static void ReadFile(String username, String hashedPassword, String userType) {
        BufferedReader br = null;

        try {
            // Read Doctor file
            br = new BufferedReader(new FileReader("C:\\Users\\ok\\Desktop\\secure coding\\Doctor.txt"));
            readUserFile(br, username, hashedPassword, userType, "Doctor");

            // Read Patient file
            br = new BufferedReader(new FileReader("C:\\Users\\ok\\Desktop\\secure coding\\Patient.txt"));
            readUserFile(br, username, hashedPassword, userType, "Patient");

            // Read Register file
            br = new BufferedReader(new FileReader("C:\\Users\\ok\\Desktop\\secure coding\\Register.txt"));
            readUserFile(br, username, hashedPassword, userType, "Register");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

     static void readUserFile(BufferedReader br, String username, String hashedPassword, String userType, String expectedType) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            String[] columns = line.split(",");
            if (columns.length >= 2 && columns[0].equalsIgnoreCase(username) && columns[1].equals(hashedPassword)) {

                if (userType.equals(expectedType)) {
                    switch (expectedType) {
                        case "Doctor":
                            Doctor(username);
                            break;
                        case "Patient":
                            Patient(username);
                            break;
                        case "Register":
                            Register(username);
                            break;
                        default:
                            System.out.println("Invalid Choice");
                            break;
                    }
                }
            }
        }
    }


    private static void WriteFile(String nameFile, String username, String hashedPassword, int phone, String gender,
            int age, String UserType) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader(nameFile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length >= 1 && columns[0].equalsIgnoreCase(username)) {
                    System.out.println("username already exists");
                    return;
                }
                if (columns.length >= 3 && columns[2].equals(String.valueOf(phone))) {
                    System.out.println("phone number already exists");
                    return;
                }
            }
            bw = new BufferedWriter(new FileWriter(nameFile, true));
            bw.write(username + "," + hashedPassword + "," + phone + "," + gender + "," + age + "," + UserType);
            bw.newLine();
            System.out.println("add successfully");
        } catch (IOException | NumberFormatException e) {
            System.out.println("error write to file");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                System.out.println("error closing" + e.getMessage());
            }
        }
    }

    private static void Register(String Username) {
        System.out.println("Login successful. Welcome, " + Username);

        Scanner register = new Scanner(System.in);
        System.out.println("Register new user");
        String username, password, gender, userType;
        int age, phone;

        System.out.println("Enter the user information");

        // Register the new user
        System.out.println("Username: ");
        username = register.nextLine();
        MyLogger.writeToLog("The username: " + username + " tried to log in");

        System.out.println("Password: ");
        password = register.nextLine();

        // Check if the password is valid
        if (passwordPolicy(password)) {
            String hashedPassword = getHash(password);

            System.out.println("Gender: ");
            gender = register.nextLine();

            System.out.println("Age: ");
            age = register.nextInt();

            System.out.println("Phone Number: ");
            phone = register.nextInt();
            register.nextLine(); // Consume the newline character

            System.out.println("Doctor or Patient");
            userType = register.nextLine();

            // Call the isPasswordValid function
            if (userType.equals("Doctor") || userType.equals("Patient")) {
                if (passwordPolicy(password)) {
                    // Proceed with user registration
                    if (userType.equals("Doctor")) {
                        WriteFile("C:\\Users\\ok\\Desktop\\secure coding\\Doctor.txt", username, hashedPassword, phone, gender, age, userType);
                    } else if (userType.equals("Patient")) {
                        WriteFile("C:\\Users\\ok\\Desktop\\secure coding\\Patient.txt", username, hashedPassword, phone, gender, age, userType);
                    }
                } else {
                    System.out.println("Invalid password. Registration aborted.");
                }
            } else {
                System.err.println("Invalid input");
            }
        } else {
            System.out.println("Invalid password. Registration aborted.");
        }
    }

     static boolean passwordPolicy(String password) {
        // Check if the password is at least 8 characters long and not more than 20 characters
        if (password.length() >= 8 && password.length() <= 20 &&
            password.matches(".*[A-Z].*") &&   // Contains at least one capital letter
            password.matches(".*\\d.*") &&      // Contains at least one digit
            password.matches(".*[!@#$%^&*()-_=+{};:'\",.<>/?\\[\\]\\\\].*")) {  // Contains at least one symbol
            return true;
        } else {
            return false;
        }
    }
    private static void Patient(String Username) {
        int patientChoice;
        System.out.println("login successful welcome: " + Username);
        Scanner patient = new Scanner(System.in);
        System.out.println("1- view information of patient\n");
        System.out.println("2- view medical information\n");
        patientChoice = patient.nextInt();

        if (patientChoice == 1) {
            viewInformationPatient(Username);
        } else if (patientChoice == 2) {
            medicalRecord(Username);
        } else {
            System.out.println("Invalid Choice");
        }
    }

    private static void viewInformationPatient(String Username) {
        BufferedReader pat = null;

        try {
            pat = new BufferedReader(new FileReader("C:\\Users\\ok\\Desktop\\secure coding\\Patient.txt"));
            String line;
            while ((line = pat.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length >= 5 && columns[0].equals(Username)) {
                    String patientName = columns[0];
                    int phoneNumber = Integer.parseInt(columns[2]);
                    String gender = columns[3];
                    int age = Integer.parseInt(columns[4]);

                    System.out.println("Patient Information:");
                    System.out.println("Name: " + patientName);
                    System.out.println("Phone Number: " + phoneNumber);
                    System.out.println("Gender: " + gender);
                    System.out.println("Age: " + age);

                    break; // Exit the loop after finding the patient's information
                }
            }
        } catch (IOException e) {
            System.out.println("error read the file");
        } finally {
            try {
                if (pat != null) {
                    pat.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing");
            }
        }
    }

    private static void viewInformationDoctor(String Username) {
        BufferedReader doc = null;
        try {
            doc = new BufferedReader(new FileReader("C:\\Users\\ok\\Desktop\\secure coding\\Doctor.txt"));
            String line;
            while ((line = doc.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length >= 5 && columns[0].equals(Username)) {
                    String doctorName = columns[0];
                    int phoneNumber = Integer.parseInt(columns[2]);
                    String gender = columns[3];
                    int age = Integer.parseInt(columns[4]);

                    System.out.println("Doctor Information:");
                    System.out.println("Name: " + doctorName);
                    System.out.println("Phone Number: " + phoneNumber);
                    System.out.println("Gender: " + gender);
                    System.out.println("Age: " + age);

                    break; // Exit the loop after finding the doctor's information
                }
            }
        } catch (IOException e) {
            System.out.println("error read the file");
        } finally {
            try {
                if (doc != null) {
                    doc.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing");
            }
        }
    }

    private static void medicalRecord(String Username) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader("C:\\Users\\ok\\Desktop\\secure coding\\MedicalInfo.txt"));

            String line;
            for (line = br.readLine(); line != null; line = br.readLine()) {
                String[] columns = line.split(",");

                if (columns.length >= 3 && columns[1].equals(Username)) {
                    String medicalSituation = columns[2];
                    String medicalTreatment = columns[3];

                    System.out.println("Medical Record:");
                    System.out.println("Medical Situation: " + medicalSituation);
                    System.out.println("Medical Treatment: " + medicalTreatment);

                    break; // Exit the loop after finding the medical record
                }
            }
        } catch (IOException e) {
            System.out.println("Error Read file");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                System.out.println("Error close file");
            }
        }
    }

    private static void Doctor(String Username) {
        int doctorChoice;
        System.out.println("login successful welcome: " + Username);
        Scanner doctor = new Scanner(System.in);
        System.out.println("1- view information of Doctor\n");
        System.out.println("2- Enter medical information of patient\n");
        doctorChoice = doctor.nextInt();
        if (doctorChoice == 1) {
            viewInformationDoctor(Username);
        } else if (doctorChoice == 2) {
            medInfo(Username);
        } else {
            System.out.println("Invalid choice, please try again");
        }
    }

    private static void medInfo(String dName) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter patient's name: ");
        String pName = sc.nextLine();

        System.out.println("Enter medical situation: ");
        String medicalSituation = sc.nextLine();

        System.out.println("Enter medical treatment: ");
        String medicalTreatment = sc.nextLine();

        writemedicalInfo("C:\\Users\\ok\\Desktop\\secure coding\\MedicalInfo.txt", dName, pName, medicalSituation, medicalTreatment);
    }

    private static void writemedicalInfo(String nameFile, String dName, String pName, String medicalSituation,
            String medicalTreatment) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nameFile, true))) {
            bw.write(dName + "," + pName + "," + medicalSituation + "," + medicalTreatment);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing medical information to the file: " + e.getMessage());
        }
    }
}
