package jp.co.sgk.yubion.fss.sdk.data.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Parameters for the getUsers API call.
 */
public class GetUsersParameter {

	@JsonProperty("userIds")
	private List<String> userIds;

	@JsonProperty("withDisabled")
	private boolean withDisabled;

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public boolean isWithDisabled() {
		return withDisabled;
	}

	public void setWithDisabled(boolean withDisabled) {
		this.withDisabled = withDisabled;
	}
}
