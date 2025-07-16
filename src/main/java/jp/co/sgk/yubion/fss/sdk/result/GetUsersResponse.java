package jp.co.sgk.yubion.fss.sdk.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.sgk.yubion.fss.sdk.data.user.UserDataWithCredentialCount;
import java.util.List;

/**
 * The response from the getUsers API call.
 */
public class GetUsersResponse {

	@JsonProperty("users")
	private List<UserDataWithCredentialCount> users;

	public List<UserDataWithCredentialCount> getUsers() {
		return users;
	}

	public void setUsers(List<UserDataWithCredentialCount> users) {
		this.users = users;
	}
}
