package in.ashokit.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.UnlockAccForm;
import in.ashokit.binding.UserForm;
import in.ashokit.entity.CityMaster;
import in.ashokit.entity.CountryMaster;
import in.ashokit.entity.StateMaster;
import in.ashokit.entity.User;
import in.ashokit.repo.CityRepository;
import in.ashokit.repo.CountryRepository;
import in.ashokit.repo.StateRepository;
import in.ashokit.repo.UserRepository;
import in.ashokit.utl.EmailUtils;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

	@Autowired
	private UserRepository uRepo;
	@Autowired
	private CountryRepository cRepo;
	@Autowired
	private StateRepository sRepo;
	@Autowired
	private CityRepository ciRepo;
	@Autowired
	private EmailUtils utils;

	@Override
	public String checkEmail(String email) {

		User user = uRepo.findByEmail(email);

		if (user == null) {
			return "UNIQUE";
		}

		return "DUPLICATE";
	}

	@Override
	public Map<Integer, String> getCountrys() {
		List<CountryMaster> countries = cRepo.findAll();
		Map<Integer, String> countryMap = new HashMap<Integer, String>();
		countries.forEach(country -> {
			countryMap.put(country.getCountryId(), country.getCountryName());
		});
		return countryMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		List<StateMaster> states = sRepo.findByCountryId(countryId);
		Map<Integer, String> stateMap = new HashMap<Integer, String>();
		states.forEach(state -> {
			stateMap.put(state.getStateId(), state.getStateName());
		});

		return stateMap;
	}

	@Override
	public Map<Integer, String> getCitys(Integer stateId) {
		List<CityMaster> cities = ciRepo.findByStateId(stateId);
		Map<Integer, String> cityMap = new HashMap<Integer, String>();
		cities.forEach(city -> {
			cityMap.put(city.getCityId(), city.getCityName());
		});

		return cityMap;
	}

	@Override
	public String registerUser(UserForm userForm) {
		User entity = new User();

		BeanUtils.copyProperties(userForm, entity);
		entity.setUserPwd(generatePassword());
		entity.setAccStatus("LOCKED");
		uRepo.save(entity);

		String to = userForm.getEmail();
		String subject = "Registration Email";
		String body = readSendEmailBody("REG_EMAIL_BODY.txt", entity);

		utils.sendEmails(to, subject, body);

		return "Account Created Sucessful";
	}

	@Override
	public String unlockAccount(UnlockAccForm unlockForm) {
		String email = unlockForm.getEmail();

		User user = uRepo.findByEmail(email);
		if (user != null && user.getUserPwd().equals(unlockForm.getTempPwd())) {
			user.setUserPwd(unlockForm.getNewPwd());
			user.setAccStatus("UNLOCKED");
			uRepo.save(user);
			return "Account Unlocked";

		}

		return "Invalid Temporary Password";
	}

	@Override
	public String login(LoginForm loginForm) {
		User user = uRepo.findByEmailAndUserPwd(loginForm.getEmail(), loginForm.getPwd());
		if (user == null) {
			return "Invalid Credentials";
		}
		if (user.getAccStatus().equals("LOCKED")) {
			return "Account Locked";
		}
		return "Success";
	}

	@Override
	public String forgotPwd(String email) {
		User user = uRepo.findByEmail(email);
		if (user == null) {
			return "No Account Found";
		}
		String subject = "Recover Password";
		String body = readSendEmailBody("FORGOT_PWD_BODY.txt", user);
		 utils.sendEmails(email, subject, body);
		
		return "Password Sent To Register Email";
	}

	private String generatePassword() {
		String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();

		int pwdLength = 6;
		for (int i = 1; i <= pwdLength; i++) {
			int index = random.nextInt(text.length());
			sb.append(text.charAt(index));

		}

		return sb.toString();

	}

	private String readSendEmailBody(String filename, User user) {
		StringBuffer sb=new StringBuffer();
		try (Stream<String> lines = Files.lines(Paths.get(filename));) {

			lines.forEach(line -> {
				line = line.replace("${FNAME}", user.getFname());
				line = line.replace("${LNAME}", user.getLname());
				line = line.replace("${TEMP_PWD}", user.getUserPwd());
				line = line.replace("${EMAIL}", user.getEmail());
				line = line.replace("${PWD}", user.getUserPwd());
				sb.append(line);

			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();

	}

}
