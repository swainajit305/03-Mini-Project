package in.ashokit.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.UnlockAccForm;
import in.ashokit.binding.UserForm;
import in.ashokit.service.UserMgmtService;

@RestController
public class UserRestController {
	@Autowired
	private UserMgmtService service;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
		String login = service.login(loginForm);
		return new ResponseEntity<String>(login, HttpStatus.OK);

	}

	@GetMapping("/countries")
	public Map<Integer, String> getCountries() {
		return service.getCountrys();

	}

	@GetMapping("/states/{countryId}")
	public Map<Integer, String> getStates(@PathVariable Integer countryId) {
		return service.getStates(countryId);

	}

	@GetMapping("/cities/{stateId}")
	public Map<Integer, String> getCities(@PathVariable Integer stateId) {
		return service.getCitys(stateId);

	}

	@GetMapping("/email/{email}")
	public String emailCheck(@PathVariable String email) {
		return service.checkEmail(email);

	}

	@PostMapping("/user")
	public ResponseEntity<String> register(@RequestBody UserForm form) {
		String registerUser = service.registerUser(form);
		return new ResponseEntity<String>(registerUser, HttpStatus.CREATED);

	}

	@PostMapping("/unlock")
	public ResponseEntity<String> unlockAccForm(@RequestBody UnlockAccForm accForm) {

		String unlockAccount = service.unlockAccount(accForm);
		return new ResponseEntity<String>(unlockAccount, HttpStatus.OK);

	}
	    @GetMapping("/forgot/{email}")
	public ResponseEntity<String> forgotPwd(@PathVariable String email){
		  String forgotPwd = service.forgotPwd(email);
		return new ResponseEntity<String>(forgotPwd,HttpStatus.OK);
		
	}

}
