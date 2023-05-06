package in.ashokit.service;

import java.util.Map;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.UnlockAccForm;
import in.ashokit.binding.UserForm;


public interface UserMgmtService {
	public String checkEmail(String email);

	public Map<Integer, String> getCountrys();

	public Map<Integer, String> getStates(Integer countryId);

	public Map<Integer, String> getCitys(Integer stateId);

	public String registerUser(UserForm user);

	public String unlockAccount(UnlockAccForm unlockForm);

	public String login(LoginForm loginForm);

	public String forgotPwd(String email);

}
