package SecureCode;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DoctorTest {

	//@Test
	//public void DoctorLogin() //{
		 //HealthClinicPro health1 = new HealthClinicPro();
		    //String doctor = "Saud";
		    //String ValidPassword = "S@ud_2k3";
		    //String hashPass = health1.getHash(ValidPassword);
		    //String userType = "Doctor";
		    //health1.ReadFile(doctor, hashPass, userType);
	
	//}//
	@Test
	public void DoctorLogin1() {
		HealthClinicPro health2 = new HealthClinicPro();
		String ValidPassword="S@ud_2k3";
		boolean isCorrect= HealthClinicPro.passwordPolicy(ValidPassword);
		assertFalse(isCorrect);
	}
}
